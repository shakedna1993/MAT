package Server;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;


import entity.Teacher;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

/**
 * This class contains all the interaction with the Data-Base *
 */

public class DBC {

	public static Teacher Teacherdetails(int Id) {
		Statement stmt;
		Teacher lst = new Teacher();

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.teachers where Id='" + Id + "'");
			// where UserName="+userName+"AND where Password="+password
			while (rs.next()) {
				// Print out the values

				try {
					lst.setName(rs.getString(2));
					lst.setId(Integer.parseInt(rs.getString(1) == null ? "-1" : rs.getString(1)));
					lst.setUnit(rs.getString(3));
					
				} 
				catch (Exception e) {
					lst.setId(0);
					e.printStackTrace();
				}

			}
			rs.close();
			Connect.close();
			return lst;

		} catch (SQLException e) {
			lst.setId(0);
			e.printStackTrace();
		}

		return lst;
	}
	public static  int  UpdateUnit(int Id, String Unit) {
		Statement stmt;
		Teacher lst = new Teacher();
		boolean flag=false;

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.teachers where Id='" + Id + "'");
			// where UserName="+userName+"AND where Password="+password
			while (rs.next()) {
				// Print out the values

				try {
					flag=true;	
				} 
				catch (Exception e) {
					lst.setId(0);
					e.printStackTrace();
				}

			}
			rs.close();
			
			String Quary="UPDATE moodle.teachers SET Unit='"+Unit+"' WHERE Id="+Id+"";
			//String Quary = "update teac.Teachers set Teaching_Unit= '" + Unit + "'" + " Where teac.Id="+Id;
			stmt.executeUpdate(Quary);
			// where UserName="+userName+"AND where Password="+password
			Connect.close();
			if(flag)
				return 1;
			return -1;


		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

		
	}

	@SuppressWarnings("unused")
	private static ResultSet executeUpdate(String quary) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
