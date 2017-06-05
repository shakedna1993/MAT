package entity;

import java.io.Serializable;
import java.util.Date;

public class Assigenment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149522994876793152L;
	private String AssId, UserId, Fileid;
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
