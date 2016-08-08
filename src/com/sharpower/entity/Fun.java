package com.sharpower.entity;

import org.apache.struts2.json.annotations.JSON;

public class Fun {
	private Integer id;
	private String name;
	// PLC类型beckhoff
	private PlcType plcType;
	private String address;
	private String line;
	private boolean disabled=false;
	private WindFarm windFarm;

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

	public PlcType getPlcType() {
		return plcType;
	}

	public void setPlcType(PlcType plcType) {
		this.plcType = plcType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
	
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@JSON(serialize=false)
	public WindFarm getWindFarm() {
		return windFarm;
	}

	public void setWindFarm(WindFarm windFarm) {
		this.windFarm = windFarm;
	}
	
	public Fun(int id, String name, PlcType type, String address, String line) {
		super();
		this.id = id;
		this.name = name;
		this.plcType = type;
		this.address = address;
		this.line = line;
	}

	public Fun() {
		super();
	}

}
