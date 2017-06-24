package client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;

public class EditClassGUIController implements Initializable {

	@FXML
	private Label classIDLabel;
	@FXML
	private Label classNameLabel;
	@FXML
	private Label numOfStudentsLabel;
	@FXML
	private Label maxStudentsLabel;
	@FXML
	private Label studentListLabel;
	
	@FXML
	private ComboBox idCombo;
	@FXML
	private ComboBox nameCombo;
	
	@FXML
	private TextField numField;
	@FXML
	private TextField maxField;
	
	@FXML
	private TableView studentListTable;
	@FXML
	private Button back_button;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

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
