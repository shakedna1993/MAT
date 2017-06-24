package entity;

import java.io.Serializable;

public class Class implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 117905001325117180L;
	private String classId, name, Teachid;
	private int MAXStudent;
	
	public Class() {
		super();
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

	@Override
	public String toString() {
		return "Class [classId=" + classId + ", name=" + name + ", Teachid=" + Teachid + ", MAXStudent=" + MAXStudent
				+ "]";
	}
	
}
