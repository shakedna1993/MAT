package Server;

import java.util.ArrayList;
import java.util.Calendar;

import client.MsgFromServer;
import thred.IndexList;
import client.Op;
import entity.Assigenment;
import entity.Course;
import entity.Student;
import entity.Teacher;
import entity.User;
import entity.Class;
import Server.DownloadFileServer;
import Server.DBC;
/**
 * 
 * This class handles with all the functionality of the server.
 * Each instants gets msg from the clients and translates it for op-code and msg that contains the relevant object/s.
 * Later, for each op-code there is a relevant functionality.  
 */
public class OpCheck {
	public Object CheakOp(Object msg) {
		Op op = (Op) msg;
		int val;

		switch (op.getOp()) {
		case LOGIN:
			User User = (User) op.getMsg();
			return DBC.LOGIN(User.getUserName(), User.getPassword());
			
		case LOGOUT:
			User User1 = (User) op.getMsg();
			DBC.LOGOUT(User1.getUserName());
			return User1;
			
		case StudentDetails:
			Student std=new Student();
			User User2 = (User) op.getMsg();
			std=DBC.StudentDetails(User2.getId());
			return std;
			
		case ResetServer:
			DBC.ResetServer();
			return 1;
			
		case Teacherdetails:
			User User3 = (User) op.getMsg();
			Teacher Teacher = new Teacher();
			Teacher=DBC.Teacherdetails(User3.getId());
			return Teacher;
		case UpdateUnit:
			Teacher Teacher1 = (Teacher) op.getMsg();
			val= DBC.UpdateUnit(Teacher1.getId(),Teacher1.getUnit());
			if (val == 1)
				return "Success";
			else
				return "fail";
		case StudentExists:
			String id = (String)op.getMsg();
			return DBC.StudentExists(id);
			
		case ParentExists:
			return DBC.ParentExists((String)op.getMsg());
			
		case AddStudent:
			return DBC.AddStudent((Student)op.getMsg());
			
		case classIDExists:
			return DBC.classIDExists((String)op.getMsg());
			
		case classNameExists:
			return DBC.classNameExists((String)op.getMsg());
			
		case DefineClass:
			return DBC.DefineClass((entity.Class)op.getMsg());
			
		case StudentCourse:
			User stud = (User) op.getMsg();
			return DBC.StudentCourse(stud.getId());	
			
			case setComboBoxTeacherCourse:
				ArrayList<String> al = new ArrayList<String>();
				al = DBC.setComboBoxTeacherCourse((String)op.getMsg());
				return al;
				
			case createCourseEntity:
				Course Course = new Course();
				Course=DBC.createCourseEntity((String)op.getMsg());
				return Course;
				
			case setTableViewTeacherCourseAssigenment:
				ArrayList<Assigenment> lst = new ArrayList<>();
				 Assigenment ass = new Assigenment();
					 ass = (Assigenment) op.getMsg();
				lst=DBC.setTableViewTeacherCourseAssigenment(ass.getCoursename(),ass.getTeacherid());
				return lst;
				
			case getWeeklyHours:
				return DBC.getWeeklyHours((String)op.getMsg());
				
			case allAssForTeacher:
				ArrayList<Assigenment> lst1 = new ArrayList<>();
				lst1 =  DBC.allAssForTeacher((String)op.getMsg());
				return lst1;
				
			case insertNewAss:
			int flag = 0;
			flag =  DBC.insertNewAss((Assigenment)op.getMsg());
				return flag;
				
			case setComboBoxStudentCourse:
				ArrayList<String> a2 = new ArrayList<String>();
				User stu = (User) op.getMsg();
				a2 = DBC.setComboBoxStudentCourse(stu.getId());
				return a2;
				
			case setTableViewStudentCourseAssigenment:
				 Assigenment ass1 = new Assigenment();
			     ass1 = (Assigenment) op.getMsg();
				return DBC.setTableViewStudentCourseAssigenment(ass1.getUserId(),ass1.getCoursename());
			case DownoladFile:
				return DownloadFileServer.sendFile((String) op.getMsg());
			case ClassCourseDetails:
				String Classid = (String) op.getMsg();
				return DBC.ClassCourseDetails(Classid);
			
				
		default:
			return "null";
		}
	}
}
