package com.sharpower.service;

import java.util.List;

import com.sharpower.entity.TroubleType;
import com.sharpower.entity.VariableType;

public interface VariableTypeService extends BaseService<VariableType> {

	public List<VariableType> findAllEntitiesLeftJoinFetch();
}
