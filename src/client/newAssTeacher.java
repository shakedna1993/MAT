package client;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import entity.Assigenment;
import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import thred.IndexList;
import thred.MyThread;

public class newAssTeacher implements Initializable {
	

	@FXML
	Button sendBtn;
	@FXML
	Button backBtn;
	
	@FXML
	TextArea showWarningField;
	
	@FXML
	TextField AssingmentidTEXT;
	@FXML
	TextField AssingmentnameTEXT;
	@FXML
	TextField CourseidTEXT;
	@FXML
	TextField ClassidTEXT;
	@FXML
	TextField FileidTEXT;
	@FXML
	TextField DaySubmissionTEXT;
	@FXML
	TextField month;
	@FXML
	TextField yearText;
	@FXML
	javafx.scene.control.Label detlabel;
	
	@FXML
	javafx.scene.control.Label label;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public void newAss(){
		int flag = 0;
	User s = new User();
	s =  (User) MsgFromServer.getDataListByIndex(IndexList.LOGIN);
	//	Date date = new Date(0, 0, 0);
		Assigenment ass = new Assigenment();
		ArrayList<Assigenment> a1 = new ArrayList<Assigenment>();
		
		ass.setAssId(AssingmentidTEXT.getText());
		ass.setAssname(AssingmentnameTEXT.getText());
		ass.setFileid(FileidTEXT.getText());
		//date = "" + yearText.getText() + "-" + month.getText()+"-" + DaySubmissionTEXT.getText()+"";
	/*	date.setYear(Integer.parseInt(yearText.getText()));
		date.setMonth(Integer.parseInt(month.getText()));
		date.setDate(Integer.parseInt(DaySubmissionTEXT.getText()));  
		ass.setDueDate(date);  */
		ass.setCourseid(CourseidTEXT.getText());
		
		if(ass.getAssId().length()==0 || ass.getAssname().length()==0 || ass.getFileid().length()==0 || ass.getCourseid().length()==0){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty Fields");
			alert.setHeaderText(null);
			alert.setContentText("Please enter all deatils");

			alert.show();
			return;
	}
	
		MyThread C = new MyThread(RequestType.allAssForTeacher, IndexList.allAssForTeacher, s.getId());
		C.start();
		try {
			C.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		a1 = (ArrayList<Assigenment>) MsgFromServer.getDataListByIndex(IndexList.allAssForTeacher);
		
		
		for (int i = 0; i< a1.size(); i++){
			if(ass.getCourseid().equals(a1.get(i).getCourseid())){
				flag = 1;
			}
		}
		
		if (flag ==0){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty Fields");
			alert.setHeaderText(null);
			alert.setContentText("this course id is not exist for you");
			alert.show();
			return;
		}
	
		flag = 0;
		for (int i = 0; i< a1.size(); i++){
			if(ass.getCourseid().equals(a1.get(i).getCourseid()) && ass.getAssId().equals(a1.get(i).getAssId())){
				flag = 1;
			}
		}
		
		if (flag ==1){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty Fields");
			alert.setHeaderText(null);
			alert.setContentText("this Assigenment id allready exist for this course");
			alert.show();
			return;
		}
		
		
		MyThread B = new MyThread(RequestType.insertNewAss, IndexList.insertNewAss, ass);
		B.start();
		try {
			B.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
	}
	
	

}
