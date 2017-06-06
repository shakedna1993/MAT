package client;



import java.io.IOException;

import client.MsgFromServer;

import client.RequestType;
import entity.Teacher;
import entity.User;
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
			User mem = new User(username.getText(), password.getText());
			
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
	
			if(((User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN))).getId().equals("-1")) {
 				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong details");
				alert.setHeaderText(null);
				alert.setContentText("The username or password are wrong");

				alert.show();
				return;
			}
			
			if(((User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN))).getIsConnected() == 1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Connection details");
				alert.setHeaderText(null);
				alert.setContentText("The username is already connected");

				alert.show();
				return;
			}
			if(((User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN))).getBlocked() == 1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Ban");
				alert.setHeaderText(null);
				alert.setContentText("The user is banned from the system");
				alert.show();
				return;
			}
			// Role={1-Secretary, 2-Manager, 3-Teacher, 4-Student, 5-System manager, 6- Parent, 7- Manager&Teacher}
			switch(((User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN))).getRole()){
			case 1:
			//	connectionmain.showSecretary();
				break;
			case 2:
			//	connectionmain.showManager();
				break;	
			case 3:
				connectionmain.showTeacher();
				break;
			case 4:
				connectionmain.showStudentMain();
				break;	
			case 5:
			//	connectionmain.showSysManager();
				break;
			case 6:
			//	connectionmain.showParent();
				break;
			case 7:
			//	connectionmain.showManager&Teacher();
				break;	
			}
			

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	/*	try {
			Stage stage = (Stage) Logcancel.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
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

	@Override
	public String toString() {
		return "LoginGUIcontroller [Login=" + Login + ", Logcancel=" + Logcancel + ", username=" + username
				+ ", password=" + password + ", IP_text=" + IP_text + ", PortText=" + PortText + "]";
	}

	
}
