package com.sharpower.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.service.ReportDayRecodeService;

public class AjaxReportDayRecodeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private ReportDayRecodeService reportDayRecodeService;
	private List<Map<String, Object>> reportDayRecodesMap = new ArrayList<>();
	private Date date;
	
	private int page;
	private int rows;
	
	public void setReportDayRecodeService(ReportDayRecodeService reportDayRecodeService) {
		this.reportDayRecodeService = reportDayRecodeService;
	}
		
	public List<Map<String, Object>> getReportDayRecodesMap() {
		return reportDayRecodesMap;
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
	
}
