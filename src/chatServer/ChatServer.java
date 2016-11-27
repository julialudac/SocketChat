package chatServer;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;

import chatServer.ServersSideThread;

public class ChatServer {
	static Scanner scanner=new Scanner(System.in);
	static ArrayList<ServersSideThread> threadArray=new ArrayList(10);
	
	public static int readPort(){
		System.out.println("Enter the port");
		boolean bol=true;
		int port=0;
		while(bol)
		try{
			port=scanner.nextInt();
			bol=false;
		}
		catch(Exception e){
			System.out.println("you did not enter a valid port");
			scanner.next();
		}
		return port;
	}
	
	public static void main(String args[]){
		
		ServerSocket listenSocket;
		int port=readPort();
		try {
			listenSocket = new ServerSocket(port); //port
			System.out.println("Server ready...");
			while (true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("Connexion from:" + clientSocket.getInetAddress());
				ServersSideThread ct = new ServersSideThread(clientSocket,threadArray.size());
				threadArray.add(ct);
				ct.start();
			}
	        } catch (Exception e) {
	            System.err.println("Error in EchoServer:" + e);
	        }
	      }
}

