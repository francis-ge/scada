package com.sharpower.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Fun;
import com.sharpower.fun.control.FunControl;
import com.sharpower.scada.exception.PlcException;

public class AjaxFunControlAction extends ActionSupport{
	private static final long serialVersionUID = 1L;

	private Fun fun;
	private float limitVal;
	private FunControl funControl;
	private String result;
	
	public Fun getFun() {
		return fun;
	}
	public void setFun(Fun fun) {
		this.fun = fun;
	}
	public float getLimitVal() {
		return limitVal;
	}
	public void setLimitVal(float limitVal) {
		this.limitVal = limitVal;
	}
	
	public FunControl getFunControl() {
		return funControl;
	}
	public void setFunControl(FunControl funControl) {
		this.funControl = funControl;
	}
	
	public String getResult() {
		return result;
	}
	
	public String run(){
		try {
			funControl.run(fun);
			result=fun.getName()+"  启动命令已下达。符合起动条件时风机自动启动。";
			return SUCCESS;
		} catch (PlcException e) {
			e.printStackTrace();
			result=fun.getName()+"  启动命令未下达:,"+e.getMessage();
			return SUCCESS;
		}

	}
	
	public String stop(){
		try {
			funControl.stop(fun);
			result=fun.getName()+"  停机命令已执行。";
			return SUCCESS;
		} catch (PlcException e) {
			e.printStackTrace();
			result=fun.getName()+"  停机命令未执行,"+e.getMessage();
			return SUCCESS;
		}
	}
	
	public String reset(){
		try {
			funControl.reset(fun);
			result=fun.getName()+"  复位命令已执行。";
			return SUCCESS;
		} catch (PlcException e) {
			e.printStackTrace();
			result=fun.getName()+"  复位命令未执行,"+e.getMessage();
			return SUCCESS;
		}
	}
	
	public String service(){
		try {
			funControl.service(fun);
			result=fun.getName()+"  维护模式已执行。";
			return SUCCESS;
		} catch (PlcException e) {
			e.printStackTrace();
			result=fun.getName()+"  维护模式未执行,"+e.getMessage();
			return SUCCESS;
		}
	}
	
	public String powerLimit(){
		try {
			funControl.powerLimit(fun, limitVal);
			result=fun.getName()+"  限功率命令已执行。";
			return SUCCESS;
		} catch (PlcException e) {
			e.printStackTrace();
			result=fun.getName()+"  限功率命令未执行,"+e.getMessage();
			return SUCCESS;
		}
	}
	
	public String powerLimitCancel(){
		try {
			funControl.powerLimitCancel(fun);
			result=fun.getName()+"  取消功率限制已执行。";
			return SUCCESS;
		} catch (PlcException e) {
			e.printStackTrace();
			result=fun.getName()+"  取消功率限制未执行,"+e.getMessage();
			return SUCCESS;
		}
	}
}
