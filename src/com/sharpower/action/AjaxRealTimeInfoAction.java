package com.sharpower.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Fun;
import com.sharpower.entity.WindFarmRealTimeInfo;
import com.sharpower.quartzs.DataQuartz;
import com.sharpower.service.RecodeService;
import com.sharpower.service.VariableService;


public class AjaxRealTimeInfoAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private VariableService variableService;
	private RecodeService recodeService;
	
	private DataQuartz plcTypeDataQuartz;
	
	private Fun fun;
	private int funId;
	
	private List<Map<String, Object>> realtimeInfo=new ArrayList<>();	
	private List<WindFarmRealTimeInfo> windFarmRealTimeInfo= new ArrayList<>();
	
	@JSON(serialize=false)
	public Fun getFun() {
		return fun;
	}

	public void setFun(Fun fun) {
		this.fun = fun;
	}

	@JSON(serialize=false)
	public RecodeService getRecodeService() {
		return recodeService;
	}
	
	public VariableService getVariableService() {
		return variableService;
	}

	public void setVariableService(VariableService variableService) {
		this.variableService = variableService;
	}

	public void setRecodeService(RecodeService recodeService) {
		this.recodeService = recodeService;
	}
	
	public void setPlcTypeDataQuartz(DataQuartz plcTypeDataQuartz) {
		this.plcTypeDataQuartz = plcTypeDataQuartz;
	}
	
	@JSON(serialize=false)
	public int getFunId() {
		return funId;
	}
	
	public void setFunId(int funId) {
		this.funId = funId;
	}
	
	public List<Map<String, Object>> getRealtimeInfo() {
		return realtimeInfo;
	}
	
	public List<WindFarmRealTimeInfo> getWindFarmRealTimeInfo() {
		return windFarmRealTimeInfo;
	}
	
	public String mainInfo(){		
		realtimeInfo.addAll(plcTypeDataQuartz.getDataMap().values());
		
		Collections.sort(realtimeInfo, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return (int)o1.get("id")-(int)o2.get("id");
			}
		});
		
		return SUCCESS;
	}
	
	public String windFarmRealTimeInfo(){
		Map<Integer, Map<String, Object>> dateMap = plcTypeDataQuartz.getDataMap();
		
		int communicationFailureFunCount = 0;
		int serviceFunCount = 0;
		int runFunCount = 0;
		int standbyFunCount = 0;
		int stopFunCount = 0;
		int errorFunCount = 0;
		float power = 0;
		float reactivePower = 0;
		float energy = 0;
		float energyCounter=0;
		float maxWindSpeed=0;
		float minWindSpeed=999;
		float averageWindSpeed=0;
		float maxPower=0;
		float minPower=99999;
		float averagePower = 0;
		
		float sumWindSpeed = 0;
		 
		for ( Map<String, Object> map : dateMap.values() ){
			
			short mode = map.get("___main_loop_mode_number")!=null?(short)map.get("___main_loop_mode_number"):10;
			
			switch (mode) {
			case 0:
			case 1:
				stopFunCount++;
				break;
			case 2:
				standbyFunCount++;
				break;
			case 3:
			case 4:
			case 5:
				runFunCount++;
				break;
			case 9:
				serviceFunCount++;
				break;
			case 10:
				communicationFailureFunCount++;
				break;
			}
			
			if (map.get("___error_error_global")!=null) {
				if ((boolean)map.get("___error_error_global")) {
					errorFunCount++;
				}
			}
			
			power = map.get("___visu_grid_power")!=null?(float)map.get("___visu_grid_power")+power : power;
			reactivePower = map.get("___visu_grid_reactive_power")!=null?(float)map.get("___visu_grid_reactive_power")+reactivePower : reactivePower;
			energy = map.get("___visu_grid_energy")!=null?(float)map.get("___visu_grid_energy")+energy : energy; 
			energyCounter = map.get("___visu_grid_energy_counter")!=null?(float)map.get("___visu_grid_energy_counter")+energyCounter : energyCounter;
			
			float windSpeed = 0;
			if (map.get("___wind_speed")!=null) {
				windSpeed = (float)map.get("___wind_speed");
				maxWindSpeed = Float.compare(windSpeed, maxWindSpeed)<=0?maxWindSpeed:windSpeed;
				minWindSpeed = Float.compare(windSpeed, minWindSpeed)<=0?windSpeed:minWindSpeed;
			}
			
			sumWindSpeed = windSpeed + sumWindSpeed;
			
			if(map.get("___visu_grid_power")!=null){	
				float nPower = (float)map.get("___visu_grid_power");
				maxPower = Float.compare(nPower, maxPower)<=0?maxPower:nPower;
				minPower = Float.compare(nPower, minPower)<=0?minPower:nPower;
			}

		}
		
		if (minWindSpeed==999) {
			minWindSpeed=0;
		}
		
		if (minPower==99999) {
			minPower=0;
		}
		
		if (dateMap.size()-communicationFailureFunCount>0) {
			averageWindSpeed = sumWindSpeed / (dateMap.size()-communicationFailureFunCount);
			averagePower = power / (dateMap.size()-communicationFailureFunCount);
		}
		
		WindFarmRealTimeInfo windFarmRealTimeInfo1= new WindFarmRealTimeInfo();
		
		windFarmRealTimeInfo1.setStopFunCount(stopFunCount);
		windFarmRealTimeInfo1.setStandbyFunCount(standbyFunCount);
		windFarmRealTimeInfo1.setRunFunCount(runFunCount);
		windFarmRealTimeInfo1.setServiceFunCount(serviceFunCount);
		windFarmRealTimeInfo1.setCommunicationFailureFunCount(communicationFailureFunCount);
		windFarmRealTimeInfo1.setErrorFunCount(errorFunCount);
		windFarmRealTimeInfo1.setPower(power);
		windFarmRealTimeInfo1.setReactivePower(reactivePower);
		windFarmRealTimeInfo1.setEnergy(energy);
		windFarmRealTimeInfo1.setEnergyCounter(energyCounter);
		windFarmRealTimeInfo1.setMaxWindSpeed(maxWindSpeed);
		windFarmRealTimeInfo1.setMinWindSpeed(minWindSpeed);
		windFarmRealTimeInfo1.setAverageWindSpeed(averageWindSpeed);
		windFarmRealTimeInfo1.setMaxPower(maxPower);
		windFarmRealTimeInfo1.setMinPower(minPower);
		windFarmRealTimeInfo1.setAveragePower(averagePower);
		
		windFarmRealTimeInfo.add(windFarmRealTimeInfo1);
		
		return SUCCESS;
	}
	
	public String realTimeInfo(){

		int id;
		if (fun==null){
			id = funId;
		}else{
			id= fun.getId();
		}
		
		Map<String, Object> map = plcTypeDataQuartz.getDataMap().get(id);
		
		if (map!=null) {
			realtimeInfo.add(map);
		}
		
		return SUCCESS;
	}



}
