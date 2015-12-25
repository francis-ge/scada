package com.sharpower.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.sharpower.entity.Fun;
import com.sharpower.entity.FunTroubleRecode;
import com.sharpower.utils.ApplicationContextSingle;



public class HibernateTest {
	@Test
	public void testGetErrorForOne(){
		SessionFactory sessionFactory = null;
		
		Configuration configuration = new Configuration().configure("hibernate.cfg2.xml"); 
		
		sessionFactory = configuration.buildSessionFactory();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date1= new Date();
		Date date2= new Date();
		
		try {
			date1 = dateFormat.parse("2015-12-22 00:00:00");
			date2 = dateFormat.parse("2015-12-23 11:10:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Session session = sessionFactory.openSession();
		//select mrc FROM MainRecode_copy mrc LEFT JOIN FETCH mrc.fun fun
		String hql ="SELECT new map(ftr.fun.name as name, ftr.funTroubleVariable.code as code, ftr.funTroubleVariable.description as description, count(ftr) as count, max(ftr.endTime) as maxTime) FROM FunTroubleRecode ftr "
				+ "WHERE ftr.fun.id=? "
				+ "AND ftr.startTime>? AND ftr.endTime<? "
				+ "GROUP BY ftr.funTroubleVariable";
		session.getTransaction().begin();
		List<Map<String, Object>> list = session.createQuery(hql).setParameter(0, 1).setParameter(1,date1 ).setParameter(2, date2).list();
		session.getTransaction().commit();
		System.out.println(list);
		
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void testGetPartMap(){
		SessionFactory sessionFactory = null;
		
		Configuration configuration = new Configuration().configure("hibernate.cfg2.xml"); 
		
		sessionFactory = configuration.buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		//select mrc FROM MainRecode_copy mrc LEFT JOIN FETCH mrc.fun fun
		String hql = "select new map(mrc.id as id, mrc.fun.name as fun, mrc.dateTime as time, mrc._MAIN__BOOLTEST1 as bool1, mrc.___main_loop_mode_number as mode) FROM MainRecode mrc";
		session.getTransaction().begin();
		List<Map<String, Object>> list = session.createQuery(hql).list();
		session.getTransaction().commit();
		System.out.println(list);
		
		session.close();
		sessionFactory.close();
	
	}
	
	@Test
	public void testChildQuery(){
		SessionFactory sessionFactory = null;
		
		Configuration configuration = new Configuration().configure("hibernate.cfg2.xml"); 
		
		sessionFactory = configuration.buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		//select mrc FROM MainRecode_copy mrc LEFT JOIN FETCH mrc.fun fun
		String hql = "FROM FunTroubleRecode ftr WHERE ftr.id=(SELECT MAX(id) FROM FunTroubleRecode WHERE funTroubleVariable.id=?)";
		session.getTransaction().begin();
		FunTroubleRecode funTroubleRecode= (FunTroubleRecode) session.createQuery(hql)
				.setParameter(0, 10).uniqueResult();
		session.getTransaction().commit();
		System.out.println(funTroubleRecode);
		
		session.close();
		sessionFactory.close();
	
	}
	
	@Test
	public void  testSave(){
		
		SessionFactory sessionFactory = null;
		
		Configuration configuration = new Configuration().configure("hibernate.cfg2.xml"); 
		
		sessionFactory = configuration.buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		
		Map<String, Object> map = new HashMap<>();
		Fun fun1 = new Fun();
		fun1.setId(1);
		map.put("id", new Long("10"));
		map.put("fun", fun1);

		session.getTransaction().begin();
		session.save("MainRecode_copy", map);
		session.getTransaction().commit();
		//System.out.println(session.get("MainRecode_copy", 1L).getClass());
		
		
		
//		Fun fun = new Fun();
//		fun.setId(5);
//		fun.setAddress("192.168.100.62.1.1:852");
//		fun.setLine("A");
//		fun.setName("2号机组");
//		fun.setType("beckhoff");
//		
//		session.getTransaction().begin();
//		session.save(fun);
//		session.getTransaction().commit();
		
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
