package client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import entity.Assigenment;
import entity.Course;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;
/**
 * This class is the controller for the Teacher edit exist assignment screen GUI.
 */
public class teacherControlerEditAss implements Initializable{
	
	@FXML
	Label Hello;
	@FXML
	javafx.scene.control.Label TeacherName;
	@FXML
	javafx.scene.control.Label courseTxt;
	@FXML
	javafx.scene.control.Label assTxt;
	@FXML
	TextField assNameTxt;
	@FXML
	TextField fileidTxt;
	@FXML
	javafx.scene.control.Label filename;
	@FXML
	JFileChooser chooser = new JFileChooser();
	@FXML
	Button backBtn;
	@FXML
	Button editBtn;
	@FXML
	Button chooseFileBTN;
	@FXML
	javafx.scene.control.Label fname;
	@FXML
	private DatePicker dp;
	Course c;
	
	

	/**
	 * initialize-initialize the teacher name, course name and assignment name.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		User s =new User();
		s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		TeacherName.setText(s.getName());
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
	
	
	/**
	 * This method teacher choose file to send
	 */
	@FXML
	public void OpenFolder() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF & DOC & DOCX & XSLS", "pdf", "doc", "xsls" , "txt", "png",
				"docx");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			fname.setText(chooser.getSelectedFile().getName());
		}
	}
	
	
	
	
	/**
	 * This method teacher choose what to edit for assignment (name, date or file)
	 */
	public void editAss() throws ParseException{	
		Assigenment ass = new Assigenment();
		ass.setAssId(TchMainGUIController.assId);
		ass.setCoursename(courseTxt.getText());
		ass.setCourseid(c.getCourseId());
		File f = chooser.getSelectedFile();
		
		
		MyThread a = new MyThread(RequestType.assCourseTeach, IndexList.assCourseTeach,ass);
		a.start();
		try {
				a.join();
		} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		
		Assigenment newAss = (Assigenment) MsgFromServer.getDataListByIndex(IndexList.assCourseTeach);
		LocalDate ld = dp.getValue();
		
		if(fname.getText().length()==0&assNameTxt.getText().length()==0&&ld==null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Edit Assignment");
			alert.setHeaderText(null);
			alert.setContentText("Fileds Empty ");
			alert.show();
			return;
		}
		
		else{	
		
		if(fname.getText().length()!=0){
			
			newAss.setPath(f.getAbsolutePath());
			newAss.setFileid(fname.getText());
		}
		
		if(assNameTxt.getText().length()!=0){
			newAss.setAssname(assNameTxt.getText());
		}
		
	
		if(ld!=null){
			Date date = java.sql.Date.valueOf(ld);
			newAss.setDueDate(date);
		}
		if(fname.getText().length()!=0){
		Path path = Paths.get(f.getAbsolutePath());
		try {
			newAss.setData(Files.readAllBytes(path));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		}
		MyThread Q = new MyThread(RequestType.UpdateAss, IndexList.UpdateAss, newAss);
		Q.start();
		try {
				Q.join();
		} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		
		if((int) MsgFromServer.getDataListByIndex(IndexList.UpdateAss)==1){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Edit Assignment");
			alert.setHeaderText(null);
			alert.setContentText("Task # "+newAss.getAssname()+" Successfully edited ");
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
} 
	
	/**
	 * Back to the last window  
	 */
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
