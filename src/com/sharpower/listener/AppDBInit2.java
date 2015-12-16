package com.sharpower.listener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.sharpower.entity.Variable;

public class AppDBInit2 {

	/**对主数据表的Hibernate文件进行初始化。
     * 
     */
    public void contextInitialized()  { 
    	SessionFactory sessionFactory = null;
		
		Configuration configuration = new Configuration().configure("hibernate.cfg2.xml"); 
		
		sessionFactory = configuration.buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		
		@SuppressWarnings("unchecked")
		List<Variable> variables= session.createQuery("FROM Variable v LEFT JOIN FETCH v.type").list();
		
		session.close();
		
		sessionFactory.close();
		
		//打开主数据表hibernate配置文件。并写入。
		try {
			generateMainRecodeHbmXml(variables, "com/sharpower/entity/MainRecode.hbm.xml", "MainRecode");
			generateMainRecodeHbmXml(variables, "com/sharpower/entity/MainRecode_copy.hbm.xml", "MainRecode_copy");
		} catch (DocumentException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
			
    }

	private void generateMainRecodeHbmXml(List<Variable> variables, String xmlPath, String entityName)
			throws DocumentException, SQLException, FileNotFoundException, UnsupportedEncodingException, IOException {
		SAXReader reader = new SAXReader();
	
		Document document = reader.read(getClass().getClassLoader().getResourceAsStream(xmlPath));

		Element rootElement = document.getRootElement();

		rootElement.clearContent();
		rootElement.remove(rootElement.attribute("default-cascade"));
		rootElement.remove(rootElement.attribute("default-access"));
		rootElement.remove(rootElement.attribute("default-lazy"));
		rootElement.remove(rootElement.attribute("auto-import"));
		
		Element classElement = rootElement.addElement("class");
		
		classElement.addAttribute("entity-name", entityName);
		
		Element idElement = classElement.addElement("id");
		idElement.addAttribute("name", "id");
		idElement.addAttribute("column", "ID");		
		Element generateElement = idElement.addElement("generator");
		
		if (entityName.equals("MainRecode_copy")) {
			idElement.addAttribute("type", "java.lang.Integer");
			generateElement.addAttribute("class", "assigned");
		}else {
			idElement.addAttribute("type", "java.lang.Long");
			generateElement.addAttribute("class", "native");
		}
		
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
		
		for (Variable variable : variables) {
			String name = variable.getName();
			String dbName = variable.getDbName();
			String variableType = "java.lang."+variable.getType().getType();
			
			Element propertyElement = classElement.addElement("property");
			propertyElement.addAttribute("name", dbName);
			propertyElement.addAttribute("column", dbName.toUpperCase());
			propertyElement.addAttribute("type", variableType);			
		}
		
		OutputStream out = new FileOutputStream(new File(getClass().getClassLoader().getResource(xmlPath).getPath()));			
		XMLWriter writer = new XMLWriter(out);
		
		writer.write(document);
		writer.close();
		
		fileCopy(getClass().getClassLoader().getResource(xmlPath).getPath(),  "D://myEclipseWorkspace//eclipseWorkspace//SHARPOWER_SCADA//src//"+xmlPath);
	}
	
	private static void fileCopy(String readfile,String writeFile) {  
	    try {  
	        FileInputStream input = new FileInputStream(readfile);  
	        FileOutputStream output = new FileOutputStream(writeFile);  
	        int read = input.read();          
	        while ( read != -1 ) {  
	            output.write(read);   
	            read = input.read();  
	        }             
	        input.close();  
	        output.close();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}  
	
}
