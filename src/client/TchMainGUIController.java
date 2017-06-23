package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Assigenment;
import entity.Course;
import entity.Teacher;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class TchMainGUIController implements Initializable {

	public static ClientConsole cli;
	public static Stage primaryStage;

	@FXML
	ComboBox<String> STC;
	@FXML
	Button Post_Ass;
	@FXML
	Button selectBtn;
	@FXML
	Button Check_Ass;
	@FXML
	Button CourseL;
	@FXML
	Button Back;
	@FXML
	Button LogOut;

	@FXML
	Label Hello, selectAss, WeekTH;
	@FXML
	Label selectTC;
	@FXML
	Label General;
	@FXML
	Label Ass;
	@FXML
	javafx.scene.control.Label TchName;
	@FXML
	javafx.scene.control.Label WeekHours;
	@FXML
	ListView<String> PubAss;
	@FXML
	ListView<String> listview;
	@FXML
	ImageView Logo;

	static String assToChoose;

	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		TchName.setText(s.getName());
		String id = s.getId();

		String maxHours = Integer.toString(getWeeklyHours(id));
		WeekHours.setText(maxHours);

		setComboBoxTeacherCourse(id);

	}

	public void addNewAssingment() throws IOException {
		connectionmain.showTeacherGUIAssWindow();
	}

	public int getWeeklyHours(String teacherId) {
		MyThread C = new MyThread(RequestType.getWeeklyHours, IndexList.getWeeklyHours, teacherId);
		C.start();
		try {
			C.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return (int) MsgFromServer.getDataListByIndex(IndexList.getWeeklyHours);

	}

	@SuppressWarnings("unchecked")
	public void setTableViewTeacherCourseAssigenment() {
		listview.getItems().clear();
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		String teacherid = s.getId();
		Object st = STC.getValue();
		String CourseName = st.toString();
		Assigenment ass = new Assigenment();
		ass.setCoursename(CourseName);
		ass.setTeacherid(teacherid);
		MyThread C = new MyThread(RequestType.setTableViewTeacherCourseAssigenment,
				IndexList.setTableViewTeacherCourseAssigenment, ass);
		C.start();
		try {
			C.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		ArrayList<Assigenment> b = (ArrayList<Assigenment>) MsgFromServer
				.getDataListByIndex(IndexList.setTableViewTeacherCourseAssigenment);
		for (int i = 0; i < b.size(); i++)
			listview.getItems().add(b.get(i).getAssId());
		listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

	}

	public void editAss() throws IOException {
		if (listview.getSelectionModel().getSelectedItem() != null) {
			assToChoose = listview.getSelectionModel().getSelectedItem();
			connectionmain.showTeacherEditAssGUI();
		}
	}

	@SuppressWarnings("unchecked")
	public void setComboBoxTeacherCourse(String id) {
		ArrayList<String> a1 = new ArrayList<String>();
		ArrayList<String> a2 = new ArrayList<String>();
		Course course = new Course();
		MyThread C = new MyThread(RequestType.setComboBoxTeacherCourse, IndexList.setComboBoxTeacherCourse, id);
		C.start();
		try {
			C.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		a1 = (ArrayList<String>) MsgFromServer.getDataListByIndex(IndexList.setComboBoxTeacherCourse);

		for (int i = 0; i < a1.size(); i++) {
			MyThread a = new MyThread(RequestType.createCourseEntity, IndexList.createCourseEntity, a1.get(i));
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			course = (Course) MsgFromServer.getDataListByIndex(IndexList.createCourseEntity);
			a2.add(course.getName());
		}
		ObservableList<String> list = FXCollections.observableArrayList(a2);
		STC.setItems(list);
	}

	@FXML
	public void clsTeacherMain() {
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
		return "TchMainGUIController [STC=" + STC + ", Post_Ass=" + Post_Ass + ", Check_Ass=" + Check_Ass + ", CourseL="
				+ CourseL + ", Back=" + Back + ", LogOut=" + LogOut + ", Hello=" + Hello + ", selectAss=" + selectAss
				+ ", WeekTH=" + WeekTH + ", selectTC=" + selectTC + ", General=" + General + ", Ass=" + Ass
				+ ", TchName=" + TchName + ", WeekHours=" + WeekHours + ", Pub_Ass=" + PubAss + ", Logo=" + Logo + "]";
	}

}
