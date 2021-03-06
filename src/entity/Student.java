package entity;

import java.io.Serializable;

/**
 * This class in charge for all the student details
 */
public class Student extends User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5638726927853322152L;
	private String ParentId,Classid,Id;
	private float avg;
	
	public Student() {
		super();
		Id="-1";
		ParentId="-1";
		Classid="-1";
		avg=0;
		// TODO Auto-generated constructor stub
	}
	public Student(String name, String password, String userName, String id, String parentId, int isConnected, int role,
			int blocked, float avg, String Classid) {
		super(name, password, userName, id, isConnected, role, blocked);
		this.Classid=Classid;
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
	
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	
	@Override
	public String toString() {
		return "Student [ParentId=" + ParentId + ", Classid=" + Classid + ", Id=" + Id + ", avg=" + avg + "]";
	}
	
}

