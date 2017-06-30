
package client;

/**
 * Enums for query for opCheck.java
 *
 */
public enum RequestType {

	/** Add new request from server */



	parSetStudentComboBox, avgOneStudent, BlockParent, unBlockParent,
	StudentsList,Teacherdetails,UpdateUnit,LOGIN,LOGOUT,StudentDetails,StudentExists,
	ParentExists,AddStudent,UserNameExists,ResetServer,classIDExists,classNameExists,
	DefineClass,setTableViewStudentCourseAssigenment,DownoladFile,ClassCourseDetails,UploadFile,
	setComboBoxTeacherCourse,createCourseEntity,setTableViewTeacherCourseAssigenment,
	getWeeklyHours,getAssArreyList,allAssForTeacher,insertNewAss,UpdateAss,
	StudentCourse,setComboBoxStudentCourse,createReportEntity,createTeacherEntity,createClassEntity,
	createSemesterEntity,TeacherClassList,TecNameToId,ClassNameToId,ClassTeacherList,getStudentInClass,
	RemoveStudentFromClass,getStudentInNoClass,AddStudentToClass,DeActivateRequest,UpdateMaxStudents,
	getClassCourses,getAvailableCoursesForClass,getTeachersForCourse,RemoveClassFromCourse,
	getCurrentSemesterID,RemoveStudentFromCourse,AddClassToCourse,getClassByID,getRequestByID,ChangeTeacherAppointment,
	AddStudentToCourse,OpenNewSemester,CheckStudentPreReq,DeleteClass,getUsersByRole,
	getUserCoursesInCurrSemester,getAllCoursesInCurrSemester,UserIdExists,AddNewRequest,
	getActiveRequests,getCourseByID,getUserByID,getClassListForTeacherInCourse,
	UnitExists, CourseExists, DefineNewCourse, CoursesList, DefinePreReq, RemoveCourse,RenameCourse,WeeklyHoursUpdate,
	PreReqList,RemovePreReq,StudentEvaluations,DownloadAssigenment,DownloadStuEvaluation,DownloadStuGradeFile,
	RequestsInfo,getUserDetailsById, ApprovalRequest, RejectRequest, getActiveClasses,CourseGradeList,
	uploadTeacherAss,allCourseForTeacher,downloadStudentAssForTeacher,createCourseEntityByName, listOfStudentForAssCourse, downloadOneFileStud, uploadEvaluation, uploadGradeFile, assCourseTeach, createClassEntityByCourseId;
	
	/** TBD */
}
