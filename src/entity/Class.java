package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Class implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 117905001325117180L;
	private String classId, name, Teachid,Courseid,Semid,TecName;
	private int MAXStudent;
	private ArrayList<Student> StudentList = new ArrayList<>();
	private ArrayList<String> TecList = new ArrayList<>();
	private ArrayList<String> CourseList = new ArrayList<>();
	private Float avg;
	
	public Class() {
		super();
		classId="-1";
		Teachid="-1";
		Courseid="-1";
		Semid="-1";
		name="-1";
		TecName="-1";
	}
	
	

	public Class(String classId, String name, String teachid, String courseid, String semid, String tecName,
			int mAXStudent, ArrayList<Student> studentList, ArrayList<String> tecList, ArrayList<String> courseList,
			Float avg) {
		super();
		this.classId = classId;
		this.name = name;
		Teachid = teachid;
		Courseid = courseid;
		Semid = semid;
		TecName = tecName;
		MAXStudent = mAXStudent;
		StudentList = studentList;
		TecList = tecList;
		CourseList = courseList;
		this.avg = avg;
	}



	public Class(String classId, String name, String teachid, String courseid, String semid, int mAXStudent,
			ArrayList<Student> studentList, ArrayList<String> tecList, ArrayList<String> courseList, Float avg) {
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
		this.avg = avg;
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
	
	



	public Float getAvg() {
		return avg;
	}



	public void setAvg(Float avg) {
		this.avg = avg;
	}



	public String getTecName() {
		return TecName;
	}


	public void setTecName(String tecName) {
		TecName = tecName;
	}



	@Override
	public String toString() {
		return "Class [classId=" + classId + ", name=" + name + ", Teachid=" + Teachid + ", Courseid=" + Courseid
				+ ", Semid=" + Semid + ", TecName=" + TecName + ", MAXStudent=" + MAXStudent + ", StudentList="
				+ StudentList + ", TecList=" + TecList + ", CourseList=" + CourseList + ", avg=" + avg + "]";
	}
	
}
