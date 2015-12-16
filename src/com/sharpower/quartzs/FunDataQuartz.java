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
	private FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService;
	private Fun fun;
	private RecodeService recodeService;
	private Map<String, Object> params;
	
	public FunDataQuartz() {
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
	
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	public void readData() {	
		Map<String, Object> saveData = new HashMap<>();
		
		try {
			Map<Variable, Object> data = funDataReadWriteBeckhoffService.readDataAll(fun.getAddress());
			
			saveData.put("id", fun.getId());
			saveData.put("fun", fun);
			saveData.put("dateTime", new Date());
			
			for ( Entry<Variable, Object> entry: data.entrySet()) {
				saveData.put(entry.getKey().getDbName(), entry.getValue());
			}
			
			if(saveData.get("___wind_speed")!=null ){
				float windSpeed = (float)saveData.get("___wind_speed") + (float)params.get("windSpeedParam");
				saveData.put("___wind_speed", windSpeed);
			}
		
			if (saveData.get("___visu_grid_power")!=null) {
				float power = (float)saveData.get("___visu_grid_power") * (float)params.get("powerParam");	
				saveData.put("___visu_grid_power", power);
			}
			
			recodeService.saveOrUpdate("MainRecode_copy", saveData);
						
		} catch (AdsException e) {
			saveData.put("id", fun.getId());
			saveData.put("fun", fun);
			
			//发生ADS异常时，设置风机模式为10(通信断开)
			saveData.put("___main_loop_mode_number", Short.valueOf("10"));
			
			//保持时间为连接断开时的时间
			Map<String, Object> oldData = recodeService.get("MainRecode_copy", fun.getId());
			if (oldData!=null) {
				saveData.put("dateTime", oldData.get("dateTime"));
			}else{
				saveData.put("dateTime", new Date());
			}
			
			recodeService.saveOrUpdate("MainRecode_copy", saveData);
			
			e.printStackTrace();
		}
			
		System.out.println("定时任务:"+fun.getName());

	}

	@Override
	public void run() {
		this.readData();	
	}
}
