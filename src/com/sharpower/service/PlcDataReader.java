package com.sharpower.service;

import java.util.Map;

import com.sharpower.entity.Fun;
import com.sharpower.entity.Variable;
import com.sharpower.scada.exception.PlcException;

public interface PlcDataReader {
	public Map<Variable,Object> readDataAll(Fun fun) throws PlcException;
}
