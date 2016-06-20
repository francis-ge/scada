package com.sharpower.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.opensymphony.xwork2.ActionSupport;
import com.sharpower.entity.Variable;
import com.sharpower.service.VariableService;

public class AjaxMainRecodeMapAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private VariableService variableService;
	
	private String info;
	
	public void setVariableService(VariableService variableService) {
		this.variableService = variableService;
	}
	public String getInfo() {
		return info;
	}

	public String generateMainRecodeHbmXml(){
		String hql = "FROM Variable";
		
		try {
			List<Variable> variables = variableService.findEntityByHQL(hql);
			
			System.out.println(variables.size());
			
			generateHbmXml(variables, "com/sharpower/entity/MainRecode.hbm.xml");
			
			info = "MainRecode.hbm.xml文件已重新生成，需要重启服务生效。";

		} catch (Exception e) {
			e.printStackTrace();
			info = "MainRecode.hbm.xml 文件生成失败。" + e.getMessage();
		}
		
		return SUCCESS;
	}
	
	private void generateHbmXml(List<Variable> variables, String xmlFile)
			throws DocumentException, SQLException, FileNotFoundException, UnsupportedEncodingException, IOException {
		SAXReader reader = new SAXReader();
	
		Document document = reader.read(getClass().getClassLoader().getResourceAsStream(xmlFile));

		Element rootElement = document.getRootElement();

		rootElement.clearContent();
		rootElement.remove(rootElement.attribute("default-cascade"));
		rootElement.remove(rootElement.attribute("default-access"));
		rootElement.remove(rootElement.attribute("default-lazy"));
		rootElement.remove(rootElement.attribute("auto-import"));
		
		Element classElement = rootElement.addElement("class");
		
		classElement.addAttribute("entity-name", "MainRecode");
		
		Element idElement = classElement.addElement("id");
		idElement.addAttribute("name", "id");
		idElement.addAttribute("column", "ID");
		Element generateElement = idElement.addElement("generator");

		idElement.addAttribute("type", "java.lang.Long");
		generateElement.addAttribute("class", "native");

		Element funIdElement = classElement.addElement("many-to-one"); 
		funIdElement.addAttribute("name", "fun");
		funIdElement.addAttribute("class", "com.sharpower.entity.Fun");
		funIdElement.addAttribute("column", "FUN_ID");
		funIdElement.addAttribute("not-null", "true");
		funIdElement.addAttribute("fetch", "join");
		
		Element dateElement = classElement.addElement("property");
		dateElement.addAttribute("name", "dateTime");
		dateElement.addAttribute("column", "DATE_TIME");
		dateElement.addAttribute("type", "timestamp");
		
		List<String> dbNames = new ArrayList<>();
		
		for (Variable variable : variables) {
			String name = variable.getName();
			String dbName = variable.getDbName();
			String variableType = variable.getType().getType();
			
			if (dbNames.indexOf(dbName)<0) {
				Element propertyElement = classElement.addElement("property");
				propertyElement.addAttribute("name", dbName);
				propertyElement.addAttribute("column", dbName);
				propertyElement.addAttribute("type","java.lang." + variableType);
			}
			
			dbNames.add(dbName);
		}
		
		OutputStream out = new FileOutputStream(new File(getClass().getClassLoader().getResource(xmlFile).getPath()));			
		XMLWriter writer = new XMLWriter(out);
		
		writer.write(document);
		writer.close();
		
	}
}
