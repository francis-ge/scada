package com.sharpower.quartzs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sharpower.beckhoff.FunTroubleBeckhoffService;
import com.sharpower.entity.Fun;
import com.sharpower.entity.FunTroubleRecode;
import com.sharpower.entity.FunTroubleVariable;
import com.sharpower.scada.exception.AdsException;
import com.sharpower.service.FunTroubleRecodeService;

public class FunTroubleQuartz implements Runnable{
	private FunTroubleBeckhoffService funTroubleBeckhoffService;
	private FunTroubleRecodeService funTroubleRecodeService;
	private Fun fun;
		
	public void setFunTroubleBeckhoffService(FunTroubleBeckhoffService funTroubleBeckhoffService) {
		this.funTroubleBeckhoffService = funTroubleBeckhoffService;
	}
	
	public void setFun(Fun fun) {
		this.fun = fun;
	}
	
	public void setFunTroubleRecodeService(FunTroubleRecodeService funTroubleRecodeService) {
		this.funTroubleRecodeService = funTroubleRecodeService;
	}
	
	public void readTrouble(){
		try {
			Map<FunTroubleVariable, Object> trouble = funTroubleBeckhoffService.readData(fun.getAddress());
			
			for (Entry<FunTroubleVariable, Object> entry : trouble.entrySet()) {
				String hql = "FROM FunTroubleRecode ftr WHERE ftr.fun.id=? AND ftr.id=(SELECT MAX(id) FROM FunTroubleRecode ftr1 WHERE ftr1.funTroubleVariable.id=?)"; 
				FunTroubleRecode funTroubleRecode = (FunTroubleRecode) funTroubleRecodeService.uniqueResult(hql, fun.getId(),entry.getKey().getId());
				
				if (funTroubleRecode!=null) {
					if ((Boolean)entry.getValue()) {
						if (funTroubleRecode.getEndTime()!=null) {
							//执行插入新故障记录操作
							FunTroubleRecode funTroubleRecode2 = new FunTroubleRecode();
							funTroubleRecode2.setFun(fun);
							funTroubleRecode2.setFunTroubleVariable(entry.getKey());
							funTroubleRecode2.setStartTime(new Date());
							funTroubleRecodeService.saveEntity(funTroubleRecode2);
						}
					}else {
						if (funTroubleRecode.getEndTime()==null) {
							//执行更新故障结束时间操作
							funTroubleRecode.setEndTime(new Date());
							funTroubleRecodeService.updateEntity(funTroubleRecode);
						}
					}
				}else {
					if ((Boolean)entry.getValue()) {
						//执行插入新故障记录操作
						FunTroubleRecode funTroubleRecode3 = new FunTroubleRecode();
						funTroubleRecode3.setFun(fun);
						funTroubleRecode3.setFunTroubleVariable(entry.getKey());
						funTroubleRecode3.setStartTime(new Date());
						funTroubleRecodeService.saveEntity(funTroubleRecode3);
						
					}
				}
			}
			
		} catch (AdsException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		readTrouble();
		
	}
}
