package entity;

import java.io.Serializable;

public class User implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name,Password;
	private int Id,isConnected,priv;
	public User() {
		super();
	}
	public User(String name, String password, int id, int isConnected, int priv) {
		super();
		Name = name;
		Password = password;
		Id = id;
		this.isConnected = isConnected;
		this.priv = priv;
	}
	
	public User(String name, String password) {
		super();
		Name = name;
		Password = password;
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
	public int getPriv() {
		return priv;
	}
	public void setPriv(int priv) {
		this.priv = priv;
	}
	@Override
	public String toString() {
		return "User [Name=" + Name + ", Password=" + Password + ", Id=" + Id + ", isConnected=" + isConnected
				+ ", priv=" + priv + "]";
	}
	
}
