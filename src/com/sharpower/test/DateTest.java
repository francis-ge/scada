package com.sharpower.test;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

public class DateTest {

	@Test
	public void testDate(){
	
		Date date = new Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		Timestamp timestamp = new Timestamp(date.getTime());
		System.out.println(timestamp.toString());
		System.out.println();
		System.out.println(date.getTime());
		System.out.println(date.toString());
		System.out.println(date);
	}
}
