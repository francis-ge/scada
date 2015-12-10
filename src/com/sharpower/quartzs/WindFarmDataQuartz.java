package com.sharpower.quartzs;

import java.util.List;
import java.util.Set;

import com.sharpower.beckhoff.FunDataReadWriteBeckhoffService;
import com.sharpower.entity.Fun;
import com.sharpower.entity.Variable;
import com.sharpower.service.FunService;
import com.sharpower.service.RecodeService;
import com.sharpower.service.VariableService;
import com.sharpower.service.WindFarmService;

public class WindFarmDataQuartz {
	private WindFarmService windFarmService;
	private VariableService variableService;
	private Set<Fun> funs;
	private List<Variable> variables;
	private FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService;
	private FunService funService;
	private RecodeService recodeService;
	
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
	
	public void setRecodeService(RecodeService recodeService) {
		this.recodeService = recodeService;
	}

	public void readData(){
		if(funs==null){
			funs = windFarmService.getEntity(1).getFuns();
			variables = variableService.findAllEntities();			  
		}
			
		//创建数据读取线程
		for (Fun fun : funs) {
			//检查上轮线程状态，如已完成则创建新线程，未完成则不创建
			if(fun.getThreadSta()==0){				
				FunDataQuartz funDataQuartz = new FunDataQuartz();
				funDataQuartz.setFunDataReadWriteBeckhoffService(funDataReadWriteBeckhoffService);
				funDataQuartz.setFunService(funService);
				funDataQuartz.setRecodeService(recodeService);
				funDataQuartz.setFun(fun);
				
				Thread thread = new Thread(funDataQuartz);
				thread.start();
				
				fun.setThreadSta(1);				
			}
		}
	}

	public void errorWorningCheck(){
		
	}

}
	

