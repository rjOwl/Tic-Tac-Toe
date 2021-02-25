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
                obj.updatePlayerStatus(userName, "status", "online");
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
    static void startGame(Database obj, String userName, String gameId)
    {
        String message="";
        /*boolean result = obj.checkIfRoomIsAvailable(gameId);
        if(!result)
        {
            message="readyGame,"+userName+",false";            
            Server.writeOnTextArea(message);
            ChatHandler.sendMessaageToAll(message);                                
        }*/
        obj.setGameId(userName, gameId);
        
        String otherPlayer = obj.checkOtherOpponent(gameId);
        if(new String(otherPlayer).equals("playerNotFound"))
        {
            message="readyGame,"+userName+",true";   
        }
        else
        {
            obj.updatePlayerStatus(userName, "isPlaying", "yes");
            obj.updatePlayerStatus(otherPlayer, "isPlaying", "yes");
            
            message = "play,"+userName+","+otherPlayer+",-1";
        }
        Server.writeOnTextArea(message);
        ChatHandler.sendMessaageToAll(message);  
    }
    static void recordScore(Database obj,String player,String result)
    {
        obj.updateScore(player, result);
    }
    static void cancelGame(Database obj, String player)
    {
        obj.updatePlayerStatus(player, "isPlaying", "no");
        String otherPlayer = obj.removeGameId(player); //and return other player with same game id
        obj.updatePlayerStatus(player, "otherPlayer", "no");
        String message;
        message="stopGame,"+otherPlayer;
        Server.writeOnTextArea(message);
        ChatHandler.sendMessaageToAll(message); 
        
    }
    static void activatePlayAgain(Database obj, String player)
    {
        String message="";
        obj.updatePlayerStatus(player, "playAgain", "true");
        String otherPlayer = obj.getPlayertoPlayAgain(player); //return other player name if his playAgain = true
        if(new String(otherPlayer).equals("playerNotReady"))
        {
            //server readyGame,hossam,true
            message="readyGame,"+player+",true";
        }
        else
        {
            //play,hossam,chris,-1 
           message="play,"+player+","+otherPlayer+"-1"; 
           obj.updatePlayerStatus(player, "playAgain", "false");
           obj.updatePlayerStatus(otherPlayer, "playAgain", "false");
        }
        Server.writeOnTextArea(message);
        ChatHandler.sendMessaageToAll(message);  
    }
    static void logout(Database obj,String userName)
    {
        obj.updatePlayerStatus(userName, "status", "offline");      
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
                else if(new String(message.split(",")[0]).equals("readyGame"))
                {
                    Server.startGame(d, message.split(",")[1], message.split(",")[2]);
                }
                else if(new String(message.split(",")[0]).equals("play"))
                {
                    //client play,hossam,chris,0,0                    
                    Server.writeOnTextArea("play,"+message.split(",")[2]+","+message.split(",")[1]+
                        ","+message.split(",")[3]+","+message.split(",")[4]+"");
                    ChatHandler.sendMessaageToAll("play,"+message.split(",")[2]+","+message.split(",")[1]+
                        ","+message.split(",")[3]+","+message.split(",")[4]+""); 
                }
                else if(new String(message.split(",")[0]).equals("endGame"))
                {
                    //endGame,hossam,draw
                    Server.recordScore(d, message.split(",")[1],message.split(",")[2]);
                    
                }
                else if(new String(message.split(",")[0]).equals("cancelGame"))
                {
                    //client cancelGame,hossam
                    Server.cancelGame(d,message.split(",")[1]);
                    
                }
                else if(new String(message.split(",")[0]).equals("contGame"))
                {
                    //client contGame,hossam
                    Server.activatePlayAgain(d,message.split(",")[0]);
                    
                }
                else if(new String(message.split(",")[0]).equals("logout"))
                {
                    Server.logout(d,message.split(",")[0]);
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
