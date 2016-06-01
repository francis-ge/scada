package com.sharpower.action;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	private int page;
	private int rows;
	
	private Map<String, Object> result = new HashMap<>();
	
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
	
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public Map<String, Object> getResult() {
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String troubleStatisticsForOne(){
		try {
			String hql = "SELECT new map(ftr.fun.name as name, ftr.funTroubleVariable.code as code, "
					+ "ftr.funTroubleVariable.description as description, count(ftr) as count, max(ftr.startTime) as maxTime) "
					+ "FROM FunTroubleRecode ftr "
					+ "WHERE ftr.fun.id=? "
					+ "AND ftr.startTime>? AND ftr.startTime<? AND ftr.funTroubleVariable.type.id=? "
					+ "GROUP BY ftr.funTroubleVariable.id";
			statisticsList = funTroubleRecodeService.executeHQLQueryPaging( hql, (page-1)*rows, rows, 
					funTroubleRecode.getFun().getId(), 
					funTroubleRecode.getStartTime(), 
					funTroubleRecode.getEndTime(), 
					funTroubleRecode.getFunTroubleVariable().getType().getId() );
			
			String totalHql = "SELECT count(*) "
					+ "FROM FunTroubleRecode ftr "
					+ "WHERE ftr.fun.id=? "
					+ "AND ftr.startTime>? AND ftr.startTime<? AND ftr.funTroubleVariable.type.id=? "
					+ "GROUP BY ftr.funTroubleVariable.id"; 
			
			int total = funTroubleRecodeService.executeHQLQuery( totalHql, 
					funTroubleRecode.getFun().getId(), 
					funTroubleRecode.getStartTime(), 
					funTroubleRecode.getEndTime(), 
					funTroubleRecode.getFunTroubleVariable().getType().getId()).size();
			
			result.put("total", total);
			result.put("rows", statisticsList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String troubleStatistics(){
		try {
			String hql = "SELECT new map(ftr.funTroubleVariable.code as code, ftr.funTroubleVariable.description as description, "
					+ "count(ftr) as count, max(ftr.startTime) as maxTime) "
					+ "FROM FunTroubleRecode ftr "
					+ "WHERE ftr.startTime>? AND ftr.startTime<? AND ftr.funTroubleVariable.type.id=? "
					+ "GROUP BY ftr.funTroubleVariable";
			statisticsList = funTroubleRecodeService.executeHQLQueryPaging(hql, (page-1)*rows, rows, 
					funTroubleRecode.getStartTime(), 
					funTroubleRecode.getEndTime(), 
					funTroubleRecode.getFunTroubleVariable().getType().getId() );
			
			String totalHql = "SELECT count(*) "
					+ "FROM FunTroubleRecode ftr "
					+ "WHERE ftr.startTime>? AND ftr.startTime<? AND ftr.funTroubleVariable.type.id=? "
					+ "GROUP BY ftr.funTroubleVariable";
			
			int total = funTroubleRecodeService.executeHQLQuery(totalHql, 
					funTroubleRecode.getStartTime(), 
					funTroubleRecode.getEndTime(), 
					funTroubleRecode.getFunTroubleVariable().getType().getId()).size();
			
			result.put("total", total);
			result.put("rows", statisticsList);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String troubleStatisticsForOneQuery(){
		
		String totalHql;
		Long total;
		
		try {
			if (funTroubleRecode.getFunTroubleVariable().getCode().equals("all")) {
				String hql = "SELECT new map(ftr.fun.name as name, ftr.funTroubleVariable.code as code, ftr.startTime as startTime, "
						+ "ftr.endTime as endTime, ftr.funTroubleVariable.description as description) "
						+ "FROM FunTroubleRecode ftr "
						+ "WHERE ftr.fun.id=? "
						+ "AND ftr.startTime>? "
						+ "AND ftr.startTime<? "
						+ "AND ftr.funTroubleVariable.type.id=? ";
				
				statisticsList = funTroubleRecodeService.executeHQLQueryPaging( hql, (page-1)*rows, rows,
						funTroubleRecode.getFun().getId(), 
						funTroubleRecode.getStartTime(), 
						funTroubleRecode.getEndTime(), 
						funTroubleRecode.getFunTroubleVariable().getType().getId());
				
				totalHql = "SELECT count(*) From FunTroubleRecode ftr "
						+ "WHERE ftr.fun.id=? "
						+ "AND ftr.startTime>? "
						+ "AND ftr.startTime<? "
						+ "AND ftr.funTroubleVariable.type.id=? ";
				
				total = (Long) funTroubleRecodeService.uniqueResult(totalHql,
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
				
				statisticsList = funTroubleRecodeService.executeHQLQueryPaging( hql, (page-1)*rows, rows,
						funTroubleRecode.getFun().getId(), 
						funTroubleRecode.getStartTime(), 
						funTroubleRecode.getEndTime(), 
						funTroubleRecode.getFunTroubleVariable().getType().getId(), 
						funTroubleRecode.getFunTroubleVariable().getCode() );
				
				totalHql = "SELECT count(*) From FunTroubleRecode ftr "
						+ "WHERE ftr.fun.id=? "
						+ "AND ftr.startTime>? "
						+ "AND ftr.startTime<? "
						+ "AND ftr.funTroubleVariable.type.id=? "
						+ "AND ftr.funTroubleVariable.code=? ";
				
				total = (Long) funTroubleRecodeService.uniqueResult(totalHql,
							funTroubleRecode.getFun().getId(), 
							funTroubleRecode.getStartTime(), 
							funTroubleRecode.getEndTime(), 
							funTroubleRecode.getFunTroubleVariable().getType().getId(), 
							funTroubleRecode.getFunTroubleVariable().getCode());
				
			}
			
			result.put("total", total);
			result.put("rows", statisticsList);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String currentError(){
		String hql ="FROM FunTroubleRecode ftr WHERE ftr.fun.id=? AND ftr.funTroubleVariable.type.id=1 AND ftr.endTime is null";
		funTroubleRecodes = funTroubleRecodeService.findEntityByHQL(hql, funId);
		
		return SUCCESS;
	}
	
	public String currentWorning(){
		String hql ="FROM FunTroubleRecode ftr WHERE ftr.fun.id=? AND ftr.funTroubleVariable.type.id=2 AND ftr.endTime is null";
		funTroubleRecodes = funTroubleRecodeService.findEntityByHQL(hql, funId);
		
		return SUCCESS;
	}
	

}
