package com.sharpower.entity;

import java.util.Date;

public class ReportDayRecode {
	private Integer id;
	private Date date;
	private Fun fun;
	private Float maxEngergy=null;
	private Float averageWindSpeed=null;
	private Float averagePower=null;
	private Float averageReactivePower=null;
	private Float maxSpeed=null;
	private Float maxPower=null;
	private Float availabilityRatio=null;
	private Float nacelleOutdoorTemperature=null;
	private Integer dataTimeEnergy=null;
	private Integer dataTimeService=null;
	private Integer dataTimeAllError=null;
	private Integer dataTimeNormal=null;
	private Integer dataTimeTotal=null;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Fun getFun() {
		return fun;
	}
	public void setFun(Fun fun) {
		this.fun = fun;
	}
	public Float getMaxEngergy() {
		return maxEngergy;
	}
	public void setMaxEngergy(Float engergy) {
		this.maxEngergy = engergy;
	}
	public Float getAverageWindSpeed() {
		return averageWindSpeed;
	}
	public void setAverageWindSpeed(Float averageWindSpeed) {
		this.averageWindSpeed = averageWindSpeed;
	}
	public Float getAveragePower() {
		return averagePower;
	}
	public void setAveragePower(Float averagePower) {
		this.averagePower = averagePower;
	}
	public Float getAverageReactivePower() {
		return averageReactivePower;
	}
	public void setAverageReactivePower(Float averageReactivePower) {
		this.averageReactivePower = averageReactivePower;
	}
	public Float getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(Float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public Float getMaxPower() {
		return maxPower;
	}
	public void setMaxPower(Float maxPower) {
		this.maxPower = maxPower;
	}
	public Float getAvailabilityRatio() {
		return availabilityRatio;
	}
	public void setAvailabilityRatio(Float availabilityRatio) {
		this.availabilityRatio = availabilityRatio;
	}
	public Float getNacelleOutdoorTemperature() {
		return nacelleOutdoorTemperature;
	}
	public void setNacelleOutdoorTemperature(Float nacelleOutdoorTemperature) {
		this.nacelleOutdoorTemperature = nacelleOutdoorTemperature;
	}
	public Integer getDataTimeEnergy() {
		return dataTimeEnergy;
	}
	public void setDataTimeEnergy(Integer dataTimeEnergy) {
		this.dataTimeEnergy = dataTimeEnergy;
	}
	public Integer getDataTimeService() {
		return dataTimeService;
	}
	public void setDataTimeService(Integer dataTimeService) {
		this.dataTimeService = dataTimeService;
	}
	public Integer getDataTimeAllError() {
		return dataTimeAllError;
	}
	public void setDataTimeAllError(Integer dataTimeAllError) {
		this.dataTimeAllError = dataTimeAllError;
	}
	public Integer getDataTimeNormal() {
		return dataTimeNormal;
	}
	public void setDataTimeNormal(Integer dataTimeNormal) {
		this.dataTimeNormal = dataTimeNormal;
	}
	public Integer getDataTimeTotal() {
		return dataTimeTotal;
	}
	public void setDataTimeTotal(Integer dataTimeTotal) {
		this.dataTimeTotal = dataTimeTotal;
	}
	
}
