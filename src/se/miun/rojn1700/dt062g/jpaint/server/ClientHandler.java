package se.miun.rojn1700.dt062g.jpaint.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <h1>ClientHandler</h1>
 * This file contains the class ClientHandler, used by the Servber class to communicate with connected clients.
 * <p>
 * Giving proper comments in your program makes it more
 * user friendly and it is assumed as a high quality code.
 *
 * @author Robin Jönsson(rojn1700)
 * @version 1.0
 * @since 2019-01-02
 */
public class ClientHandler extends Thread {

    private Socket s;
    private DataInputStream is;
    private DataOutputStream os;

    /**
     * Initializes a new ClientHandler. Will open up output- and inputstreams. If a xml-directory is not present, a xml-
     * directory will be created.
     *
     * @param s The socket of the connecting client.
     */
    ClientHandler(Socket s) {
        this.s = s;
        try {
            os = new DataOutputStream(s.getOutputStream());
            is = new DataInputStream(s.getInputStream());
            if (!Files.isDirectory(Paths.get("xml"))) {
                Files.createDirectories(Paths.get("xml"));
            }
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
            close();
        }
    }

    /**
     * Reads a message from the client and runs the appropriate method. Handled options are "list", "save" and "load".
     */
    @Override
    public void run() {
        try {
            String message = is.readUTF();

            switch (message) {
                case "list":
                    listFiles();
                    break;
                case "save":
                    receiveFileFromClient();
                    break;
                case "load":
                    sendFileToClient();
                    break;
                default:
                    System.out.println("Unhandled option. Closing connection...");
            }

            close();
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Sends list of files on the server to the connected client.
     */
    public void listFiles() {
        File dir = new File("xml");
        String[] fileList = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("(?i)^.*\\.xml$");
            }
        });
        try {
            System.out.println("Sending list of files to " + s.getInetAddress().getHostAddress() + "...");

            // Sending amount of files and list of names to client
            os.writeInt(fileList.length);
            for (int i = 0; i < fileList.length; i++) {
                os.writeUTF(fileList[i]);
            }
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Saves a file from the client to the server.
     */
    public void receiveFileFromClient() {
        String filePath = "xml/";
        try {
            System.out.println("Receiving file from " + s.getInetAddress().getHostAddress() + "...");

            // Receiving filename from client
            filePath += is.readUTF();

            // Receiving file content from client
            int length = is.readInt();
            byte[] data = new byte[length];
            is.read(data);

            // Create file
            Path file = Paths.get(filePath);
            Files.write(file, data);
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Sends a file from the server to the client.
     */
    public void sendFileToClient() {
        String filePath = "xml/";
        try {
            System.out.println("Sending file to " + s.getInetAddress().getHostAddress() + "...");

            // Receiving filename of file to send from client
            String fileName = is.readUTF();

            // Sending file content to client
            filePath += fileName;
            byte[] data = Files.readAllBytes(Paths.get(filePath));
            os.writeInt(data.length);
            os.write(data);
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Closes the connection to the client
     */
    public void close() {
        try {
            is.close();
            os.close();
            s.close();
            interrupt();
            System.out.println("Connection by " + s.getInetAddress().getHostAddress() + " closed.");
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
