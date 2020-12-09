package exam.test11;

import java.text.SimpleDateFormat;
import java.util.Properties;

public class TireFactory {
	
	public static Tire createTire(String maker) {
		if (maker.equals("Kumho")) {
			return createKumhoTire();
		}
		return createHankookTire();
	}
	
	private static Tire createKumhoTire() {
		Tire tire = new Tire();
		tire.setMaker("Kumho");
		
		Properties specProp = new Properties();
		specProp.setProperty("width", "65");
		specProp.setProperty("ratio", "30");
		specProp.setProperty("rim.diameter", "13");
		tire.setSpec(specProp);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			tire.setCreatedDate(dateFormat.parse("2014-5-5"));	
		} catch (Exception e) {
			
		}
		return tire;
	}
	
	private static Tire createHankookTire() {
		Tire tire = new Tire();
		tire.setMaker("Hankook");
		
		Properties specProp = new Properties();
		specProp.setProperty("width", "100");
		specProp.setProperty("ratio", "60");
		specProp.setProperty("rim.diameter", "24");
		tire.setSpec(specProp);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			tire.setCreatedDate(dateFormat.parse("2014-3-21"));	
		} catch (Exception e) {
			
		}
		return tire;
	}

}
