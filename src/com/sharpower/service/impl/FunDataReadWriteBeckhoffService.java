package com.sharpower.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharpower.entity.Variable;
import com.sharpower.scada.exception.AdsException;
import com.sharpower.service.VariableTypeService;
import com.sharpower.utils.AddressConvertUtils;
import com.sharpower.utils.ByteUtil;

import de.beckhoff.jni.JNIByteBuffer;
import de.beckhoff.jni.tcads.AdsCallDllFunction;
import de.beckhoff.jni.tcads.AmsAddr;

public class FunDataReadWriteBeckhoffService {
	private static final int ERROR_CODE_LENGTH = 4;
	private static final int READ_DATA_LENGTH  = 4;
	private static final int WRITE_TYPE_LENGTH = 16;
	
	private VariableTypeService variableTypeService;
	
	public void setVariableTypeService(VariableTypeService variableTypeService) {
		this.variableTypeService = variableTypeService;
	}
	
	private  List<Variable> booleanValNames;
	private  List<Variable> byteValNames;
	private  List<Variable> shortValNames;
	private  List<Variable> intValNames;
	private  List<Variable> floatValNames;
	private  List<Variable> longValNames;
	private  List<Variable> doubleValNames;

	private  long lj_indexOffset_boolean;
	private  long lj_lengthRead_boolean;
	private  long lj_lengthWrite_boolean;
	private  JNIByteBuffer lj_pDataWrite_boolean;

	private  long lj_indexOffset_byte;
	private  long lj_lengthRead_byte;
	private  long lj_lengthWrite_byte;
	private  JNIByteBuffer lj_pDataWrite_byte;

	private  long lj_indexOffset_short;
	private  long lj_lengthRead_short;
	private  long lj_lengthWrite_short;
	private  JNIByteBuffer lj_pDataWrite_short;

	private  long lj_indexOffset_int;
	private  long lj_lengthRead_int;
	private  long lj_lengthWrite_int;
	private  JNIByteBuffer lj_pDataWrite_int;

	private  long lj_indexOffset_float;
	private  long lj_lengthRead_float;
	private  long lj_lengthWrite_float;
	private  JNIByteBuffer lj_pDataWrite_float;

	private  long lj_indexOffset_long;
	private  long lj_lengthRead_long;
	private  long lj_lengthWrite_long;
	private  JNIByteBuffer lj_pDataWrite_long;

	private  long lj_indexOffset_double;
	private  long lj_lengthRead_double;
	private  long lj_lengthWrite_double;
	private  JNIByteBuffer lj_pDataWrite_double;
	
	private static boolean initFlag=false;
	
	private void init(){
		
		booleanValNames = variableTypeService.getEntity(1).getVals();
		byteValNames = variableTypeService.getEntity(2).getVals();
		shortValNames= variableTypeService.getEntity(3).getVals();
		intValNames= variableTypeService.getEntity(4).getVals();
		floatValNames= variableTypeService.getEntity(5).getVals();
		longValNames= variableTypeService.getEntity(6).getVals();
		doubleValNames= variableTypeService.getEntity(7).getVals();
		
		readyToReadBooleanData(booleanValNames);
		readyToReadByteData(byteValNames);
		readyToReadShortData(shortValNames);
		readyToReadIntData(intValNames);
		readyToReadFloatData(floatValNames);
		readyToReadLongData(longValNames);
		readyToReadDoubleData(doubleValNames);
		
		initFlag=true;
		
	}

	private void getWriteJNIByteBuffer(int size, List<Variable> names, long lj_lengthWrite,
			JNIByteBuffer lj_pDataWrite) {

		byte[] bytes = new byte[(int) lj_lengthWrite];
		byte[] bytes1 = new byte[0];

		int destPos = 0;

		int indexGroup;
		int indexOffset;
		int rLength;
		int wLength;

		for (Variable name : names) {
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

		for (Variable name : names) {
			bytes1 = ByteUtil.getBytes(name.getName());
			System.arraycopy(bytes1, 0, bytes, destPos, bytes1.length);
			destPos = destPos + bytes1.length;
		}

		lj_pDataWrite.setByteArray(bytes, false);
	}
	/**
	 * 
	 * @param names
	 * @param lj_indexOffset
	 * @return
	 */
	private long getLengWrite(List<Variable> names, long lj_indexOffset) {
		long lj_lengthWrite = 0;

		for (Variable name : names) {
			lj_lengthWrite = lj_lengthWrite + name.getName().length();
		}

		lj_lengthWrite = WRITE_TYPE_LENGTH * lj_indexOffset + lj_lengthWrite;
		return lj_lengthWrite;
	}

	private void readyToReadBooleanData(List<Variable> booleanValNames2) {
		if (booleanValNames2 == null || booleanValNames2.size() == 0) {
			return;
		}

		int size = 1;
		List<Variable> names = booleanValNames;

		long lj_indexOffset = names.size();

		long lj_lengthRead = (ERROR_CODE_LENGTH + READ_DATA_LENGTH + size) * lj_indexOffset;

		long lj_lengthWrite = getLengWrite(names, lj_indexOffset);

		JNIByteBuffer lj_pDataWrite = new JNIByteBuffer((int) lj_lengthWrite);

		getWriteJNIByteBuffer(size, names, lj_lengthWrite, lj_pDataWrite);

		lj_indexOffset_boolean = lj_indexOffset;
		lj_lengthRead_boolean = lj_lengthRead;
		lj_lengthWrite_boolean = lj_lengthWrite;
		lj_pDataWrite_boolean = lj_pDataWrite;

	}

	private void readyToReadByteData(List<Variable> byteValNames2) {
		if (byteValNames2 == null || byteValNames2.size() == 0) {
			return;
		}

		int size = 1;
		List<Variable> names = byteValNames;

		long lj_indexOffset = names.size();

		long lj_lengthRead = (ERROR_CODE_LENGTH + READ_DATA_LENGTH + size) * lj_indexOffset;

		long lj_lengthWrite = getLengWrite(names, lj_indexOffset);

		JNIByteBuffer lj_pDataWrite = new JNIByteBuffer((int) lj_lengthWrite);

		getWriteJNIByteBuffer(size, names, lj_lengthWrite, lj_pDataWrite);

		lj_indexOffset_byte = lj_indexOffset;
		lj_lengthRead_byte = lj_lengthRead;
		lj_lengthWrite_byte = lj_lengthWrite;
		lj_pDataWrite_byte = lj_pDataWrite;

	}

	private void readyToReadShortData(List<Variable> shortValNames2) {
		if (shortValNames2 == null || shortValNames2.size() == 0) {
			return;
		}

		int size = 2;
		List<Variable> names = shortValNames;

		long lj_indexOffset = names.size();

		long lj_lengthRead = (ERROR_CODE_LENGTH + READ_DATA_LENGTH + size) * lj_indexOffset;

		long lj_lengthWrite = getLengWrite(names, lj_indexOffset);

		JNIByteBuffer lj_pDataWrite = new JNIByteBuffer((int) lj_lengthWrite);

		getWriteJNIByteBuffer(size, names, lj_lengthWrite, lj_pDataWrite);

		lj_indexOffset_short = lj_indexOffset;
		lj_lengthRead_short = lj_lengthRead;
		lj_lengthWrite_short = lj_lengthWrite;
		lj_pDataWrite_short = lj_pDataWrite;

	}

	private void readyToReadIntData(List<Variable> intValNames2) {
		if (intValNames2 == null || intValNames2.size() == 0) {
			return;
		}

		int size = 4;
		List<Variable> names = intValNames;

		long lj_indexOffset = names.size();

		long lj_lengthRead = (ERROR_CODE_LENGTH + READ_DATA_LENGTH + size) * lj_indexOffset;

		long lj_lengthWrite = getLengWrite(names, lj_indexOffset);

		JNIByteBuffer lj_pDataWrite = new JNIByteBuffer((int) lj_lengthWrite);

		getWriteJNIByteBuffer(size, names, lj_lengthWrite, lj_pDataWrite);

		lj_indexOffset_int = lj_indexOffset;
		lj_lengthRead_int = lj_lengthRead;
		lj_lengthWrite_int = lj_lengthWrite;
		lj_pDataWrite_int = lj_pDataWrite;

	}

	private void readyToReadFloatData(List<Variable> floatValNames2) {
		if (floatValNames2 == null || floatValNames2.size() == 0) {
			return;
		}

		int size = 4;
		List<Variable> names = floatValNames;

		long lj_indexOffset = names.size();

		long lj_lengthRead = (ERROR_CODE_LENGTH + READ_DATA_LENGTH + size) * lj_indexOffset;

		long lj_lengthWrite = getLengWrite(names, lj_indexOffset);

		JNIByteBuffer lj_pDataWrite = new JNIByteBuffer((int) lj_lengthWrite);

		getWriteJNIByteBuffer(size, names, lj_lengthWrite, lj_pDataWrite);

		lj_indexOffset_float = lj_indexOffset;
		lj_lengthRead_float = lj_lengthRead;
		lj_lengthWrite_float = lj_lengthWrite;
		lj_pDataWrite_float = lj_pDataWrite;

	}

	private void readyToReadLongData(List<Variable> longValNames2) {
		if (longValNames2 == null || longValNames2.size() == 0) {
			return;
		}

		int size = 1;
		List<Variable> names = longValNames;

		long lj_indexOffset = names.size();

		long lj_lengthRead = (ERROR_CODE_LENGTH + READ_DATA_LENGTH + size) * lj_indexOffset;

		long lj_lengthWrite = getLengWrite(names, lj_indexOffset);

		JNIByteBuffer lj_pDataWrite = new JNIByteBuffer((int) lj_lengthWrite);

		getWriteJNIByteBuffer(size, names, lj_lengthWrite, lj_pDataWrite);

		lj_indexOffset_long = lj_indexOffset;
		lj_lengthRead_long = lj_lengthRead;
		lj_lengthWrite_long = lj_lengthWrite;
		lj_pDataWrite_long = lj_pDataWrite;

	}

	private void readyToReadDoubleData(List<Variable> doubleValNames2) {
		if (doubleValNames2 == null || doubleValNames2.size() == 0) {
			return;
		}

		int size = 8;
		List<Variable> names = doubleValNames;

		long lj_indexOffset = names.size();

		long lj_lengthRead = (ERROR_CODE_LENGTH + READ_DATA_LENGTH + size) * lj_indexOffset;

		long lj_lengthWrite = getLengWrite(names, lj_indexOffset);

		JNIByteBuffer lj_pDataWrite = new JNIByteBuffer((int) lj_lengthWrite);

		getWriteJNIByteBuffer(size, names, lj_lengthWrite, lj_pDataWrite);

		lj_indexOffset_double = lj_indexOffset;
		lj_lengthRead_double = lj_lengthRead;
		lj_lengthWrite_double = lj_lengthWrite;
		lj_pDataWrite_double = lj_pDataWrite;

	}
	
	/**
	 * 从ADS返回的数据块中解析数据
	 * @param clazz 数据块中的数据类型
	 * @param lj_pDataRead ADS返回的数据块
	 * @return 返回变量和值得键值对
	 */
	@SuppressWarnings("unchecked")
	private <E> Map<Variable, E> getData(Class<E> clazz, JNIByteBuffer lj_pDataRead ) {
		//读取变量的数量
		long lj_indexOffset;
		//变量名List
		List<Variable> valnames;
		//数据类型的字节数
		int size = 0;
		//返回值
		Map<Variable, E> data = new HashMap<>();
		
		//确定读取变量的数量、变量名的List、数据类型的字节数
		if (clazz == Boolean.class) {
			lj_indexOffset = lj_indexOffset_boolean;
			valnames = booleanValNames;
			size = 1;
		} else if (clazz == Byte.class) {
			lj_indexOffset = lj_indexOffset_byte;
			valnames = byteValNames;
			size = 1;
		} else if (clazz == Short.class) {
			lj_indexOffset = lj_indexOffset_short;
			valnames = shortValNames;
			size = 2;
		} else if (clazz == Integer.class) {
			lj_indexOffset = lj_indexOffset_int;
			valnames = intValNames;
			size = 4;
		} else if (clazz == Float.class) {
			lj_indexOffset = lj_indexOffset_float;
			valnames = floatValNames;
			size = 4;
		} else if (clazz == Long.class) {
			lj_indexOffset = lj_indexOffset_long;
			valnames = longValNames;
			size = 8;
		} else if (clazz == Double.class) {
			lj_indexOffset = lj_indexOffset_double;
			valnames = doubleValNames;
			size = 8;
		} else {
			return data;
		}
		
		//ADS数据块转换为字节数组
		byte[] bytes = lj_pDataRead.getByteArray();
		
		//变量读取的错误码
		int errCode = 0;
		//读取错误的变量数量
		int errCount = 0;
		//变量值
		E result = null;
		
		//读取字节数组的临时变量
		int destPos = 0;
		
		//读取错误码的字节数组
		byte[] bytes1 = new byte[ERROR_CODE_LENGTH];
		//读取变量值的字节数组
		byte[] bytes2 = new byte[size];

		for (int i = 0; i < lj_indexOffset; i++) {
			//获取错误码字节数组
			System.arraycopy(bytes, destPos, bytes1, 0, ERROR_CODE_LENGTH);
			destPos = destPos + ERROR_CODE_LENGTH + READ_DATA_LENGTH;
			
			//转换字节数组为整型值
			errCode = ByteUtil.getInteger(bytes1);
			//如果错误码为0则读取变量值
			if (errCode == 0) {
				System.arraycopy(bytes, (int) (8 * lj_indexOffset + size * i - size * errCount), bytes2, 0, size);
				
				if (clazz == Boolean.class) {
					result = (E) (Boolean) (ByteUtil.getBoolean(bytes2));

				} else if (clazz == Byte.class) {
					result = (E) (Byte) (ByteUtil.getByte(bytes2));

				} else if (clazz == Integer.class) {
					result = (E) (Integer) (ByteUtil.getInteger(bytes2));

				} else if (clazz == Float.class) {
					result = (E) (Float) (ByteUtil.getFloat(bytes2));

				} else if (clazz == Long.class) {
					result = (E) (Long) (ByteUtil.getLong(bytes2));

				} else if (clazz == Double.class) {
					result = (E) (Double) (ByteUtil.getDouble(bytes2));
				}

			} else {
				++errCount;
				result = null;
			}

			data.put(valnames.get(i), result);

		}
		return data;
	}
	
	private <E> Map<Variable, E> readData(String sFunAddress, Class<E> clazz) throws AdsException {
		if(initFlag==false){
			init();
		}
		
		Map<Variable, E> data = new HashMap<Variable, E>();

		AmsAddr lj_AmsAddr = AddressConvertUtils.string2AmsAddr(sFunAddress);

		long lj_indexOffset;
		long lj_lengthRead;
		JNIByteBuffer lj_pDataRead;
		long lj_lengthWrite;
		JNIByteBuffer lj_pDataWrite;

		if (clazz == Boolean.class) {
			lj_indexOffset = lj_indexOffset_boolean;
			lj_lengthRead = lj_lengthRead_boolean;
			// Ϊ���̶߳�ȡ���ʱ�̰߳�ȫ���Ǵ˴���ҪΪ��̬����������Ϊÿ�ζ�ȡ�����ı������ٲ�ͬ�ĵ�ַ�ռ䡣
			lj_pDataRead = new JNIByteBuffer((int) lj_lengthRead);
			lj_lengthWrite = lj_lengthWrite_boolean;
			lj_pDataWrite = lj_pDataWrite_boolean;
		} else if (clazz == Byte.class) {
			lj_indexOffset = lj_indexOffset_byte;
			lj_lengthRead = lj_lengthRead_byte;
			lj_pDataRead = new JNIByteBuffer((int) lj_lengthRead);
			lj_lengthWrite = lj_lengthWrite_byte;
			lj_pDataWrite = lj_pDataWrite_byte;
		} else if (clazz == Short.class) {
			lj_indexOffset = lj_indexOffset_short;
			lj_lengthRead = lj_lengthRead_short;
			lj_pDataRead = new JNIByteBuffer((int) lj_lengthRead);
			lj_lengthWrite = lj_lengthWrite_short;
			lj_pDataWrite = lj_pDataWrite_short;
		} else if (clazz == Integer.class) {
			lj_indexOffset = lj_indexOffset_int;
			lj_lengthRead = lj_lengthRead_int;
			lj_pDataRead = new JNIByteBuffer((int) lj_lengthRead);
			lj_lengthWrite = lj_lengthWrite_int;
			lj_pDataWrite = lj_pDataWrite_int;
		} else if (clazz == Float.class) {
			lj_indexOffset = lj_indexOffset_float;
			lj_lengthRead = lj_lengthRead_float;
			lj_pDataRead = new JNIByteBuffer((int) lj_lengthRead);
			lj_lengthWrite = lj_lengthWrite_float;
			lj_pDataWrite = lj_pDataWrite_float;
		} else if (clazz == Long.class) {
			lj_indexOffset = lj_indexOffset_long;
			lj_lengthRead = lj_lengthRead_long;
			lj_pDataRead = new JNIByteBuffer((int) lj_lengthRead);
			lj_lengthWrite = lj_lengthWrite_long;
			lj_pDataWrite = lj_pDataWrite_long;
		} else if (clazz == Double.class) {
			lj_indexOffset = lj_indexOffset_double;
			lj_lengthRead = lj_lengthRead_double;
			lj_pDataRead = new JNIByteBuffer((int) lj_lengthRead);
			lj_lengthWrite = lj_lengthWrite_double;
			lj_pDataWrite = lj_pDataWrite_double;
		} else {
			return null;
		}

		AdsCallDllFunction.adsPortOpen();

		Long err = AdsCallDllFunction.adsSyncReadWriteReq(lj_AmsAddr, 0xF082, lj_indexOffset, lj_lengthRead,
				lj_pDataRead, lj_lengthWrite, lj_pDataWrite);

		AdsCallDllFunction.adsPortClose();

		if (err == 0) {
			data = getData(clazz, lj_pDataRead);

			return data;

		} else {
			throw new AdsException("ADS Error,ErrorCode:" + err);
		}

	}

	public  Map<Variable,Object> readDataAll(String sFunAddress) throws AdsException {
		if(initFlag==false){
			init();
		}
		
		Map<Variable,Object> dataAll = new HashMap<>();
		
		Map<Variable, Boolean> dataBoolean;
		Map<Variable, Byte> dataByte;
		Map<Variable, Short> dataShort;
		Map<Variable, Integer> dataInteger;
		Map<Variable, Float> dataFloat;
		Map<Variable, Long> dataLong;
		Map<Variable, Double> dataDouble;
		
		if (booleanValNames.size()>0){
			dataBoolean = readData(sFunAddress, Boolean.class);
			dataAll.putAll(dataBoolean);
		}
		
		if(byteValNames.size()>0){
			dataByte = readData(sFunAddress, Byte.class);
			dataAll.putAll(dataByte);
		}
		
		if(shortValNames.size()>0){
			dataShort = readData(sFunAddress, Short.class);
			dataAll.putAll(dataShort);
		}
		
		if(intValNames.size()>0){
			dataInteger = readData(sFunAddress, Integer.class);
			dataAll.putAll(dataInteger);
		}
		
		if(floatValNames.size()>0) {
			dataFloat = readData(sFunAddress, Float.class);
			dataAll.putAll(dataFloat);
		}
		
		if (longValNames.size()>0) {
			dataLong = readData(sFunAddress, Long.class);
			dataAll.putAll(dataLong);
		}
		
		if (doubleValNames.size()>0) {
			dataDouble = readData(sFunAddress, Double.class);
			dataAll.putAll(dataDouble);
		}
		
		return dataAll;
	}


}