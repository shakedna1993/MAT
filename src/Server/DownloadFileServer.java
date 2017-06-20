package Server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import entity.FileEvent;


/**
 * This class charge for transferring book from the server to client 
 *
 */
public class DownloadFileServer {

	// private String destinationPath = "C:/AppBooks/";

	/**
	 * @param sourceFilePath The path of the file
	 * @return fileEvent object, including the file
	 */
	public static FileEvent sendFile(String sourceFilePath) {

		FileEvent fileEvent = new FileEvent();
		String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
		// String path = sourceFilePath.substring(0,
		// sourceFilePath.lastIndexOf("/") + 1);
		fileEvent.setDestinationDirectory("C:/Download/");
		fileEvent.setFilename(fileName);
		fileEvent.setSourceDirectory(sourceFilePath);
		File file = new File(sourceFilePath);
		if (file.isFile()) {
			try {
				DataInputStream diStream = new DataInputStream(new FileInputStream(file));
				long len = (int) file.length();
				byte[] fileBytes = new byte[(int) len];
				int read = 0;
				int numRead = 0;
				while (read < fileBytes.length
						&& (numRead = diStream.read(fileBytes, read, fileBytes.length - read)) >= 0) {
					read = read + numRead;
				}
				fileEvent.setFileSize(len);
				fileEvent.setFileData(fileBytes);
				fileEvent.setStatus("Success");
			} catch (Exception e) {
				e.printStackTrace();
				fileEvent.setStatus("Error");
			}
		} else {
			System.out.println("path specified is not pointing to a file");
			fileEvent.setStatus("Error");
		}

		return fileEvent;

	}
}
