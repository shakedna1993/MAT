package client;

import java.util.ArrayList;

import thred.IndexList;

/**
 * This class handles the connection between the server and the client
 */
public class MsgFromServer {

	public static ArrayList<Object> dataList = new ArrayList<>();

	/**
	 * This method creates new data list for all the info that returns from the server
	 */
	public static void initiazlizeData() {
		dataList = new ArrayList<>();
	}

	/**
	 * This method sets the data in the data list by int index to be obj value.
	 * 
	 * @param index where to insert data in list.
	 * @param obj what data to insert/change.
	 */
	public static void setDataListByIndex(int index, Object obj) {
		while (index >= dataList.size())
			dataList.add(null);
		dataList.set(index, obj);
	}

	/**
	 * This method returns the wanted data from the data list by int index.
	 * 
	 * @param index
	 * @return the data from dataList in wanted index
	 */
	public static Object getDataListByIndex(int index) {
		Object tmp = null;
		try {
			if (index >= dataList.size())
				return null;
			tmp = dataList.get(index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return tmp;
	}

	/**
	 * This method sets the data in the data list by int index to be null.
	 * 
	 * @param index where to insert data in list.
	 */
	public static void deleteDataListByIndex(int index) {
		if (index >= dataList.size())
			return;
		dataList.set(index, null);
	}

	/**
	 * This method sets all data list from 4 till end to null, clean sheets
	 */
	public static void renewDataList() {
		for (int index = 4; index < dataList.size(); index++)
			deleteDataListByIndex(index);
	}

	public static void handleMsg(Object msg) {
		Op returnMsg = null;
		/* Switch case */
		try {
			returnMsg = (Op) msg;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println(returnMsg.getMsg());
		switch (returnMsg.getOp()) {
		case LOGIN:
			setDataListByIndex(IndexList.LOGIN, returnMsg.getMsg());
			return;
		case LOGOUT:
			setDataListByIndex(IndexList.LOGOUT, returnMsg.getMsg());
		case StudentDetails:
			setDataListByIndex(IndexList.StudentDetails, returnMsg.getMsg());
			return;
		case UpdateUnit:
			setDataListByIndex(IndexList.UpdateUnit, returnMsg.getMsg());
		case Teacherdetails:
			setDataListByIndex(IndexList.Teacherdetails, returnMsg.getMsg());
		case StudentExists:
			setDataListByIndex(IndexList.StudentExists, returnMsg.getMsg());
		case ParentExists:
			setDataListByIndex(IndexList.ParentExists, returnMsg.getMsg());
		case AddStudent:
			setDataListByIndex(IndexList.AddStudent, returnMsg.getMsg());
		case UserNameExists:
			setDataListByIndex(IndexList.UserNameExists, returnMsg.getMsg());
		case StudentCourse:
			setDataListByIndex(IndexList.StudentCourse, returnMsg.getMsg());
			return;
		case parSetStudentComboBox:
			setDataListByIndex(IndexList.parSetStudentComboBox, returnMsg.getMsg());
			return;
		case avgOneStudent:
			setDataListByIndex(IndexList.avgOneStudent, returnMsg.getMsg());
		case BlockParent:
			setDataListByIndex(IndexList.BlockParent, returnMsg.getMsg());
		case unBlockParent:
			setDataListByIndex(IndexList.unBlockParent, returnMsg.getMsg());
		case getUsersByRole:
			setDataListByIndex(IndexList.getUsersByRole, returnMsg.getMsg());
		case getUserCoursesInCurrSemester:
			setDataListByIndex(IndexList.getUserCoursesInCurrSemester, returnMsg.getMsg());
		case UserIdExists:
			setDataListByIndex(IndexList.UserIdExists, returnMsg.getMsg());
		case AddNewRequest:
			setDataListByIndex(IndexList.AddNewRequest, returnMsg.getMsg());
		case getAllCoursesInCurrSemester:
			setDataListByIndex(IndexList.getAllCoursesInCurrSemester, returnMsg.getMsg());
		case getActiveRequests:
			setDataListByIndex(IndexList.getActiveRequests, returnMsg.getMsg());
		case getCourseByID:
			setDataListByIndex(IndexList.getCourseByID, returnMsg.getMsg());
		case getUserByID:
			setDataListByIndex(IndexList.getUserByID, returnMsg.getMsg());
		case getClassListForTeacherInCourse:
			setDataListByIndex(IndexList.getClassListForTeacherInCourse, returnMsg.getMsg());
		case getClassByID:
			setDataListByIndex(IndexList.getClassByID, returnMsg.getMsg());
		case getRequestByID:
			setDataListByIndex(IndexList.getRequestByID, returnMsg.getMsg());
		case ChangeTeacherAppointment:
			setDataListByIndex(IndexList.ChangeTeacherAppointment, returnMsg.getMsg());
		case DeActivateRequest:
			setDataListByIndex(IndexList.DeActivateRequest, returnMsg.getMsg());
		case UpdateMaxStudents:
			setDataListByIndex(IndexList.UpdateMaxStudents, returnMsg.getMsg());
		case StudentsList:
			setDataListByIndex(IndexList.StudentsList, returnMsg.getMsg());
			return;
		case getActiveClasses:
			setDataListByIndex(IndexList.getActiveClasses, returnMsg.getMsg());
		case getStudentInClass:
			setDataListByIndex(IndexList.getStudentInClass, returnMsg.getMsg());
		case getAvailableCoursesForClass:
			setDataListByIndex(IndexList.getAvailableCoursesForClass, returnMsg.getMsg());
		case RemoveStudentFromCourse:
			setDataListByIndex(IndexList.RemoveStudentFromCourse, returnMsg.getMsg());
		case AddClassToCourse:
			setDataListByIndex(IndexList.AddClassToCourse, returnMsg.getMsg());
		case AddStudentToCourse:
			setDataListByIndex(IndexList.AddStudentToCourse, returnMsg.getMsg());
		case getCurrentSemesterID:
			setDataListByIndex(IndexList.getCurrentSemesterID, returnMsg.getMsg());
		case RemoveClassFromCourse:
			setDataListByIndex(IndexList.RemoveClassFromCourse, returnMsg.getMsg());
		case getTeachersForCourse:
			setDataListByIndex(IndexList.getTeachersForCourse, returnMsg.getMsg());
		case OpenNewSemester:
			setDataListByIndex(IndexList.OpenNewSemester, returnMsg.getMsg());
		case getStudentInNoClass:
			setDataListByIndex(IndexList.getStudentInNoClass, returnMsg.getMsg());
		case RemoveStudentFromClass:
			setDataListByIndex(IndexList.RemoveStudentFromClass, returnMsg.getMsg());
		case DeleteClass:
			setDataListByIndex(IndexList.DeleteClass, returnMsg.getMsg());
		case AddStudentToClass:
			setDataListByIndex(IndexList.AddStudentToClass, returnMsg.getMsg());
		case getClassCourses:
			setDataListByIndex(IndexList.getClassCourses, returnMsg.getMsg());
		case classIDExists:
			setDataListByIndex(IndexList.classIDExists, returnMsg.getMsg());
		case classNameExists:
			setDataListByIndex(IndexList.classNameExists, returnMsg.getMsg());
		case DefineClass:
			setDataListByIndex(IndexList.DefineClass, returnMsg.getMsg());
		case setComboBoxTeacherCourse:
			setDataListByIndex(IndexList.setComboBoxTeacherCourse, returnMsg.getMsg());
		case createCourseEntity:
			setDataListByIndex(IndexList.createCourseEntity, returnMsg.getMsg());	
		case setTableViewTeacherCourseAssigenment:
			setDataListByIndex(IndexList.setTableViewTeacherCourseAssigenment, returnMsg.getMsg());		
		case getWeeklyHours:
			setDataListByIndex(IndexList.getWeeklyHours, returnMsg.getMsg());
		case allAssForTeacher:
			setDataListByIndex(IndexList.allAssForTeacher, returnMsg.getMsg());
		case insertNewAss:
			setDataListByIndex(IndexList.insertNewAss, returnMsg.getMsg());
		case setComboBoxStudentCourse:
			setDataListByIndex(IndexList.setComboBoxStudentCourse, returnMsg.getMsg());
		case setTableViewStudentCourseAssigenment:
			setDataListByIndex(IndexList.setTableViewStudentCourseAssigenment, returnMsg.getMsg());	
		case DownoladFile:
			setDataListByIndex(IndexList.DownloadFile, returnMsg.getMsg());
		case ClassCourseDetails:
			setDataListByIndex(IndexList.ClassCourseDetails, returnMsg.getMsg());
		case UploadFile:
			setDataListByIndex(IndexList.UploadFile, returnMsg.getMsg());
		case UpdateAss:
			setDataListByIndex(IndexList.UpdateAss, returnMsg.getMsg());
		case createReportEntity:
			setDataListByIndex(IndexList.createReportEntity, returnMsg.getMsg());
		case createTeacherEntity:
			setDataListByIndex(IndexList.createTeacherEntity, returnMsg.getMsg());
		case createClassEntity:
			setDataListByIndex(IndexList.createClassEntity, returnMsg.getMsg());
		case createSemesterEntity:
			setDataListByIndex(IndexList.createSemesterEntity, returnMsg.getMsg());
		case TeacherClassList:
			setDataListByIndex(IndexList.TeacherClassList, returnMsg.getMsg());
		case TecNameToId:
			setDataListByIndex(IndexList.TecNameToId, returnMsg.getMsg());
		case ClassNameToId:
			setDataListByIndex(IndexList.ClassNameToId, returnMsg.getMsg());
		case ClassTeacherList:
			setDataListByIndex(IndexList.ClassTeacherList, returnMsg.getMsg());
		case CheckStudentPreReq:
			setDataListByIndex(IndexList.CheckStudentPreReq, returnMsg.getMsg());
		case UnitExists:
			setDataListByIndex(IndexList.UnitExists, returnMsg.getMsg());
		case CourseExists:
			setDataListByIndex(IndexList.CourseExists, returnMsg.getMsg());
		case DefineNewCourse:
			setDataListByIndex(IndexList.DefineNewCourse, returnMsg.getMsg());
		case CoursesList:
			setDataListByIndex(IndexList.CoursesList, returnMsg.getMsg());
		case DefinePreReq:
			setDataListByIndex(IndexList.DefinePreReq, returnMsg.getMsg());
		case RemoveCourse:
			setDataListByIndex(IndexList.RemoveCourse, returnMsg.getMsg());
		case RenameCourse:
			setDataListByIndex(IndexList.RenameCourse, returnMsg.getMsg());
		case WeeklyHoursUpdate:
			setDataListByIndex(IndexList.WeeklyHoursUpdate, returnMsg.getMsg());
		case PreReqList:
			setDataListByIndex(IndexList.PreReqList, returnMsg.getMsg());	
		case RemovePreReq:
			setDataListByIndex(IndexList.RemovePreReq, returnMsg.getMsg());	
		
		default:
			break;
		}

	}
}
