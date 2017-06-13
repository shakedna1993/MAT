package client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.applet.Main;

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
	    System.out.println("Stage is closing");
	    // Save file
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
