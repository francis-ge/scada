package com.sharpower.test;

import java.lang.reflect.Method;

import org.junit.Test;

import com.sharpower.utils.ByteUtil;
import com.sharpower.utils.ReflectionUtils;

public class ObjectTypeTest {
	@Test
	public void testType(){
		int int1 = 1;
		Object object = 1;

		System.out.println(object.getClass());
		
		Class pramas[] = new Class[]{object.getClass()};
		//System.out.println(pramas);
		
		Method method = ReflectionUtils.getDeclaredMethod(new ByteUtil(), "getBytes", pramas);
		System.out.println(method);
	}
}