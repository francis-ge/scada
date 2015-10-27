package com.sharpower.entity;

import java.util.Set;

public class Fun {
	private int id;
	private String name;
	// PLC类型beckhoff
	private String type;
	private String address;
	private String line;
	private WindFarm windFarm;
	private Set<Variable> variables;
	private Integer threadSta=0;
	
	public Integer getThreadSta() {
		return threadSta;
	}
	
	public void setThreadSta(Integer threadSta) {
		this.threadSta = threadSta;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public WindFarm getWindFarm() {
		return windFarm;
	}

	public void setWindFarm(WindFarm windFarm) {
		this.windFarm = windFarm;
	}
	
	public Set<Variable> getVariables() {
		return variables;
	}

	public void setVariables(Set<Variable> variables) {
		this.variables = variables;
	}

	public Fun(int id, String name, String type, String address, String line) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.address = address;
		this.line = line;
	}

	public Fun() {
		super();
	}

}
