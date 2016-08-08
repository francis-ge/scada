package com.sharpower.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.Permission;

public class Role {
	private Integer id;
	private String name;
	private List<Resource> resources = new ArrayList<>();
	private List<User> users = new ArrayList<>();
	
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
	public List<Resource> getResources() {
		return resources;
	}
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
