package client;

import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.RequestType;
import entity.Student;
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
	Button Ass_Sub;
	@FXML
	Button GradeL;
	@FXML
	Button CourseL;
	@FXML
	Button Evalu;
	@FXML
	Button Avg;
	@FXML
	Button LogOut;

	@FXML
	Label Hello;
	@FXML
	Label Per_File;
	@FXML
	Label Ass;
	@FXML
	Label AvgC;
	@FXML
	javafx.scene.control.Label stuName;
	
	@FXML
	ImageView Logo;
	
	
	public void initialize(URL location, ResourceBundle resources) {
			User s =new User();
			s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
			stuName.setText(s.getName());
		}
	
	@FXML
	public void Avgset() {
		String avg;		
		Student stud =new Student();
		stud=(Student) (MsgFromServer.getDataListByIndex(IndexList.StudentDetails));
		avg=Float.toString(stud.getAvg());
		AvgC.setText(avg);
	}
	
	@FXML
	public void CourseList() {
		try {
			connectionmain.ShowCourseList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void GradeList() {
		try {
			connectionmain.ShowGradeList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void AssignmentWin() {
		try {
			connectionmain.ShowAssOptions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		return "StuMainGUIController [Ass_Sub=" + Ass_Sub + ", GradeL=" + GradeL + ", CourseL=" + CourseL + ", Evalu="
				+ Evalu + ", Avg=" + Avg + ", LogOut=" + LogOut + ", Hello=" + Hello + ", Per_File=" + Per_File
				+ ", Ass=" + Ass + ", AvgC=" + AvgC + ", stuName=" + stuName + ", Logo=" + Logo + "]";
	}
	
}
