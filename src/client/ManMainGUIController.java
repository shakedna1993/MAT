package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import entity.Course;
import entity.Requests;
import entity.Student;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import thred.IndexList;
import thred.MyThread;



/**
 * d
 * 
 *	
 */
public class ManMainGUIController implements Initializable {

	public static ClientConsole cli;
	public static Stage primaryStage;
	private ArrayList<Requests> req=new ArrayList<Requests>();
	
	
	@FXML
	private Label Reqs;
	@FXML
	private Label BlockLabel;
	@FXML
	private Label Ins_PID;
	@FXML
	private javafx.scene.control.Label ManName;
	@FXML
	private javafx.scene.control.Label numReq;
	@FXML
	private Button CheckReq;
	@FXML
	private Button OpenReq;
	@FXML
	private Button GenRep;
	@FXML
	private Button UnBlock;
	@FXML
	private Button Block;
	@FXML
	private Button Back;
	@FXML
	private Button LogOut;
	@FXML
	private ListView<String> list = new ListView<String>();
	@FXML
	private TableView<Student> table = new TableView<>();
	
	private static Requests chooseRequests=new Requests();
	private ObservableList<Student> data;
	private String[] type={"Register Student to Course","Remove Student from Course","Change Teacher Appointment"};

	
	
	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		ManName.setText(s.getName());
		studentParentInfo();
		numReq.setText(Integer.toString(re()));
	}

	
	/**
	 * d
	 * 
	 *
	 */
	private void studentParentInfo() {
		MyThread a = new MyThread(RequestType.StudentsList, IndexList.StudentsList);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		@SuppressWarnings("unchecked")
		ArrayList<Student> b = (ArrayList<Student>) MsgFromServer.getDataListByIndex(IndexList.StudentsList);
		data = FXCollections.observableArrayList(b);

		TableColumn<Student, String> c1 = new TableColumn<>("Student Id");
		c1.setCellValueFactory(new PropertyValueFactory<>("Id"));
		TableColumn<Student, String> c2 = new TableColumn<>("Parent");
		c2.setCellValueFactory(new PropertyValueFactory<>("ParentId"));
		TableColumn<Student, String> c3 = new TableColumn<>("Class");
		c3.setCellValueFactory(new PropertyValueFactory<>("classid"));
		TableColumn<Student, String> c4 = new TableColumn<>("avg");
		c4.setCellValueFactory(new PropertyValueFactory<>("avg"));

		table.getColumns().addAll(c1, c2, c3, c4);
		table.setItems(data);
	}

	
	
	
	@FXML
	private void BlockParent() {
		String Pid = table.getSelectionModel().getSelectedItem().getParentId();
		MyThread a = new MyThread(RequestType.BlockParent, IndexList.BlockParent, Pid);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		boolean b = (boolean) MsgFromServer.getDataListByIndex(IndexList.BlockParent);
		
		if (b){
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Success!");
	        alert.setHeaderText(null);
	        alert.setContentText("The selected parent is block successfully");
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
	
	
	
	
	
	@FXML
	private void unBlockParent() {
		String Pid = table.getSelectionModel().getSelectedItem().getParentId();
		MyThread a = new MyThread(RequestType.unBlockParent, IndexList.unBlockParent, Pid);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		boolean b = (boolean) MsgFromServer.getDataListByIndex(IndexList.unBlockParent);
		if (b){
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Success!");
	        alert.setHeaderText(null);
	        alert.setContentText("Successfully unblocked");
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
	
	

	@FXML
	private int re() {
		int count=0;
		MyThread a = new MyThread(RequestType.RequestsInfo, IndexList.RequestsInfo);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		req= (ArrayList<Requests>) MsgFromServer.getDataListByIndex(IndexList.RequestsInfo);
		ArrayList<String> S=new ArrayList<String>();
		for (int i = 0; i < req.size(); i++)
		{
			if(req.get(i).getStatus()==0)
			{
				String typeString=new String();

				typeString=type[req.get(i).getReqType()-1];

				MyThread b = new MyThread(RequestType.getUserDetailsById, IndexList.getUserDetailsById,req.get(i).getUserId());
				b.start();
				try {
					b.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				User u = (User) MsgFromServer.getDataListByIndex(IndexList.getUserDetailsById);

				typeString=typeString+" - "+u.getName();

				S.add(typeString);
				
				count++;
			}
		}

		list.setItems((ObservableList<String>) FXCollections.observableArrayList(S));
		return count;
	}
	
	
	
	
	
	
	@FXML
	private void chooseReq() {
		String Selected=list.getSelectionModel().getSelectedItem();
		int i;
		boolean flag=false;
		for (i = 0; i < req.size(); i++)
		{
			MyThread b = new MyThread(RequestType.getUserDetailsById, IndexList.getUserDetailsById,req.get(i).getUserId());
			b.start();
			try {
				b.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			User u = (User) MsgFromServer.getDataListByIndex(IndexList.getUserDetailsById);

			if (Selected.contains(u.getName())&&Selected.contains(type[req.get(i).getReqType()-1]))
			{
				flag=true;
				break;
			}	
		}
		if (flag==true)
		{
			setChooseRequests(req.get(i));
			try {connectionmain.showRequests();}
			catch (IOException e){e.printStackTrace();}
		}
		else{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText(null);
				alert.setContentText("Oops something went wrong");
				alert.show();
		}
	}
	
	
	
	
	
	@FXML
	public void clsManagerMain() {
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

	
	

	public String toString() {
		return "ManMainGUIController [ Reqs=" + Reqs + ", BlockLabel="
				+ BlockLabel + ", Ins_PID=" + Ins_PID + ", ManName=" + ManName + ", CheckReq=" + CheckReq + ", OpenReq="
				+ OpenReq + ", GenRep=" + GenRep + ", UnBlock=" + UnBlock + ", Block=" + Block + ", Back=" + Back
				+ ", LogOut=" + LogOut + "]";
	}


	public static Requests getChooseRequests() {
		return chooseRequests;
	}


	@SuppressWarnings("static-access")
	public void setChooseRequests(Requests chooseRequests) {
		this.chooseRequests = chooseRequests;
	}
}