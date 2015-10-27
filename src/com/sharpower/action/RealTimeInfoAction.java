package com.sharpower.action;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class RealTimeInfoAction extends ActionSupport implements RequestAware{

	private static final long serialVersionUID = 1L;
	
	public String funData(){
		
		return null;
	}

	Map<String, Object> request;
	@Override
	public void setRequest(Map<String, Object> arg0) {
		this.request = arg0;		
	}	
}