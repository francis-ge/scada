package com.sharpower.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.hibernate.Query;
import org.hibernate.SessionFactory;


public class RecodeDaoImpl{
	SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void saveByMap(Map<String, Object> map){
		sessionFactory.getCurrentSession().save("MainRecode_copy", map);
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