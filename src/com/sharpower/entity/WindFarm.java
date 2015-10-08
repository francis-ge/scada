package com.sharpower.entity;

import java.util.HashSet;
import java.util.Set;

public class WindFarm {
	private int id;
	private String name;
	
	private Set<Fun> funs = new HashSet<Fun>();
	
	{		

		//Fun fun = new Fun(1, "1#", "beckhoff", "192.168.100.61.1.1:852", "A");
		//funs.put(fun.getId(), fun);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Fun> getFuns() {
		return funs;
	}

	public void setFuns(Set<Fun> funs) {
		this.funs = funs;
	}
	
}