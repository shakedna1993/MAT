package entity;

import java.io.Serializable;



public class Teacher implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8969357742306461220L;
	private String Name,Password,Unit;
	private int Id,isConnected;
	
	
	public Teacher(String unit, int id) {
		super();
		Unit = unit;
		Id = id;
	}
	
	public Teacher() {
		super();
	}

	public Teacher(int id) {
		super();
		Id = id;
	}
	public Teacher(String name, String password, String unit, int id) {
		super();
		Name = name;
		Password = password;
		Unit = unit;
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getIsConnected() {
		return isConnected;
	}
	public void setIsConnected(int isConnected) {
		this.isConnected = isConnected;
	}
	@Override
	public String toString() {
		return "Teacher [Name=" + Name + ", Password=" + Password + ", Unit=" + Unit + ", Id=" + Id + ", isConnected="
				+ isConnected + "]";
	}
	
	
}