package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.dao.DataIntegrityViolationException;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Fun;
import com.sharpower.service.FunService;

public class AjaxFunAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private Fun fun;
	private FunService funService;
	private List<Fun> funs = new ArrayList<>();
	private String result;
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
	
	public String getResult() {
		return result;
	}
	
	public void setResulte(String result) {
		this.result = result;
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
			result = "共查到" + funs.size() + "条记录。";
		} catch (Exception e) {
			result = "获取失败！"+e.getMessage();
		}
		
		return SUCCESS;
	}
	
	public String saveOrUpdateFun(){
		try {
			funService.saveOrUpdateEntity(fun);
			result = "保存成功！";
		} catch (Exception e) {
			result= e.getMessage();
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
			result = "删除成功！";
		}catch (DataIntegrityViolationException e) {
			
			e.printStackTrace();
			result = "存在相关数据记录，无法删除！" + e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			result = "删除失败！";
		}
		
		return SUCCESS;

	}

}
