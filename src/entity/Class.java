package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Class implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 117905001325117180L;
	private String classId, name, Teachid,Courseid,Semid;
	private int MAXStudent;
	private ArrayList<Student> StudentList = new ArrayList<>();
	private ArrayList<String> TecList = new ArrayList<>();
	private ArrayList<String> CourseList = new ArrayList<>();
	
	public Class() {
		super();
	}
	
	

	public Class(String classId, String name, String teachid, String courseid, String semid, int mAXStudent,
			ArrayList<Student> studentList, ArrayList<String> tecList, ArrayList<String> courseList) {
		super();
		this.classId = classId;
		this.name = name;
		Teachid = teachid;
		Courseid = courseid;
		Semid = semid;
		MAXStudent = mAXStudent;
		StudentList = studentList;
		TecList = tecList;
		CourseList = courseList;
	}



	public Class(String classId, String name, String teachid, String courseid, String semid, int mAXStudent,
			ArrayList<Student> studentList) {
		super();
		this.classId = classId;
		this.name = name;
		Teachid = teachid;
		Courseid = courseid;
		Semid = semid;
		MAXStudent = mAXStudent;
		StudentList = studentList;
	}



	public Class(String classId, String name, String teachid, String courseid, String semid, int mAXStudent) {
		super();
		this.classId = classId;
		this.name = name;
		Teachid = teachid;
		Courseid = courseid;
		Semid = semid;
		MAXStudent = mAXStudent;
	}



	public Class(String classId, String name, String teachid, String courseid, int mAXStudent) {
		super();
		this.classId = classId;
		this.name = name;
		Teachid = teachid;
		Courseid = courseid;
		MAXStudent = mAXStudent;
	}


	public Class(String classId, String name,String Teachid, int mAXStudent) {
		super();
		this.classId = classId;
		this.name = name;
		MAXStudent = mAXStudent;
		this.Teachid=Teachid;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMAXStudent() {
		return MAXStudent;
	}

	public void setMAXStudent(int mAXStudent) {
		MAXStudent = mAXStudent;
	}
	



	public String getTeachid() {
		return Teachid;
	}

	public void setTeachid(String teachid) {
		Teachid = teachid;
	}
	
	

	public String getCourseid() {
		return Courseid;
	}


	public void setCourseid(String courseid) {
		Courseid = courseid;
	}


	public String getSemid() {
		return Semid;
	}


	public void setSemid(String semid) {
		Semid = semid;
	}



	public ArrayList<Student> getStudentList() {
		return StudentList;
	}

	public void setStudentList(ArrayList<Student> studentList) {
		StudentList = new ArrayList<Student>(studentList);
	}



	public ArrayList<String> getTecList() {
		return TecList;
	}



	public void setTecList(ArrayList<String> tecList) {
		TecList = new ArrayList<String>(tecList);
	}



	public ArrayList<String> getCourseList() {
		return CourseList;
	}



	public void setCourseList(ArrayList<String> courseList) {
		CourseList = new ArrayList<String>( courseList);
	}



	@Override
	public String toString() {
		return "Class [classId=" + classId + ", name=" + name + ", Teachid=" + Teachid + ", Courseid=" + Courseid
				+ ", Semid=" + Semid + ", MAXStudent=" + MAXStudent + ", StudentList=" + StudentList + ", TecList="
				+ TecList + ", CourseList=" + CourseList + "]";
	}
	
}
