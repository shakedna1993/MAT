package Server;

import java.util.ArrayList;
import java.util.Calendar;

import client.MsgFromServer;
import thred.IndexList;
import client.Op;
import entity.Student;
import entity.Teacher;
import entity.User;
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
			
		case Teacherdetails:
			Teacher Teacher = (Teacher) op.getMsg();
			return DBC.Teacherdetails(Teacher.getId());
		case UpdateUnit:
			Teacher Teacher1 = (Teacher) op.getMsg();
			val= DBC.UpdateUnit(Teacher1.getId(),Teacher1.getUnit());
			if (val == 1)
				return "Success";
			else
				return "fail";
		
		default:
			return "null";
		}
	}
}
