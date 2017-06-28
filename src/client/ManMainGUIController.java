package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Course;
import entity.Student;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class ManMainGUIController implements Initializable {

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

	@FXML
	TableView<Student> table = new TableView<>();
	private ObservableList<Student> data;
	int num=0;

	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		ManName.setText(s.getName());
		studentParentInfo();
	}

	@SuppressWarnings("unchecked")
	public void studentParentInfo() {
		MyThread a = new MyThread(RequestType.StudentsList, IndexList.StudentsList);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		ArrayList<Student> b = (ArrayList<Student>) MsgFromServer.getDataListByIndex(IndexList.StudentsList);
		data = FXCollections.observableArrayList(b);

		TableColumn<Student, String> c1 = new TableColumn<>("Student Id");
		c1.setCellValueFactory(new PropertyValueFactory<>("Id"));
		TableColumn<Student, String> c2 = new TableColumn<>("Parent");
		c2.setCellValueFactory(new PropertyValueFactory<>("ParentId"));
		TableColumn<Student, String> c3 = new TableColumn<>("Class");
		c3.setCellValueFactory(new PropertyValueFactory<>("classid"));
		TableColumn<Student, String> c4 = new TableColumn<>("avg");
		c4.setCellValueFactory(new PropertyValueFactory<>("avg"));

		table.getColumns().addAll(c1, c2, c3, c4);
		table.setItems(data);
	}

	@FXML
	public void BlockParent() {
		String Pid = table.getSelectionModel().getSelectedItem().getParentId();
		MyThread a = new MyThread(RequestType.BlockParent, IndexList.BlockParent, Pid);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		num = (int) (MsgFromServer.getDataListByIndex(IndexList.BlockParent));
	}

	@FXML
	public void unBlockParent() {
		String Pid = table.getSelectionModel().getSelectedItem().getParentId();
		MyThread a = new MyThread(RequestType.unBlockParent, IndexList.unBlockParent, Pid);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		num = (int) (MsgFromServer.getDataListByIndex(IndexList.unBlockParent));
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
		return "ManMainGUIController [Hello=" + Hello + ", Reqs=" + Reqs + ", Reps=" + Reps + ", BlockLabel="
				+ BlockLabel + ", Ins_PID=" + Ins_PID + ", ManName=" + ManName + ", CheckReq=" + CheckReq + ", OpenReq="
				+ OpenReq + ", GenRep=" + GenRep + ", UnBlock=" + UnBlock + ", Block=" + Block + ", Back=" + Back
				+ ", LogOut=" + LogOut + ", Open_req=" + Open_req + ", Logo=" + Logo + "]";
	}
}