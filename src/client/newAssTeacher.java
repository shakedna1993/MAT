package client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import entity.Assigenment;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.Assignment;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;
/**
 * This class is the controller for post a new assignments.
 */
public class newAssTeacher implements Initializable {

	@FXML
	private Button ChooseFile;
	@FXML
	private Button sendBtn;
	@FXML
	private Button backBtn;
	@FXML
	private Button LogOut;

	@FXML
	private TextField description;
	@FXML
	private javafx.scene.control.Label filename;

	@FXML
	private TextField AssingmentnameTEXT;
	@FXML
	private TextField CourseidTEXT;
	@FXML
	private TextField ClassidTEXT;
	@FXML
	private javafx.scene.control.Label detlabel;

	@FXML
	private javafx.scene.control.Label tecName;
	@FXML
	private DatePicker dp;
	JFileChooser chooser = new JFileChooser();

	
	/**
	 * initialize-initialize the student name.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		tecName.setText(s.getName());
	}

	
	/**
	 * This method teacher choose file to send
	 */
	@FXML
	public void OpenFolder() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF & DOC & DOCX & XSLS", "pdf", "doc", "xsls",
				"txt", "png", "docx");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			filename.setText(chooser.getSelectedFile().getName());
			sendBtn.setDisable(false);
		}
	}

	
	/**
	 * This method teacher fill all details and post the new assignment
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void newAss() throws ParseException {
		int flag = 0;
		File f = chooser.getSelectedFile();
		User s = new User();
		s = (User) MsgFromServer.getDataListByIndex(IndexList.LOGIN);
	
		Assigenment ass = new Assigenment();
		ArrayList<String> a1 = new ArrayList<String>();
		ArrayList<Assignment> a2 = new ArrayList<Assignment>();

		ass.setAssname(AssingmentnameTEXT.getText());
		ass.setUserId(s.getId());
		ass.setCourseid(CourseidTEXT.getText());
		ass.setPath(f.getAbsolutePath());
		ass.setFileid(filename.getText());
		
		Path path = Paths.get(ass.getPath());
		try {
			ass.setData(Files.readAllBytes(path));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		LocalDate ld = dp.getValue();
		
		
		
		if (ass.getAssname().length() == 0 || ass.getCourseid().length() == 0 || ld==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty fields");
			alert.setHeaderText(null);
			alert.setContentText("Please enter all details");
			alert.show();
			return;
		}
		
		Date date = java.sql.Date.valueOf(ld);
		ass.setDueDate(date);

		MyThread C = new MyThread(RequestType.allCourseForTeacher, IndexList.allCourseForTeacher, s.getId());
		C.start();
		try {
			C.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		a1 = (ArrayList<String>) MsgFromServer.getDataListByIndex(IndexList.allCourseForTeacher);

		MyThread d = new MyThread(RequestType.allAssForTeacher, IndexList.allAssForTeacher, a1);
		d.start();
		try {
			d.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		a2 = (ArrayList<Assignment>) MsgFromServer.getDataListByIndex(IndexList.allAssForTeacher);

		for (int i = 0; i < a1.size(); i++) {
			if (ass.getCourseid().equals(a1.get(i))) {
				flag = 1;
			}
		}

		if (flag == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Post New Assignment");
			alert.setHeaderText(null);
			alert.setContentText("This course does not exist for you");
			alert.show();
			return;
		}

		flag = 0;
		for (int i = 0; i < a2.size(); i++) {
			if (ass.getCourseid().equals(((Assigenment) a2.get(i)).getCourseid())
					&& ass.getAssname().equals(((Assigenment) a2.get(i)).getAssname())) {
				flag = 1;
			}
		}

		if (flag == 1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(" New Assignment");
			alert.setHeaderText(null);
			alert.setContentText("This Assignment name already exists for this course");
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

		if ((int) MsgFromServer.getDataListByIndex(IndexList.insertNewAss) == 1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(" New Assignment");
			alert.setHeaderText(null);
			alert.setContentText("Task # " + ass.getAssname() + " was successfully added");
			alert.show();
			return;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(" New Assignment");
			alert.setHeaderText(null);
			alert.setContentText("Something went wrong, please try again");
			alert.show();
			return;
		}

	}
	
	public static int newAss(Assigenment ass){
		MyThread B = new MyThread(RequestType.insertNewAss, IndexList.insertNewAss, ass);
		B.start();
		try {
			B.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return(int) MsgFromServer.getDataListByIndex(IndexList.insertNewAss);
	}
	/**
	 * Back to the last window  
	 */
	@FXML
	private void backButton(ActionEvent event) throws Exception {
		Stage primaryStage = connectionmain.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/TeacherMainGUI.fxml"));
		Pane root = loader.load();
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("M.A.T- Secretary Connection");
		primaryStage.show();
	}

}
