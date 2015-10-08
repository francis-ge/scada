package com.sharpower.action;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.util.ServletContextAware;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sharpower.entity.Fun;
import com.sharpower.entity.PlcType;
import com.sharpower.service.FunService;
import com.sharpower.service.PlcTypeService;

public class FunAction extends ActionSupport implements ModelDriven<Fun>, Preparable, RequestAware, ServletContextAware {
	
	private static final long serialVersionUID = 1L;
	private FunService funService;
	private PlcTypeService plcTypeService;
	
	public FunService getFunService() {
		return funService;
	}
	public void setFunService(FunService funService) {
		this.funService = funService;
	}
	
	public void setPlcTypeService(PlcTypeService plcTypeService) {
		this.plcTypeService = plcTypeService;
	}
	
	
	public String inputFun(){
		//ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
		//System.out.println(ac.getBean("funService"));
		List<PlcType> plcTypes = plcTypeService.findAllEntities();
		requestMap.put("plcTypes", plcTypes);
		return SUCCESS;
	}
	
	public void prepareSave(){
		fun = new Fun();
	}
	public String save(){
		funService.saveEntity(fun);
		inputFun();
		return "save-success";
	}
	
	private Fun fun;
	public Fun getFun() {
		return fun;
	}
	public void setFun(Fun fun) {
		this.fun = fun;
	}
	@Override
	public Fun getModel() {
		//fun = new Fun();
		return fun;
	}

	@Override
	public void prepare() throws Exception {

	}
	
	private ServletContext sc;
	@Override
	public void setServletContext(ServletContext arg0) {
		sc = arg0;
	}
	
	private Map<String, Object> requestMap;
	@Override
	public void setRequest(Map<String, Object> arg0) {
		requestMap = arg0;		
	}

}
