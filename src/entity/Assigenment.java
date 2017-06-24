package entity;

import java.io.Serializable;
import java.util.Date;

public class Assigenment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149522994876793152L;
	private String AssId, UserId, Fileid, courseid, coursename, classid, classname, teacherid, techername,assname,grade,semester,comments,evalid,path;
	private int check;
	private Date DueDate;
	
	/**
	 * FileType={1-PDF, 2-WORD, 3-ZIP, 4-Excel}
	 * check={0-Teacher Upload
	 * 		  1-Unchecked- student upload 
	 * 		  2-Checked - teacher upload }
	 */
	
	public Assigenment() {
		super();
		AssId = "-1";
	}


	public Assigenment(String assId, String userId, String fileid, String courseid, String semester, int check,
			Date dueDate) {
		super();
		AssId = assId;
		UserId = userId;
		Fileid = fileid;
		this.courseid = courseid;
		this.semester = semester;
		this.check = check;
		DueDate = dueDate;
	}
	
	

	public Assigenment(String assId, String userId, String fileid, String courseid, String classid, String grade,
			String semester, String comments, String evalid, int check, Date dueDate,String path) {
		super();
		AssId = assId;
		UserId = userId;
		Fileid = fileid;
		this.courseid = courseid;
		this.classid = classid;
		this.grade = grade;
		this.semester = semester;
		this.comments = comments;
		this.evalid = evalid;
		this.check = check;
		DueDate = dueDate;
		this.path=path;
	}
	
	public Assigenment(String userId, String coursename) {
		super();
		UserId = userId;
		this.coursename = coursename;
	}


	public String getCourseid() {
		return courseid;
	}

	public String getAssname() {
		return assname;
	}

	public void setAssname(String assname) {
		this.assname = assname;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(String teacherid) {
		this.teacherid = teacherid;
	}

	public String getTechername() {
		return techername;
	}

	public void setTechername(String techername) {
		this.techername = techername;
	}


	public String getAssId() {
		return AssId;
	}

	public void setAssId(String assId) {
		AssId = assId;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getFileid() {
		return Fileid;
	}

	public void setFileid(String fileid) {
		Fileid = fileid;
	}

	public Date getDueDate() {
		return DueDate;
	}

	public void setDueDate(Date dueDate) {
		DueDate = dueDate;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getEvalid() {
		return evalid;
	}

	public void setEvalid(String evalid) {
		this.evalid = evalid;
	}

	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	@Override
	public String toString() {
		return "Assigenment [AssId=" + AssId + ", UserId=" + UserId + ", Fileid=" + Fileid + ", courseid=" + courseid
				+ ", coursename=" + coursename + ", classid=" + classid + ", classname=" + classname + ", teacherid="
				+ teacherid + ", techername=" + techername + ", assname=" + assname + ", grade=" + grade + ", semester="
				+ semester + ", comments=" + comments + ", evalid=" + evalid + ", path=" + path + ", check=" + check
				+ ", DueDate=" + DueDate + "]";
	}
	
}
