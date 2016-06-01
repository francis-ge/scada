package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.VariableType;
import com.sharpower.service.VariableTypeService;

public class AjaxVariableTypeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private VariableTypeService variableTypeService;
	private List<VariableType> variableTypes = new ArrayList<>();

	private String result;
	
	
	@JSON(serialize=false)
	public VariableTypeService getVariableTypeService() {
		return variableTypeService;
	}

	public void setVariableTypeService(VariableTypeService variableTypeService) {
		this.variableTypeService = variableTypeService;
	}

	public List<VariableType> getVariableTypes() {
		return variableTypes;
	}

	public void setVariableTypes(List<VariableType> variableTypes) {
		this.variableTypes = variableTypes;
	}
	

	
	public String allVariableType(){
		try {
			variableTypes = variableTypeService.findAllEntities();
			
		} catch (Exception e) {
			result = "获取失败！"+e.getMessage();
		}
		
		return SUCCESS;
	}
	

}
