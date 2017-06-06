package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.RequestType;
import entity.Student;
import entity.Teacher;
import entity.User;
import client.connectionmain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class StuMainGUIController implements Initializable{
	
	public static ClientConsole cli;
	public static Stage primaryStage;
	
	@FXML
	Button Average;
	@FXML
	Button Ass_Sub;
	@FXML
	Button GradeL;
	@FXML
	Button CourseL;
	@FXML
	Button Evalu;
	@FXML
	Button LogOut;

	@FXML
	Label Hello;
	@FXML
	javafx.scene.control.Label stuName;
	@FXML
	Label CalcAvg;
	
	@FXML
	ImageView Logo;
	
	
	public void initialize(URL location, ResourceBundle resources) {
			User s =new User();
			s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
			stuName.setText(s.getName());
		//	Average.setText(Float.toString(s.getAvg()));

		}
	
	@FXML
	public void clsStudentMain() {
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
		return "StuMainGUIController [Average=" + Average + ", Ass_Sub=" + Ass_Sub + ", GradeL=" + GradeL + ", CourseL="
				+ CourseL + ", Evalu=" + Evalu + ", LogOut=" + LogOut + ", Hello=" + Hello + ", stuName=" + stuName
				+ ", CalcAvg=" + CalcAvg + ", Logo=" + Logo + "]";
	}


	

}
