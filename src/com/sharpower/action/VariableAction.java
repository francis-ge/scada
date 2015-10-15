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
import com.sharpower.entity.Variable;
import com.sharpower.entity.VariableType;
import com.sharpower.service.VariableService;
import com.sharpower.service.VariableTypeService;

public class VariableAction extends ActionSupport implements ModelDriven<Variable>, Preparable, RequestAware, ServletContextAware {
	
	private static final long serialVersionUID = 1L;
	private VariableService variableService;
	private VariableTypeService variableTypeService;
	private Integer Id;
		
	public VariableService getVariableService() {
		return variableService;
	}
	public void setVariableService(VariableService variableService) {
		this.variableService = variableService;
	}
	public VariableTypeService getVariableTypeService() {
		return variableTypeService;
	}
	public void setVariableTypeService(VariableTypeService variableTypeService) {
		this.variableTypeService = variableTypeService;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer Id) {
		this.Id = Id;
	}
	
	public String variableManage(){
		//ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
		//System.out.println(ac.getBean("funService"));

		List<VariableType> variableTypes = variableTypeService.findAllEntities();
		requestMap.put("variableTypes", variableTypes);
		
		List<Variable> variables = variableService.findAllEntities();
		requestMap.put("variables", variables);

		return SUCCESS;
	}
	
	private String convertNameToDBname(String name){
		List<Variable> variables = variableService.findAllEntities();
		
		char dbNameConvertFlag=(char)64;
		
		String dbName = name.replace(".", "__");
		
		for(Variable val: variables){
			if(val.getName().equals(name)){
				dbNameConvertFlag=(char)((int)dbNameConvertFlag+1);
			}			
		}
		
		if((int)dbNameConvertFlag==64){
			return  dbName;
		}
		
		dbName = dbNameConvertFlag + "_" + dbName;
			
		return dbName;
	}
	
	public void prepareSave(){
		variable = new Variable();
	}
	public String save(){
		String dbName = convertNameToDBname(variable.getName());
		variable.setDbName(dbName);
		
		variableService.saveOrUpdateEntity(variable);	
		
		List<VariableType> variableTypes = variableTypeService.findAllEntities();
		requestMap.put("variableTypes", variableTypes);
		
		List<Variable> variables = variableService.findAllEntities();
		
		requestMap.put("variables", variables);
		
		
		addActionMessage("添加成功！");
		return SUCCESS;
	}
	
	public void prepareUpdate(){
		variable = new Variable();
	}
	
	public String update(){
		//转换变量名为数据存储名
		String dbName = convertNameToDBname(variable.getName());
		variable.setDbName(dbName);

		variableService.saveOrUpdateEntity(variable);	
		
		List<VariableType> variableTypes = variableTypeService.findAllEntities();
		requestMap.put("variableTypes", variableTypes);
		
		List<Variable> variables = variableService.findAllEntities();
		requestMap.put("variables", variables);

		addActionMessage("修改成功！");
		
		return SUCCESS;
	}
	
	public void prepareEdit(){
		variable = variableService.getEntity(Id);
	}

	public String edit(){
		List<VariableType> variableTypes = variableTypeService.findAllEntities();
		requestMap.put("variableTypes", variableTypes);
		return SUCCESS;
	}
	
	public void prepareDelete(){
		variable = variableService.getEntity(Id);
	
	}
	public String delete(){		
		List<VariableType> variableTypes = variableTypeService.findAllEntities();
		requestMap.put("variableTypes", variableTypes);
		
		if(variable==null){
			List<Variable> variables = variableService.findAllEntities();
			requestMap.put("variables", variables);
			
			addActionMessage("变量信息不存在!");
			return ERROR;
		}
		
		variableService.deleteEntity(variable);
		
		List<Variable> variables = variableService.findAllEntities();
		requestMap.put("variables", variables);
		
		addActionMessage("删除成功！");
		
		return SUCCESS;
	}
	
	private Variable variable;

	public Variable getVariable() {
		return variable;
	}
	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	@Override
	public Variable getModel() {
		return variable;
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
