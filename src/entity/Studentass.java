package entity;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Studentass implements Serializable{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = -5256070921522529168L;
	private String AssiName, Studid, Courseid, Fileid, fname, evaFileName, gradeFileName, courseName;
	private int Assid;
	private Date Date;
	private File path, evapath, gradepath;
	
	
	public Studentass() {
		super();
	}

	public Studentass(int assid, String assiName, String studid, String courseid, String fileid, 
			java.util.Date date, File path, String fname) {
		super();
		Assid = assid;
		AssiName = assiName;
		Studid = studid;
		Courseid = courseid;
		Fileid = fileid;
		this.fname = fname;
		Date = date;
		this.path = path;
	}
	
	public Studentass(String assiName, String studid, String courseid, String fileid, java.util.Date date,
			File path, String fname) {
		super();
		AssiName = assiName;
		Studid = studid;
		Courseid = courseid;
		Fileid = fileid;
		this.fname = fname;
		Date = date;
		this.path = path;
	}
	
	
	
	public Studentass( int assid, String studid, String courseid, String fileid, java.util.Date date,
			File path, String fname) {
		super();
		Studid = studid;
		Courseid = courseid;
		Fileid = fileid;
		this.fname = fname;
		Assid = assid;
		Date = date;
		this.path = path;
	}

	public String getAssiName() {
		return AssiName;
	}

	public void setAssiName(String assiName) {
		AssiName = assiName;
	}

	public int getAssid() {
		return Assid;
	}

	public void setAssid(int assid) {
		Assid = assid;
	}

	public String getStudid() {
		return Studid;
	}

	public void setStudid(String studid) {
		Studid = studid;
	}

	public String getCourseid() {
		return Courseid;
	}

	public void setCourseid(String courseid) {
		Courseid = courseid;
	}


	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public String getFileid() {
		return Fileid;
	}

	public void setFileid(String fileid) {
		Fileid = fileid;
	}

	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}
	

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getEvaFileName() {
		return evaFileName;
	}

	public void setEvaFileName(String evaFileName) {
		this.evaFileName = evaFileName;
	}

	public String getGradeFileName() {
		return gradeFileName;
	}

	public void setGradeFileName(String gradeFileName) {
		this.gradeFileName = gradeFileName;
	}

	public File getEvapath() {
		return evapath;
	}

	public void setEvapath(File evapath) {
		this.evapath = evapath;
	}

	public File getGradepath() {
		return gradepath;
	}

	public void setGradepath(File gradepath) {
		this.gradepath = gradepath;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@Override
	public String toString() {
		return "Studentass [AssiName=" + AssiName + ", Studid=" + Studid + ", Courseid=" + Courseid + ", Fileid="
				+ Fileid + ", fname=" + fname + ", evaFileName=" + evaFileName + ", gradeFileName=" + gradeFileName
				+ ", courseName=" + courseName + ", Assid=" + Assid + ", Date=" + Date + ", path=" + path + ", evapath="
				+ evapath + ", gradepath=" + gradepath + "]";
	}	
	
	
}
