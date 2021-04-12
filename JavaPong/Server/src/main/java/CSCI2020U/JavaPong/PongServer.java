package CSCI2020U.JavaPong;

import java.io.*;
import java.net.*;

public class PongServer 
{
	private ServerSocket serverSocket = null;
	private int port;

	public PongServer(int port) throws IOException 
	{
		serverSocket = new ServerSocket(port);
		this.port = port;
	}

	public void handleRequests() throws IOException 
	{
		System.out.println("Listening to port: " + port);

		// creating a thread to handle each of the clients
		while (true) 
		{
			Socket clientSocket = serverSocket.accept();

			PongServerHandler handler = new PongServerHandler(clientSocket,1);
			Thread handlerThread = new Thread(handler);
			handlerThread.start();

			Socket clientSocket2 = serverSocket.accept();
			
			PongServerHandler handler2 = new PongServerHandler(clientSocket2,2);
			Thread handlerThread2= new Thread(handler2);
			handlerThread2.start();

			handler.otherClient = handler2;
			handler2.otherClient = handler;
		}
	}


	public static void main(String[] args) 
	{
		int port = 8080;
		// port to listen default 8080, or the port from the argument
		if (args.length > 0) 
		{
			port = Integer.parseInt(args[0]);
		}

		try 
		{
			//Instantiating the HttpServer Class
			PongServer server = new PongServer(port);
			// handle any requests from the port
			server.handleRequests();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}