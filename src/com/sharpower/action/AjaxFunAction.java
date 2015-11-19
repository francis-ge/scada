package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Fun;
import com.sharpower.service.FunService;

public class AjaxFunAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private FunService funService;
	private List<Fun> funs = new ArrayList<>();
	
	public String allFun(){
		funs = funService.findAllEntities();
		return SUCCESS;
	}

	@JSON(serialize=false)
	public FunService getFunService() {
		return funService;
	}

	public void setFunService(FunService funService) {
		this.funService = funService;
	}

	public List<Fun> getFuns() {
		return funs;
	}

	public void setFuns(List<Fun> funs) {
		this.funs = funs;
	}
}
