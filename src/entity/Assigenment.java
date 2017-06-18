package entity;

import java.io.Serializable;
import java.util.Date;

public class Assigenment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149522994876793152L;
	private String AssId, UserId, Fileid, courseid, coursename, classid, classname, teacherid, techername,assname;
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

	private int state;
	private Date DueDate;

	/**
	 * FileType={1-PDF, 2-WORD, 3-ZIP}
	 * state={0-Teacher Upload
	 * 		  1-Checked
	 * 		  2-Unchecked }
	 */
	
	public Assigenment() {
		super();
	}

	public Assigenment(String assId, String userId, String fileid, int state, Date dueDate) {
		super();
		AssId = assId;
		UserId = userId;
		Fileid = fileid;
		this.state = state;
		DueDate = dueDate;
		
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getDueDate() {
		return DueDate;
	}

	public void setDueDate(Date dueDate) {
		DueDate = dueDate;
	}
}
