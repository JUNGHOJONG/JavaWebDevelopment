package exam.test04;

import org.springframework.context.support.
ClassPathXmlApplicationContext;

public class Test {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = 
				new ClassPathXmlApplicationContext("exam/test04/beans.xml");
		
		for (int i=1; i<=5; i++) {
			System.out.println(ctx.getBean("score" + i));
		}
	}

}
