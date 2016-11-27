/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatClient;
import java.io.*;
import java.net.*;
/**
 *
 * @author Dannemp
 */
public class ClientSideThread extends Thread{
    BufferedReader socIn = null;
    PrintStream socOut=null;
    Socket serverSocket;
    public ClientSideThread(Socket s) {
		this.serverSocket = s;
	}
    
    public void run() {
		try {
			socIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			socOut = new PrintStream(serverSocket.getOutputStream());
			while (true) {
				//System.out.println("waiting for a message ");
				String line = socIn.readLine();
				System.out.println("received a message: "+line);
			}
		} catch (Exception e) {
			System.err.println("Error in ClientSideThread:" + e);
		}
	}
}
