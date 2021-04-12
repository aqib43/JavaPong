package CSCI2020U.JavaPong;

import java.io.*;
import java.net.*;
import java.util.*;

public class PongServerHandler implements Runnable 
{
	private Socket socket = null;
	private BufferedReader requestInput = null;
	private PrintWriter responseOutput = null;

	private boolean keepReading = true;

	public PongServerHandler otherClient = null;

	private float x;
	private float y;
	private int playerNum;

	public PongServerHandler(Socket socket, int playerNumber) throws IOException 
	{
		this.socket = socket;
		requestInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		responseOutput = new PrintWriter(socket.getOutputStream());
		playerNum = playerNumber;

		
		
	}


	public void run() 
	{
		String line = null;
		try {

			line = requestInput.readLine();
			System.out.println(line);
			sendResponse(String.valueOf(playerNum));
			System.out.println("sent");
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (keepReading)
		{
			try 
			{
				
				line = requestInput.readLine();
				//System.out.println("not progressing");
				handleRequest(line);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
		}
	}

	public void handleRequest(String request) throws IOException 
	{
		try 
		{
			if (request != null)
			{
				StringTokenizer tokenizer = new StringTokenizer(request);
				String command = "";
				if (tokenizer.hasMoreTokens())
				{
					command = tokenizer.nextToken();		
				}
				
				//Lets server know that client is disconnecting
				if (command.equalsIgnoreCase("DISCONNECT")) 
				{
					try 
					{
						keepReading = false;

						requestInput.close();
						responseOutput.close();
						socket.close();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
				else if (command.equalsIgnoreCase("POSITION"))
				{
					//Get the rest of the information from the message
					int objName = 0;
					float objX = 0.0f;
					float objY = 0.0f;

					//Splits based on spaces
					String[] information = request.split("\\s+");
					objName = Integer.parseInt(information[1]);
					x = Float.parseFloat(information[2]);
					y = Float.parseFloat(information[3]);

					//Print out the object information
					System.out.println(objName + " " + x +
					 " " + y);

					//byte[] content = Float.floatToIntBits(x);
					//byte[] content2 = Float.floatToIntBits(y);
					//otherClient.sendResponse(content);
					//otherClient.sendResponse(content2);
				}
				else 
				{
					sendError(405, "Method Not Allowed", "You cannot use the '" + command + "' command on this server.");
				}
			}
		} 
		catch (NoSuchElementException e) 
		{
			e.printStackTrace();
		}

	}

	private void sendResponse(String content) throws IOException 
	{
		responseOutput.print(content);
		responseOutput.flush();
		System.out.println(content);
	}

	private void sendError(int errorCode,
						   String errorMessage,
						   String description) throws IOException 
	{
		String responseCode = "HTTP/1.1 " + errorCode + " " + errorMessage + "\r\n";
		String content = "Error reading file";
		//sendResponse(content.getBytes());
	}
}