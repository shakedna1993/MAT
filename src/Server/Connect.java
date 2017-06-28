
package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class charge for connection to server
 */
public class Connect {
	public static  String password;
	public static  String username;
	public static  String DB;
	/**
	 * This method make connection to server
	 * @return if the connection succeeded 
	 */
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			// handle the error 
			ex.printStackTrace();
		}

		try {
			 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+""+DB+"?useSSL=false","'"+username+"'" , password);
			

		}
		catch (Exception ex) { 
			ex.printStackTrace();
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Connection Error");
			alert.setHeaderText(null);
			alert.setContentText("can't connect to the database");

			alert.showAndWait();
			
		}
		return conn;
	}
	

	/**
	 * This method closes the connection to server
	 * @throws SQLException
	 */
	public static  void close() throws SQLException{
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+""+DB+"","'"+username+"'" , password);
		conn.close();
		
	  }

	public static String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDB() {
		return DB;
	}

	public void setDB(String dB) {
		this.DB = DB;
	}

	@Override
	public String toString() {
		return "Connect [getPassword()=" + getPassword() + ", getUsername()=" + getUsername() + ", getDB()=" + getDB()
				+ ", toString()=" + super.toString() + "]";
	}
}



