package com.sharpower.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.service.RecodeService;

public class AjaxMainRecodeAcrion extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private RecodeService recodeService;
	private Integer funId;
	private Date beginTime;
	private Date endTime;
	private String variables;
	
	private String result;
	private Map<String, Object> dataMap = new HashMap<>();
	private List<Map<String, Object>> dataList = new ArrayList<>();
	
	public void setRecodeService(RecodeService recodeService) {
		this.recodeService = recodeService;
	}
	public void setFunId(Integer funId) {
		this.funId = funId;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setVariables(String variables) {
		this.variables = variables;
	}
	
	public String getResult() {
		return result;
	}
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	
	public List<Map<String, Object>> getDataList() {
		return dataList;
	}
	
	public String powerCurve(){
		String hql ="SELECT new map((sum(CASE WHEN mr.___wind_speed>=0 AND mr.___wind_speed<1.5 THEN mr.___visu_grid_power else 0 end))/(sum(CASE WHEN mr.___wind_speed>=0 AND mr.___wind_speed<1.5 THEN 1 else 0 end)), ";
		
		for(int i = 0; i < 47; i++){
			hql = hql + " (sum( CASE WHEN mr.___wind_speed>=" + (1.5+i*0.5) + " AND mr.___wind_speed<" + (1.5+i*0.5+0.5) + " THEN mr.___visu_grid_power ELSE 0 END))/(sum( CASE WHEN mr.___wind_speed>=" + (1.5+i*0.5) + " AND mr.___wind_speed<" + (1.5+i*0.5+0.5) + " THEN 1 ELSE 0 END)),";
		}
		
		hql = hql + " (sum(CASE WHEN mr.___wind_speed>=25 THEN mr.___visu_grid_power ELSE 0 END))/(sum(CASE WHEN mr.___wind_speed>=25 THEN 1 ELSE 0 END))) " 
				 + " FROM MainRecode mr WHERE mr.fun.id=? AND mr.dateTime>? AND mr.dateTime<?";
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		try {
			list = recodeService.findMapByHql(hql, funId, beginTime, endTime);
			dataMap = list.get(0); 
			result = "查询成功！";
		} catch (Exception e) {
			result = "查询失败！"+ e.getMessage();
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String windFrequencyCurve(){
		String hql ="SELECT new map(sum(CASE WHEN mr.___wind_speed>=0 AND mr.___wind_speed<1.5 THEN 1 ELSE 0 END), ";

		for(int i = 0; i < 47; i++){
			hql = hql + " sum( CASE WHEN mr.___wind_speed>=" + (1.5+i*0.5) + " AND mr.___wind_speed<" + (1.5+i*0.5+0.5) + " THEN 1 ELSE 0 END),";
		}
		
		hql = hql + " sum(CASE WHEN mr.___wind_speed>=25 THEN 1 ELSE 0 END)) " 
				 + " FROM MainRecode mr WHERE mr.fun.id=? AND mr.dateTime>? AND mr.dateTime<?";
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		try {
			list = recodeService.findMapByHql(hql, funId, beginTime, endTime);
			dataMap = list.get(0); 
			result = "查询成功！";
		} catch (Exception e) {
			result = "查询失败！"+ e.getMessage();
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String mainRecodeForOne(){
		String[] varNames = variables.split(",");
		String hql = "SELECT new map(";
		
		if (varNames.length<=0) {
			result = "请选择要查询的数据！";
			return SUCCESS;
		}else{
			for (String string : varNames) {
				hql = hql + "mr." + string + " as " + string + ",";
			}
						
			hql = hql + "mr.dateTime as time) FROM MainRecode mr WHERE mr.fun.id=? AND mr.dateTime>? AND mr.dateTime<?";
			
			try {
				dataList = recodeService.findMapByHql(hql, funId, beginTime, endTime);
				result = "查询成功！";
			} catch (Exception e) {
				result = "查询失败！"+ e.getMessage();
				e.printStackTrace();
			}
		}
				
		return SUCCESS;
	}
}
