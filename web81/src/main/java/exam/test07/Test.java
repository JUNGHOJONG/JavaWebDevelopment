package exam.test07;

import org.springframework.context.support.
ClassPathXmlApplicationContext;

public class Test {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = 
				new ClassPathXmlApplicationContext("exam/test07/beans.xml");
		
		Engine engine = (Engine) ctx.getBean("engine1");
		System.out.println(engine);
		
		engine.setCc(3000);
		System.out.println(engine);
		
		for (int i=1; i<=4; i++) {
			Car car1 = (Car) ctx.getBean("car" + i);
			System.out.println(car1.toString());	
		}	
	}

}
