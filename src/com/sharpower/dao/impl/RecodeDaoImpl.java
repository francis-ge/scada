package com.sharpower.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.event.spi.SaveOrUpdateEvent;


public class RecodeDaoImpl{
	SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> get(String mapName, Integer id){
		 return (Map<String, Object>) sessionFactory.getCurrentSession().get(mapName, id);
	}
	
	public void saveByMap(String mapName, Map<String, Object> map){
		sessionFactory.getCurrentSession().save(mapName, map);
	}
	
	public void saveOrUpdate(String mapName, Map<String, Object> map){
		sessionFactory.getCurrentSession().saveOrUpdate(mapName, map);;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findEntityMapByHql(String hql, Object ... objs){		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		for (int i=0; i<objs.length;i++) {
			query.setParameter(i, objs[i]);
		}
		
		return query.list();
		
	}
}