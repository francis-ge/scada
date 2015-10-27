package com.sharpower.service.impl;

import com.sharpower.service.BaseService;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import com.sharpower.dao.BaseDao;
import com.sharpower.entity.Variable;

/**
 * �����baseService,ר�����ڼ̳�
 */
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
	
	//ִ��ԭ���sql���
	public void executeSQL(String sql,Object...objects){
		dao.executeSQL(sql, objects);
	}
	
	public void executeSQL(String sql, Map<Variable, Object> objects){
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
	
	//��ѯ����ʵ��
	public List<T> findAllEntities(){
		String hql = "from " + clazz.getSimpleName() ;
		return this.findEntityByHQL(hql);
	}
	
	//��ֵ����,ȷ����ѯ�������ֻ��һ����¼
	public Object uniqueResult(String hql,Object...objects){
		return dao.uniqueResult(hql, objects);
	}
	
	//ִ��sqlԭ���ѯ
	public List executeSQLQuery(Class clazz,String sql,Object...objects){
		return dao.executeSQLQuery(clazz,sql, objects);
	}
}
