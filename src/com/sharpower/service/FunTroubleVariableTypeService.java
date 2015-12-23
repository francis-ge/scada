package com.sharpower.service;

import java.util.List;

import com.sharpower.entity.TroubleType;

public interface FunTroubleVariableTypeService extends BaseService<TroubleType> {

	public List<TroubleType> findAllEntitiesLeftJoinFetch();
}
