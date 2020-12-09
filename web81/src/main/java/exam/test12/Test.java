package exam.test12;

import org.springframework.context.support.
ClassPathXmlApplicationContext;

public class Test {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = 
				new ClassPathXmlApplicationContext("exam/test12/beans.xml");
		
		TireFactory tireFactory = (TireFactory) ctx.getBean("tireFactory");
		if (tireFactory != null) {
			System.out.println("tireFactory exist");
		}
		
		Tire tire1 = (Tire) ctx.getBean("kumhoTire"); 
		Tire tire2 = (Tire) ctx.getBean("hankookTire");
		Tire tire3 = (Tire) ctx.getBean("kumhoTire");
		
		System.out.println(tire1);
		System.out.println(tire2);
		System.out.println(tire3);
		
		if (tire1 != tire2) {
			System.out.println("tire1 != tire2");
		}
		
		if (tire1 == tire3) {
			System.out.println("tire1 == tire3");
		}
	}

}