package com.sharpower.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.quartzs.ReportDayQuartz;
import com.sharpower.service.ReportDayRecodeService;

public class AjaxReportDayRecodeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private ReportDayRecodeService reportDayRecodeService;
	private List<Map<String, Object>> reportDayRecodesMap = new ArrayList<>();
	private Date date;
	
	private String message;
	
	private int page;
	private int rows;
	
	private ReportDayQuartz reportDayQuartz;
	
	public void setReportDayRecodeService(ReportDayRecodeService reportDayRecodeService) {
		this.reportDayRecodeService = reportDayRecodeService;
	}
		
	public List<Map<String, Object>> getReportDayRecodesMap() {
		return reportDayRecodesMap;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void setReportDayQuartz(ReportDayQuartz reportDayQuartz) {
		this.reportDayQuartz = reportDayQuartz;
	}
	
	public String findMainRecode(){
	
		String hql = "SELECT new map(rdr.date as date, "
				+ 					"rdr.fun.name as name, "
				+ 					"rdr.maxEngergy as engergy, "
				+ 					"rdr.averageWindSpeed as averageWindSpeed, "
				+                   "rdr.averagePower as averagePower, "
				+ 					"rdr.averageReactivePower as averageReactivePower, "
				+ 					"rdr.maxSpeed as maxSpeed, "
				+ 					"rdr.maxPower as maxPower, "
				+ 					"rdr.availabilityRatio as availabilityRatio) "
				+ 					"FROM ReportDayRecode rdr "
				+ 					"WHERE rdr.date=?";
			
		try {
			reportDayRecodesMap = reportDayRecodeService.executeHQLQuery(hql, date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String findTimeRecode(){
		String hql = "SELECT new map(rdr.date as date, "
				+ 					"rdr.fun.name as name, "
				+ 					"rdr.nacelleOutdoorTemperature as nacelleOutdoorTemperature, "
				+ 					"rdr.dataTimeEnergy as dataTimeEnergy, "
				+ 					"rdr.dataTimeService as dataTimeService, "
				+ 					"rdr.dataTimeAllError as dataTimeAllError, "
				+ 					"rdr.dataTimeNormal as dataTimeNormal, "
				+ 					"rdr.dataTimeTotal as dataTimeTotal) "
				+ 					"FROM ReportDayRecode rdr "
				+ 					"WHERE rdr.date=?";
		
		try {
			reportDayRecodesMap = reportDayRecodeService.executeHQLQuery(hql, date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String statistic(){
		Calendar calendar = Calendar.getInstance();		
		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		Date minDate = calendar.getTime();
		
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		
		Date maxDate = calendar.getTime();
		
		reportDayQuartz.statistic(minDate, maxDate);
		message = "手动统计成功！请重新执行查询操作。";
		return SUCCESS;
	}
	
}
