package client;

import java.io.IOException;

import client.MsgFromServer;

import client.RequestType;
import entity.User;
import client.ClientConsole;
import client.connectionmain;
import thred.IndexList;
import thred.MyThread;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
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
	ImageView Logo;

	@FXML
	public void initialize() {
		Login.setDefaultButton(true);
	}

	/**
	 * This method check few things when user trying to login 1.if there are
	 * Empty Fields 2.if the The username or password are wrong 3.if the
	 * username is already connected 4.if The user is banned from the system The
	 * proper window is opening according to the user role Role={1-Secretary,
	 * 2-Manager, 3-Teacher, 4-Student, 5-System manager, 6- Parent, 7-
	 * Manager&Teacher}
	 * 
	 * @throws IOException
	 *             throw exception in any of the cases above
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

			if (((User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN))).getId().equals("-1")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong details");
				alert.setHeaderText(null);
				alert.setContentText("The username or password are wrong");

				alert.show();
				return;
			}

			if (((User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN))).getIsConnected() == 1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Connection details");
				alert.setHeaderText(null);
				alert.setContentText("The username is already connected");

				alert.show();
				return;
			}
			if (((User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN))).getBlocked() == 1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Block");
				alert.setHeaderText(null);
				alert.setContentText("The user is Block from the system\n Contact school administration in order to unblock your user.");
				alert.show();
				return;
			}
			// Role={1-Secretary, 2-Manager, 3-Teacher, 4-Student, 5-System
			// manager, 6- Parent, 7- Manager&Teacher}
			switch (((User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN))).getRole()) {
			case 1:
				connectionmain.showSecretaryMain();
				break;
			case 2:
				connectionmain.showManagerMain();
				break;
			case 3:
				connectionmain.showTeacherMain();
				break;
			case 4:
				MyThread b = new MyThread(RequestType.StudentDetails, IndexList.StudentDetails,
						MsgFromServer.getDataListByIndex(IndexList.LOGIN));
				b.start();
				try {
					b.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				connectionmain.showStudentMain();
				break;
			case 5:
				connectionmain.showSysManMain();
				break;
			case 6:
					connectionmain.showParentMain();
				break;
			case 7:
				connectionmain.showTch_ManMain();
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("The serve seems to be offline, please try again later");

			alert.show();
		}
		/*
		 * try { Stage stage = (Stage) Logcancel.getScene().getWindow();
		 * stage.close(); } catch (Exception e) { e.printStackTrace(); }
		 */

		// connectionmain.showMainMenu();
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
