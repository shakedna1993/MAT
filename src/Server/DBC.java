package Server;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.*;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import entity.Assigenment;
import entity.Course;
import entity.FileEnt;
import entity.Reports;
import entity.Semester;
import entity.Student;
import entity.Teacher;
import entity.User;
import entity.Class;
import Server.Connect;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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
	
	/**This method will check if specific unit is in the DB
	 * @param unitid-will hold the id number of the unit that been checked
	 * @return return the unit that been checked if it found
	 */
	public static Unit UnitExists(String unitid){
		Statement stmt;
		Unit lst = new Unit();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.unit where unitID='" + unitid + "'");
			while (rs.next()) {
				try {
					lst.setName(rs.getString(2));
					lst.setUnitId(rs.getString(1)== null ? "-1" : rs.getString(1));	
				} 
				catch (Exception e) {
					lst.setUnitId("-1");
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
			return lst;

		} catch (SQLException e) {
			lst.setUnitId("-1");
			e.printStackTrace();
		}
		return lst;
	}
	
	/**This method will check if specific course is in the DB
	 * @param coursid-will hold the id number of the course that been checked
	 * @return return the course that been checked if it found
	 */
	public static Course CourseExists(String courseid){
		Statement stmt;
		Course lst = new Course();
		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.managercourses where courseid='" + courseid + "'");
			while (rs.next()) {
				try {
					lst.setName(rs.getString(2));
					lst.setCourseId(rs.getString(1)== null ? "-1" : rs.getString(1));
					lst.setUnit(rs.getString(3));
					lst.setHours(Integer.parseInt(rs.getString(4)));
				} 
				catch (Exception e) {
					lst.setCourseId("-1");
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
			return lst;

		} catch (SQLException e) {
			lst.setCourseId("-1");
			e.printStackTrace();
		}
		return lst;
	}
	
	/**This method will insert a new course to the DB
	 * @param crs-will hold the details of the course that we want to insert 
	 * @return return the course that insert to the DB - (-1) in the courseid will testify of failure
	 */
	public static Course DefineNewCourse(Course crs){
		Statement stmt;
		Course lst = new Course(crs.getCourseId());
		lst.setName(crs.getName());
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			
			boolean rs = stmt.execute(
					"INSERT INTO moodle.managercourses (`Courseid`, `CourseName`, `unit`, `hours`) VALUES ('"+crs.getCourseId()+"', '"+crs.getName()+"', '"+crs.getUnit()+
					"', '"+crs.getHours()+"');\n");			
			if (rs) {
				try{
					return lst;
				} 
				catch (Exception e) {
					e.printStackTrace();
					lst.setCourseId("-1");
				}
			}
			Connect.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			lst.setCourseId("-1");
		}
		return lst;
	}
	
	/**This method will get a list of courses
	 * @param crsid-will hold the course id that we do not want to return in the list
	 * @return return list of courses 
	 */
	public static ArrayList<Course> CoursesList(String crsid) {
		Statement stmt;
		ArrayList<Course> lst = new ArrayList<>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Quary = "SELECT moodle.managercourses.* FROM moodle.managercourses "
					+ "WHERE moodle.managercourses.Courseid!= '" + crsid +"'";
			ResultSet rs = stmt.executeQuery(Quary);

			while (rs.next()) {
				Course cu = new Course();
				try {
					cu.setHours(Integer.parseInt(rs.getString(4)));
					cu.setUnit(rs.getString(3));
					cu.setName(rs.getString(2));
					cu.setCourseId(rs.getString(1));
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
	
	/**This method will insert a new PreRequisite of a course to the DB
	 * @param crs-will hold the course id that we want to insert 
	 * @param pre-will hold the prerequisite course id that we want to insert 
	 * @return return 1 for success & (-1) for failure
	 */
	public static int DefinePreReq(String crs, String pre){
		Statement stmt;
		boolean flag=false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.precourses WHERE course_id='" + crs + "' AND pre_id='"+ pre+"'");
			while (rs.next()) {
				try {
					flag=true;
					return -1;
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (flag==false){
				boolean rs1 = stmt.execute(
						"INSERT INTO moodle.precourses (`Course_id`, `pre_id`) VALUES ('"+crs+"', '"+pre+"');\n");	
				if (rs1==true) {
					try{
						return 1;
					} 
					catch (Exception e) {
						e.printStackTrace();
						return -1;
					}
				}
			}
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	/**This method will remove course from the DB
	 * @param crs-will hold the course id that we want to remove 
	 * @return return 1 for success & (-1) for failure
	 */
	public static int RemoveCourse(String crs){
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Quary = "DELETE FROM moodle.precourses WHERE course_id='" + crs +"' OR pre_id='"+ crs+"'";		
			stmt.executeUpdate(Quary);
			String Quary1 = "DELETE FROM moodle.managercourses WHERE Courseid='"+ crs+"'";
			stmt.executeUpdate(Quary1);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**This method will update course name in the DB
	 * @param crs-will hold the details of the course that we want to update 
	 * @return return 1 for success & (-1) for failure
	 */
	public static int RenameCourse(Course crs){
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.managercourses WHERE courseid='" + crs.getCourseId() + "'");
			while (rs.next()) {
				try {
					if (crs.getName().equals(rs.getString(2))){
						return -1;
					}
					else{
						String Query = ("UPDATE moodle.managercourses set CourseName = '" + crs.getName() + "' WHERE courseid ='" + crs.getCourseId() + "'");
						stmt.executeUpdate(Query);
						Connect.close();
						return 1;	
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch (Exception e) {
				e.printStackTrace();
		}
		return 1;	
	}
	
	/**This method will update course Weekly Hours in the DB
	 * @param crs-will hold the details of the course that we want to update 
	 * @return return 1 for success & (-1) for failure
	 */
	public static int WeeklyHoursUpdate(Course crs){
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.managercourses WHERE courseid='" + crs.getCourseId() + "'");
			while (rs.next()) {
				try {
					if (crs.getHours()==(Integer.parseInt(rs.getString(4)))){
						return -1;
					}
					else{
						String Query = ("UPDATE moodle.managercourses set hours = '" + crs.getHours() + "' WHERE courseid ='" + crs.getCourseId() + "'");
						stmt.executeUpdate(Query);
						Connect.close();
						return 1;	
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch (Exception e) {
				e.printStackTrace();
		}
		return 1;
	}

	/**This method will get a list of PreRequisite courses
	 * @param crsid-will hold the course id that we do not want to return his PreRequisite
	 * @return return list of PreRequisite courses 
	 */
	public static ArrayList<Course> PreReqList(String crsid) {
		Statement stmt;
		ArrayList<Course> lst = new ArrayList<>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Quary = "SELECT moodle.precourses.* FROM moodle.precourses "
					+ "WHERE moodle.precourses.course_id= '" + crsid +"'";
			ResultSet rs = stmt.executeQuery(Quary);

			while (rs.next()) {
				Course cu = new Course();
				try {
					cu.setCourseId(rs.getString(2));
				//	String Quary1 = "SELECT * FROM moodle.managercourses WHERE courseid= '" + cu.getCourseId() +"'";
				//	ResultSet rs1 = stmt.executeQuery(Quary1);
				//	String a=rs1.getString(2);
				//	cu.setName(a);
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
	
	/**This method will remove PreRequisite from the DB
	 * @param crs-will hold the course id that we want to remove
	 * @param pre-will hold the PreRequisite course id that we want to remove  
	 * @return return 1 for success & (-1) for failure
	 */
	public static int RemovePreReq(String crs, String pre){
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Quary = "SELECT moodle.precourses.* FROM moodle.precourses "
					+ "WHERE course_id='" + crs +"' AND pre_id='"+ pre+"'";
			ResultSet rs = stmt.executeQuery(Quary);
			while (rs.next()){
				String Quary1 = "DELETE FROM moodle.precourses WHERE course_id='" + crs +"' AND pre_id='"+ pre+"'";		
				stmt.executeUpdate(Quary1);
				return 1;
			}
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
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

	public static ArrayList<Assigenment> setTableViewStudentCourseAssigenment(String courseid) {
		Statement stmt;
		ArrayList<Assigenment> lst = new ArrayList<>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM moodle.teacherassingment where CourseId='" + courseid +"'");
				while (rs.next()) {
					// Print out the values
					Assigenment ass = new Assigenment();
					try {
						
						ass.setAssId(rs.getString(1));
						ass.setFileid(rs.getString(2));
						ass.setDueDate(rs.getDate(3));
						ass.setUserId(rs.getString(4));
						ass.setCourseid(rs.getString(5));
						lst.add(ass);				
					}

					catch (Exception e) {
						e.printStackTrace();
					}
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
	/*
	public static Class ClassCourseDetails(String Cid) {
		Statement stmt;
		Course cl = new Course();
		ArrayList<Course> clst=new ArrayList<>();

		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.classcourse where classid='" + Cid + "'");
			while (rs.next()) {
				// Print out the values

				try {
					cl.setCourseId(rs.getString(2));
					cl.setTeachid(rs.getString(3));
					clst.add(cl);
				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}
			rs.close();
			Connect.close();
			return clst;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clst;
	}
	*/

	public static int UploadFile(File file) throws Exception {
		try {
			Connection conn = Connect.getConnection();
	        String Quary = "INSERT INTO moodle.studentassignment (Assid,Studid,Courseid,Semid,Date,Fileid,path) VALUES (?,?,?,?,?,?,?)";
	        InputStream InputStream =new FileInputStream(file.getPath());
	        PreparedStatement stmt = conn.prepareStatement(Quary);
	        String Assid="test",Studid="11111",Courseid="1201",Semid="011",Date="2017-06-22";
	        
	        stmt.setString(1,Assid );
	        stmt.setString(2,Studid );
	        stmt.setString(3,Courseid );
	        stmt.setString(4,Semid );
	        stmt.setString(6,"1234" );
	      //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	       // java.util.Date date=sdf.parse(Date);
	        //stmt.setDate(5,"2017-06-22");
	        stmt.setBinaryStream(7,InputStream,(long)file.length());
	        stmt.executeUpdate();
	        JOptionPane.showMessageDialog(null, "File stored successfully!");
			}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
			}
		return 1;
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
	public static ArrayList<Reports> createReportEntity() {
		Statement stmt;
		ArrayList<Reports> rep =new ArrayList<Reports>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.reports");
			while (rs.next()) {
				Reports re=new Reports();
				try {
					re.setRepId(rs.getString(1));
					re.setType(Integer.parseInt(rs.getString(2)));
					re.setRepName(rs.getString(3));
					rep.add(re);
				} 
				catch (Exception e) {
					re.setRepId("-1");
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
			return rep;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rep;
	}
	
	public static ArrayList<Teacher> createTeacherEntity() {
		Statement stmt;
		ArrayList<Teacher> tec =new ArrayList<Teacher>();
		ArrayList<Teacher> tecup =new ArrayList<Teacher>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.teachers");
			while (rs.next()) {
				Teacher teacher=new Teacher();
				try {
					teacher.setTecId(rs.getString(3));
					tec.add(teacher);
				} 
				catch (Exception e) {
					teacher.setTecId("-1");
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			for(int j=0; j<tec.size();j++){
				ResultSet rs = stmt.executeQuery(
						"SELECT * FROM moodle.users where Id='" + tec.get(j).getTecId() +"'");
				while (rs.next()) {
					Teacher teacher=new Teacher();
					try {
						teacher.setTecId(tec.get(j).getTecId());
						teacher.setTecName(rs.getString(2));
						tecup.add(teacher);
					} 
					catch (Exception e) {
						teacher.setTecId("-1");
						e.printStackTrace();
					}
				}
				rs.close();
				Connect.close();
			}
			return tecup;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tecup;
	}
	
	public static ArrayList<Class> createClassEntity() {
		Statement stmt;
		ArrayList<Class> cla =new ArrayList<Class>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.class");
			while (rs.next()) {
				Class c1 = new Class();
				try {
					c1.setClassId(rs.getString(1));
					c1.setName(rs.getString(2));
					c1.setMAXStudent(Integer.parseInt(rs.getString(3)));
					cla.add(c1);
				} 
				catch (Exception e) {
					c1.setClassId("-1");
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
			return cla;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cla;
	}
	
	public static ArrayList<Semester> createSemesterEntity() {
		Statement stmt;
		ArrayList<Semester> se =new ArrayList<Semester>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.semester");
			while (rs.next()) {
				Semester sem = new Semester();
				try {
					sem.setSemId(rs.getString(1));
					sem.setCurrentStatus(Integer.parseInt(rs.getString(2)));
					sem.setNo_week(Integer.parseInt(rs.getString(3)));
					se.add(sem);
				} 
				catch (Exception e) {
					sem.setSemId("-1");
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
			return se;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return se;
	}
	

//*******************
	/**Bar Parent
	 * 	
	 * @param Sid
	 * @return
	 */
		
		public static ArrayList<Student> parSetStudentComboBox(String Pid) {
			Statement stmt;
			ArrayList<Student> lst = new ArrayList<Student>();
			try {
				Connection conn = Connect.getConnection();
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT moodle.student.sid, moodle.users.Fullname, moodle.student.avg, moodle.student.classid FROM "
						+ "moodle.student, moodle.users WHERE moodle.student.parentid='" + Pid +"' AND moodle.users.Id=moodle.student.sid");
		

				while (rs.next()) {
					// Print out the values
					try {
						Student stu = new Student();	
						stu.setId(rs.getString(1));
						stu.setName(rs.getString(2));
						stu.setAvg(rs.getFloat(3));
						stu.setClassid(rs.getString(4));
						stu.setParentId(Pid);
						lst.add(stu);
					}

					catch (Exception e) {
						e.printStackTrace();
					}
				}

				rs.close();
				Connect.close();
				return lst;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return lst;
		}
		public static ArrayList<Student> StudentsList(){
			Statement stmt;
			ArrayList<Student> lst = new ArrayList<Student>();

			try {			
				Connection conn = Connect.getConnection();
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.student");

				while (rs.next()) {
					try {
						Student stu = new Student();	
						stu.setParentId(rs.getString(1));
						stu.setAvg(rs.getFloat(2));
						stu.setId(rs.getString(3));
						stu.setClassid(rs.getString(4));

						lst.add(stu);
						}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				rs.close();
				Connect.close();
				return lst;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return lst;
		}
		public static float avgOneStudent(String Sid) {
			
			float sum=0;
			ArrayList<Course> lst=StudentCourse(Sid);
			for(Course c:lst)
				sum+=c.getGrade();
			float avg= sum/(float)lst.size();

			
			Statement stmt;
			try {
				Connection conn = Connect.getConnection();
				stmt = conn.createStatement();
				String Query = "UPDATE moodle.student SET avg='"+avg+"' WHERE sid='"+Sid+"'";
				stmt.executeUpdate(Query);
				Connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return avg;
		}
		
		public static void BlockParent(String Pid) {
			Statement stmt;
			int one=1;
			try {
				Connection conn = Connect.getConnection();
				stmt = conn.createStatement();
				String Query = "UPDATE moodle.users SET isBlocked =" + 1 + " WHERE Id ='"+Pid+"'";
				stmt.executeUpdate(Query);
				Connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		public static void unBlockParent(String Pid) {
			Statement stmt;
			
			try {
				Connection conn = Connect.getConnection();
				stmt = conn.createStatement();
				String Query = "UPDATE moodle.users SET isBlocked = " + 0 + " WHERE Id ='"+Pid+"'";
				stmt.executeUpdate(Query);
				Connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		public static ArrayList<Class> TeacherClassList(String teacherid) {
			Statement stmt;
			ArrayList<Class> se =new ArrayList<Class>();
			try {
				Connection conn = Connect.getConnection();
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT * FROM moodle.classcourse where tid='" + teacherid +"'");
				while (rs.next()) {
					Class c = new Class();
					try {
						c.setCourseid(rs.getString(1));
						c.setClassId(rs.getString(2));
						c.setTeachid(rs.getString(3));
						c.setSemid(rs.getString(4));
						se.add(c);
					} 
					catch (Exception e) {
						c.setClassId("-1");
						e.printStackTrace();
					}
				}
				rs.close();
				Connect.close();
				return se;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return se;
		}
		
		public static Teacher TecNameToId(String tecname) {
			Statement stmt;
			Teacher t = new Teacher();
			try {
				Connection conn = Connect.getConnection();
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT * FROM moodle.users where FullName='" + tecname + "'");
				while (rs.next()) {
					try {
						t.setTecId(rs.getString(1));
	
					} 
					catch (Exception e) {
						t.setTecId("-1");
						e.printStackTrace();
					}
				}
				rs.close();
				Connect.close();
				return t;
			} catch (SQLException e) {
				t.setTecId("-1");
				e.printStackTrace();
			}
			return t;
		}
		
		
	
	@SuppressWarnings("unused")
	private static ResultSet executeUpdate(String quary) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
