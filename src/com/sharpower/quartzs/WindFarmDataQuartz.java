package com.sharpower.quartzs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sharpower.beckhoff.FunDataReadWriteBeckhoffService;
import com.sharpower.beckhoff.FunTroubleBeckhoffService;
import com.sharpower.entity.Fun;
import com.sharpower.service.FunTroubleRecodeService;
import com.sharpower.service.RecodeService;
import com.sharpower.service.WindFarmService;

public class WindFarmDataQuartz {
	private WindFarmService windFarmService;
	private Set<Fun> funs;
	private FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService;
	private RecodeService recodeService;
	
	private Map<Integer,Map<String, Object>> dataMap = new HashMap<>();
	
	private Map<String, Object> params;
	
	public void setWindFarmService(WindFarmService windFarmService) {
		this.windFarmService = windFarmService;
	}
	
	public void setFunDataReadWriteBeckhoffService(FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService) {
		this.funDataReadWriteBeckhoffService = funDataReadWriteBeckhoffService;
	}
	
	public void setRecodeService(RecodeService recodeService) {
		this.recodeService = recodeService;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Map<Integer, Map<String, Object>> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<Integer, Map<String, Object>> dataMap) {
		this.dataMap = dataMap;
	}

	public void readData(){
		if(funs==null){
			funs = windFarmService.getEntity(1).getFuns();
		}
			
		//创建数据读取线程
		for (Fun fun : funs) {
			//检查上轮线程状态，如已完成则创建新线程，未完成则不创建				
				FunDataQuartz funDataQuartz = new FunDataQuartz();
				funDataQuartz.setFunDataReadWriteBeckhoffService(funDataReadWriteBeckhoffService);
				funDataQuartz.setFun(fun);
				funDataQuartz.setParams(params);
				funDataQuartz.setDataMap(dataMap);
				
				Thread thread = new Thread(funDataQuartz);
				thread.start();
				
			}
		
	}
	
	public void saveData(){
		Date now = new Date();
		
		for (Entry<Integer,Map<String, Object>> entry : dataMap.entrySet()) {
			Map<String, Object> dataTemp = new HashMap<>();

			dataTemp.putAll(entry.getValue());
			
			dataTemp.put("id", null);
			dataTemp.put("dateTime", now);
			
			recodeService.save(dataTemp);
		}
		
	}

	private FunTroubleBeckhoffService funTroubleBeckhoffService;
	private FunTroubleRecodeService funTroubleRecodeService;
	
	public void setFunTroubleBeckhoffService(FunTroubleBeckhoffService funTroubleBeckhoffService) {
		this.funTroubleBeckhoffService = funTroubleBeckhoffService;
	}
	public void setFunTroubleRecodeService(FunTroubleRecodeService funTroubleRecodeService) {
		this.funTroubleRecodeService = funTroubleRecodeService;
	}
	
	public void checkTrouble(){
		if(funs==null){
			funs = windFarmService.getEntity(1).getFuns();
		}
			
		//创建风机故障检测线程
		for (Fun fun : funs) {
			//检查上轮线程状态，如已完成则创建新线程，未完成则不创建
			FunTroubleQuartz funTroubleQuartz = new FunTroubleQuartz();
			funTroubleQuartz.setFun(fun);
			funTroubleQuartz.setFunTroubleBeckhoffService(funTroubleBeckhoffService);
			funTroubleQuartz.setFunTroubleRecodeService(funTroubleRecodeService);
			
			Thread thread = new Thread(funTroubleQuartz);
			thread.start();
				
			}
	}
	

}
	

