package entity;

import java.io.Serializable;

public class User implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	/**
	 * 
	 */
	private String Name,Password,UserName,Id;
	private int isConnected,Role,Blocked;
	/*
	 * Role={1-Secretary, 2-Manager, 3-Teacher, 4-Student, 5-System manager, 6- Parent, 7- Manager&Teacher}
	 * isConnected={0-No, 1-Yes}
	 * Blocked={0-Blocked, 1-Not Blocked}
	 */
	
	public User() {
		super();
	}
	
	public User(String name, String password, String userName, String id, int isConnected, int role, int blocked) {
		super();
		Name = name;
		Password = password;
		UserName = userName;
		Id = id;
		this.isConnected = isConnected;
		Role = role;
		Blocked = blocked;
	}
	
	
	
	public User(String userName) {
		super();
		UserName = userName;
	}

	public User(String userName, String password) {
		super();
		Password = password;
		UserName = userName;
	}

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public int getIsConnected() {
		return isConnected;
	}
	public void setIsConnected(int isConnected) {
		this.isConnected = isConnected;
	}
 
	public int getBlocked() {
		return Blocked;
	}

	public void setBlocked(int blocked) {
		Blocked = blocked;
	}

	public int getRole() {
		return Role;
	}

	public void setRole(int role) {
		Role = role;
	}

	@Override
	public String toString() {
		return "User [Name=" + Name + ", Password=" + Password + ", UserName=" + UserName + ", Id=" + Id
				+ ", isConnected=" + isConnected + ", Role=" + Role + ", Blocked=" + Blocked + "]";
	}
}
