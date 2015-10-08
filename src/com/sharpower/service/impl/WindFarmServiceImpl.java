package com.sharpower.service.impl;

import java.util.Set;

import com.sharpower.entity.Fun;
import com.sharpower.entity.WindFarm;
import com.sharpower.service.WindFarmService;

public class WindFarmServiceImpl extends BaseServiceImpl<WindFarm> implements WindFarmService{
	
	public Set<Fun> getWindFarmFuns(int windFarmId){
		return getEntity(windFarmId).getFuns();
	}

}