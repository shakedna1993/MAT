package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Course;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class EditCourseGUIController implements Initializable{
	
	public static ClientConsole cli;
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
	ImageView BackIcon;
	@FXML
	ImageView EditIcon, EditIcon1;
	@FXML
	ImageView LogOutIcon;
	@FXML
	Button EnterButton;
	@FXML
	Button BackButton;
	@FXML
	Button LogOutButton;
	@FXML
	Label EditLabel;
	@FXML
	Label CourseID_Label;
	@FXML
	TextField CourseID;
	@FXML
	Label Note_Label;
	@FXML
	Label Insert_Label;
	@FXML
	Label Press_Label;
	@FXML
	Tab Rename_TAB;
	@FXML
	Label CourseName_Label;
	@FXML
	TextField CourseName;
	@FXML
	Button Rename_Button;
	@FXML
	Tab WeekltHours_TAB;
	@FXML
	Label Week_Label;
	@FXML
	TextField Week;
	@FXML
	Button ChangeWH_Button;
	@FXML
	Tab AddPre_TAB;
	@FXML
	Tab RemovePre_TAB;
	@FXML
	Button Add_Button;
	@FXML
	Button Remove_Button;
	@FXML
	Label Check;
	@FXML
	TableView<Course> ADDPreReq = new TableView<>();
	@FXML
	TableView<Course> RemovePreReq = new TableView<>();
	private ObservableList<Course> data;
	private ObservableList<Course> data1;
	private String crs_id="-1";

	
	public void initialize(URL location, ResourceBundle resources) {
		User s =new User();
		s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		SysName.setText(s.getName());
	}
	
	@SuppressWarnings("unchecked")
	public void EnterCourseID() throws IOException {
		try {
			Check.setText("");
			CourseName.setText("");
			Week.setText("");
			Rename_TAB.setDisable(true);
			WeekltHours_TAB.setDisable(true);
			AddPre_TAB.setDisable(true);
			RemovePre_TAB.setDisable(true);
			Course crs = new Course(CourseID.getText());
			
			if (crs.getCourseId().replaceAll("\\s","").length() == 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Empty Fields");
				alert.setHeaderText(null);
				alert.setContentText("Please insert the Course ID");

				alert.show();
				return;
			}
			MyThread c = new MyThread(RequestType.CourseExists, IndexList.CourseExists, crs);
			c.start();
			c.join();
			
			
			Course crs_up=((Course) (MsgFromServer.getDataListByIndex(IndexList.CourseExists)));
			if (crs_up.getCourseId().equals("-1")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong details");
				alert.setHeaderText(null);
				alert.setContentText("The Course is not exists");
				
				alert.show();
				return;
			}
			else{
				crs_id=crs.getCourseId();
				Check.setText(crs_up.getName()+" - Open for updates");
				Rename_TAB.setDisable(false);
				WeekltHours_TAB.setDisable(false);
				AddPre_TAB.setDisable(false);
				RemovePre_TAB.setDisable(false);
				RemovePreReq.getColumns().clear();
				ADDPreReq.getColumns().clear();
				
				try {
					MyThread a = new MyThread(RequestType.CoursesList, IndexList.CoursesList, crs);
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

				ADDPreReq.getColumns().addAll(c1, c2);
				ADDPreReq.setItems(data);
				
				try {
					MyThread d = new MyThread(RequestType.PreReqList, IndexList.PreReqList, crs);
					d.start();
					d.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				data1 = FXCollections.observableArrayList();
				ArrayList<Course> e=(ArrayList<Course>)MsgFromServer.getDataListByIndex(IndexList.PreReqList);
				for(int i=0;i<e.size();i++)
				{
					data1.add(e.get(i));
				}
					
				TableColumn<Course, String> c11 = new TableColumn<>("Pre Requisite Course ID");
				c11.setCellValueFactory(new PropertyValueFactory<>("courseId"));
			//	TableColumn<Course, String> c22 = new TableColumn<>("  Course Name ");
			//	c22.setCellValueFactory(new PropertyValueFactory<>("name"));

				RemovePreReq.getColumns().addAll(c11);
				RemovePreReq.setItems(data1);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void RenameCourse() throws IOException {
		try {
			if (CourseName.getText().replaceAll("\\s","").length() == 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Empty Fields");
				alert.setHeaderText(null);
				alert.setContentText("Please fill the Field");

				alert.show();
				return;
			}
			
			if(CourseName.getText().length()< 2 ) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Invalid Fields");
					alert.setHeaderText(null);
					alert.setContentText("The Course Name is too short");
	
					alert.show();
					return;
			}
			
			Course crs = new Course(crs_id);
			crs.setName(CourseName.getText());
			MyThread b = new MyThread(RequestType.RenameCourse, IndexList.RenameCourse, crs);
			b.start();
			b.join();
			
			if ((int)MsgFromServer.getDataListByIndex(IndexList.RenameCourse) == 1)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText(null);
				alert.setContentText("Rename Successfully");
				alert.show();
				return;
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("No Change");
				alert.setHeaderText(null);
				alert.setContentText("New name identical to old, details not changed");
				alert.show();
				return;
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void WeeklyHoursUpdateButton() throws IOException {
		try {
			for (char c : Week.getText().toCharArray())
		    {
		        if (!Character.isDigit(c)){
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Invalid Fields");
					alert.setHeaderText(null);
					alert.setContentText("Invalid Weekly Hours");

					alert.show();
					return;
		        }
		    }
			
			Course crs = new Course(crs_id);
			crs.setHours(Integer.parseInt(Week.getText().replaceAll("\\s","").length()==0? "-1" : Week.getText()));
			if (crs.getHours() <=0 ) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Invalid Fields");
				alert.setHeaderText(null);
				alert.setContentText("Invalid Weekly Hours");

				alert.show();
				return;
			}
			
			MyThread b = new MyThread(RequestType.WeeklyHoursUpdate, IndexList.WeeklyHoursUpdate, crs);
			b.start();
			b.join();
			
			if ((int)MsgFromServer.getDataListByIndex(IndexList.WeeklyHoursUpdate) == 1)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText(null);
				alert.setContentText("Weekly Hours Changed Successfully");
				alert.show();
				return;
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("No Change");
				alert.setHeaderText(null);
				alert.setContentText("Hours identical to the original, details not changed");
				alert.show();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void AddPreReq() {
		if (ADDPreReq.getSelectionModel().getSelectedItem() == null)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Wrong Choise");
			alert.setHeaderText(null);
			alert.setContentText("Please choose a course before you press on ADD");
			alert.show();
			return;
		}
		String tmp=ADDPreReq.getSelectionModel().getSelectedItem().getCourseId();
		Course crs = new Course(crs_id,tmp);
		try {
			MyThread b = new MyThread(RequestType.DefinePreReq, IndexList.DefinePreReq, crs);
			b.start();
			b.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
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
	
	public void RemovePreReq() {
		if (RemovePreReq.getSelectionModel().getSelectedItem() == null)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Wrong Choise");
			alert.setHeaderText(null);
			alert.setContentText("Please choose a course before you press on Remove");
			alert.show();
			return;
		}
		String tmp=RemovePreReq.getSelectionModel().getSelectedItem().getCourseId();
		Course crs = new Course(crs_id);
		crs.setPreReqId(tmp);
		try {
			MyThread b = new MyThread(RequestType.RemovePreReq, IndexList.RemovePreReq, crs);
			b.start();
			b.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if ((int)MsgFromServer.getDataListByIndex(IndexList.RemovePreReq) == 1)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("Pre Request successfully removed");
			alert.show();
			return;
		}
		else																
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Wrong Details");
			alert.setHeaderText(null);
			alert.setContentText("Pre Request already removed");
			alert.show();
			return;
		}
	}
		
	public void back() {
		try {
			Stage stage = (Stage) BackButton.getScene().getWindow();
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
	@FXML
	public void clsSysMan() {
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

}
