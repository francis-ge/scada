package com.sharpower.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.sharpower.entity.Fun;
import com.sharpower.entity.FunTroubleRecode;
import com.sharpower.utils.ApplicationContextSingle;



public class HibernateTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMainRecodeMapHbmXml(){
		SessionFactory sessionFactory = null;
		
		Configuration configuration = new Configuration().configure("hibernate.cfg2.xml"); 
		
		sessionFactory = configuration.buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		
		String hql = "SELECT new map(sum(CASE WHEN (mr.___wind_direction>348.75 AND mr.___wind_direction<=360) OR ( mr.___wind_direction>=0 AND mr.___wind_direction<=11.25 ) THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>11.25 AND mr.___wind_direction<=33.75 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>33.75 AND mr.___wind_direction<=56.25 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>56.25 AND mr.___wind_direction<=78.75 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>78.75 AND mr.___wind_direction<=101.25 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>101.25 AND mr.___wind_direction<=123.75 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>123.75 AND mr.___wind_direction<=146.25 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>146.25 AND mr.___wind_direction<=168.75 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>168.75 AND mr.___wind_direction<=191.25 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>191.25 AND mr.___wind_direction<=213.75 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>213.75 AND mr.___wind_direction<=236.25 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>236.25 AND mr.___wind_direction<=258.75 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>258.75 AND mr.___wind_direction<=281.25 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>281.25 AND mr.___wind_direction<=303.75 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>303.75 AND mr.___wind_direction<=326.25 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000, "
				+ "sum(CASE WHEN mr.___wind_direction>326.25 AND mr.___wind_direction<=348.75 THEN 1 ELSE 0 END)/sum(CASE WHEN mr.___wind_direction is not null THEN 1 ELSE 0 END)*10000) "
				+ "FROM MainRecode mr WHERE mr.fun.id=? AND mr.dateTime>? AND mr.dateTime<?";
		
		session.getTransaction().begin();
		Date date1 = new Date();
		Date date2 = new Date();
		
		date1 = DateUtils.setYears(date1, 2015);
		date1 = DateUtils.setMonths(date1, 6);
		date1 = DateUtils.setDays(date1, 1);
		
		date2 = DateUtils.setYears(date2, 2016);
		date2 = DateUtils.setMinutes(date2, 6);
		date2 = DateUtils.setDays(date2, 1);
		
		List<Map<String, Object>> list = session.createQuery(hql).setParameter(0, 1).setParameter(1,date1).setParameter(2, date2).list();
		session.getTransaction().commit();
		System.out.println(list);
		
		session.close();
		sessionFactory.close();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testreportHourForOne() throws ParseException{
		SessionFactory sessionFactory = null;
		Configuration configuration = new Configuration().configure("hibernate.cfg2.xml"); 
		sessionFactory = configuration.buildSessionFactory();
		
		Calendar calendar = Calendar.getInstance();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date time = dateFormat.parse("2016-04-01 04:00:00");
		calendar.setTime(time);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		Date beginTime = calendar.getTime();
		
		calendar.set(Calendar.HOUR_OF_DAY, 23);		
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date endTime = calendar.getTime();

		
		String hql = "SELECT new map( hour(mr.dateTime),  "
				+ "mr.fun.id as funId, "
				+ "mr.fun.name as name, "
				+ "max(mr.___visu_grid_energy) as maxEngergy, "
				+ "avg(mr.___wind_speed) as averageWindSpeed, "
				+ "avg(mr.___visu_grid_power) as averagePower, "
				+ "avg(mr.___visu_grid_reactive_power) as averageReactivePower, "
				+ "max(mr.___wind_speed) as maxSpeed, "
				+ "max(mr.___visu_grid_power) as maxPower, "
				+ "avg(mr.___visu_nacelle_outdoor_temperature) as nacelleOutdoorTemperature) "
				+ "FROM MainRecode mr WHERE mr.fun.id=? AND mr.dateTime>? AND mr.dateTime<? "
				+ "GROUP BY hour(mr.dateTime)";
		
		Session session = sessionFactory.openSession();
		
		session.getTransaction().begin();
		List<Map<String, Object>> list = session.createQuery(hql).setParameter(0, 1).setParameter(1, beginTime)
				.setParameter(2, endTime).list();
				
		
		session.getTransaction().commit();
		System.out.println(list);
		
		session.close();
		sessionFactory.close();
				
	}
	@Test
	public void testPowerCurve(){
		SessionFactory sessionFactory = null;
		
		Configuration configuration = new Configuration().configure("hibernate.cfg2.xml"); 
		
		sessionFactory = configuration.buildSessionFactory();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date1= new Date();
		Date date2= new Date();
		
		try {
			date1 = dateFormat.parse("2016-04-01 00:00:00");
			date2 = dateFormat.parse("2016-04-01 23:10:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Session session = sessionFactory.openSession();
		String hql ="SELECT new map((sum(CASE WHEN mr.___wind_speed>=0 AND mr.___wind_speed<1.5 THEN mr.___visu_grid_power else 0 end))/(sum(CASE WHEN mr.___wind_speed>=0 AND mr.___wind_speed<1.5 THEN 1 else 0 end)), ";
		
		for(int i = 0; i < 47; i++){
			hql = hql + " (sum( CASE WHEN mr.___wind_speed>=" + (1.5+i*0.5) + " AND mr.___wind_speed<" + (1.5+i*0.5+0.5) + " THEN mr.___visu_grid_power ELSE 0 END))/(sum( CASE WHEN mr.___wind_speed>=" + (1.5+i*0.5) + " AND mr.___wind_speed<" + (1.5+i*0.5+0.5) + " THEN 1 ELSE 0 END)),";
		}
		
		hql = hql + " (sum(CASE WHEN mr.___wind_speed>=25 THEN mr.___visu_grid_power ELSE 0 END))/(sum(CASE WHEN mr.___wind_speed>=25 THEN 1 ELSE 0 END))) " 
				 + " FROM MainRecode mr WHERE mr.fun.id=? AND mr.dateTime>? AND mr.dateTime<?";
		
		session.getTransaction().begin();
		List<Map<String, Object>> list = session.createQuery(hql).setParameter(0, 1).setParameter( 1, date1).setParameter(2, date2).list();
		session.getTransaction().commit();
		System.out.println(list);
		
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void testWindFrequencyCurve(){
		SessionFactory sessionFactory = null;
		
		Configuration configuration = new Configuration().configure("hibernate.cfg2.xml"); 
		
		sessionFactory = configuration.buildSessionFactory();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date1= new Date();
		Date date2= new Date();
		
		try {
			date1 = dateFormat.parse("2016-04-01 00:00:00");
			date2 = dateFormat.parse("2016-04-01 23:10:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Session session = sessionFactory.openSession();
		String hql ="SELECT new map(sum(CASE WHEN mr.___wind_speed>=0 AND mr.___wind_speed<1.5 THEN 1 else 0 end), "
							+ " sum( case WHEN mr.___wind_speed>=1.5 AND mr.___wind_speed<3 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=3 AND mr.___wind_speed<4.5 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=4.5 AND mr.___wind_speed<6 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=6 AND mr.___wind_speed<7.5 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=7.5 AND mr.___wind_speed<9 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=9 AND mr.___wind_speed<10.5 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=10.5 AND mr.___wind_speed<12 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=12 AND mr.___wind_speed<13.5 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=13.5 AND mr.___wind_speed<15 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=15 AND mr.___wind_speed<16.5 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=16.5 AND mr.___wind_speed<18 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=18 AND mr.___wind_speed<19.5 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=19.5 AND mr.___wind_speed<21 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=21 AND mr.___wind_speed<22.5 THEN 1 else 0 END),"
							+ " sum( case WHEN mr.___wind_speed>=22.5  THEN 1 else 0 END))"
							+ " FROM MainRecode mr WHERE mr.fun.id=? AND mr.dateTime>? AND mr.dateTime<? ";
		
		session.getTransaction().begin();
		List<Map<String, Object>> list = session.createQuery(hql).setParameter(0, 1).setParameter(1,date1 ).setParameter(2, date2).list();
		session.getTransaction().commit();
		System.out.println(list);
		
		session.close();
		sessionFactory.close();
	}
	
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
