package com.sharpower.service.impl;

import com.sharpower.service.BaseService;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import com.sharpower.dao.BaseDao;
import com.sharpower.entity.Variable;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDao<T> dao ;
	
	private Class<T> clazz ;
	
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	public void setDao(BaseDao<T> dao) {
		this.dao = dao;
	}
	public BaseDao<T> getDao() {
		return dao;
	}
	

	public void saveEntity(T t) {
		dao.saveEntity(t);
	}

	public void saveOrUpdateEntity(T t) {
		dao.saveOrUpdateEntity(t);
	}

	public void updateEntity(T t) {
		dao.updateEntity(t);
	}

	public void deleteEntity(T t) {
		dao.deleteEntity(t);
	}

	public void batchEntityByHQL(String hql, Object... objects) {
		dao.batchEntityByHQL(hql, objects);
	}
	
	public void executeSQL(String sql,Object...objects){
		dao.executeSQL(sql, objects);
	}

	public T loadEntity(Integer id) {
		return dao.loadEntity(id);
	}

	public T getEntity(Integer id) {
		return dao.getEntity(id);
	}

	public List<T> findEntityByHQL(String hql, Object... objects) {
		return dao.findEntityByHQL(hql, objects);
	}
	
	public List<T> findEntityByHQLPaging(String hql, int page, int rows, Object... objects) {
		return dao.findEntityByHQLPaging(hql, page, rows, objects);
	}
	
	public List<T> findAllEntities(){
		String hql = "from " + clazz.getSimpleName();
		return this.findEntityByHQL(hql);
	}
	public List<T> findAllEntitiesPaging(int page, int rows){
		String hql = "from " + clazz.getSimpleName();
		return dao.findEntityByHQLPaging(hql, page, rows);
	}
	
	public Object uniqueResult(String hql,Object...objects){
		return dao.uniqueResult(hql, objects);
	}
	
	public List executeSQLQuery(Class clazz,String sql,Object...objects){
		return dao.executeSQLQuery(clazz,sql, objects);
	}
	
	public List executeHQLQuery(String Hql,Object...objects){
		return dao.executeHQLQuery(Hql, objects);
	}
	public List executeHQLQueryPaging(String Hql, int page, int rows, Object...objects){
		return dao.executeHQLQueryPaging(Hql, page, rows, objects);
	}
}
