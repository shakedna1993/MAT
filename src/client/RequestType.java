
package client;

/**
 * Enums for query for opCheck.java
 *
 */
public enum RequestType {

	/** Add new request from server */
	
	Teacherdetails,UpdateUnit,LOGIN, LOGOUT,StudentDetails,StudentExists,ParentExists,AddStudent,UserNameExists,ResetServer,classIDExists,classNameExists,DefineClass,
	StudentCourse,setComboBoxStudentCourse,setTableViewStudentCourseAssigenment,DownoladFile,ClassCourseDetails,UploadFile,
	setComboBoxTeacherCourse,createCourseEntity, setTableViewTeacherCourseAssigenment, getWeeklyHours, getAssArreyList, allAssForTeacher, insertNewAss, UpdateAss;
	
	/** TBD */
}
