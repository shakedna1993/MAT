package client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
/**
 * 
 * DefineNewClassGUI controller class for the "Define new Class" function in the Secretary window.
 *
 */

public class DefineNewClassGUIController implements Initializable {
/**
 * temp class placeholder
 */
	private static entity.Class tempClass;

	public static entity.Class getTempClass() {
		return tempClass;
	}

	public static void setTempClass(entity.Class c) {
		tempClass = c;
	}

	@FXML
	private Button back_button;
	@FXML
	private Button define_button;

	@FXML
	Label id_label;
	@FXML
	Label name_label;
	@FXML
	Label max_label;

	@FXML
	TextField id_field;
	@FXML
	TextField name_field;
	@FXML
	TextField max_field;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
/**
 * This method is called when pressing the "Define" button.
 * It verifies all input fields and checks the DB for duplicate entries before trying to send it the DB.
 * After creating the new class it opens the "Edit/Remove Class" window with the newly created class.
 */
	@FXML
	private void defineClassBtn() {
		if (id_field.getText().equals("") || name_field.getText().equals("") || max_field.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all fields");

			alert.show();
			return;
		}
		if (classIDExists(id_field.getText())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Class already exsists");
			alert.setHeaderText(null);
			alert.setContentText("Class ID already exists in DB");

			alert.show();
			return;
		}
		if (classNameExists(name_field.getText())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Class already exsists");
			alert.setHeaderText(null);
			alert.setContentText("Class Name already exists in DB");

			alert.show();
			return;
		}
		if (Integer.parseInt(max_field.getText()) < 1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Max Number Error");
			alert.setHeaderText(null);
			alert.setContentText("Max number must be greater than 0");

			alert.show();
			return;
		}
		try {
			defineClass(id_field.getText(), name_field.getText(), Integer.parseInt(max_field.getText()));

			connectionmain.editClass(null);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
/**
 * This method checks if the given String 'cid' is a class ID that already exists in the DB.
 * @param cid - the class ID in question.
 * @return true if the class ID is already in the DB, false if it doesn't.
 */
	private boolean classIDExists(String cid) {
		MyThread a = new MyThread(RequestType.classIDExists, IndexList.classIDExists, cid);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return ((boolean) MsgFromServer.getDataListByIndex(IndexList.classIDExists));
	}
	/**
	 * This method checks if the given String 'name' is a class name that already exists in the DB.
	 * @param name - the class name in question.
	 * @return true if the class name is already in the DB, false if it doesn't.
	 */
	private boolean classNameExists(String name) {
		MyThread a = new MyThread(RequestType.classNameExists, IndexList.classNameExists, name);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return ((boolean) MsgFromServer.getDataListByIndex(IndexList.classNameExists));
	}
/**
 * This method creates an entity from the given parameters and sends it to the server DB.
 * @param cid - the class ID.
 * @param name - the class Name.
 * @param max - the class's max students allowed.
 */
	private void defineClass(String cid, String name, int max) {
		entity.Class c = new entity.Class();
		c.setClassId(cid);
		c.setName(name);
		c.setMAXStudent(max);
		tempClass = c;
		MyThread a = new MyThread(RequestType.DefineClass, IndexList.DefineClass, c);
		a.start();
		try {
			a.join();
			// System.out.println("Success");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}
	/**
	 * Goes back to the main Secretary menu.
	 * @param event
	 * @throws Exception
	 */
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
}
