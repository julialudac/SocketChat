/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package chat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientThread extends Thread {

	private Socket clientSocket;
	ArrayList<String> message=new ArrayList();
	public ClientThread(Socket s) {
		this.clientSocket = s;
	}

	/**
	 * receives a request from client then sends an echo to the client
	 * 
	 * @param clientSocket
	 *            the client socket
	 **/
	
	public void messageAllClients(String mes) {
		System.out.println("we are in messageAllClients mes="+mes);
		for(int i=0;i<ChatServer.threadArray.size();i++){
			try{
				ChatServer.threadArray.get(i).printMessage(mes,i);
			}
			catch (Exception e) {
	        	System.err.println("Error in ChatServer:" + e); 
	        }
		}
	}
	
	public void printMessage(String mes,int i){
		try {
			BufferedReader socIn = null;
			socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
			String mess="client "+(i+1)+" said: "+mes;
			System.out.println("we are in print message");
			System.out.println(mess);
			socOut.println(mess);
			
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}
	
	public void run() {
		try {
			BufferedReader socIn = null;
			socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
			while (true) {
				System.out.println("waiting for a message ");
				String line = socIn.readLine();
				System.out.println("received a message: "+line);
				message.add(line);
				
				socOut.println(line);
				System.out.println("sent message to client ");
				messageAllClients(message.get(message.size()-1));
				System.out.println("after message all clients ");
				message.remove(message.size()-1);
				System.out.println("after remove ");
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}

}
