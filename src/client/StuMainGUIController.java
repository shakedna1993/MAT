package client;

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

/**
 * This class is the controller for the Student main screen GUI.
 */
public class StuMainGUIController implements Initializable {

	public static ClientConsole cli;
	public static Stage primaryStage;

	@FXML
	private Button Ass_Sub;
	@FXML
	private Button GradeL;
	@FXML
	private Button CourseL;
	@FXML
	private Button Evalu;
	@FXML
	private Button Avg;
	@FXML
	private Button LogOut;
	@FXML
	private ImageView LogOutIcon;
	@FXML
	private ImageView BackGround;

	@FXML
	private Label Hello;
	@FXML
	private Label Per_File;
	@FXML
	private Label Ass;
	@FXML
	private Label AvgC;
	@FXML
	private javafx.scene.control.Label stuName;

	@FXML
	private ImageView Logo;
	
	private static String id;

	/**
	 * initialize-initialize the student name.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		stuName.setText(s.getName());
		id=s.getId();
	}

	/**
	 * This method gets the student average and display it. 
	 */
	@FXML
	private void Avgset() {
		MyThread a = new MyThread(RequestType.avgOneStudent, IndexList.avgOneStudent, id);
		a.start();
		try {a.join();} 
		catch (InterruptedException e1) {e1.printStackTrace();}
		
		float avg;
		avg = (float)(MsgFromServer.getDataListByIndex(IndexList.avgOneStudent));
		AvgC.setText(""+avg);
	}

	/**
	 * This method shows list of courses the student learning 
	 */
	@FXML
	private void CourseList() {
		try {
			connectionmain.ShowCourseList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method shows the student grade list 
	 */
	@FXML
	private void GradeList() {
		try {
			connectionmain.ShowGradeList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method shows the Assignment window
	 */
	@FXML
	private void AssignmentWin() {
		try {
			connectionmain.ShowAssOptions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method shows the Evaluation window
	 */
	@FXML
	private void EvaluationWin() {
		try {
			connectionmain.ShowEvaluationList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Logout from the server  
	 */
	@FXML
	private void clsStudentMain() {
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
		return "StuMainGUIController [Ass_Sub=" + Ass_Sub + ", GradeL=" + GradeL + ", CourseL=" + CourseL + ", Evalu="
				+ Evalu + ", Avg=" + Avg + ", LogOut=" + LogOut + ", Hello=" + Hello + ", Per_File=" + Per_File
				+ ", Ass=" + Ass + ", AvgC=" + AvgC + ", stuName=" + stuName + ", Logo=" + Logo + "]";
	}

}
