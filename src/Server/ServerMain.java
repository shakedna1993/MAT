
package Server;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * server GUI main
 *  */
public class ServerMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/Server/ServerGUI.fxml"));
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Moodel: Server");
			primaryStage.show();
			
			ServerGUIController.setPrimaryStage(primaryStage);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
