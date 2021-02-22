/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.*;
import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*; 

public class Server extends JFrame {
    
    static JTextArea text;	
    JScrollPane scrol;
    
    public Server()
    {
        this.setLayout(new FlowLayout());
        text = new JTextArea(20,50);
        scrol = new JScrollPane(text);
        text.setEditable(false);

        //newServer = new ServerConnection();

        add(scrol);		
    }

    static public void login(Database obj, String userName, String passwd)
    {
        String message ;
        if(obj.checkIfuserExist(userName))
        {
            if(obj.checkUserPassword(userName, passwd))
            {
                message = "login,"+userName+",true";
                     
            }
            else
            {
                
                message = "login,"+userName+",false";     
            }
            Server.writeOnTextArea(message);
            ChatHandler.sendMessaageToAll(message);
        }
        else
        {
            System.out.println("false");
            message = "login,"+userName+",false";
            Server.writeOnTextArea(message);
            ChatHandler.sendMessaageToAll(message);
        }        
    }
    
    static public void register(Database obj, String userName, String password)
    {
        String message ;
        if(obj.checkIfuserExist(userName))
        {
            message = "register,"+userName+",false";
             //user already exist;
        }
        else
        {
            obj.insertInPlayers(userName, password, "0", "0", "0", "online", "no");
            message = "register,"+userName+",true";
        }
        Server.writeOnTextArea(message);
        ChatHandler.sendMessaageToAll(message);
    }
    static public void scoreBoard(Database obj, String userName)
    {
        String message = "";
        message = "scoreBoard,"+userName+","+obj.showScoreBoard();
        System.out.println(message);
        Server.writeOnTextArea(message);
        ChatHandler.sendMessaageToAll(message);  
    }
    static void writeOnTextArea(String message)
    {
        text.append(message.split(",")[0]+" -> "+message.split(",")[1]+" -> "+message.split(",")[2]+"\n");
    }
    public static void main(String[] args)
    {
        Server s1 = new Server();
        s1.setSize(600 , 400);
        s1.setResizable(false);
        s1.setVisible(true);
        
        
        Database d1 = new Database("localhost","3306","tictactoe","root","12345");
        ServerConnection newServer = new ServerConnection(d1);
        
    }   
}
class ServerConnection
{
    ServerSocket serverSocket;
    ServerConnection(Database database)
    {
        try
        {
            serverSocket = new ServerSocket(5005);
            while(true)
            {
                Socket s = serverSocket.accept();
                new ChatHandler(s, database);
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
    Database d;
    static Vector<ChatHandler> clientVector = new Vector<ChatHandler>();

    public ChatHandler(Socket cs, Database database)
    {
        try
        {
            dis = new DataInputStream(cs.getInputStream());
            ps = new PrintStream(cs.getOutputStream());
            d = database;
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
                Server.writeOnTextArea(message);
                System.out.println(message);
                if(new String(message.split(",")[0]).equals("login"))
                {
                   Server.login(d, message.split(",")[1], message.split(",")[2]);

                }
                else if(new String(message.split(",")[0]).equals("register"))
                {
                    Server.register(d, message.split(",")[1], message.split(",")[2]);
                }
                else if(new String(message.split(",")[0]).equals("scoreBoard"))
                {
                    Server.scoreBoard(d, message.split(",")[1]);
                }
                else if(new String(message.split(",")[0]).equals("game"))
                {

                }				
            }
            catch(IOException ex)
            {
                    ex.printStackTrace();
                    stop();
            }
        }
    }
	
    static public void sendMessaageToAll(String msg)
    {		
        for(ChatHandler ch : clientVector)
        {
            ch.ps.println(msg);
        }
    }
}
