package com.sharpower.test;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import com.sharpower.beckhoff.FunControlBeckhoffImpl;
import com.sharpower.beckhoff.FunDataReadWriteBeckhoff;
import com.sharpower.entity.Variable;
import com.sharpower.entity.VariableType;
import com.sharpower.fun.control.FunControl;
import com.sharpower.scada.exception.AdsException;
import com.sharpower.scada.exception.PlcException;
import com.sharpower.service.VariableTypeService;
import com.sharpower.service.impl.VariableTypeServiceImpl;

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
	public void testWrite(){
		FunControl funControl = new FunControlBeckhoffImpl();
		//System.out.println(funControl.powerLimit("192.168.100.61.1.1:852",111.1f));
		try {
			funControl.powerLimitCancel("192.168.100.61.1.1:852");
		} catch (PlcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadmap1(){
		SessionFactory sessionFactory = null;
		
		Configuration configuration = new Configuration().configure("hibernate.cfg2.xml"); 
		
		sessionFactory = configuration.buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		
		VariableType variableType = session.load(VariableType.class, 4);
		
		FunDataReadWriteBeckhoff funDataReadWriteBeckhoff = new FunDataReadWriteBeckhoff(variableType);

		try {
			Map<Variable, Object> data = funDataReadWriteBeckhoff.readData("192.168.100.61.1.1:852");
			for (Entry<Variable, Object> entry: data.entrySet()) {
				System.out.println(entry.getKey().getName() + "=" + entry.getValue()+";");
			}
		} catch (AdsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		session.close();
	}
}
