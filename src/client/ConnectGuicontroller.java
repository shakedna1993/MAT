package client;

import java.io.IOException;

import Server.Connect;
import Server.EchoServer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectGuicontroller {
	@FXML
	Button connect;
	@FXML
	Button Logcancel;

	@FXML
	TextField Ip;

	@FXML
	TextField Port;

	public static EchoServer sv;
	public static Stage primaryStage;
	public static ClientConsole cli;
	Connect con =new Connect();
	connectionmain concent= new connectionmain();
	
	@FXML
	public void clsLogin() {
		try {
			Stage stage = (Stage) Logcancel.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	void Startconnection(){
		cli = new ClientConsole(Ip.getText(), Integer.valueOf(Port.getText()));
		connect.setDisable(cli.isConectedFlag());
		if (cli.client.isConnected()) {
			connect.setDisable(true);
			System.out.print("the server has connected ");
			try {
				connectionmain.showUserLogin();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public String toString() {
		return "ConnectGuicontroller [connect=" + connect + ", cancel=" + Logcancel + ", Ip=" + Ip + ", Port=" + Port
				+ ", con=" + con + ", concent=" + concent + "]";
	}
	
}
