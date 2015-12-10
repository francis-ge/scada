package com.sharpower.entity;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import java.util.ArrayList;


public class VariableType{
	private Integer id;
	private String type;
	private Integer dbSize;
	
	private String plcType;
	private Integer plcSize;

	private List<Variable> vals=new ArrayList<>();
	
	public String getPlcType() {
		return plcType;
	}
	public void setPlcType(String plcType) {
		this.plcType = plcType;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getPlcSize() {
		return plcSize;
	}
	public void setPlcSize(Integer plcSize) {
		this.plcSize = plcSize;
	}
	
	public Integer getDbSize() {
		return dbSize;
	}
	public void setDbSize(Integer size) {
		this.dbSize = size;
	}
	
	@JSON(serialize=false)
	public List<Variable> getVals() {
		return vals;
	}
	public void setVals(List<Variable> vals) {
		this.vals = vals;
	}
	
}
