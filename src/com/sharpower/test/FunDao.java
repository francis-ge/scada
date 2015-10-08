package com.sharpower.test;

import org.hibernate.SessionFactory;

import com.sharpower.entity.Fun;

public class FunDao {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	public void save(Fun fun){	
		sessionFactory.getCurrentSession().save(fun);
	}
}

