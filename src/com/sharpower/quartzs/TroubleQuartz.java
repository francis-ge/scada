package com.sharpower.quartzs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sharpower.beckhoff.FunTroubleBeckhoffService;
import com.sharpower.entity.Fun;
import com.sharpower.entity.FunTroubleRecode;
import com.sharpower.entity.FunTroubleVariable;
import com.sharpower.entity.PlcType;
import com.sharpower.service.FunTroubleRecodeService;
import com.sharpower.service.FunTroubleVariableReader;
import com.sharpower.service.PlcTypeService;

import de.beckhoff.jni.tcads.AdsCallDllFunction;

public class TroubleQuartz {
	private PlcTypeService plcTypeService;
	private FunTroubleBeckhoffService funTroubleBeckhoffService;
	private FunTroubleRecodeService funTroubleRecodeService;
	private List<Fun> funs = new ArrayList<>();
	private List<PlcType> plcTypes = new ArrayList<>();
	private Map<Integer, Map<FunTroubleVariable, FunTroubleRecode>> funTroubleRecodeMap = new ConcurrentHashMap<>();
	private Map<Integer, Boolean> readDataTheadStaMap = new HashMap<>();
	
	public void setPlcTypeService(PlcTypeService plcTypeService) {
		this.plcTypeService = plcTypeService;
	}
	
	public void setFunTroubleBeckhoffService(FunTroubleBeckhoffService funTroubleBeckhoffService) {
		this.funTroubleBeckhoffService = funTroubleBeckhoffService;
	}
	
	public void setFunTroubleRecodeService(FunTroubleRecodeService funTroubleRecodeService) {
		this.funTroubleRecodeService = funTroubleRecodeService;
	}
	
	private void init(){
		if (funs.isEmpty()){
			plcTypes = plcTypeService.findEntityByHQL("FROM PlcType p LEFT JOIN FETCH p.funs");
			
			for ( PlcType plcType : plcTypes ) {
				for (Fun fun : plcType.getFuns()) {
					if (!fun.isDisabled()) {
						funs.add(fun);
					}
				}
			}
		
			for (Fun fun : funs) {
				readDataTheadStaMap.put(fun.getId(), false);
				
				if (fun.getPlcType().getPlcCommType().getName().equals("beckhoff")) {					
					AdsCallDllFunction.adsPortOpen();
				}
			
			}
		
		}
	}

	public void checkTrouble(){
		synchronized (this) {
			init();
			//创建风机故障检测线程
			for (Fun fun : funs) {
				
				FunTroubleVariableReader funTroubleVariableReader;
				if (fun.getPlcType().getPlcCommType().getName().equals("beckhoff")) {
					funTroubleVariableReader = funTroubleBeckhoffService;
				}else {
					//其它种类的PLC读取
					funTroubleVariableReader = funTroubleBeckhoffService;
				}
				
				//检查上轮线程状态，如已完成则创建新线程，未完成则不创建		
				Boolean threadSta = readDataTheadStaMap.get(fun.getId());
	
				if (threadSta!=true) {
					readDataTheadStaMap.put(fun.getId(), true);
					
					FunTroubleThread funTroubleThread = new FunTroubleThread();
					funTroubleThread.setFun(fun);
					funTroubleThread.setFunTroubleRecodeMap(funTroubleRecodeMap);
					funTroubleThread.setFunTroubleBeckhoffService(funTroubleVariableReader);
					funTroubleThread.setFunTroubleRecodeService(funTroubleRecodeService);
					funTroubleThread.setReadDataTheadStaMap(readDataTheadStaMap);
					
					Thread thread = new Thread(funTroubleThread);
					thread.start();
				}
			}
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
		for (Fun fun : funs) {
			AdsCallDllFunction.adsPortClose();
		}
	}
}
