package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

import client.RequestType;
import entity.Course;
import entity.Student;
import entity.User;
import client.connectionmain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class BlockedParentMain implements Initializable {

	public static ClientConsole cli;
	public static Stage primaryStage;

	@FXML
	Button LogOut;
	@FXML
	Label Hello;
	@FXML
	javafx.scene.control.Label parName;
	@FXML
	Label SorryMsg;
	@FXML
	ImageView Logo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		parName.setText(s.getName());
	}

	@FXML
	public void clsParentMain() {
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

	@Override
	public String toString() {
		return "BlockParentMainGUIController [ LogOut=" + LogOut + ", Hello=" + Hello + ", parName=" + parName
				+ ", Logo=" + Logo + "]";
	}

}
