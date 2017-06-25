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
	public static boolean AddStudentToClass(String par[]){
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute(
					"UPDATE `moodle`.`student` SET `classid`='"+par[1]+"' WHERE `sid`='"+par[0]+"'");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static boolean RemoveStudentFromClass(String par[]){
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute(
					"UPDATE `moodle`.`student` SET classid=NULL WHERE `sid`='"+par[0]+"'");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static ArrayList<Course> getAvailableCoursesForClass(Class c){
		Statement stmt;
		ArrayList<Course> courseList = new ArrayList<Course>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.course");
			while (rs.next()) {
				try {
					Course c1 = new Course();
					c1.setCourseId(rs.getString(1));
					c1.setName(rs.getString(2));
					courseList.add(c1);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs = stmt.executeQuery(
					"SELECT * FROM moodle.classcourse where classid='"+c.getClassId()+"'");
			while (rs.next()) {
				try {
					Course c1 = new Course();
					c1.setCourseId((rs.getString(1)));
					for (int i=0; i<courseList.size(); i++){
						if (courseList.get(i).getCourseId().equals(c1.getCourseId())){
							courseList.remove(courseList.get(i));
							break;
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
			return courseList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Object[] getClassCourses(entity.Class c){
		Statement stmt;
		ArrayList<Course> courseList = new ArrayList<Course>();
		ArrayList<Teacher> teacherList = new ArrayList<Teacher>();
		String semid = getCurrentSemesterID();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.classcourse where classid='"+c.getClassId()+"' and semid= '" + semid + "';");
			while (rs.next()) {
				try {
					Course c1 = new Course();
					c1.setCourseId((rs.getString(1)));
					Teacher t = new Teacher();
					t.setId(rs.getString(3));
					c1.setSemid(rs.getString(4));
					teacherList.add(t);
					courseList.add(c1);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i=0; i<courseList.size(); i++){
				rs = stmt.executeQuery(
						"SELECT * FROM moodle.course where Courseid='" + courseList.get(i).getCourseId() + "'");
				while (rs.next()) {
					try {
						courseList.get(i).setName((rs.getString(2)));
						courseList.get(i).setUnit(rs.getString(3));
						courseList.get(i).setHours(rs.getInt(4));
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				rs = stmt.executeQuery(
						"SELECT * FROM moodle.users where Id='" + teacherList.get(i).getId() + "'");
				while (rs.next()) {
					try {
						teacherList.get(i).setName((rs.getString(2)));
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			rs.close();
			Connect.close();
			Object o[] = {courseList,teacherList};
			return o;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<Student> getStudentInNoClass(String classId){
		Statement stmt;
		ArrayList<Student> studList = new ArrayList<Student>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.student where classid is NULL");
			while (rs.next()) {
				try {
					Student s = new Student();
					s.setParentId((rs.getString(1)));
					s.setAvg((rs.getFloat(2)));
					s.setId(rs.getString(3));
					s.setClassid(classId);
					studList.add(s);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i=0; i<studList.size(); i++){
				rs = stmt.executeQuery(
						"SELECT * FROM moodle.users where Id='" + studList.get(i).getId() + "'");
				while (rs.next()) {
					try {
						studList.get(i).setName((rs.getString(2)));
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			rs.close();
			Connect.close();
			return studList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static ArrayList<Teacher> getTeachersForCourse(String courseid){
		Statement stmt;
		ArrayList<Teacher> teacherList = new ArrayList<Teacher>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.teachers");// where classid='" + classId + "'");
			while (rs.next()) {
				try {
					Teacher t = new Teacher();
					t.setMaxHours(rs.getInt(1));
					t.setIntership(rs.getString(2));
					t.setId(rs.getString(3));
					t.setUnit(rs.getString(4));
					teacherList.add(t);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i=0; i<teacherList.size(); i++){
				rs = stmt.executeQuery(
						"SELECT * FROM moodle.users where Id='" + teacherList.get(i).getId() + "'");
				while (rs.next()) {
					try {
						teacherList.get(i).setName((rs.getString(2)));
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			rs.close();
			Connect.close();
			return teacherList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static boolean RemoveClassFromCourse(String par[]){
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute(
					"DELETE FROM `moodle`.`classcourse` WHERE `courseid`='"+par[0]+"' and`classid`='" + par[1] + "' and`semid`='"+par[2]+"'");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static boolean OpenNewSemester(){
		Statement stmt;
		boolean flag = false;
		String currsem = getCurrentSemesterID();
		int currsemint = Integer.parseInt(currsem);
		currsemint++;
		String newsem = ""+currsemint;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute(
					"UPDATE `moodle`.`semester` SET `currentStatus`='0' WHERE `semid`='" + currsem + "';");
			rs = stmt.execute(
					"INSERT INTO `moodle`.`semester` (`semid`, `currentStatus`, `week`) VALUES ('" + newsem + "', '1', '0');");
			
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ArrayList<Course> courseList = new ArrayList<Course>();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.managercourses");
			while (rs.next()) {
				try {
					Course c = new Course();
					c.setCourseId(rs.getString(1));
					c.setName(rs.getString(2));
					c.setUnit(rs.getString(3));
					c.setHours(rs.getInt(4));
					courseList.add(c);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			for (int i=0; i<courseList.size(); i++){
				Course c = courseList.get(i);
				try{
					boolean rs2 = stmt.execute(
						"INSERT INTO `moodle`.`course` (`Courseid`, `CourseName`, `unit`, `hours`) VALUES ('" + c.getCourseId() + "', '"+c.getName() +"', '"+ c.getUnit() +"', '"+c.getHours()+"');");
				}catch (SQLException e) {
					try{
						boolean rs2 = stmt.execute(
					
							"UPDATE `moodle`.`course` SET `CourseName`='"+c.getName()+"', `hours`='" + c.getHours() + "'WHERE `Courseid`='"+c.getCourseId() +"';");
					}catch (SQLException e1) {
						e1.printStackTrace();
					}

				}
			}
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static boolean AddStudentToCourse(String par[]){
		//par = {studentid,semid,courseid,coursename}
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute(
					"INSERT INTO `moodle`.`studentcourse` (`studid`, `semid`, `courseid`, `grade`, `cname`) VALUES ('"+par[0]+"', '" + par[1] + "', '"+ par[2] + "', '"+"0"+"', '"+par[3]+"')");

			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static boolean AddClassToCourse(String par[]){
		//par = {courseid,classid,tid,semid}
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute(
					"INSERT INTO `moodle`.`classcourse` (`courseid`, `classid`, `tid`, `semid`) VALUES ('"+par[0]+"', '" + par[1] + "', '"+ par[2] + "', '"+par[3]+"')");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static boolean RemoveStudentFromCourse(String par[]){
		// par = {studid,courseid,semid}
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute(
					"DELETE FROM `moodle`.`studentcourse` WHERE `studid`='"+par[0]+"' and`semid`='" + par[2] + "' and`courseid`='"+par[1]+"'");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static String getCurrentSemesterID(){
		Statement stmt;
		String res;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.semester where currentStatus='1'");
			while (rs.next()) {
				try {
					res = rs.getString(1);
					return res;
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
		return null;
	}
	public static ArrayList<Student> getStudentInClass(String classId){
		Statement stmt;
		ArrayList<Student> studList = new ArrayList<Student>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.student where classid='" + classId + "'");
			while (rs.next()) {
				try {
					Student s = new Student();
					s.setParentId((rs.getString(1)));
					s.setAvg((rs.getFloat(2)));
					s.setId(rs.getString(3));
					s.setClassid(classId);
					studList.add(s);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i=0; i<studList.size(); i++){
				rs = stmt.executeQuery(
						"SELECT * FROM moodle.users where Id='" + studList.get(i).getId() + "'");
				while (rs.next()) {
					try {
						studList.get(i).setName((rs.getString(2)));
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			rs.close();
			Connect.close();
			return studList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static ArrayList<entity.Class> getAllClasses(){
		Statement stmt;
		ArrayList<entity.Class> classList = new ArrayList<entity.Class>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.class");
			while (rs.next()) {
				try {
					entity.Class c = new Class();
					c.setClassId((rs.getString(1)));
					c.setName((rs.getString(2)));
					c.setMAXStudent(rs.getInt(3));
					classList.add(c);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
			return classList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
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
				String Query = "UPDATE moodle.users SET isBlocked =1 WHERE Id ='"+Pid+"'";
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
				String Query = "UPDATE moodle.users SET isBlocked = 0 WHERE Id ='"+Pid+"'";
				stmt.executeUpdate(Query);
				Connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		

	
	@SuppressWarnings("unused")
	private static ResultSet executeUpdate(String quary) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
