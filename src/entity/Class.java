package entity;

import java.io.Serializable;

public class Class implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 117905001325117180L;
	private String classId, name;
	private int MAXStudent;
	
	public Class() {
		super();
	}

	public Class(String classId, String name, int mAXStudent) {
		super();
		this.classId = classId;
		this.name = name;
		MAXStudent = mAXStudent;
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

	@Override
	public String toString() {
		return "Class [classId=" + classId + ", name=" + name + ", MAXStudent=" + MAXStudent + "]";
	}
	
}
