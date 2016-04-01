package com.sharpower.test;

import com.sharpower.entity.Fun;
import com.sharpower.entity.WindFarm;

public class FunService {
	private FunDao funDao;
	private WindFarnDao windFarnDao;

	public FunDao getFunDao() {
		return funDao;
	}
	public void setFunDao(FunDao funDao) {
		this.funDao = funDao;
	}

	public WindFarnDao getWindFarnDao() {
		return windFarnDao;
	}
	public void setWindFarnDao(WindFarnDao windFarnDao) {
		this.windFarnDao = windFarnDao;
	}

	public void add() {
		WindFarm windFarm = new WindFarm();
		windFarm.setName("Sharpower");

		Fun fun = new Fun();
		Fun fun1 = new Fun();

		fun.setWindFarm(windFarm);
		fun1.setWindFarm(windFarm);

		windFarm.getFuns().add(fun);
		windFarm.getFuns().add(fun1);

		
		windFarnDao.save(windFarm);
		funDao.save(fun);
		funDao.save(fun1);

	}
}
