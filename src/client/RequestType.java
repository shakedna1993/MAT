
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
	parSetStudentComboBox, avgOneStudent, BlockParent, unBlockParent,getActiveClasses,getStudentInClass,RemoveStudentFromClass,getStudentInNoClass,AddStudentToClass,DeActivateRequest,UpdateMaxStudents,
	getClassCourses,getAvailableCoursesForClass,getTeachersForCourse,RemoveClassFromCourse,getCurrentSemesterID,RemoveStudentFromCourse,AddClassToCourse,getClassByID,getRequestByID,ChangeTeacherAppointment,
	AddStudentToCourse,OpenNewSemester,CheckStudentPreReq,DeleteClass,getUsersByRole,getUserCoursesInCurrSemester,getAllCoursesInCurrSemester,UserIdExists,AddNewRequest,getActiveRequests,getCourseByID,getUserByID,getClassListForTeacherInCourse,
	UnitExists, CourseExists, DefineNewCourse, CoursesList, DefinePreReq, RemoveCourse,RenameCourse,WeeklyHoursUpdate,PreReqList,RemovePreReq,		// Rinat
	StudentsList;
	
	/** TBD */
}
