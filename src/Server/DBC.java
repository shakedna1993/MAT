package Server;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Student;
import entity.Teacher;
import entity.User;
import Server.Connect;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

/**
 * This class contains all the interaction with the Data-Base *
 */

public class DBC {

	public static User LOGIN(String userName , String password) {
		Statement stmt;
		User lst = new User();

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.users where UserName='" + userName + "' AND Password='" + password + "'");
			
			while (rs.next()) {
				// Print out the values

				try {
					lst.setId(rs.getString(1) == null ? "-1" : rs.getString(1));
					lst.setName(rs.getString(2));
					lst.setPassword(rs.getString(3));
					lst.setUserName(rs.getString(4));
					lst.setRole(Integer.parseInt(rs.getString(5)));
					lst.setBlocked(Integer.parseInt(rs.getString(6)));
					lst.setIsConnected(Integer.parseInt(rs.getString(7)));
				}
				catch (Exception e) {
					lst.setId("-1");
					e.printStackTrace();
				}

			}
			rs.close();  // Before every close.stage we need to update isConnected=0 
			if (lst.getIsConnected() == 0 && lst.getBlocked() == 0) {
				String Quary = ("UPDATE moodle.users set isConnected = " + 1 + " where UserName ='" + userName + "' AND Password='" + password + "'");
				stmt.executeUpdate(Quary);
				System.out.println(lst);
			}
			Connect.close();
			return lst;

		} catch (SQLException e) {
			lst.setId("-1");
			e.printStackTrace();
		}
		return lst;
	}
	
	public static void ResetServer() {
		Statement stmt;

		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Query = ("UPDATE moodle.users set IsConnected = " + 0 + " where IsConnected =" + 1);
			stmt.executeUpdate(Query);
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public static Student StudentDetails(String Id) {
		Statement stmt;
		Student stud = new Student();

		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.student where sid='" + Id + "'");
			while (rs.next()) {
				// Print out the values

				try {
					stud.setParentId(rs.getString(1));
					stud.setAvg(rs.getFloat(2));
					stud.setId(rs.getString(3) == null ? "-1" : rs.getString(3));
				}
				catch (Exception e) {
					stud.setId("-1");
					e.printStackTrace();
				}

			}
			rs.close();
			Connect.close();
			return stud;

		} catch (SQLException e) {
			stud.setId("-1");
			e.printStackTrace();
		}
		return stud;
	}

	public static void LOGOUT(String userName) {
		Statement stmt;

		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Query = ("UPDATE moodle.users set isConnected = " + 0 + " where UserName ='" + userName + "'");
			stmt.executeUpdate(Query);

			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static Teacher Teacherdetails(String id) {
		Statement stmt;
		Teacher lst = new Teacher();

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.teachers where tid='" + id + "'");
			// where UserName="+userName+"AND where Password="+password
			while (rs.next()) {
				// Print out the values

				try {
					lst.setMaxHours(Integer.parseInt(rs.getString(1)));
					lst.setIntership(rs.getString(2));
					lst.setId(rs.getString(3)== null ? "-1" : rs.getString(3));
					lst.setUnit(rs.getString(4));
					
				} 
				catch (Exception e) {
					lst.setId("-1");
					e.printStackTrace();
				}

			}
			rs.close();
			Connect.close();
			return lst;

		} catch (SQLException e) {
			lst.setId("-1");
			e.printStackTrace();
		}

		return lst;
	}
	public static  int  UpdateUnit(String id, String Unit) {
		Statement stmt;
		Teacher lst = new Teacher();
		boolean flag=false;

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.teachers where Id='" + id + "'");
			// where UserName="+userName+"AND where Password="+password
			while (rs.next()) {
				// Print out the values

				try {
					flag=true;	
				} 
				catch (Exception e) {
					lst.setId("-1");
					e.printStackTrace();
				}

			}
			rs.close();
			
			String Quary="UPDATE moodle.teachers SET Unit='"+Unit+"' WHERE Id="+id+"";
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
	public static String StudentExists(String sid){
		Statement stmt;
		boolean flag=false;

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.student where sid='" + sid + "'");
			
			while (rs.next()) {
				try {
					flag=true;	
				} 
				catch (Exception e) {
					e.printStackTrace();
				}

			}
			rs.close();
			Connect.close();
			return "" + flag;
		} catch (SQLException e) {
			e.printStackTrace();
			return "" + false;
		}

	}
	public static String ParentExists(String pid){
		Statement stmt;
		boolean flag=false;

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.users where Id='" + pid + "' AND Role='6'");
			
			while (rs.next()) {
				try {
					flag=true;	
				} 
				catch (Exception e) {
					e.printStackTrace();
				}

			}
			rs.close();
			Connect.close();
			return ""+flag;
		} catch (SQLException e) {
			e.printStackTrace();
			return ""+false;
		}
	}

	public static int DefineClass(entity.Class c){
		Statement stmt;
		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			
			boolean rs = stmt.execute(
					"INSERT INTO `moodle`.`class` (`Classid`, `name`, `MAXStudent`) VALUES ('"+c.getClassId()+"', '"+c.getName()+"', '"+c.getMAXStudent()+"');");

			Connect.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return 0;
	}
	public static int AddStudent(Student s){
		Statement stmt;
		

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			
			boolean rs = stmt.execute(
					"INSERT INTO moodle.users (`Id`, `Fullname`, `password`, `UserName`, `Role`, `isBlocked`, `isConnected`) VALUES ('"+s.getId()+"', '"+s.getName()+"', '"+s.getPassword()+
					"', '"+s.getUserName()+"', '"+"4"+"', '"+0+"', '"+0+"');\n");
			rs = stmt.execute("INSERT INTO `moodle`.`student` (`parentid`, `avg`, `sid`) VALUES ('"+s.getParentId()+"', '"+0+"', '"+s.getId()+"')");
			
			if (rs) {
				try {
						
				} 
				catch (Exception e) {
					e.printStackTrace();
				}

			}
			//rs.close();
			Connect.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return 0;
	}
	public static boolean UserNameExists(String name){
		Statement stmt;
		boolean flag=false;

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.users where UserName='" + name + "'");
			
			while (rs.next()) {
				try {
					flag=true;	
				} 
				catch (Exception e) {
					e.printStackTrace();
				}

			}
			rs.close();
			Connect.close();
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean classIDExists (String id){
		Statement stmt;
		boolean flag=false;

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.class where Classid='" + id + "'");
			
			while (rs.next()) {
				try {
					flag=true;	
				} 
				catch (Exception e) {
					e.printStackTrace();
				}

			}
			rs.close();
			Connect.close();
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean classNameExists (String name){
		Statement stmt;
		boolean flag=false;
		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.class where name='" + name + "'");
			
			while (rs.next()) {
				try {
					flag=true;	
				} 
				catch (Exception e) {
					e.printStackTrace();
				}

			}
			rs.close();
			Connect.close();
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	@SuppressWarnings("unused")
	private static ResultSet executeUpdate(String quary) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
