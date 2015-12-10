package com.sharpower.quartzs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.sharpower.beckhoff.FunDataReadWriteBeckhoffService;
import com.sharpower.entity.Fun;
import com.sharpower.entity.Variable;
import com.sharpower.scada.exception.AdsException;
import com.sharpower.service.FunService;
import com.sharpower.service.RecodeService;

public class FunDataQuartz implements Runnable {
	FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService;
	Fun fun;
	FunService funService;
	RecodeService recodeService;
	
	public FunDataQuartz() {
	}
	
	public void setFunService(FunService funService) {
		this.funService = funService;
	}
	
	public void setFunDataReadWriteBeckhoffService(FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService) {
		this.funDataReadWriteBeckhoffService = funDataReadWriteBeckhoffService;
	}
	
	public void setFun(Fun fun) {
		this.fun = fun;
	}
	
	public void setRecodeService(RecodeService recodeService) {
		this.recodeService = recodeService;
	}
	
	public void readData() {
		
		String sql1 = "DELETE FROM mainRecode_copy WHERE FUN_ID=? ";
		funService.executeSQL(sql1, fun.getId());
		
		try {
			Map<Variable, Object> data = funDataReadWriteBeckhoffService.readDataAll(fun.getAddress());
			Map<String, Object> saveData = new HashMap<>();
			
			saveData.put("fun", fun);
			saveData.put("dateTime", new Date());
			
			for ( Entry<Variable, Object> entry: data.entrySet()) {
				saveData.put(entry.getKey().getDbName(), entry.getValue());
			}
			
			recodeService.save(saveData);
						
		} catch (AdsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		System.out.println("定时任务:"+fun.getName());
		fun.setThreadSta(0);
	}

	@Override
	public void run() {
		this.readData();	
	}
}
