package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;

public class SecMainGUIController implements Initializable{
	
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
			User s =new User();
			s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
			SecName.setText(s.getName());
		}
	
	@FXML
	public void clsSecretaryMain() {
		MyThread a = new MyThread(RequestType.LOGOUT, IndexList.LOGOUT, MsgFromServer.getDataListByIndex(IndexList.LOGIN));
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
				+ ", LogOut=" + LogOut + ", Hello=" + Hello + ", ReqD=" + ReqD + ", General="
				+ General + ", Req=" + Req + ", InReqID=" + InReqID + ", Req_ID=" + Req_ID + ", SecName=" + SecName
				+ ", ReqDes=" + ReqDes + ", Logo=" + Logo + "]";
	}
	@FXML
	public void addStudent(ActionEvent event) throws IOException{
		Stage primaryStage = connectionmain.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/AddStudentGUI.fxml"));
		Pane root = loader.load();		
		primaryStage.setScene(new Scene(root));		
		primaryStage.setTitle("M.A.T- Add Student");
		primaryStage.show();
	}
	@FXML
	public void newClass(ActionEvent event) throws IOException{
		Stage primaryStage = connectionmain.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/DefineNewClassGUI.fxml"));
		Pane root = loader.load();		
		primaryStage.setScene(new Scene(root));		
		primaryStage.setTitle("M.A.T- Add Student");
		primaryStage.show();
	}
	@FXML
	public void editClass(ActionEvent event) throws IOException{
		Stage primaryStage = connectionmain.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/EditClassGUI.fxml"));
		Pane root = loader.load();		
		primaryStage.setScene(new Scene(root));		
		primaryStage.setTitle("M.A.T- Add Student");
		primaryStage.show();
	}
	@FXML
	public void removeClass(ActionEvent event) throws IOException{
		Stage primaryStage = connectionmain.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/RemoveClassGUI.fxml"));
		Pane root = loader.load();		
		primaryStage.setScene(new Scene(root));		
		primaryStage.setTitle("M.A.T- Add Student");
		primaryStage.show();
	}
	@FXML
	public void defineClass4Course(ActionEvent event) throws IOException{
		Stage primaryStage = connectionmain.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/DefineClass4CourseGUI.fxml"));
		Pane root = loader.load();		
		primaryStage.setScene(new Scene(root));		
		primaryStage.setTitle("M.A.T- Add Student");
		primaryStage.show();
	}
	
}
