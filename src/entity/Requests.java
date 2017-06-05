package entity;

import java.io.Serializable;

public class Requests implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6190831168060592941L;
	private String ReqId, RequestDescription;
	private int status, ReqType;
	
	/**
	 * status={0-NotAnswer, 1-Confirmed, 2-Rejected }
	 * ReqType={1-Register student for a course
	 * 			2-Remove student from a course
	 * 			3-Change teacher appointment }
	 */
	
	public Requests() {
		super();
	}

	public Requests(String reqId, String requestDescription, int status, int reqType) {
		super();
		ReqId = reqId;
		RequestDescription = requestDescription;
		this.status = status;
		ReqType = reqType;
	}

	public String getReqId() {
		return ReqId;
	}

	public void setReqId(String reqId) {
		ReqId = reqId;
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
	
	
	
}
