package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.RequestType;
import entity.User;
import client.connectionmain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class ParMainGUIController implements Initializable{
	
	public static ClientConsole cli;
	public static Stage primaryStage;
	

	@FXML
	Button GradeL;
	@FXML
	Button CourseL;
	@FXML
	Button Evalu;
	@FXML
	Button LogOut;
	@FXML
	Button AvgBtn;

	@FXML
	Label Hello;

	@FXML
	javafx.scene.control.Label parName;
	@FXML
	javafx.scene.control.Label CalcAvg ;
	@FXML
	ComboBox<String> StuN;
	
	@FXML
	ImageView Logo;
	
	
	public void initialize(URL location, ResourceBundle resources) {
			User s =new User();
			s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
			parName.setText(s.getName());

		}
	
	@FXML
	public void clsParentMain() {
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
