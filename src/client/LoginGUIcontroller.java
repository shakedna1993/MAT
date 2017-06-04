package client;



import java.io.IOException;

import client.MsgFromServer;

import client.RequestType;
import entity.Teacher;
import client.ClientConsole;
import client.connectionmain;
import thred.IndexList;
import thred.MyThread;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

/**
 * This class is the controller for the login GUI.
 */
public class LoginGUIcontroller {

	public static ClientConsole cli;
	public static Stage primaryStage;


	@FXML
	Button Login;
	@FXML
	Button Logcancel;

	@FXML
	TextField username;
	@FXML
	PasswordField password;
	@FXML
	TextField IP_text;
	@FXML
	TextField PortText;

	@FXML
	public void initialize() {
	}

	/**This method check few things when member trying to login
	 * 1.if there are Empty Fields 
	 * 2.if the The username or password are wrong
	 * 3.if the username is already connected
	 * 4.if The user is banned from the system
	 * @throws IOException throw exception in any of the cases above
	 */
	@FXML
	public void Login() throws IOException {
		try {

			cli = new ClientConsole(IP_text.getText(), Integer.valueOf(PortText.getText())); 
			Member mem = new Member(username.getText(), password.getText());
			
			if (mem.getUserName().length() == 0 || mem.getPassword().length() == 0) {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Empty Fields");
				alert.setHeaderText(null);
				alert.setContentText("Please enter username and password");

				alert.show();
				return;
			}
			
			MyThread a = new MyThread(RequestType.LOGIN, IndexList.LOGIN, mem);
			a.start();
			a.join();
			//
	
			if(((Member) (MsgFromServer.getDataListByIndex(IndexList.LOGIN))).getmID() == -1) {
 				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong details");
				alert.setHeaderText(null);
				alert.setContentText("The username or password are wrong");

				alert.show();
				return;
			}
			
			if(((Member) (MsgFromServer.getDataListByIndex(IndexList.LOGIN))).getIsConnected() == 1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Connection details");
				alert.setHeaderText(null);
				alert.setContentText("The username is already connected");

				alert.show();
				return;
			}
			if(((Member) (MsgFromServer.getDataListByIndex(IndexList.LOGIN))).getmStatus() == 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Ban");
				alert.setHeaderText(null);
				alert.setContentText("The user is banned from the system");

				alert.show();
				return;
			}
			

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		try {
			Stage stage = (Stage) Logcancel.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	//	connectionmain.showMainMenu();
	}


	/**
	 * This method cancel the login 
	 */
	public void clsLogin() {
		try {
			Stage stage = (Stage) Logcancel.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	 
}
