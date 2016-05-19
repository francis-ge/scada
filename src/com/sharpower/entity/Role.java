package com.sharpower.entity;

import java.util.ArrayList;
import java.util.List;

public class Role {
	private Integer id;
	private List<User> users = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
