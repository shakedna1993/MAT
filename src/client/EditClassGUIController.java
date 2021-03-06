package client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Student;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;
/**
 * 
 * EditClassGUI controller class for the "Edit/Remove Class" function in the Secretary window.
 *
 */
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
	private ComboBox<String> idCombo;
	@FXML
	private ComboBox<String> nameCombo;

	@FXML
	private TextField numField;
	@FXML
	private TextField maxField;

	@FXML
	private TableView<Student> studentListTable;
	@FXML
	private TableView<Student> studentListTable2;

	@FXML
	private Button back_button;
	@FXML
	private Button add_button;
	@FXML
	private Button remove_button;
	@FXML
	private Button deleteClass_button;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getClasslessStudents();
		fillComboBox();
		entity.Class c = DefineNewClassGUIController.getTempClass();
		if (c != null) {
			idCombo.setValue(c.getClassId());
			nameCombo.setValue(c.getName());
			try {
				selectByID();
				DefineNewClassGUIController.setTempClass(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method polls the DB for all students who are not in any class and displays them on the class-less student tableview.
	 */
	@SuppressWarnings("unchecked")
	private void getClasslessStudents() {
		ArrayList<Student> studentList = getStudentList(null);
		if (studentList == null)
			return;
		studentListTable2.getColumns().clear();

		TableColumn<Student, String> c1 = new TableColumn<>("ID");
		c1.setCellValueFactory(new PropertyValueFactory<>("Id"));
		TableColumn<Student, String> c2 = new TableColumn<>("Name");
		c2.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Student, String> c3 = new TableColumn<>("Parent");

		studentListTable2.getColumns().addAll(c1, c2, c3);
		studentListTable2.setItems(FXCollections.observableArrayList(studentList));
	}
/**
 * Deletes the selected class in the combo-boxs, after confirming selection inputs and displaying the appropriate messages if the selections are wrong.
 * Also removes students from the selected class and un-registers them from the courses the class was registered to.
 * 
 */
	@FXML
	private void deleteClass() {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"This will delete the class.\nIt will remove the class from the DB along with the courses the class is registered to, as well as all the courses the students are registered to(of this class).\nAre you sure you want to delete the class?",
				ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			entity.Class c = new entity.Class();
			for (int i = 0; i < classList.size(); i++) {
				if (idCombo.getValue().equals(classList.get(i).getClassId())) {
					c = classList.get(i);
					MyThread a = new MyThread(RequestType.DeleteClass, IndexList.DeleteClass, c);
					a.start();
					try {
						a.join();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					boolean b = (boolean) (MsgFromServer.getDataListByIndex(IndexList.DeleteClass));
					if (b) {
						getClasslessStudents();
						studentListTable.setItems(null);
						numField.setText("");
						maxField.setText("");
					}
				}
			}
		}
	}

	/**
	 * Fills the class combo-boxs for selection with the active classes that are in the DB.
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
	 * Happens when a class is selected in the "ID" combo box. After selecting a class by ID it displays the class name in the "Name" combo box and displays the student list in the student tableview.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void selectByID() throws Exception {

		entity.Class c = new entity.Class();
		for (int i = 0; i < classList.size(); i++) {
			if (idCombo.getValue().equals(classList.get(i).getClassId())) {
				c = classList.get(i);

				nameCombo.setValue(c.getName());
				maxField.setText("" + c.getMAXStudent());
				ArrayList<Student> studentList = getStudentList(c.getClassId());
				if (studentList == null)
					return;
				numField.setText("" + studentList.size());
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
				maxField.setEditable(true);
				break;
			}
		}

	}
	/**
	 * Happens when a class is selected in the "Name" combo box. After selecting a class by Name it displays the class ID in the "ID" combo box and displays the student list in the student tableview.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void selectByName(ActionEvent e) throws Exception {

		entity.Class c = new entity.Class();
		for (int i = 0; i < classList.size(); i++) {
			if (nameCombo.getValue().equals(classList.get(i).getName())) {
				c = classList.get(i);

				idCombo.setValue(c.getClassId());
				maxField.setText("" + c.getMAXStudent());
				ArrayList<Student> studentList = getStudentList(c.getClassId());
				if (studentList == null)
					return;
				numField.setText("" + studentList.size());
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
				maxField.setEditable(true);
				break;
			}
		}

	}
/**
 * Gets all the students in the class with the ID "classId".
 * @param classId - the target class ID
 * @return the student list for the class classId, if classId is null, it returns all the class-less students.
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
 * This method happens when updating the class max capacity field and hitting "Enter".
 * It updates the selected class's max student capacity in the DB.
 * @throws Exception
 */
	@FXML
	private void updateMax() throws Exception {
		int newMax = Integer.parseInt(maxField.getText());
		String cid = (String) idCombo.getValue();
		Object par[] = { cid, newMax };
		MyThread a = new MyThread(RequestType.UpdateMaxStudents, IndexList.UpdateMaxStudents, par);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if ((boolean) MsgFromServer.getDataListByIndex(IndexList.UpdateMaxStudents)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success!");
			alert.setHeaderText(null);
			alert.setContentText("Max student number updated");
			alert.show();
			for (entity.Class c : classList) {
				if (c.getClassId().equals(cid)) {
					c.setMAXStudent(newMax);
				}
			}
		}
	}
/**
 * This method adds the selected class-less student to the selected class.
 * If the selected fields are not correct if displays an appropriate message.
 * If the class is full it doesn't allow more students to be added.
 * @throws Exception
 */
	@FXML
	private void addButton() throws Exception {
		if (studentListTable2.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Null selection");
			alert.setHeaderText(null);
			alert.setContentText("No student selected.");

			alert.show();
			return;
		}
		String sid = "" + ((Student) studentListTable2.getSelectionModel().getSelectedItem()).getId();
		if (idCombo.getValue().equals("Select Class by ID")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No class selected");
			alert.setHeaderText(null);
			alert.setContentText("Please Select a class first");

			alert.show();
			return;
		}
		if (Integer.parseInt(maxField.getText()) <= Integer.parseInt(numField.getText())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Class Full!");
			alert.setHeaderText(null);
			alert.setContentText("The Class is full!\nUnable to add more students!");

			alert.show();
			return;
		}
		String cid = (String) idCombo.getValue();
		String par[] = { sid, cid };
		if (AddStudentToClass(par)) {
			selectByID();
			getClasslessStudents();
		}
		
	}
	public boolean AddStudentToClass(String[] par) {
		//String par[] = { sid, cid };
		MyThread a = new MyThread(RequestType.AddStudentToClass, IndexList.AddStudentToClass, par);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return (boolean) MsgFromServer.getDataListByIndex(IndexList.AddStudentToClass);
	}

/**
 * This method happens when pressing the "remove" button.
 * It removes the selected student from the selected class and displays warning messages if selection is wrong.
 * @throws Exception
 */
	@FXML
	private void removeButton() throws Exception {
		String sid = "" + ((Student) studentListTable.getSelectionModel().getSelectedItem()).getId();
		String cid = "" + ((Student) studentListTable.getSelectionModel().getSelectedItem()).getClassid();
		String par[] = { sid, cid };
		MyThread a = new MyThread(RequestType.RemoveStudentFromClass, IndexList.RemoveStudentFromClass, par);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if ((boolean) MsgFromServer.getDataListByIndex(IndexList.RemoveStudentFromClass)) {
			selectByID();
			getClasslessStudents();
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
