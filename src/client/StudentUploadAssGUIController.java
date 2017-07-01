package client;

import java.io.File;
//import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import entity.User;
import entity.Studentass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import thred.IndexList;
import thred.MyThread;

public class StudentUploadAssGUIController implements Initializable {
	@FXML
	private Button ChooseFile;
	@FXML
	private Button Upload_Ass;
	@FXML
	private Button Back;
	@FXML
	private Button LogOut;
	@FXML
	private Label Hello;
	@FXML
	private Label stuName;
	@FXML
	private Label filename;
	@FXML
	private Label lab1;

	JFileChooser chooser = new JFileChooser();
	private User s = new User();
	private static String crs, fname;
	private static int ass;
	private static Date duedate;

	public void initialize(URL location, ResourceBundle resources) {
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		stuName.setText(s.getName());
	}

	@FXML
	private void OpenFolder() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF & DOC & DOCX & XSLS", "pdf", "doc", "xsls",
				"docx");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			filename.setText(chooser.getSelectedFile().getName());
			fname = chooser.getSelectedFile().getName();
			Upload_Ass.setDisable(false);
		}
	}

	@FXML
	private void UploadAss() {
		try {
			File f = new File(chooser.getSelectedFile().getPath());
			Studentass SA = new Studentass(ass, s.getId(), crs, "", duedate, f, fname);
			Path path = Paths.get(f.getAbsolutePath());
			try {
				SA.setData(Files.readAllBytes(path));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			MyThread C = new MyThread(RequestType.UploadFile, IndexList.UploadFile, SA);
			try {
				C.start();
				C.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if ((int) MsgFromServer.getDataListByIndex(IndexList.UploadFile) == 1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Late Submission");
				alert.setHeaderText(null);
				alert.setContentText("File uploaded successfully, but note that you are late to submit");
				alert.show();
				connectionmain.ShowAssOptions();
				return;
			} else if ((int) MsgFromServer.getDataListByIndex(IndexList.UploadFile) == 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText(null);
				alert.setContentText("File uploaded successfull");
				alert.show();
				connectionmain.ShowAssOptions();
				return;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Fail");
				alert.setHeaderText(null);
				alert.setContentText("Upload Failed");
				alert.show();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initVariable(String crs2, int tmp, Date tmp1) {
		crs = crs2;
		ass = tmp;
		duedate = tmp1;

	}

	@FXML
	private void backButton(ActionEvent event) throws Exception {
		connectionmain.ShowAssOptions();
	}

	@FXML
	private void clsUploadAss() {
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
