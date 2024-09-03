package ioc.xml.po;

public class Course {

	private Student student;

	private String name;

	public String getName() {
		System.out.println(name);
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
