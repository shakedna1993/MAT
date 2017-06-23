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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	Button LogOut;
	@FXML
	TabPane Main;
	@FXML
	Tab DefineTab;
	@FXML
	Tab EditTab;
	@FXML
	Tab RemoveTab;
	@FXML
	Label DefineLabel;
	@FXML
	Label EditLabel;
	@FXML
	Label RemoveLabel;
	@FXML
	Button DefineButton;
	@FXML
	Button EditButton;
	@FXML
	Button RemoveButton;
	@FXML
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
	ImageView Logo;

	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		SysName.setText(s.getName());

	}

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

}
