package com.sharpower.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class AppDBInit {
	/**对主数据表的Hibernate文件进行初始化。
     * 
     */
    public void contextInitialized()  { 
    	//1.获取jdbc数据库连接信息。
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("db.properties");
		Properties properties = new Properties();
		
		try {
			properties.load(inputStream);

			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			String jdbcUrl = properties.getProperty("jdbcUrl");
			String driverClass = properties.getProperty("driverClass");
			
			//2.连接数据库。
			Class.forName(driverClass);
			
			Connection connection = DriverManager.getConnection(jdbcUrl,user,password);
			
			//3.进行Plc变量名的查询。
			String sql = "select variable.name,variable.dbname, "
					+ "variabletype.type from variable "
					+ "left outer join variabletype on variable.type=variabletype.id;";
			
			ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
	
			//4.打开主数据表hibernate配置文件。并写入。
			generateMainRecodeHbmXml(resultSet, "com/sharpower/entity/MainRecode.hbm.xml", "MainRecode");  
			
			inputStream.close();
			resultSet.close();
			connection.close();
			
//			Enumeration<Driver> enumeration = DriverManager.getDrivers();
//			
//			while (enumeration.hasMoreElements()) {
//				System.out.println(DriverManager.getDrivers().hasMoreElements());
//				Driver driver = enumeration.nextElement();
//				DriverManager.deregisterDriver(driver);
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	private void generateMainRecodeHbmXml(ResultSet resultSet, String xmlPath, String entityName)
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
		
		while (resultSet.next()) {
			String name = resultSet.getString(1);
			String dbName = resultSet.getString(2);
			
			String variableType = resultSet.getString(3);
			
			if(dbNames.indexOf(dbName)<0){
				
				Element propertyElement = classElement.addElement("property");
				propertyElement.addAttribute("name", dbName);
				propertyElement.addAttribute("column", dbName);
				propertyElement.addAttribute("type","java.lang."+variableType);			
			}
			
					
			dbNames.add(dbName);
			
		}
		
		resultSet.beforeFirst();
				
		OutputStream out = new FileOutputStream(new File(getClass().getClassLoader().getResource(xmlPath).getPath()));			
		XMLWriter writer = new XMLWriter(out);
		
		writer.write(document);
		writer.close();
		
		fileCopy(getClass().getClassLoader().getResource(xmlPath).getPath(), "D://myEclipseWorkspace//eclipseWorkspace//SHARPOWER_SCADA//src//"+xmlPath);
	}
	
	public static void fileCopy(String readfile,String writeFile) {  
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
