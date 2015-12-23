package com.sharpower.entity;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class TroubleType {
	private Integer id;
	private String name;
	private List<FunTroubleVariable> funTroubleVariables;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JSON(serialize=false)
	public List<FunTroubleVariable> getFunTroubleVariables() {
		return funTroubleVariables;
	}
	public void setFunTroubleVariables(List<FunTroubleVariable> funTroubleVariables) {
		this.funTroubleVariables = funTroubleVariables;
	}
	
	
}
