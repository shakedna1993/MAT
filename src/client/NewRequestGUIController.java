package client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Assigenment;
import entity.Course;
import entity.Requests;
import entity.Student;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;

public class NewRequestGUIController implements Initializable {

	
	private ArrayList<String> typeList;
	private ArrayList<User> userList;
	private Course selectedCourse;
	private User selectedUser;
	private entity.Class selectedClass = null;
	private int typeIndex=0;
	private Alert alert = new Alert(AlertType.WARNING);
	
	@FXML
	private Label typeLabel;
	@FXML
	private Label userLabel;
	@FXML
	private Label courseLabel;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Label searchLabel;
	@FXML
	private Label classLabel;

	@FXML
	private ComboBox typeCombo;
	@FXML
	private ComboBox userCombo;
	@FXML
	private ComboBox courseCombo;
	@FXML
	private ComboBox classCombo;
	
	@FXML
	private TextArea descField;
	
	@FXML
	private TextField searchField;
	
	@FXML
	private TableView userListTable;

	@FXML
	private Button search_button;
	@FXML
	private Button back_button;
	@FXML
	private Button submit_button;

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		classLabel.setVisible(false);
		classCombo.setVisible(false);
		typeList = new ArrayList<String>();
		typeList.add("Register Student to Course");
		typeList.add("Remove Student from Course");
		typeList.add("Change Teacher Appointment");
		alert.setTitle("Empty Fields");
		alert.setHeaderText(null);
		alert.setContentText("Please fill all fields");

		typeCombo.setItems(FXCollections.observableArrayList(typeList));
	}
	@FXML
	private void typeCombo(ActionEvent event) throws Exception {
		userCombo.setItems(null);
		courseCombo.setItems(null);
		for (int i=0; i<typeList.size(); i++){
			if (typeList.get(i).equals(typeCombo.getValue())){
				typeIndex=i+1;
			}
		}// 1 = Register Student, 2 = Remove Student from course, 3 = Teacher Appointment;
		if (typeIndex == 0) return;
		else populateUserCombo();
	}
	
	private void populateUserCombo() {
		userList = new ArrayList<User>();
		int role;
		if (typeIndex==3){
			userLabel.setText("Change Teacher:");
			searchLabel.setText("Search Teacher By ID:");
			courseLabel.setText("In Course:");
			classLabel.setVisible(true);
			classCombo.setVisible(true);
			role = 3;
		}
		else {
			if (typeIndex == 1) {
				userLabel.setText("Register Student:");
				courseLabel.setText("To Course:");
			}
			else {
				userLabel.setText("Remove Student:");
				courseLabel.setText("From Course:");
			}
			classLabel.setVisible(false);
			classCombo.setVisible(false);
			searchLabel.setText("Search Student By ID:");
			role = 4;
		}
		MyThread a = new MyThread(RequestType.getUsersByRole, IndexList.getUsersByRole, role);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		userList = (ArrayList<User>) (MsgFromServer.getDataListByIndex(IndexList.getUsersByRole));
		ArrayList<String> userStrList = new ArrayList<String>();
		for (int i=0; i<userList.size(); i++){
			userStrList.add(userList.get(i).getId() + " - " + userList.get(i).getName());
		}
		userCombo.setItems(FXCollections.observableArrayList(userStrList));
	}
	@FXML
	private void searchButton(ActionEvent event) throws Exception {
		if (typeIndex==0) return;
		String searchValue = searchField.getText().replaceAll(" ", "");
		MyThread a = new MyThread(RequestType.UserIdExists, IndexList.UserIdExists, searchValue);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		boolean flag = (boolean) (MsgFromServer.getDataListByIndex(IndexList.UserIdExists));
		if (flag){
			for (int i=0; i<userList.size(); i++){
				if (userList.get(i).getId().equals(searchValue)) {
					selectedUser = userList.get(i);
				}
			}
			if (selectedUser == null) return;
			if (selectedUser.getRole() > 4 || selectedUser.getRole()<3) return;
			if (selectedUser.getRole() == 4 && typeIndex == 3) return;
			if (selectedUser.getRole() == 5 && typeIndex != 3) return;
			userCombo.setValue(selectedUser.getId() + " - " + selectedUser.getName());
			userCombo(null);
		}
	}
	@FXML
	private void submitButton(ActionEvent event) throws Exception {
		Requests r = new Requests();
		if (selectedCourse == null) {
			alert.show();
			return;
		}
		r.setCourseId(selectedCourse.getCourseId());
		r.setUserId(selectedUser.getId());
		r.setStatus(0);
		r.setReqType(typeIndex);
		r.setRequestDescription(descField.getText());
		if (selectedClass == null) r.setClassId(null);
		else r.setClassId(selectedClass.getClassId());
		MyThread a = new MyThread(RequestType.AddNewRequest, IndexList.AddNewRequest, r);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		boolean flag = (boolean) (MsgFromServer.getDataListByIndex(IndexList.AddNewRequest));
		if (flag) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Request added successfully!");
			alert.setHeaderText(null);
			alert.setContentText("Request added successfully!");
			alert.show();
			connectionmain.showSecretaryMain();
		}
	}
	@FXML
	private void userCombo(ActionEvent event) throws Exception {
		String str = (String) userCombo.getValue();
		if (str == null) return;
		User u = new User();
		u.setId(str.substring(0,str.indexOf('-')-1));
		for (int i=0; i<userList.size(); i++){
			if (userList.get(i).getId().equals(u.getId())) {
				selectedUser = userList.get(i);
			}
		}
		MyThread a = new MyThread(RequestType.getUserCoursesInCurrSemester, IndexList.getUserCoursesInCurrSemester, selectedUser);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		ArrayList<Course> returnedCourseList = (ArrayList<Course>) (MsgFromServer.getDataListByIndex(IndexList.getUserCoursesInCurrSemester));
		if (typeIndex == 1){
			a = new MyThread(RequestType.getAllCoursesInCurrSemester, IndexList.getAllCoursesInCurrSemester, selectedUser);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			ArrayList<Course> allCourseList = (ArrayList<Course>) (MsgFromServer.getDataListByIndex(IndexList.getAllCoursesInCurrSemester));
			for (int i=0; i<returnedCourseList.size(); i++){
				for (int j=0; j<allCourseList.size(); j++){
					if (returnedCourseList.get(i).getCourseId().equals(allCourseList.get(j).getCourseId())) {
						allCourseList.remove(j);
						break;
					}
				}
			}
			returnedCourseList = allCourseList;
		}
		
		ArrayList<String> courseStrList = new ArrayList<String>();
		for (int i=0; i<returnedCourseList.size(); i++){
			courseStrList.add(returnedCourseList.get(i).getCourseId() + " - " + returnedCourseList.get(i).getName());
		}
		courseCombo.setItems(FXCollections.observableArrayList(courseStrList));
	}
	@FXML
	private void courseCombo(ActionEvent event) throws Exception {
		String str = (String) courseCombo.getValue();
		selectedCourse = new Course();
		selectedCourse.setCourseId(str.substring(0,str.indexOf('-')-1));
		selectedCourse.setName(str.substring(str.indexOf('-')+2,str.length()));
		if (typeIndex == 3){
			// par = {selectedUser, selectedCourse};
			Object par[] = {selectedUser, selectedCourse};
			MyThread a = new MyThread(RequestType.getClassListForTeacherInCourse, IndexList.getClassListForTeacherInCourse, par);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			ArrayList<entity.Class> classList = (ArrayList<entity.Class>) (MsgFromServer.getDataListByIndex(IndexList.getClassListForTeacherInCourse));
			ArrayList<String> classStrList = new ArrayList<String>();
			for (int i=0; i<classList.size(); i++){
				classStrList.add(classList.get(i).getClassId() + " - " + classList.get(i).getName());
			}
			classCombo.setItems(FXCollections.observableArrayList(classStrList));
		}
	}
	@FXML
	private void classCombo(ActionEvent event) throws Exception {
		String str = (String) classCombo.getValue();
		selectedClass = new entity.Class();
		selectedClass.setClassId(str.substring(0,str.indexOf('-')-1));
		selectedClass.setName(str.substring(str.indexOf('-')+2,str.length()));
	}
	@FXML
	private void backButton(ActionEvent event) throws Exception {
		Stage primaryStage = connectionmain.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/SecretaryMainGUI.fxml"));
		Pane root = loader.load();
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("M.A.T- Secretary Connection");
		primaryStage.show();
	}
}
