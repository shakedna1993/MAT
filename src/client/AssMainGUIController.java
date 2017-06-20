package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Assigenment;
import entity.Course;
import entity.Student;
import entity.User;
import entity.Class;
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

public class AssMainGUIController implements Initializable {
	
	public static ClientConsole cli;
	public static Stage primaryStage;
	
	@FXML
	ComboBox<String> STC;
	@FXML
	Button Upload_Ass;
	@FXML
	Button selectBtn;
	@FXML
	Button Down_Ass;
	@FXML
	Button CourseL;
	@FXML
	Button Back;
	@FXML
	Button LogOut;

	@FXML
	Label Hello, selectAss;
	@FXML
	Label selectC;
	@FXML
	Label Ass;
	@FXML
	javafx.scene.control.Label StuName;
	@FXML
	ListView<String> PubAss; 
	@FXML
	ListView<String> listview; 
	@FXML
	ImageView Logo;
	
	static String assToChoose;
	public void initialize(URL location, ResourceBundle resources) {
		User s =new User();
		s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		StuName.setText(s.getName());
		String id = s.getId();
	
		setComboBoxStudentCourse(id);	
		
	}
	@SuppressWarnings("unchecked")
	public void setComboBoxStudentCourse(String id){
		ArrayList<String> a1 = new ArrayList<String>();
		ArrayList<String> a2 = new ArrayList<String>();
		Course course = new Course();
		MyThread C = new MyThread(RequestType.setComboBoxStudentCourse, IndexList.setComboBoxStudentCourse, MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		C.start();
		try {
			C.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		a1 = (ArrayList<String>) MsgFromServer.getDataListByIndex(IndexList.setComboBoxStudentCourse);

		for(int i = 0;i<a1.size();i++){
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
	
	@SuppressWarnings("unchecked")
	public void	setTableViewStudentCourseAssigenment( ){
		listview.getItems().clear();
		Student stud =new Student();
		stud=(Student) (MsgFromServer.getDataListByIndex(IndexList.StudentDetails));
		String Classid = stud.getClassid();
		MyThread b1 = new MyThread(RequestType.ClassCourseDetails, IndexList.ClassCourseDetails, Classid);
		b1.start();
		try {
			b1.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		Object st= STC.getValue();
		String  CourseName=st.toString();
		Class cl =new Class();
		cl=(Class) (MsgFromServer.getDataListByIndex(IndexList.ClassCourseDetails));
		String teacherid=cl.getTeachid();
		Assigenment ass = new Assigenment();
		 ass.setCoursename(CourseName);
		 ass.setUserId(teacherid);
		MyThread C = new MyThread(RequestType.setTableViewStudentCourseAssigenment, IndexList.setTableViewStudentCourseAssigenment, ass);
		C.start();
		try {
			C.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		ArrayList<Assigenment> b=(ArrayList<Assigenment>)MsgFromServer.getDataListByIndex(IndexList.setTableViewStudentCourseAssigenment);
		for(int i =0; i<b.size();i++)
			listview.getItems().add(b.get(i).getAssId());
		listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);	
	}
	
	@FXML
	public void clsAssMain() {
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
	
	public void DownloadAss(){
		try {
			connectionmain.ShowStudentUploadAss();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void UploadAss(){
		
	}
	
	@FXML
	private void backButton(ActionEvent event) throws Exception{
		connectionmain.showStudentMain();
	}
	@Override
	public String toString() {
		return "AssMainGUIController [STC=" + STC + ", Upload_Ass=" + Upload_Ass + ", selectBtn=" + selectBtn
				+ ", Down_Ass=" + Down_Ass + ", CourseL=" + CourseL + ", Back=" + Back + ", LogOut=" + LogOut
				+ ", Hello=" + Hello + ", selectAss=" + selectAss + ", selectC=" + selectC + ", Ass=" + Ass
				+ ", StuName=" + StuName + ", PubAss=" + PubAss + ", listview=" + listview + ", Logo=" + Logo + "]";
	}
	
	
}
