package com.sharpower.entity;

import java.util.Date;

public class BooleanData {
	Date date;
	boolean val;
	Variable variable;
	Fun fun;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isVal() {
		return val;
	}
	public void setVal(boolean val) {
		this.val = val;
	}
	public Variable getVariable() {
		return variable;
	}
	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	public Fun getFun() {
		return fun;
	}
	public void setFun(Fun fun) {
		this.fun = fun;
	}
	
}
