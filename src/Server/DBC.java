package Server;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Assigenment;
import entity.Course;
import entity.Student;
import entity.Teacher;
import entity.User;
import entity.Class;
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
					stud.setClassid(rs.getString(4));
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
	
	/**This method will search for a specific course in the DB
	 * @param cid-will hold the id number of the course that been searched
	 * @return return the course that been searched if it found
	 */
	public static ArrayList<Course> StudentCourse(String Sid) {
		Statement stmt;
		ArrayList<Course> lst = new ArrayList<>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Quary = "SELECT moodle.studentcourse.* FROM moodle.studentcourse WHERE moodle.studentcourse.studid='" + Sid + "'" ;

			ResultSet rs = stmt.executeQuery(Quary);

			while (rs.next()) {
				// Print out the values
				Course cu = new Course();
				try {
					cu.setSemid((rs.getString(2)));
					cu.setCourseId(rs.getString(3));
					cu.setGrade(Float.parseFloat(rs.getString(4)));
					cu.setName((rs.getString(5)));
					lst.add(cu);
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}

			rs.close();
			Connect.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lst;
	}
	
	
	
	public static ArrayList<String>	setComboBoxTeacherCourse(String id){
		ArrayList<String> al = new ArrayList<String>();
		ArrayList<String> a2 = new ArrayList<String>();
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.classcourse where tid='" + id + "'");
			while (rs.next()) {
				// Print out the values

				try {
					al.add(rs.getString(1));		
				}
				catch (Exception e) {
		
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
			return al;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return al;
	}



	public static Course createCourseEntity(String id) {
		Statement stmt;
		Course course = new Course();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.course where Courseid='" + id + "'");
			while (rs.next()) {
				try {
					course.setCourseId(rs.getString(1));
					course.setName(rs.getString(2));
					course.setUnit(rs.getString(3));
					course.setHours(rs.getInt(4));		
				} 
				catch (Exception e) {
					course.setCourseId(null);
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
			return course;
		} catch (SQLException e) {
			course.setCourseId(null);
			e.printStackTrace();
		}
		return course;
	}

	public static ArrayList<Assigenment> setTableViewTeacherCourseAssigenment(String coursename,String teacherid) {
		Statement stmt;
		ArrayList<String> a1 = new ArrayList<>();
		ArrayList<String> a2 = new ArrayList<>();
		ArrayList<Assigenment> lst = new ArrayList<>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery( "SELECT * FROM moodle.classcourse where tid='" + teacherid +"'");

			while (rs.next()) {
				// Print out the values
			//	Assigenment ass = new Assigenment();
				try {
					
					a1.add(rs.getString(1));
					/*
					ass.setAssId(rs.getString(1));
					ass.setFileid(rs.getString(2));
					ass.setDueDate(rs.getDate(3));
					ass.setUserId(rs.getString(4));
					ass.setState(rs.getInt(5));
					ass.setAssname(rs.getString(6));
					ass.setCourseid(rs.getString(7));
					ass.setClassid(rs.getString(8));
					ass.setTeacherid(rs.getString(9));
					ass.setCoursename(rs.getString(10));
					lst.add(ass);
					*/
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
				for(int i = 0; i<a1.size();i++ ){
					ResultSet rs1 = stmt.executeQuery( "SELECT * FROM moodle.course where Courseid='" + a1.get(i) +"'");
					while (rs1.next()) {
						
						try {
							if(coursename.equals(rs1.getString(2))) {
								a2.add(rs1.getString(1));
							}	
								
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
					rs1.close();
				}
				
				for(int i = 0; i<a2.size();i++ ){
				ResultSet rs2 = stmt.executeQuery( "SELECT * FROM moodle.teacherassingment where CourseId='" + a2.get(i) +"'");
				
				while (rs2.next()) {
					// Print out the values
					Assigenment ass = new Assigenment();
					try {
						
						ass.setAssId(rs2.getString(1));
						ass.setFileid(rs2.getString(2));
						ass.setDueDate(rs2.getDate(3));
						ass.setUserId(rs2.getString(4));
						//ass.setCheck(rs2.getInt(5));
						ass.setCourseid(rs2.getString(5));
						lst.add(ass);				
					}

					catch (Exception e) {
						e.printStackTrace();
					}
				}
				rs2.close();
			}
			rs.close();					
			Connect.close();
			return lst;

		}
			catch (SQLException e) {
			e.printStackTrace();
		}

		return lst;
	}

	public static int getWeeklyHours(String teacherId){
		ArrayList<String> a1 = new ArrayList<String>();
		int cnt = 0;
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery( "SELECT * FROM moodle.classcourse where tid='" + teacherId +"'");

			while (rs.next()) {
				
				try {
						a1.add(rs.getString(1));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			for(int i = 0; i<a1.size();i++ ){
				ResultSet rs1 = stmt.executeQuery( "SELECT hours FROM moodle.course where Courseid='" + a1.get(i) +"'");
				while (rs1.next()) {
					
					try {
							cnt = cnt + rs1.getInt(1);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			rs.close();
			Connect.close();
			return cnt;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cnt;
		
	}


	public static ArrayList<Assigenment> allAssForTeacher(String teacherid){
		Statement stmt;
		ArrayList<String> a1 = new ArrayList<>();
		ArrayList<String> a2 = new ArrayList<>();
		ArrayList<Assigenment> lst = new ArrayList<>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery( "SELECT * FROM moodle.classcourse where tid='" + teacherid +"'");

			while (rs.next()) {
		
				try {
					
					a1.add(rs.getString(1));
			
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
		
				
				for(int i = 0; i<a1.size();i++ ){
				ResultSet rs1 = stmt.executeQuery( "SELECT * FROM moodle.teacherassingment where CourseId='" + a1.get(i) +"'");
				
				while (rs1.next()) {
					// Print out the values
					Assigenment ass = new Assigenment();
					try {
						
						ass.setAssId(rs1.getString(1));
						ass.setFileid(rs1.getString(2));
						ass.setDueDate(rs1.getDate(3));
						ass.setUserId(rs1.getString(4));
						//ass.setCheck(rs1.getInt(5));
						ass.setCourseid(rs1.getString(5));
						lst.add(ass);
					
					}

					catch (Exception e) {
						e.printStackTrace();
					}
				}
				rs1.close();
			}

			rs.close();
			Connect.close();
			return lst;

			
		}
			catch (SQLException e) {
			e.printStackTrace();
		}

		return lst;
	}


	public static int insertNewAss(Assigenment ass){
	
	Statement stmt;
	try {

		Connection conn = Connect.getConnection();
		stmt = conn.createStatement();

		String Quary = "INSERT INTO moodle.teacherassingment (Assid,Fileid,DueDate,tecid,CourseId,SemId) VALUES ('"
				+ ass.getAssId()+  "','" + ass.getFileid() +  "','" + "2020-01-01" +  "','" + ass.getUserId() + "','" + ass.getCourseid() + "','" + ass.getSemester() + "')";
		stmt.executeUpdate(Quary);

		}

			catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
		return 1;
	}
	
	public static ArrayList<String>	setComboBoxStudentCourse(String Id){
		ArrayList<String> al = new ArrayList<String>();
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.studentcourse where studid='" + Id + "'");
			while (rs.next()) {
				// Print out the values

				try {
					al.add(rs.getString(3));		
				}
				catch (Exception e) {
		
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
			return al;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return al;
	}

	public static ArrayList<Assigenment> setTableViewStudentCourseAssigenment(String teacherid,String coursename) {
		Statement stmt;
		ArrayList<String> a1 = new ArrayList<>();
		ArrayList<String> a2 = new ArrayList<>();
		ArrayList<Assigenment> lst = new ArrayList<>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.classcourse where tid='" + teacherid +"'");

			while (rs.next()) {
				// Print out the values
			//	Assigenment ass = new Assigenment();
				try {
					
					a1.add(rs.getString(1));
					/*
					ass.setAssId(rs.getString(1));
					ass.setFileid(rs.getString(2));
					ass.setDueDate(rs.getDate(3));
					ass.setUserId(rs.getString(4));
					ass.setState(rs.getInt(5));
					ass.setAssname(rs.getString(6));
					ass.setCourseid(rs.getString(7));
					ass.setClassid(rs.getString(8));
					ass.setTeacherid(rs.getString(9));
					ass.setCoursename(rs.getString(10));
					lst.add(ass);
					*/
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
				for(int i = 0; i<a1.size();i++ ){
					ResultSet rs1 = stmt.executeQuery( "SELECT * FROM moodle.course where Courseid='" + a1.get(i) +"'");
					while (rs1.next()) {
						
						try {
							if(coursename.equals(rs1.getString(2))) {
								a2.add(rs1.getString(1));
							}	
								
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
					rs1.close();
				}
				
				for(int i = 0; i<a2.size();i++ ){
				ResultSet rs2 = stmt.executeQuery( "SELECT * FROM moodle.teacherassingment where CourseId='" + a2.get(i) +"'");
				
				while (rs2.next()) {
					// Print out the values
					Assigenment ass = new Assigenment();
					try {
						
						ass.setAssId(rs2.getString(1));
						ass.setFileid(rs2.getString(2));
						ass.setDueDate(rs2.getDate(3));
						ass.setUserId(rs2.getString(4));
						//ass.setCheck(rs2.getInt(5));
						ass.setCourseid(rs2.getString(5));
						lst.add(ass);				
					}

					catch (Exception e) {
						e.printStackTrace();
					}
				}
				rs2.close();
			}
			rs.close();					
			Connect.close();
			return lst;

		}
			catch (SQLException e) {
			e.printStackTrace();
		}

		return lst;
	}
	public static Class ClassCourseDetails(String Cid) {
		Statement stmt;
		Class cl = new Class();

		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.classcourse where classid='" + Cid + "'");
			while (rs.next()) {
				// Print out the values

				try {
					cl.setClassId(rs.getString(2));
					cl.setTeachid(rs.getString(3));
				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}
			rs.close();
			Connect.close();
			return cl;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cl;
	}
	
	public static int UpdateAss(Assigenment ass){
	Statement stmt;
	try {

		Connection conn = Connect.getConnection();
		stmt = conn.createStatement();
		String Quary = "update moodle.teacherassingment set Fileid= '" + ass.getFileid()+ "', DueDate= '" +  "2020-01-01" +
				 "' where Assid= '" + ass.getAssId() + "' AND CourseId= '" + ass.getCourseid() + "'";
		stmt.executeUpdate(Quary);

		}
			catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
		return 1;
	}
	

	
	@SuppressWarnings("unused")
	private static ResultSet executeUpdate(String quary) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
