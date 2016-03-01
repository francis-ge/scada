package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Fun;
import com.sharpower.entity.Variable;
import com.sharpower.entity.WindFarmRealTimeInfo;
import com.sharpower.quartzs.WindFarmDataQuartz;
import com.sharpower.service.RecodeService;
import com.sharpower.service.VariableService;


public class AjaxRealTimeInfoAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private VariableService variableService;
	private RecodeService recodeService;
	
	private WindFarmDataQuartz windFarmDataQuartz;
	
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
	
	public void setWindFarmDataQuartz(WindFarmDataQuartz windFarmDataQuartz) {
		this.windFarmDataQuartz = windFarmDataQuartz;
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

		realtimeInfo.addAll(windFarmDataQuartz.getDataMap().values());
		
		return SUCCESS;
	}
	
	public String windFarmRealTimeInfo(){
		Map<Integer, Map<String, Object>> dateMap = windFarmDataQuartz.getDataMap();
		
		int communicationFailureFunCount = 0;
		int serviceFunCount = 0;
		int runFunCount = 0;
		int standbyFunCount = 0;
		int errorFunCount = 0;
		float power = 0;
		float reactivePower = 0;
		float energy = 0;
		float energyCounter=0;
		float maxWindSpeed=0;
		float minWindSpeed=0;
		float averageWindSpeed=0;
		float maxPower=0;
		float minPower=0;
		float averagePower = 0;
		
		int i = 0;
		 
		for ( Map<String, Object> map : dateMap.values() ){
			i++;
			short mode = map.get("___main_loop_mode_number")!=null?(short)map.get("___main_loop_mode_number"):10;
			
			switch (mode) {
			case 0:
			case 1:
				errorFunCount++;
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
			
			power = map.get("___visu_grid_power")!=null?(float)map.get("___visu_grid_power")+power : 0;
			reactivePower = map.get("___visu_grid_reactive_power")!=null?(float)map.get("___visu_grid_reactive_power")+reactivePower : 0;
			energy = map.get("___visu_grid_energy")!=null?(float)map.get("___visu_grid_energy")+energy : 0; 
			energyCounter = map.get("___visu_grid_energy_counter")!=null?(float)map.get("___visu_grid_energy_counter")+energyCounter : 0;
			
			float windSpeed = 0;
			if (map.get("___wind_speed")!=null) {
				windSpeed = (float)map.get("___wind_speed");
				maxWindSpeed = Float.compare(windSpeed, maxWindSpeed)<=0?maxWindSpeed:windSpeed; 
				minWindSpeed = Float.compare(windSpeed, minWindSpeed)<=0?windSpeed:minWindSpeed;
				
			}
			
			float sumWindSpeed = 0;
			sumWindSpeed = windSpeed + sumWindSpeed;
			averageWindSpeed = sumWindSpeed / i;
			
			if(map.get("___visu_grid_power")!=null){	
				float nPower = (float)map.get("___visu_grid_power");
				maxPower = Float.compare(nPower, maxPower)<=0?maxPower:nPower;
				minPower = Float.compare(nPower, minPower)<=0?minPower:nPower;
			}
			
			averagePower = power / i;

		}
		
		WindFarmRealTimeInfo windFarmRealTimeInfo1= new WindFarmRealTimeInfo();
		
		windFarmRealTimeInfo1.setErrorFunCount(errorFunCount);
		windFarmRealTimeInfo1.setStandbyFunCount(standbyFunCount);
		windFarmRealTimeInfo1.setRunFunCount(runFunCount);
		windFarmRealTimeInfo1.setServiceFunCount(serviceFunCount);
		windFarmRealTimeInfo1.setCommunicationFailureFunCount(communicationFailureFunCount);
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
		
		Map<String, Object> map = windFarmDataQuartz.getDataMap().get(id);
		
		if (map!=null) {
			realtimeInfo.add(map);
		}
		
		return SUCCESS;
	}



}
