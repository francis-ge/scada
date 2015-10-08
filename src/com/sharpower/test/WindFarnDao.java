package com.sharpower.test;

import org.hibernate.SessionFactory;

import com.sharpower.entity.WindFarm;

public class WindFarnDao {
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void save(WindFarm windFarm ){
		sessionFactory.getCurrentSession().save(windFarm);
	}
}
