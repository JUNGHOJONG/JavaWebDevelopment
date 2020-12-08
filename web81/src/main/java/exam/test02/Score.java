package exam.test02;

public class Score {
	private static final int SUBJECT_COUNT = 3;
	
	String name;
	float kor;
	float eng;
	float math;
	
	public Score() { }
	
	public Score(String name, float kor, float eng, float math) {
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
	}
	
	public float average() {
		return sum() / (float) SUBJECT_COUNT;
	}
	
	public float sum() {
		return kor + eng + math;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getKor() {
		return this.kor;
	}
	
	public void setKor(float kor) {
		this.kor = kor;
	}
	
	public float getEng() {
		return this.eng;
	}
	
	public void setEng(float eng) {
		this.eng = eng;
	}
	
	public float getMath() {
		return this.math;
	}
	
	public void setMath(float math) {
		this.math = math;
	}

}
