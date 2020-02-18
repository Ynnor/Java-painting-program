package se.miun.rojn1700.dt062g.jpaint;

import javax.swing.SwingUtilities;
import se.miun.rojn1700.dt062g.jpaint.client.Client;

/**
* <h1>Assignment 8</h1>
* This class is the starting point for the drawing application.
* It creates our JFrame and makes it visible. A Client is created
* that the JFrame uses to communicate with the server.
* 
*
* @author  Robin Jönsson (rojn1700)
* @version 1.0
* @since   2019-01-03
*/
public class Assignment8 {

	public static void main(String[] args) {
		// Default address and port to server.
		String address = Client.DEFAULT_ADDRESS;
		int port = Client.DEFAULT_PORT;
				
		// Check arguments if different address and port should be used.
		if(args.length > 0) {
			address = args[0];
			if(args.length > 1) {
				try {
					port = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		
		/* Declare this variables as final so that it can be used
		   in our anonymous class below (new Runnable). A variable referenced
		   from inside an anonymous class (that is not declared in the anonymous
		   class itself) must be either an instance variable or a local variable
		   that is not seen to change in the calling scope. An anonymous
		   class cannot access local variables in its enclosing scope that are not
		   declared as final.
		*/
		final Client client = new Client(address, port);
		
		// Make sure GUI is created on the event dispatching thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new JPaintFrame(client).setVisible(true);
			}
		});
	}
}