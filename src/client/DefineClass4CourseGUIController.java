package client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Class;
import entity.Course;
import entity.Student;
import entity.Teacher;
import entity.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;

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
	private ComboBox idCombo;
	@FXML
	private ComboBox nameCombo;
	@FXML
	private ComboBox courseCombo;
	@FXML
	private ComboBox teacherCombo;
	
	@FXML
	private Button back_button;
	@FXML
	private Button define_button;
	@FXML
	private Button remove_button;

	@FXML
	private TableView courseTable;
	@FXML
	private TableView classCoursesTable;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillComboBox();
		
	}
	@FXML
	private void defineButton(ActionEvent e) throws Exception{
		String semid = getCurrentSemesterID();
		String str = (String) courseCombo.getValue();
		String courseid = str.substring(0,str.indexOf('-')-1);
		String coursename = str.substring(str.indexOf('-')+2, str.length());
		String classid = (String)idCombo.getValue();
		str = (String) teacherCombo.getValue();
		if (str == null) return;
		String teacherid = str.substring(0,str.indexOf('-')-1);
		String par[] = {courseid,classid,teacherid,semid};
		MyThread a = new MyThread(RequestType.AddClassToCourse, IndexList.AddClassToCourse, par);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if ((boolean)MsgFromServer.getDataListByIndex(IndexList.AddClassToCourse)) {
			selectByID(null);
		}
		par[1] = semid;
		par[2] = courseid;
		par[3] = coursename;
		for (int i=0; i<classStudentList.size(); i++){
			par[0] = classStudentList.get(i).getId();
			a = new MyThread(RequestType.AddStudentToCourse, IndexList.AddStudentToCourse, par);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if ((boolean)MsgFromServer.getDataListByIndex(IndexList.AddStudentToCourse)) {
				//selectByID(null);
			}
		}
		
	}
	@FXML
	private void removeButton(ActionEvent e) throws Exception{
		String courseid = ((CourseClassView)classCoursesTable.getSelectionModel().getSelectedItem()).getCourseid();
		
		String semid = getCurrentSemesterID();
		if (semid == null) return;
		
		String classid = (String)idCombo.getValue();
		String par[] = {courseid,classid,semid};
		MyThread a = new MyThread(RequestType.RemoveClassFromCourse, IndexList.RemoveClassFromCourse, par);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if ((boolean)MsgFromServer.getDataListByIndex(IndexList.RemoveClassFromCourse)) {
			selectByID(null);
		}
		par[1] = courseid;
		for (int i=0; i<classStudentList.size(); i++){
			par[0] = classStudentList.get(i).getId();
			a = new MyThread(RequestType.RemoveStudentFromCourse, IndexList.RemoveStudentFromCourse, par);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if ((boolean)MsgFromServer.getDataListByIndex(IndexList.RemoveStudentFromCourse)) {
				//selectByID(null);
			}
		}
		
	}
	private String getCurrentSemesterID() {
		MyThread a = new MyThread(RequestType.getCurrentSemesterID, IndexList.getCurrentSemesterID, null);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return (String)(MsgFromServer.getDataListByIndex(IndexList.getCurrentSemesterID)) ;
	}
	private void getClassCourses(Class c) {
		MyThread a = new MyThread(RequestType.getClassCourses, IndexList.getClassCourses, c);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		Object o[] = (Object[])(MsgFromServer.getDataListByIndex(IndexList.getClassCourses));
		if (o == null) return;
		ArrayList<Course> courseList = (ArrayList<Course>) o[0];
		ArrayList<Teacher> teacherList = (ArrayList<Teacher>) o[1];
		ArrayList<CourseClassView> lst = new ArrayList<CourseClassView>();
		for (int i=0; i<courseList.size(); i++){
			CourseClassView ccv = new CourseClassView(courseList.get(i).getCourseId(), courseList.get(i).getName(), ""+courseList.get(i).getHours(), teacherList.get(i).getId(), teacherList.get(i).getName());
			lst.add(ccv);
		}
		if (courseList == null || teacherList == null) return;
		
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

	private void fillComboBox() {
		MyThread a = new MyThread(RequestType.getAllClasses, IndexList.getAllClasses, null);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		classList = (ArrayList<entity.Class>) (MsgFromServer.getDataListByIndex(IndexList.getAllClasses));
		if (classList == null) return;
		ArrayList<String> idList = new ArrayList<String>();
		ArrayList<String> nameList = new ArrayList<String>();
		for (int i = 0; i < classList.size(); i++) {
			idList.add(classList.get(i).getClassId());
			nameList.add(classList.get(i).getName());
		}

		idCombo.setItems(FXCollections.observableArrayList(idList));
		nameCombo.setItems(FXCollections.observableArrayList(nameList));
	}
	@FXML
	private void selectByID(ActionEvent e) throws Exception {

		entity.Class c = new entity.Class();
		for (int i = 0; i<classList.size(); i++){
			if (idCombo.getValue().equals(classList.get(i).getClassId())){
				c = classList.get(i);
				nameCombo.setValue(c.getName());
				classStudentList = getStudentList(c.getClassId());
				getClassCourses(c);
				fillCourseComboBox(c);
				if (classStudentList == null) return;
				break;
			}
		}
		
	}
	private void fillCourseComboBox(Class c) {
		MyThread a = new MyThread(RequestType.getAvailableCoursesForClass, IndexList.getAvailableCoursesForClass, c);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		ArrayList<Course> courseList = (ArrayList<Course>) (MsgFromServer.getDataListByIndex(IndexList.getAvailableCoursesForClass));
		if (courseList == null) return;
		ArrayList<String> nameList = new ArrayList<String>();
		for (int i = 0; i < courseList.size(); i++) {
			nameList.add(courseList.get(i).getCourseId()+" - "+courseList.get(i).getName());
		}

		courseCombo.setItems(FXCollections.observableArrayList(nameList));
	}

	@FXML
	private void selectCourse(ActionEvent e) throws Exception{
		String str = (String) courseCombo.getValue();
		if (str == null) return;
		String courseid = str.substring(0,str.indexOf('-')-1);
		ArrayList<Teacher> teacherList = getTeachersForCourse(courseid);
		ArrayList<String> teacherComboList = new ArrayList<String>();
		for (int i = 0; i < teacherList.size(); i++) {
			teacherComboList.add(teacherList.get(i).getId()+" - "+teacherList.get(i).getName());
		}

		teacherCombo.setItems(FXCollections.observableArrayList(teacherComboList));
	}
	private ArrayList<Teacher> getTeachersForCourse(String courseid) {
		if (courseid == null){
			return null;
		}
		else {
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

	@FXML
	private void selectByName(ActionEvent e) throws Exception {

		entity.Class c = new entity.Class();
		for (int i = 0; i<classList.size(); i++){
			if (nameCombo.getValue().equals(classList.get(i).getName())){
				c = classList.get(i);
				idCombo.setValue(c.getClassId());
				classStudentList = getStudentList(c.getClassId());
				getClassCourses(c);
				if (classStudentList == null) return;
				break;
			}
		}
		
	}
	
	private ArrayList<Student> getStudentList(String classId) {
		if (classId == null){
			MyThread a = new MyThread(RequestType.getStudentInNoClass, IndexList.getStudentInNoClass, classId);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
	
			return (ArrayList<Student>) (MsgFromServer.getDataListByIndex(IndexList.getStudentInNoClass));
		}
		else {
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
