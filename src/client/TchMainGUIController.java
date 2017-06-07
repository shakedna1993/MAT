package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class TchMainGUIController implements Initializable{
	
	public static ClientConsole cli;
	public static Stage primaryStage;
	
	@FXML
	ComboBox<String> STC;
	@FXML
	Button Post_Ass;
	@FXML
	Button Check_Ass;
	@FXML
	Button CourseL;
	@FXML
	Button Back;
	@FXML
	Button LogOut;

	@FXML
	Label Hello, selectAss, WeekTH ;
	@FXML
	Label selectTC;
	@FXML
	Label General;
	@FXML
	Label Ass;
	@FXML
	javafx.scene.control.Label TchName;
	@FXML
	javafx.scene.control.Label WeekHours;
	@FXML
	TableView<String> Pub_Ass; 
	@FXML
	ImageView Logo;
	
	
	public void initialize(URL location, ResourceBundle resources) {
			User s =new User();
			s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
			TchName.setText(s.getName());
		}
	
	@FXML
	public void clsTeacherMain() {
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

	@Override
	public String toString() {
		return "TchMainGUIController [STC=" + STC + ", Post_Ass=" + Post_Ass + ", Check_Ass=" + Check_Ass + ", CourseL="
				+ CourseL + ", Back=" + Back + ", LogOut=" + LogOut + ", Hello=" + Hello + ", selectAss=" + selectAss
				+ ", WeekTH=" + WeekTH + ", selectTC=" + selectTC + ", General=" + General + ", Ass=" + Ass
				+ ", TchName=" + TchName + ", WeekHours=" + WeekHours + ", Pub_Ass=" + Pub_Ass + ", Logo=" + Logo + "]";
	}
	


}
