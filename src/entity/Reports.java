package entity;

import java.io.Serializable;

/**
 * This class in charge for all the reports details
 */
public class Reports implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5138553479058902L;
	private String RepId,RepName,StartSem,EndSem;
	private int Type;
	/**
	 * Type={1-Grades of different classes(same teacher)
	 * 		 2-Grades of different teachers(same class)
	 * 		 3-Grades of different courses(same class) }
	 */
	
	public Reports() {
		super();
		RepId="-1";
	}
	
	
	public Reports(String repId, String repName, String startSem, String endSem, int type) {
		super();
		RepId = repId;
		RepName = repName;
		StartSem = startSem;
		EndSem = endSem;
		Type = type;
	}


	public Reports(String repId, String repName, int type) {
		super();
		RepId = repId;
		RepName = repName;
		Type = type;
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
	
	public String getRepName() {
		return RepName;
	}

	public void setRepName(String repName) {
		RepName = repName;
	}
	
	
	public String getStartSem() {
		return StartSem;
	}


	public void setStartSem(String startSem) {
		StartSem = startSem;
	}


	public String getEndSem() {
		return EndSem;
	}


	public void setEndSem(String endSem) {
		EndSem = endSem;
	}


	@Override
	public String toString() {
		return "Reports [RepId=" + RepId + ", RepName=" + RepName + ", StartSem=" + StartSem + ", EndSem=" + EndSem
				+ ", Type=" + Type + "]";
	}
	
	

}
