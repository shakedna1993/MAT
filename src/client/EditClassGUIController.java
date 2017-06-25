package client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Assigenment;
import entity.Course;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;

public class EditClassGUIController implements Initializable {

	
	private ArrayList<entity.Class> classList;
	@FXML
	private Label classIDLabel;
	@FXML
	private Label classNameLabel;
	@FXML
	private Label numOfStudentsLabel;
	@FXML
	private Label maxStudentsLabel;
	@FXML
	private Label studentListLabel;
	@FXML
	private Label studentListLabel2;
	
	@FXML
	private ComboBox idCombo;
	@FXML
	private ComboBox nameCombo;
	
	@FXML
	private TextField numField;
	@FXML
	private TextField maxField;
	
	@FXML
	private TableView studentListTable;
	@FXML
	private TableView studentListTable2;
	
	@FXML
	private Button back_button;
	@FXML
	private Button add_button;
	@FXML
	private Button remove_button;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getClasslessStudents();
		fillComboBox();
	}

	private void getClasslessStudents() {
		ArrayList<Student> studentList = getStudentList(null);
		if (studentList == null) return;
		studentListTable2.getColumns().clear();

		TableColumn<Student, String> c1 = new TableColumn<>("ID");
		c1.setCellValueFactory(new PropertyValueFactory<>("Id"));
		TableColumn<Student, String> c2 = new TableColumn<>("Name");
		c2.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Student, String> c3 = new TableColumn<>("Parent");

		studentListTable2.getColumns().addAll(c1, c2, c3);
		studentListTable2.setItems(FXCollections.observableArrayList(studentList));
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
				maxField.setText(""+c.getMAXStudent());
				ArrayList<Student> studentList = getStudentList(c.getClassId());
				if (studentList == null) return;
				numField.setText(""+studentList.size());
				studentListTable.getColumns().clear();

				TableColumn<Student, String> c1 = new TableColumn<>("ID");
				c1.setCellValueFactory(new PropertyValueFactory<>("Id"));
				TableColumn<Student, String> c2 = new TableColumn<>("Name");
				c2.setCellValueFactory(new PropertyValueFactory<>("name"));
				TableColumn<Student, String> c3 = new TableColumn<>("Parent");
				c3.setCellValueFactory(new PropertyValueFactory<>("ParentId"));
				TableColumn<Student, String> c4 = new TableColumn<>("Class");
				c4.setCellValueFactory(new PropertyValueFactory<>("ClassId"));
				TableColumn<Student, String> c5 = new TableColumn<>("avg");
				c5.setCellValueFactory(new PropertyValueFactory<>("avg"));
				

				studentListTable.getColumns().addAll(c1, c2, c3, c4,c5);
				studentListTable.setItems(FXCollections.observableArrayList(studentList));
				
				break;
			}
		}
		
	}
	@FXML
	private void selectByName(ActionEvent e) throws Exception {

		entity.Class c = new entity.Class();
		for (int i = 0; i<classList.size(); i++){
			if (nameCombo.getValue().equals(classList.get(i).getName())){
				c = classList.get(i);

				idCombo.setValue(c.getClassId());
				maxField.setText(""+c.getMAXStudent());
				ArrayList<Student> studentList = getStudentList(c.getClassId());
				if (studentList == null) return;
				numField.setText(""+studentList.size());
				studentListTable.getColumns().clear();

				TableColumn<Student, String> c1 = new TableColumn<>("ID");
				c1.setCellValueFactory(new PropertyValueFactory<>("Id"));
				TableColumn<Student, String> c2 = new TableColumn<>("Name");
				c2.setCellValueFactory(new PropertyValueFactory<>("name"));
				TableColumn<Student, String> c3 = new TableColumn<>("Parent");
				c3.setCellValueFactory(new PropertyValueFactory<>("ParentId"));
				TableColumn<Student, String> c4 = new TableColumn<>("Class");
				c4.setCellValueFactory(new PropertyValueFactory<>("class"));
				TableColumn<Student, String> c5 = new TableColumn<>("avg");
				c5.setCellValueFactory(new PropertyValueFactory<>("avg"));
				

				studentListTable.getColumns().addAll(c1, c2, c3, c4, c5);
				studentListTable.setItems(FXCollections.observableArrayList(studentList));
				
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
	private void addButton(ActionEvent event) throws Exception {
		if (studentListTable2.getSelectionModel().getSelectedItem()==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Null selection");
			alert.setHeaderText(null);
			alert.setContentText("Null selection");

			alert.show();
			return;
		}
		String sid = ""+((Student)studentListTable2.getSelectionModel().getSelectedItem()).getId();
		if (idCombo.getValue().equals("Select Class by ID")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No class selected");
			alert.setHeaderText(null);
			alert.setContentText("Please Select a class first");

			alert.show();
			return;
		}
		String cid = (String) idCombo.getValue();
		String par[] = {sid,cid};
		MyThread a = new MyThread(RequestType.AddStudentToClass, IndexList.AddStudentToClass, par);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if ((boolean)MsgFromServer.getDataListByIndex(IndexList.AddStudentToClass)) {
			selectByID(null);
			getClasslessStudents();
		}
	}
	
	@FXML
	private void removeButton(ActionEvent event) throws Exception {
		String sid = ""+((Student)studentListTable.getSelectionModel().getSelectedItem()).getId();
		String cid = ""+((Student)studentListTable.getSelectionModel().getSelectedItem()).getClassid();
		String par[] = {sid,cid};
		MyThread a = new MyThread(RequestType.RemoveStudentFromClass, IndexList.RemoveStudentFromClass, par);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if ((boolean)MsgFromServer.getDataListByIndex(IndexList.RemoveStudentFromClass)) {
			selectByID(null);
			getClasslessStudents();
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
