package exam.test14;

import org.springframework.context.support.
ClassPathXmlApplicationContext;

public class Test {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = 
				new ClassPathXmlApplicationContext("exam/test14/beans.xml");
			
		// Singleton 방식
		Engine hyundaiEngine1 = (Engine) ctx.getBean("hyundaiEngine");
		Engine hyundaiEngine2 = (Engine) ctx.getBean("hyundaiEngine");
		Engine hyundaiEngine3 = (Engine) ctx.getBean("hyundaiEngine");
		System.out.println(hyundaiEngine1.hashCode());
		System.out.println(hyundaiEngine2.hashCode());
		System.out.println(hyundaiEngine3.hashCode());
		
		// Prototype 방식
		Engine kiaEngine1 = (Engine) ctx.getBean("kiaEngine");
		Engine kiaEngine2 = (Engine) ctx.getBean("kiaEngine");
		Engine kiaEngine3 = (Engine) ctx.getBean("kiaEngine");
		System.out.println(kiaEngine1.hashCode());
		System.out.println(kiaEngine2.hashCode());
		System.out.println(kiaEngine3.hashCode());
	}

}
