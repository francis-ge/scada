package com.sharpower.utils;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextSingle {
	private final static ApplicationContextSingle instance = new ApplicationContextSingle();
	private static ApplicationContext applicationContext;
	private static SessionFactory sessionFactory;
	
	private  ApplicationContextSingle() {}
	
	public static ApplicationContextSingle getAppApplicationContextSingle(){
		return instance;
	}
	public ApplicationContext getApplicationContext() {
		if(applicationContext==null){
			applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");			
		}
		
		return applicationContext;
	}
	
	public SessionFactory getSessionFactory(){
		if(sessionFactory==null){
			sessionFactory =getApplicationContext().getBean(SessionFactory.class);
		}
		return sessionFactory;
	}

}
	

