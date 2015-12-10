package com.sharpower.entity;

import java.util.HashSet;
import java.util.Set;

public class WindFarm {
	private Integer id;
	private String name;
	
	private Set<Fun> funs = new HashSet<Fun>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Fun> getFuns() {
		return funs;
	}

	public void setFuns(Set<Fun> funs) {
		this.funs = funs;
	}
	
}