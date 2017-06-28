package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Assigenment;
import entity.Course;
import entity.Reports;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;

public class ReportsMainGUIController implements Initializable {
	public static ClientConsole cli;
	public static Stage primaryStage;
	
	@FXML
	ComboBox<String> STC;
	@FXML
	Button selectBtn;
	@FXML
	Button Back;
	@FXML
	Button LogOut;
	@FXML
	Label Hello;
	@FXML
	Label ManName;
	@FXML
	ImageView Logo;
	
	public void initialize(URL location, ResourceBundle resources) {
		User s =new User();
		s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		ManName.setText(s.getName());
	
		setComboBoxManagerReports();	
	}
	
	@SuppressWarnings("unchecked")
	public void setComboBoxManagerReports(){
		ArrayList<Reports> a1 = new ArrayList<Reports>();
		ArrayList<String> a2 = new ArrayList<String>();
		Reports rep = new Reports();
		MyThread a = new MyThread(RequestType.createReportEntity, IndexList.createReportEntity, rep);
		a.start();
		try {
			a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		a1 = (ArrayList<Reports>) MsgFromServer.getDataListByIndex(IndexList.createReportEntity);
		for(int i=0;i<a1.size();i++)
			a2.add(a1.get(i).getRepName());
		ObservableList<String> list = FXCollections.observableArrayList(a2);
		STC.setItems(list);
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	public void	setStatisticReport(){	 
		Object st= STC.getValue();
		if(st==null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please Select Report");

			alert.show();
			return;
		}
		
		String  ReportName=st.toString();
		ArrayList<Reports> a1 = new ArrayList<Reports>();
		int a2 = 0;
		
		Reports rep = new Reports();
		MyThread a = new MyThread(RequestType.createReportEntity, IndexList.createReportEntity, rep);
		a.start();
		try {
			a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		a1 = (ArrayList<Reports>) MsgFromServer.getDataListByIndex(IndexList.createReportEntity);
		for(int i=0;i<a1.size();i++){
			if(a1.get(i).getRepName().equals(ReportName)){
				a2=a1.get(i).getType();
				break;
			}
		}
		try {
			if(a2==1)
				connectionmain.ShowStatisticReportClassTec();
			if(a2==2)
				connectionmain.ShowStatisticReportTecClass();
			else if(a2==3)
				connectionmain.ShowStatisticReportCourseClass();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void backButton(ActionEvent event) throws Exception{
		connectionmain.showManagerMain();
	}
	
	@FXML
	public void clsReportMain() {
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
		return "ReportsMainGUIController [STC=" + STC + ", selectBtn=" + selectBtn + ", Back=" + Back + ", LogOut="
				+ LogOut + ", Hello=" + Hello + ", ManName=" + ManName + ", Logo=" + Logo + "]";
	}
	
	

}
