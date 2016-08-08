package com.sharpower.entity;

import java.util.Date;

public class User {
	private Integer id;
	private String name="";
	private String password="";
	private Boolean disabled=false;
	private Date creatDate;
	private Role role;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreatDate() {
		return creatDate;
	}
	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public User() {
	}
	public User(Integer id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}	
	
}
