package com.sharpower.quartzs;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;
import org.hibernate.SQLQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Fun;
import com.sharpower.entity.Variable;
import com.sharpower.service.FunService;
import com.sharpower.service.VariableService;
import com.sharpower.service.WindFarmService;
import com.sharpower.service.impl.FunDataReadWriteBeckhoffService;

public class WindFarmDataQuartz {
	private WindFarmService windFarmService;
	private VariableService variableService;
	private Set<Fun> funs;
	private List<Variable> variables;
	private FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService;
	private FunService funService;
	
	public void setWindFarmService(WindFarmService windFarmService) {
		this.windFarmService = windFarmService;
	}
	
	public void setVariableService(VariableService variableService) {
		this.variableService = variableService;
	}
	
	public void setFunDataReadWriteBeckhoffService(FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService) {
		this.funDataReadWriteBeckhoffService = funDataReadWriteBeckhoffService;
	}
	
	public void setFunService(FunService funService) {
		this.funService = funService;
	}

	public void readData(){
		if(funs==null){
			funs = windFarmService.getEntity(1).getFuns();
			variables = variableService.findAllEntities();

			String sql = "CREATE TABLE IF NOT EXISTS "
					+ "mainRecodeTemp(FUN_ID int primary key not null, "; 
			
			String valType;
			String valName;
			
			for (Variable variable : variables) {
				valName = variable.getDbName();
				valType = variable.getType().getName();
				sql = sql + valName + " " + valType + ",";
				
			}
			
			sql = sql.substring(0, sql.length()-1);
			
			sql = sql + ") ENGINE=MEMORY DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";
			
			windFarmService.executeSQL(sql);
			
			  
		}
			
		//创建数据读取线程
		for (Fun fun : funs) {
			//检查上轮线程状态，如已完成则创建，未完成则不创建
			if(fun.getThreadSta()==0){				
				FunDataQuartz funDataQuartz = new FunDataQuartz();
				funDataQuartz.setFunDataReadWriteBeckhoffService(funDataReadWriteBeckhoffService);
				funDataQuartz.setFunService(funService);			
				funDataQuartz.setFun(fun);
				
				Thread thread = new Thread(funDataQuartz);
				thread.start();
				
				fun.setThreadSta(1);				
			}
		}
	}



	}
	

