package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Requests;
import entity.Student;
import entity.Teacher;
import entity.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	
	private static Requests selectedRequest;

	public static Requests getSelectedRequest() {
		return selectedRequest;
	}

	public static void setSelectedRequest(Requests selectedRequest) {
		selectedRequest = selectedRequest;
	}

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
	Button newReq_btn;
	@FXML
	Button openReq_btn;
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
	private TableView<Requests> requestListTable;

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
		getRequests();
	}

	private void getRequests() {
		MyThread a = new MyThread(RequestType.getActiveRequests, IndexList.getActiveRequests, null);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		ArrayList<Requests> reqList = (ArrayList<Requests>) (MsgFromServer.getDataListByIndex(IndexList.getActiveRequests));
		
		requestListTable.getColumns().clear();

		TableColumn<Requests, String> c1 = new TableColumn<>("ID");
		c1.setCellValueFactory(new PropertyValueFactory<>("ReqId"));
		TableColumn<Requests, String> c3 = new TableColumn<>("User ID");
		c3.setCellValueFactory(new PropertyValueFactory<>("UserId"));
		TableColumn<Requests, String> c4 = new TableColumn<>("Course ID");
		c4.setCellValueFactory(new PropertyValueFactory<>("CourseId"));
		TableColumn<Requests, String> c5 = new TableColumn<>("Type");
		c5.setCellValueFactory(new PropertyValueFactory<>("ReqTypeString"));
		TableColumn<Requests, String> c6 = new TableColumn<>("Status");
		c6.setCellValueFactory(new PropertyValueFactory<>("statusString"));
		

		requestListTable.getColumns().addAll(c1, c3, c4, c5, c6);
		requestListTable.setItems(FXCollections.observableArrayList(reqList));
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
	public void newRequest(ActionEvent event) throws IOException {
		connectionmain.newRequest();
	}

	@FXML
	public void openRequest(ActionEvent event) throws IOException {
		selectedRequest = requestListTable.getSelectionModel().getSelectedItem();
		if (selectedRequest == null) return;
		connectionmain.openRequest();
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
