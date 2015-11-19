package com.sharpower.action;

import java.io.File;
import java.io.IOException;
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
	private Integer Id;
	
	public FunService getFunService() {
		return funService;
	}
	public void setFunService(FunService funService) {
		this.funService = funService;
	}
	
	public void setPlcTypeService(PlcTypeService plcTypeService) {
		this.plcTypeService = plcTypeService;
	}
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer Id) {
		this.Id = Id;
	}
	
	public String funManage() throws IOException{
		//ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
		//System.out.println(ac.getBean("funService"));
		List<PlcType> plcTypes = plcTypeService.findAllEntities();
		requestMap.put("plcTypes", plcTypes);
		
		List<Fun> funs = funService.findAllEntities();
		requestMap.put("funs", funs);
		
		return SUCCESS;
	}
	
	public String allFun(){
		List<Fun> funs = funService.findAllEntities();
		requestMap.put("funs", funs);
		return SUCCESS;
	}
	
	public void prepareSave(){
		fun = new Fun();
	}
	public String save(){
		funService.saveOrUpdateEntity(fun);	
		
		List<PlcType> plcTypes = plcTypeService.findAllEntities();
		requestMap.put("plcTypes", plcTypes);
		
		List<Fun> funs = funService.findAllEntities();
		requestMap.put("funs", funs);
		
		addActionMessage("添加成功！");
		return SUCCESS;
	}
	
	public void prepareUpdate(){
		fun = new Fun();
	}
	public String update(){
		funService.saveOrUpdateEntity(fun);	
		List<PlcType> plcTypes = plcTypeService.findAllEntities();
		requestMap.put("plcTypes", plcTypes);
		
		List<Fun> funs = funService.findAllEntities();
		requestMap.put("funs", funs);
		
		addActionMessage("修改成功！");
		
		return SUCCESS;
	}
	
	public void prepareEditFun(){
		fun = funService.getEntity(Id);

	}

	public String editFun(){
		List<PlcType> plcTypes = plcTypeService.findAllEntities();
		requestMap.put("plcTypes", plcTypes);
		return SUCCESS;
	}
	
	public void prepareDelete(){
		fun = funService.getEntity(Id);
	
	}
	public String delete(){
		
		List<PlcType> plcTypes = plcTypeService.findAllEntities();
		requestMap.put("plcTypes", plcTypes);
		
		if(fun==null){
			List<Fun> funs = funService.findAllEntities();
			requestMap.put("funs", funs);
			
			addActionMessage("风机信息不存在!");
			return ERROR;
		}
		
		funService.deleteEntity(fun);
		
		List<Fun> funs = funService.findAllEntities();
		requestMap.put("funs", funs);
		
		addActionMessage("删除成功！");
		
		return SUCCESS;
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
