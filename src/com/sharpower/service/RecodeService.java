package com.sharpower.service;

import java.util.List;
import java.util.Map;

public interface RecodeService {
	public void save(Map<String, Object> map);
	public List<Map<String, Object>> findMapByHql(String hql, Object...objects);
}
