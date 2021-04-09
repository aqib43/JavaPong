package CSCI2020U.JavaPong;

import java.io.*;
import java.net.*;

import javafx.scene.text.Text;


public class PongClient
{
	private Socket _socket;
	private BufferedReader _in;
	private PrintWriter _out;

	private String _hostName;
	private int _port;

	private Text _connected = null;

	public PongClient(String host, int port, Text connected) 
	{
		//Sets hostName to host
		_hostName = host;
		//Sets port to port
		_port = port;

		_connected = connected;
	}

	public void SetConnectedText(Text connected)
	{
		_connected = connected;
	}

	public boolean GetConnected()
	{
		boolean connected = false;

		if (_socket != null)
		{
			connected = !_socket.isClosed();
		}

		return connected;
	}

	public void ConnectToServer() 
	{
		try 
		{
			System.out.println("Connecting to server");
			_socket = new Socket(_hostName, _port);

			_in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
			_out = new PrintWriter(_socket.getOutputStream());

			if (_connected != null)
			{
				_connected.setText("Connected to Server!");
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void SendRequest(String command) 
	{
		// send the request simplified
		_out.print(command + "\r\n");
		_out.flush();
	}

	public void DisconnectFromServer() 
	{
		try 
		{
			//Sends disconnect notification and then closes everything
			SendRequest("DISCONNECT");

			//Closes everything
			_in.close();
			_out.close();
			_socket.close();

			if (_connected != null)
			{
				_connected.setText("Disconnected from Server!");
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}