package com.sharpower.beckhoff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharpower.entity.Fun;
import com.sharpower.entity.PlcType;
import com.sharpower.entity.Variable;
import com.sharpower.entity.VariableType;
import com.sharpower.scada.exception.AdsException;
import com.sharpower.service.PlcDataReader;
import com.sharpower.service.PlcTypeService;
import com.sharpower.service.VariableService;
import com.sharpower.service.VariableTypeService;

public class FunDataReadWriteBeckhoffService implements PlcDataReader{
	private VariableTypeService variableTypeService;
	private VariableService variableService;
	private PlcTypeService plcTypeService;
	private Map<String, List<FunDataReadWriteBeckhoff>> funDataReaders=new HashMap<>();
	
	public VariableTypeService getVariableTypeService() {
		return variableTypeService;
	
	}
	public void setVariableTypeService(VariableTypeService variableTypeService) {
		this.variableTypeService = variableTypeService;
	}
	public void setPlcTypeService(PlcTypeService plcTypeService) {
		this.plcTypeService = plcTypeService;
		
	}
	public VariableService getVariableService() {
		return variableService;
	}
	public void setVariableService(VariableService variableService) {
		this.variableService = variableService;
		
	}
	
	public Map<Variable,Object> readDataAll(Fun fun) throws AdsException {
		if (funDataReaders.isEmpty()) {
			
			List<VariableType> variableTypes = variableTypeService.findEntityByHQL("FROM VariableType");
			List<PlcType> plcTypes = plcTypeService.findEntityByHQL("FROM PlcType p WHERE p.plcCommType.name='beckhoff'");
			
			for (PlcType plcType : plcTypes){
				List<FunDataReadWriteBeckhoff> funDataReader = new ArrayList<>();
				
				for (VariableType variableType : variableTypes) {
					List<Variable> variables = variableService.findEntityByHQL("FROM Variable v where v.plcType.name=? and v.type.plcType=?", plcType.getName(), variableType.getPlcType());
					if (variables==null){
						continue;
					}
					variableType.setVals(variables);
					funDataReader.add(new FunDataReadWriteBeckhoff(variableType));
				}
				
				funDataReaders.put(plcType.getName(), funDataReader);
			}
		}
		
		Map<Variable, Object> datas = new HashMap<>();
		
		List<FunDataReadWriteBeckhoff> funDataReader= funDataReaders.get(fun.getPlcType().getName());
		
		for (FunDataReadWriteBeckhoff funData : funDataReader) {
			
			datas.putAll(funData.readData(fun.getAddress()));
		}
	
		return datas;
	}

}
