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
		text.append(message.split(",")[0]+" from "+message.split(",")[1]+" -> "+message.split(",")[2]+"\n");
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
			serverSocket = new ServerSocket(5005);
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
				if(new String(message.split(",")[0]).equals("login"))
				{
					
				}
				else if(new String(message.split(",")[0]).equals("register"))
				{
					
				}
				else if(new String(message.split(",")[0]).equals("scoreBoard"))
				{
					
				}
				else if(new String(message.split(",")[0]).equals("game"))
				{
					
				}				
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
		}
	}
}