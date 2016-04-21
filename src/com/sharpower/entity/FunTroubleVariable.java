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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FunTroubleVariable other = (FunTroubleVariable) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
