package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.FunTroubleVariable;
import com.sharpower.entity.Variable;
import com.sharpower.service.VariableService;
import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

public class AjaxVariableAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Variable variable;
	private VariableService variableService;
	private List<Variable> variables = new ArrayList<>();
	private String resulte;
	private String ids;
	private String searchKey;
	
	public Variable getVariable() {
		return variable;
	}
	
	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	
	@JSON(serialize=false)
	public VariableService getVariableService() {
		return variableService;
	}

	public void setVariableService(VariableService variableService) {
		this.variableService = variableService;
	}

	public List<Variable> getVariables() {
		return variables;
	}
	
	public String getResulte() {
		return resulte;
	}
	
	public void setResulte(String resulte) {
		this.resulte = resulte;
	}
	
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	
	public String allVariable(){
		try {
			if (searchKey==null) {
				variables = variableService.findAllEntities();
			}else{
				String hql = "From Variable v WHERE v.name like ?";
				variables = variableService.findEntityByHQL(hql, "%"+searchKey+"%");
			}
			resulte = "共查到" + variables.size() + "条记录。";
		} catch (Exception e) {
			e.printStackTrace();
			resulte = e.getMessage();
		}
		return SUCCESS;
	}
	
	/**
	 * 将PLC变量名转换为数据库存储名
	 * @param name plc变量名
	 * @return
	 */
	private String convertNameToDBname(String name){
		String hql = "From Variable v WHERE v.name=?";
		List<Variable> variables = variableService.findEntityByHQL(hql, name);
		
		String dbName = name.replace(".", "__");
		
		char dbNameConvertFlag;
		
		//解决数据库名称重名的问题,重复变量名添加大写字母前缀加“_”，变量名中的“.”转变为“__”.
		if(variables.size()>0){
			String lastDbName="";
			for (int i=variables.size()-1;i>=0;i--) {
				if  (variables.get(i).getDbName()!=""){
					lastDbName=variables.get(i).getDbName();
					break;
				}
			}
			
			if (lastDbName=="") {
				return name;
			}
			char head = lastDbName.charAt(0);
			
			if(head=='_'){
				dbNameConvertFlag = 'A';
			}else {				
				dbNameConvertFlag = (char)((int)head + 1);
			}
			return dbNameConvertFlag + "_" + dbName;

		}else {
			return "_" + dbName;
		}
		
	}
	
	public String saveOrUpdateVariable(){
		try {
			if (variable.getId()==null ) {
				variable.setDbName(this.convertNameToDBname(variable.getName()));
			}else {
				String updateName = variable.getName();
				Variable variable1 = variableService.getEntity(variable.getId());
				String oldName = variable1.getName();
				
				if (updateName.equals(oldName)==false) {
					variable.setDbName(this.convertNameToDBname(variable.getName()));
				}else{
					variable.setDbName(variable1.getDbName());
				}
			}
			
			variable.setDbName(this.convertNameToDBname(variable.getName()));
			variableService.saveOrUpdateEntity(variable);
			resulte = "保存成功！";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resulte= e.getMessage();
		}
		
		return SUCCESS;
	}
	
	public String deleteVariable(){
		
		String idString[] = ids.split(",");
		try {
			for (String idStr : idString) {
				Variable variable = new Variable();
				variable.setId(Integer.parseInt(idStr));
				variableService.deleteEntity(variable);
			}
			resulte = "删除成功！";
		} catch (Exception e) {
			e.printStackTrace();
			resulte = "删除失败！" + e.getMessage();
		}
		return SUCCESS;
	}
	
	public String findVariable(){
		try {
			String hql = "From Variable v WHERE v.name like ?";
			variables = variableService.findEntityByHQL(hql, "%"+searchKey+"%");
			resulte = "共查到" + variables.size() + "条记录。";
		} catch (Exception e) {
			e.printStackTrace();
			resulte = e.getMessage();
		}
		return SUCCESS;
	}

}
