package com.sharpower.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.util.SystemPropertyUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Fun;
import com.sharpower.entity.Variable;
import com.sharpower.service.FunService;
import com.sharpower.service.VariableTypeService;

import sun.print.resources.serviceui;

public class realTimeInfoAjaxAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private FunService funService;
	private VariableTypeService variableTypeService;
	
	private Fun fun;
	List<Variable> variables;
	private Map<Variable, Object> vals = new HashMap<>();

	@JSON(serialize=false)
	public Fun getFun() {
		return fun;
	}

	public void setFun(Fun fun) {
		this.fun = fun;
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	public Map<Variable, Object> getVals() {
		return vals;
	}
	
	public void setVals(Map<Variable, Object> vals) {
		this.vals = vals;
	}
	
	public String realTimeInfo(){
		String sql = "SELECT * FROM mainrecodetemp WHERE FUN_ID=?";
		
		String sql1 = "SELECT ";
		
		for (Variable variable : variables) {
			sql1 = sql1 + variable.getDbName() + ",";
		}
		
		sql1= sql1.substring(0, sql1.length()-1) + " FROM mainrecodetemp WHERE FUN_ID=?";
		
		
		List list = funService.executeSQLQuery(null, sql, fun.getId());
		
		return SUCCESS;
	}

}
