package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Teacher;
import entity.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;

public class SecMainGUIController implements Initializable {

	public static ClientConsole cli;

	@FXML
	Button NewSem;
	@FXML
	Button DefCl_Cs;
	@FXML
	Button DefineCl;
	@FXML
	Button EditCl;
	@FXML
	Button RemoveCl;
	@FXML
	Button add_student_btn;

	@FXML
	Button Req_Regi;
	@FXML
	Button Req_Remo;
	@FXML
	Button Req_Tch;
	@FXML
	Button Regi;
	@FXML
	Button Remo;
	@FXML
	Button TchAp;
	@FXML
	Button Check;

	@FXML
	Button LogOut;

	@FXML
	Label Hello;
	@FXML
	Label ReqD;
	@FXML
	Label General;
	@FXML
	Label Req;
	@FXML
	Label InReqID;

	@FXML
	TextField Req_ID;
	@FXML
	javafx.scene.control.Label SecName;
	@FXML
	javafx.scene.control.Label ReqDes;

	@FXML
	ImageView Logo;

	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		SecName.setText(s.getName());
	}

	@FXML
	public void clsSecretaryMain() {
		MyThread a = new MyThread(RequestType.LOGOUT, IndexList.LOGOUT,
				MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			connectionmain.showLogin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "SecMainGUIController [NewSem=" + NewSem + ", DefCl_Cs=" + DefCl_Cs + ", DefineCl=" + DefineCl
				+ ", EditCl=" + EditCl + ", RemoveCl=" + RemoveCl + ", Req_Regi=" + Req_Regi + ", Req_Remo=" + Req_Remo
				+ ", Req_Tch=" + Req_Tch + ", Regi=" + Regi + ", Remo=" + Remo + ", TchAp=" + TchAp + ", Check=" + Check
				+ ", LogOut=" + LogOut + ", Hello=" + Hello + ", ReqD=" + ReqD + ", General=" + General + ", Req=" + Req
				+ ", InReqID=" + InReqID + ", Req_ID=" + Req_ID + ", SecName=" + SecName + ", ReqDes=" + ReqDes
				+ ", Logo=" + Logo + "]";
	}

	@FXML
	public void addStudent(ActionEvent event) throws IOException {
		connectionmain.addStudent(event);
	}

	@FXML
	public void newClass(ActionEvent event) throws IOException {
		connectionmain.newClass(event);
	}

	@FXML
	public void editClass(ActionEvent event) throws IOException {
		connectionmain.editClass(event);
	}

	@FXML
	public void removeClass(ActionEvent event) throws IOException {
		connectionmain.removeClass(event);
	}

	@FXML
	public void defineClass4Course(ActionEvent event) throws IOException {
		connectionmain.defineClass4Course(event);
	}
	
	@FXML
	public void openNewSemester(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION, "This action will open a new semester.\nThe current semester will be set inactive and some courses may change.\nAre you sure you want to open a new semester?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			MyThread a = new MyThread(RequestType.OpenNewSemester, IndexList.OpenNewSemester, null);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
	
			boolean b = (boolean) (MsgFromServer.getDataListByIndex(IndexList.OpenNewSemester));
		}
	}

}
