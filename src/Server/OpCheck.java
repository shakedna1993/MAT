package Server;

import java.io.File;
import java.io.IOException;
//import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFileChooser;

import client.MsgFromServer;
import thred.IndexList;
import client.Op;
import entity.Course;
import entity.Assigenment;
import entity.Course;
import entity.Student;
import entity.Studentass;
import entity.Teacher;
import entity.Unit;
import entity.User;
import entity.Class;
import Server.DBC;
import entity.Requests;
import entity.Requests;
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
		case getActiveClasses:
			return DBC.getActiveClasses();
		case getUsersByRole:
			return DBC.getUsersByRole((int)op.getMsg());
		case getUserCoursesInCurrSemester:
			return DBC.getUserCoursesInCurrSemester((User)op.getMsg());
		case getAllCoursesInCurrSemester:
			return DBC.getAllCoursesInCurrSemester();
		case UserIdExists:
			return DBC.UserIdExists((String)op.getMsg());
		case AddNewRequest:
			return DBC.AddNewRequest((Requests)op.getMsg());
		case getActiveRequests:
			return DBC.getActiveRequests();
		case getCourseByID:
			return DBC.getCourseByID((String)op.getMsg());
		case getUserByID:
			return DBC.getUserByID((String)op.getMsg());
		case getStudentInClass:
			return DBC.getStudentInClass((String)op.getMsg());
		case getStudentInNoClass:
			return DBC.getStudentInNoClass((String)op.getMsg());
		case RemoveStudentFromClass:
			return DBC.RemoveStudentFromClass((String[])op.getMsg());
		case AddStudentToClass:
			return DBC.AddStudentToClass((String[])op.getMsg());
		case getClassCourses:
			return DBC.getClassCourses((Class)op.getMsg());
		case getTeachersForCourse:
			return DBC.getTeachersForCourse((String)op.getMsg());
		case getClassListForTeacherInCourse:
			return DBC.getClassListForTeacherInCourse((Object[])op.getMsg());
		case getClassByID:
			return DBC.getClassByID((String)op.getMsg());
		case getRequestByID:
			return DBC.getRequestByID((String)op.getMsg());
		case ChangeTeacherAppointment:
			return DBC.ChangeTeacherAppointment((String[])op.getMsg());
		case DeActivateRequest:
			return DBC.DeActivateRequest((String)op.getMsg());
		case UpdateMaxStudents:
			return DBC.UpdateMaxStudents((Object [])op.getMsg());
		case OpenNewSemester:
			return DBC.OpenNewSemester();
		case getCurrentSemesterID:
			return DBC.getCurrentSemesterID(); 
		case RemoveStudentFromCourse:
			return DBC.RemoveStudentFromCourse((String[])op.getMsg());
		case AddClassToCourse:
			return DBC.AddClassToCourse((String[])op.getMsg());
		case CheckStudentPreReq:
			return DBC.CheckStudentPreReq((String[])op.getMsg());
		case AddStudentToCourse:
			return DBC.AddStudentToCourse((String[])op.getMsg());
		case DeleteClass:
			return DBC.DeleteClass((Class)op.getMsg());
		case RemoveClassFromCourse:
			return DBC.RemoveClassFromCourse((String[])op.getMsg()); 
		case getAvailableCoursesForClass:
			return DBC.getAvailableCoursesForClass((Class)op.getMsg());
		case UserNameExists:
			return DBC.UserNameExists((String)op.getMsg());
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
			User stud = (User)op.getMsg();
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
			

		case parSetStudentComboBox:
			 return DBC.parSetStudentComboBox((String)op.getMsg());
		case avgOneStudent:
			 return DBC.avgOneStudent((String)op.getMsg());
		case BlockParent:
			return DBC.BlockParent((String)op.getMsg());
			 
		case unBlockParent:
			 return DBC.unBlockParent((String)op.getMsg());
			 
		case StudentsList:
			return DBC.StudentsList();
		case RequestsInfo:
			return DBC.RequestsInfo();
		case getUserDetailsById:
			return DBC.getUserDetailsById((String)op.getMsg());
		case ApprovalRequest:
			return DBC.ApprovalRequest((String)op.getMsg());
		case RejectRequest:
			return DBC.RejectRequest((String)op.getMsg());		
		case setComboBoxStudentCourse:
			ArrayList<String> a2 = new ArrayList<String>();
			User stu = (User) op.getMsg();
			a2 = DBC.setComboBoxStudentCourse(stu.getId());
			return a2;
		case setTableViewStudentCourseAssigenment:
			ArrayList<Assigenment> lst2 = new ArrayList<>();
			Studentass asud=(Studentass)op.getMsg();
			lst2= DBC.setTableViewStudentCourseAssigenment(asud);
			return lst2;
		case UploadFile:
			int check = 0;
			Studentass sa=(Studentass)(op.getMsg());
			try {
				check= DBC.UploadFile(sa);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return check;
			case createReportEntity:
				return DBC.createReportEntity();
			case createTeacherEntity:
				return DBC.createTeacherEntity();
			case createClassEntity:
				return DBC.createClassEntity();
			case createSemesterEntity:
				return DBC.createSemesterEntity();
			case TeacherClassList:
				String tec =(String)op.getMsg();
				return DBC.TeacherClassList(tec);
			case TecNameToId:
				Teacher tec1 =(Teacher)op.getMsg();
				return DBC.TecNameToId(tec1.getTecName());
			case ClassNameToId:
				Class cl1 =(Class)op.getMsg();
				return DBC.ClassNameToId(cl1.getName());
			case ClassTeacherList:
				Class cla =(Class)op.getMsg();
				return DBC.ClassTeacherList(cla.getClassId());
			case CourseGradeList:
				String courseid1=(String)op.getMsg();
				return DBC.CourseGradeList(courseid1);
			case ClassCourseDetails:
				String classid =(String)op.getMsg();
				return DBC.ClassCourseDetails(classid);
				
				
			case StudentEvaluations:
				User studEv = (User)op.getMsg();
				return DBC.StudentEvaluations(studEv.getId());
			case DownloadAssigenment:
				Assigenment DownAss = (Assigenment)op.getMsg();
				try {
					return DBC.DownloadAssigenment(DownAss);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			case DownloadStuEvaluation:
				Studentass DownEva1 = (Studentass)op.getMsg();
				try {
					return DBC.DownloadStuEvaluation(DownEva1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case DownloadStuGradeFile:
				Studentass DownEva2 = (Studentass)op.getMsg();
				try {
					return DBC.DownloadStuGradeFile(DownEva2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
		
			case setComboBoxTeacherCourse:
				ArrayList<Assigenment> al = new ArrayList<Assigenment>();
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
				
				
				//	public static int getAssArreyList = 44;
				//where is it
				
				
			case allAssForTeacher:
				ArrayList<Assigenment> lst1 = new ArrayList<>();
				lst1 =  DBC.allAssForTeacher((ArrayList<String>)op.getMsg());
				return lst1;
				
			case insertNewAss:
			int flag = 0;
			try {
				flag =  DBC.insertNewAss((Assigenment)op.getMsg());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				return flag;
					
				
			case UpdateAss:
				int flag1 = 0;
				flag1 =  DBC.UpdateAss((Assigenment)op.getMsg());
				return flag1;
				
			//	public static int uploadTeacherAss=48;
				//where is it
				
				
			case allCourseForTeacher:
				String courseid;
				courseid=(String)op.getMsg();
				return DBC.allCourseForTeacher(courseid);
				
			case downloadStudentAssForTeacher:
			try {
				return DBC.downloadStudentAssForTeacher((Assigenment)op.getMsg());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			case createCourseEntityByName:
				return DBC.createCourseEntityByName((String) op.getMsg());
		
				
			case listOfStudentForAssCourse:
				return DBC.listOfStudentForAssCourse((Assigenment) op.getMsg());
		
					
			case downloadOneFileStud:
			try {
				return DBC.downloadOneFileStud((Assigenment) op.getMsg());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
					
			case uploadEvaluation:
			try {
				return DBC.uploadEvaluation((Assigenment) op.getMsg());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
							
			case uploadGradeFile:
				try {
					return DBC.uploadGradeFile((Assigenment) op.getMsg());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
			case assCourseTeach:
				return DBC.assCourseTeach((Assigenment) op.getMsg());
				
				
				
				
				
				
		default:
			return "null";
		}
	}
}
