package com.sharpower.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

public class XmlTest {
	@Test
	public void testCreateXml() throws DocumentException, IOException {
		
		SAXReader reader = new SAXReader();
		
//		Document document = reader.read(getClass().getClassLoader().getResourceAsStream("com/sharpower/entity/mainRecode.hbm.xml"));
		Document document = reader.read(new File("ot.hbm.xml"));
//		Element element = document.getRootElement();
//		System.out.println(element.attribute("default-access").getValue());
//		element.remove(element.attribute("default-cascade"));
//		element.remove(element.attribute("default-access"));
//		element.remove(element.attribute("default-lazy"));
//		element.remove(element.attribute("auto-import"));
//		Element element2 = element.addElement("id");
//		element2.setText("asdfasd");
//		element2.addAttribute("culomn", "AA");
//		//element.clearContent();
//		
//		XMLWriter writer = new XMLWriter(new  FileWriter("ot.hbm.xml"));
//		writer.write(document);  
//		writer.close();  
//		
//		//System.out.println(element.getName());
//		Iterator iterator= element.elementIterator();
//		for (iterable_type iterable_element : iterable) {
//			
//		}
//     Document document = reader.read(); 
//     Element element = document.getRootElement();
//     System.out.println(element.getName());
     
	}
}
