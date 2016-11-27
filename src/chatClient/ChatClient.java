/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package chatClient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

	static Scanner scanner = new Scanner(System.in);
	static String host = "localhost";

	/**
	 * main method accepts a connection, receives a message from client then
	 * sends an echo to the client
	 **/

	public static int readPort() {
		System.out.println("Enter the port");
		boolean bol = true;
		int port = 0;
		while (bol)
			try {
				port = scanner.nextInt();
				bol = false;
			} catch (Exception e) {
				System.out.println("you did not enter a valid port");
				scanner.next();
			}
		return port;
	}

	public static void main(String[] args) throws IOException {

		Socket echoSocket = null;
		PrintStream socOut = null;
		BufferedReader stdIn = null;
		BufferedReader socIn = null;
                ClientSideThread clientSideThread=null;
                
		int port = readPort();
		try {
			// creation socket ==> connexion
			echoSocket = new Socket(host, port);
                        clientSideThread=new ClientSideThread(echoSocket);
                        clientSideThread.start();
			socIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			socOut = new PrintStream(echoSocket.getOutputStream());
			stdIn = new BufferedReader(new InputStreamReader(System.in));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host:" + host);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for " + "the connection to:" + host);
			System.exit(1);
		}

		String line;
              	while (true) {
			System.out.println("Type a message please: ");
			line = stdIn.readLine();
			if (line.equals("."))
				break;
			socOut.println(line);
			//System.out.println("received message: " + socIn.readLine());
		}
		socOut.close();
		socIn.close();
		stdIn.close();
		echoSocket.close();
	}
}
