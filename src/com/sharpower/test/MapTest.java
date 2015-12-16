package com.sharpower.test;

import java.util.HashMap;
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
}
