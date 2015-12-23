package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Fun;
import com.sharpower.entity.Variable;
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
	
	public String mainInfo(){		

		realtimeInfo.addAll(windFarmDataQuartz.getDataMap().values());
		
		return SUCCESS;
	}
	
	public String realTimeInfo(){

		int id;
		if (fun==null){
			id = funId;
		}else{
			id= fun.getId();
		}
		
		realtimeInfo.add(windFarmDataQuartz.getDataMap().get(id));
		
		return SUCCESS;
	}



}
