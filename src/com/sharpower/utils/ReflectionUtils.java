package com.sharpower.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ����� Utils �����
 * �ṩ����˽�б���, ��ȡ�������� Class, ��ȡ������Ԫ�����Ե� Utils ����
 * @author Administrator
 *
 */
public class ReflectionUtils {

	
	/**
	 * ͨ����, ��ö��� Class ʱ�����ĸ���ķ��Ͳ��������
	 * ��: public EmployeeDao extends BaseDao<Employee, String>
	 * @param clazz
	 * @param index
	 * @return
	 */
	public static Class getSuperClassGenricType(Class clazz, int index){
		Type genType = clazz.getGenericSuperclass();
		
		if(!(genType instanceof ParameterizedType)){
			return Object.class;
		}
		
		Type [] params = ((ParameterizedType)genType).getActualTypeArguments();
		
		if(index >= params.length || index < 0){
			return Object.class;
		}
		
		if(!(params[index] instanceof Class)){
			return Object.class;
		}
		
		return (Class) params[index];
	}
	
	/**
	 * ͨ����, ��� Class �����������ĸ���ķ��Ͳ�������
	 * ��: public EmployeeDao extends BaseDao<Employee, String>
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T> Class<T> getSuperGenericType(Class clazz){
		return getSuperClassGenricType(clazz, 0);
	}
	
	/**
	 * ͨ���䣬��ȡָ�������ķ���ֵ�ķ�������
	 * @return
	 */
	public static Class getReturnGenericType(Object object, String methodName, Class<?>[] parameterTypes) {
		
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		Type type = method.getGenericReturnType();
		
		if ( type instanceof ParameterizedType ){
			Type[] types = ((ParameterizedType) type).getActualTypeArguments();// ���������б�
			type = types[0];
			return (Class) type;
			}
				
		return null;
	}
	
	/**
	 * ѭ������ת��, ��ȡ����� DeclaredMethod
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes){
		
		for(Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()){
			try {
				//superClass.getMethod(methodName, parameterTypes);
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				//Method ���ڵ�ǰ�ඨ��, ��������ת��
			}
			//..
		}
		
		return null;
	}
	
	/**
	 * ʹ filed ��Ϊ�ɷ���
	 * @param field
	 */
	public static void makeAccessible(Field field){
		if(!Modifier.isPublic(field.getModifiers())){
			field.setAccessible(true);
		}
	}
	
	/**
	 * ѭ������ת��, ��ȡ����� DeclaredField
	 * @param object
	 * @param filedName
	 * @return
	 */
	public static Field getDeclaredField(Object object, String filedName){
		
		for(Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()){
			try {
				return superClass.getDeclaredField(filedName);
			} catch (NoSuchFieldException e) {
				//Field ���ڵ�ǰ�ඨ��, ��������ת��
			}
		}
		return null;
	}
	
	/**
	 * ֱ�ӵ��ö��󷽷�, ��������η�(private, protected)
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @param parameters
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static Object invokeMethod(Object object, String methodName, Class<?> [] parameterTypes,
			Object [] parameters) throws InvocationTargetException{
		
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		
		if(method == null){
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}
		
		method.setAccessible(true);
		
		try {
			return method.invoke(object, parameters);
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	
	/**
	 * ֱ�����ö�������ֵ, ���� private/protected ���η�, Ҳ������ setter
	 * @param object
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValue(Object object, String fieldName, Object value){
		Field field = getDeclaredField(object, fieldName);
		
		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		
		makeAccessible(field);
		
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ֱ�Ӷ�ȡ���������ֵ, ���� private/protected ���η�, Ҳ������ getter
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object object, String fieldName){
		Field field = getDeclaredField(object, fieldName);
		
		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		
		makeAccessible(field);
		
		Object result = null;
		
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
