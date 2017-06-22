package client;

import java.awt.Desktop;
import java.io.File;
//import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import entity.User;
import entity.FileEnt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	TextField description;
	@FXML
	Label filename;
	
	JFileChooser chooser = new JFileChooser();
	public void initialize(URL location, ResourceBundle resources) {
		User s =new User();
		s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		stuName.setText(s.getName());
	}
	
	@FXML
	public void OpenFolder() {
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	             "PDF & DOC & DOCX & XSLS", "pdf", "doc","xsls","docx");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	filename.setText(chooser.getSelectedFile().getName());
	        }
     }
	@FXML
	public void UploadAss(){
		File f;
		try{
			f = new File(chooser.getSelectedFile().getPath());
		}catch(Exception e){
			// Do something
			return;
		}
		MyThread C = new MyThread(RequestType.UploadFile, IndexList.UploadFile,f) ;
		try {
			C.start();
			C.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}	
	
	

	
	
	
	
	
	
	
	@FXML
	private void backButton(ActionEvent event) throws Exception{
		connectionmain.ShowAssOptions();
	}
	
	@FXML
	public void clsUploadAss() {
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
