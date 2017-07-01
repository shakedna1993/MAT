package client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Course;
import entity.Requests;
import entity.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;
/**
 * 
 * NewRequestGUI controller class for the "New Request" function in the Secretary window.
 *
 */
public class NewRequestGUIController implements Initializable {

	/**
	 * Saves all different types
	 */
	private ArrayList<String> typeList;
	/**
	 * Saves all the users depending on the type of request.
	 */
	private ArrayList<User> userList;
	/**
	 * Saves the selected course for the request
	 */
	private Course selectedCourse;
	/**
	 * Saves the selected user for the request.
	 */
	private User selectedUser;
	/**
	 * Saves the selected class for the request.
	 */
	private entity.Class selectedClass = null;
	/**
	 * Saves the request type selected:
	 * 0 - no type selected.
	 * 1 - Register Student to Course.
	 * 2 - Remove Student from Course
	 * 3 - Change Teacher Appointment
	 */
	private int typeIndex = 0;
	/**
	 * Alert for display when an error has occurred.
	 */
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
	private ComboBox<String> typeCombo;
	@FXML
	private ComboBox<String> userCombo;
	@FXML
	private ComboBox<String> courseCombo;
	@FXML
	private ComboBox<String> classCombo;

	@FXML
	private TextArea descField;

	@FXML
	private TextField searchField;

	@FXML
	private TableView<?> userListTable;

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
/**
 * This method checks what type of request was selected and stores it in the typeIndex field.
 * @throws Exception
 */
	@FXML
	private void typeCombo() throws Exception {
		userCombo.setItems(null);
		courseCombo.setItems(null);
		for (int i = 0; i < typeList.size(); i++) {
			if (typeList.get(i).equals(typeCombo.getValue())) {
				typeIndex = i + 1;
			}
		} // 1 = Register Student, 2 = Remove Student from course, 3 = Teacher
			// Appointment;
		if (typeIndex == 0)
			return;
		else
			populateUserCombo();
	}
/**
 * This method populates the user combo box depending on the typeIndex (selected Request Type) field.
 * If it's a register/remove students, it populates the user comboBox with students.
 * If it's a teacher Appointment, it populates the user ComboBox with teachers.
 */
	@SuppressWarnings({ "unchecked"})
	private void populateUserCombo() {
		userList = new ArrayList<User>();
		int role;
		if (typeIndex == 3) {
			userLabel.setText("Change Teacher:");
			searchLabel.setText("Search Teacher By ID:");
			courseLabel.setText("In Course:");
			classLabel.setVisible(true);
			classCombo.setVisible(true);
			role = 3;
		} else {
			if (typeIndex == 1) {
				userLabel.setText("Register Student:");
				courseLabel.setText("To Course:");
			} else {
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
		for (int i = 0; i < userList.size(); i++) {
			userStrList.add(userList.get(i).getId() + " - " + userList.get(i).getName());
		}
		userCombo.setItems(FXCollections.observableArrayList(userStrList));
	}
/**
 * This method is for the "Search" option.
 * If pressed, the entered User ID will be searched in the DB and if exists will be populated in the user combo box.
 * It also checks for matching ID and role (i.e. if the ID searched is a teacher's ID and the request is to register a student, it won't place the User in the combo box.)
 * @throws Exception
 */
	@FXML
	private void searchButton() throws Exception {
		if (typeIndex == 0)
			return;
		String searchValue = searchField.getText().replaceAll(" ", "");
		MyThread a = new MyThread(RequestType.UserIdExists, IndexList.UserIdExists, searchValue);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		boolean flag = (boolean) (MsgFromServer.getDataListByIndex(IndexList.UserIdExists));
		if (flag) {
			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getId().equals(searchValue)) {
					selectedUser = userList.get(i);
				}
			}
			if (selectedUser == null)
				return;
			if (selectedUser.getRole() > 4 || selectedUser.getRole() < 3)
				return;
			if (selectedUser.getRole() == 4 && typeIndex == 3)
				return;
			if (selectedUser.getRole() == 5 && typeIndex != 3)
				return;
			userCombo.setValue(selectedUser.getId() + " - " + selectedUser.getName());
			userCombo();
		}
	}
/**
 * This method happens when the submit button is pressed.
 * It verifies fields and then sends a new request to the DB.
 * If it was successful it will exit the 'new request' window and go back to the secretary main menu.
 * @throws Exception
 */
	@FXML
	private void submitButton() throws Exception {
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
		if (selectedClass == null)
			r.setClassId(null);
		else
			r.setClassId(selectedClass.getClassId());
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
/**
 * This method happens when a user is selected with the user combo box.
 * It fills the course combo box with the appropriate courses (register to/remove from/teacher change) depending on the type of the request.
 * @throws Exception
 */
	@FXML
	private void userCombo() throws Exception {
		String str = (String) userCombo.getValue();
		if (str == null)
			return;
		User u = new User();
		u.setId(str.substring(0, str.indexOf('-') - 1));
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getId().equals(u.getId())) {
				selectedUser = userList.get(i);
			}
		}
		MyThread a = new MyThread(RequestType.getUserCoursesInCurrSemester, IndexList.getUserCoursesInCurrSemester,
				selectedUser);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		@SuppressWarnings("unchecked")
		ArrayList<Course> returnedCourseList = (ArrayList<Course>) (MsgFromServer
				.getDataListByIndex(IndexList.getUserCoursesInCurrSemester));
		if (typeIndex == 1) {
			a = new MyThread(RequestType.getAllCoursesInCurrSemester, IndexList.getAllCoursesInCurrSemester,
					selectedUser);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			@SuppressWarnings("unchecked")
			ArrayList<Course> allCourseList = (ArrayList<Course>) (MsgFromServer
					.getDataListByIndex(IndexList.getAllCoursesInCurrSemester));
			for (int i = 0; i < returnedCourseList.size(); i++) {
				for (int j = 0; j < allCourseList.size(); j++) {
					if (returnedCourseList.get(i).getCourseId().equals(allCourseList.get(j).getCourseId())) {
						allCourseList.remove(j);
						break;
					}
				}
			}
			returnedCourseList = allCourseList;
		}

		ArrayList<String> courseStrList = new ArrayList<String>();
		for (int i = 0; i < returnedCourseList.size(); i++) {
			courseStrList.add(returnedCourseList.get(i).getCourseId() + " - " + returnedCourseList.get(i).getName());
		}
		courseCombo.setItems(FXCollections.observableArrayList(courseStrList));
	}
/**
 * This method happens when a course is selected in the course Combo box.
 * It stores the course in the local variables and if the request type is a teacher change, it populates the class list for that teacher in that course.
 * @throws Exception
 */
	@FXML
	private void courseCombo() throws Exception {
		String str = (String) courseCombo.getValue();
		selectedCourse = new Course();
		selectedCourse.setCourseId(str.substring(0, str.indexOf('-') - 1));
		selectedCourse.setName(str.substring(str.indexOf('-') + 2, str.length()));
		if (typeIndex == 3) {
			// par = {selectedUser, selectedCourse};
			Object par[] = { selectedUser, selectedCourse };
			MyThread a = new MyThread(RequestType.getClassListForTeacherInCourse,
					IndexList.getClassListForTeacherInCourse, par);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			@SuppressWarnings("unchecked")
			ArrayList<entity.Class> classList = (ArrayList<entity.Class>) (MsgFromServer
					.getDataListByIndex(IndexList.getClassListForTeacherInCourse));
			ArrayList<String> classStrList = new ArrayList<String>();
			for (int i = 0; i < classList.size(); i++) {
				classStrList.add(classList.get(i).getClassId() + " - " + classList.get(i).getName());
			}
			classCombo.setItems(FXCollections.observableArrayList(classStrList));
		}
	}
/**
 * This method happens when selecting a class for the teacher appointment request type.
 * It saves the selected class to the local variables.
 * @throws Exception
 */
	@FXML
	private void classCombo() throws Exception {
		String str = (String) classCombo.getValue();
		selectedClass = new entity.Class();
		selectedClass.setClassId(str.substring(0, str.indexOf('-') - 1));
		selectedClass.setName(str.substring(str.indexOf('-') + 2, str.length()));
	}
	/**
	 * Goes back to the main Secretary menu.
	 * @param event
	 * @throws Exception
	 */
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
