package com.sharpower.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class PlcType {
	private Integer id;
	private String name;
	private PlcCommType plcCommType;
	private List<Fun> funs;
	private List<Variable> variables = new ArrayList<>();
	private List<FunTroubleVariable> funTroubleVariables = new ArrayList<>();
	
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
	
	public PlcCommType getPlcCommType() {
		return plcCommType;
	}
	public void setPlcCommType(PlcCommType plcCommType) {
		this.plcCommType = plcCommType;
	}
	
	@JSON(serialize=false)
	public List<Fun> getFuns() {
		return funs;
	}
	
	public void setFuns(List<Fun> funs) {
		this.funs = funs;
	}
	
	@JSON(serialize=false)
	public List<Variable> getVariables() {
		return variables;
	}
	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}
	
	@JSON(serialize=false)
	public List<FunTroubleVariable> getFunTroubleVariables() {
		return funTroubleVariables;
	}
	public void setFunTroubleVariables(List<FunTroubleVariable> funTroubleVariables) {
		this.funTroubleVariables = funTroubleVariables;
	}
	
}
