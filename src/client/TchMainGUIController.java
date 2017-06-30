package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Assigenment;
import entity.Course;
import entity.Class;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;
/**
 * This class is the controller for the Teacher main screen GUI.
 */
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

	
	/**
	 * initialize-initialize the teacher name and weekly teach hours.
	 */
	public void initialize(URL location, ResourceBundle resources) {

		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		TchName.setText(s.getName());
		String id = s.getId();

		String maxHours = Integer.toString(getWeeklyHours(id));
		WeekHours.setText(maxHours);
		int role;
		role = ((User) MsgFromServer.getDataListByIndex(IndexList.LOGIN)).getRole();
		if (role == 3){
			Back.setDisable(true);
		}

		setComboBoxTeacherCourse(id);

	}

	/**
	 * This method send the teacher to the download & upload assigenment window  
	 */
	public void goToDownloadAssStudent() throws IOException {
		if (listview.getSelectionModel().getSelectedItem() != null) {
			assToChoose = listview.getSelectionModel().getSelectedItem();
			for (int i = 0; i < b.size(); i++) {
				if (b.get(i).getAssname().equals(assToChoose)) {
					assId = b.get(i).getAssId();
				}
			}
			connectionmain.showTeacherDownloadAssStudent();
		}
	}
	/**
	 * This method send the teacher to post new assigenment
	 */
	public void addNewAssingment() throws IOException {
		connectionmain.showTeacherGUIAssWindow();
	}

	/**
	 * method that calculates the number of weekly work hours
	 */
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

	/**
	 * method that show all the assigenments the teacher post divided by courses
	 */
	@SuppressWarnings("unchecked")
	public void setTableViewTeacherCourseAssigenment() {	
		listviewClass.getItems().clear();
		Assigenment ass = new Assigenment();
		String courseName = STC.getSelectionModel().getSelectedItem();
		ass.setCoursename(courseName);
		ass.setTeacherid(s.getId()); 
		
		MyThread C = new MyThread(RequestType.createCourseEntityByName, IndexList.createCourseEntityByName,courseName);
		C.start();
		try {
			C.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		Course	course = (Course) MsgFromServer.getDataListByIndex(IndexList.createCourseEntityByName);
		ass.setCourseid(course.getCourseId());
		MyThread B = new MyThread(RequestType.createClassEntityByCourseId, IndexList.createClassEntityByCourseId,ass);
		B.start();
		try {
			B.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		ArrayList<Class> class1 = (ArrayList<Class>) MsgFromServer.getDataListByIndex(IndexList.createClassEntityByCourseId);
		
		for (int i = 0; i < class1.size(); i++) {
			listviewClass.getItems().add(class1.get(i).getName());
		}
		listviewClass.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		
		
		listview.getItems().clear();
		MyThread Q = new MyThread(RequestType.setTableViewTeacherCourseAssigenment,
				IndexList.setTableViewTeacherCourseAssigenment, ass);
		Q.start();
		try {
			Q.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		b = (ArrayList<Assigenment>) MsgFromServer.getDataListByIndex(IndexList.setTableViewTeacherCourseAssigenment);
		for (int i = 0; i < b.size(); i++)
			listview.getItems().add(b.get(i).getAssname());
		listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

	
	/**
	 * This method send the teacher to edit old assigenment
	 */
	public void editAss() throws IOException {
		if (listview.getSelectionModel().getSelectedItem() != null) {
			assToChoose = listview.getSelectionModel().getSelectedItem();
			for (int i = 0; i < b.size(); i++) {
				if (b.get(i).getAssname().equals(assToChoose)) {
					assId = b.get(i).getAssId();
				}
			}
			connectionmain.showTeacherEditAssGUI();
		}
	}

	/**
	 * This method show the teacher courses
	 * @param id -will
	 *            hold the teacher id for set the ComboBox
	 */
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

	/**
	 * Logout from the server  
	 */
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

	/**
	 * Back to the last window  
	 */
	@FXML
	private void backButton(ActionEvent event) throws Exception {
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
