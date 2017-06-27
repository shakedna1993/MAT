package entity;

import java.io.Serializable;

public class Evaluation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9187745187405215531L;
	private int EvalId;
	private String AssId, stuId, path;
	
	public Evaluation() {
		super();
	}

	public Evaluation(int evalId, String assId, String stuId, String path) {
		super();
		EvalId = evalId;
		AssId = assId;
		this.stuId = stuId;
		this.path = path;
	}

	public Evaluation(String assId, String stuId, String path) {
		super();
		AssId = assId;
		this.stuId = stuId;
		this.path = path;
	}

	public int getEvalId() {
		return EvalId;
	}

	public void setEvalId(int evalId) {
		EvalId = evalId;
	}

	public String getAssId() {
		return AssId;
	}

	public void setAssId(String assId) {
		AssId = assId;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Evaluation [EvalId=" + EvalId + ", AssId=" + AssId + ", stuId=" + stuId + ", path=" + path + "]";
	}


	
}
