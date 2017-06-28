package entity;

import java.io.Serializable;



public class Teacher extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8969357742306461220L;
//	private String Name,Password,UserName,Id;
//	private int isConnected,Role,Blocked;
	private String Unit,intership,TecName,TecId;
	private int maxHours;
	
	public Teacher() {
		super();
		TecId="-1";
		Unit="-1";
		intership="-1";
		TecName="-1";
		TecId="-1";
		maxHours=0;
		// TODO Auto-generated constructor stub
	}


	public Teacher(String unit, String intership, String tecName, String tecId, int maxHours) {
		super();
		Unit = unit;
		this.intership = intership;
		TecName = tecName;
		TecId = tecId;
		this.maxHours = maxHours;
	}

	public Teacher(String name, String password, String userName, String id, String unit, String intership,
			int isConnected, int role, int blocked, int maxHours){
		super(name, password, userName, id, isConnected, role, blocked);
		Unit = unit;
		this.intership = intership;
		this.maxHours = maxHours;
		// TODO Auto-generated constructor stub
	}

	public Teacher(String userName, String password) {
		super(userName, password);
		// TODO Auto-generated constructor stub
	}

	public Teacher(String userName) {
		super(userName);
		// TODO Auto-generated constructor stub
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getIntership() {
		return intership;
	}

	public void setIntership(String intership) {
		this.intership = intership;
	}

	public int getMaxHours() {
		return maxHours;
	}

	public void setMaxHours(int maxHours) {
		this.maxHours = maxHours;
	}

	
	public String getTecName() {
		return TecName;
	}

	public void setTecName(String tecName) {
		TecName = tecName;
	}
	
	
	
	public String getTecId() {
		return TecId;
	}


	public void setTecId(String tecId) {
		TecId = tecId;
	}


	@Override
	public String toString() {
		return "Teacher [Unit=" + Unit + ", intership=" + intership + ", TecName=" + TecName + ", TecId=" + TecId
				+ ", maxHours=" + maxHours + "]";
	}

	
	
	
}