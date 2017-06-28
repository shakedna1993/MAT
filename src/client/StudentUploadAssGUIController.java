package client;

import java.awt.Desktop;
import java.io.File;
//import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import entity.User;
import entity.Assigenment;
import entity.Course;
import entity.Studentass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import thred.IndexList;
import thred.MyThread;

public class StudentUploadAssGUIController implements Initializable {
	@FXML
	Button ChooseFile;
	@FXML
	Button Upload_Ass;
	@FXML
	Button Back;
	@FXML
	Button LogOut;
	@FXML
	Label Hello;
	@FXML
	Label stuName;
	@FXML
	Label filename;
	@FXML
	Label lab1;

	JFileChooser chooser = new JFileChooser();
	User s = new User();
	static String crs, fname;
	static int ass;
	static Date duedate;

	
	
	public void initialize(URL location, ResourceBundle resources) {
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		stuName.setText(s.getName());
	}

	@FXML
	public void OpenFolder() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF & DOC & DOCX & XSLS", "pdf", "doc", "xsls",
				"docx");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			filename.setText(chooser.getSelectedFile().getName());
			fname=chooser.getSelectedFile().getName();
			Upload_Ass.setDisable(false);
		}
	}

	@SuppressWarnings("deprecation")
	@FXML
	public void UploadAss(){
		try{
			File f =new File(chooser.getSelectedFile().getPath());
			Studentass SA=new Studentass(ass, s.getId(), crs, "", duedate, f, fname);
			MyThread C = new MyThread(RequestType.UploadFile, IndexList.UploadFile,SA) ;
			try {
				C.start();
				C.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if ((int)MsgFromServer.getDataListByIndex(IndexList.UploadFile) == 1)
			{	
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Late Submission");
				alert.setHeaderText(null);
				alert.setContentText("File uploaded successfully, but note that you are late to submit");
				alert.show();
				connectionmain.ShowAssOptions();
				return;
			}
			else if((int)MsgFromServer.getDataListByIndex(IndexList.UploadFile) == 0)
			{	
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText(null);
				alert.setContentText("File uploaded successfull");
				alert.show();
				connectionmain.ShowAssOptions();
				return;
			}
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Fail");
				alert.setHeaderText(null);
				alert.setContentText("Upload Failed");
				alert.show();
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void initVariable(String crs2, int tmp, Date tmp1){
		crs=crs2;
		ass=tmp;
		duedate=tmp1;
		
	}

	@FXML
	private void backButton(ActionEvent event) throws Exception {
		connectionmain.ShowAssOptions();
	}

	@FXML
	public void clsUploadAss() {
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



}
