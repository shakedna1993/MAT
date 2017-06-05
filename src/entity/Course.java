package entity;

import java.io.Serializable;

public class Course implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7643170416233054127L;
	private String courseId, name, unit;
	private int Hours;
	
	public Course() {
		super();
	}
	
	public Course(String courseId, String name, String unit, int hours) {
		super();
		this.courseId = courseId;
		this.name = name;
		this.unit = unit;
		Hours = hours;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getHours() {
		return Hours;
	}

	public void setHours(int hours) {
		Hours = hours;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", name=" + name + ", unit=" + unit + ", Hours=" + Hours + "]";
	}
}
