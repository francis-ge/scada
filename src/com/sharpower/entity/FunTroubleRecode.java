package com.sharpower.entity;

import java.util.Date;

public class FunTroubleRecode {
	private Long id;
	private Date startTime;
	private Date endTime;
	private Fun fun;
	private FunTroubleVariable funTroubleVariable;
	
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
	public FunTroubleVariable getFunTroubleVariable() {
		return funTroubleVariable;
	}
	public void setFunTroubleVariable(FunTroubleVariable funTroubleVariable) {
		this.funTroubleVariable = funTroubleVariable;
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
	@Override
	public String toString() {
		return "FunTroubleRecode [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", fun=" + fun
				+ ", funTroubleVariable=" + funTroubleVariable + "]";
	}

	

	
}
