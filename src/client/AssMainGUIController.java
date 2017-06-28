package client;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import com.sun.glass.ui.Pixels.Format;

import entity.Assigenment;
import entity.Course;
import entity.Student;
import entity.Studentass;
import entity.User;
import entity.Class;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.Assignment;
import thred.IndexList;
import thred.MyThread;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssMainGUIController implements Initializable {

	public static ClientConsole cli;
	public static Stage primaryStage;

	@FXML
	ComboBox<String> STC;
	@FXML
	Button Upload_Ass;
	@FXML
	Button selectBtn;
	@FXML
	Button Down_Ass;
	@FXML
	Button CourseL;
	@FXML
	Button Back;
	@FXML
	Button LogOut;
	@FXML
	Label Hello, selectAss;
	@FXML
	Label Down;
	@FXML
	Label UP;
	@FXML
	Label selectC;
	@FXML
	Label Ass;
	@FXML
	javafx.scene.control.Label StuName;
	@FXML
	TableView<Assigenment> table = new TableView<>();
	@FXML
	ImageView Logo;

	private ObservableList<Assigenment> data;
	static String assToChoose;
	static String crs, filedir, id;


	public void initialize(URL location, ResourceBundle resources) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		StuName.setText(s.getName());
		id = s.getId();

		setComboBoxStudentCourse(id);

	}

	@SuppressWarnings("unchecked")
	public void setComboBoxStudentCourse(String id) {
		ArrayList<String> a1 = new ArrayList<String>();
		ArrayList<String> a2 = new ArrayList<String>();
		Course course = new Course();
		MyThread C = new MyThread(RequestType.setComboBoxStudentCourse, IndexList.setComboBoxStudentCourse,MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		C.start();
		try {
			C.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		a1 = (ArrayList<String>) MsgFromServer.getDataListByIndex(IndexList.setComboBoxStudentCourse);

		for (int i = 0; i < a1.size(); i++) {
			MyThread a = new MyThread(RequestType.createCourseEntity, IndexList.createCourseEntity, a1.get(i));
			a.start();
			try {
				a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			course = (Course) MsgFromServer.getDataListByIndex(IndexList.createCourseEntity);
			a2.add(course.getName());
		}
		ObservableList<String> list = FXCollections.observableArrayList(a2);
		STC.setItems(list);
	}

	@SuppressWarnings("unchecked")
	public void setTableViewStudentCourseAssigenment() {
		table.getColumns().clear();

		Object st= STC.getValue();
		String  CourseName=st.toString();
		MyThread a = new MyThread(RequestType.StudentCourse, IndexList.StudentCourse, MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		ArrayList<Course> course = (ArrayList<Course>) MsgFromServer.getDataListByIndex(IndexList.StudentCourse);
		for (int i = 0; i < course.size(); i++) {
			if (course.get(i).getName().equals(CourseName)) {
				crs=course.get(i).getCourseId();
				Studentass asud=new Studentass();
				asud.setCourseid(course.get(i).getCourseId());
				asud.setStudid(id);
				MyThread b = new MyThread(RequestType.setTableViewStudentCourseAssigenment,
						IndexList.setTableViewStudentCourseAssigenment, asud);
				b.start();
				try {
					b.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				break;
			}
		}
		ArrayList<Assigenment> b = (ArrayList<Assigenment>) MsgFromServer
				.getDataListByIndex(IndexList.setTableViewStudentCourseAssigenment);
		data = FXCollections.observableArrayList();
		for (int i = 0; i < b.size(); i++)
			data.add(b.get(i));
		TableColumn<Assigenment, String> c1 = new TableColumn<>("Assigenment Name");
		c1.setCellValueFactory(new PropertyValueFactory<>("assname"));
		TableColumn<Assigenment, String> c2 = new TableColumn<>("Due Date");
		c2.setCellValueFactory(new PropertyValueFactory<>("DueDate"));
		table.getColumns().addAll(c1, c2);
		table.setItems(data);
		b.clear();
	}


	public void UploadAss() {
		try {
			if (table.getSelectionModel().getSelectedItem() == null)
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong Choise");
				alert.setHeaderText(null);
				alert.setContentText("Please select course and assignment");
				alert.show();
				return;
			}
			int tmp=table.getSelectionModel().getSelectedItem().getAssId();
			Date tmp1=table.getSelectionModel().getSelectedItem().getDueDate();
			
			
			StudentUploadAssGUIController.initVariable(crs,tmp,tmp1);
			connectionmain.ShowStudentUploadAss();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void DownloadAss() throws IOException {
		if (table.getSelectionModel().getSelectedItem() == null)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Wrong Choise");
			alert.setHeaderText(null);
			alert.setContentText("Please select course and assignment");
			alert.show();
			return;
		}
		Assigenment ass= table.getSelectionModel().getSelectedItem();
		ass.setFileid(filedir);

		MyThread a = new MyThread(RequestType.DownloadAssigenment, IndexList.DownloadAssigenment, ass);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if ((int)MsgFromServer.getDataListByIndex(IndexList.DownloadAssigenment) == 1)
		{	
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("Assigenment download successfull");
			alert.show();
			return;
		}
		else{	
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Fail");
			alert.setHeaderText(null);
			alert.setContentText("Download Failed");
			alert.show();
			return;
		}
	}
	@FXML
	public void clsAssMain() {
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

	@FXML
	private void backButton(ActionEvent event) throws Exception {
		connectionmain.showStudentMain();
	}

	@Override
	public String toString() {
		return "AssMainGUIController [STC=" + STC + ", Upload_Ass=" + Upload_Ass + ", selectBtn=" + selectBtn
				+ ", Down_Ass=" + Down_Ass + ", CourseL=" + CourseL + ", Back=" + Back + ", LogOut=" + LogOut
				+ ", Hello=" + Hello + ", selectAss=" + selectAss + ", selectC=" + selectC + ", Ass=" + Ass
				+ ", StuName=" + StuName + ", table=" + table + ", Logo=" + Logo + ", data=" + data + "]";
	}

}
