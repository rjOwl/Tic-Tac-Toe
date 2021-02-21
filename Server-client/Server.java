package server; 

import java.net.*;
import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*; 

public class Server extends JFrame
{	
	static JTextArea text;	
	JScrollPane scrol;
	//ServerConnection newServer;
	
	public Server()
	{
		this.setLayout(new FlowLayout());
		text = new JTextArea(20,50);
		scrol = new JScrollPane(text);
		text.setEditable(false);
		
		//newServer = new ServerConnection();

		add(scrol);		
	}
	
	static void writeOnTextArea(String message)
	{
		text.append("Message from "+message.split(",")[0]+" to "+message.split(",")[1]+" -> "+message.split(",")[2]+"\n");
	}
	
	public static void main(String[] args)
	{
		Server s1 = new Server();
		s1.setSize(600 , 400);
		s1.setResizable(false);
		s1.setVisible(true);
		ServerConnection newServer = new ServerConnection();
	}
}


class ServerConnection
{
	ServerSocket serverSocket;
	ServerConnection()
	{
		try
		{
			serverSocket = new ServerSocket(5000);
			while(true)
			{
				Socket s = serverSocket.accept();
				new ChatHandler(s);
			}
			
		}
		catch(IOException ex)
		{
			//ex.printStackTrace();
			
		}
	}
	
	
	
}

class ChatHandler extends Thread
{
	DataInputStream dis;
	PrintStream ps;
	static Vector<ChatHandler> clientVector = new Vector<ChatHandler>();

	public ChatHandler(Socket cs)
	{
		try
		{
			dis = new DataInputStream(cs.getInputStream());
			ps = new PrintStream(cs.getOutputStream());
			//clientVector.add(this);
			//start();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		clientVector.add(this);
		start();
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				String message = dis.readLine();
				
				//String[] messageArray = message.split(",");
				/*if(..)
				{
					
				}
				else
				{
					
				}*/
				//System.out.println("Message from "+messageArray[0]+" to "+messageArray[1]+": "+messageArray[2]);
				Server.writeOnTextArea(message);
				
				sendMessaageToAll(message);
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
				stop();
			}
		}
	}
	
	void sendMessaageToAll(String msg)
	{
		
		for(ChatHandler ch : clientVector)
		{
			ch.ps.println(msg);
			//System.out.println(msg);
		}
	}
}