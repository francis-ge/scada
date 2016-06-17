package com.sharpower.beckhoff;

import com.sharpower.entity.Fun;
import com.sharpower.fun.control.FunControl;
import com.sharpower.scada.exception.AdsException;
import com.sharpower.scada.exception.PlcException;
import com.sharpower.utils.AddressConvertUtils;
import com.sharpower.utils.ByteUtil;

import de.beckhoff.jni.JNIByteBuffer;
import de.beckhoff.jni.tcads.AdsCallDllFunction;
import de.beckhoff.jni.tcads.AmsAddr;

public class FunControlBeckhoffImpl implements FunControl {
	
	private long openPort() throws AdsException{		
		long port = AdsCallDllFunction.adsPortOpen();
		
		if (port==0){
			throw new AdsException("ADS open port Error.");
		}else {
			return port;
		}
	}
	
	private int getHandle(AmsAddr lj_AmsAddr, String varName) throws AdsException{
		
		JNIByteBuffer lj_pDataRead = new JNIByteBuffer(4);
		JNIByteBuffer lj_pDataWrite = new JNIByteBuffer(ByteUtil.getBytes(varName));
		
		long err = AdsCallDllFunction.adsSyncReadWriteReq(	lj_AmsAddr, 
															AdsCallDllFunction.ADSIGRP_SYM_HNDBYNAME, 
															0, 
															lj_pDataRead.getUsedBytesCount(), 
															lj_pDataRead, 
															lj_pDataWrite.getUsedBytesCount(), 
															lj_pDataWrite);
													
		if(err!=0){
			throw new AdsException("getHandle ADS Error, ErrorCode:" + err + ",AmsAddr:" + lj_AmsAddr.getNetIdString());
		}else {
			return ByteUtil.getInteger(lj_pDataRead.getByteArray());
			 
		}
	}
	
	private void execute(AmsAddr lj_AmsAddr, long handle, long lj_length, JNIByteBuffer lj_pData) throws AdsException{
		long err = AdsCallDllFunction.adsSyncWriteReq(	lj_AmsAddr, 
														AdsCallDllFunction.ADSIGRP_SYM_VALBYHND, 
														handle, 
														lj_length, 
														lj_pData);
		
		if(err!=0){
			throw new AdsException("execute ADS Error, ErrorCode:" + err + ",AmsAddr:" + lj_AmsAddr.getNetIdString());
		}
	}
	
	private void writeBoolean(String addr, String name, boolean sta) throws AdsException{
		AmsAddr lj_AmsAddr = AddressConvertUtils.string2AmsAddr(addr);
		
		openPort();

		int handle = getHandle( lj_AmsAddr, name);
		
		byte[] pData;
		pData = ByteUtil.getBytes(sta);

		JNIByteBuffer lj_pData = new JNIByteBuffer(pData);	
		
		execute(lj_AmsAddr, handle,lj_pData.getUsedBytesCount(),lj_pData);
		
		AdsCallDllFunction.adsPortClose();
		
	}
	
	@Override
	public void run(Fun fun) throws PlcException{
		writeBoolean(fun.getAddress(), ".visu_di_control_start_signal", true);
	}

	@Override
	public void stop(Fun fun) throws PlcException{
		writeBoolean(fun.getAddress(), ".visu_di_control_stop_signal", true);
	}

	@Override
	public void service(Fun fun) throws PlcException{
		writeBoolean(fun.getAddress(), ".visu_di_control_service_mode", true);
	}

	@Override
	public void reset(Fun fun) throws PlcException{
		writeBoolean(fun.getAddress(), ".visu_di_control_reset_signal", true);
	}

	@Override
	public void yawLeft(Fun fun) throws PlcException{
		writeBoolean(fun.getAddress(), ".commiss_yaw_left", true);
	}

	@Override
	public void yawRight(Fun fun) throws PlcException{
		writeBoolean(fun.getAddress(), ".commiss_yaw_right", true);
	}

	@Override
	public void powerLimit(Fun fun, float val) throws PlcException{
		writeBoolean(fun.getAddress(), ".hmi_power_limit_enable", true);
		
		AmsAddr lj_AmsAddr = AddressConvertUtils.string2AmsAddr(fun.getAddress());
		
		openPort();

		int handle = getHandle( lj_AmsAddr, ".visu_power_setpoint_limit_power_setpoint");

		byte[] pData = ByteUtil.getBytes(val);

		JNIByteBuffer lj_pData = new JNIByteBuffer(pData);	
		
		execute(lj_AmsAddr, handle,lj_pData.getUsedBytesCount(),lj_pData);
		
		AdsCallDllFunction.adsPortClose();
		
	}

	@Override
	public void powerLimitCancel(Fun fun) throws PlcException{
		writeBoolean(fun.getAddress(), ".hmi_power_limit_enable", false);
	}

}
