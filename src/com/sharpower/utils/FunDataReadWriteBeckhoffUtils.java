package com.sharpower.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharpower.scada.exception.AdsException;
import com.sharpower.service.VariableTypeService;

import de.beckhoff.jni.JNIByteBuffer;
import de.beckhoff.jni.tcads.AdsCallDllFunction;
import de.beckhoff.jni.tcads.AmsAddr;

public class FunDataReadWriteBeckhoffUtils {
	private static final int ERROR_CODE_LENGTH = 4;
	private static final int READ_DATA_LENGTH  = 4;
	private static final int WRITE_TYPE_LENGTH = 16;
	
	private VariableTypeService variableTypeService;

	public VariableTypeService getVariableTypeService() {
		return variableTypeService;
	}

	public void setVariableTypeService(VariableTypeService variableTypeService) {
		this.variableTypeService = variableTypeService;
	}

	private static List<String> booleanValNames;
	private static List<String> byteValNames;
	private static List<String> shortValNames;
	private static List<String> intValNames;
	private static List<String> floatValNames;
	private static List<String> longValNames;
	private static List<String> doubleValNames;

	private static long lj_indexOffset_boolean;
	private static long lj_lengthRead_boolean;
	private static long lj_lengthWrite_boolean;
	private static JNIByteBuffer lj_pDataWrite_boolean;

	private static long lj_indexOffset_byte;
	private static long lj_lengthRead_byte;
	private static long lj_lengthWrite_byte;
	private static JNIByteBuffer lj_pDataWrite_byte;

	private static long lj_indexOffset_short;
	private static long lj_lengthRead_short;
	private static long lj_lengthWrite_short;
	private static JNIByteBuffer lj_pDataWrite_short;

	private static long lj_indexOffset_int;
	private static long lj_lengthRead_int;
	private static long lj_lengthWrite_int;
	private static JNIByteBuffer lj_pDataWrite_int;

	private static long lj_indexOffset_float;
	private static long lj_lengthRead_float;
	private static long lj_lengthWrite_float;
	private static JNIByteBuffer lj_pDataWrite_float;

	private static long lj_indexOffset_long;
	private static long lj_lengthRead_long;
	private static long lj_lengthWrite_long;
	private static JNIByteBuffer lj_pDataWrite_long;

	private static long lj_indexOffset_double;
	private static long lj_lengthRead_double;
	private static long lj_lengthWrite_double;
	private static JNIByteBuffer lj_pDataWrite_double;

	static {
		booleanValNames = new ArrayList<String>();
		booleanValNames.add("Main.Booltest1");
		booleanValNames.add("Main.Booltest2");
		booleanValNames.add("Main.Booltest3");
		booleanValNames.add("Main.Booltest4");
		booleanValNames.add("Main.Booltest5");
		booleanValNames.add("Main.Booltest6");
		booleanValNames.add("Main.Booltest7");
		booleanValNames.add("Main.Booltest8");
		booleanValNames.add("Main.Booltest9");

		floatValNames = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			floatValNames.add("main.singletest" + (i + 1));

		}

		readyToReadBooleanData(booleanValNames);
		readyToReadByteData(byteValNames);
		readyToReadShortData(shortValNames);
		readyToReadIntData(intValNames);
		readyToReadFloatData(floatValNames);
		readyToReadLongData(longValNames);
		readyToReadDoubleData(doubleValNames);
	}

	private FunDataReadWriteBeckhoffUtils() {

	}

	private static void getWriteJNIByteBuffer(int size, List<String> names, long lj_lengthWrite,
			JNIByteBuffer lj_pDataWrite) {

		byte[] bytes = new byte[(int) lj_lengthWrite];
		byte[] bytes1 = new byte[0];

		int destPos = 0;

		int indexGroup;
		int indexOffset;
		int rLength;
		int wLength;

		for (String name : names) {
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

			wLength = name.length();
			bytes1 = ByteUtil.getBytes(wLength);
			System.arraycopy(bytes1, 0, bytes, destPos, bytes1.length);
			destPos = destPos + bytes1.length;
		}

		for (String name : names) {
			bytes1 = ByteUtil.getBytes(name);
			System.arraycopy(bytes1, 0, bytes, destPos, bytes1.length);
			destPos = destPos + bytes1.length;
		}

		lj_pDataWrite.setByteArray(bytes, false);
	}

	private static long getLengWrite(List<String> names, long lj_indexOffset) {
		long lj_lengthWrite = 0;

		for (String name : names) {
			lj_lengthWrite = lj_lengthWrite + name.length();
		}

		lj_lengthWrite = WRITE_TYPE_LENGTH * lj_indexOffset + lj_lengthWrite;
		return lj_lengthWrite;
	}

	private static void readyToReadBooleanData(List<String> valNames) {
		if (valNames == null || valNames.size() == 0) {
			return;
		}

		int size = 1;
		List<String> names = booleanValNames;

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

	private static void readyToReadByteData(List<String> valNames) {
		if (valNames == null || valNames.size() == 0) {
			return;
		}

		int size = 1;
		List<String> names = byteValNames;

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

	private static void readyToReadShortData(List<String> valNames) {
		if (valNames == null || valNames.size() == 0) {
			return;
		}

		int size = 2;
		List<String> names = shortValNames;

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

	private static void readyToReadIntData(List<String> valNames) {
		if (valNames == null || valNames.size() == 0) {
			return;
		}

		int size = 4;
		List<String> names = intValNames;

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

	private static void readyToReadFloatData(List<String> valNames) {
		if (valNames == null || valNames.size() == 0) {
			return;
		}

		int size = 4;
		List<String> names = floatValNames;

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

	private static void readyToReadLongData(List<String> valNames) {
		if (valNames == null || valNames.size() == 0) {
			return;
		}

		int size = 1;
		List<String> names = longValNames;

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

	private static void readyToReadDoubleData(List<String> valNames) {
		if (valNames == null || valNames.size() == 0) {
			return;
		}

		int size = 8;
		List<String> names = doubleValNames;

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

	public static <E> Map<String, E> readData(String sFunAddress, Class<E> clazz) throws AdsException {
		Map<String, E> data = new HashMap<String, E>();

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
			getData(clazz, lj_pDataRead, data);

			return data;

		} else {
			throw new AdsException("ADS Error,ErrorCode:" + err);
		}

	}

	@SuppressWarnings("unchecked")
	private static <E> void getData(Class<E> clazz, JNIByteBuffer lj_pDataRead, Map<String, E> data) {
		long lj_indexOffset;
		List<String> valnames;
		int size = 0;

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
			return;
		}

		byte[] bytes = lj_pDataRead.getByteArray();

		int errCode = 0;
		int errCount = 0;
		E result = null;

		int destPos = 0;

		byte[] bytes1 = new byte[ERROR_CODE_LENGTH];
		byte[] bytes2 = new byte[size];

		for (int i = 0; i < lj_indexOffset; i++) {
			System.arraycopy(bytes, destPos, bytes1, 0, ERROR_CODE_LENGTH);
			destPos = destPos + ERROR_CODE_LENGTH + READ_DATA_LENGTH;

			errCode = ByteUtil.getInt(bytes1);

			if (errCode == 0) {

				System.arraycopy(bytes, (int) (8 * lj_indexOffset + size * i - size * errCount), bytes2, 0, size);

				if (clazz == Boolean.class) {
					result = (E) (Boolean) (ByteUtil.getBoolean(bytes2));

				} else if (clazz == Byte.class) {
					result = (E) (Byte) (ByteUtil.getByte(bytes2));

				} else if (clazz == Integer.class) {
					result = (E) (Integer) (ByteUtil.getInt(bytes2));

				} else if (clazz == Float.class) {
					result = (E) (Float) (ByteUtil.getFloat(bytes2));

				} else if (clazz == Long.class) {
					result = (E) (Long) (ByteUtil.getLong(bytes2));

				} else if (clazz == Double.class) {
					result = (E) (Double) (ByteUtil.getDouble(bytes2));
				}

			} else {
				errCount++;
				result = null;
			}

			data.put(valnames.get(i), result);

		}
	}

}