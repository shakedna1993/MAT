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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;

public class CourseListGUIController implements Initializable {
	@FXML
	Button back;
	@FXML
	Label stuName;
	@FXML
	Button LogOut;
	
	@FXML
	TableView<Course> table = new TableView<>();
	private ObservableList<Course> data;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		User s =new User();
		s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		stuName.setText(s.getName());
		try {
			MyThread a = new MyThread(RequestType.StudentCourse, IndexList.StudentCourse,
					MsgFromServer.getDataListByIndex(IndexList.LOGIN));
			a.start();
			a.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

		data = FXCollections.observableArrayList();
		ArrayList<Course> b = (ArrayList<Course>) MsgFromServer.getDataListByIndex(IndexList.StudentCourse);
		for (int i = 0; i < b.size(); i++) {
			data.add(b.get(i));
		}
		TableColumn<Course, String> c1 = new TableColumn<>("Course Id");
		c1.setCellValueFactory(new PropertyValueFactory<>("courseId"));
		TableColumn<Course, String> c2 = new TableColumn<>("Course Name");
		c2.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Course, String> c3 = new TableColumn<>("Semester");
		c3.setCellValueFactory(new PropertyValueFactory<>("semid"));
		
		table.getColumns().addAll(c1, c2, c3);
		table.setItems(data);
	}
	@FXML
	public void clsCourseList() {
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
	@FXML
	private void backButton(ActionEvent event) throws Exception{
		connectionmain.showStudentMain();
	}
	

}
