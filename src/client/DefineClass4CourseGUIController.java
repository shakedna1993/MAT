package client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Class;
import entity.Course;
import entity.CourseClassView;
import entity.Student;
import entity.Teacher;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;
/**
 * 
 * DefineClass4CourseGUI controller class for the "Define Class for a Course" function in the Secretary window.
 *
 */
public class DefineClass4CourseGUIController implements Initializable {

	private ArrayList<entity.Class> classList;
	private ArrayList<Student> classStudentList;

	@FXML
	private Label classIDLabel;
	@FXML
	private Label classNameLabel;
	@FXML
	private Label courseLabel;
	@FXML
	private Label TeacherLabel;

	@FXML
	private ComboBox<String> idCombo;
	@FXML
	private ComboBox<String> nameCombo;
	@FXML
	private ComboBox<String> courseCombo;
	@FXML
	private ComboBox<String> teacherCombo;

	@FXML
	private Button back_button;
	@FXML
	private Button define_button;
	@FXML
	private Button remove_button;

	@FXML
	private TableView<?> courseTable;
	@FXML
	private TableView<CourseClassView> classCoursesTable;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillComboBox();

	}
/**
 * Defines a course to a class based on the selections in the GUI window.
 * Before defining it check all parameters for correct input and presents error messages if something is missing.
 * @throws Exception
 */
	@FXML
	private void defineButton() throws Exception {
		String semid = getCurrentSemesterID();
		String str = (String) courseCombo.getValue();
		if (str == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all fields before defining courses!");

			alert.show();
			return;
		}
		String courseid = str.substring(0, str.indexOf('-') - 1);
		String coursename = str.substring(str.indexOf('-') + 2, str.length());
		String classid = (String) idCombo.getValue();
		str = (String) teacherCombo.getValue();
		if (str == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all fields before defining courses!");

			alert.show();
			return;
		}
		String teacherid = str.substring(0, str.indexOf('-') - 1);
		String par[] = { courseid, classid, teacherid, semid };
		MyThread a = new MyThread(RequestType.AddClassToCourse, IndexList.AddClassToCourse, par);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if ((boolean) MsgFromServer.getDataListByIndex(IndexList.AddClassToCourse)) {
			selectByID();
		}
		par[1] = semid;
		par[2] = courseid;
		par[3] = coursename;
		for (int i = 0; i < classStudentList.size(); i++) {
			par[0] = classStudentList.get(i).getId();
			// boolean preFlag = false;
			a = new MyThread(RequestType.CheckStudentPreReq, IndexList.CheckStudentPreReq, par);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			// if
			// ((boolean)MsgFromServer.getDataListByIndex(IndexList.CheckStudentPreReq))
			// {
			boolean preFlag = (boolean) MsgFromServer.getDataListByIndex(IndexList.CheckStudentPreReq);
			// }
			if (preFlag) {
				a = new MyThread(RequestType.AddStudentToCourse, IndexList.AddStudentToCourse, par);
				a.start();
				try {
					a.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if ((boolean) MsgFromServer.getDataListByIndex(IndexList.AddStudentToCourse)) {
					// selectByID(null);
				}
			}
		}

	}
	/**
	 * Removes selected class from selected course. i.e. removes the class-course tuple in the DB as well as all students in the class selected.
	 * Before removing it verifies all selections are legal and present warning if they are not.
	 * @throws Exception
	 */
	@FXML
	private void removeButton() throws Exception {
		CourseClassView ccv = (CourseClassView) classCoursesTable.getSelectionModel().getSelectedItem();
		if (ccv == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No Course Selected");
			alert.setHeaderText(null);
			alert.setContentText("Please a course from the table");

			alert.show();
			return;
		}
		String courseid = ccv.getCourseid();

		String semid = getCurrentSemesterID();
		if (semid == null) {
			return;
		}

		String classid = (String) idCombo.getValue();
		String par[] = { courseid, classid, semid };
		MyThread a = new MyThread(RequestType.RemoveClassFromCourse, IndexList.RemoveClassFromCourse, par);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if ((boolean) MsgFromServer.getDataListByIndex(IndexList.RemoveClassFromCourse)) {
			selectByID();
		}
		par[1] = courseid;
		for (int i = 0; i < classStudentList.size(); i++) {
			par[0] = classStudentList.get(i).getId();
			a = new MyThread(RequestType.RemoveStudentFromCourse, IndexList.RemoveStudentFromCourse, par);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if ((boolean) MsgFromServer.getDataListByIndex(IndexList.RemoveStudentFromCourse)) {
				// selectByID(null);
			}
		}

	}
/**
 * Retrieves the current semester as defined in the DB.
 * @return a String containing the current semester ID.
 */
	private String getCurrentSemesterID() {
		MyThread a = new MyThread(RequestType.getCurrentSemesterID, IndexList.getCurrentSemesterID, null);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return (String) (MsgFromServer.getDataListByIndex(IndexList.getCurrentSemesterID));
	}
/**
 * Gets all courses for a specific class in the current semester from the DB and displaying them in the tableview using a "CourseClassView" class 
 * to display the class-course details in a much clearer format.
 * @param c - the class for which the courses will be shown.
 */
	private void getClassCourses(Class c) {
		MyThread a = new MyThread(RequestType.getClassCourses, IndexList.getClassCourses, c);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		Object o[] = (Object[]) (MsgFromServer.getDataListByIndex(IndexList.getClassCourses));
		if (o == null)
			return;
		@SuppressWarnings("unchecked")
		ArrayList<Course> courseList = (ArrayList<Course>) o[0];
		@SuppressWarnings("unchecked")
		ArrayList<Teacher> teacherList = (ArrayList<Teacher>) o[1];
		ArrayList<CourseClassView> lst = new ArrayList<CourseClassView>();
		for (int i = 0; i < courseList.size(); i++) {
			CourseClassView ccv = new CourseClassView(courseList.get(i).getCourseId(), courseList.get(i).getName(),
					"" + courseList.get(i).getHours(), teacherList.get(i).getId(), teacherList.get(i).getName());
			lst.add(ccv);
		}
		if (courseList == null || teacherList == null)
			return;

		classCoursesTable.getColumns().clear();
		TableColumn<CourseClassView, String> c1 = new TableColumn<>("Course ID");
		c1.setCellValueFactory(new PropertyValueFactory<>("courseid"));
		classCoursesTable.getColumns().add(c1);
		TableColumn<CourseClassView, String> c2 = new TableColumn<>("Course Name");
		c2.setCellValueFactory(new PropertyValueFactory<>("coursename"));
		classCoursesTable.getColumns().add(c2);
		TableColumn<CourseClassView, String> c3 = new TableColumn<>("Hours");
		c3.setCellValueFactory(new PropertyValueFactory<>("hours"));
		classCoursesTable.getColumns().add(c3);
		TableColumn<CourseClassView, String> c4 = new TableColumn<>("Teacher ID");
		c4.setCellValueFactory(new PropertyValueFactory<>("teacherid"));
		classCoursesTable.getColumns().add(c4);
		TableColumn<CourseClassView, String> c5 = new TableColumn<>("Teacher Name");
		c5.setCellValueFactory(new PropertyValueFactory<>("teachername"));
		classCoursesTable.getColumns().add(c5);

		classCoursesTable.setItems(FXCollections.observableArrayList(lst));
	}

	/**
	 * Fills the appropriate combo-boxs for class selection by name of ID.
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

		idCombo.setItems(FXCollections.observableArrayList(idList));
		nameCombo.setItems(FXCollections.observableArrayList(nameList));
	}
/**
 * When selecting a class by it's ID, this method sets the name in the other combo box and fills the course combo boxs for the appropriate courses.
 * @throws Exception
 */
	@FXML
	private void selectByID() throws Exception {

		entity.Class c = new entity.Class();
		for (int i = 0; i < classList.size(); i++) {
			if (idCombo.getValue().equals(classList.get(i).getClassId())) {
				c = classList.get(i);
				nameCombo.setValue(c.getName());
				classStudentList = getStudentList(c.getClassId());
				getClassCourses(c);
				fillCourseComboBox(c);
				if (classStudentList == null)
					return;
				break;
			}
		}

	}
/**
 * Fills the course combo box for the class c.
 * It only fills the courses the class can take in this semester, so any courses already taken by the class wont be shown in the combo box.
 * @param c - the class in question.
 */
	private void fillCourseComboBox(Class c) {
		MyThread a = new MyThread(RequestType.getAvailableCoursesForClass, IndexList.getAvailableCoursesForClass, c);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		@SuppressWarnings("unchecked")
		ArrayList<Course> courseList = (ArrayList<Course>) (MsgFromServer
				.getDataListByIndex(IndexList.getAvailableCoursesForClass));
		if (courseList == null)
			return;
		ArrayList<String> nameList = new ArrayList<String>();
		for (int i = 0; i < courseList.size(); i++) {
			nameList.add(courseList.get(i).getCourseId() + " - " + courseList.get(i).getName());
		}

		courseCombo.setItems(FXCollections.observableArrayList(nameList));
	}
/**
 * When selecting a course from the course combo-box, this method fills the teacher combo list according to the teaching unit they are in.
 * @throws Exception
 */
	@FXML
	private void selectCourse() throws Exception {
		String str = (String) courseCombo.getValue();
		if (str == null)
			return;
		String courseid = str.substring(0, str.indexOf('-') - 1);
		ArrayList<Teacher> teacherList = getTeachersForCourse(courseid);
		ArrayList<String> teacherComboList = new ArrayList<String>();
		for (int i = 0; i < teacherList.size(); i++) {
			teacherComboList.add(teacherList.get(i).getId() + " - " + teacherList.get(i).getName());
		}

		teacherCombo.setItems(FXCollections.observableArrayList(teacherComboList));
	}

	/**
	 * Getting a list of teachers from the DB that can teach the course with the ID 'courseid' given as parameter.
	 * @param courseid - the course ID in question.
	 * @return ArrayList<Teacher> object with all teachers for the given course.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Teacher> getTeachersForCourse(String courseid) {
		if (courseid == null) {
			return null;
		} else {
			MyThread a = new MyThread(RequestType.getTeachersForCourse, IndexList.getTeachersForCourse, courseid);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			return (ArrayList<Teacher>) (MsgFromServer.getDataListByIndex(IndexList.getTeachersForCourse));
		}
	}
	/**
	 * When selecting a class by it's name, this method sets the ID in the other combo box and fills the course combo boxs for the appropriate courses.
	 * @throws Exception
	 */
	@FXML
	private void selectByName() throws Exception {

		entity.Class c = new entity.Class();
		for (int i = 0; i < classList.size(); i++) {
			if (nameCombo.getValue().equals(classList.get(i).getName())) {
				c = classList.get(i);
				idCombo.setValue(c.getClassId());
				classStudentList = getStudentList(c.getClassId());
				getClassCourses(c);
				if (classStudentList == null)
					return;
				break;
			}
		}

	}

	/**
	 * Getting a list of students from the DB that are in the class with the ID 'classId' as given in parameter.
	 * @param classId - the class ID in question.
	 * @return ArrayList<Teacher> object with all students for the given class.
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
