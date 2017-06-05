package client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.applet.Main;

public class connectionmain extends Application {
	private static Stage primaryStage;
	private static AnchorPane mainLayout;
	private static VBox vbox;

	@Override
	public void start(Stage primaryStage) throws IOException {
		connectionmain.primaryStage = primaryStage;
		showcoonect();


	}

	/**
	 * show the window of login
	 */
	
	public static void showUserLogin() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/UserLogin.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("M.A.T");
		primaryStage.show();
	}
	
	public static void showcoonect() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/Connect.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("connect");
		primaryStage.show();
	}
	
	 
		public static void showInfo() throws IOException {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/client/Info&Update.fxml"));
			mainLayout = loader.load();
			primaryStage.setScene(new Scene(mainLayout));
			primaryStage.setTitle("Info");
			primaryStage.show();
		}
		
		public static void showTeacher() throws IOException {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/client/Info&Update.fxml"));
			mainLayout = loader.load();
			primaryStage.setScene(new Scene(mainLayout));
			primaryStage.setTitle("Info");
			primaryStage.show();
		}
		
		
		
	public static void main(String[] args) {
		launch(args);
	}
}
