package com.sharpower.service;

import java.util.Map;

import com.sharpower.entity.Fun;
import com.sharpower.entity.FunTroubleVariable;
import com.sharpower.scada.exception.PlcException;

public interface FunTroubleVariableReader {
	public Map<FunTroubleVariable,Object> readDataAll(Fun fun) throws PlcException;
}
