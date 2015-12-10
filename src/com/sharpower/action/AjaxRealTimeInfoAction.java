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
		
		String hql = "select mrc FROM MainRecode_copy mrc LEFT JOIN FETCH mrc.fun fun";

		realtimeInfo = recodeService.findMapByHql(hql);
		
		return SUCCESS;
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
		
//		List<Map<String, Object>> realtimeInfo1;
		realtimeInfo = recodeService.findMapByHql(hql, id);
		
//		Map<String, Object> realtimeInfoMap=new HashMap<>();
//		
//		if (realtimeInfo1.size()>0) {
//			Map<String, Object> recodeMap = realtimeInfo1.get(0);
//			
//			for(Entry<String, Object> entry:recodeMap.entrySet()){
//				
//			String valName = entry.getKey();
//				
//			for(int i=0 ;i<variables.size();i++){
//				if(variables.get(i).getDbName().equals(entry.getKey())){
//					valName = variables.get(i).getName();
//					break;
//				}
//			}
//			realtimeInfoMap.put(valName, entry.getValue());
//		}
//	}
//
//		realtimeInfo.add(realtimeInfoMap);
		
//		if (list.size()>0) {
//			Map<String, Object> recodeMap = list.get(0);
//			
//			for(Entry<String, Object> entry:recodeMap.entrySet()){
//				RealtimeInfoDspObj realtimeInfoDspObj = new RealtimeInfoDspObj();
//				
//				String valName = entry.getKey();;
//				
//				for(int i=0 ;i<variables.size();i++){
//					if(variables.get(i).getDbName().equals(entry.getKey())){
//						valName = variables.get(i).getShowNameCN();
//						break;
//					}
//					
//				}
//	
//				realtimeInfoDspObj.setName(valName);
//				realtimeInfoDspObj.setValue(entry.getValue());
//				
//				realtimeInfoDspObjs.add(realtimeInfoDspObj);
//			}
//		}
		
		return SUCCESS;
	}



}
