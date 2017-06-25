package Server;

import java.util.ArrayList;
import java.util.Calendar;

import client.MsgFromServer;
import thred.IndexList;
import client.Op;
import entity.Course;
import entity.Student;
import entity.Teacher;
import entity.Unit;
import entity.User;
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
		case StudentCourse:
			User stud = (User) op.getMsg();
			return DBC.StudentCourse(stud.getId());
			
		case UnitExists:
			Unit Unit1 = (Unit) op.getMsg();
			return DBC.UnitExists(Unit1.getUnitId());
		case CourseExists:
			Course Course1 = (Course) op.getMsg();
			return DBC.CourseExists(Course1.getCourseId());
		case DefineNewCourse:
			Course Course2 = (Course) op.getMsg();
			return DBC.DefineNewCourse(Course2);
		case CoursesList:
			Course Course3 = (Course) op.getMsg();
			return DBC.CoursesList(Course3.getCourseId());
		case DefinePreReq:
			Course Course4=(Course) op.getMsg();
			return DBC.DefinePreReq(Course4.getCourseId(), Course4.getPreReqId());
		case RemoveCourse:
			Course Course5 = (Course) op.getMsg();
			return DBC.RemoveCourse(Course5.getCourseId());	
		case RenameCourse:
			Course Course6 = (Course) op.getMsg();
			return DBC.RenameCourse(Course6);
		case WeeklyHoursUpdate:
			Course Course7 = (Course) op.getMsg();
			return DBC.WeeklyHoursUpdate(Course7);
		case PreReqList:
			Course Course8 = (Course) op.getMsg();
			return DBC.PreReqList(Course8.getCourseId());
		case RemovePreReq:
			Course Course9=(Course) op.getMsg();
			return DBC.RemovePreReq(Course9.getCourseId(), Course9.getPreReqId());
			

		default:
			return "null";
		}
	}
}
