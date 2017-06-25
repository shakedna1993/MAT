
package client;

/**
 * Enums for query for opCheck.java
 *
 */
public enum RequestType {

	/** Add new request from server */
	
	Teacherdetails,UpdateUnit,LOGIN, LOGOUT,StudentDetails,StudentExists,ParentExists,AddStudent,UserNameExists,ResetServer,classIDExists,classNameExists,DefineClass,
	StudentCourse,setComboBoxStudentCourse,setTableViewStudentCourseAssigenment,DownoladFile,ClassCourseDetails,UploadFile,createReportEntity,createTeacherEntity,createClassEntity,createSemesterEntity,TeacherClassList,TecNameToId,ClassNameToId,ClassTeacherList,
	setComboBoxTeacherCourse,createCourseEntity, setTableViewTeacherCourseAssigenment, getWeeklyHours, getAssArreyList, allAssForTeacher, insertNewAss, UpdateAss,
	parSetStudentComboBox, avgOneStudent, BlockParent, unBlockParent,
	StudentsList;
	
	/** TBD */
}
