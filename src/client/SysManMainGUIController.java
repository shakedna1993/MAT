package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.RequestType;
import entity.Course;
import entity.User;
import client.connectionmain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

/**
 * This class is the controller for the System Manager main screen GUI.
 */
public class SysManMainGUIController implements Initializable{
	
	public static ClientConsole cli;
	public static Stage primaryStage;
	
	@FXML
	Label Hello;
	@FXML
	javafx.scene.control.Label SysName;
	@FXML
	ImageView BackGround;
	@FXML
	ImageView AddIcon;
	@FXML
	ImageView EditIcon;
	@FXML
	ImageView RemoveIcon;
	@FXML
	ImageView LogOutIcon;
	@FXML
	Button LogOut;
	@FXML
	Button DefineButton;
	@FXML
	Button EditButton;
	@FXML
	Button RemoveButton;
	@FXML
	ImageView Logo;
	@FXML
	Label CourseList_Label;
	@FXML
	TableView<Course> CourseLIST = new TableView<>();
	private ObservableList<Course> data;
	
	/**
	 * initialize-initialize the system manager name and a list of existing course in the DB
	 */
	@SuppressWarnings("unchecked")
	public void initialize(URL location, ResourceBundle resources) {
		User s =new User();
		s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		SysName.setText(s.getName());
		
		
		Course crs =new Course("-1");
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
		
		TableColumn<Course, String> c1 = new TableColumn<>("Course ID");
		c1.setCellValueFactory(new PropertyValueFactory<>("courseId"));
		TableColumn<Course, String> c2 = new TableColumn<>("Course Name");
		c2.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Course, String> c3 = new TableColumn<>("Unit");
		c3.setCellValueFactory(new PropertyValueFactory<>("unit"));
		TableColumn<Course, String> c4 = new TableColumn<>("Weekly Hours");
		c4.setCellValueFactory(new PropertyValueFactory<>("Hours"));

		CourseLIST.getColumns().addAll(c1, c2, c3, c4);
		CourseLIST.setItems(data);
		c1.setStyle("-fx-alignment: CENTER;");
		c2.setStyle("-fx-alignment: CENTER;");
		c3.setStyle("-fx-alignment: CENTER;");
		c4.setStyle("-fx-alignment: CENTER;");
	}
	
	/**
	 * DefineWindow-shows the Define course window
	 */
	@FXML
	public void DefineWindow() throws IOException{
		connectionmain.showDefineWindow();
	}
	
	/**
	 * EditWindow-shows the Edit course window
	 */
	@FXML
	public void EditWindow() throws IOException{
		connectionmain.showEditWindow();
	}
	
	/**
	 * RemoveWindow-shows the Remove course window
	 */
	@FXML
	public void RemoveWindow() throws IOException{
		connectionmain.showRemoveWindow();
	}
	
	/**
	 * Logout from the server  
	 */
	@FXML
	public void clsSysManMain() {
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
	