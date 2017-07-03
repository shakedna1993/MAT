package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Course;
import entity.Student;
import entity.Teacher;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class InfoGUIController implements Initializable {

	private ArrayList<entity.Class> classList;
	private ArrayList<User> userList;
	private static User selectedUser;
	private ObservableList<Course> data;

	@FXML
	private ComboBox<String> classidCombo;
	@FXML
	private ComboBox<String> classnameCombo;
	@FXML
	private ComboBox<String> userCombo;


	@FXML
	private Button back_btn;
	@FXML
	private Button showEva_btn;
	@FXML
	private Button showGrade_btn;
	@FXML
	private Button logOut_btn;
	
	
	@FXML
	private TableView<Student> studentListTable;
	@FXML
	private TableView<User> teacherListTable;
	@FXML
	private TableView<Course> CourseLIST = new TableView<>();

	@FXML
	private TextField searchField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillComboBox();
		populateUserCombo();
		populateCourseTable();
		populateTeacherTable();
	}

	/**
	 * Happens when a class is selected in the "ID" combo box. After selecting a
	 * class by ID it displays the class name in the "Name" combo box and
	 * displays the student list in the student tableview.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void selectByID() throws Exception {

		entity.Class c = new entity.Class();
		for (int i = 0; i < classList.size(); i++) {
			if (classidCombo.getValue().equals(classList.get(i).getClassId())) {
				c = classList.get(i);

				classnameCombo.setValue(c.getName());
				ArrayList<Student> studentList = getStudentList(c.getClassId());
				if (studentList == null)
					return;
				studentListTable.getColumns().clear();

				TableColumn<Student, String> c1 = new TableColumn<>("ID");
				c1.setCellValueFactory(new PropertyValueFactory<>("Id"));
				TableColumn<Student, String> c2 = new TableColumn<>("Name");
				c2.setCellValueFactory(new PropertyValueFactory<>("name"));
				TableColumn<Student, String> c3 = new TableColumn<>("Parent");
				c3.setCellValueFactory(new PropertyValueFactory<>("ParentId"));
				TableColumn<Student, String> c4 = new TableColumn<>("avg");
				c4.setCellValueFactory(new PropertyValueFactory<>("avg"));

				studentListTable.getColumns().addAll(c1, c2, c3, c4);
				studentListTable.setItems(FXCollections.observableArrayList(studentList));
				break;
			}
		}

	}

	/**
	 * Happens when a class is selected in the "Name" combo box. After selecting
	 * a class by Name it displays the class ID in the "ID" combo box and
	 * displays the student list in the student tableview.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void selectByName() throws Exception {

		entity.Class c = new entity.Class();
		for (int i = 0; i < classList.size(); i++) {
			if (classnameCombo.getValue().equals(classList.get(i).getName())) {
				c = classList.get(i);

				classidCombo.setValue(c.getClassId());
				ArrayList<Student> studentList = getStudentList(c.getClassId());
				if (studentList == null)
					return;
				studentListTable.getColumns().clear();

				TableColumn<Student, String> c1 = new TableColumn<>("ID");
				c1.setCellValueFactory(new PropertyValueFactory<>("Id"));
				TableColumn<Student, String> c2 = new TableColumn<>("Name");
				c2.setCellValueFactory(new PropertyValueFactory<>("name"));
				TableColumn<Student, String> c3 = new TableColumn<>("Parent");
				c3.setCellValueFactory(new PropertyValueFactory<>("ParentId"));
				TableColumn<Student, String> c4 = new TableColumn<>("avg");
				c4.setCellValueFactory(new PropertyValueFactory<>("avg"));

				studentListTable.getColumns().addAll(c1, c2, c3, c4);
				studentListTable.setItems(FXCollections.observableArrayList(studentList));
				break;
			}
		}

	}

	/**
	 * Gets all the students in the class with the ID "classId".
	 * 
	 * @param classId
	 *            - the target class ID
	 * @return the student list for the class classId, if classId is null, it
	 *         returns all the class-less students.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Student> getStudentList(String classId) {
		if (classId == null) {
			MyThread a = new MyThread(RequestType.getStudentInNoClass, IndexList.getStudentInNoClass, classId);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			return (ArrayList<Student>) (MsgFromServer.getDataListByIndex(IndexList.getStudentInNoClass));
		} else {
			MyThread a = new MyThread(RequestType.getStudentInClass, IndexList.getStudentInClass, classId);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			return (ArrayList<Student>) (MsgFromServer.getDataListByIndex(IndexList.getStudentInClass));
		}
	}

	/**
	 * Fills the class combo-boxs for selection with the active classes that are
	 * in the DB.
	 */
	@SuppressWarnings("unchecked")
	private void fillComboBox() {
		MyThread a = new MyThread(RequestType.getActiveClasses, IndexList.getActiveClasses, null);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		classList = (ArrayList<entity.Class>) (MsgFromServer.getDataListByIndex(IndexList.getActiveClasses));
		if (classList == null)
			return;
		ArrayList<String> idList = new ArrayList<String>();
		ArrayList<String> nameList = new ArrayList<String>();
		for (int i = 0; i < classList.size(); i++) {
			idList.add(classList.get(i).getClassId());
			nameList.add(classList.get(i).getName());
		}

		classidCombo.setItems(FXCollections.observableArrayList(idList));
		classnameCombo.setItems(FXCollections.observableArrayList(nameList));
	}

	/**
	 * This method is for the "Search" option. If pressed, the entered User ID
	 * will be searched in the DB and if exists will be populated in the user
	 * combo box. It also checks for matching ID and role (i.e. if the ID
	 * searched is a teacher's ID and the request is to register a student, it
	 * won't place the User in the combo box.)
	 * 
	 * @throws Exception
	 */
	@FXML
	private void searchButton() throws Exception {
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
			if (selectedUser == null || selectedUser.getRole() != 4) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Student doesn't exist!");
				alert.setHeaderText(null);
				alert.setContentText("Student doesn't exist!");
				alert.show();
				return;
			}
			userCombo.setValue(selectedUser.getId() + " - " + selectedUser.getName());
			selectUser();
		}
	}
	@FXML
	private void selectUser() throws Exception {
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
	}

	/**
	 * This method populates the user combo box depending on the typeIndex
	 * (selected Request Type) field. If it's a register/remove students, it
	 * populates the user comboBox with students. If it's a teacher Appointment,
	 * it populates the user ComboBox with teachers.
	 */
	@SuppressWarnings({ "unchecked" })
	private void populateUserCombo() {
		userList = new ArrayList<User>();
		int role = 4;
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

	@SuppressWarnings("unchecked")
	public void populateCourseTable() {
		Course crs = new Course("-1");
		try {
			MyThread a = new MyThread(RequestType.CoursesList, IndexList.CoursesList, crs);
			a.start();
			a.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		data = FXCollections.observableArrayList();
		ArrayList<Course> b = (ArrayList<Course>) MsgFromServer.getDataListByIndex(IndexList.CoursesList);
		for (int i = 0; i < b.size(); i++) {
			data.add(b.get(i));
		}

		TableColumn<Course, String> c1 = new TableColumn<>("Course ID");
		c1.setCellValueFactory(new PropertyValueFactory<>("courseId"));
		TableColumn<Course, String> c2 = new TableColumn<>("Course Name");
		c2.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Course, String> c3 = new TableColumn<>("Unit");
		c3.setCellValueFactory(new PropertyValueFactory<>("unit"));
		TableColumn<Course, String> c4 = new TableColumn<>("Weekly Hours");
		c4.setCellValueFactory(new PropertyValueFactory<>("Hours"));

		CourseLIST.getColumns().addAll(c1, c2, c3, c4);
		CourseLIST.setItems(data);
		c1.setStyle("-fx-alignment: CENTER;");
		c2.setStyle("-fx-alignment: CENTER;");
		c3.setStyle("-fx-alignment: CENTER;");
		c4.setStyle("-fx-alignment: CENTER;");
	}

	public void stuGradeButton() throws IOException {
		if(selectedUser == null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No student selected");
			alert.setHeaderText(null);
			alert.setContentText("Please select a student first!");
			alert.show();
			return;
		}
		connectionmain.ShowGradeList();
	}

	public void stuEvaluButton() throws IOException {
		if(selectedUser == null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No student selected");
			alert.setHeaderText(null);
			alert.setContentText("Please select a student first!");
			alert.show();
			return;
		}
		connectionmain.ShowEvaluationList();
	}

	@SuppressWarnings("unchecked")
	private void populateTeacherTable() {
		MyThread a = new MyThread(RequestType.getUsersByRole, IndexList.getUsersByRole, 3);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		ArrayList<User> teacherList = (ArrayList<User>) (MsgFromServer.getDataListByIndex(IndexList.getUsersByRole));
		ArrayList<String> userStrList = new ArrayList<String>();
		for (int i = 0; i < teacherList.size(); i++) {
			userStrList.add(teacherList.get(i).getId() + " - " + teacherList.get(i).getName());
		}
		TableColumn<User, String> c1 = new TableColumn<>("ID");
		c1.setCellValueFactory(new PropertyValueFactory<>("Id"));
		TableColumn<User, String> c2 = new TableColumn<>("Name");
		c2.setCellValueFactory(new PropertyValueFactory<>("name"));

		teacherListTable.getColumns().addAll(c1, c2);
		teacherListTable.setItems(FXCollections.observableArrayList(teacherList));
	}

	@FXML
	private void backButton() throws Exception {
		connectionmain.showManagerMain();
	}

	@FXML
	public void clsSysManMain() {
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

	public static User getSelectedUser() {
		User u = selectedUser;
		selectedUser = null;
		return u;
	}
}
