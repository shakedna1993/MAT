package entity;

import java.io.Serializable;

public class Student extends User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5638726927853322152L;
//	private String Name,Password,UserName,Id;
//	private int isConnected,Role,Blocked;
	private String ParentId;
	private float avg;
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(String name, String password, String userName, String id, String parentId, int isConnected, int role,
			int blocked, float avg) {
		super(name, password, userName, id, isConnected, role, blocked);
		ParentId = parentId;
		this.avg = avg;
		// TODO Auto-generated constructor stub
	}
	public Student(String userName, String password) {
		super(userName, password);
		// TODO Auto-generated constructor stub
	}
	public Student(String userName) {
		super(userName);
		// TODO Auto-generated constructor stub
	}
	public String getParentId() {
		return ParentId;
	}
	public void setParentId(String parentId) {
		ParentId = parentId;
	}
	public float getAvg() {
		return avg;
	}
	public void setAvg(float avg) {
		this.avg = avg;
	}
	
	@Override
	public String toString() {
		return "Student [ParentId=" + ParentId + ", avg=" + avg + "]";
	}
	
}

