package com.sharpower.test;
import java.util.Map;

import org.junit.Test;

import com.sharpower.scada.exception.AdsException;
import com.sharpower.utils.FunDataReadWriteBeckhoffUtils;

import de.beckhoff.jni.JNIByteBuffer;
import de.beckhoff.jni.tcads.AdsCallDllFunction;
import de.beckhoff.jni.tcads.AdsState;
import de.beckhoff.jni.tcads.AmsAddr;

public class AdsTest {
	
	@Test
	public void testOpenPort(){
		//JNIByteBuffer readBuffer = new JNIByteBuffer();
		//readBuffer.setByteArray(null, true);
		//AdsCallDllFunction.adsSyncReadWriteReq(lj_AmsAddr, lj_indexGroup, lj_indexOffset, lj_lengthRead, lj_pDataRead, lj_lengthWrite, lj_pDataWrite)

		AmsAddr amsAddr = new AmsAddr();

		long port = AdsCallDllFunction.adsPortOpen();
		System.out.println(port);
		
		AdsCallDllFunction.getLocalAddress(amsAddr);
		amsAddr.setPort(852);
		
		JNIByteBuffer jniByteBuffer = new JNIByteBuffer();
		long Err = AdsCallDllFunction.adsSyncWriteControlReq( amsAddr, AdsState.ADSSTATE_RUN, 0, 0, jniByteBuffer);
		
		System.out.println(Err);
		
		port = AdsCallDllFunction.adsPortClose();
		System.out.println(port);
	}
	
	@Test
	public void testreadMap(){
		
		try {
			Map<String, Long> LongS = FunDataReadWriteBeckhoffUtils.readData("", Long.class);
		} catch (AdsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long time = System.currentTimeMillis();
		Map<String, Float> Longd = null;
		try {
			Longd = FunDataReadWriteBeckhoffUtils.readData("", Float.class);
		} catch (AdsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		System.out.println(System.currentTimeMillis()-time);
		System.out.println(Longd);
	}
}
