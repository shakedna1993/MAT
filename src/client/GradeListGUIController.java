package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Course;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import thred.IndexList;
import thred.MyThread;

/**
 * This class is the controller for the Grade list screen GUI.
 */
public class GradeListGUIController implements Initializable {
	@FXML
	private Button back;
	@FXML
	private Label stuName;
	@FXML
	private Button LogOut;

	@FXML
	private TableView<Course> table = new TableView<>();
	private ObservableList<Course> data;

	/**
	 * initialize-initialize the student name, shows the grade list.
	 */
	@SuppressWarnings("unchecked")
	public void initialize(URL arg0, ResourceBundle arg1) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		stuName.setText(s.getName());

		if (s.getRole() == 4) {
			try {
				MyThread a = new MyThread(RequestType.StudentCourse, IndexList.StudentCourse,
						MsgFromServer.getDataListByIndex(IndexList.LOGIN));
				a.start();
				a.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (s.getRole() == 6) {
			try {
				MyThread a = new MyThread(RequestType.StudentCourse, IndexList.StudentCourse,
						ParMainGUIController.getSelectedSon());
				a.start();
				a.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (s.getRole() == 2) {
			try {
				MyThread a = new MyThread(RequestType.StudentCourse, IndexList.StudentCourse,
						InfoGUIController.getSelectedUser());
				a.start();
				a.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		data = FXCollections.observableArrayList();
		ArrayList<Course> b = (ArrayList<Course>) MsgFromServer.getDataListByIndex(IndexList.StudentCourse);
		for (int i = 0; i < b.size(); i++)
			data.add(b.get(i));

		TableColumn<Course, String> c1 = new TableColumn<>("Course Id");
		c1.setCellValueFactory(new PropertyValueFactory<>("courseId"));
		TableColumn<Course, String> c2 = new TableColumn<>("Course Name");
		c2.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Course, String> c3 = new TableColumn<>("Semester");
		c3.setCellValueFactory(new PropertyValueFactory<>("semid"));
		TableColumn<Course, String> c4 = new TableColumn<>("Grade");
		c4.setCellValueFactory(new PropertyValueFactory<>("grade"));

		table.getColumns().addAll(c1, c2, c3, c4);
		table.setItems(data);
	}

	/**
	 * Logout from the server
	 */
	@FXML
	public void clsGradeList() {
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
	 * This method goes back to the last window that been shown
	 */
	@FXML
	private void backButton(ActionEvent event) throws Exception {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		int a = s.getRole();
		if (a == 4)
			connectionmain.showStudentMain();
		else if (a == 6)
			connectionmain.showParentMain();
		else if (a == 2)
			connectionmain.showInfo();
	}

}
