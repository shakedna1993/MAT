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

public class ParMainGUIController implements Initializable {

	public static ClientConsole cli;
	public static Stage primaryStage;
	private ArrayList<Student> DaddyStudent = new ArrayList<Student>();
	private static Student selectedSon = new Student();

	@FXML
	Button GradeL;
	@FXML
	Button CourseL;
	@FXML
	Button Evalu;
	@FXML
	Button LogOut;
	@FXML
	Button AvgBtn;
	@FXML
	Label Hello;
	@FXML
	javafx.scene.control.Label parName;
	@FXML
	javafx.scene.control.Label CalcAvg;
	@FXML
	ComboBox<String> StuN;
	@FXML
	ImageView Logo;
	private ObservableList<String> list;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		parName.setText(s.getName());
		parSetStudentComboBox(s.getId());
		StuN.getValue();
	}

	@SuppressWarnings("unchecked")
	public void parSetStudentComboBox(String id) {
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
	public void Avgset() {
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

	public void selectedChild() {
		String name = new String();
		name = StuN.getSelectionModel().getSelectedItem();
		if (name != null) {

			int i;
			for (i = 0; i < DaddyStudent.size(); i++) {
				if (name.equals(DaddyStudent.get(i).getName()))
					break;
			}
			selectedSon = DaddyStudent.get(i);
		} else
			selectedSon = null;
	}

	@FXML
	public void CourseList() {

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
	public void GradeList() {

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
				+ AvgBtn + ", LogOut=" + LogOut + ", Hello=" + Hello + ", StuN=" + StuN + ", CalcAvg=" + CalcAvg
				+ ", parName=" + parName + ", Logo=" + Logo + "]";
	}

	public static Student getSelectedSon() {
		return selectedSon;
	}

}
