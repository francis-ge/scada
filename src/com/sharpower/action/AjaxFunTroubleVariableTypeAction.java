package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.TroubleType;
import com.sharpower.entity.VariableType;
import com.sharpower.service.FunTroubleVariableTypeService;
import com.sharpower.service.VariableTypeService;

public class AjaxFunTroubleVariableTypeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private FunTroubleVariableTypeService funTroubleVariableTypeService;
	private List<TroubleType> troubleTypes = new ArrayList<>();

	private String result;
	
	public void setFunTroubleVariableTypeService(FunTroubleVariableTypeService funTroubleVariableTypeService) {
		this.funTroubleVariableTypeService = funTroubleVariableTypeService;
	}

	public List<TroubleType> getTroubleTypes() {
		return troubleTypes;
	}
	
	public String all(){
		try {
			troubleTypes = funTroubleVariableTypeService.findAllEntities();
			
		} catch (Exception e) {
			result = "获取失败！"+e.getMessage();
		}
		
		return SUCCESS;
	}
	

}
