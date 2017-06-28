package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Assigenment;
import entity.Course;
import entity.Requests;
import entity.Student;
import entity.Teacher;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;
import thred.IndexList;
import thred.MyThread;

public class OpenRequestGUIController implements Initializable {

	private Requests selectedRequest;
	private User selectedUser = null;
	private Course selectedCourse = null;
	@FXML
	private Label classIDLabel;
	@FXML
	private Label typeLabel;
	@FXML
	private Label userLabel;
	@FXML
	private Label courseLabel;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Label replaceWithLabel;
	@FXML
	private Label statusLabel;
	@FXML
	private Label classLabel;
	
	@FXML
	private ComboBox replaceCombo;
	
	
	@FXML
	private TextField reqTypeField;
	@FXML
	private TextField userField;
	@FXML
	private TextField courseField;
	@FXML
	private TextField statusField;
	@FXML
	private TextField classField;
	
	@FXML
	private TextArea descriptionArea;
	
	@FXML
	private Button back_button;
	@FXML
	private Button process_button;
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectedRequest = SecMainGUIController.getSelectedRequest();
		SecMainGUIController.setSelectedRequest(null);
		MyThread a = new MyThread(RequestType.getRequestByID, IndexList.getRequestByID, selectedRequest.getReqId());
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		selectedRequest = (Requests) (MsgFromServer.getDataListByIndex(IndexList.getRequestByID));
		populateFields(selectedRequest.getReqType());
	}
	
	private void populateFields(int reqType) {
		MyThread a = new MyThread(RequestType.getCourseByID, IndexList.getCourseByID, selectedRequest.getCourseId());
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		selectedCourse = (Course) (MsgFromServer.getDataListByIndex(IndexList.getCourseByID));
		a = new MyThread(RequestType.getUserByID, IndexList.getUserByID, selectedRequest.getUserId());
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		selectedUser = (User) (MsgFromServer.getDataListByIndex(IndexList.getUserByID));
		
		reqTypeField.setText(selectedRequest.getReqTypeString());
		if (selectedUser == null) return;
		userField.setText(selectedUser.getId()+ " - " + selectedUser.getName());
		courseField.setText(selectedCourse.getCourseId() + " - " + selectedCourse.getName());;
		statusField.setText(selectedRequest.getStatusString());
		descriptionArea.setText(selectedRequest.getRequestDescription());
		if (selectedRequest.getStatus() == 0) process_button.setDisable(true);
		else process_button.setDisable(false);
		if (reqType == 3){
			a = new MyThread(RequestType.getTeachersForCourse, IndexList.getTeachersForCourse, selectedRequest.getCourseId());
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			ArrayList<Teacher> teacherList = (ArrayList<Teacher>)(MsgFromServer.getDataListByIndex(IndexList.getTeachersForCourse));
			userLabel.setText("Teacher:");
			replaceWithLabel.setVisible(true);
			replaceCombo.setVisible(true);
			classLabel.setVisible(true);
			classField.setVisible(true);
			
			a = new MyThread(RequestType.getClassByID, IndexList.getClassByID, selectedRequest.getClassId());
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			entity.Class c = (entity.Class)(MsgFromServer.getDataListByIndex(IndexList.getClassByID));
			if (c == null) return;
			classField.setText(c.getName());
			ArrayList<String> replaceStrList = new ArrayList<String>();
			if (teacherList==null) return;
			for (int i=0; i<teacherList.size(); i++){
				if (teacherList.get(i).getId().equals(selectedUser.getId())) {
					teacherList.remove(i);
					i--;
				}
				else replaceStrList.add(teacherList.get(i).getId() + " - " + teacherList.get(i).getName());
			}
			replaceCombo.setItems(FXCollections.observableArrayList(replaceStrList));
		}
	}
	
	@FXML
	private void processButton(ActionEvent event) throws Exception {
		if (selectedRequest.getStatus() == 1){
			if (selectedRequest.getReqType() == 1){
				// par = {studentid,semid,courseid,coursename}
				String par[] = {selectedRequest.getUserId(), null, selectedRequest.getCourseId(), selectedCourse.getName()};
				MyThread a = new MyThread(RequestType.AddStudentToCourse, IndexList.AddStudentToCourse, par);
				a.start();
				try {
					a.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if ((boolean)MsgFromServer.getDataListByIndex(IndexList.AddStudentToCourse)) {
					deActivateRequest(selectedRequest);
				}
			} else if (selectedRequest.getReqType() == 2){
				// par = {studid,courseid,semid}
				String par[] = {selectedRequest.getUserId(), selectedRequest.getCourseId(), null};
				MyThread a = new MyThread(RequestType.RemoveStudentFromCourse, IndexList.RemoveStudentFromCourse, par);
				a.start();
				try {
					a.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if ((boolean)MsgFromServer.getDataListByIndex(IndexList.RemoveStudentFromCourse)) {
					deActivateRequest(selectedRequest);
				}
			}
			else {
				// par = {courseid,classid,oldTeacherid,semid,newTeacherID}
				String str = (String)replaceCombo.getValue();
				if (str == null) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Empty Fields");
					alert.setHeaderText(null);
					alert.setContentText("Please fill all fields");
					alert.show();
					return;
				}
				String newTeacherID = str.substring(0,str.indexOf('-')-1);
				
				String par[] = {selectedRequest.getCourseId(), selectedRequest.getClassId(), selectedRequest.getUserId(), null, newTeacherID};
				MyThread a = new MyThread(RequestType.ChangeTeacherAppointment, IndexList.ChangeTeacherAppointment, par);
				a.start();
				try {
					a.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if ((boolean)MsgFromServer.getDataListByIndex(IndexList.ChangeTeacherAppointment)) {
					deActivateRequest(selectedRequest);
				}
			}
		}
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION, "The request was rejected by the manager.\nProcessing it will close the request.\nAre you sure?", ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				deActivateRequest(selectedRequest);
			}
		}
	}
	private void deActivateRequest(Requests r) throws IOException {
		MyThread a = new MyThread(RequestType.DeActivateRequest, IndexList.DeActivateRequest, r.getReqId());
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if ((boolean)MsgFromServer.getDataListByIndex(IndexList.DeActivateRequest)){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Request processed successfully!");
			alert.setHeaderText(null);
			alert.setContentText("Request processed successfully!");
			alert.show();
			connectionmain.showSecretaryMain();
		}
	}

	@FXML
	private void backButton(ActionEvent event) throws Exception {
		Stage primaryStage = connectionmain.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/client/SecretaryMainGUI.fxml"));
		Pane root = loader.load();
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("M.A.T- Secretary Connection");
		primaryStage.show();
	}
}
