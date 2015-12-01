package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Fun;
import com.sharpower.service.FunService;

public class AjaxFunAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Fun fun;
	private FunService funService;
	private List<Fun> funs = new ArrayList<>();
	private String resulte;
	private String ids;
	
	public Fun getFun() {
		return fun;
	}
	
	public void setFun(Fun fun) {
		this.fun = fun;
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
	
	public String getResulte() {
		return resulte;
	}
	
	public void setResulte(String resulte) {
		this.resulte = resulte;
	}
	
	public String getIds() {
		return ids;
	}
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public String allFun(){
		try {
			funs = funService.findAllEntities();
			
		} catch (Exception e) {
			resulte = "获取失败！"+e.getMessage();
		}
		
		return SUCCESS;
	}
	
	public String saveOrUpdateFun(){
		try {
			funService.saveOrUpdateEntity(fun);
			resulte = "保存成功！";
		} catch (Exception e) {
			// TODO: handle exception
			resulte= e.getMessage();
		}
		
		return SUCCESS;
	}
	
	public String deleteFun(){
		
		String idString[] = ids.split(",");
		try {
			for (String idStr : idString) {
				Fun fun = new Fun();
				fun.setId(Integer.parseInt(idStr));
				funService.deleteEntity(fun);
			}
			resulte = "删除成功！";
		} catch (Exception e) {
			resulte = "删除失败！" + e.getMessage();
		}
		return SUCCESS;
	}

}
