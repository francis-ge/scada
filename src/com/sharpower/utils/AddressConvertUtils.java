package com.sharpower.utils;

import de.beckhoff.jni.tcads.AmsAddr;

public class AddressConvertUtils {
	
	public static AmsAddr string2AmsAddr(String addrStr){
		AmsAddr amsAddr = new AmsAddr();
		
		int endIndex = addrStr.indexOf(":");
		amsAddr.setNetIdStringEx(addrStr.substring(0, endIndex));
		
		String sPort = addrStr.substring(endIndex+1); 
		amsAddr.setPort(Integer.valueOf(sPort));
		
		return amsAddr;
	}
	
	public static String amsAddr2String( AmsAddr amsAddr){
		
		 return amsAddr.getNetIdString() + ":" + String.valueOf(amsAddr.getPort());
		
	}
}
