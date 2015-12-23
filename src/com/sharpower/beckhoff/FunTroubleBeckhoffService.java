package com.sharpower.beckhoff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharpower.entity.FunTroubleVariable;
import com.sharpower.scada.exception.AdsException;
import com.sharpower.service.FunTroubleVariableService;
import com.sharpower.utils.AddressConvertUtils;
import com.sharpower.utils.ByteUtil;

import de.beckhoff.jni.JNIByteBuffer;
import de.beckhoff.jni.tcads.AdsCallDllFunction;
import de.beckhoff.jni.tcads.AmsAddr;

public class FunTroubleBeckhoffService {
	private static final int ERROR_CODE_LENGTH = 4;//PLC错误码字节数
	private static final int READ_DATA_LENGTH  = 4;//读取数据字节数
	private static final int WRITE_TYPE_LENGTH = 16;//读取PLC数值信息结构字节数
	
	private  List<FunTroubleVariable> valNames;//变量名集合
	
	private long lj_indexOffset;//变量数量
	private long lj_lengthRead;//读取数据块的字节数
	private long lj_lengthWrite;//写入数据块的字节数
	private JNIByteBuffer lj_pDataWrite;//写入的数据块

	FunTroubleVariableService funTroubleVariableService;
	
	/**
	 * 获取写入PLC的数据块
	 * @param size 变量数量
	 * @param names 变量名集合
	 * @param lj_lengthWrite 写入PLC数据块长度
	 * @return 写入PLC的数据块
	 */
	private JNIByteBuffer getWriteJNIByteBuffer(int size, List<FunTroubleVariable> names, long lj_lengthWrite) {
		JNIByteBuffer lj_pDataWrite;
		lj_pDataWrite = new JNIByteBuffer((int) lj_lengthWrite);
		
		byte[] bytes = new byte[(int) lj_lengthWrite];
		byte[] bytes1 = new byte[0];

		int destPos = 0;

		int indexGroup;
		int indexOffset;
		int rLength;
		int wLength;

		for (FunTroubleVariable name : names) {
			indexGroup = AdsCallDllFunction.ADSIGRP_SYM_VALBYNAME;
			bytes1 = ByteUtil.getBytes(indexGroup);
			System.arraycopy(bytes1, 0, bytes, destPos, bytes1.length);
			destPos = destPos + bytes1.length;

			indexOffset = 0;
			bytes1 = ByteUtil.getBytes(indexOffset);
			System.arraycopy(bytes1, 0, bytes, destPos, bytes1.length);
			destPos = destPos + bytes1.length;

			rLength = size;
			bytes1 = ByteUtil.getBytes(rLength);
			System.arraycopy(bytes1, 0, bytes, destPos, bytes1.length);
			destPos = destPos + bytes1.length;

			wLength = name.getName().length();
			bytes1 = ByteUtil.getBytes(wLength);
			System.arraycopy(bytes1, 0, bytes, destPos, bytes1.length);
			destPos = destPos + bytes1.length;
		}

		for (FunTroubleVariable name : names) {
			bytes1 = ByteUtil.getBytes(name.getName());
			System.arraycopy(bytes1, 0, bytes, destPos, bytes1.length);
			destPos = destPos + bytes1.length;
		}

		lj_pDataWrite.setByteArray(bytes, false);
		
		return lj_pDataWrite;
	}
	
	/**
	 * 获取写入plc的数据块的长度
	 * @param names 变量名集合
	 * @return 写入plc的数据块的长度
	 */
	private long getLengWrite(List<FunTroubleVariable> names) {
		long lj_lengthWrite = 0;

		for (FunTroubleVariable name : names) {
			lj_lengthWrite = lj_lengthWrite + name.getName().length();
		}

		lj_lengthWrite = WRITE_TYPE_LENGTH * lj_indexOffset + lj_lengthWrite;
		return lj_lengthWrite;
	}
	
	/**
	 * 准备读取PLC变量所需数据
	 * @param variableType
	 */
	private void readyToReadData(FunTroubleVariableService funTroubleVariableService) {
		valNames = funTroubleVariableService.findAllEntities();
		
		if (valNames == null || valNames.size() == 0) {
			return;
		}
		
		//故障警告为布尔类型，所以大小为1Byte
		int size = 1;

		lj_indexOffset = valNames.size();

		lj_lengthRead = (ERROR_CODE_LENGTH + READ_DATA_LENGTH + size) * lj_indexOffset;

		lj_lengthWrite = getLengWrite(valNames);

		lj_pDataWrite = getWriteJNIByteBuffer(size, valNames, lj_lengthWrite);

	}
	
	/**
	 * 根据地址读取全部PLC数据
	 * @param sFunAddress PLC地址
	 * @return PLC全部数据
	 * @throws AdsException PLC异常
	 */
	public Map<FunTroubleVariable, Object> readData(String sFunAddress) throws AdsException {
		Map<FunTroubleVariable, Object> data = new HashMap<>();
		
		if (lj_indexOffset==0) {
			return data;
		}
		
		AmsAddr lj_AmsAddr = AddressConvertUtils.string2AmsAddr(sFunAddress);
		
		JNIByteBuffer lj_pDataRead = new JNIByteBuffer((int) lj_lengthRead);
		
		AdsCallDllFunction.adsPortOpen();

		Long err = AdsCallDllFunction.adsSyncReadWriteReq(lj_AmsAddr, 0xF082, lj_indexOffset, lj_lengthRead,
				lj_pDataRead, lj_lengthWrite, lj_pDataWrite);

		AdsCallDllFunction.adsPortClose();
		
		if (err == 0) {
			data = getData(lj_pDataRead);

			return data;
		} else {
			throw new AdsException("ADS Error,ErrorCode:" + err);
		}

	}
	
	/**
	 * 解析ads返回的数据块
	 * @param lj_pDataRead ADS数据块
	 * @return
	 */
	private Map<FunTroubleVariable, Object> getData( JNIByteBuffer lj_pDataRead ) {
		//ADS数据块转换为字节数组
		byte[] bytes = lj_pDataRead.getByteArray();
		
		//变量读取的错误码
		int errCode = 0;
		//读取错误的变量数量
		int errCount = 0;
		//变量值
		Object result = null;
		
		//读取字节数组的临时变量
		int destPos = 0;
		
		//读取错误码的字节数组
		byte[] bytes1 = new byte[ERROR_CODE_LENGTH];
		//读取变量值的字节数组
		byte[] bytes2 = new byte[1];

		Map<FunTroubleVariable, Object> data = new HashMap<>();
		
		for (int i = 0; i < lj_indexOffset; i++) {
			//获取错误码字节数组
			System.arraycopy(bytes, destPos, bytes1, 0, ERROR_CODE_LENGTH);
			destPos = destPos + ERROR_CODE_LENGTH + READ_DATA_LENGTH;
			
			//转换字节数组为整型值
			errCode = ByteUtil.getInteger(bytes1);
			//如果错误码为0则读取变量值
			if (errCode == 0) {
				System.arraycopy(bytes, (int)(8 * lj_indexOffset + 1 * i - 1 * errCount), 
						bytes2, 0, 1);
				
				result = ByteUtil.getBoolean(bytes2);
				
			} else {
				errCount++;
				result = null;
			}

			data.put(valNames.get(i), result);

		}
		
		return data;
		
	}

	public FunTroubleBeckhoffService(FunTroubleVariableService funTroubleVariableService) {
		this.funTroubleVariableService = funTroubleVariableService;
		
		readyToReadData(funTroubleVariableService);
	}
	
	public FunTroubleBeckhoffService() {
	}
}
