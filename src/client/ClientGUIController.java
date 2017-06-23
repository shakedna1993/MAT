
package client;

import java.util.ArrayList;
import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class is the controller for the client GUI.
 */
public class ClientGUIController {

	public static ClientConsole cli;
	public static Stage primaryStage;

	private ArrayList<CheckBox> ArrOp = new ArrayList<>();
	private ArrayList<RequestType> request = new ArrayList<>();
	private int ArrSelectedIndex;

	@FXML
	Button CloseServerBtn;
	@FXML
	Button CloseServerBtnA;

	@FXML
	Button ConnectToServerBtn;
	@FXML
	TextField IP_text;
	@FXML
	TextArea Logger;
	@FXML
	TextField PortText;

	@FXML
	TextField NameF;
	@FXML
	TextField IdF;
	@FXML
	TextField DepF;
	@FXML
	Button OkBtn;
	@FXML
	CheckBox add;/* ,update,delete,info; */
	@FXML
	CheckBox update;
	@FXML
	CheckBox delete;
	@FXML
	CheckBox info;
	@FXML
	Tab DB_Tab;

	/**
	 * initialize- initialize the
	 */
	@FXML
	void initialize() {
		Logger.setText("----- Client log:\n");
		Logger.setWrapText(true);
		Logger.setEditable(false);
		ArrOp.add(delete);

	}

	/*
	 * @FXML void OkPressed() {
	 * 
	 * IdF.getText().isEmpty() ? 0 : Integer.valueOf(IdF.getText())); try {
	 * cli.client.sendToServer((Object) (new Op(tmp,
	 * request.get(ArrSelectedIndex)))); cli.controller.addTextToLog(
	 * "\nmassege sended:" + tmp + "  op" + request.get(ArrSelectedIndex)); }
	 * catch (IOException e) { e.printStackTrace(); } // send to // server // }
	 * // }.start();
	 * 
	 * 
	 * 
	 * }
	 */

	/*
	 * @FXML void Boxpress() { ArrSelectedIndex = -1; for (CheckBox c : ArrOp)
	 * if (c.isSelected()) ArrSelectedIndex = ArrOp.indexOf(c); else
	 * c.setDisable(true);
	 * 
	 * if (ArrSelectedIndex == -1) { for (CheckBox c : ArrOp)
	 * c.setDisable(false); OkBtn.setDisable(true); } else
	 * OkBtn.setDisable(false); }
	 */
	/**
	 * this method is making the close button in GUI
	 */
	@FXML
	void closeBtn() {
		if (ConnectToServerBtn.isDisable()) {
			try {
				cli.client.closeConnection();
				cli.controller.addTextToLog("Communication with the server stopped");
				ConnectToServerBtn.setDisable(false);
			} catch (Exception ex) {
				ConnectToServerBtn.setDisable(true);
			}
		} else
			primaryStage.close();
	}

	/**
	 * this method starting the server by getting ip text
	 */
	@FXML
	void startServer() {
		cli = new ClientConsole(IP_text.getText(), Integer.valueOf(PortText.getText()), this);
		ConnectToServerBtn.setDisable(cli.isConectedFlag());
		if (cli.client.isConnected()) {
			ConnectToServerBtn.setDisable(true);
			cli.controller.addTextToLog("the server has connected");
		}
	}

	/**
	 * @param msg-
	 *            show the hour that the client logged in
	 */
	public void addTextToLog(String msg) {
		Logger.appendText("[" + Calendar.HOUR + ":" + Calendar.MINUTE + ":" + Calendar.SECOND + "] : " + msg + "\n");

	}

	/**
	 * this method cleaning the field of the logText
	 */
	public void cleanLogText() {
		Logger.setText("");
	}

	/**
	 * this getter method return the clientConsole
	 * 
	 * @return return client console
	 */
	public static ClientConsole getCli() {
		return cli;
	}

	/**
	 * set clientConsole
	 * 
	 * @param cli-client
	 *            console
	 */
	public static void setCli(ClientConsole cli) {
		ClientGUIController.cli = cli;
	}

	/**
	 * this method get the PrimaryStage
	 * 
	 * @return the primary stage
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * set the PrimaryStage
	 * 
	 * @param primaryStage-will
	 *            represent the PrimaryStage
	 */
	public static void setPrimaryStage(Stage primaryStage) {
		ClientGUIController.primaryStage = primaryStage;
	}

	/**
	 * this method get the close button server
	 * 
	 * @return the close server button
	 */
	public Button getCloseServerBtn() {
		return CloseServerBtn;
	}

	/**
	 * this method set close button server
	 * 
	 * @param closeServerBtn-will
	 *            hold the close button server
	 */
	public void setCloseServerBtn(Button closeServerBtn) {
		CloseServerBtn = closeServerBtn;
	}

	/**
	 * get the connect button server
	 * 
	 * @return the connect button server
	 */
	public Button getConnectToServerBtn() {
		return ConnectToServerBtn;
	}

	/**
	 * set the connect button server
	 * 
	 * @param connectToServerBtn-will
	 *            hold the connect button server
	 */
	public void setConnectToServerBtn(Button connectToServerBtn) {
		ConnectToServerBtn = connectToServerBtn;
	}

	/**
	 * get the IP_text
	 * 
	 * @return the IP_text
	 */
	public TextField getIP_text() {
		return IP_text;
	}

	/**
	 * will set the ip text
	 * 
	 * @param iP_text-hold
	 *            the ip text
	 */
	public void setIP_text(TextField iP_text) {
		IP_text = iP_text;
	}

	/**
	 * getting the logger id
	 * 
	 * @return logger id
	 */
	public TextArea getLogger() {
		return Logger;
	}

	/**
	 * will set the the logger id
	 * 
	 * @param logger-will
	 *            hold the logger id
	 */
	public void setLogger(TextArea logger) {
		Logger = logger;
	}

	/**
	 * get the port text
	 * 
	 * @return the portText
	 */
	public TextField getPortText() {
		return PortText;
	}

	/**
	 * setting the portText
	 * 
	 * @param portText-will
	 *            hold the portText
	 */
	public void setPortText(TextField portText) {
		PortText = portText;
	}

}
