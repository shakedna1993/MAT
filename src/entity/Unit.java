package entity;

import java.io.Serializable;

/**
 * This class in charge for all the unit details
 */
public class Unit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8228339322449306500L;
	private String UnitId, Name;
	
	public Unit() {
		super();
		UnitId="-1";
	}
	
	public Unit(String unitId) {
		super();
		UnitId = unitId;
	}

	public Unit(String unitId, String name) {
		super();
		UnitId = unitId;
		Name = name;
	}

	public String getUnitId() {
		return UnitId;
	}

	public void setUnitId(String unitId) {
		UnitId = unitId;
	}

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}

	@Override
	public String toString() {
		return "Unit [UnitId=" + UnitId + ", Name=" + Name + "]";
	}
}
