package com.sharpower.quartzs;

import java.util.Map;

import com.sharpower.entity.Fun;
import com.sharpower.entity.Variable;
import com.sharpower.scada.exception.AdsException;
import com.sharpower.service.FunService;
import com.sharpower.service.impl.FunDataReadWriteBeckhoffService;

public class FunDataQuartz implements Runnable {
	
	FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService;
	Fun fun;
	FunService funService;
	
	public FunDataQuartz() {
	}
	
	public void setFunService(FunService funService) {
		this.funService = funService;
	}
	
	public void setFunDataReadWriteBeckhoffService(FunDataReadWriteBeckhoffService funDataReadWriteBeckhoffService) {
		this.funDataReadWriteBeckhoffService = funDataReadWriteBeckhoffService;
	}
	
	public void setFun(Fun fun) {
		this.fun = fun;
	}
	
	public void readData() {
		
		try {
			String sql1 = "DELETE FROM mainRecodeTemp WHERE FUN_ID=? ";
			funService.executeSQL(sql1, fun.getId());
		
			Map<Variable, Object> data = funDataReadWriteBeckhoffService.readDataAll(fun.getAddress());
			
			String sqlInsert= "INSERT INTO mainRecodeTemp (";
			String sqlInsert1 = " VALUES(";
			
			for(Variable var: data.keySet() ){
				sqlInsert  = sqlInsert  + var.getDbName() + ",";
				sqlInsert1 = sqlInsert1 + data.get(var) + ",";
			}
			
			sqlInsert  = sqlInsert.substring(0, sqlInsert.length()-1) + ")";
			sqlInsert1 = sqlInsert1.substring(0, sqlInsert1.length()-1) + ")";
			sqlInsert  = sqlInsert + sqlInsert1;
			
			funService.executeSQL(sqlInsert);
			
		} catch (AdsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		System.out.println("定时任务:"+fun.getName());
		fun.setThreadSta(0);
	}

	@Override
	public void run() {
		this.readData();	
	}
}
