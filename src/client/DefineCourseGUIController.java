
package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.User;
import entity.Course;
import entity.Unit;
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
 * This class is the controller for the Define Course screen GUI.
 */
public class DefineCourseGUIController implements Initializable{

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
	ImageView LogOutIcon;
	@FXML
	ImageView AddIcon;
	@FXML
	Button DefineButton;
	@FXML
	Button BackButton;
	@FXML
	Button LogOutButton;
	@FXML
	Label DefineLabel;
	@FXML
	Label CourseName_Label;
	@FXML
	Label Unit_Label;
	@FXML
	Label CourseID_Label;
	@FXML
	Label Week_Label;
	@FXML
	TextField CourseName;
	@FXML
	TextField Unit;
	@FXML
	TextField CourseID;
	@FXML
	TextField Week;

	/**
	 * initialize-initialize the system manager name
	 */
	public void initialize(URL location, ResourceBundle resources) {
		User s =new User();
		s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		SysName.setText(s.getName());
	}
	
	/**
	 * Define- get all the details of the new course to insert into the DB
	 */
	@FXML
	public void Define() throws IOException {
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
			
			Course crs = new Course(CourseID.getText(), CourseName.getText(),Unit.getText(),Integer.parseInt(Week.getText().replaceAll("\\s","").length()==0? "-1" : Week.getText()));
			
			if (crs.getCourseId().replaceAll("\\s","").length() == 0 || crs.getName().replaceAll("\\s","").length() == 0 || crs.getUnit().replaceAll("\\s","").length() == 0 || crs.getHours() == -1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Empty Fields");
				alert.setHeaderText(null);
				alert.setContentText("Please fill all the Fields");

				alert.show();
				return;
			}
			
			if (crs.getCourseId().length()!= 3 || crs.getCourseId().replaceAll("\\s","").length()!= 3 || Character.isLetter(crs.getCourseId().charAt(0)) || Character.isLetter(crs.getCourseId().charAt(1)) || Character.isLetter(crs.getCourseId().charAt(2))) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Invalid Fields");
				alert.setHeaderText(null);
				alert.setContentText("Invalid Course ID");

				alert.show();
				return;
			}
			
			if (crs.getUnit().length()!= 2 ) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Invalid Fields");
				alert.setHeaderText(null);
				alert.setContentText("Invalid Unit Number");

				alert.show();
				return;
			}
			
			if (crs.getName().length()< 2 ) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Invalid Fields");
				alert.setHeaderText(null);
				alert.setContentText("The Course Name is too short");

				alert.show();
				return;
			}
			
			if (crs.getHours() <=0 ) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Invalid Fields");
				alert.setHeaderText(null);
				alert.setContentText("Invalid Weekly Hours");

				alert.show();
				return;
			}
			
			Unit un= new Unit (crs.getUnit());
			MyThread a = new MyThread(RequestType.UnitExists, IndexList.UnitExists, un);
			a.start();
			a.join();
			
			if(((Unit) (MsgFromServer.getDataListByIndex(IndexList.UnitExists))).getUnitId().equals("-1")) {
 				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong details");
				alert.setHeaderText(null);
				alert.setContentText("The teaching unit does not exist");

				alert.show();
				return;
			}
			
			crs.setCourseId(crs.getUnit()+crs.getCourseId());
			Course crs1=new Course(crs.getCourseId());
			MyThread b = new MyThread(RequestType.CourseExists, IndexList.CourseExists, crs1);
			b.start();
			b.join();
			
			if (!((Course) (MsgFromServer.getDataListByIndex(IndexList.CourseExists))).getCourseId().equals("-1")) {
 				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong details");
				alert.setHeaderText(null);
				alert.setContentText("The Course ID is already exists");

				alert.show();
				return;
			}
			
			MyThread c = new MyThread(RequestType.DefineNewCourse, IndexList.DefineNewCourse, crs);
			c.start();
			c.join();
			if (!((Course) (MsgFromServer.getDataListByIndex(IndexList.DefineNewCourse))).getCourseId().equals("-1")) {
				connectionmain.showDefineCourseAddPre();
			}
			else{
 				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Failer");
				alert.setHeaderText(null);
				alert.setContentText("Failer- The course was not added");

				alert.show();
				return;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	public static String courseExists(Course crs){
		MyThread b = new MyThread(RequestType.CourseExists, IndexList.CourseExists, crs);
		b.start();
		try {
			b.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ""+((Course) (MsgFromServer.getDataListByIndex(IndexList.CourseExists))).getCourseId().equals("-1");
	}
	public static String defNewCourse(Course crs){
		MyThread c = new MyThread(RequestType.DefineNewCourse, IndexList.DefineNewCourse, crs);
		c.start();
		try {
			c.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Course course = (Course) (MsgFromServer.getDataListByIndex(IndexList.DefineNewCourse)); 
		return course.getCourseId();
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
