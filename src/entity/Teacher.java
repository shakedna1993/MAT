package entity;

import java.io.Serializable;



public class Teacher extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8969357742306461220L;
//	private String Name,Password,UserName,Id;
//	private int isConnected,Role,Blocked;
	private String Unit,intership;
	private int maxHours;
	
	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
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

	@Override
	public String toString() {
		return "Teacher [Unit=" + Unit + ", intership=" + intership + ", maxHours=" + maxHours + "]";
	}

	
	
	
}