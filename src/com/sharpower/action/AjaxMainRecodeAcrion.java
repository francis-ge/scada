package com.sharpower.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
	private Date time;
	private String variables;
	
	private String result;
	private Map<String, Object> dataMap = new HashMap<>();
	private List<Map<String, Object>> dataList = new ArrayList<>();
	private List<Map<String, Object>> dataList1 = new ArrayList<>();
	
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
	
	public void setTime(Date time) {
		this.time = time;
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
	
	public List<Map<String, Object>> getDataList1() {
		return dataList1;
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
	
	public String reportHourForOne(){
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(time);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		Date beginTime = calendar.getTime();
		
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date endTime = calendar.getTime();
		
		String hql = "SELECT new map( "
				+ "hour(mr.dateTime) as time, "
				+ "mr.fun.id as funId, "
				+ "mr.fun.name as name, "
				+ "max(mr.___visu_grid_energy) as maxEngergy, "
				+ "avg(mr.___wind_speed) as averageWindSpeed, "
				+ "avg(mr.___visu_grid_power) as averagePower, "
				+ "avg(mr.___visu_grid_reactive_power) as averageReactivePower, "
				+ "max(mr.___wind_speed) as maxSpeed, "
				+ "max(mr.___visu_grid_power) as maxPower, "
				+ "avg(mr.___visu_nacelle_outdoor_temperature) as nacelleOutdoorTemperature) "
				+ "FROM MainRecode mr WHERE mr.fun.id=? AND mr.dateTime>? AND mr.dateTime<? "
				+ "GROUP by hour(mr.dateTime)";
		
		try {
			dataList = recodeService.findMapByHql(hql, funId, beginTime, endTime);
			result = "查询成功！";
		} catch (Exception e) {
			result = "查询失败！"+ e.getMessage();
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String funChangeOfState(){
		String hql = "SELECT new map((CASE mr.___main_loop_mode_number "
										+ "WHEN 0 THEN '机组转为【初始化】状态' "
										+ "WHEN 1 THEN '机组转为【停机】状态' "
										+ "WHEN 2 THEN '机组转为【待机】状态' "
										+ "WHEN 3 THEN '机组转为【启动】状态' "
										+ "WHEN 4 THEN '机组转为【运行】状态' "
										+ "WHEN 5 THEN '机组转为【并网】状态' "
										+ "WHEN 9 THEN '机组转为【维护】状态' "
										+ "WHEN 10 THEN '机组转为【通信故障】状态' "
										+ "ELSE CONCAT('机组转为【', mr.___main_loop_mode_number,'】状态') END) AS state,"
										+ "mr.fun.name as name, "
										+ "mr.dateTime as dateTime) "
										+ "FROM MainRecode mr WHERE mr.fun.id=? AND mr.dateTime>? AND mr.dateTime<?";
		
		try {
			dataList = recodeService.findMapByHql(hql, funId, beginTime, endTime);
			
			for (int n=0; n<dataList.size(); n++) {
				Map<String, Object> map = dataList.get(n);
				
				if (n==0) {
					dataList1.add(map);
				}
				
				if (n<dataList.size()-1) {
					Map<String, Object> map1 = (Map<String, Object>) dataList.get(n+1);
					
					if (map.get("state")!=null && map1.get("state")!=null) {
						if (map.get("state").equals(map1.get("state"))==false) {
							dataList1.add(map1);
						}
					}else if (map.get("state")!=null && map1.get("state")==null){
						dataList1.add(map1);
					}else if (map.get("state")==null && map1.get("state")!=null){
						dataList1.add(map1);
					}
				}
			}
			
			result = "查询成功！";
		} catch (Exception e) {
			result = "查询失败！"+ e.getMessage();
			e.printStackTrace();
		}
		
		return SUCCESS;
		
	}
	
	public String reportWindFarmHour(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		
		Date thisBeginTime;
		Date thisEndTime;
		
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		thisBeginTime = calendar.getTime();
		
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		
		thisEndTime = calendar.getTime();
		
		String hql = "SELECT new map("
						+ "hour(mr.dateTime) as hour, "
						+ "mr.fun.name as name, "
						+ "avg(mr.___wind_speed) as averageWindSpeed, "
						+ "avg(mr.___visu_grid_power) as averagePower, "
						+ "max(mr.___visu_grid_energy) as maxEngergy, "
						+ "avg(mr._converter_com__converter_motor_speed) as avgMotorSpeed, "
						+ "avg(mr._visu_generator_winding_temperature_u) as avgGeneratorTemp,"
						+ "avg(mr.___visu_nacelle_temperature) as avgNacelleTemp,"
						+ "avg(mr.___hydraulic_main_sys_pressure) as avgSysPressure ) FROM MainRecode mr WHERE mr.dateTime>? AND mr.dateTime<? "
						+ "GROUP BY mr.fun.id";
		
		try {
			dataList = recodeService.findMapByHql(hql, thisBeginTime, thisEndTime);
			result = "查询成功！";
		} catch (Exception e) {
			result = "查询失败！"+ e.getMessage();
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
}
