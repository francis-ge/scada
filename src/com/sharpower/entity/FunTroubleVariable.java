package com.sharpower.entity;

public class FunTroubleVariable {
	private Integer id;
	private String name;
	private String dbName;
	private String description;
	private String code;
	private TroubleType type;//0:Error, 1:Worning
	private PlcType plcType; 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public TroubleType getType() {
		return type;
	}
	public void setType(TroubleType type) {
		this.type = type;
	}
	public PlcType getPlcType() {
		return plcType;
	}
	public void setPlcType(PlcType plcType) {
		this.plcType = plcType;
	}
}
