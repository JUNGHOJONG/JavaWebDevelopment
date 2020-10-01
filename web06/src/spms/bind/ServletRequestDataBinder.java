package spms.bind;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

public class ServletRequestDataBinder {
	
	public static Object bind(ServletRequest request, String dataName,
			Class<?> dataType) throws Exception{
				
		if(isPrimitiveType(dataType)) {
			return createValueObject(dataType, request.getParameter(dataName));
		}
		Set<String> paraNames = request.getParameterMap().keySet();
		Object dataObject = dataType.newInstance();
		Method method = null;
		for(String paraName : paraNames) {
			method = findSetter(dataType, paraName);
			if(method != null) {
				method.invoke(dataObject, createValueObject(
						method.getParameterTypes()[0], request.getParameter(paraName)));
			}
		}
		return dataObject;
	}
	
	private static boolean isPrimitiveType(Class<?> dataType) {
		if(dataType.getName().equals("int") || dataType == Integer.class ||
			dataType.getName().equals("long") || dataType == Long.class ||
			dataType.getName().equals("float") || dataType == Float.class ||
			dataType.getName().equals("double") || dataType == Double.class ||
			dataType.getName().equals("boolean") || dataType == Boolean.class ||
			dataType == Date.class || dataType == String.class) {
			return true;
		}
		return false;
	}
	
	private static Object createValueObject(Class<?> dataType, String data) {
		if(dataType.getName().equals("int") || dataType == Integer.class) {
			return new Integer(data);
		}else if(dataType.getName().equals("long") || dataType == Long.class) {
			return new Long(data);
		}else if(dataType.getName().equals("float") || dataType == Float.class) {
			return new Float(data);
		}else if(dataType.getName().equals("double") || dataType == Double.class) {
			return new Double(data);
		}else if(dataType.getName().equals("boolean") || dataType == Boolean.class) {
			return new Boolean(data);
		}else if(dataType == Date.class) {
			return java.sql.Date.valueOf(data);
		}else {
			return data;
		}
	}
	
	private static Method findSetter(Class<?> dataType, String paraName) {
		Method[] methods = dataType.getMethods();
		for(Method method : methods) {
			if(!method.getName().startsWith("set")) continue;
			String properName = method.getName().substring(3);
			if(properName.toLowerCase().equals(paraName.toLowerCase())) {
				return method;
			}
		}
		return null;
	}
}
