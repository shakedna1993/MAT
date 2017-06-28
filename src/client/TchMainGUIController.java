package client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Assigenment;
import entity.Course;
import entity.Class;
import entity.Teacher;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
	Button selectClassBTN;
	@FXML
	ComboBox<String> classCMB;
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
	ListView<String> listviewClass;
	@FXML
	ImageView Logo;


	@FXML
	Button downAss;
	static User s;
	
	ArrayList<Assigenment> b;
	
	static String assToChoose;
	static int assId;
	public void initialize(URL location, ResourceBundle resources) {
	
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		TchName.setText(s.getName());
		String id = s.getId();

		String maxHours = Integer.toString(getWeeklyHours(id));
		WeekHours.setText(maxHours);
		int role;
		role=((User) MsgFromServer.getDataListByIndex(IndexList.LOGIN)).getRole();
		if(role==3)
			Back.setDisable(true);

		setComboBoxTeacherCourse(id);

	}
	
	public void goToDownloadAssStudent() throws IOException {
		if (listview.getSelectionModel().getSelectedItem() != null) {
			assToChoose = listview.getSelectionModel().getSelectedItem();
			for(int i = 0; i<b.size();i++){
				if(b.get(i).getAssname().equals(assToChoose)){
					assId = b.get(i).getAssId();
				}
			}
			connectionmain.showTeacherDownloadAssStudent();
		}
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
		
		
		listviewClass.getItems().clear();
		ArrayList<Assigenment> ltd = new ArrayList<Assigenment>();
		ArrayList<String> a2 = new ArrayList<String>();
		Assigenment a = new Assigenment();
		String courseName = STC.getValue();
		a.setCoursename(courseName);
		
		MyThread C = new MyThread(RequestType.createCourseEntityByName, IndexList.createCourseEntityByName,courseName);
		C.start();
		try {
			C.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
	Course	course = (Course) MsgFromServer.getDataListByIndex(IndexList.createCourseEntityByName);
		
		ltd = (ArrayList<Assigenment>) MsgFromServer.getDataListByIndex(IndexList.setComboBoxTeacherCourse);

		
		MyThread B = new MyThread(RequestType.createClassEntity, IndexList.createClassEntity,null);
		B.start();
		try {
			B.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		ArrayList<Class> class1 = (ArrayList<Class>) MsgFromServer.getDataListByIndex(IndexList.createClassEntity);
		
		for (int i = 0; i < ltd.size(); i++) {
		if(	ltd.get(i).getCourseid().equals(course.getCourseId())){
			for(int j = 0;j<class1.size();j++){
				if(ltd.get(i).getClassid().equals(class1.get(j).getClassId())){
					a2.add(class1.get(j).getName());
				}
			}
		}
		}
		ObservableList<String> list = FXCollections.observableArrayList(a2);
		listviewClass.setItems(list);
		listview.getItems().clear();
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		String teacherid = s.getId();
		Object st = STC.getValue();
		String CourseName = st.toString();
		Assigenment ass = new Assigenment();
		ass.setCoursename(CourseName);
		ass.setTeacherid(teacherid);
		MyThread Q = new MyThread(RequestType.setTableViewTeacherCourseAssigenment,
				IndexList.setTableViewTeacherCourseAssigenment, ass);
		Q.start();
		try {
			Q.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	 b = (ArrayList<Assigenment>) MsgFromServer
				.getDataListByIndex(IndexList.setTableViewTeacherCourseAssigenment);
		for (int i = 0; i < b.size(); i++)
			listview.getItems().add(b.get(i).getAssname());
		listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

	}

	public void editAss() throws IOException {
		if (listview.getSelectionModel().getSelectedItem() != null) {
			assToChoose = listview.getSelectionModel().getSelectedItem();
			for(int i = 0; i<b.size();i++){
				if(b.get(i).getAssname().equals(assToChoose)){
					assId = b.get(i).getAssId();
				}
			}
			connectionmain.showTeacherEditAssGUI();
		}
	}

	@SuppressWarnings("unchecked")
	public void setComboBoxTeacherCourse(String id) {
		ArrayList<Assigenment> a1 = new ArrayList<Assigenment>();
		ArrayList<String> a2 = new ArrayList<String>();
		Course course = new Course();
		MyThread C = new MyThread(RequestType.setComboBoxTeacherCourse, IndexList.setComboBoxTeacherCourse, id);
		C.start();
		try {
			C.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		a1 = (ArrayList<Assigenment>) MsgFromServer.getDataListByIndex(IndexList.setComboBoxTeacherCourse);

		for (int i = 0; i < a1.size(); i++) {
			MyThread a = new MyThread(RequestType.createCourseEntity, IndexList.createCourseEntity, a1.get(i).getCourseid());
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
	
	@SuppressWarnings("unchecked")
	public void setComboBoxTeacherClass() {
		
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
	
	@FXML
	private void backButton(ActionEvent event) throws Exception{
		connectionmain.showTch_ManMain();
	}
	

	@Override
	public String toString() {
		return "TchMainGUIController [STC=" + STC + ", Post_Ass=" + Post_Ass + ", Check_Ass=" + Check_Ass + ", CourseL="
				+ CourseL + ", Back=" + Back + ", LogOut=" + LogOut + ", Hello=" + Hello + ", selectAss=" + selectAss
				+ ", WeekTH=" + WeekTH + ", selectTC=" + selectTC + ", General=" + General + ", Ass=" + Ass
				+ ", TchName=" + TchName + ", WeekHours=" + WeekHours + ", Pub_Ass=" + PubAss + ", Logo=" + Logo + "]";
	}

}
