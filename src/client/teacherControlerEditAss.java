package client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Assigenment;
import entity.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;

public class teacherControlerEditAss implements Initializable{
	@FXML
	javafx.scene.control.Label courseTxt;
	@FXML
	javafx.scene.control.Label assTxt;
	@FXML
	TextField stateTxt;
	@FXML
	TextField fileidTxt;
	@FXML
	TextField DaySubmissionTEXT;
	@FXML
	TextField month;
	@FXML
	TextField yearText;
	
	@FXML
	Button backBtn;
	@FXML
	Button editBtn;
	Course c;
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	 c = new Course();
	assTxt.setText(TchMainGUIController.assToChoose);
	ArrayList<Assigenment> b=(ArrayList<Assigenment>)MsgFromServer.getDataListByIndex(IndexList.setTableViewTeacherCourseAssigenment);
	
	MyThread a = new MyThread(RequestType.createCourseEntity, IndexList.createCourseEntity, b.get(0).getCourseid());
	a.start();
	try {
			a.join();
	} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	c = (Course) MsgFromServer.getDataListByIndex(IndexList.createCourseEntity);
	courseTxt.setText(c.getName());
	}
	
	
	
	public void editAss(){
		Assigenment ass = new Assigenment();
		ass.setAssId(assTxt.getText());
		ass.setCoursename(courseTxt.getText());
		ass.setCourseid(c.getCourseId());
		ass.setFileid(fileidTxt.getText());
		ass.setSemester(stateTxt.getText());
		
		MyThread a = new MyThread(RequestType.UpdateAss, IndexList.UpdateAss, ass);
		a.start();
		try {
				a.join();
		} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		
		if((int) MsgFromServer.getDataListByIndex(IndexList.UpdateAss)==1){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Edit assignment");
			alert.setHeaderText(null);
			alert.setContentText("Task # "+ass.getAssname()+" edit successfully ");
			alert.show();
			return;
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Edit assignment");
			alert.setHeaderText(null);
			alert.setContentText("Something went wrong, please try again");
			alert.show();
			return;
		
	}
} 
	
	
	@FXML
	private void backButton(ActionEvent event) throws Exception{
		Stage primaryStage = connectionmain.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/TeacherMainGUI.fxml"));
		Pane root = loader.load();		
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("M.A.T- Secretary Connection");
		primaryStage.show();
	}
	
	


}
