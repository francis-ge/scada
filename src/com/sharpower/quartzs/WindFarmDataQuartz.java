package com.sharpower.quartzs;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sharpower.beckhoff.FunDataReadWriteBeckhoffService;
import com.sharpower.entity.Fun;
import com.sharpower.service.RecodeService;
import com.sharpower.service.WindFarmService;

public class WindFarmDataQuartz {
	private WindFarmService windFarmService;
	private Set<Fun> funs;
	private FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService;
	private RecodeService recodeService;
	
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

	public void readData(){
		if(funs==null){
			funs = windFarmService.getEntity(1).getFuns();
		}
			
		//创建数据读取线程
		for (Fun fun : funs) {
			//检查上轮线程状态，如已完成则创建新线程，未完成则不创建				
				FunDataQuartz funDataQuartz = new FunDataQuartz();
				funDataQuartz.setFunDataReadWriteBeckhoffService(funDataReadWriteBeckhoffService);
				funDataQuartz.setRecodeService(recodeService);
				funDataQuartz.setFun(fun);
				funDataQuartz.setParams(params);
				
				Thread thread = new Thread(funDataQuartz);
				thread.start();
				
			}
		
	}
	
	public void saveData(){
		String hql = "FROM MainRecode_copy";
		List<Map<String, Object>> data = recodeService.findMapByHql(hql);
		
		Date now = new Date();
		
		for (Map<String, Object> map : data) {
			map.put("id", null);
			map.put("dateTime", now);
			
			recodeService.save("MainRecode", map);
		}
		
	}

	public void errorWorningCheck(){
		
	}



}
	

