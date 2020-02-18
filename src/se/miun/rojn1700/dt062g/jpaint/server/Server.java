package se.miun.rojn1700.dt062g.jpaint.server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * <h1>Server</h1>
 * Starts a server. Requires a ClientHandler class to communicate with connected clients.
 * <p>
 * Giving proper comments in your program makes it more
 * user friendly and it is assumed as a high quality code.
 *
 * @author Robin Jönsson(rojn1700)
 * @version 1.0
 * @since 2019-01-02
 */
public class Server {
    /**
     * Initiates a serversocket and starts listening and accepting sockets connecting to it.
     * Default port is 10000
     * @param args arguments for the server. The first argument will be used as the port number.
     */
    public static void main(String[] args) {
        int port = 10000;
        ServerSocket ss;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }
        }
        try {
            ss = new ServerSocket(port);
            System.out.println("Server started and listening on port: " + port);
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        while(true) {
            try {
                Socket s = ss.accept();
                System.out.println("New connection by " + s.getInetAddress().getHostAddress());
                new ClientHandler(s).start();
            } catch (java.io.IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
