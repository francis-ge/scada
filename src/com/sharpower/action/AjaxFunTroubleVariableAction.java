package com.sharpower.action;

import java.util.ArrayList;
import java.util.List;

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
	private String resulte;
	
	private String ids;
	
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
	
	public String getResulte() {
		return resulte;
	}
	
	public List<FunTroubleVariable> getFunTroubleVariables() {
		return funTroubleVariables;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}

	public String allFunTroubleVariable(){
		try {
			if (searchKey==null) {
				funTroubleVariables = funTroubleVariableService.findAllEntities();
			}else{
				String hql = "From FunTroubleVariable v WHERE v.name like ?";
				funTroubleVariables = funTroubleVariableService.findEntityByHQL(hql, "%"+searchKey+"%");
			}
			resulte = "共查到" + funTroubleVariables.size() + "条记录。";
		} catch (Exception e) {
			e.printStackTrace();
			resulte = e.getMessage();
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
			resulte = "保存成功！";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resulte= e.getMessage();
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
			resulte = "删除成功！";
		} catch (Exception e) {
			e.printStackTrace();
			resulte = "删除失败！" + e.getMessage();
		}
		return SUCCESS;
	}
}
