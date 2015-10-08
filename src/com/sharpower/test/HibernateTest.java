package com.sharpower.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.sharpower.entity.Fun;
import com.sharpower.utils.ApplicationContextSingle;



public class HibernateTest {

	
	@Test
	public void  testSave(){
		
		SessionFactory sessionFactory = null;
		
		Configuration configuration = new Configuration().configure(); 
		
		sessionFactory = configuration.buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		
		Fun fun = new Fun();
		fun.setId(1);
		fun.setAddress("192.168.100.62.1.1:852");
		fun.setLine("A");
		fun.setName("2ºÅ·ç»ú");
		fun.setType("beckhoff");
		
		session.getTransaction().begin();
		session.save(fun);
		session.getTransaction().commit();
		
		session.close();
		sessionFactory.close();
	
	}
	
	@Test
	public void  testSpringHibernate(){

		ApplicationContext ax = ApplicationContextSingle.getAppApplicationContextSingle().getApplicationContext();
		
		FunService funService= ax.getBean(FunService.class);
		
		funService.add();
		
	}
	
}
