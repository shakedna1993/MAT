package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class ManMainGUIController implements Initializable{
	
	public static ClientConsole cli;
	public static Stage primaryStage;
	
	@FXML
	Label Hello;
	@FXML
	Label Reqs;
	@FXML
	Label Reps;
	@FXML
	Label BlockLabel;
	@FXML
	Label Ins_PID;
	@FXML
	TextField Parent_id;
	@FXML
	javafx.scene.control.Label ManName;
	@FXML
	Button CheckReq;
	@FXML
	Button OpenReq;
	@FXML
	Button GenRep;
	@FXML
	Button UnBlock;
	@FXML
	Button Block;
	@FXML
	Button Back;
	@FXML
	Button LogOut;
	@FXML
	TableView<String> Open_req; 
	@FXML
	ImageView Logo;
	
	
	public void initialize(URL location, ResourceBundle resources) {
			User s =new User();
			s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
			ManName.setText(s.getName());
		}
	
	@FXML
	public void GenrateReports() {
		try {
			connectionmain.ShowReportSection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	public void clsManagerMain() {
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
		return "ManMainGUIController [Hello=" + Hello + ", Reqs=" + Reqs + ", Reps=" + Reps + ", BlockLabel="
				+ BlockLabel + ", Ins_PID=" + Ins_PID + ", Parent_id=" + Parent_id + ", ManName=" + ManName
				+ ", CheckReq=" + CheckReq + ", OpenReq=" + OpenReq + ", GenRep=" + GenRep + ", UnBlock=" + UnBlock
				+ ", Block=" + Block + ", Back=" + Back + ", LogOut=" + LogOut + ", Open_req=" + Open_req + ", Logo="
				+ Logo + "]";
	}
}