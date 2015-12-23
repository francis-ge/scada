package com.sharpower.service.impl;

import java.util.List;

import com.sharpower.entity.TroubleType;
import com.sharpower.service.FunTroubleVariableTypeService;

public class FunTroubleVariableTypeServiceImpl extends BaseServiceImpl<TroubleType> implements FunTroubleVariableTypeService {
	
	public List<TroubleType> findAllEntitiesLeftJoinFetch(){
		String hql = "FROM TroubleType v LEFT JOIN FETCH v.funTroubleVariables"  ;
		return this.findEntityByHQL(hql);
	}
}