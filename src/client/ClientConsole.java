package client;
// This file contains material supporting section 3.7 of the textbook:

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;


import thred.*;


/**
 * This class constructs the UI for a chat client. It implements the chat
 * interface in order to activate the display() method. Warning: Some of the
 * code here is cloned in ServerConsole
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF {
	// Class variables *************************************************
	ArrayList<String> chat = new ArrayList<>();
	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	public static ChatClient client;
	ClientGUIController controller;
	public boolean conectedFlag;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host
	 *            The host to connect to.
	 * @param port
	 *            The port to connect on.
	 */
	
	/**
	 * checking the host and the port of the client 
	 * @param host the current host
	 * @param port  the current p
	 */
	public ClientConsole(String host, int port) {
		try {
			client = new ChatClient(host, port, this);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client.");

		}
	}

	/**
	 * checking the host and the port of the client 
	 * @param host the current host
	 * @param port the current p
	 * @param controller which controller
	 */
	public ClientConsole(String host, int port, ClientGUIController controller) {
		this.controller = controller;
		conectedFlag = false;
		try {
			client = new ChatClient(host, port, this);
		} catch (IOException exception) {
			controller.addTextToLog("Error: Can't setup connection!" + " Terminating client.");

		}
		
	}

	// Instance methods ************************************************

	/**
	 * This method waits for input from the console. Once it is received, it
	 * sends it to the client's message handler.
	 * 
	 * @param message the message that received from server 
	 */
	public void accept(Object message) {
		try {
			client.handleMessageFromClientUI(message);
			conectedFlag = true;
		} catch (Exception ex) {
			conectedFlag = false;
			System.out.println("Unexpected error while reading from console!");
		}
	}

	/**
	 * This method overrides the method in the ChatIF interface. It displays a
	 * message onto the screen.
	 *
	 * @param message
	 *            The string to be displayed.
	 */
	public void display(String message) {
		System.out.println("> " + message);
		controller.addTextToLog("> " + message);
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the Client UI.
	 *
	 * @param args[0]
	 *            The host to connect to.
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		String host = "";
		// int port = 0; // The port number

		try {
			host = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			host = "localhost";
		}
		// ClientConsole chat = new ClientConsole(host, DEFAULT_PORT);
		// chat.accept(); // Wait for console data
	}

	/**
	 * @return return 1 if connected and 0 if not
	 */
	public boolean isConectedFlag() {
		return conectedFlag;
	}

	/**
	 * @param conectedFlag-put 1 if connected and 0 if not 
	 */
	public void setConectedFlag(boolean conectedFlag) {
		this.conectedFlag = conectedFlag;
	}

	/**
	 * @param op-this is the request type that client chose
	 * @param obj- the object that we get from the server 
	 */
	public static void getFromServer(RequestType op, Object obj) {
		client.handleMessageFromClientUI(op, obj);	
	}

}
// End of ConsoleChat class
