package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Course;
import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

/**
 * This class is the controller for the Remove Course screen GUI.
 */
public class RemoveCourseGUIController implements Initializable{
	
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
	ImageView RemoveIcon;
	@FXML
	ImageView LogOutIcon;
	@FXML
	Button RemoveButton;
	@FXML
	Button BackButton;
	@FXML
	Button LogOutButton;
	@FXML
	Label RemoveLabel;
	@FXML
	Label CourseID_Label;
	@FXML
	Label Note_Label;
	@FXML
	TextField CourseID;

	/**
	 * initialize-initialize the system manager name
	 */
	public void initialize(URL location, ResourceBundle resources) {
		User s =new User();
		s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		SysName.setText(s.getName());
	}
	
	/**
	 * Remove-get the course id to remove from the DB
	 */
	public void Remove() throws IOException {
		try {
			Course crs = new Course(CourseID.getText());
			
			if (crs.getCourseId().replaceAll("\\s","").length() == 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Empty Fields");
				alert.setHeaderText(null);
				alert.setContentText("Please insert the Course ID");

				alert.show();
				return;
			}
			Course crs1=new Course(crs.getCourseId());
			MyThread b = new MyThread(RequestType.CourseExists, IndexList.CourseExists, crs1);
			b.start();
			b.join();
			
			if (((Course) (MsgFromServer.getDataListByIndex(IndexList.CourseExists))).getCourseId().equals("-1")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong details");
				alert.setHeaderText(null);
				alert.setContentText("The Course is not exists");
				
				alert.show();
				return;
			}
			
			MyThread c = new MyThread(RequestType.RemoveCourse, IndexList.RemoveCourse, crs1);
			c.start();
			c.join();
			
			if ((int)MsgFromServer.getDataListByIndex(IndexList.RemoveCourse) == 1)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Course Delete");
					alert.setHeaderText(null);
					alert.setContentText("The Course Deleted from the system");
					alert.show();
					return;
				}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
	}
	
	/**
	 * This method goes back to the last window that been shown
	 */
	public void back() {
		try {
			connectionmain.showSysManMain();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * Logout from the server
	 */
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
