package se.miun.rojn1700.dt062g.jpaint.client;

import java.io.File;

/**
* <h1>ClientTest</h1>
* This class uses Client and tests its functionality. 
*
* @author  Robert Jonsson (roberi)
* @version 1.0
* @since   2018-01-05
*/
public class ClientTest {

	public static void main(String[] args) {
		// Create the client
		Client client = new Client(Client.DEFAULT_ADDRESS, Client.DEFAULT_PORT);
		System.out.println("Client(" + Client.DEFAULT_ADDRESS + ", " +  Client.DEFAULT_PORT + ") created");

		// TEST 1
		// "list" filenames on server
		System.out.println("\nGet filenames from server...");

		// getFilenamesFromServer returns
		// null - if something went wrong (ex. wrong address/port)
		// String array of length 0 - if connected but the server has no files
		// String array of length 1 or more - if connected and the server has one or more files
		String[] filenames = client.getFilenamesFromServer();
		handleList(filenames);

		// TEST 2
		// "load" file from server (the first one in the list)
		System.out.println("\nLoad '" + filenames[0] + "' from server...");

		// getFileFromServer returns
		// null - if something went wrong (ex. wrong address/port)
		// An empty String (length 0) - if connected but the file does not exists on the server
		// A String containing path to saved file - if connected, the server has the file and the file was transfered
		String path = client.getFileFromServer(filenames[0]);
		handleLoad(path);

		// TEST 3
		// "save" file on server (the file previously received)

		// Save file with a different name
		String clientFilename = new File(path).getName();
		String serverFilename = clientFilename.replace(".xml",  " (copy).xml");

		System.out.println("\nSave '" + clientFilename + "' as '" + serverFilename + "' on server...");

		// saveFileToServer returns
		// false - if something went wrong (ex. wrong address/port)
		// true - if file was sent the server
		boolean success = client.saveAsFileToServer(clientFilename, serverFilename);
		handleSave(success);

		// TEST 4
		// "list" filenames on server again to see the new file
		System.out.println("\nGet filenames from server...");

		filenames = client.getFilenamesFromServer();
		handleList(filenames);
	}

	private static void handleList(String[] filenames) {
		// Check for error
		if (filenames == null) {
			System.err.println("Error getting filenames from server! Exiting!");
			System.exit(-1);
		}
		
		// Exit if no filenames
		if (filenames.length == 0) {
			System.out.println("No files on server. Exiting!");
			System.exit(-1);
		}
		
		// List received filenames
		System.out.println("\nListing " + filenames.length + " file(s)");
		for (String s : filenames) {
			System.out.println(s);
		}		
	}

	private static void handleLoad(String path) {
		// Check for error
		if (path == null) {
			System.err.println("Error getting file from server! Exiting!");
			System.exit(-2);
		}
		
		// Exit if no file
		if (path.length() == 0) {
			System.out.println("File not found on server. Exiting!");
			System.exit(-2);
		}
		
		// List path to saved file
		System.out.println("File saved to " + path);
	}
	
	private static void handleSave(boolean success) {
		// Check for error
		if (!success) {
			System.err.println("Error sending file to server! Exiting!");
			System.exit(-3);
		}
		
		System.out.println("File saved on server");		
	}

}
