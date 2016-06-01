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
	public void save(Map<String, Object> map) {
		recodeDao.saveByMap(map);
	}

	@Override
	public List<Map<String, Object>> findMapByHql(String hql, Object ...objects) {
		
		return recodeDao.findEntityMapByHql(hql, objects);
	}

	@Override
	public void saveOrUpdate(Map<String, Object> map) {
		recodeDao.saveOrUpdate(map);
	}

	@Override
	public Map<String, Object> get(Integer id) {
		
		return recodeDao.get(id);
	}

	@Override
	public List<Map<String, Object>> findMapByHqlPaging(String hql, int startIndex, int rows, Object... objects) {
		
		return recodeDao.findEntityMapByHqlPaging(hql, startIndex, rows, objects);
	}


}
