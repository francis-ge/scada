package com.sharpower.service;

import java.util.List;
import java.util.Map;

public interface RecodeService {
	public Map<String, Object> get(String mapName, Integer id);
	public void save(String mapName, Map<String, Object> map);
	public void saveOrUpdate(String mapName, Map<String, Object> map);
	public List<Map<String, Object>> findMapByHql(String hql, Object...objects);
}
