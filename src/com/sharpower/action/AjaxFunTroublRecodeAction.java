package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.FunTroubleRecode;
import com.sharpower.service.FunTroubleRecodeService;

public class AjaxFunTroublRecodeAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private FunTroubleRecodeService funTroubleRecodeService;
	private Integer funId;
	private List<FunTroubleRecode> funTroubleRecodes = new ArrayList<>();
	private FunTroubleRecode funTroubleRecode;
	private List<Map<String, Object>> statisticsList = new ArrayList<>();
	
	public void setFunTroubleRecodeService(FunTroubleRecodeService funTroubleRecodeService) {
		this.funTroubleRecodeService = funTroubleRecodeService;
	}
	public void setFunId(Integer funId) {
		this.funId = funId;
	}
	
	public FunTroubleRecode getFunTroubleRecode() {
		return funTroubleRecode;
	}
	public void setFunTroubleRecode(FunTroubleRecode funTroubleRecode) {
		this.funTroubleRecode = funTroubleRecode;
	}
	public List<FunTroubleRecode> getFunTroubleRecodes() {
		return funTroubleRecodes;
	}
	
	public List<Map<String, Object>> getStatisticsList() {
		return statisticsList;
	}
	
	@SuppressWarnings("unchecked")
	public String getTroubleStatisticsForOne(){
		String hql = "SELECT new map(ftr.fun.name as name, ftr.funTroubleVariable.code as code, "
				+ "ftr.funTroubleVariable.description as description, count(ftr) as count, max(ftr.startTime) as maxTime) "
				+ "FROM FunTroubleRecode ftr "
				+ "WHERE ftr.fun.id=? "
				+ "AND ftr.startTime>? AND ftr.startTime<? AND ftr.funTroubleVariable.type.id=?"
				+ "GROUP BY ftr.funTroubleVariable";
		statisticsList = funTroubleRecodeService.executeHQLQuery( hql, funTroubleRecode.getFun().getId(), funTroubleRecode.getStartTime(), funTroubleRecode.getEndTime(), funTroubleRecode.getFunTroubleVariable().getType().getId() );
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String getTroubleStatistics(){
		String hql = "SELECT new map(ftr.funTroubleVariable.code as code, ftr.funTroubleVariable.description as description, "
				+ "count(ftr) as count, max(ftr.startTime) as maxTime) "
				+ "FROM FunTroubleRecode ftr "
				+ "WHERE ftr.startTime>? AND ftr.startTime<? AND ftr.funTroubleVariable.type.id=?"
				+ "GROUP BY ftr.funTroubleVariable";
		statisticsList = funTroubleRecodeService.executeHQLQuery(hql, funTroubleRecode.getStartTime(), funTroubleRecode.getEndTime(), funTroubleRecode.getFunTroubleVariable().getType().getId() );
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String getTroubleStatisticsForOneQuery(){
		
		if (funTroubleRecode.getFunTroubleVariable().getCode().equals("all")) {
			String hql = "SELECT new map(ftr.fun.name as name, ftr.funTroubleVariable.code as code, ftr.startTime as startTime, "
					+ "ftr.endTime as endTime, ftr.funTroubleVariable.description as description) "
					+ "FROM FunTroubleRecode ftr "
					+ "WHERE ftr.fun.id=? "
					+ "AND ftr.startTime>? "
					+ "AND ftr.startTime<? "
					+ "AND ftr.funTroubleVariable.type.id=? ";
			
			statisticsList = funTroubleRecodeService.executeHQLQuery( hql, 
					funTroubleRecode.getFun().getId(), 
					funTroubleRecode.getStartTime(), 
					funTroubleRecode.getEndTime(), 
					funTroubleRecode.getFunTroubleVariable().getType().getId());
		}else{
			String hql = "SELECT new map(ftr.fun.name as name, ftr.funTroubleVariable.code as code, ftr.startTime as startTime, "
					+ "ftr.endTime as endTime, ftr.funTroubleVariable.description as description) "
					+ "FROM FunTroubleRecode ftr "
					+ "WHERE ftr.fun.id=? "
					+ "AND ftr.startTime>? "
					+ "AND ftr.startTime<? "
					+ "AND ftr.funTroubleVariable.type.id=? "
					+ "AND ftr.funTroubleVariable.code=? ";
			
			statisticsList = funTroubleRecodeService.executeHQLQuery( hql, 
					funTroubleRecode.getFun().getId(), 
					funTroubleRecode.getStartTime(), 
					funTroubleRecode.getEndTime(), 
					funTroubleRecode.getFunTroubleVariable().getType().getId(), 
					funTroubleRecode.getFunTroubleVariable().getCode() );
			
		}
		
		
		return SUCCESS;
	}
	
	
	public String getCurrentError(){
		String hql ="FROM FunTroubleRecode ftr WHERE ftr.fun.id=? AND ftr.funTroubleVariable.type.id=1 AND ftr.endTime is null";
		funTroubleRecodes = funTroubleRecodeService.findEntityByHQL(hql, funId);
		
		return SUCCESS;
	}
	
	public String getCurrentWorning(){
		String hql ="FROM FunTroubleRecode ftr WHERE ftr.fun.id=? AND ftr.funTroubleVariable.type.id=2 AND ftr.endTime is null";
		funTroubleRecodes = funTroubleRecodeService.findEntityByHQL(hql, funId);
		
		return SUCCESS;
	}
	

}
