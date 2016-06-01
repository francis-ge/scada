package com.sharpower.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.FunTroubleVariable;
import com.sharpower.entity.TroubleType;
import com.sharpower.entity.Variable;
import com.sharpower.service.FunTroubleVariableService;
import com.sharpower.service.VariableService;

public class AjaxFunTroubleVariableAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private FunTroubleVariable funTroubleVariable;
	private FunTroubleVariableService funTroubleVariableService;
	private String searchKey;
	
	private List<FunTroubleVariable> funTroubleVariables = new ArrayList<>();
	private Map<String, Object> result = new HashMap<>();
	
	private String ids;
	
	private int page;
	private int rows;
	
	public FunTroubleVariable getFunTroubleVariable() {
		return funTroubleVariable;
	}
	public void setFunTroubleVariable(FunTroubleVariable funTroubleVariable) {
		this.funTroubleVariable = funTroubleVariable;
	}
	public void setFunTroubleVariableService(FunTroubleVariableService funTroubleVariableService) {
		this.funTroubleVariableService = funTroubleVariableService;
	}
	
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	
	public Map<String, Object> getResult() {
		return result;
	}
	
	public List<FunTroubleVariable> getFunTroubleVariables() {
		return funTroubleVariables;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public String allFunTroubleVariable(){
		try {
			String totalHql = "";
			Long total;
			
			if (searchKey==null) {
				funTroubleVariables = funTroubleVariableService.findAllEntitiesPaging((page-1)*rows, rows);
				totalHql = "SELECT count(*) From FunTroubleVariable";
				
				total = (Long) funTroubleVariableService.uniqueResult(totalHql);
				 
			}else{
				String hql = "From FunTroubleVariable v WHERE v.name like ?";
				funTroubleVariables = funTroubleVariableService.findEntityByHQLPaging( hql, (page-1)*rows, rows, "%"+searchKey+"%");
				totalHql = "SELECT count(*) From FunTroubleVariable v WHERE v.name like ?";
				
				total = (Long) funTroubleVariableService.uniqueResult(totalHql, "%"+searchKey+"%");
			}
			
			result.put("total", total);
			result.put("rows", funTroubleVariables);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", e.getMessage());
			
		}
		
		return SUCCESS;
	}
	
	/**
	 * 将变量名转换为数据库存储名
	 * @param name plc变量名
	 * @return
	 */
	private String convertNameToDBname(String name){
		String hql = "From FunTroubleVariable v WHERE v.name=?";
		List<FunTroubleVariable> funTroubleVariables = funTroubleVariableService.findEntityByHQL(hql, name);
		
		if (name.equals("")) {
			return "";
		}
		String dbName = name.replace(".", "__");
		
		char dbNameConvertFlag;
		
		//解决数据库名称重名的问题,重复变量名添加大写字母前缀加“_”，变量名中的“.”转变为“__”.
		if(funTroubleVariables.size()>0){
			String lastDbName="";
			for (int i=funTroubleVariables.size()-1;i>=0;i--) {
				if  (funTroubleVariables.get(i).getDbName()!=""){
					lastDbName=funTroubleVariables.get(i).getDbName();
					break;
				}
			}
			
			if (lastDbName=="") {
				return name;
			}
			char head = lastDbName.charAt(0);
			
			if(head=='_'){
				dbNameConvertFlag = 'A';
			}else {				
				dbNameConvertFlag = (char)((int)head + 1);
			}
			return dbNameConvertFlag + "_" + dbName;

		}else {
			return "_" + dbName;
		}
		
	}
	
	public String saveOrUpdate(){
		try {
			if (funTroubleVariable.getId()==null ) {
				funTroubleVariable.setDbName(this.convertNameToDBname(funTroubleVariable.getName()));
			}else {
				String updateName = funTroubleVariable.getName();
				FunTroubleVariable funTroubleVariable1 = funTroubleVariableService.getEntity(this.funTroubleVariable.getId());
				String oldName = funTroubleVariable1.getName();
				
				if (updateName.equals(oldName)==false) {
					funTroubleVariable.setDbName(this.convertNameToDBname(funTroubleVariable.getName()));
				}else{
					funTroubleVariable.setDbName(funTroubleVariable1.getDbName());
				}
			}
			
			funTroubleVariableService.saveOrUpdateEntity(funTroubleVariable);
			
			result.put("message", "保存成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", e.getMessage());
			
		}
		
		return SUCCESS;
	}

	public String delete(){
		String idString[] = ids.split(",");
		try {
			for (String idStr : idString) {
				FunTroubleVariable variable = new FunTroubleVariable();
				variable.setId(Integer.parseInt(idStr));
				funTroubleVariableService.deleteEntity(variable);
			}
			
			result.put("message", "删除成功！");
		
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", e.getMessage());
			
		}
		return SUCCESS;
	}
}
