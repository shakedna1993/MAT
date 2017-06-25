package entity;

import java.io.Serializable;
import java.util.ArrayList;

import Server.DBC;

public class Student extends User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5638726927853322152L;
//	private String Name,Password,UserName,Id;
//	private int isConnected,Role,Blocked;
	private String ParentId,Classid;
	private float avg;
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(String name, String password, String userName, String id, String parentId, int isConnected, int role,
			int blocked, float avg, String Classid) {
		super(name, password, userName, id, isConnected, role, blocked);
		Classid=this.Classid;
		ParentId = parentId;
		this.avg = avg;
		// TODO Auto-generated constructor stub
	}
	public Student(String userName, String password) {
		super(userName, password);
		// TODO Auto-generated constructor stub
	}	
	
	public Student(String id) {
		super(id);
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
	
	public String getClassid() {
		
		return Classid;
	}
	public void setClassid(String classid) {
		Classid = classid;
	}
	
	@Override
	public String toString() {
		return "Student [ParentId=" + ParentId + ", Classid=" + Classid + ", avg=" + avg + "]";
	}
	
}

