package com.sharpower.dao.impl;

import com.sharpower.dao.BaseDao;
import com.sharpower.entity.Variable;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;

@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	private SessionFactory sf;

	private Class<T> clazz;

	public void setSessionFactory(SessionFactory sf) {
		this.sf = sf;
	}
	
	public SessionFactory getSessionFactory(){
		return sf;
	}

	public BaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];

	}

	public void saveEntity(T t) {
		sf.getCurrentSession().save(t);
	}	

	public void saveOrUpdateEntity(T t) {
		sf.getCurrentSession().saveOrUpdate(t);
	}

	public void updateEntity(T t) {
		sf.getCurrentSession().update(t);
	}

	public void deleteEntity(T t) {
		sf.getCurrentSession().delete(t);
	}

	public void batchEntityByHQL(String hql, Object... objects) {
		Query q = sf.getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		q.executeUpdate();
	}

	public void executeSQL(String sql, Object... objects) {
		SQLQuery q = sf.getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		q.executeUpdate();
	}

	public T loadEntity(Integer id) {
		return (T) sf.getCurrentSession().load(clazz, id);
	}

	public T getEntity(Integer id) {
		return (T) sf.getCurrentSession().get(clazz, id);
	}

	public List<T> findEntityByHQL(String hql, Object... objects) {
		Query q = sf.getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		
		return q.list();
	}
	public List<T> findEntityByHQLPaging(String hql, int page, int rows, Object... objects) {
		Query q = sf.getCurrentSession().createQuery(hql).setFirstResult(page).setMaxResults(rows);
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		
		return q.list();
	}

	public Object uniqueResult(String hql, Object... objects) {
		Query q = sf.getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		return q.uniqueResult();
	}
	
	public List executeHQLQuery(String hql, Object... objects) {
		Query q = sf.getCurrentSession().createQuery(hql);
		
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		
		return q.list();
	}
	
	public List executeHQLQueryPaging(String hql, int page, int rows, Object... objects) {
		Query q = sf.getCurrentSession().createQuery(hql).setFirstResult(page).setMaxResults(rows);
		
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		
		return q.list();
	}

	public List executeSQLQuery(Class clazz, String sql, Object... objects) {
		SQLQuery q = sf.getCurrentSession().createSQLQuery(sql);

		if (clazz != null) {
			q.addEntity(clazz);
		}
		
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		
		return q.list();
	}
}
