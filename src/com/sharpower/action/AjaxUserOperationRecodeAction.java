package com.sharpower.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.UserOperationRecode;
import com.sharpower.service.UserOperationRecodeService;

public class AjaxUserOperationRecodeAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private UserOperationRecodeService userOperationRecodeService;
	
	private List<UserOperationRecode> userOperationRecodes = new ArrayList<>();
	private Map<String, Object> result = new HashMap<>();
	
	private Integer userId;
	private Date beginTime;
	private Date endTime;
	private int page;
	private int rows;

	public void setUserOperationRecodeService(UserOperationRecodeService userOperationRecodeService) {
		this.userOperationRecodeService = userOperationRecodeService;
	}
	
	public List<UserOperationRecode> getUserOperationRecodes() {
		return userOperationRecodes;
	}
	public Map<String, Object> getResult() {
		return result;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public String findUserOperationRecode(){
		String hql = "from UserOperationRecode ur where ur.user.id=? and ur.dateTime>? and ur.dateTime<?";
		long total;
		
		try {
			userOperationRecodes = userOperationRecodeService.findEntityByHQLPaging(hql, (page-1)*rows, rows, userId, beginTime, endTime);
			total = (Long) userOperationRecodeService.uniqueResult("SELECT count(*) From UserOperationRecode");
			
			result.put("total", total);
			result.put("rows", userOperationRecodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return SUCCESS;
	}
}
