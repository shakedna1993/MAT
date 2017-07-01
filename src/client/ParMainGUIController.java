package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.RequestType;
import entity.Student;
import entity.User;
import client.connectionmain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

/**
 * This class is the controller for the parents main screen GUI.
 */
public class ParMainGUIController implements Initializable {

	public static ClientConsole cli;
	public static Stage primaryStage;

	private ArrayList<Student> DaddyStudent = new ArrayList<Student>();
	private static Student selectedSon = new Student();
	private ObservableList<String> list;

	@FXML
	private Button GradeL;
	@FXML
	private Button CourseL;
	@FXML
	private Button Evalu;
	@FXML
	private Button LogOut;
	@FXML
	private Button AvgBtn;
	@FXML
	private javafx.scene.control.Label parName;
	@FXML
	private javafx.scene.control.Label CalcAvg;
	@FXML
	private ComboBox<String> StuN;

	/**
	 * this method carried out First thing. It is designed to initialize the information in the window 
	 * 
	 */
	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		parName.setText(s.getName());
		parSetStudentComboBox(s.getId());
		StuN.getValue();
	}

	/**
	 * This method is used to fill the combo box with the parent's children so that they can follow their information in school
	 * 
	 * @param id is the parent id to look about all is children.
	 *
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	private void parSetStudentComboBox(String id) {
		MyThread a = new MyThread(RequestType.parSetStudentComboBox, IndexList.parSetStudentComboBox, id);
		a.start();
		try {a.join();} 
		catch (InterruptedException e1) {e1.printStackTrace();}

		DaddyStudent = (ArrayList<Student>) (MsgFromServer.getDataListByIndex(IndexList.parSetStudentComboBox));
		ArrayList<String> nameMyStu = new ArrayList<String>();
		for (int i = 0; i < DaddyStudent.size(); i++) {
			nameMyStu.add(DaddyStudent.get(i).getName());
		}
		list = FXCollections.observableArrayList(nameMyStu);
		StuN.setItems(list);
	}

	
	
	/**
	 *	This method is used to calculate the average of the selected student
	 */
	@FXML
	private void Avgset() {
		selectedChild();
		if (selectedSon == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please select a student");
			alert.show();
			return;
		}
		MyThread a = new MyThread(RequestType.avgOneStudent, IndexList.avgOneStudent, selectedSon.getId());
		a.start();
		try {a.join();} 
		catch (InterruptedException e1) {e1.printStackTrace();}
		
		float avg = (float) (MsgFromServer.getDataListByIndex(IndexList.avgOneStudent));
		selectedSon.setAvg(avg);
		String avgToScreen = Float.toString(selectedSon.getAvg());
		CalcAvg.setText(avgToScreen);
	}

	/**
	 * This method is used to save the selected student 
	 */
	private void selectedChild() {
		String name = new String();
		boolean flag = false;
		name = StuN.getSelectionModel().getSelectedItem();
		if (name != null)
		{
			int i;
			for (i = 0; i < DaddyStudent.size(); i++) {
				if (name.equals(DaddyStudent.get(i).getName())) {
					flag = true;
					break;
				}
			}
			if (flag)
				selectedSon = DaddyStudent.get(i);
			else
				selectedSon = null;
		}
	}

	
	/**
	 * This method is used to show the student's courses if they exist
	 */
	@FXML
	private void CourseList() {
		selectedChild();
		if (selectedSon == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please select a student");
			alert.show();
			return;
		}
		try {connectionmain.ShowCourseList();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * This method is used to show the student's Grades if they exist
	 */
	@FXML
	private void GradeList() {
		selectedChild();
		if (selectedSon == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please select a student");
			alert.show();
			return;
		}
		try {connectionmain.ShowGradeList();}
		catch (IOException e) {e.printStackTrace();}
	}

	
	
	/**
	 * This method is used to show the student's Evaluations if they exist
	 */
	@FXML
	public void EvaluationWin() {
		selectedChild();
		if (selectedSon == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please select a student");
			alert.show();
			return;
		}
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
	public void clsParentMain() {
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
		return "StuMainGUIController [GradeL=" + GradeL + ", CourseL=" + CourseL + ", Evalu=" + Evalu + ", Avg="
				+ AvgBtn + ", LogOut=" + LogOut + ", StuN=" + StuN + ", CalcAvg=" + CalcAvg + ", parName=" + parName+ "]";
	}

	public static Student getSelectedSon() {
		return selectedSon;
	}

}
