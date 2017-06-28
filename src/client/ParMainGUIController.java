package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

import client.RequestType;
import entity.Course;
import entity.Student;
import entity.User;
import client.connectionmain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;


/**
 *	
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
	 * d
	 * 
	 *	@param	location an absolute URL giving the base location
	 *	@param	resources
	 *
	 *	@return void
	 */
	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		parName.setText(s.getName());
		parSetStudentComboBox(s.getId());
		StuN.getValue();
	}

	
	/**
	 * d
	 * 
	 *	@param	id
	 *
	 *	@return void
	 */
	private void parSetStudentComboBox(String id) {
		MyThread a = new MyThread(RequestType.parSetStudentComboBox, IndexList.parSetStudentComboBox, id);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		DaddyStudent = (ArrayList<Student>) (MsgFromServer.getDataListByIndex(IndexList.parSetStudentComboBox));
		ArrayList<String> nameMyStu = new ArrayList<String>();
		for (int i = 0; i < DaddyStudent.size(); i++) {
			nameMyStu.add(DaddyStudent.get(i).getName());
		}
		list = FXCollections.observableArrayList(nameMyStu);
		StuN.setItems(list);
	}
	
	
	

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
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		float avg = (float) (MsgFromServer.getDataListByIndex(IndexList.avgOneStudent));
		selectedSon.setAvg(avg);
		String avgToScreen = Float.toString(selectedSon.getAvg());
		CalcAvg.setText(avgToScreen);
	}

	
	/**
	 * d
	 * 
	 *
	 *	@return void
	 */
	private void selectedChild() {
		String name = new String();
		boolean flag=false;
		name = StuN.getSelectionModel().getSelectedItem();
		if (name != null) {

			int i;
			for (i = 0; i < DaddyStudent.size(); i++) {
				if (name.equals(DaddyStudent.get(i).getName())){
					flag=true;
					break;}
			}
			if (flag)
				selectedSon = DaddyStudent.get(i);
		} else
			selectedSon = null;
	}

	
	
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
		try {
			connectionmain.ShowCourseList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
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
		try {
			connectionmain.ShowGradeList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
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
				+ AvgBtn + ", LogOut=" + LogOut + ", StuN=" + StuN + ", CalcAvg=" + CalcAvg
				+ ", parName=" + parName +"]";
	}

	
	
	public static Student getSelectedSon() {
		return selectedSon;
	}

}
