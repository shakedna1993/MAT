package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Course;
import entity.Requests;
import entity.Student;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;


/**
 *This class presents the requests to the school principal
 */
public class RequestsController implements Initializable{

	public static ClientConsole cli;
	public static Stage primaryStage;

	@FXML
	private Label Hello;
	@FXML
	private ImageView Logo;
	@FXML
	private Label textArea;
	@FXML
	private Button Approval;
	@FXML
	private Button Rejection;
	@FXML
	private Button Back;
	@FXML
	private Button LogOut;
	@FXML
	private javafx.scene.control.Label UserName;

	private Requests chooseRequests=new Requests();
	


	
	/**
	 * this method carried out First thing. It is designed to initialize the information in the window 
	 * 
	 */
	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		UserName.setText(s.getName());
		RequestInfoToManeger();
	}
	
	/**
	*	When the request confirmation key is pressed the application is approved and this functionality follows
	*/
	@FXML
	private void ApprovalRequest() {
		MyThread a = new MyThread(RequestType.ApprovalRequest, IndexList.ApprovalRequest, chooseRequests.getReqId());
		a.start();
		try {a.join();}
		catch (InterruptedException e1) {e1.printStackTrace();}
		boolean b = (boolean) MsgFromServer.getDataListByIndex(IndexList.ApprovalRequest);
		if (b){
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Success!");
	        alert.setHeaderText(null);
	        alert.setContentText("The Request is approval successfully");
	        alert.show();
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText(null);
			alert.setContentText("Not completed successfully");
			alert.show();
		}
	}
	
	
	/**
	*	When the prompt rejection key is pressed the application is not approved and this functionality follows
	*/
	@FXML
	private void RejectRequest() {
			MyThread a = new MyThread(RequestType.RejectRequest, IndexList.RejectRequest, chooseRequests.getReqId());
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			boolean b = (boolean) MsgFromServer.getDataListByIndex(IndexList.RejectRequest);
			if (b){
		        Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Success!");
		        alert.setHeaderText(null);
		        alert.setContentText("The Request is Rejected");
		        alert.show();
			}
			else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText(null);
				alert.setContentText("Not completed successfully");
				alert.show();
			}
	}
	
	
	/**
	 * This method goes back to the last window that been shown
	 */
	@FXML
	private void backButton(ActionEvent event) throws Exception {
		connectionmain.showManagerMain();
	}
	
	/**
	*	This method shows all the information that a particular request has to the manager
	*/
	private void RequestInfoToManeger()
	{
		chooseRequests=ManMainGUIController.getChooseRequests();
		
		MyThread b = new MyThread(RequestType.createCourseEntity, IndexList.createCourseEntity,chooseRequests.getCourseId() );
		b.start();
		try {b.join();} 
		catch (InterruptedException e1) {e1.printStackTrace();}
		Course Cou=(Course) (MsgFromServer.getDataListByIndex(IndexList.createCourseEntity));
		
		MyThread c = new MyThread(RequestType.getUserDetailsById, IndexList.getUserDetailsById,chooseRequests.getUserId());
		c.start();
		try {c.join();}
		catch (InterruptedException e1) {e1.printStackTrace();}
		User s2 = (User) MsgFromServer.getDataListByIndex(IndexList.getUserDetailsById);
	
		if(chooseRequests.getReqType()==3)
		{
			textArea.setText("Teacher name:\t"+s2.getName()+"\n"
					+ "Id:\t"+chooseRequests.getUserId()+"\n"
					+ "Course:\t"+Cou.getName()+"Course code:\t"+chooseRequests.getCourseId()+"\n\n\n"
					+ "description:\t"+chooseRequests.getRequestDescription());
		}
		else
		{
			MyThread a = new MyThread(RequestType.StudentDetails, IndexList.StudentDetails,s2);
			a.start();
			try {	a.join();} 
			catch (InterruptedException e1) {e1.printStackTrace();}
			Student stu=(Student) (MsgFromServer.getDataListByIndex(IndexList.StudentDetails));
			
			textArea.setText("student name:\t\t\t"+s2.getName()+"\n"
					+"Id:\t\t\t\t"+chooseRequests.getUserId()+"\n"
					+"Class:\t\t\t"+stu.getClassid()+"\n"
					+"Course:\t\t\t"+Cou.getName()+"\n"
					+"Course code:\t\t\t"+chooseRequests.getCourseId()+"\n"
					+"average:\t\t\t"+stu.getAvg()+"\n\n\n"
					+"description:\n"+chooseRequests.getRequestDescription());
		}
	}
	
	/**
	*	When the LOGOUT is pressed, this function works to make an orderly LOGOUT
	*/
	@FXML
	public void clsRequestList() {
		MyThread a = new MyThread(RequestType.LOGOUT, IndexList.LOGOUT,
				MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		a.start();
		try {a.join();} 
		catch (InterruptedException e1) {e1.printStackTrace();}
		try {connectionmain.showLogin();}
		catch (IOException e) {e.printStackTrace();}
	}
}
