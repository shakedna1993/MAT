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

public class SysManMainGUIController implements Initializable {

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
<<<<<<< HEAD
=======
	Label CN_Define;
	@FXML
	Label TU_Define;
	@FXML
	Label CID_Define;
	@FXML
	Label WH_Define;
	@FXML
	Label PR_Define;
	@FXML
	TextField CNT_Define;
	@FXML
	TextField TUT_Define;
	@FXML
	TextField CIDT_Define;
	@FXML
	TextField WHT_Define;
	@FXML
	TextArea PRT_Define;
	@FXML
	Label CID_Edit;
	@FXML
	Label WH_Edit;
	@FXML
	Label PR_Edit;
	@FXML
	TextField CIDT_Edit;
	@FXML
	TextField WHT_Edit;
	@FXML
	TextArea PRT_Edit;
	@FXML
	Label CID_Remove;
	@FXML
	TextField CIDT_Remove;

	@FXML
>>>>>>> branch 'master' of https://github.com/shakedna1993/MAT.git
	ImageView Logo;
<<<<<<< HEAD
	@FXML
	Label CourseList_Label;
	@FXML
	TableView<Course> CourseLIST = new TableView<>();
	private ObservableList<Course> data;
	
	
	@SuppressWarnings("unchecked")
=======

>>>>>>> branch 'master' of https://github.com/shakedna1993/MAT.git
	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
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
<<<<<<< HEAD
	
	@FXML
	public void DefineWindow() throws IOException{
		connectionmain.showDefineWindow();
	}
	
	@FXML
	public void EditWindow() throws IOException{
		connectionmain.showEditWindow();
	}
	@FXML
	public void RemoveWindow() throws IOException{
		connectionmain.showRemoveWindow();
	}
	
=======

>>>>>>> branch 'master' of https://github.com/shakedna1993/MAT.git
	@FXML
	public void clsSysManMain() {
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

<<<<<<< HEAD
	
=======
	@Override
	public String toString() {
		return "SysManMainGUIController [Hello=" + Hello + ", SysName=" + SysName + ", LogOut=" + LogOut + ", Main="
				+ Main + ", DefineTab=" + DefineTab + ", EditTab=" + EditTab + ", RemoveTab=" + RemoveTab
				+ ", DefineLabel=" + DefineLabel + ", EditLabel=" + EditLabel + ", RemoveLabel=" + RemoveLabel
				+ ", DefineButton=" + DefineButton + ", EditButton=" + EditButton + ", RemoveButton=" + RemoveButton
				+ ", CN_Define=" + CN_Define + ", TU_Define=" + TU_Define + ", CID_Define=" + CID_Define
				+ ", WH_Define=" + WH_Define + ", PR_Define=" + PR_Define + ", CNT_Define=" + CNT_Define
				+ ", TUT_Define=" + TUT_Define + ", CIDT_Define=" + CIDT_Define + ", WHT_Define=" + WHT_Define
				+ ", PRT_Define=" + PRT_Define + ", CID_Edit=" + CID_Edit + ", WH_Edit=" + WH_Edit + ", PR_Edit="
				+ PR_Edit + ", CIDT_Edit=" + CIDT_Edit + ", WHT_Edit=" + WHT_Edit + ", PRT_Edit=" + PRT_Edit
				+ ", CID_Remove=" + CID_Remove + ", CIDT_Remove=" + CIDT_Remove + ", Logo=" + Logo + "]";
	}

>>>>>>> branch 'master' of https://github.com/shakedna1993/MAT.git
}
