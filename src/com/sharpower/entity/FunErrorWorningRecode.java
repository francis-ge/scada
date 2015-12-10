package com.sharpower.entity;

import java.util.Date;

public class FunErrorWorningRecode {
	private Long id;
	private Fun fun;
	private FunErrorWorning funErrorWorning;
	private Date startTime;
	private Date endTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Fun getFun() {
		return fun;
	}
	public void setFun(Fun fun) {
		this.fun = fun;
	}
	public FunErrorWorning getFunErrorWorning() {
		return funErrorWorning;
	}
	public void setFunErrorWorning(FunErrorWorning funErrorWorning) {
		this.funErrorWorning = funErrorWorning;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
