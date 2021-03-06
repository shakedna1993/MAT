package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class in charge for all the courses details
 */
public class Course implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7643170416233054127L;
	private String courseId, name, unit,Semid;
	private int Hours;
	private float grade,avg;
	private String preReqId;
	private ArrayList<Float> GradeList = new ArrayList<>();
	
	public Course() {
		super();
		courseId="-1";
		preReqId="-1";
		Hours=-1;
		name="-1";
		Semid="-1";
		unit="-1";
		preReqId="-1";
		Hours=0;
		grade=avg=0;
	}
	
	
	public Course(String courseId, String name, String unit, String semid, int hours, float grade, float avg,
			String preReqId, ArrayList<Float> gradeList) {
		super();
		this.courseId = courseId;
		this.name = name;
		this.unit = unit;
		Semid = semid;
		Hours = hours;
		this.grade = grade;
		this.avg = avg;
		this.preReqId = preReqId;
		GradeList = gradeList;
	}


	public Course(String courseId, String name, String unit, String semid, int hours, float grade, String preReqId,
			ArrayList<Float> gradeList) {
		super();
		this.courseId = courseId;
		this.name = name;
		this.unit = unit;
		Semid = semid;
		Hours = hours;
		this.grade = grade;
		this.preReqId = preReqId;
		GradeList = gradeList;
	}

	public Course(String courseId, String name, String unit,String Semid, int hours, Float grade) {
		super();
		this.courseId = courseId;
		this.name = name;
		this.unit = unit;
		Hours = hours;
		this.Semid=Semid;
		this.setGrade(grade);
	}

	public Course(String courseId, String name, String unit, int hours) {
		super();
		this.courseId = courseId;
		this.name = name;
		this.unit = unit;
		Hours = hours;
	}
	
	public Course(String courseId, String preReqId) {
		super();
		this.courseId = courseId;
		this.preReqId = preReqId;
	}

	public Course(String courseId) {
		super();
		this.courseId = courseId;
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
	

	public String getSemid() {
		return Semid;
	}

	public void setSemid(String semid) {
		Semid = semid;
	}
	public Float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}
	

	
	public ArrayList<Float> getGradeList() {
		return GradeList;
	}

	public void setGradeList(ArrayList<Float> gradeList) {
		GradeList = new ArrayList<Float>(gradeList);
	}

	public void addGrade(Float s) {
		this.GradeList.add(s) ;
	}

	public String getPreReqId() {
		return preReqId;
	}

	public void setPreReqId(String preReqId) {
		this.preReqId = preReqId;
	}
	
	

	public float getAvg() {
		return avg;
	}


	public void setAvg(float avg) {
		this.avg = avg;
	}


	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", name=" + name + ", unit=" + unit + ", Semid=" + Semid + ", Hours="
				+ Hours + ", grade=" + grade + ", avg=" + avg + ", preReqId=" + preReqId + ", GradeList=" + GradeList
				+ "]";
	}
}
