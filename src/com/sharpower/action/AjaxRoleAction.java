package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Role;
import com.sharpower.service.RoleService;

public class AjaxRoleAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private RoleService roleService;
	private Role role;
	private List<Role> roles = new ArrayList<>();
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public String allRoles(){
		try {
			roles = roleService.findAllEntities();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
