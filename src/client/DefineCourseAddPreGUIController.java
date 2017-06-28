package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.User;
import entity.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class DefineCourseAddPreGUIController implements Initializable{

	public static Stage primaryStage;
	
	@FXML
	Label Hello;
	@FXML
	javafx.scene.control.Label SysName; 
	@FXML
	ImageView Logo;
	@FXML
	ImageView BackGround;
	@FXML
	ImageView successIcon;
	@FXML
	Button FINISHButton;
	@FXML
	Button ADDButton;
	@FXML
	Label SuccessLabel;
	@FXML
	Label SuccessLabel1;
	@FXML
	Label SuccessLabel2;
	@FXML
	Label PreReqLabel;
	@FXML
	Label NoteLabel;
	@FXML
	Label newCoursename;
	@FXML
	Label newCourseid;
	
	@FXML
	TableView<Course> PreReq = new TableView<>();
	private ObservableList<Course> data;
	private String new_crs_id;
	
	@SuppressWarnings("unchecked")
	public void initialize(URL location, ResourceBundle resources) {
		User s =new User();
		s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		SysName.setText(s.getName());
		
		Course new_crs =new Course();
		new_crs=(Course) (MsgFromServer.getDataListByIndex(IndexList.DefineNewCourse));
		Course new_crs1 =new Course(new_crs.getCourseId());
		newCoursename.setText(new_crs.getName());
		newCourseid.setText(new_crs.getCourseId());
		new_crs_id=new_crs.getCourseId();
		
		try {
		MyThread a = new MyThread(RequestType.CoursesList, IndexList.CoursesList, new_crs1);
		a.start();
		a.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		data = FXCollections.observableArrayList();
		ArrayList<Course> b=(ArrayList<Course>)MsgFromServer.getDataListByIndex(IndexList.CoursesList);
		for(int i=0;i<b.size();i++)
		{
			data.add(b.get(i));
		}
		
		TableColumn<Course, String> c1 = new TableColumn<>(" Course ID  ");
		c1.setCellValueFactory(new PropertyValueFactory<>("courseId"));
		TableColumn<Course, String> c2 = new TableColumn<>("  Course Name ");
		c2.setCellValueFactory(new PropertyValueFactory<>("name"));

		PreReq.getColumns().addAll(c1, c2);
		PreReq.setItems(data);
	}
	
	public void AddPreReq() {
		if (PreReq.getSelectionModel().getSelectedItem() == null)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Wrong Choise");
			alert.setHeaderText(null);
			alert.setContentText("Please choose a course before you press on ADD");
			alert.show();
			return;
		}
		String tmp=PreReq.getSelectionModel().getSelectedItem().getCourseId();
		Course crs = new Course(new_crs_id,tmp);
		try {
			MyThread b = new MyThread(RequestType.DefinePreReq, IndexList.DefinePreReq, crs);
			b.start();
			b.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
			// alert !!
		}
		if ((int)MsgFromServer.getDataListByIndex(IndexList.DefinePreReq) == 1)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("Pre Request successfully added");
			alert.show();
			return;
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Wrong Details");
			alert.setHeaderText(null);
			alert.setContentText("Pre Request for this course is alreay exists");
			alert.show();
			return;
		}
	}
	
	public void back() {
		try {
			Stage stage = (Stage) FINISHButton.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connectionmain.showSysManMain();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
