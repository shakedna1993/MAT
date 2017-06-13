/*************************************************************************
 * 
 *	Good Reading project
 *	====================
 * 	File		: ServerGUIController.java
 *  Package		: gui.server
 *   
 * 	Author		: Topaz Uliel
 * 	Date		: December 2017
 * 
 * 
 * 	Info		: 
 * 
 **************************************************************************/

package Server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * GUI-controller for server main screen.
 */
public class ServerGUIController {

	public static EchoServer sv;
	public static Stage primaryStage;
	DBC check=new DBC();
	
	@FXML
	Button CloseServerBtn;
	@FXML
	Button StartServerBtn;
	@FXML
	TextField IP_text;
	@FXML
	TextArea Logger;
	@FXML
	TextField PortText;
	@FXML
	TextField PortText1;
	@FXML
	TextField username;
	@FXML
	PasswordField Pass;
	@FXML
	TextField DB1;

	
	/**
	 * This method initializing the server by searching ip
	 */
	@FXML
	public void initialize() {

		Logger.setText("----- Server log:\n");
		Logger.setWrapText(true);
		Logger.setEditable(false);
		try {
			IP_text.setText("" + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			System.out.println("Error getting ip");
		}
	}

	/**
	 * This method close the server by the close button
	 */
	@FXML
	void closeBtn() {
		if (StartServerBtn.isDisable()) {
			try {
				sv.close();
				StartServerBtn.setDisable(false);
			} catch (Exception ex) {
			}
		} else
			primaryStage.close();
	}

	/**
	 * This method starting the server 
	 */
	@FXML
	void startServer() {
		Connect con= new Connect();
		sv = new EchoServer(Integer.valueOf(PortText.getText()), this);
		con.password=Pass.getText();
		con.username=username.getText();
		con.DB=DB1.getText();
		check.ResetServer();
	
		try {
			sv.listen(); // Start listening for connections
			StartServerBtn.setDisable(true);
		} catch (Exception ex) {
			StartServerBtn.setDisable(false);
			
		}
		
   	}
	
	

	/**
	 * @param msg-adding text next to the clock in the screen
	 */
	public void addTextToLog(String msg) {
		Logger.appendText("[" + Calendar.HOUR + ":" + Calendar.MINUTE + ":" + Calendar.SECOND + "] : " + msg + "\n");
	}

	/**
	 * cleaning the login text in the screen
	 */
	public void cleanLogText() {
		Logger.setText("");
	}

	public static EchoServer getSv() {
		return sv;
	}

	public static void setSv(EchoServer sv) {
		ServerGUIController.sv = sv;
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		ServerGUIController.primaryStage = primaryStage;
	}

	public Button getCloseServerBtn() {
		return CloseServerBtn;
	}

	public void setCloseServerBtn(Button closeServerBtn) {
		CloseServerBtn = closeServerBtn;
	}

	public Button getStartServerBtn() {
		return StartServerBtn;
	}

	public void setStartServerBtn(Button startServerBtn) {
		StartServerBtn = startServerBtn;
	}

	public TextField getIP_text() {
		return IP_text;
	}

	public void setIP_text(TextField iP_text) {
		IP_text = iP_text;
	}

	public TextArea getLogger() {
		return Logger;
	}

	public void setLogger(TextArea logger) {
		Logger = logger;
	}

	public TextField getPortText() {
		return PortText;
	}

	public void setPortText(TextField portText) {
		PortText = portText;
	}
	public TextField getUsername() {
		return username;
	}

	public void setUsername(TextField username) {
		this.username = username;
	}

	public PasswordField getPass() {
		return Pass;
	}

	public void setPass(PasswordField pass) {
		Pass = pass;
	}

	@Override
	public String toString() {
		return "ServerGUIController [CloseServerBtn=" + CloseServerBtn + ", StartServerBtn=" + StartServerBtn
				+ ", IP_text=" + IP_text + ", Logger=" + Logger + ", PortText=" + PortText + ", PortText1=" + PortText1
				+ ", username=" + username + ", Pass=" + Pass + "]";
	}

}
