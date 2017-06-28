package client;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
//import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;

/**
 * This class contains all the methods of the opening windows.
 */
public class connectionmain extends Application {
	private static Stage primaryStage;
	private static AnchorPane mainLayout;
//	private static VBox vbox;

	
	@Override
	public void start(Stage primaryStage) throws IOException {
		connectionmain.primaryStage = primaryStage;
		showLogin();
	}
	
	/**
	 * This method log out user from the DB.
	 */
	@Override
	public void stop(){
		MyThread a = new MyThread(RequestType.LOGOUT, IndexList.LOGOUT, MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		if (a.getObj()!=null){
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * show the window of login
	 */
	
	public static void showLogin() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/LoginGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T");
		primaryStage.show();
	}
		 
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		connectionmain.primaryStage = primaryStage;
	}

	public static AnchorPane getMainLayout() {
		return mainLayout;
	}

	public static void setMainLayout(AnchorPane mainLayout) {
		connectionmain.mainLayout = mainLayout;
	}

<<<<<<< HEAD
	/**
	 * show the window of Teacher
	 */
	public static void showTeacherMain() throws IOException {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/client/TeacherMainGUI.fxml"));
			mainLayout = loader.load();
			primaryStage.setScene(new Scene(mainLayout));
			primaryStage.setTitle("M.A.T- Teacher Connection");
			primaryStage.show();
	}
=======

>>>>>>> branch 'master' of git@github.com:shakedna1993/MAT.git
	
	/**
	 * show the window of Student
	 */
	public static void showStudentMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/StudentMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Student Connection");
		primaryStage.show();
	}
	
	/**
	 * show the window of System manager
	 */
	public static void showSysManMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/SystemManMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- System Manager Connection");
		primaryStage.show();
	}
	
	/**
	 * show the window of Secretary
	 */
	public static void showSecretaryMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/SecretaryMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Secretary Connection");
		primaryStage.show();
	}
	
	/**
	 * show the window of School manager
	 */
	public static void showManagerMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/ManagerMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- School Manager Connection");
		primaryStage.show();
	}
	
	/**
	 * show the window of Teacher and school manager (special permission)
	 */
	public static void showTch_ManMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/Tch&ManGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T");
		primaryStage.show();
		}
	
	/**
	 * show the window of Parent
	 */
	public static void showParentMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/ParentMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Parent Connection");
		primaryStage.show();
	}
	
	/**
	 * show the window of Student- course list
	 */
	public static void ShowCourseList() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/CourseListGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Course List");
		primaryStage.show();
	}
	
	/**
	 * show the window of Student- grade list
	 */
	public static void ShowGradeList() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/GradeListGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Grade List");
		primaryStage.show();
	}
<<<<<<< HEAD
	
	/**
	 * show the window of Define course
	 */
=======

>>>>>>> branch 'master' of git@github.com:shakedna1993/MAT.git
	public static void showDefineWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/DefineCourseGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Define Course");
		primaryStage.show();	
	}
<<<<<<< HEAD
	
	/**
	 * show the window of Parent Connection
	 */
	public static void showTeacherGUIAssWindow() throws IOException {
=======

	public static void addStudent(ActionEvent event) throws IOException{
		Stage primaryStage = connectionmain.getPrimaryStage();
>>>>>>> branch 'master' of git@github.com:shakedna1993/MAT.git
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/AddStudentGUI.fxml"));
		Pane root = loader.load();		
		primaryStage.setScene(new Scene(root));		
		primaryStage.setTitle("M.A.T- Add Student");
		primaryStage.show();
	}
<<<<<<< HEAD
	
	
	/**
	 * show the window of Edit assignment
	 */
	public static void showTeacherEditAssGUI() throws IOException {
=======

	public static void newClass(ActionEvent event) throws IOException{
>>>>>>> branch 'master' of git@github.com:shakedna1993/MAT.git
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/DefineNewClassGUI.fxml"));
		Pane root = loader.load();		
		primaryStage.setScene(new Scene(root));		
		primaryStage.setTitle("M.A.T- Define a New Class");
		primaryStage.show();
	}
	public static void editClass(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/EditClassGUI.fxml"));
		Pane root = loader.load();		
		primaryStage.setScene(new Scene(root));		
		primaryStage.setTitle("M.A.T- Add Student");
		primaryStage.show();
	}
	public static void removeClass(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/RemoveClassGUI.fxml"));
		Pane root = loader.load();		
		primaryStage.setScene(new Scene(root));		
		primaryStage.setTitle("M.A.T- Add Student");
		primaryStage.show();
	}
	public static void defineClass4Course(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/DefineClass4CourseGUI.fxml"));
		Pane root = loader.load();		
		primaryStage.setScene(new Scene(root));		
		primaryStage.setTitle("M.A.T- Add Student");
		primaryStage.show();
	}
	
	/**
	 * show the window of Student assignment
	 */
	public static void ShowAssOptions() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/AssMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Assignment");
		primaryStage.show();
	}
	
	/**
	 * show the window of Assignment Submission
	 */
	public static void ShowStudentUploadAss() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/StudentUploadAssGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Assignment Submission");
		primaryStage.show();
	}
	
	/**
	 * show the window of Assignment Submission
	 */
	public static void ShowReportSection() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/ReportsMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Genrate Reports");
		primaryStage.show();
	}
	
	/**
	 * show the window of Report-Teacher classes grades
	 */
	public static void ShowStatisticReportClassTec() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/StatisticReportTCGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Teacher classes grades");
		primaryStage.show();
	}
	
	/**
	 * show the window of Report-Class teachers grades
	 */
	public static void ShowStatisticReportTecClass() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/StatisticReportCTGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Class teachers grades");
		primaryStage.show();
	}
	
	/**
	 * show the window of Define Course ADD Pre Requisite
	 */
	public static void showDefineCourseAddPre() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/DefineCourseAddPreGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Define Course ADD Pre Requisite");
		primaryStage.show();	
	}

	/**
	 * show the window of Edit Course
	 */
	public static void showEditWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/EditCourseGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Edit Course");
		primaryStage.show();
	}
	
	/**
	 * show the window of Remove Course
	 */
	public static void showRemoveWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/RemoveCourseGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Remove Course");
		primaryStage.show();
	}

	/**
	 * show the window of Report-Class courses grades
	 */
	public static void ShowStatisticReportCourseClass() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/StatisticReportCCGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Class courses grades");
		primaryStage.show();
	}

	
	
	public static void main(String[] args) {
		launch(args);
	}

	public static void BlockedParentMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/BlockedParentMain.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Grade List");
		primaryStage.show();
		
	}


	public static void newRequest() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/NewRequestGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- New Request Form");
		primaryStage.show();
	}
	public static void showRequests() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/Requests.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T");
		primaryStage.show();
	}


	public static void openRequest() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/OpenRequestGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Open Request Form");
		primaryStage.show();
	}
	
	public static void ShowEvaluationList() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/EvaluationGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Evaluations");
		primaryStage.show();
	}
	
	
	
	
	
	

	
	
	
	
	
	public static void showTeacherMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/TeacherMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Teacher Connection");
		primaryStage.show();
}
	
	
	public static void showTeacherGUIAssWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/addNewAssingmentGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Teacher Connection");
		primaryStage.show();
	}
	
	
	public static void showTeacherEditAssGUI() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/teacherEditAssGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Teacher Connection");
		primaryStage.show();
	}
	
	
	
	public static void showTeacherDownloadAssStudent() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/teacherDownloadAssStu.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Teacher Connection");
		primaryStage.show();
	}
	
	

}
