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

public class connectionmain extends Application {
	private static Stage primaryStage;
	private static AnchorPane mainLayout;
//	private static VBox vbox;

	@Override
	public void start(Stage primaryStage) throws IOException {
		connectionmain.primaryStage = primaryStage;
		showLogin();
	}
	
	
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

	public static void showTeacherMain() throws IOException {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/client/TeacherMainGUI.fxml"));
			mainLayout = loader.load();
			primaryStage.setScene(new Scene(mainLayout));
			primaryStage.setTitle("M.A.T- Teacher Connection");
			primaryStage.show();
	}
	
	public static void showStudentMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/StudentMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Student Connection");
		primaryStage.show();
	}
	
	public static void showSysManMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/SystemManMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- System Manager Connection");
		primaryStage.show();
	}
	
	public static void showSecretaryMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/SecretaryMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Secretary Connection");
		primaryStage.show();
	}
	
	public static void showManagerMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/ManagerMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- School Manager Connection");
		primaryStage.show();
	}
	
	public static void showTch_ManMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/Tch&ManGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T");
		primaryStage.show();
		}
	
	public static void showParentMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/ParentMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Parent Connection");
		primaryStage.show();
	}
	
	public static void ShowCourseList() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/CourseListGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Course List");
		primaryStage.show();
	}
	
	public static void ShowGradeList() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/GradeListGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Grade List");
		primaryStage.show();
	}

	public static void showDefineWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/DefineCourseGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Define Course");
		primaryStage.show();	
	}
	public static void showTeacherGUIAssWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/addNewAssingmentGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Parent Connection");
		primaryStage.show();
	}
	public static void addStudent(ActionEvent event) throws IOException{
		Stage primaryStage = connectionmain.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/AddStudentGUI.fxml"));
		Pane root = loader.load();		
		primaryStage.setScene(new Scene(root));		
		primaryStage.setTitle("M.A.T- Add Student");
		primaryStage.show();
	}

	public static void newClass(ActionEvent event) throws IOException{
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
	
	public static void ShowAssOptions() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/AssMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Assignment");
		primaryStage.show();
	}
	
	public static void ShowStudentUploadAss() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/StudentUploadAssGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Assignment Submission");
		primaryStage.show();
	}
	
	public static void ShowReportSection() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/ReportsMainGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Genrate Reports");
		primaryStage.show();
	}
	
	public static void ShowStatisticReportClassTec() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/StatisticReportTCGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Teacher classes grades");
		primaryStage.show();
	}
	
	public static void ShowStatisticReportTecClass() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/StatisticReportCTGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Class teachers grades");
		primaryStage.show();
	}
	
	public static void showDefineCourseAddPre() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/DefineCourseAddPreGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Define Course ADD Pre Requisite");
		primaryStage.show();	
	}

	public static void showEditWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/EditCourseGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Edit Course");
		primaryStage.show();
	}
	
	public static void showRemoveWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/RemoveCourseGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Remove Course");
		primaryStage.show();
	}

	public static void ShowStatisticReportCourseClass() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/StatisticReportCCGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Class courses grades");
		primaryStage.show();
	}
	public static void showTeacherEditAssGUI() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/teacherEditAssGUI.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T- Parent Connection");
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

}
