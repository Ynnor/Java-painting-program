package se.miun.rojn1700.dt062g.jpaint.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <h1>Client</h1>
 * Opens up a connection to a server.
 * <p>
 * Giving proper comments in your program makes it more
 * user friendly and it is assumed as a high quality code.
 *
 * @author Robin Jönsson(rojn1700)
 * @version 1.0
 * @since 2019-01-02
 */
public class Client {
    public static String DEFAULT_ADDRESS = "127.0.0.1";
    public static int DEFAULT_PORT = 10000;

    private String address;
    private int port;

    private Socket s;
    private DataOutputStream os;
    private DataInputStream is;

    /**
     * Instantiates a new client using the default values for the address and port.
     */
    public Client() {
        this.address = DEFAULT_ADDRESS;
        this.port = DEFAULT_PORT;
    }

    /**
     * Instantiates a new client with specific address and port values.
     *
     * @param address the address to the server.
     * @param port the port the server is listening to.
     */
    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Tries to connect to a server and open up output- and input streams.
     *
     * @return True if connection is successful.
     */
    protected boolean connect() {
        try {
            s = new Socket(address, port);
            s.setSoTimeout(10000);
            os = new DataOutputStream(s.getOutputStream());
            is = new DataInputStream(s.getInputStream());
            return true;
        } catch (java.io.IOException e) {
            System.out.println("Fel vid upprättande av förbindelse med server:\n" + e);
            disconnect();
            return false;
        }
    }

    /**
     * Disconnects from a connected server.
     */
    protected void disconnect() {
        try {
            is.close();
            os.close();
            s.close();
            is = null;
            os = null;
            s = null;
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Receives a list of files available on the server.
     *
     * @return the list of files on the server. Returns null if connection fails.
     */
    public String[] getFilenamesFromServer() {
        String[] files;

        if (connect()) {
            try {
                // Sends the list command
                os.writeUTF("list");

                // Receives the number of files
                int numberOfFiles = is.readInt();
                files = new String[numberOfFiles];

                // Receives the filenames.
                for (int i = 0; i < numberOfFiles; i++) {
                    files[i] = is.readUTF();
                }
                disconnect();
                return files;
            } catch (java.io.IOException e) {
                System.err.println(e.getMessage());
            }
        }
        disconnect();
        return null;
    }

    /**
     * Receives a file from the server.
     *
     * @param filename the name of the file on the server,
     * @return a String of the path to the received file.
     */
    public String getFileFromServer(String filename) {
        if (connect()) {
            try {
                // Sends the load command
                os.writeUTF("load");

                // Sends the name of the file
                os.writeUTF(filename);

                // Receives the file content
                int length = is.readInt();
                byte[] data = new byte[length];
                is.read(data);

                // Saves the received file on the client.
                Path file = Paths.get(filename);
                Files.write(file,data);
                disconnect();
                return Paths.get(filename).toString();
            } catch (java.io.IOException e) {
                System.err.println(e.getMessage());
            }
        }
        disconnect();
        return "";
    }

    /**
     * Sends a file to be saved on the server.
     *
     * @param clientFilename the filename of the file to send
     * @param serverFilename the filename to save the file as.
     * @return
     */
    public boolean saveAsFileToServer(String clientFilename, String serverFilename) {
        if(connect()) {
            try {
                os.writeUTF("save");
                os.writeUTF(serverFilename);
                byte[] data = Files.readAllBytes(Paths.get(clientFilename));
                os.writeInt(data.length);
                os.write(data);
                disconnect();
                return true;
            } catch (java.io.IOException e) {
                System.err.println(e.getMessage());
            }
        }
        disconnect();
        return false;
    }
}
