package entity;

import java.io.Serializable;

public class Reports implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5138553479058902L;
	private String RepId;
	private int Type;
	/**
	 * Type={1-Grades of different classes(same teacher)
	 * 		 2-Grades of different teachers(same class)
	 * 		 3-Grades of different courses(same class) }
	 */
	
	public Reports() {
		super();
	}
	public Reports(String repId, int type) {
		super();
		RepId = repId;
		Type = type;
	}
	public String getRepId() {
		return RepId;
	}
	public void setRepId(String repId) {
		RepId = repId;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	
	@Override
	public String toString() {
		return "Reports [RepId=" + RepId + ", Type=" + Type + "]";
	}
	
	

}
