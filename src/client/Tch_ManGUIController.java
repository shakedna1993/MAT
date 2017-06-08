package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class Tch_ManGUIController implements Initializable{
	
	public static ClientConsole cli;
	public static Stage primaryStage;
	
	@FXML
	ImageView Logo;
	@FXML
	Label Hello;
	@FXML
	Button LogOut;
	@FXML
	javafx.scene.control.Label UName;
	@FXML
	Button Teacher_Win;
	@FXML
	Button Manager_Win;
	
	
	public void initialize(URL location, ResourceBundle resources) {
			User s =new User();
			s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
			UName.setText(s.getName());
	}
	@FXML
	public void showManMain() throws IOException {
		connectionmain.showManagerMain();
	}
	@FXML
	public void showTchMain() throws IOException {
		connectionmain.showTeacherMain();
	}
	
	@FXML
	public void clsTchManMain() {
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
		return "Tch_ManGUIController [Logo=" + Logo + ", Hello=" + Hello + ", LogOut=" + LogOut + ", UName=" + UName
				+ ", Teacher_Win=" + Teacher_Win + ", Manager_Win=" + Manager_Win + "]";
	}
	
	
}