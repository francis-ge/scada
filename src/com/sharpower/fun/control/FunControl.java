package com.sharpower.fun.control;

import com.sharpower.scada.exception.PlcException;

public interface FunControl {
	public void run(String addr) throws PlcException;
	public void stop(String addr) throws PlcException;
	public void service(String addr) throws PlcException;
	public void reset(String addr) throws PlcException;
	public void yawLeft(String addr) throws PlcException;
	public void yawRight(String addr) throws PlcException;
	public void powerLimit(String addr, float val) throws PlcException;
	public void powerLimitCancel(String addr) throws PlcException;
	
}
