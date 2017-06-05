package entity;

import java.io.Serializable;

public class Semester implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7376554907231485400L;
	private String semId;
	private int currentStatus, No_week;
	
	/**
	 * currentStatus={0- Not Current, 1-Current}
	 */
	
	public Semester() {
		super();
	}
	
	public Semester(String semId, int currentStatus, int no_week) {
		super();
		this.semId = semId;
		this.currentStatus = currentStatus;
		No_week = no_week;
	}

	public String getSemId() {
		return semId;
	}

	public void setSemId(String semId) {
		this.semId = semId;
	}

	public int getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}

	public int getNo_week() {
		return No_week;
	}

	public void setNo_week(int no_week) {
		No_week = no_week;
	}

	@Override
	public String toString() {
		return "Semester [semId=" + semId + ", currentStatus=" + currentStatus + ", No_week=" + No_week + "]";
	}
	
}
