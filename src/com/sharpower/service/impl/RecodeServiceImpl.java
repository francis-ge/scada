package com.sharpower.service.impl;

import java.util.List;
import java.util.Map;

import com.sharpower.dao.impl.RecodeDaoImpl;
import com.sharpower.service.RecodeService;

public class RecodeServiceImpl implements RecodeService{
	private RecodeDaoImpl recodeDao;
	
	public RecodeDaoImpl getRecodeDao() {
		return recodeDao;
	}
	
	public void setRecodeDao(RecodeDaoImpl recodeDao) {
		this.recodeDao = recodeDao;
	}
	
	@Override
	public void save(String mapName, Map<String, Object> map) {
		recodeDao.saveByMap(mapName, map);
	}

	@Override
	public List<Map<String, Object>> findMapByHql(String hql, Object ...objects) {
		
		return recodeDao.findEntityMapByHql(hql, objects);
	}

	@Override
	public void saveOrUpdate(String mapName, Map<String, Object> map) {
		recodeDao.saveOrUpdate(mapName, map);
	}

	@Override
	public Map<String, Object> get(String mapName, Integer id) {
		
		return recodeDao.get(mapName, id);
	}

}
