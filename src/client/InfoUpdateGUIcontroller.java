package client;

import java.io.IOException;

import client.MsgFromServer;
import entity.Teacher;
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


public class InfoUpdateGUIcontroller {
	
	public static ClientConsole cli;
	public static Stage primaryStage;


	@FXML
	Button info;
	@FXML
	Button CancelBtn;

	@FXML
	TextField Id;
	@FXML
	TextField unitupdate;
	@FXML
	PasswordField password;
	@FXML
	TextField IP_text;
	@FXML
	TextField PortText;
	@FXML
	Label name,id,unit;
	
	@FXML
	public void Info() throws IOException {
		try {
 
			Teacher mem = new Teacher(Integer.parseInt(Id.getText().length() == 0 ? "-1" : Id.getText()));
			
			if (mem.getId() == -1 ) {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Empty Fields");
				alert.setHeaderText(null);
				alert.setContentText("Please enter Id");

				alert.show();
				return;
			}
			
			MyThread a = new MyThread(RequestType.Teacherdetails, IndexList.Teacherdetails, mem);
			a.start();
			a.join();
			//
	
			if(((Teacher) (MsgFromServer.getDataListByIndex(IndexList.Teacherdetails))).getId() == 0) {
 				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Wrong details");
				alert.setHeaderText(null);
				alert.setContentText("The Id is wrong");

				alert.show();
				return;
			}
			
		Teacher t =new Teacher();
		t=(Teacher) (MsgFromServer.getDataListByIndex(IndexList.Teacherdetails));
		name.setText(t.getName());
		unit.setText(t.getUnit());
		id.setText(Integer.toString(t.getId()));
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void UpdateUnit() throws IOException {
		try {
 
			Teacher mem = new Teacher(unitupdate.getText(),Integer.parseInt(Id.getText().length() == 0 ? "-1" : Id.getText()));
			
			if (mem.getUnit().length()== 0 || mem.getId()== -1) {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Empty Fields");
				alert.setHeaderText(null);
				alert.setContentText("Please enter Id or teacher unit");

				alert.show();
				return;
			}

			MyThread a = new MyThread(RequestType.UpdateUnit, IndexList.UpdateUnit, mem);
			a.start();
			a.join();
			//
	
			if(((MsgFromServer.getDataListByIndex(IndexList.UpdateUnit)).equals("Success"))) {
 				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("The update was Successful ");

				alert.show();
				return;
			}
			else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("The update faild ");

				alert.show();
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * This method cancel the login 
	 */
	@FXML
	public void clsInfoUpdate() {
		try {
			Stage stage = (Stage) CancelBtn.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	@Override
	public String toString() {
		return "LoginGUIcontroller [info=" + info + ", CancelBtn=" + CancelBtn + ", Id=" + Id + ", password=" + password
				+ ", IP_text=" + IP_text + ", PortText=" + PortText + ", name=" + name + ", id=" + id + ", unit=" + unit
				+ "]";
	}
}
