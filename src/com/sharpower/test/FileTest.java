package com.sharpower.test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

import org.junit.Test;

class DirFilter implements FilenameFilter{
	private Pattern pattern;
	
	public DirFilter(String regex) {
		pattern = Pattern.compile(regex);
	}
	@Override
	public boolean accept(File dir, String name) {
		
		return pattern.matcher(name).matches();
	}
	
}

public class FileTest {
	private File file = new File(".");
	
	@Test
	public void testList(){
		
		String[] lists = file.list();
		
		for (String string : lists) {
			
			System.out.println(string);
		}
	}
}
