package entity;

import java.io.Serializable;

public class Requests implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6190831168060592941L;
	private String ReqId, RequestDescription,UserId,CourseId,ClassId;
	private int status, ReqType;
	
	
	/**
	 * status={0-NotAnswer, 1-Confirmed, 2-Rejected}
	 * ReqType={1-Register student for a course
	 * 			2-Remove student from a course
	 * 			3-Change teacher appointment }
	 */
	
	public Requests() {
		super();
	}

	public Requests(String reqId,String userId,String courseId,String requestDescription, int status, int reqType) {
		super();
		ReqId = reqId;
		RequestDescription = requestDescription;
		this.status = status;
		ReqType = reqType;
		UserId=userId;
		CourseId=courseId;
	}

	public String getReqId() {
		return ReqId;
	}

	public void setReqId(String reqId) {
		ReqId = reqId;
	}

	

	public String getClassId() {
		return ClassId;
	}

	public void setClassId(String classId) {
		ClassId = classId;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userid) {
		UserId = userid;
	}

	
	public String getRequestDescription() {
		return RequestDescription;
	}

	public void setRequestDescription(String requestDescription) {
		RequestDescription = requestDescription;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getReqType() {
		return ReqType;
	}

	public void setReqType(int reqType) {
		ReqType = reqType;
	}

	@Override
	public String toString() {
		return "Requests [ReqId=" + ReqId + ", RequestDescription=" + RequestDescription + ", status=" + status
				+ ", ReqType=" + ReqType + "]";
	}

	public String getCourseId() {
		return CourseId;
	}

	public void setCourseId(String courseId) {
		CourseId = courseId;
	}
	
	public String getReqTypeString(){
		switch (ReqType){
		case 1:
			return "Register student";
		case 2: 
			return "Remove student";
		case 3:
			return "Teacher appointment";
		default:
			return null;
		}
	}
	public String getStatusString(){
		switch (status){
		case 0:
			return "Not Answered";
		case 1: 
			return "Confirmed";
		case 2:
			return "Rejected";
		default:
			return null;
		}
	}
}
