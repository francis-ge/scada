package com.sharpower.action;

import java.util.ArrayList;
import java.util.HashMap;
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

public class RealTimeInfoAjaxAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private FunService funService;
	private VariableService variableService;
	private RecodeService recodeService;
	
	private Fun fun;
	private int funId;
	
	private List<RealtimeInfoDspObj> realtimeInfoDspObjs= new ArrayList<>();

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
	
	public List<RealtimeInfoDspObj> getRealtimeInfoDspObjs() {
		return realtimeInfoDspObjs;
	}

	public void setRealtimeInfoDspObjs(List<RealtimeInfoDspObj> realtimeInfoDspObjs) {
		this.realtimeInfoDspObjs = realtimeInfoDspObjs;
	}
	
	public String realTimeInfo(){
		variables= variableService.findAllEntities();
		
		String hql = "FROM MainRecode_copy WHERE funId=?";
		
		int id;
		if (fun==null){
			id = funId;
		}else{
			id= fun.getId();
		}
	
		List<Map<String, Object>> list;
		list = recodeService.findMapByHql(hql, id);
		
		if (list.size()>0) {
			Map<String, Object> recodeMap = list.get(0);
			
			for(Entry<String, Object> entry:recodeMap.entrySet()){
				RealtimeInfoDspObj realtimeInfoDspObj = new RealtimeInfoDspObj();
				
				String valName = entry.getKey();;
				
				for(int i=0 ;i<variables.size();i++){
					if(variables.get(i).getDbName().equals(entry.getKey())){
						valName = variables.get(i).getShowNameCN();
						break;
					}
					
				}
	
				realtimeInfoDspObj.setName(valName);
				realtimeInfoDspObj.setValue(entry.getValue());
				
				realtimeInfoDspObjs.add(realtimeInfoDspObj);
			}
		}
		
		return SUCCESS;
	}



}
