package com.sharpower.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.dao.DataIntegrityViolationException;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Fun;
import com.sharpower.service.FunService;
import com.sharpower.utils.ExportExlUtils;

public class AjaxFunAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Fun fun;
	private FunService funService;
	private List<Fun> funs = new ArrayList<>();
	private String resulte;
	private String ids;
	
	private String fields;
	private String titles;
	
	public Fun getFun() {
		return fun;
	}
	
	public void setFun(Fun fun) {
		this.fun = fun;
	}
	
	@JSON(serialize=false)
	public FunService getFunService() {
		return funService;
	}

	public void setFunService(FunService funService) {
		this.funService = funService;
	}

	public List<Fun> getFuns() {
		return funs;
	}

	public void setFuns(List<Fun> funs) {
		this.funs = funs;
	}
	
	public String getResulte() {
		return resulte;
	}
	
	public void setResulte(String resulte) {
		this.resulte = resulte;
	}
	
	public String getIds() {
		return ids;
	}
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String allFun(){
		try {
			funs = funService.findAllEntities();
			resulte = "共查到" + funs.size() + "条记录。";
		} catch (Exception e) {
			resulte = "获取失败！"+e.getMessage();
		}
		
		return SUCCESS;
	}
	
	public String saveOrUpdateFun(){
		try {
			funService.saveOrUpdateEntity(fun);
			resulte = "保存成功！";
		} catch (Exception e) {
			// TODO: handle exception
			resulte= e.getMessage();
		}
		
		return SUCCESS;
	}
	
	public String deleteFun(){
		String idString[] = ids.split(",");
		try {
			for (String idStr : idString) {
				Fun fun = new Fun();
				fun.setId(Integer.parseInt(idStr));
				funService.deleteEntity(fun);
			}
			resulte = "删除成功！";
		}catch (DataIntegrityViolationException e) {
			
			e.printStackTrace();
			resulte = "存在相关数据记录，无法删除！" + e.getMessage();
		}finally {
			resulte = "删除失败！";
		}
		
		return SUCCESS;

	}
	
	public InputStream getExcelFile(){
		//创建Excel
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet0");

		List<Fun> funs = funService.findAllEntities();
		
		ExportExlUtils.outputHeaders(titles.split(","), sheet);
		ExportExlUtils.outputColumns(fields.split(","), funs, sheet, 1);
		
		InputStream inputStream = new ByteArrayInputStream(wb.getBytes());
		
		return inputStream;
			
	}


}
