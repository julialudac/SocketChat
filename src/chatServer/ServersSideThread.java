/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package chatServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServersSideThread extends Thread {

	private Socket clientSocket;
        BufferedReader socIn = null;
        PrintStream socOut=null;
	ArrayList<String> message=new ArrayList();
        int clientNumber;
	public ServersSideThread(Socket s, int nr) {
		this.clientSocket = s;
                this.clientNumber=nr;
	}

	/**
	 * receives a request from client then sends an echo to the client
	 * 
	 * @param clientSocket
	 *            the client socket
	 **/
	
	public void messageAllClients(String mes, int source) {
		for(int i=0;i<ChatServer.threadArray.size();i++){
			try{
				ChatServer.threadArray.get(i).printMessage(mes,source);
			}
			catch (Exception e) {
	        	System.err.println("Error in ChatServer:" + e); 
	        }
		}
	}
	
	public void printMessage(String mes,int source){
		try {
			socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
			String mess="client "+(source+1)+" said: "+mes;
			socOut.println(mess);
			
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}
	
	public void run() {
		try {
			socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			socOut = new PrintStream(clientSocket.getOutputStream());
			while (true) {
				System.out.println("waiting for a message ");
				String line = socIn.readLine();
				System.out.println("received a message: "+line);
				message.add(line);
				messageAllClients(message.get(message.size()-1),clientNumber);
				System.out.println("after message all clients ");
				message.remove(message.size()-1);
				System.out.println("after remove ");
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}

}
