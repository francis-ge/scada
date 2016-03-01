package com.sharpower.beckhoff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.sharpower.entity.Variable;
import com.sharpower.entity.VariableType;
import com.sharpower.scada.exception.AdsException;
import com.sharpower.service.VariableTypeService;

public class FunDataReadWriteBeckhoffService {
	private VariableTypeService variableTypeService;
	private List<FunDataReadWriteBeckhoff> funDatas = new ArrayList<>();
	
	public VariableTypeService getVariableTypeService() {
		return variableTypeService;
	}
	public void setVariableTypeService(VariableTypeService variableTypeService) {
		this.variableTypeService = variableTypeService;
		List<VariableType> variableTypes = new ArrayList<>( new HashSet<VariableType>(variableTypeService.findAllEntitiesLeftJoinFetch()));
		
		for (VariableType variableType : variableTypes) {
			funDatas.add(new FunDataReadWriteBeckhoff(variableType));
		}
	}
	
	public Map<Variable,Object> readDataAll(String sFunAddress) throws AdsException {
		Map<Variable, Object> datas = new HashMap<>();
		
		for (FunDataReadWriteBeckhoff funData : funDatas) {
			
			datas.putAll(funData.readData(sFunAddress));
		}
	
		return datas;
	}

}
