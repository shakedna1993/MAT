package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;

public class AddStudentGUIController implements Initializable {
	public static ClientConsole cli;

	@FXML
	private Button back_button;
	@FXML
	private Button add_button;

	@FXML
	private TextField id_field;
	@FXML
	private TextField name_field;
	@FXML
	private TextField user_field;
	@FXML
	private TextField password_field;
	@FXML
	private TextField confirm_field;
	@FXML
	private TextField parentid_field;

	@FXML
	private Label id_label;
	@FXML
	private Label name_label;
	@FXML
	private Label user_label;
	@FXML
	private Label password_label;
	@FXML
	private Label confirm_label;
	@FXML
	private Label parentid_label;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void addButton() {
		if (id_field.getText().length() != 9) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid ID");

			alert.show();
			return;
		}
		if (name_field.getText().length() < 2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid name");

			alert.show();
			return;
		}
		if (user_field.getText().length() < 1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid name");

			alert.show();
			return;
		}
		if (password_field.getText().length() < 1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid password");

			alert.show();
			return;
		}
		if (!password_field.getText().equals(confirm_field.getText())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("The Passwords do not match");

			alert.show();
			return;
		}
		if (parentid_field.getText().length() != 9) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid Parent ID");

			alert.show();
			return;
		}

		if (studentExists(id_field.getText())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Student already exsists");
			alert.setHeaderText(null);
			alert.setContentText("Student ID already exists in DB");

			alert.show();
			return;
		}
		if (!parentExists(parentid_field.getText())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Parent doesn't exsist");
			alert.setHeaderText(null);
			alert.setContentText("Parent ID doesn't exist in DB");

			alert.show();
			return;
		}
		if (!userNameExists(user_field.getText())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("PUsername Already Exists!");
			alert.setHeaderText(null);
			alert.setContentText("Username Already Exists!");

			alert.show();
			return;
		}
		try {
			addStudent(id_field.getText(), name_field.getText(), user_field.getText(), password_field.getText(),
					parentid_field.getText());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void addStudent(String sid, String name, String user, String password, String pid) {
		Student s = new Student();
		s.setId(sid);
		s.setName(name);
		s.setUserName(user);
		s.setPassword(password);
		s.setParentId(pid);
		MyThread a = new MyThread(RequestType.AddStudent, IndexList.AddStudent, s);
		a.start();
		try {
			a.join();
			// System.out.println("Success");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}

	@FXML
	private void backButton(ActionEvent event) throws Exception {
		Stage primaryStage = connectionmain.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/SecretaryMainGUI.fxml"));
		Pane root = loader.load();
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("M.A.T- Secretary Connection");
		primaryStage.show();
	}

	private boolean studentExists(String sid) {
		MyThread a = new MyThread(RequestType.StudentExists, IndexList.StudentExists, sid);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return ((String) MsgFromServer.getDataListByIndex(IndexList.StudentExists)).equals("true");
	}

	private boolean userNameExists(String name) {
		MyThread a = new MyThread(RequestType.UserNameExists, IndexList.UserNameExists, name);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return ((String) MsgFromServer.getDataListByIndex(IndexList.UserNameExists)).equals("true");
	}

	private boolean parentExists(String pid) {
		MyThread a = new MyThread(RequestType.ParentExists, IndexList.ParentExists, pid);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return ((String) MsgFromServer.getDataListByIndex(IndexList.ParentExists)).equals("true");
	}

}
