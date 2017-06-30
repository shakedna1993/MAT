package client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import entity.Studentass;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import thred.IndexList;
import thred.MyThread;

/**
 * This class is the controller for the Evaluations screen GUI.
 */
public class EvaluationsGUIController implements Initializable {
	@FXML
	Button back;
	@FXML
	Label stuName;
	@FXML
	Label Hello;
	@FXML
	Label ChooseLabel;
	@FXML
	Button DownLoadButton;
	@FXML
	Button LogOut;
	@FXML
	ImageView Logo;
	@FXML
	ImageView BackGround;
	@FXML
	ImageView BackIcon;
	@FXML
	ImageView LogOutIcon;
	@FXML
	Label Eva_label;
	@FXML
	Button downEvaluation;
	@FXML
	Button downGradeFile;
	@FXML
	Button chooseFolder;

	JFileChooser chooser = new JFileChooser();

	@FXML
	TableView<Studentass> table = new TableView<>();

	private ObservableList<Studentass> data;
	static String filedir;

	/**
	 * initialize-initialize the student name, shows the evaluations and grade files list.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		stuName.setText(s.getName());

		if (s.getRole() == 4) {
			try {
				MyThread a = new MyThread(RequestType.StudentEvaluations, IndexList.StudentEvaluations,
						MsgFromServer.getDataListByIndex(IndexList.LOGIN));
				a.start();
				a.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (s.getRole() == 6) {
			try {
				MyThread a = new MyThread(RequestType.StudentEvaluations, IndexList.StudentEvaluations,
						ParMainGUIController.getSelectedSon());
				a.start();
				a.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		data = FXCollections.observableArrayList();
		ArrayList<Studentass> b = (ArrayList<Studentass>) MsgFromServer
				.getDataListByIndex(IndexList.StudentEvaluations);
		for (int i = 0; i < b.size(); i++) {
			data.add(b.get(i));
		}

		TableColumn<Studentass, String> c1 = new TableColumn<>("Course Name");
		c1.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		TableColumn<Studentass, String> c2 = new TableColumn<>("Assigenment Name");
		c2.setCellValueFactory(new PropertyValueFactory<>("AssiName"));

		table.getColumns().addAll(c1, c2);
		table.setItems(data);
	}

	/**
	 * DownloadAssigenmentEvaluation-Downloads an assignment evaluation
	 */
	public void DownloadAssigenmentEvaluation() {
		if (table.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Wrong Choise");
			alert.setHeaderText(null);
			alert.setContentText("Please select an assignment");
			alert.show();
			return;
		}
		Studentass ass = table.getSelectionModel().getSelectedItem();
		ass.setFileid(filedir);

		MyThread a = new MyThread(RequestType.DownloadStuEvaluation, IndexList.DownloadStuEvaluation, ass);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Studentass dlass = (Studentass) MsgFromServer.getDataListByIndex(IndexList.DownloadStuEvaluation);
		if (dlass != null) {
			String home = System.getProperty("user.home");
			File newFolder = new File(home + "\\Downloads\\");
			if (!newFolder.exists()) newFolder.mkdirs();
			String filePath = newFolder.getAbsolutePath();
			Path path = Paths.get(filePath+"\\" + dlass.getEvaFileName());
			try {
				Files.write(path, dlass.getEvaData());
			} catch (IOException e) {
				e.printStackTrace();
			}
			filePath = filePath+"\\" + dlass.getFileid();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("Evaluation download successfull");
			alert.show();
			return;
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Fail");
			alert.setHeaderText(null);
			alert.setContentText("Download Failed");
			alert.show();
			return;
		}
	}
	/**
	 * DownloadAssigenmentGradeFile-Downloads an assignment grade file
	 */
	public void DownloadAssigenmentGradeFile() {
		if (table.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Wrong Choise");
			alert.setHeaderText(null);
			alert.setContentText("Please select an assignment");
			alert.show();
			return;
		}
		Studentass ass = table.getSelectionModel().getSelectedItem();
		ass.setFileid(filedir);

		MyThread a = new MyThread(RequestType.DownloadStuGradeFile, IndexList.DownloadStuGradeFile, ass);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Studentass dlass = (Studentass) MsgFromServer.getDataListByIndex(IndexList.DownloadStuGradeFile);
		if (dlass != null) {
			String home = System.getProperty("user.home");
			File newFolder = new File(home + "\\Downloads\\");
			if (!newFolder.exists()) newFolder.mkdirs();
			String filePath = newFolder.getAbsolutePath();
			Path path = Paths.get(filePath+"\\" + dlass.getGradeFileName());
			try {
				Files.write(path, dlass.getGradeData());
			} catch (IOException e) {
				e.printStackTrace();
			}
			filePath = filePath+"\\" + dlass.getFileid();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("Grade File download successfull");
			alert.show();
			return;
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Fail");
			alert.setHeaderText(null);
			alert.setContentText("Download Failed");
			alert.show();
			return;
		}
	}
	
	/**
	 * Logout from the server  
	 */
	@FXML
	public void clsEva() {
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
	/**
	 * This method goes back to the last window that been shown 
	 */	
	@FXML
	private void backButton(ActionEvent event) throws Exception {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		int a = s.getRole();
		if (a == 4)
			connectionmain.showStudentMain();
		else if (a == 6)
			connectionmain.showParentMain();
	}

}
