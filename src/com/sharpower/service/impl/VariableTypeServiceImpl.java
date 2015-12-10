package com.sharpower.service.impl;

import java.util.List;

import com.sharpower.entity.VariableType;
import com.sharpower.service.VariableTypeService;

public class VariableTypeServiceImpl extends BaseServiceImpl<VariableType> implements VariableTypeService {
	
	public List<VariableType> findAllEntitiesLeftJoinFetch(){
		String hql = "FROM VariableType v LEFT JOIN FETCH v.vals"  ;
		return this.findEntityByHQL(hql);
	}
}
