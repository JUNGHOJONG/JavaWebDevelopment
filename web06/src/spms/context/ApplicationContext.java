package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.reflections.Reflections;

import spms.annotation.Component;

public class ApplicationContext {
	Hashtable<String, Object> hashTable = new Hashtable<String, Object>();
	
	public Object getBean(String key) {
		return hashTable.get(key); 
	}
	
	public ApplicationContext(String propertiesPath) throws Exception{
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));
		
		prepareObjects(props);
		prepareAnnotationObjects();
		injectDependency();
	}
	
	private void prepareObjects(Properties props) throws Exception{
		Context context = new InitialContext();
		for(Object object : props.keySet()) {
			String key = (String) object;
			if(key.startsWith("jndi")) {
				hashTable.put(key, context.lookup(props.getProperty(key)));
			}else {
				hashTable.put(key, Class.forName(props.getProperty(key)).newInstance());
			}
		}
	}

	private void prepareAnnotationObjects() throws Exception{
		Reflections reflector = new Reflections("");
		Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
		for(Class<?> clazz : list) {
			String key = clazz.getAnnotation(Component.class).value();
			System.out.println(key);
			Object value = clazz.newInstance();
			System.out.println(value);
			hashTable.put(key, value);
		}
	}
	
	private void injectDependency() throws Exception{
		for(Object object : hashTable.keySet()) {
			String key = (String) object;
			if(!key.startsWith("jndi")) {
				callSetter(hashTable.get(key));
			}
		}
	}
	
	private void callSetter(Object object) throws Exception{
		for(Method method : object.getClass().getMethods()) {
			if(method.getName().startsWith("set")) {
				Object dependency = getObjectOfType(method.getParameterTypes()[0]);
				if(dependency != null) method.invoke(object, dependency);
			}
		}
	}
	
	private Object getObjectOfType(Class<?> type) {
		for(Object object : hashTable.values()) {
			if(type.isInstance(object)) {
				return object;
			}
		}
		return null;
	}
	
}
