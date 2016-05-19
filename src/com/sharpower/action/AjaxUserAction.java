package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.User;
import com.sharpower.service.UserService;

public class AjaxUserAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private User user;
	private UserService userService;
	private String result;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getResult() {
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String verificationUser(){
		String hql = "FROM User u WHERE u.name=?";
		List<User> users = new ArrayList<>();
		
		try {
			users = userService.findEntityByHQL(hql, user.getName());
			
			if (users.isEmpty()) {
				result = "用户名错误。";
			}else {
				User user1 = new User();
				user1 = users.get(0);
				if (user1.getPassword().equals(user.getPassword())) {
					result = "验证成功！";
				}else{
					result = "密码错误。";
				}
			}
			
		} catch (Exception e) {
			e.getMessage();
		}
		
		return SUCCESS;
	}

}
