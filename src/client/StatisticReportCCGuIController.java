package client;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Class;
import entity.Reports;
import entity.Semester;
import entity.Teacher;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import thred.IndexList;
import thred.MyThread;

public class StatisticReportCCGuIController implements Initializable {
	@FXML 
	Label ManName,selectclass,choosenum;
	@FXML
	ComboBox<String> STC;
	@FXML
	ComboBox<String> STC1;
	@FXML
	Button Back;
	@FXML
	Button LogOut;
	@FXML 
	TextField num;
	@FXML
	ImageView Logo;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		User s =new User();
		s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		ManName.setText(s.getName());
	
		setComboBoxChooseClass();
		setComboBoxStartSem();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setComboBoxChooseClass(){
		ArrayList<Class> a1 = new ArrayList<Class>();
		ArrayList<String> a2 = new ArrayList<String>();
		Class cl1 = new Class();
		MyThread a = new MyThread(RequestType.createClassEntity, IndexList.createClassEntity,cl1);
		a.start();
		try {
			a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		a1 = (ArrayList<Class>) MsgFromServer.getDataListByIndex(IndexList.createClassEntity);
		
		for(int i=0;i<a1.size();i++)
			a2.add(a1.get(i).getName());
		ObservableList<String> list = FXCollections.observableArrayList(a2);
		STC.setItems(list);
	}
	public void setComboBoxStartSem(){
		ArrayList<Semester> a1 = new ArrayList<Semester>();
		ArrayList<String> a2 = new ArrayList<String>();
		Semester sem = new Semester();
		MyThread a = new MyThread(RequestType.createSemesterEntity, IndexList.createSemesterEntity, sem);
		a.start();
		try {
			a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		a1 = (ArrayList<Semester>) MsgFromServer.getDataListByIndex(IndexList.createSemesterEntity);
		
		for(int i=0;i<a1.size();i++)
			a2.add(a1.get(i).getSemId());
		ObservableList<String> list = FXCollections.observableArrayList(a2);
		STC1.setItems(list);
	}
	
	@FXML
	private void backButton(ActionEvent event) throws Exception{
		connectionmain.ShowReportSection();
	}
	
	@FXML
	public void clsReportCC() {
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
