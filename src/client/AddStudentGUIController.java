package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class AddStudentGUIController implements Initializable {
	public static ClientConsole cli;
	public static Stage primaryStage;
	
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
	
	public void addButton(){
		if (id_field.getText().length() !=9) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid ID");

			alert.show();
		}
		if (name_field.getText().length() <2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid name");

			alert.show();
		}
		if (user_field.getText().length() <1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid name");

			alert.show();
		}
		if (password_field.getText().length() <1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid password");

			alert.show();
		}
		if (!password_field.getText().equals(confirm_field.getText())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("The Passwords do not match");

			alert.show();
		}
		if (parentid_field.getText().length() !=9) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid Parent ID");

			alert.show();
		}
		
		if (studentExists(id_field.getText())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Student already exsists");
			alert.setHeaderText(null);
			alert.setContentText("Student ID already exists in DB");

			alert.show();
		}
		if (!parentExists(parentid_field.getText())){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Parent doesn't exsist");
			alert.setHeaderText(null);
			alert.setContentText("Parent ID doesn't exist in DB");

			alert.show();
		}
	}

	@FXML
	private void backButton(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide();
	}
	
	private boolean studentExists(String sid) {
		MyThread a = new MyThread(RequestType.StudentExists, IndexList.StudentExists, sid);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}	
			return (boolean)MsgFromServer.getDataListByIndex(IndexList.StudentExists);

	}

	
	private boolean parentExists(String pid) {
		// TODO Auto-generated method stub
		return false;
	}

}
