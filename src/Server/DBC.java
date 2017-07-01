package Server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.*;

import entity.Assigenment;
import entity.Course;
import entity.Reports;
import entity.Requests;
import entity.Semester;
import entity.Student;
import entity.Studentass;
import entity.Teacher;
import entity.Unit;
import entity.User;
import entity.Class;
import Server.Connect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class contains all the interaction with the Data-Base *
 */

public class DBC {

	public static User LOGIN(String userName, String password) {
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
				} catch (Exception e) {
					lst.setId("-1");
					e.printStackTrace();
				}

			}
			rs.close(); // Before every close.stage we need to update
						// isConnected=0
			if (lst.getIsConnected() == 0 && lst.getBlocked() == 0) {
				String Quary = ("UPDATE moodle.users set isConnected = " + 1 + " where UserName ='" + userName
						+ "' AND Password='" + password + "'");
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

	/**
	 * This method logging out (setting IsConnected=0) all the users when we
	 * starting the server.
	 */

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
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.student where sid='" + Id + "'");
			while (rs.next()) {
				// Print out the values

				try {
					avgOneStudent(Id);
					stud.setParentId(rs.getString(1));
					stud.setAvg(rs.getFloat(2));
					stud.setId(rs.getString(3) == null ? "-1" : rs.getString(3));
					stud.setClassid(rs.getString(4));
				} catch (Exception e) {
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

	public static Course getCourseByID(String cid) {
		Statement stmt;
		Course c = null;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.course where Courseid='" + cid + "'");
			while (rs.next()) {
				try {
					c = new Course();
					c.setCourseId(cid);
					c.setName(rs.getString(2));
					c.setUnit(rs.getString(3));
					c.setHours(rs.getInt(4));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public static Teacher Teacherdetails(String id) {
		Statement stmt;
		Teacher lst = new Teacher();

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.teachers where tid='" + id + "'");
			// where UserName="+userName+"AND where Password="+password
			while (rs.next()) {
				// Print out the values

				try {
					lst.setMaxHours(Integer.parseInt(rs.getString(1)));
					lst.setIntership(rs.getString(2));
					lst.setId(rs.getString(3) == null ? "-1" : rs.getString(3));
					lst.setUnit(rs.getString(4));

				} catch (Exception e) {
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

	public static int UpdateUnit(String id, String Unit) {
		Statement stmt;
		Teacher lst = new Teacher();
		boolean flag = false;

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.teachers where Id='" + id + "'");
			// where UserName="+userName+"AND where Password="+password
			while (rs.next()) {
				// Print out the values

				try {
					flag = true;
				} catch (Exception e) {
					lst.setId("-1");
					e.printStackTrace();
				}

			}
			rs.close();

			String Quary = "UPDATE moodle.teachers SET Unit='" + Unit + "' WHERE Id=" + id + "";
			// String Quary = "update teac.Teachers set Teaching_Unit= '" + Unit
			// + "'" + " Where teac.Id="+Id;
			stmt.executeUpdate(Quary);
			// where UserName="+userName+"AND where Password="+password
			Connect.close();
			if (flag)
				return 1;
			return -1;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}

	public static String StudentExists(String sid) {
		Statement stmt;
		boolean flag = false;

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.student where sid='" + sid + "'");

			while (rs.next()) {
				try {
					flag = true;
				} catch (Exception e) {
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

	public static String ParentExists(String pid) {
		Statement stmt;
		boolean flag = false;

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.users where Id='" + pid + "' AND Role='6'");

			while (rs.next()) {
				try {
					flag = true;
				} catch (Exception e) {
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

	public static int DefineClass(entity.Class c) {
		Statement stmt;
		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();

			boolean rs = stmt.execute("INSERT INTO `moodle`.`class` (`Classid`, `name`, `MAXStudent`) VALUES ('"
					+ c.getClassId() + "', '" + c.getName() + "', '" + c.getMAXStudent() + "');");

			Connect.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return 0;
	}

	public static int AddStudent(Student s) {
		Statement stmt;

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();

			boolean rs = stmt.execute(
					"INSERT INTO moodle.users (`Id`, `Fullname`, `password`, `UserName`, `Role`, `isBlocked`, `isConnected`) VALUES ('"
							+ s.getId() + "', '" + s.getName() + "', '" + s.getPassword() + "', '" + s.getUserName()
							+ "', '" + "4" + "', '" + 0 + "', '" + 0 + "');\n");
			rs = stmt.execute("INSERT INTO `moodle`.`student` (`parentid`, `avg`, `sid`) VALUES ('" + s.getParentId()
					+ "', '" + 0 + "', '" + s.getId() + "')");

			if (rs) {
				try {

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			// rs.close();
			Connect.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return 0;
	}

	public static boolean AddStudentToClass(String par[]) {
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt
					.execute("UPDATE `moodle`.`student` SET `classid`='" + par[1] + "' WHERE `sid`='" + par[0] + "'");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean RemoveStudentFromClass(String par[]) {
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute("UPDATE `moodle`.`student` SET classid=NULL WHERE `sid`='" + par[0] + "'");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static ArrayList<Course> getAvailableCoursesForClass(Class c) {
		Statement stmt;
		ArrayList<Course> courseList = new ArrayList<Course>();
		String semid = getCurrentSemesterID();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.course");
			while (rs.next()) {
				try {
					Course c1 = new Course();
					c1.setCourseId(rs.getString(1));
					c1.setName(rs.getString(2));
					courseList.add(c1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs = stmt.executeQuery("SELECT * FROM moodle.classcourse where classid='" + c.getClassId() + "' and semid='"
					+ semid + "'");
			while (rs.next()) {
				try {
					Course c1 = new Course();
					c1.setCourseId((rs.getString(1)));
					for (int i = 0; i < courseList.size(); i++) {
						if (courseList.get(i).getCourseId().equals(c1.getCourseId())) {
							courseList.remove(courseList.get(i));
							break;
						}
					}
				} catch (Exception e) {
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

	public static Object[] getClassCourses(entity.Class c) {
		Statement stmt;
		ArrayList<Course> courseList = new ArrayList<Course>();
		ArrayList<Teacher> teacherList = new ArrayList<Teacher>();
		String semid = getCurrentSemesterID();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.classcourse where classid='" + c.getClassId()
					+ "' and semid= '" + semid + "';");
			while (rs.next()) {
				try {
					Course c1 = new Course();
					c1.setCourseId((rs.getString(1)));
					Teacher t = new Teacher();
					t.setId(rs.getString(3));
					c1.setSemid(rs.getString(4));
					teacherList.add(t);
					courseList.add(c1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < courseList.size(); i++) {
				rs = stmt.executeQuery(
						"SELECT * FROM moodle.course where Courseid='" + courseList.get(i).getCourseId() + "'");
				while (rs.next()) {
					try {
						courseList.get(i).setName((rs.getString(2)));
						courseList.get(i).setUnit(rs.getString(3));
						courseList.get(i).setHours(rs.getInt(4));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				rs = stmt.executeQuery("SELECT * FROM moodle.users where Id='" + teacherList.get(i).getId() + "'");
				while (rs.next()) {
					try {
						teacherList.get(i).setName((rs.getString(2)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
			rs.close();
			Connect.close();
			Object o[] = { courseList, teacherList };
			return o;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<Student> getStudentInNoClass(String classId) {
		Statement stmt;
		ArrayList<Student> studList = new ArrayList<Student>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.student where classid is NULL");
			while (rs.next()) {
				try {
					Student s = new Student();
					s.setParentId((rs.getString(1)));
					s.setAvg((rs.getFloat(2)));
					s.setId(rs.getString(3));
					s.setClassid(classId);
					studList.add(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < studList.size(); i++) {
				rs = stmt.executeQuery("SELECT * FROM moodle.users where Id='" + studList.get(i).getId() + "'");
				while (rs.next()) {
					try {
						studList.get(i).setName((rs.getString(2)));
					} catch (Exception e) {
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

	public static ArrayList<entity.Class> getClassListForTeacherInCourse(Object par[]) {
		// par = {User, Course};
		Statement stmt;
		ArrayList<entity.Class> classList = new ArrayList<entity.Class>();
		String semid = getCurrentSemesterID();
		User u = (User) par[0];
		Course c = (Course) par[1];
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.classcourse where courseid='" + c.getCourseId()
					+ "' and tid='" + u.getId() + "' and semid='" + semid + "'");
			while (rs.next()) {
				try {
					entity.Class temp = new entity.Class();
					temp.setClassId(rs.getString(2));
					classList.add(temp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < classList.size(); i++) {
				rs = stmt.executeQuery(
						"SELECT * FROM moodle.class where Classid='" + classList.get(i).getClassId() + "'");
				while (rs.next()) {
					try {
						classList.get(i).setName((rs.getString(2)));
					} catch (Exception e) {
						e.printStackTrace();
					}
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

	public static ArrayList<Teacher> getTeachersForCourse(String courseid) {
		Statement stmt;
		ArrayList<Teacher> teacherList = new ArrayList<Teacher>();
		Course c = getCourseByID(courseid);
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.teachers where Unit='" + c.getUnit() + "'");
			while (rs.next()) {
				try {
					Teacher t = new Teacher();
					t.setMaxHours(rs.getInt(1));
					t.setIntership(rs.getString(2));
					t.setId(rs.getString(3));
					t.setUnit(rs.getString(4));
					teacherList.add(t);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < teacherList.size(); i++) {
				rs = stmt.executeQuery("SELECT * FROM moodle.users where Id='" + teacherList.get(i).getId() + "'");
				while (rs.next()) {
					try {
						teacherList.get(i).setName((rs.getString(2)));
					} catch (Exception e) {
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

	public static boolean UserIdExists(String uid) {
		// Role={1-Secretary, 2-Manager, 3-Teacher, 4-Student, 5-System manager,
		// 6- Parent, 7- Manager&Teacher}
		boolean flag = false;
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.users where Id='" + uid + "'");
			while (rs.next()) {
				try {
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static ArrayList<User> getUsersByRole(int role) {
		// Role={1-Secretary, 2-Manager, 3-Teacher, 4-Student, 5-System manager,
		// 6- Parent, 7- Manager&Teacher}
		ArrayList<User> userList = new ArrayList<User>();
		Statement stmt;

		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.users where Role='" + role + "'");
			while (rs.next()) {
				try {
					User u = new User();
					u.setId(rs.getString(1));
					u.setName(rs.getString(2));
					u.setRole(rs.getInt(5));
					userList.add(u);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	public static ArrayList<Course> getAllCoursesInCurrSemester() {
		// Role={1-Secretary, 2-Manager, 3-Teacher, 4-Student, 5-System manager,
		// 6- Parent, 7- Manager&Teacher}
		ArrayList<Course> courseList = new ArrayList<Course>();
		Statement stmt;
		String semid = getCurrentSemesterID();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.semestercourse where semid='" + semid + "'");
			while (rs.next()) {
				try {
					Course c = new Course();
					c.setCourseId(rs.getString(2));
					// c.setName(rs.getString(5));
					courseList.add(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			for (int i = 0; i < courseList.size(); i++) {
				Course c = courseList.get(i);
				ResultSet rs1 = stmt
						.executeQuery("SELECT * FROM moodle.course where Courseid='" + c.getCourseId() + "'");
				while (rs1.next()) {
					try {
						c.setName(rs1.getString(2));
						c.setUnit(rs1.getString(3));
						c.setHours(rs1.getInt(4));
						// courseList.add(c);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				rs1.close();
			}
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseList;
	}

	public static Requests getRequestByID(String rid) {
		Statement stmt;
		Requests r = null;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.requests where Reqid='" + rid + "'");
			while (rs.next()) {
				try {
					r = new Requests();
					r.setReqId(rid);
					r.setRequestDescription(rs.getString(2));
					r.setStatus(rs.getInt(3));
					r.setReqType(rs.getInt(4));
					r.setUserId(rs.getString(5));
					r.setCourseId(rs.getString(6));
					r.setClassId(rs.getString(7));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	public static entity.Class getClassByID(String cid) {
		Statement stmt;
		entity.Class c = null;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.class where Classid='" + cid + "'");
			while (rs.next()) {
				try {
					c = new entity.Class();
					c.setClassId(cid);
					c.setName(rs.getString(2));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public static User getUserByID(String uid) {
		Statement stmt;
		String semid = getCurrentSemesterID();
		User u = null;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.users where Id='" + uid + "'");
			while (rs.next()) {
				try {
					u = new User();
					u.setId(uid);
					u.setName(rs.getString(2));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

	public static ArrayList<Course> getUserCoursesInCurrSemester(User u) {
		// Role={1-Secretary, 2-Manager, 3-Teacher, 4-Student, 5-System manager,
		// 6- Parent, 7- Manager&Teacher}
		ArrayList<Course> courseList = new ArrayList<Course>();
		Statement stmt;
		String semid = getCurrentSemesterID();
		if (u.getRole() == 4) {
			try {
				Connection conn = Connect.getConnection();
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.studentcourse where studid='" + u.getId()
						+ "' and semid='" + semid + "'");
				while (rs.next()) {
					try {
						Course c = new Course();
						c.setCourseId(rs.getString(3));
						c.setName(rs.getString(5));
						courseList.add(c);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				rs.close();
				Connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (u.getRole() == 3) {
			try {
				Connection conn = Connect.getConnection();
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT * FROM moodle.classcourse where tid='" + u.getId() + "' and semid='" + semid + "'");
				while (rs.next()) {
					try {
						Course c = new Course();
						c.setCourseId(rs.getString(1));
						courseList.add(c);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				rs.close();
				for (int i = 0; i < courseList.size(); i++) {
					rs = stmt.executeQuery(
							"SELECT * FROM moodle.course where Courseid='" + courseList.get(i).getCourseId() + "'");
					while (rs.next()) {
						try {
							courseList.get(i).setName(rs.getString(2));
							courseList.get(i).setUnit(rs.getString(3));
							courseList.get(i).setHours(rs.getInt(4));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					rs.close();
				}
				Connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return courseList;
	}

	public static boolean DeleteClass(entity.Class c) {
		Statement stmt;
		ArrayList<Course> courseList = (ArrayList<Course>) (getClassCourses(c)[0]);
		ArrayList<Student> studentList = (ArrayList<Student>) (getStudentInClass(c.getClassId()));
		String semid = getCurrentSemesterID();
		for (int j = 0; j < studentList.size(); j++) {
			String par[] = { studentList.get(j).getId(), null, semid };
			RemoveStudentFromClass(par);
		}
		for (int i = 0; i < courseList.size(); i++) {
			for (int j = 0; j < studentList.size(); j++) {
				String par[] = { studentList.get(j).getId(), courseList.get(i).getCourseId(), semid };
				RemoveStudentFromCourse(par);
			}
			String par[] = { courseList.get(i).getCourseId(), c.getClassId(), semid };

			RemoveClassFromCourse(par);
		}
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt
					.execute("UPDATE `moodle`.`class` SET `status`='DELETED' WHERE `classid`='" + c.getClassId() + "'");
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean RemoveClassFromCourse(String par[]) {
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute("DELETE FROM `moodle`.`classcourse` WHERE `courseid`='" + par[0]
					+ "' and`classid`='" + par[1] + "' and`semid`='" + par[2] + "'");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean OpenNewSemester() {
		Statement stmt;
		boolean flag = false;
		String currsem = getCurrentSemesterID();
		if (currsem == null)
			currsem = "0";
		int currsemint = Integer.parseInt(currsem);
		currsemint++;
		String newsem = "" + currsemint;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt
					.execute("UPDATE `moodle`.`semester` SET `currentStatus`='0' WHERE `semid`='" + currsem + "';");
			rs = stmt.execute("INSERT INTO `moodle`.`semester` (`semid`, `currentStatus`, `week`) VALUES ('" + newsem
					+ "', '1', '0');");

			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ArrayList<Course> courseList = new ArrayList<Course>();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.managercourses");
			while (rs.next()) {
				try {
					Course c = new Course();
					c.setCourseId(rs.getString(1));
					c.setName(rs.getString(2));
					c.setUnit(rs.getString(3));
					c.setHours(rs.getInt(4));
					courseList.add(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			for (int i = 0; i < courseList.size(); i++) {
				Course c = courseList.get(i);
				try {
					boolean rs2 = stmt.execute(
							"INSERT INTO `moodle`.`course` (`Courseid`, `CourseName`, `unit`, `hours`) VALUES ('"
									+ c.getCourseId() + "', '" + c.getName() + "', '" + c.getUnit() + "', '"
									+ c.getHours() + "');");
				} catch (SQLException e) {
					try {
						boolean rs2 = stmt.execute("UPDATE `moodle`.`course` SET `CourseName`='" + c.getName()
								+ "', `hours`='" + c.getHours() + "'WHERE `Courseid`='" + c.getCourseId() + "';");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				try {
					boolean rs2 = stmt.execute("INSERT INTO `moodle`.`semestercourse` (`semid`, `courseid`) VALUES ('"
							+ newsem + "', '" + c.getCourseId() + "');");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean CheckStudentPreReq(String par[]) {
		// par = {studentid,semid,courseid,coursename}
		Statement stmt;
		boolean flag = false;
		ArrayList<Course> preList = new ArrayList<Course>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.precourses where course_id='" + par[2] + "'");
			while (rs.next()) {
				try {
					Course c = new Course();
					c.setCourseId(rs.getString(2));
					preList.add(c);
				} catch (Exception e) {
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
			if (preList.size() == 0)
				flag = true;
			else
				for (int i = 0; i < preList.size(); i++) {
					ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.studentcourse WHERE `studid`='" + par[0]
							+ "' and`courseid`='" + preList.get(i).getCourseId() + "'");
					while (rs.next()) {
						try {
							if (rs.getInt(4) > 55)
								flag = true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					rs.close();
				}
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean AddStudentToCourse(String par[]) {
		// par = {studentid,semid,courseid,coursename}
		if (par[1] == null)
			par[1] = getCurrentSemesterID();
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute(
					"INSERT INTO `moodle`.`studentcourse` (`studid`, `semid`, `courseid`, `grade`, `cname`) VALUES ('"
							+ par[0] + "', '" + par[1] + "', '" + par[2] + "', '" + "-1" + "', '" + par[3] + "')");

			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean AddClassToCourse(String par[]) {
		// par = {courseid,classid,tid,semid}
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt
					.execute("INSERT INTO `moodle`.`classcourse` (`courseid`, `classid`, `tid`, `semid`) VALUES ('"
							+ par[0] + "', '" + par[1] + "', '" + par[2] + "', '" + par[3] + "')");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static ArrayList<Requests> getActiveRequests() {
		ArrayList<Requests> reqList = new ArrayList<Requests>();
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM `moodle`.`requests` WHERE active='YES'");
			while (rs.next()) {
				try {
					Requests r = new Requests();
					r.setReqId(rs.getString(1));
					r.setRequestDescription(rs.getString(2));
					r.setStatus(rs.getInt(3));
					r.setReqType(rs.getInt(4));
					r.setUserId(rs.getString(5));
					r.setCourseId(rs.getString(6));

					reqList.add(r);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reqList;
	}

	public static boolean AddNewRequest(Requests r) {
		String lastReqID = "";
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM `moodle`.`requests` ");
			while (rs.next()) {
				try {
					lastReqID = rs.getString(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String newReqID;
		if (lastReqID.equals(""))
			newReqID = "1";
		else {
			int lastReq = Integer.parseInt(lastReqID);
			lastReq++;
			newReqID = "" + lastReq;
		}
		r.setReqId(newReqID);
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs;
			if (r.getReqType() == 3)
				rs = stmt.execute(
						"INSERT INTO `moodle`.`requests` (`Reqid`, `requestDesc`, `status`, `reqType`, `userid`, `courseid`, `classid`) VALUES ('"
								+ r.getReqId() + "', '" + r.getRequestDescription() + "', '" + r.getStatus() + "', '"
								+ r.getReqType() + "', '" + r.getUserId() + "', '" + r.getCourseId() + "', '"
								+ r.getClassId() + "')");
			else
				rs = stmt.execute(
						"INSERT INTO `moodle`.`requests` (`Reqid`, `requestDesc`, `status`, `reqType`, `userid`, `courseid`) VALUES ('"
								+ r.getReqId() + "', '" + r.getRequestDescription() + "', '" + r.getStatus() + "', '"
								+ r.getReqType() + "', '" + r.getUserId() + "', '" + r.getCourseId() + "')");

			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean UpdateMaxStudents(Object par[]) {
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute(
					"UPDATE `moodle`.`class` SET `MAXStudent`='" + par[1] + "' WHERE `Classid`='" + par[0] + "';");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean DeActivateRequest(String rid) {
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute("UPDATE `moodle`.`requests` SET `active`='NO' WHERE `Reqid`='" + rid + "';");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean ChangeTeacherAppointment(String par[]) {
		// par = {courseid,classid,oldTeacherid,semid,newTeacherID}
		if (par[3] == null)
			par[3] = getCurrentSemesterID();
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt
					.execute("UPDATE `moodle`.`classcourse` SET `tid`='" + par[4] + "' WHERE `courseid`='" + par[0]
							+ "' and`classid`='" + par[1] + "' and`semid`='" + par[3] + "' and`tid`='" + par[2] + "';");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean RemoveStudentFromCourse(String par[]) {
		// par = {studid,courseid,semid}
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			boolean rs = stmt.execute("DELETE FROM `moodle`.`studentcourse` WHERE `studid`='" + par[0]
					+ "' and`semid`='" + par[2] + "' and`courseid`='" + par[1] + "'");
			flag = true;
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static String getCurrentSemesterID() {
		Statement stmt;
		String res;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.semester where currentStatus='1'");
			while (rs.next()) {
				try {
					res = rs.getString(1);
					return res;
				} catch (Exception e) {
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

	public static ArrayList<Student> getStudentInClass(String classId) {
		Statement stmt;
		ArrayList<Student> studList = new ArrayList<Student>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.student where classid='" + classId + "'");
			while (rs.next()) {
				try {
					Student s = new Student();
					s.setParentId((rs.getString(1)));
					s.setAvg((rs.getFloat(2)));
					s.setId(rs.getString(3));
					s.setClassid(classId);
					studList.add(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < studList.size(); i++) {
				rs = stmt.executeQuery("SELECT * FROM moodle.users where Id='" + studList.get(i).getId() + "'");
				while (rs.next()) {
					try {
						studList.get(i).setName((rs.getString(2)));
					} catch (Exception e) {
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

	public static ArrayList<entity.Class> getActiveClasses() {
		Statement stmt;
		ArrayList<entity.Class> classList = new ArrayList<entity.Class>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.class where status='ACTIVE'");
			while (rs.next()) {
				try {
					entity.Class c = new Class();
					c.setClassId((rs.getString(1)));
					c.setName((rs.getString(2)));
					c.setMAXStudent(rs.getInt(3));
					classList.add(c);
				} catch (Exception e) {
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

	public static boolean UserNameExists(String name) {
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.users where UserName='" + name + "'");

			while (rs.next()) {
				try {
					flag = true;
				} catch (Exception e) {
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

	public static boolean classIDExists(String id) {
		Statement stmt;
		boolean flag = false;

		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.class where Classid='" + id + "'");

			while (rs.next()) {
				try {
					flag = true;
				} catch (Exception e) {
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

	/**
	 * This method take all the fields in requests table from DB.
	 *
	 * @return all the Requests in the DB
	 */
	public static ArrayList<Requests> RequestsInfo() {
		Statement stmt;
		ArrayList<Requests> lst = new ArrayList<Requests>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Quary = "SELECT * FROM moodle.requests";
			ResultSet rs = stmt.executeQuery(Quary);

			while (rs.next()) {
				Requests r = new Requests();
				try {
					r.setReqId(rs.getString(1));
					r.setUserId(rs.getString(2));
					r.setCourseId(rs.getString(3));
					r.setRequestDescription(rs.getString(4));
					r.setStatus(rs.getInt(5));
					r.setReqType(rs.getInt(6));
					lst.add(r);
				} catch (Exception e) {
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

	public static boolean classNameExists(String name) {
		Statement stmt;
		boolean flag = false;
		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.class where name='" + name + "'");

			while (rs.next()) {
				try {
					flag = true;
				} catch (Exception e) {
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

	/**
	 * This method will search for course student study in the DB
	 * @param Sid-will hold the id number of the Student that been searched
	 * @return return the course that been searched if it found
	 */
	public static ArrayList<Course> StudentCourse(String Sid) {
		Statement stmt;
		ArrayList<Course> lst = new ArrayList<Course>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Quary = "SELECT moodle.studentcourse.* FROM moodle.studentcourse WHERE moodle.studentcourse.studid='"
					+ Sid + "'";
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
					cu.setSemid("-1");
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

	/**
	 * This method will check if specific unit is in the DB
	 * @param unitid-will hold the id number of the unit that been checked
	 * @return return the unit that been checked if it found
	 */
	public static Unit UnitExists(String unitid) {
		Statement stmt;
		Unit lst = new Unit();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.unit where unitID='" + unitid + "'");
			while (rs.next()) {
				try {
					lst.setName(rs.getString(2));
					lst.setUnitId(rs.getString(1) == null ? "-1" : rs.getString(1));
				} catch (Exception e) {
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

	/**
	 * This method will check if specific course is in the DB
	 * @param coursid-will hold the id number of the course that been checked
	 * @return return the course that been checked if it found
	 */
	public static Course CourseExists(String courseid) {
		Statement stmt;
		Course lst = new Course();
		try {

			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.managercourses where courseid='" + courseid + "'");
			while (rs.next()) {
				try {
					lst.setName(rs.getString(2));
					lst.setCourseId(rs.getString(1) == null ? "-1" : rs.getString(1));
					lst.setUnit(rs.getString(3));
					lst.setHours(Integer.parseInt(rs.getString(4)));
				} catch (Exception e) {
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

	/**
	 * This method will insert a new course to the DB
	 * @param crs-will hold the details of the course that we want to insert
	 * @return return the course that insert to the DB; 
	 * 				(-1) in the courseidwill testify of failure
	 */
	public static Course DefineNewCourse(Course crs) {
		Statement stmt;
		Course lst = new Course(crs.getCourseId());
		lst.setName(crs.getName());
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();

			boolean rs = stmt
					.execute("INSERT INTO moodle.managercourses (`Courseid`, `CourseName`, `unit`, `hours`) VALUES ('"
							+ crs.getCourseId() + "', '" + crs.getName() + "', '" + crs.getUnit() + "', '"
							+ crs.getHours() + "');\n");
			if (rs) {
				try {
					return lst;
				} catch (Exception e) {
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

	/**
	 * This method will get a list of courses
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
					+ "WHERE moodle.managercourses.Courseid!= '" + crsid + "'";
			ResultSet rs = stmt.executeQuery(Quary);

			while (rs.next()) {
				Course cu = new Course();
				try {
					cu.setHours(Integer.parseInt(rs.getString(4)));
					cu.setUnit(rs.getString(3));
					cu.setName(rs.getString(2));
					cu.setCourseId(rs.getString(1));
					lst.add(cu);
				} catch (Exception e) {
					cu.setCourseId("1");
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

	/**
	 * This method will insert a new PreRequisite of a course to the DB
	 * @param crs-will hold the course id that we want to insert
	 * @param pre-will hold the prerequisite course id that we want to insert
	 * @return return 1 for success & (-1) for failure
	 */
	public static int DefinePreReq(String crs, String pre) {
		Statement stmt;
		boolean flag = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM moodle.precourses WHERE course_id='" + crs + "' AND pre_id='" + pre + "'");
			while (rs.next()) {
				try {
					flag = true;
					return -1;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (flag == false) {
				boolean rs1 = stmt.execute("INSERT INTO moodle.precourses (`Course_id`, `pre_id`) VALUES ('" + crs
						+ "', '" + pre + "');\n");
				if (rs1 == true) {
					try {
						return 1;
					} catch (Exception e) {
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

	/**
	 * This method will remove course from the DB
	 * @param crs-will hold the course id that we want to remove
	 * @return return 1 for success & (-1) for failure
	 */
	public static int RemoveCourse(String crs) {
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Quary = "DELETE FROM moodle.precourses WHERE course_id='" + crs + "' OR pre_id='" + crs + "'";
			stmt.executeUpdate(Quary);
			String Quary1 = "DELETE FROM moodle.managercourses WHERE Courseid='" + crs + "'";
			stmt.executeUpdate(Quary1);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * This method will update course name in the DB
	 * @param crs-will hold the details of the course that we want to update
	 * @return return 1 for success & (-1) for failure
	 */
	public static int RenameCourse(Course crs) {
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM moodle.managercourses WHERE courseid='" + crs.getCourseId() + "'");
			while (rs.next()) {
				try {
					if (crs.getName().equals(rs.getString(2))) {
						return -1;
					} else {
						String Query = ("UPDATE moodle.managercourses set CourseName = '" + crs.getName()
								+ "' WHERE courseid ='" + crs.getCourseId() + "'");
						stmt.executeUpdate(Query);
						Connect.close();
						return 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * This method will update course Weekly Hours in the DB
	 * @param crs-will hold the details of the course that we want to update
	 * @return return 1 for success & (-1) for failure
	 */
	public static int WeeklyHoursUpdate(Course crs) {
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM moodle.managercourses WHERE courseid='" + crs.getCourseId() + "'");
			while (rs.next()) {
				try {
					if (crs.getHours() == (Integer.parseInt(rs.getString(4)))) {
						return -1;
					} else {
						String Query = ("UPDATE moodle.managercourses set hours = '" + crs.getHours()
								+ "' WHERE courseid ='" + crs.getCourseId() + "'");
						stmt.executeUpdate(Query);
						Connect.close();
						return 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * This method will get a list of PreRequisite courses
	 * @param crsid-will hold the course id that we do not want to return his PreRequisite
	 * @return return list of PreRequisite courses
	 */
	public static ArrayList<Course> PreReqList(String crsid) {
		Statement stmt;
		ArrayList<Course> lst = new ArrayList<>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Quary = "SELECT moodle.precourses.* FROM moodle.precourses " + "WHERE moodle.precourses.course_id= '"
					+ crsid + "'";
			ResultSet rs = stmt.executeQuery(Quary);

			while (rs.next()) {
				Course cu = new Course();
				try {
					cu.setCourseId(rs.getString(2));
					// String Quary1 = "SELECT * FROM moodle.managercourses
					// WHERE courseid= '" + cu.getCourseId() +"'";
					// ResultSet rs1 = stmt.executeQuery(Quary1);
					// String a=rs1.getString(2);
					// cu.setName(a);
					lst.add(cu);
				} catch (Exception e) {
					cu.setCourseId("-1");
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

	/**
	 * This method will remove PreRequisite from the DB
	 * @param crs-will hold the course id that we want to remove
	 * @param pre-will hold the PreRequisite course id that we want to remove
	 * @return return 1 for success & (-1) for failure
	 */
	public static int RemovePreReq(String crs, String pre) {
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Quary = "SELECT moodle.precourses.* FROM moodle.precourses " + "WHERE course_id='" + crs
					+ "' AND pre_id='" + pre + "'";
			ResultSet rs = stmt.executeQuery(Quary);
			while (rs.next()) {
				String Quary1 = "DELETE FROM moodle.precourses WHERE course_id='" + crs + "' AND pre_id='" + pre + "'";
				stmt.executeUpdate(Quary1);
				return 1;
			}
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static ArrayList<Assigenment> allAssForTeacher(String teacherid) {
		Statement stmt;
		ArrayList<String> a1 = new ArrayList<>();
		ArrayList<String> a2 = new ArrayList<>();
		ArrayList<Assigenment> lst = new ArrayList<>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.classcourse where tid='" + teacherid + "'");

			while (rs.next()) {

				try {

					a1.add(rs.getString(1));

				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (int i = 0; i < a1.size(); i++) {
				ResultSet rs1 = stmt
						.executeQuery("SELECT * FROM moodle.teacherassingment where CourseId='" + a1.get(i) + "'");

				while (rs1.next()) {
					// Print out the values
					Assigenment ass = new Assigenment();
					try {

						ass.setAssId(rs1.getInt(1));
						ass.setFileid(rs1.getString(2));
						ass.setDueDate(rs1.getDate(3));
						ass.setUserId(rs1.getString(4));
						// ass.setCheck(rs1.getInt(5));
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

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lst;
	}

	/**
	 * This method will search for the courses student is learning in the DB
	 * 
	 * @param Id-will
	 *            hold the id number of the Student that we search for his
	 *            courses.
	 * @return return list of courses studying buy the student if it found.
	 */
	public static ArrayList<String> setComboBoxStudentCourse(String Id) {
		ArrayList<String> al = new ArrayList<String>();
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.studentcourse where studid='" + Id + "'");
			while (rs.next()) {
				// Print out the values

				try {
					al.add(rs.getString(3));
				} catch (Exception e) {

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
	/**
	 * This method will search for all the assignments course for a student in the DB
	 * @param asud-will hold the details of the required table- course id, student id
	 * @return return list of assignment course for a student
	 */
	public static ArrayList<Assigenment> setTableViewStudentCourseAssigenment(Studentass asud) {
		Statement stmt;
		ArrayList<Assigenment> lst = new ArrayList<>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM moodle.teacherassingment where CourseId='" + asud.getCourseid() + "'");
			while (rs.next()) {
				// Print out the values
				Assigenment ass = new Assigenment();
				try {
					ass.setAssId(rs.getInt(1));
					ass.setFileid(rs.getString(2));
					ass.setDueDate(rs.getDate(3));
					ass.setUserId(rs.getString(4));
					ass.setCourseid(rs.getString(5));
					ass.setAssname(rs.getString(8));
					lst.add(ass);
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i=0; i<lst.size(); i++){
				Assigenment ass = lst.get(i);
				rs = stmt
						.executeQuery("SELECT * FROM moodle.studentassignment where " + "CourseId='" + asud.getCourseid()
						+ "' AND Studid='" + asud.getStudid() + "' AND TechAssId='" + ass.getAssId() + "'");
				while (rs.next()) {
					try {
						int id = rs.getInt(9);
						if (ass.getAssId() == id) {
							lst.remove(i);
							i--;
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
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

	/**
	 * This method will search for courses class study in the DB
	 * @param Cid-will hold the id number of the class we search courses for.
	 * @return return list of courses that the class learn if it found
	 */
	public static ArrayList<Course> ClassCourseDetails(String Cid) {
		Statement stmt;
		ArrayList<Course> clst = new ArrayList<>();

		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.classcourse where classid='" + Cid + "'");
			while (rs.next()) {
				Course cl = new Course();
				try {
					cl.setCourseId(rs.getString(1));
					cl.setSemid(rs.getString(4));
					clst.add(cl);
				} catch (Exception e) {
					cl.setCourseId("-1");
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

	/**
	 * This method will upload an student assignment submission to the DB. 
	 * @param asud-will hold the details of the required assignment submission
	 * @return return 1- for successfully uploaded but delay submission
	 * 				  0- for successfully uploaded in time
	 * 				  (-1)- for failed upload
	 */
	public static int UploadFile(Studentass stuass) throws Exception {
		
		File newFolder = new File("/MAT-LocalFiles/");
		if (!newFolder.exists()) newFolder.mkdirs();
		String filePath = newFolder.getAbsolutePath();
		Path path = Paths.get(filePath+"/" + stuass.getFname());
		Files.write(path, stuass.getData());
		filePath = filePath+"/" + stuass.getFname();
		File f = new File(filePath);
		stuass.setPath(f);
		
		try {
			Connection conn = Connect.getConnection();
			FileInputStream InputStream = new FileInputStream(stuass.getPath());
			String Quary = "INSERT INTO moodle.studentassignment"
					+ "(Studid,Courseid,Date,Fileid,path,FileName,subDelay,TechAssId) VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(Quary);
			Date datenow = java.sql.Date.valueOf(java.time.LocalDate.now());

			stmt.setString(1, stuass.getStudid());
			stmt.setString(2, stuass.getCourseid());
			stmt.setDate(3, datenow);
			stmt.setString(4, stuass.getFileid());
			Blob blob = new javax.sql.rowset.serial.SerialBlob(stuass.getData());
			stmt.setBlob(5, blob);
			stmt.setString(6, stuass.getFname());
			stmt.setInt(8, stuass.getAssid());
			if (datenow.after(stuass.getDate())) {
				stmt.setInt(7, 1);
				stmt.executeUpdate();
				return 1;
			} else {
				stmt.setInt(7, 0);
				stmt.executeUpdate();
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * This method will download an assignment from the DB. 
	 * @param ass-will hold the details of the required assignment
	 * @return return ret- the download assignment file
	 */
	public static Assigenment DownloadAssigenment(Assigenment ass) throws IOException{
		Assigenment ret = null;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			 conn = Connect.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from moodle.teacherassingment where Assid= '"+ ass.getAssId() + "'";
			rs = stmt.executeQuery(sql);
			while (rs.next()){
				ret = new Assigenment();
				ret.setAssId(rs.getInt(1));
				ret.setFileid(rs.getString(2));
				ret.setDueDate(rs.getDate(3));
				ret.setTeacherid(rs.getString(4));
				ret.setCourseid(rs.getString(5));
				ret.setSemester(rs.getString(6));
				ret.setData(rs.getBytes(7));
				ret.setAssname(rs.getString(8));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * This method will search for Reports in the DB
	 * 
	 * @param
	 * @return return list of all the reports exists in the DB.
	 */
	public static ArrayList<Reports> createReportEntity() {
		Statement stmt;
		ArrayList<Reports> rep = new ArrayList<Reports>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.reports");
			while (rs.next()) {
				Reports re = new Reports();
				try {
					re.setRepId(rs.getString(1));
					re.setType(Integer.parseInt(rs.getString(2)));
					re.setRepName(rs.getString(3));
					rep.add(re);
				} catch (Exception e) {
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

	/**
	 * This method will search for Teachers in the DB
	 * 
	 * @param
	 * @return return list of all the teachers exists in the DB.
	 */
	public static ArrayList<Teacher> createTeacherEntity() {
		Statement stmt;
		ArrayList<Teacher> tec = new ArrayList<Teacher>();
		ArrayList<Teacher> tecup = new ArrayList<Teacher>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.teachers");
			while (rs.next()) {
				Teacher teacher = new Teacher();
				try {
					teacher.setTecId(rs.getString(3));
					tec.add(teacher);
				} catch (Exception e) {
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
			for (int j = 0; j < tec.size(); j++) {
				ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.users where Id='" + tec.get(j).getTecId() + "'");
				while (rs.next()) {
					Teacher teacher = new Teacher();
					try {
						teacher.setTecId(tec.get(j).getTecId());
						teacher.setTecName(rs.getString(2));
						tecup.add(teacher);
					} catch (Exception e) {
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

	/**
	 * This method will search for Courses in the DB
	 * 
	 * @param
	 * @return return list of all the courses exists in the DB.
	 */
	public static ArrayList<Class> createClassEntity() {
		Statement stmt;
		ArrayList<Class> cla = new ArrayList<Class>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.class");
			while (rs.next()) {
				Class c1 = new Class();
				try {
					c1.setClassId(rs.getString(1));
					c1.setName(rs.getString(2));
					c1.setMAXStudent(Integer.parseInt(rs.getString(3)));
					cla.add(c1);
				} catch (Exception e) {
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

	/**
	 * This method will search for Semesters in the DB
	 * 
	 * @param
	 * @return return list of all the semesters exists in the DB.
	 */
	public static ArrayList<Semester> createSemesterEntity() {
		Statement stmt;
		ArrayList<Semester> se = new ArrayList<Semester>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.semester");
			while (rs.next()) {
				Semester sem = new Semester();
				try {
					sem.setSemId(rs.getString(1));
					sem.setCurrentStatus(Integer.parseInt(rs.getString(2)));
					sem.setNo_week(Integer.parseInt(rs.getString(3)));
					se.add(sem);
				} catch (Exception e) {
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

	/**
	 * This method will give all the students in the DB that belong to specific
	 * parent
	 * 
	 * @param Pid
	 *            will hold the id of the parent
	 * @return list of parent's child (students)
	 */
	public static ArrayList<Student> parSetStudentComboBox(String Pid) {
		Statement stmt;
		ArrayList<Student> lst = new ArrayList<Student>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT moodle.student.sid, moodle.users.Fullname, moodle.student.avg, moodle.student.classid FROM "
							+ "moodle.student, moodle.users WHERE moodle.student.parentid='" + Pid
							+ "' AND moodle.users.Id=moodle.student.sid");

			while (rs.next()) {
				Student stu = new Student();
				try {
					stu.setId(rs.getString(1));
					stu.setName(rs.getString(2));
					stu.setAvg(rs.getFloat(3));
					stu.setClassid(rs.getString(4));
					stu.setParentId(Pid);
					lst.add(stu);
				} catch (Exception e) {
					stu.setId("-1");
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

	/**
	 * This method Accessing to data base and take all the student table to list
	 * of student
	 *
	 * @return list of student
	 */
	public static ArrayList<Student> StudentsList() {
		Statement stmt;
		ArrayList<Student> lst = new ArrayList<Student>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.student");

			while (rs.next()) {
				Student stu = new Student();
				try {
					stu.setParentId(rs.getString(1));
					stu.setAvg(rs.getFloat(2));
					stu.setId(rs.getString(3));
					stu.setClassid(rs.getString(4));

					lst.add(stu);
				} catch (Exception e) {
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

	/**
	 * This method calculate and update the field avg of specific student
	 *
	 * @param Sid
	 *            describe the student id.
	 * @return the avg number
	 */
	public static float avgOneStudent(String Sid) {
		float sum = 0, grade;
		int count = 0;
		ArrayList<Course> lst = StudentCourse(Sid);
		for (Course c : lst) {
			grade = c.getGrade();
			if (grade >= 0) {
				sum += grade;
				count++;
			}
		}
		float avg = sum / (float) count;

		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Query = "UPDATE moodle.student SET avg='" + avg + "' WHERE sid='" + Sid + "'";
			stmt.executeUpdate(Query);
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return avg;
	}

	/**
	 * This method update the field to be 1, it means to blocked the parent
	 * 
	 * @param Pid
	 *            this describe the parent id.
	 * @return if we Successfully update the DB or not
	 */
	public static boolean BlockParent(String Pid) {
		boolean UPDATE = false;
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Query = "UPDATE moodle.users SET isBlocked = 1 WHERE Id ='" + Pid + "'";
			stmt.executeUpdate(Query);
			Connect.close();
			UPDATE = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UPDATE;
	}

	/**
	 * This method update the field to be 0, it means to unblocked the parent
	 * 
	 * @param Pid
	 *            this describe the parent id.
	 * @return if we Successfully update the DB or not
	 */
	public static boolean unBlockParent(String Pid) {
		Statement stmt;
		boolean UPDATE = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Query = "UPDATE moodle.users SET isBlocked = " + 0 + " WHERE Id ='" + Pid + "'";
			stmt.executeUpdate(Query);
			Connect.close();
			UPDATE = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UPDATE;
	}

	/**
	 * This method update the field status, in requests table, when the school
	 * manger confirmed that specific request
	 * 
	 * @param Rid
	 *            will hold the id of this request
	 * @return if we Successfully update the DB or not
	 */
	public static boolean ApprovalRequest(String Rid) {
		Statement stmt;
		boolean UPDATE = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Query = "UPDATE moodle.requests SET status = 1 WHERE Reqid ='" + Rid + "'";
			stmt.executeUpdate(Query);
			Connect.close();
			UPDATE = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UPDATE;
	}

	/**
	 * This method update the field status, in requests table, when the school
	 * manger Rejected that specific request
	 * 
	 * @param Rid
	 *            will hold the id of this request
	 * @return if we Successfully update the DB or not
	 */
	public static boolean RejectRequest(String Rid) {
		Statement stmt;
		boolean UPDATE = false;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Query = "UPDATE moodle.requests SET status = 2 WHERE Reqid ='" + Rid + "'";
			stmt.executeUpdate(Query);
			Connect.close();
			UPDATE = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UPDATE;
	}

	/**
	 * This method look for the user by some id
	 * 
	 * @param id
	 *            describe some person which takes part in the system.
	 * @return object with the details required to this id
	 */
	public static User getUserDetailsById(String id) {
		Statement stmt;
		User u = new User();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.users WHERE moodle.users.Id='" + id + "'");

			while (rs.next()) {
				try {
					u.setId(rs.getString(1));
					u.setName(rs.getString(2));
					u.setPassword(rs.getString(3));
					u.setUserName(rs.getString(4));
					u.setRole(Integer.parseInt(rs.getString(5)));
					u.setBlocked(Integer.parseInt(rs.getString(6)));
					u.setIsConnected(Integer.parseInt(rs.getString(7)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}

	/**
	 * This method will search for classes teacher assign to in the DB
	 * 
	 * @param teacherid-will
	 *            hold the id number of the Teacher we search classes for
	 * @return return list of the classes that the teacher assign to if it found
	 */
	public static ArrayList<Class> TeacherClassList(String teacherid) {
		Statement stmt;
		ArrayList<Class> se = new ArrayList<Class>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.classcourse where tid='" + teacherid + "'");
			while (rs.next()) {
				Class c = new Class();
				try {
					c.setCourseid(rs.getString(1));
					c.setClassId(rs.getString(2));
					c.setTeachid(rs.getString(3));
					c.setSemid(rs.getString(4));
					se.add(c);
				} catch (Exception e) {
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

	/**
	 * This method will search for a Teacher in the DB
	 * 
	 * @param tecname-will
	 *            hold the name of the teacher that been searched
	 * @return return the id of the teacher that been searched if it found
	 */
	public static Teacher TecNameToId(String tecname) {
		Statement stmt;
		Teacher t = new Teacher();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.users where FullName='" + tecname + "'");
			while (rs.next()) {
				try {
					t.setTecId(rs.getString(1));

				} catch (Exception e) {
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

	/**
	 * This method will search for a Class in the DB
	 * 
	 * @param classname-will
	 *            hold the name of the class that been searched
	 * @return return the id of the class that been searched if it found
	 */
	public static Class ClassNameToId(String classname) {
		Statement stmt;
		Class c = new Class();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.class where name='" + classname + "'");
			while (rs.next()) {
				try {
					c.setClassId(rs.getString(1));

				} catch (Exception e) {
					c.setClassId("-1");
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
			return c;
		} catch (SQLException e) {
			c.setClassId("-1");
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * This method will search for the teachers that teach a specific class in
	 * the DB
	 * 
	 * @param classid-will
	 *            hold the id number of the class that we search teachers for
	 * @return return list of classes that holds the course and teacher.
	 */
	public static ArrayList<Class> ClassTeacherList(String classid) {
		Statement stmt;
		ArrayList<Class> cla = new ArrayList<Class>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.classcourse where classid='" + classid + "'");
			while (rs.next()) {
				Class c = new Class();
				try {
					c.setCourseid(rs.getString(1));
					c.setClassId(rs.getString(2));
					c.setTeachid(rs.getString(3));
					c.setSemid(rs.getString(4));
					cla.add(c);
				} catch (Exception e) {
					c.setClassId("-1");
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

	/**
	 * This method will search for a course students learn in the DB
	 * 
	 * @param Cid-will
	 *            hold the id number of the Course that been searched
	 * @return return list courses that holds the grade of every student that
	 *         learn it.
	 */
	public static ArrayList<Course> CourseGradeList(String Cid) {
		Statement stmt;
		ArrayList<Course> lst = new ArrayList<>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			String Quary = "SELECT moodle.studentcourse.* FROM moodle.studentcourse WHERE moodle.studentcourse.courseid='"
					+ Cid + "'";

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
					cu.setSemid("-1");
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

	@SuppressWarnings("unused")
	private static ResultSet executeUpdate(String quary) {
		return null;
	}

	
	/**
	 * This method will search all courses for specific teacher in
	 * the DB
	 * 
	 * @param id-will
	 *            hold the id number of the teacher that we search course for
	 * @return return list of courses id thet the teacher teach.
	 */
	public static ArrayList<String>	setComboBoxTeacherCourse(String id){
		ArrayList<String> al = new ArrayList<String>();
		Assigenment ass;
	
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
				"	SELECT distinct (moodle.classcourse.courseid)   FROM moodle.classcourse where tid='"+id+"'"  );
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

	
	
	/**
	 * This method will search all the details about a specific course in
	 * the DB
	 * 
	 * @param id -will
	 *            hold the id number of the course that we create entity for
	 * @return return course entity
	 */
	public static Course createCourseEntity(String id) {
		Statement stmt;
		Course course = new Course();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.course where Courseid='" + id + "'");
			while (rs.next()) {
				try {
					course.setCourseId(rs.getString(1));
					course.setName(rs.getString(2));
					course.setUnit(rs.getString(3));
					course.setHours(rs.getInt(4));
				} catch (Exception e) {
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

	
	
	/**
	 * This method will search all the Assignment for a specific course with specific teacher in
	 * the DB
	 * 
	 *@param ass1 -will
	 *            the course id and the teacher id to find the assignment
     
	 * @return return list of assignment for this course 
	 */
	public static ArrayList<Assigenment> setTableViewTeacherCourseAssigenment(Assigenment ass1) {
		Statement stmt;
		ArrayList<Assigenment> cla =new ArrayList<Assigenment>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
				"	SELECT * FROM moodle.teacherassingment where tecid = '"+ass1.getTeacherid()+"' and CourseId = '"+ass1.getCourseid()+"'");
			while (rs.next()) {
				Assigenment ass = new Assigenment();
				try {

					ass.setAssId(rs.getInt(1));
					ass.setFileid(rs.getString(2));
					ass.setDueDate(rs.getDate(3));
					ass.setUserId(rs.getString(4));
					// ass.setCheck(rs2.getInt(5));
					ass.setCourseid(rs.getString(5));
					ass.setAssname(rs.getString(8));
					cla.add(ass);
				} 
				catch (Exception e) {
					
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


	
	/**
	 * This method will calculates the number of hours a teacher works per week
	 * the DB
	 * 

	 *@param teacherid -will
	 *            hold the id number of the teacher         
	 * @return return the number hours
	 */
	public static int getWeeklyHours(String teacherId) {
		ArrayList<String> a1 = new ArrayList<String>();
		int cnt = 0;
		Statement stmt;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.classcourse where tid='" + teacherId + "'");

			while (rs.next()) {

				try {
					a1.add(rs.getString(1));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (int i = 0; i < a1.size(); i++) {
				ResultSet rs1 = stmt.executeQuery("SELECT hours FROM moodle.course where Courseid='" + a1.get(i) + "'");
				while (rs1.next()) {

					try {
						cnt = cnt + rs1.getInt(1);
					} catch (Exception e) {
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

	
	
	
	
	/**
	 * This method will search all the Assignment the teacher post in all the courses in
	 * the DB
	 * 
	 *@param a1 -will
	 *            hold all the courses that the teacher is teaching       
	 * @return return list with all the Assignment
	 */
	public static ArrayList<Assigenment> allAssForTeacher(ArrayList<String> a1) {
		Statement stmt;
		ArrayList<Assigenment> lst = new ArrayList<Assigenment>();

		Connection conn = Connect.getConnection();
		try {
			stmt = conn.createStatement();
			for (int i = 0; i < a1.size(); i++) {
				ResultSet rs1 = stmt
						.executeQuery("SELECT * FROM moodle.teacherassingment where CourseId='" + a1.get(i) + "'");

				while (rs1.next()) {
					// Print out the values
					Assigenment ass = new Assigenment();
					try {

						ass.setAssId(rs1.getInt(1));
						ass.setFileid(rs1.getString(2));
						ass.setDueDate(rs1.getDate(3));
						ass.setUserId(rs1.getString(4));
						ass.setCourseid(rs1.getString(5));
						ass.setAssname(rs1.getString(8));
						lst.add(ass);

					}

					catch (Exception e) {
						e.printStackTrace();
					}
				}
				rs1.close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return lst;
	}

	
	/**
	 * This method will insert a new Assignment to 
	 * the DB
	 *@param ass -will
	 *            hold all the details about the new Assignment   
	 * @return return if the upload to the DB succeeded or failed
	 */
	public static int insertNewAss(Assigenment ass) throws IOException {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String data = fmt.format(ass.getDueDate());

		System.out.println(data);
		PreparedStatement myStmt = null;

		File newFolder = new File("/MAT-LocalFiles/");
		if (!newFolder.exists()) newFolder.mkdirs();
		String filePath = newFolder.getAbsolutePath();
		Path path = Paths.get(filePath+"/" + ass.getFileid());
		Files.write(path, ass.getData());
		filePath = filePath+"/" + ass.getFileid();
		
		FileInputStream inputStream = null;
		try {// need to fix!

			String Quary = "INSERT INTO moodle.teacherassingment (Fileid,DueDate,tecid,CourseId,path,assName) VALUES (?,?,?,?,?,?)";
			Connection conn = Connect.getConnection();
			myStmt = conn.prepareStatement(Quary);

			File theFile = new File(filePath);
			inputStream = new FileInputStream(theFile);

			myStmt.setString(1, ass.getFileid());// need to fix
			myStmt.setString(2, data);
			myStmt.setString(3, ass.getUserId());
			myStmt.setString(4, ass.getCourseid());
			Blob blob = new javax.sql.rowset.serial.SerialBlob(ass.getData());
			myStmt.setBlob(5, blob);

			myStmt.setString(6, ass.getAssname());

			if (myStmt.executeUpdate() == 0)
				System.out.println("error");

		} // end try

		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	/**
	 * This method will edit exist Assignment to the DB
	 *@param ass -will hold all the details that we want to edit  
	 * @return return if the upload to the DB succeeded or failed
	 */
	public static int UpdateAss(Assigenment ass) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String data = fmt.format(ass.getDueDate());
		PreparedStatement myStmt = null;

	
		if (ass.getPath() != null) {
			File newFolder = new File("/MAT-LocalFiles/");
			if (!newFolder.exists()) newFolder.mkdirs();
			String filePath = newFolder.getAbsolutePath();
			Path path = Paths.get(filePath+"/" + ass.getFileid());
			try {
				Files.write(path, ass.getData());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			filePath = filePath+"/" + ass.getFileid();
			
			FileInputStream inputStream = null;
			try {
				String Quary = "update moodle.teacherassingment set Fileid=?, DueDate=?, assName=?, path=? where Assid= '"
						+ ass.getAssId() + "' AND CourseId= '" + ass.getCourseid() + "'";
				Connection conn = Connect.getConnection();
				myStmt = conn.prepareStatement(Quary);

				myStmt.setString(1, ass.getFileid());
				myStmt.setString(2, data);
				myStmt.setString(3, ass.getAssname());

				Blob blob = new javax.sql.rowset.serial.SerialBlob(ass.getData());
				myStmt.setBlob(4, blob);

				if (myStmt.executeUpdate() == 0)
					System.out.println("error");
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			try {

				String Quary = "update moodle.teacherassingment set  DueDate=?, assName=? where Assid= '"
						+ ass.getAssId() + "' AND CourseId= '" + ass.getCourseid() + "'";
				Connection conn = Connect.getConnection();
				myStmt = conn.prepareStatement(Quary);

				myStmt.setString(1, data);
				myStmt.setString(2, ass.getAssname());

				if (myStmt.executeUpdate() == 0)
					System.out.println("error");
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 1;
	}



	/**
	 * This method will search the all  courses that the teacher is teaching in
	 * the DB
	 *@param teacherid -will hold all the id number of the teacher
	 * @return return list of courses of the teacher
	 */
	public static ArrayList<String> allCourseForTeacher(String teacherid) {
		Statement stmt;
		ArrayList<String> a1 = new ArrayList<String>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.classcourse where tid='" + teacherid + "'");

			while (rs.next()) {
				try {
					a1.add(rs.getString(1));
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs.close();
			Connect.close();
			return a1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a1;
	}

	
	/**
	 * This method will download all students solution for a specific  Assignment from 
	 * the DB
	 *@param teacherAss -will
	 *            hold all the details for the Assignment
	 * @return return list of students solution
	 */
	public static ArrayList<Studentass> downloadStudentAssForTeacher(Assigenment teacherAss) throws IOException {
		ArrayList<Studentass> ret = new ArrayList<Studentass>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		Studentass ass = null;

		try {
			conn = Connect.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from moodle.studentassignment where TechAssId= '" + teacherAss.getAssId() + "'";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				ass = new Studentass();
				ass.setAssid(rs.getInt(1));
				ass.setData(rs.getBytes(6));
				ass.setFname(rs.getString(7));
				ret.add(ass);
			}
		} catch (Exception e) {
			e.printStackTrace();
			}
		return ret;

	}

	
	/**
	 * This method will search students that upload solution for specific Assignment to course in
	 * the DB
	 * 
	 *@param ass -will
	 *            hold all the details for the Assignment
	 * @return return list of Assignment that the student upload
	 */
	public static ArrayList<Assigenment> listOfStudentForAssCourse(Assigenment ass) {

		Statement stmt;
		ArrayList<Assigenment> lst = new ArrayList<Assigenment>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"	SELECT moodle.studentassignment.Studid, moodle.users.Fullname,moodle.studentassignment.Assid,moodle.studentassignment.subDelay FROM moodle.studentassignment , moodle.users WHERE Courseid= '"
							+ ass.getCourseid() + "' AND TechAssId= '" + ass.getAssId() + "' AND Id = Studid");

			while (rs.next()) {
				// Print out the values
				try {
					Assigenment assstud = new Assigenment();
					assstud.setUserId(rs.getString(1));
					assstud.setUserName(rs.getString(2));
					assstud.setAssStudent(rs.getInt(3));
					assstud.setCheck(rs.getInt(4));
					if (assstud.getCheck() == 1) {
						assstud.setLate("Delay");
					}
					assstud.setAssId(ass.getAssId());
					assstud.setCourseid(ass.getCourseid());
					assstud.setTeacherid(ass.getTeacherid());
					assstud.setTechername(ass.getTechername());
					lst.add(assstud);
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
	
	/**
	 * This method will download assignment of student
	 * 
	 *@param assToDownload -will
	 *            hold all the details for the assignment of the student
	 * @return return the student assignment for download
	 */
	public static Studentass downloadOneFileStud(Assigenment assToDownload) throws IOException {
		Studentass ret = null;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		Studentass ass = null;

		try {
			conn = Connect.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from moodle.studentassignment where TechAssId= '" + assToDownload.getAssId()
					+ "' AND Studid='" + assToDownload.getUserId() + "' AND Courseid='" + assToDownload.getCourseid()
					+ "'";
			rs = stmt.executeQuery(sql);

			ass = new Studentass();

			while (rs.next()) {
				ret = new Studentass();
				ret.setAssid(rs.getInt(1));
				ret.setStudid(rs.getString(2));
				ret.setCourseid(rs.getString(3));
				ret.setDate(rs.getDate(4));
				ret.setFileid(rs.getString(5));
				ret.setData(rs.getBytes(6));
				ret.setFname(rs.getString(7));
				ret.setEvaData(rs.getBytes(10));
				ret.setEvaFileName(rs.getString(11));
				ret.setGradeData(rs.getBytes(12));
				ret.setGradeFileName(rs.getString(13));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return ret;

	}

	
	/**
	 * This method will upload  evaluation for student solution in DB
	 * 
	 *@param assToDownload -will
	 *            hold all the details for the assignment of the student 
	 * @return return if the upload to the DB succeeded or failed
	 */
	public static int uploadEvaluation(Assigenment ass) throws IOException {

		File newFolder = new File("/MAT-LocalFiles/");
		if (!newFolder.exists()) newFolder.mkdirs();
		String filePath = newFolder.getAbsolutePath();
		Path path = Paths.get(filePath+"/" + ass.getFileid());
		Files.write(path, ass.getData());
		filePath = filePath+"/" + ass.getFileid();
		ass.setPath(filePath);
		
		
		PreparedStatement myStmt = null;
		
		FileInputStream inputStream = null;
		try {// need to fix! String Quary="UPDATE moodle.teachers SET
				// Unit='"+Unit+"' WHERE Id="+id+"";
			
			String Quary = "UPDATE moodle.studentassignment SET evaluation=?, evaluationName=?  WHERE Courseid='"
					+ ass.getCourseid() + "' AND Studid='" + ass.getUserId() + "' AND TechAssId='" + ass.getAssId()
					+ "'";
			Connection conn = Connect.getConnection();
			myStmt = conn.prepareStatement(Quary);

			Blob blob = new javax.sql.rowset.serial.SerialBlob(ass.getData());
			myStmt.setBlob(1, blob);
			myStmt.setString(2, ass.getFileid());// need to fix

			if (myStmt.executeUpdate() == 0)
				System.out.println("error");

		} // end try

		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	
	/**
	 * This method will upload  Grade File for student solution in DB
	 * 
	 *@param ass -will
	 *            hold all the details for the assignment of the student 
	 * @return return if the upload to the DB succeeded or failed
	 */
	public static int uploadGradeFile(Assigenment ass) throws IOException {

		File newFolder = new File("/MAT-LocalFiles/");
		if (!newFolder.exists()) newFolder.mkdirs();
		String filePath = newFolder.getAbsolutePath();
		Path path = Paths.get(filePath+"/" + ass.getFileid());
		Files.write(path, ass.getData());
		filePath = filePath+"/" + ass.getFileid();
		ass.setPath(filePath);
		
		PreparedStatement myStmt = null;

		FileInputStream inputStream = null;
		try {// need to fix! String Quary="UPDATE moodle.teachers SET
				// Unit='"+Unit+"' WHERE Id="+id+"";

			String Quary = "UPDATE moodle.studentassignment SET gradeFile=?, gradeFileName=?  WHERE Courseid='"
					+ ass.getCourseid() + "' AND Studid='" + ass.getUserId() + "' AND TechAssId='" + ass.getAssId()
					+ "'";
			Connection conn = Connect.getConnection();
			myStmt = conn.prepareStatement(Quary);

			Blob blob = new javax.sql.rowset.serial.SerialBlob(ass.getData());
			myStmt.setBlob(1, blob);
			myStmt.setString(2, ass.getFileid());// need to fix

			if (myStmt.executeUpdate() == 0)
				System.out.println("error");

		} // end try

		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	
	
	/**
	 * This method search all the details from specific assignment
	 * 
	 *@param ass -will
	 *            hold the assignment id and course id
	 * @return return the assignment with all is details inside
	 */
	public static Assigenment assCourseTeach(Assigenment ass) {

		Statement stmt;
		Assigenment lst = new Assigenment();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.teacherassingment WHERE Courseid= '"
					+ ass.getCourseid() + "' AND Assid= '" + ass.getAssId() + "'");

			while (rs.next()) {
				// Print out the values
				try {

					lst.setAssId(rs.getInt(1));
					lst.setFileid(rs.getString(2));
					lst.setDueDate(rs.getDate(3));
					lst.setTeacherid(rs.getString(4));
					lst.setCourseid(rs.getString(5));
					lst.setAssname(rs.getString(8));
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
	/**
	 * This method will check for all the evaluations and grade files in the DB. 
	 * @param Sid-will hold the student id
	 * @return lst- list of student evaluations.
	 */
	public static ArrayList<Studentass> StudentEvaluations(String Sid) {
		Statement stmt;
		ArrayList<Studentass> lst = new ArrayList<Studentass>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.studentassignment WHERE Studid='" + Sid + "'");
			while (rs.next()) {
				try {
					Studentass ev = new Studentass();
					String crs = createCourseEntity(rs.getString(3)).getName();
					ev.setCourseName(crs);
					if (rs.getString(11) != null) {
						ev.setEvaFileName(rs.getString(11));
						String s1 = GetAssName(rs.getInt(9));
						ev.setAssiName(s1);
						ev.setAssid(rs.getInt(1));

						lst.add(ev);
					}

				} catch (Exception e) {
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
	
	/**
	 * This method will get an assignment name from the DB. 
	 * @param assid-will hold the assignment id
	 * @return return the assignment name
	 */
	public static String GetAssName(int assid) {
		Statement stmt;
		String s = null;
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.teacherassingment WHERE Assid='" + assid + "'");
			while (rs.next()) {
				s = rs.getString(8);
				return s;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return s;
	}
	
	/**
	 * This method will download an assignment evaluation from the DB. 
	 * @param ass-will hold the details of the required assignment evaluation
	 * @return return ret- the assignment details include the download evaluation file
	 */
	public static Studentass DownloadStuEvaluation(Studentass ass) throws IOException {
		Studentass ret = null;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = Connect.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from moodle.studentassignment where Assid= '" + ass.getAssid() + "'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ret = new Studentass();
				ret.setAssid(rs.getInt(1));
				ret.setStudid(rs.getString(2));
				ret.setCourseid(rs.getString(3));
				ret.setDate(rs.getDate(4));
				ret.setFileid(rs.getString(5));
				ret.setData(rs.getBytes(6));
				ret.setFname(rs.getString(7));
				ret.setEvaData(rs.getBytes(10));
				ret.setEvaFileName(rs.getString(11));
				ret.setGradeData(rs.getBytes(12));
				ret.setGradeFileName(rs.getString(13));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * This method will download an assignment grade file from the DB. 
	 * @param ass-will hold the details of the required assignment grade file
	 * @return return ret- the assignment details include the download grade file
	 */
	public static Studentass DownloadStuGradeFile(Studentass ass) throws IOException {
		Studentass ret = null;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = Connect.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from moodle.studentassignment where Assid= '" + ass.getAssid() + "'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ret = new Studentass();
				ret.setAssid(rs.getInt(1));
				ret.setStudid(rs.getString(2));
				ret.setCourseid(rs.getString(3));
				ret.setDate(rs.getDate(4));
				ret.setFileid(rs.getString(5));
				ret.setData(rs.getBytes(6));
				ret.setFname(rs.getString(7));
				ret.setEvaData(rs.getBytes(10));
				ret.setEvaFileName(rs.getString(11));
				ret.setGradeData(rs.getBytes(12));
				ret.setGradeFileName(rs.getString(13));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	/**
	 * This method will search all details of specific course
	 * 
	 * @param courseName-will
	 *            hold the name of the course that we want to find
	 * @return return the course that been checked if it found
	 */
	public static Course createCourseEntityByName(String courseName) {
		Statement stmt;
		Course course = new Course();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM moodle.course where CourseName='" + courseName + "'");
			while (rs.next()) {
				try {
					course.setCourseId(rs.getString(1));
					course.setName(rs.getString(2));
					course.setUnit(rs.getString(3));
					course.setHours(rs.getInt(4));
				} catch (Exception e) {
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
	
	/**
	 * This method will search all details of specific class
	 * 
	 * @param ass-will
	 *            hold the details of course that we want to find is class
	 * @return return list of class for course
	 */
	public static ArrayList<Class> createClassEntityByCourseId(Assigenment ass) {
		Statement stmt;
		ArrayList<Class> cla =new ArrayList<Class>();
		try {
			Connection conn = Connect.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
				"	SELECT moodle.class.*  FROM moodle.class, moodle.classcourse, moodle.course where   moodle.classcourse.courseid= '" + ass.getCourseid() + "'and moodle.classcourse.classid=moodle.class.Classid AND moodle.course.Courseid= moodle.classcourse.courseid AND moodle.classcourse.tid='"+ass.getTeacherid()+"'");
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

}
