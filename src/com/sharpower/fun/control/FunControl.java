package com.sharpower.fun.control;

import com.sharpower.entity.Fun;
import com.sharpower.scada.exception.PlcException;

public interface FunControl {
	public void run(Fun fun) throws PlcException;
	public void stop(Fun fun) throws PlcException;
	public void service(Fun fun) throws PlcException;
	public void reset(Fun fun) throws PlcException;
	public void yawLeft(Fun fun) throws PlcException;
	public void yawRight(Fun fun) throws PlcException;
	public void powerLimit(Fun fun, float val) throws PlcException;
	public void powerLimitCancel(Fun fun) throws PlcException;
	
}
