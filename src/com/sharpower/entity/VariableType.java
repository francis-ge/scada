package com.sharpower.entity;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.ArrayList;


public class VariableType{
	private int id;
	private String name;
	private Class clazz;
	private int size;
	
	private List<Variable> vals=new ArrayList<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	@JSON(serialize=false)
	public List<Variable> getVals() {
		return vals;
	}
	public void setVals(List<Variable> vals) {
		this.vals = vals;
	}
	
}
