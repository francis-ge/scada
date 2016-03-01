package com.sharpower.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Fun;
import com.sharpower.fun.control.FunControl;
import com.sharpower.scada.exception.PlcException;

public class AjaxFunControlAction extends ActionSupport{
	private static final long serialVersionUID = 1L;

	private String addr;
	private boolean bool;
	private float nfloat;
	private FunControl funControl;
	private String result;
	
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	public boolean isBool() {
		return bool;
	}
	public void setBool(boolean bool) {
		this.bool = bool;
	}
	public float getNfloat() {
		return nfloat;
	}
	public void setNfloat(float nfloat) {
		this.nfloat = nfloat;
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
			funControl.run(addr);
			result="启动命令已执行。";
			return SUCCESS;
		} catch (PlcException e) {
			e.printStackTrace();
			result="启动命令未执行:,"+e.getMessage();
			return SUCCESS;
		}

	}
	
	public String stop(){
		try {
			funControl.stop(addr);
			result="停机命令已执行。";
			return SUCCESS;
		} catch (PlcException e) {
			e.printStackTrace();
			result="停机命令未执行,"+e.getMessage();
			return SUCCESS;
		}
	}
	
	public String reset(){
		try {
			funControl.reset(addr);
			result="复位命令已执行。";
			return SUCCESS;
		} catch (PlcException e) {
			e.printStackTrace();
			result="复位命令未执行,"+e.getMessage();
			return SUCCESS;
		}
	}
	
	public String service(){
		try {
			funControl.service(addr);
			result="维护模式已执行。";
			return SUCCESS;
		} catch (PlcException e) {
			e.printStackTrace();
			result="维护模式未执行,"+e.getMessage();
			return SUCCESS;
		}
	}
	
	public String powerLimit(){
		try {
			funControl.powerLimit(addr, nfloat);
			result="命令已执行。";
			return SUCCESS;
		} catch (PlcException e) {
			e.printStackTrace();
			result="命令未执行,"+e.getMessage();
			return SUCCESS;
		}
	}
	
	public String powerLimitCancel(){
		try {
			funControl.powerLimitCancel(addr);
			result="命令已执行。";
			return SUCCESS;
		} catch (PlcException e) {
			e.printStackTrace();
			result="命令未执行,"+e.getMessage();
			return SUCCESS;
		}
	}
}
