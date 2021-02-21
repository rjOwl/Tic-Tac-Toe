package client; 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*; 



public class Client extends JFrame
{
	static JTextArea text;
	JTextField textField;
	JScrollPane scrol;
	JButton sendButton;
	
	ClientThread newClient;
	
	String sender;
	String receiver;
	
	public Client(String name)
	{
		this.setLayout(new FlowLayout());
		text = new JTextArea(20,50);
		scrol = new JScrollPane(text);
		textField = new JTextField(40);
		text.setEditable(false);
		sendButton = new JButton("Send");
		
		newClient =  new ClientThread(name);;
		
		
		
		sendButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//text.append(sender+","+receiver+","+textField.getText());
				newClient.ps.println(sender+","+receiver+","+textField.getText());
				textField.setText("");
			}	
		});
		
		add(scrol);
		add(textField);
		add(sendButton);

	}
	public static void main(String args[])
	{
		
		//System.out.println(args[0]);
		//System.out.println(args[1]);
		Client sample = new Client(args[0]);
		sample.sender = args[0];
		sample.receiver = args[1];
		text.append("I am "+sample.sender+" talking to "+sample.receiver+"\n");
		sample.setSize(600 , 400);
		sample.setResizable(false);
		sample.setVisible(true);
		
	}	
	
	static void writeOnTextArea(String msg)
	{
		text.append(msg+"\n");
	}
}

class ClientThread extends Thread{
	Socket mySocket;
	DataInputStream dis;
	PrintStream ps;
	String myName;
	public ClientThread(String name){
		try
		{
			mySocket = new Socket(InetAddress.getLocalHost() , 5000);
			dis = new DataInputStream(mySocket.getInputStream());
			ps = new PrintStream(mySocket.getOutputStream());
			start();
			myName = name;
		}
		catch(IOException exc)
		{
			//exc.printStackTrace();
			System.out.println("Connection lost");
			stop();
		}
	}
	public void run()
	{
		while(true)
		{
			try
			{
				String message = dis.readLine();
				//String[] messageArray = message.split(",");
				if(new String(message.split(",")[1]).equals(myName))
				{
					Client.writeOnTextArea(message.split(",")[2]);
					//Client.writeOnTextArea(message);
				}
			}
			catch(IOException ex)
			{
				//ex.printStackTrace();
				System.out.println("Connection lost");
				stop();
			}
		}
	}
}