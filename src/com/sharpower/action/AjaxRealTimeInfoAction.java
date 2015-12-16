package com.sharpower.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.util.SystemPropertyUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Fun;
import com.sharpower.entity.RealtimeInfoDspObj;
import com.sharpower.entity.Variable;
import com.sharpower.service.FunService;
import com.sharpower.service.RecodeService;
import com.sharpower.service.VariableService;
import com.sharpower.service.VariableTypeService;

import sun.print.resources.serviceui;

public class AjaxRealTimeInfoAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private FunService funService;
	private VariableService variableService;
	private RecodeService recodeService;
	
	private Fun fun;
	private int funId;
	
	private List<Map<String, Object>> realtimeInfo=new ArrayList<>();

	private List<Variable> variables;

	@JSON(serialize=false)
	public Fun getFun() {
		return fun;
	}

	public void setFun(Fun fun) {
		this.fun = fun;
	}

	@JSON(serialize=false)
	public RecodeService getRecodeService() {
		return recodeService;
	}
	
	public VariableService getVariableService() {
		return variableService;
	}

	public void setVariableService(VariableService variableService) {
		this.variableService = variableService;
	}

	public void setRecodeService(RecodeService recodeService) {
		this.recodeService = recodeService;
	}
	
	@JSON(serialize=false)
	public int getFunId() {
		return funId;
	}
	
	public void setFunId(int funId) {
		this.funId = funId;
	}
	
	public List<Map<String, Object>> getRealtimeInfo() {
		return realtimeInfo;
	}
	
	public String mainInfo(){
		variables= variableService.findAllEntities();
		
		//String hql = "select mrc FROM MainRecode_copy mrc LEFT JOIN FETCH mrc.fun fun";
		String hql = "select new map(mrc.id as id, mrc.fun.name as funName,mrc.___main_loop_mode_number as mode, "
				+ "mrc.dateTime as dateTime,mrc.___wind_speed as windSpeed,mrc._MAIN__BOOLTEST1 as boolTest1, mrc._MAIN__SINGLETEST1 as singleTest1,"
				+ " mrc._MAIN__DOUBLETEST1 as doubleTest1, mrc._MAIN__LONGTEST1 as longTest1) FROM MainRecode_copy mrc";
		
		realtimeInfo = recodeService.findMapByHql(hql);
		
		return SUCCESS;
	}
	public String realTimeInfo(){
		variables= variableService.findAllEntities();
		
		String hql = "select new map(mrc.id as id, mrc.fun.name as funName, mrc.fun.id as funId, mrc.___main_loop_mode_number as mode, "
				+ "mrc.dateTime as dateTime,mrc.___wind_speed as windSpeed,mrc._MAIN__BOOLTEST1 as boolTest1, mrc._MAIN__SINGLETEST1 as singleTest1,"
				+ " mrc._MAIN__DOUBLETEST1 as doubleTest1, mrc._MAIN__LONGTEST1 as longTest1) FROM MainRecode_copy mrc WHERE mrc.fun.id=?";
		
		int id;
		if (fun==null){
			id = funId;
		}else{
			id= fun.getId();
		}
		
		realtimeInfo = recodeService.findMapByHql(hql, id);
		
		return SUCCESS;
	}



}
