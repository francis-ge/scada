package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.PlcType;
import com.sharpower.service.PlcTypeService;

public class AjaxPlcTypeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private PlcTypeService plcTypeService;
	private PlcType plcType;
	private List<PlcType> plcTypes = new ArrayList<>();
	
	public void setPlcTypeService(PlcTypeService plcTypeService) {
		this.plcTypeService = plcTypeService;
	}
	
	public void setPlcType(PlcType plcType) {
		this.plcType = plcType;
	}
	
	public List<PlcType> getPlcTypes() {
		return plcTypes;
	}

	public String allPlcType(){
		
		plcTypes = plcTypeService.findAllEntities();
		
		return SUCCESS; 
	}
}
