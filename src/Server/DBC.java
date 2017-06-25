package Server;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Course;
import entity.Student;
import entity.Teacher;
import entity.Unit;
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
	
	@SuppressWarnings("unused")
	private static ResultSet executeUpdate(String quary) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
