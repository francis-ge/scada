package com.sharpower.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class MapTest {
	@Test
	public void testmap(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("aa", 11);
		map.put("aa", 22);
		System.out.println(map);
	}
	
	@Test
	public void testListAdd(){
		List<String> list = new ArrayList<>();
		
		list.add(0, "222");
		
		System.out.println(list);
		list.remove(0);
		list.add(0, "000");
		System.out.println(list);
	}
}
