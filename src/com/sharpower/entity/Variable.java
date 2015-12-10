package com.sharpower.entity;

public class Variable{
	private Integer id;
	private String name;
	private String dbName;
	private String showNameCN;
	private VariableType type;
	
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
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public String getShowNameCN() {
		return showNameCN;
	}
	public void setShowNameCN(String showName) {
		this.showNameCN = showName;
	}
	public VariableType getType() {
		return type;
	}
	public void setType(VariableType type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Variable [id=" + id + ", name=" + name + ", dbName=" + dbName + ", type=" + type + "]";
	}
		
	
}
