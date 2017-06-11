package client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

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
		
		
	}

}
