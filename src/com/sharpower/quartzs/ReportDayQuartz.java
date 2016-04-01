package com.sharpower.quartzs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sharpower.entity.Fun;
import com.sharpower.entity.ReportDayRecode;
import com.sharpower.service.RecodeService;
import com.sharpower.service.ReportDayRecodeService;

public class ReportDayQuartz {
	private ReportDayRecodeService reportDayRecodeService;
	private RecodeService recodeService;
	
	public ReportDayRecodeService getReportDayRecodeService() {
		return reportDayRecodeService;
	}

	public void setReportDayRecodeService(ReportDayRecodeService reportDayRecodeService) {
		this.reportDayRecodeService = reportDayRecodeService;
	}

	public RecodeService getRecodeService() {
		return recodeService;
	}

	public void setRecodeService(RecodeService recodeService) {
		this.recodeService = recodeService;
	}

	public void statistic(){
		List<Map<String, Object>> list = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(new Date());

		calendar.add(Calendar.DAY_OF_MONTH, -1);
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
		
		String hql = "SELECT new map( "
				+ "mr.dateTime as date, "
				+ "mr.fun.id as funId, "
				+ "max(mr.___visu_grid_energy) as maxEngergy, "
				+ "avg(mr.___wind_speed) as averageWindSpeed, "
				+ "avg(mr.___visu_grid_power) as averagePower, "
				+ "avg(mr.___visu_grid_reactive_power) as averageReactivePower, "
				+ "max(mr.___wind_speed) as maxSpeed, "
				+ "max(mr.___visu_grid_power) as maxPower, "
				+ "avg(mr.___availability_ratio) as availabilityRatio, "
				+ "avg(mr.___visu_nacelle_outdoor_temperature) as nacelleOutdoorTemperature, "
				+ "(max(mr.___data_time_energy) - min(mr.___data_time_energy)) as dataTimeEnergy, "
				+ "(max(mr.___data_time_service) - min(mr.___data_time_service)) as dataTimeService, "
				+ "(max(mr.___data_time_all_error) - min(mr.___data_time_all_error)) as dataTimeAllError, "
				+ "(max(mr.___data_time_normal) - min(mr.___data_time_normal)) as dataTimeNormal, "
				+ "(max(mr.___data_time_total) - min(mr.___data_time_total)) as dataTimeTotal ) "
				+ "FROM MainRecode mr WHERE mr.dateTime>? AND mr.dateTime<? "
				+ "GROUP BY mr.fun.id";
		
		try {
			list = recodeService.findMapByHql(hql, minDate, maxDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Map<String, Object> map : list) {
			ReportDayRecode reportDayRecode = new ReportDayRecode();
			
			reportDayRecode.setId(null);
			
			reportDayRecode.setDate(minDate);

			Fun fun = new Fun();
			fun.setId((Integer) map.get("funId"));
			reportDayRecode.setFun(fun);
			
			if (map.get("maxEngergy")!=null) {
				reportDayRecode.setMaxEngergy((Float)map.get("maxEngergy"));
			}
			
			if (map.get("averageWindSpeed")!=null) {
				reportDayRecode.setAverageWindSpeed(((Double)map.get("averageWindSpeed")).floatValue());
			}
			
			if (map.get("averagePower")!=null) {
				reportDayRecode.setAveragePower(((Double)map.get("averagePower")).floatValue());
			}
			
			if (map.get("averageReactivePower")!=null) {
				reportDayRecode.setAverageReactivePower(((Double)map.get("averageReactivePower")).floatValue());
			}
			
			if (map.get("maxSpeed")!=null) {
				reportDayRecode.setMaxSpeed((Float)map.get("maxSpeed"));
			}
			
			if (map.get("maxPower")!=null) {
				reportDayRecode.setMaxPower((Float)map.get("maxPower"));
			}
			
			if (map.get("availabilityRatio")!=null) {
				reportDayRecode.setAvailabilityRatio(((Double)map.get("availabilityRatio")).floatValue());
			}
			
			if (map.get("nacelleOutdoorTemperature")!=null) {
				reportDayRecode.setNacelleOutdoorTemperature(((Double)map.get("nacelleOutdoorTemperature")).floatValue());
			}
			
			if (map.get("dataTimeEnergy")!=null) {
				reportDayRecode.setDataTimeEnergy(((Short)map.get("dataTimeEnergy")).intValue());
			}
			
			if (map.get("dataTimeService")!=null) {
				reportDayRecode.setDataTimeService(((Short)map.get("dataTimeService")).intValue());
			}
			
			if (map.get("dataTimeAllError")!=null) {
				reportDayRecode.setDataTimeAllError(((Short)map.get("dataTimeAllError")).intValue());
			}
			
			if (map.get("dataTimeNormal")!=null) {
				reportDayRecode.setDataTimeNormal(((Short)map.get("dataTimeNormal")).intValue());
			}
			
			if (map.get("dataTimeTotal")!=null) {
				reportDayRecode.setDataTimeTotal(((Short)map.get("dataTimeTotal")).intValue());
			}
			
			reportDayRecodeService.saveEntity(reportDayRecode);
			
		}
		
	}

}
