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
import java.sql.Timestamp;

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

    static public void login(Database obj, String userName, String passwd, int threadNumber)
    {
        String message ;
        if(obj.checkIfuserExist(userName))
        {
            if(obj.checkUserPassword(userName, passwd))
            {
                // check user status 
                obj.updatePlayerStatus(userName, "status", "online");
                String s=String.valueOf(threadNumber);
                obj.setThreadNumber(userName, s);
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
    
    static public void register(Database obj, String userName, String password, int threadNumber)
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
            String s=String.valueOf(threadNumber);
            obj.setThreadNumber(userName, s);
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
        boolean result = obj.checkIfRoomIsAvailable(gameId);
        if(!result)
        {
            message="readyGame,"+userName+",false";            
            Server.writeOnTextArea(message);
            ChatHandler.sendMessaageToAll(message);  
            return;
        }
        obj.setGameId(userName, gameId);
        
        String otherPlayer = obj.checkOtherOpponent(userName,gameId);
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
        String gameId =  obj.getRoomIdByPlayer(player);
        String otherPlayer = obj.checkOtherOpponent(player, gameId);
        String otherResult = "";
        if(new String(result).equals("win"))
        {
           otherResult = "lose"; 
        }
        else if(new String(result).equals("lose"))
        {
           otherResult = "win"; 
        }
        if(new String(result).equals("draw"))
        {
           otherResult = "draw"; 
        }
        obj.updateScore(otherPlayer, otherResult);
    }
    static void cancelGame(Database obj, String player)
    {
        System.out.println("cancel game is done");

        obj.updatePlayerStatus(player, "isPlaying", "no");
        System.out.println("cancel game is done");
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
        obj.updatePlayerStatus(userName, "status", "offline");    //improve it  
    }
    
    static void writeOnTextArea(String message)
    {
        if(new String(message.split(",")[0]).equals("play"))
        {
            String[] test = message.split(",");
            int arrayLength = test.length;
            //System.out.println(arrayLength);
            if(arrayLength == 5)
            {
                text.append(message.split(",")[0]+" -> "+message.split(",")[1]+" -> "+message.split(",")[2]
                    +" -> "+message.split(",")[3]+" -> "+message.split(",")[4]+"\n");  
            }
            else if(arrayLength == 6)
            {
                text.append(message.split(",")[0]+" -> "+message.split(",")[1]+" -> "+message.split(",")[2]
                    +" -> "+message.split(",")[3]+" -> "+message.split(",")[4]+" -> "+message.split(",")[5]+"\n");  
            }
            else
            {
                text.append(message.split(",")[0]+" -> "+message.split(",")[1]+" -> "+message.split(",")[2]
                    +" -> "+message.split(",")[3]+"\n");   
            }
            
        }
        else if(new String(message.split(",")[0]).equals("cancelGame") || 
                new String(message.split(",")[0]).equals("contGame") ||
                new String(message.split(",")[0]).equals("logout") )
        {
            text.append(message.split(",")[0]+" -> "+message.split(",")[1]+"\n");
        }
        else
        {
            text.append(message.split(",")[0]+" -> "+message.split(",")[1]+" -> "+message.split(",")[2]+"\n");
        }
        
    }
    
    public static void main(String[] args)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
        Server s1 = new Server();
        s1.setSize(600 , 400);
        s1.setResizable(false);
        s1.setVisible(true);
        
        
        //Database d1 = new Database("localhost","3306","tictactoe","root","12345");
        Database d1 = Database.getInstance();
                d1.showScoreBoard();
        d1.resetUsers();
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
    static int counter = 0;
    DataInputStream dis;
    PrintStream ps;
    Database d;
    int myThreadNumber;
    static Vector<ChatHandler> clientVector = new Vector<ChatHandler>();

    public ChatHandler(Socket cs, Database database)
    {
        try
        {
            dis = new DataInputStream(cs.getInputStream());
            ps = new PrintStream(cs.getOutputStream());
            d = database;
            counter++;
            myThreadNumber = counter;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        clientVector.add(this);
        System.out.println(clientVector);
        System.out.println(clientVector.size());
        start();
    }
	
    public void run()
    {
        while(true)
        {
            try
            {
                String message = dis.readLine();
                System.out.println(message);
                Server.writeOnTextArea(message);
               
                if(new String(message.split(",")[0]).equals("login"))
                {
                   Server.login(d, message.split(",")[1], message.split(",")[2],this.myThreadNumber);

                }
                else if(new String(message.split(",")[0]).equals("register"))
                {
                    Server.register(d, message.split(",")[1], message.split(",")[2],this.myThreadNumber);
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
                    Server.writeOnTextArea("play,"+message.split(",")[1]+","+message.split(",")[2]+
                        ","+message.split(",")[3]+","+message.split(",")[4]+","+message.split(",")[1]+"");
                    ChatHandler.sendMessaageToAll("play,"+message.split(",")[1]+","+message.split(",")[2]+
                        ","+message.split(",")[3]+","+message.split(",")[4]+","+message.split(",")[1]+""); 
                    
                    Server.writeOnTextArea("play,"+message.split(",")[2]+","+message.split(",")[1]+
                        ","+message.split(",")[3]+","+message.split(",")[4]+",non");
                    ChatHandler.sendMessaageToAll("play,"+message.split(",")[2]+","+message.split(",")[1]+
                        ","+message.split(",")[3]+","+message.split(",")[4]+",non"); 
                }
                else if(new String(message.split(",")[0]).equals("endGame"))
                {
                    //endGame,hossam,draw
                    Server.recordScore(d, message.split(",")[1],message.split(",")[2]);
                    
                }
                else if(new String(message.split(",")[0]).equals("cancelGame"))
                {
                    System.out.println("hello");
                    //cancelGame
                    //"cancelGame"+","+client.myName
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
                    Server.logout(d,message.split(",")[1]);
                }
            }
            catch(IOException ex)
            {
                
                //ex.printStackTrace();
                String name = d.resetUserDataUsingThreadNumber(this.myThreadNumber);
                System.out.println("Clien went offline");
                Server.writeOnTextArea(""+name+",went,offline");
                clientVector.remove(this);
                System.out.println(clientVector);
                System.out.println(clientVector.size()); 
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
