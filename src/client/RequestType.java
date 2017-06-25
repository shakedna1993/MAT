
package client;

/**
 * Enums for query for opCheck.java
 *
 */
public enum RequestType {

	/** Add new request from server */
	
	Teacherdetails,UpdateUnit,LOGIN, LOGOUT,StudentDetails,StudentExists,ParentExists,AddStudent,UserNameExists,ResetServer,classIDExists,classNameExists,DefineClass,
	StudentCourse,setComboBoxStudentCourse,setTableViewStudentCourseAssigenment,DownoladFile,ClassCourseDetails,UploadFile,createReportEntity,createTeacherEntity,createClassEntity,createSemesterEntity,
	setComboBoxTeacherCourse,createCourseEntity, setTableViewTeacherCourseAssigenment, getWeeklyHours, getAssArreyList, allAssForTeacher, insertNewAss, UpdateAss,
	parSetStudentComboBox, avgOneStudent, BlockParent, unBlockParent,getAllClasses,getStudentInClass,RemoveStudentFromClass,getStudentInNoClass,AddStudentToClass,getClassCourses,getAvailableCoursesForClass,getTeachersForCourse,RemoveClassFromCourse,getCurrentSemesterID,RemoveStudentFromCourse,AddClassToCourse,AddStudentToCourse,OpenNewSemester,
	StudentsList;
	
	/** TBD */
}
