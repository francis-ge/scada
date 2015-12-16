package com.sharpower.service;

import java.util.List;
import java.util.Map;

public interface RecodeService {
	public Map<String, Object> get(Integer id);
	public void save(Map<String, Object> map);
	public void saveOrUpdate(Map<String, Object> map);
	public List<Map<String, Object>> findMapByHql(String hql, Object...objects);
}
