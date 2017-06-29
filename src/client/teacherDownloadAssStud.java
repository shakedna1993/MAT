package client;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import entity.Assigenment;
import entity.Course;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;

public class teacherDownloadAssStud implements Initializable {

	@FXML
	Button chooseFileUP;
	@FXML
	Button evaluationBTN;
	@FXML
	Button gradefileBTN;
	@FXML
	javafx.scene.control.Label courseTxt;
	@FXML
	javafx.scene.control.Label assTxt;
	@FXML
	Button downloadBtn;
	@FXML
	Button chooseFileBTN;
	@FXML
	Button backBTN;
	@FXML
	Button downAssStud;
	@FXML
	javafx.scene.control.Label filename;
	Course c;
	@FXML
	javafx.scene.control.Label fileDir;
	@FXML
	TableView<Assigenment> tabelList = new TableView<Assigenment>();
	  JFileChooser chooser;// = new JFileChooser();
	
		JFileChooser chooser2 = new JFileChooser();
	  private ObservableList<Assigenment> data; 
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
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
			
			
			Assigenment	assForListStudAss = new Assigenment();
			assForListStudAss.setAssId(TchMainGUIController.assId);
			assForListStudAss.setCourseid(c.getCourseId());
			assForListStudAss.setTeacherid(	TchMainGUIController.s.getId());
			assForListStudAss.setTechername(TchMainGUIController.s.getName());
			MyThread Thread = new MyThread(RequestType.listOfStudentForAssCourse, IndexList.listOfStudentForAssCourse, assForListStudAss);
			Thread.start();
			try {
				Thread.join();
			} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			
			data = FXCollections.observableArrayList();
			ArrayList<Assigenment> StudAssList =  (ArrayList<Assigenment>) MsgFromServer.getDataListByIndex(IndexList.listOfStudentForAssCourse);
			for(int i = 0; i<StudAssList.size();i++){
				data.add(StudAssList.get(i));
			}
				TableColumn<Assigenment, String> c1 = new TableColumn<Assigenment, String>("Student ID");
				c1.setCellValueFactory(new PropertyValueFactory<>("UserId"));
				TableColumn<Assigenment, String> c2 = new TableColumn<Assigenment, String>("Student Name");
				c2.setCellValueFactory(new PropertyValueFactory<>("UserName"));
				TableColumn<Assigenment, String> c3 = new TableColumn<Assigenment, String>("Late Submissiom");
				c3.setCellValueFactory(new PropertyValueFactory<>("late"));
				
				tabelList.getColumns().addAll(c1,c2,c3);
				tabelList.setItems(data);
			
			
			
	}
	
	
	public void downloadOneStudAss(){
		if(tabelList.getSelectionModel().getSelectedItem()==null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Download assignments");
			alert.setHeaderText(null);
			alert.setContentText("choose student");
			alert.show();
			return;
		}
		else{
			Assigenment choosenStud = tabelList.getSelectionModel().getSelectedItem();
			
			choosenStud.setAssId(TchMainGUIController.assId);
		//	choosenStud.setFileid(fileDir.getText());
			MyThread Thread = new MyThread(RequestType.downloadOneFileStud, IndexList.downloadOneFileStud, choosenStud);
			Thread.start();
			try {
				Thread.join();
			} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			
			int	flag =   (int) MsgFromServer.getDataListByIndex(  IndexList.downloadOneFileStud);
			if (flag==1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Download assignments");
				alert.setHeaderText(null);
				alert.setContentText("ALL assigenments class are download");
				alert.show();
				return;
			}
			else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Download assignments");
				alert.setHeaderText(null);
				alert.setContentText("Someting wrong");
				alert.show();
				return;
			}
		}
	}
	

	
	public void downloadAss() {
	
	Assigenment ass = new Assigenment();
	ass.setAssId(TchMainGUIController.assId);
	ass.setCoursename(courseTxt.getText());
	ass.setCourseid(c.getCourseId());
	User s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
	ass.setUserId(s.getId());
//	ass.setFileid(fileDir.getText());
	MyThread a = new MyThread(RequestType.downloadStudentAssForTeacher, IndexList.downloadStudentAssForTeacher, ass);
	a.start();
	try {
			a.join();
	} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	
	int	flag =   (int) MsgFromServer.getDataListByIndex(  IndexList.downloadStudentAssForTeacher);
	if (flag==1){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Download assignments");
		alert.setHeaderText(null);
		alert.setContentText("ALL assigenments class are download");
		alert.show();
		return;
	}
	else{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Download assignments");
		alert.setHeaderText(null);
		alert.setContentText("Someting wrong");
		alert.show();
		return;
	}
	
	}
	
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

	
	
	public void OpenFolder() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF & DOC & DOCX & XSLS", "pdf", "doc", "xsls" , "txt", "png",
				"docx");
		chooser2.setFileFilter(filter);
		int returnVal = chooser2.showOpenDialog(null);
	if (returnVal == JFileChooser.APPROVE_OPTION) {
			//filename.setText(chooser2.getSelectedFile().getName());
		gradefileBTN.setDisable(false);
		evaluationBTN.setDisable(false);
		}
	}
	
	
	public void upEvaluation() {
		if(tabelList.getSelectionModel().getSelectedItem()==null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Download assignments");
			alert.setHeaderText(null);
			alert.setContentText("choose student");
			alert.show();
			return;
		}
		else{
			Assigenment choosenStud = tabelList.getSelectionModel().getSelectedItem();
			choosenStud.setAssId(TchMainGUIController.assId);
			File f = chooser2.getSelectedFile();
			choosenStud.setPath(f.getAbsolutePath());
			choosenStud.setFileid(chooser2.getSelectedFile().getName());
			MyThread a = new MyThread(RequestType.uploadEvaluation, IndexList.uploadEvaluation, choosenStud);
			a.start();
			try {
					a.join();
			} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			
			
			int	flag =   (int) MsgFromServer.getDataListByIndex(  IndexList.uploadEvaluation);
			if (flag==1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("upload evaluatin");
				alert.setHeaderText(null);
				alert.setContentText("upload successful");
				alert.show();
				return;
			}
			else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Download assignments");
				alert.setHeaderText(null);
				alert.setContentText("Someting wrong");
				alert.show();
				return;
			}
			
			
	}
}
	
	
	
	public void upgradeFile() {
		if(tabelList.getSelectionModel().getSelectedItem()==null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("upload grade file");
			alert.setHeaderText(null);
			alert.setContentText("choose student");
			alert.show();
			return;
		}
		else{
			Assigenment choosenStud = tabelList.getSelectionModel().getSelectedItem();
			choosenStud.setAssId(TchMainGUIController.assId);
			File f = chooser2.getSelectedFile();
			choosenStud.setPath(f.getAbsolutePath());
			choosenStud.setFileid(chooser2.getSelectedFile().getName());
			MyThread a = new MyThread(RequestType.uploadGradeFile, IndexList.uploadGradeFile, choosenStud);
			a.start();
			try {
					a.join();
			} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			
			
			int	flag =   (int) MsgFromServer.getDataListByIndex(  IndexList.uploadGradeFile);
			if (flag==1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("upload grade file");
				alert.setHeaderText(null);
				alert.setContentText("upload successful");
				alert.show();
				return;
			}
			else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("upload grade file");
				alert.setHeaderText(null);
				alert.setContentText("Someting wrong");
				alert.show();
				return;
			}
			
			
	}
}
	
	
}
