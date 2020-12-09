package exam.test14;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Properties;

public class Tire {
	String maker;
	Properties spec;
	Date createdDate;
	
	public String getMaker() {
		return maker;
	}
	
	public void setMaker(String maker) {
		this.maker = maker;
	}
	
	public Properties getSpec() {
		return spec;
	}
	
	public void setSpec(Properties spec) {
		this.spec = spec;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Override
	public String toString() {
		StringBuffer specInfo = new StringBuffer();
		if (spec != null) {
			for (Entry<Object, Object> entry : spec.entrySet()) {
				specInfo.append(entry.getKey() + ":" + entry.getValue() + ",");
			}
		}
		return "[Engine:" + maker + "," + specInfo.toString().substring(0, specInfo.length() - 1) + 
				((createdDate != null) ? "," + createdDate : "") + "]";
	}

}
