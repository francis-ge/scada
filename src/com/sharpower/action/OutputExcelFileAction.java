package com.sharpower.action;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.utils.ExportExlUtils;

public class OutputExcelFileAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private String fields;
	private String titles;
	private String excelData;
	private InputStream inputStream;

	private int contentLength;
	private String contentDisposition;
	
	public void setFields(String fields) {
		this.fields = fields;
	}
	public void setTitles(String titles) {
		this.titles = titles;
	}
	public void setExcelData(String excelData) {
		this.excelData = excelData;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public int getContentLength() {
		return contentLength;
	}
	public String getContentDisposition() {
		return contentDisposition;
	}
	
	@SuppressWarnings("unchecked")
	public String excelFile(){
		
		//创建Excel
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet0");

		try {
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) JSONUtil.deserialize(excelData);
			
			String[] fieldArray = fields.split(",");
			
			ExportExlUtils.outputHeaders(titles.split(","), sheet);
			
			ExportExlUtils.outputColumnsforMap(fieldArray, dataList, sheet);
			
			FileOutputStream fileOut;
		
			fileOut = new FileOutputStream("workbook.xls");
			inputStream = new FileInputStream("workbook.xls");
			wb.write(fileOut);
			fileOut.close();
			wb.close();
			contentLength = inputStream.available();
			contentDisposition = "attachment;filename=\"Donwload_" + new Date() + ".xls\"";
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return SUCCESS;
	}
}