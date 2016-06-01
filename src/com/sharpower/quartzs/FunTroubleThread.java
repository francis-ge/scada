package com.sharpower.quartzs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.sharpower.entity.Fun;
import com.sharpower.entity.FunTroubleRecode;
import com.sharpower.entity.FunTroubleVariable;
import com.sharpower.scada.exception.PlcException;
import com.sharpower.service.FunTroubleRecodeService;
import com.sharpower.service.FunTroubleVariableReader;

public class FunTroubleThread implements Runnable {
	private FunTroubleVariableReader funTroubleVariableReader;
	private FunTroubleRecodeService funTroubleRecodeService;
	private Fun fun;
	private Map<Integer, Map<FunTroubleVariable, FunTroubleRecode>> funTroubleRecodeMap = new HashMap<>();
	
	private Map<Integer, Boolean> readDataTheadStaMap = new HashMap<>();

	public void setFunTroubleBeckhoffService(FunTroubleVariableReader funTroubleVariableReader) {
		this.funTroubleVariableReader = funTroubleVariableReader;
	}

	public void setFun(Fun fun) {
		this.fun = fun;
	}

	public void setFunTroubleRecodeMap(Map<Integer, Map<FunTroubleVariable, FunTroubleRecode>> funTroubleRecodeMap) {
		this.funTroubleRecodeMap = funTroubleRecodeMap;
	}

	public void setFunTroubleRecodeService(FunTroubleRecodeService funTroubleRecodeService) {
		this.funTroubleRecodeService = funTroubleRecodeService;
	}
	
	public void setReadDataTheadStaMap(Map<Integer, Boolean> readDataTheadStaMap) {
		this.readDataTheadStaMap = readDataTheadStaMap;
	}

	public void readTrouble() {
		try {
			Map<FunTroubleVariable, Object> troubles = funTroubleVariableReader.readDataAll(fun);

			Map<FunTroubleVariable, FunTroubleRecode> funTroubleRecodes = funTroubleRecodeMap.get(fun.getId());
			
			if (funTroubleRecodes==null) {
				funTroubleRecodes = new HashMap<>();
				funTroubleRecodeMap.put(fun.getId(), funTroubleRecodes);
			}

			for (Entry<FunTroubleVariable, Object> entry : troubles.entrySet()) {
				if (entry.getValue()==null) {
					continue;
				}
				
				FunTroubleRecode funTroubleRecode = funTroubleRecodes.get(entry.getKey());

				if (funTroubleRecode == null) {
					String hql = "FROM FunTroubleRecode ftr WHERE ftr.fun.id=? AND ftr.id=(SELECT MAX(id) FROM FunTroubleRecode ftr1 WHERE ftr1.funTroubleVariable.id=?)";
					funTroubleRecode = (FunTroubleRecode) funTroubleRecodeService.uniqueResult(hql, fun.getId(),
							entry.getKey().getId());
				}

				if (funTroubleRecode != null) {
					if ((Boolean)entry.getValue()) {
						if (funTroubleRecode.getEndTime() != null) {
							// 执行插入新故障记录操作
							FunTroubleRecode funTroubleRecode2 = new FunTroubleRecode();
							funTroubleRecode2.setFun(fun);
							funTroubleRecode2.setFunTroubleVariable(entry.getKey());
							funTroubleRecode2.setStartTime(new Date());
							funTroubleRecodeService.saveEntity(funTroubleRecode2);

							funTroubleRecode = funTroubleRecode2;
						}else{
							if (funTroubleRecode.getStartTime()==null) {
								funTroubleRecode.setStartTime(new Date());
								funTroubleRecodeService.saveEntity(funTroubleRecode);
							}
						}
					} else {
						if (funTroubleRecode.getStartTime()!=null && funTroubleRecode.getEndTime() == null) {
							// 执行更新故障结束时间操作
							funTroubleRecode.setEndTime(new Date());
							System.out.println(funTroubleRecode);
							funTroubleRecodeService.updateEntity(funTroubleRecode);
						}
					}
				} else {
					if ((Boolean) entry.getValue()) {
						// 执行插入新故障记录操作
						funTroubleRecode = new FunTroubleRecode();
						funTroubleRecode.setFun(fun);
						funTroubleRecode.setFunTroubleVariable(entry.getKey());
						funTroubleRecode.setStartTime(new Date());
						funTroubleRecodeService.saveEntity(funTroubleRecode);

					}else{
						funTroubleRecode = new FunTroubleRecode();
						funTroubleRecode.setFun(fun);
						funTroubleRecode.setFunTroubleVariable(entry.getKey());
					}
				}

				funTroubleRecodes.put(entry.getKey(), funTroubleRecode);
			}
			
		} catch (PlcException e) {
			e.printStackTrace();
		}finally {
			readDataTheadStaMap.put(fun.getId(), false);
		}

	}

	@Override
	public void run() {
		readTrouble();

	}
}
