package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientThread extends Thread {
    public Socket mySocket;
    public DataInputStream dis;
    public PrintStream ps;
    public String myName;
    public String opponent;
    public Stage currentWindow;
    public int OK=2;
    private String message;
    public boolean IMY=false;
    private static ClientThread client = new ClientThread("");

    public void setData(String name, Stage currentWindow) {
        this.myName = name;
        this.currentWindow = currentWindow;
    }

    public static ClientThread getInstance(){  
        return client;
    }  
    public static void resetClient(){
        client = new ClientThread("");
    }
    private ClientThread(String name){
        try {
            System.out.println("Calling the client thread constructor");
            mySocket = new Socket("hossamradwan.hopto.org", 5005);
            dis = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
            start();
            myName = name;
        } catch (IOException exc) {
            //exc.printStackTrace();
            System.out.println("Connection lost");
            stop();
        }
    }

    ClientThread() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public String getRows(){
        return message;
    }
    public String getMessage(){
        return message;
    }
    public void run() {
        while (true) {
            try {
                System.out.println("In run client thread");
                message = dis.readLine();
                //String[] messageArray = message.split(",");
                if (new String(message.split(",")[1]).equals(myName)){
                    if( new String(message.split(",")[0]).equals("login")
                        || new String(message.split(",")[0]).equals("register")){
                        if(new String(message.split(",")[2]).equals("true")) OK = 1;
                        else OK = 0;
                    }

                    // scoreBoard,hossam,requested
                    //scoreBoard,hossam,3,..,..,..,..,..,..,..,..,...
                    //name,win,draw,lose
                    else if(new String(message. split(",")[0]).equals("scoreBoard")){
                        System.out.println("In run client thread");
                        System.out.println(message);
                        OK = 1;
                    }
                    else if(new String(message. split(",")[0]).equals("readyGame")){
                        if(new String(message. split(",")[2]).equals("true")){
                            System.out.println(message);                        
                            OK=1;
                            IMY=true;
                        }
                        if(new String(message. split(",")[3]).equals("-1")){
                            IMY=false;                            
                        }
                        if(new String(message. split(",")[2]).equals("false")){
                            System.out.println(message);
                            OK=1;
                        }
                    }
                    else if(new String(message. split(",")[0]).equals("play")){
                        if(new String(message. split(",")[1]).equals(myName)){
                            System.out.println(message);
                            OK=1;
                            opponent = new String(message. split(",")[2]);
                        }
                        else if(new String(message. split(",")[2]).equals(myName)){
                            System.out.println(message);
                            OK=1;
                        }
                    }
                    else if(true){
                        //scoreBoard,hossam,3,..,..,..,..,..,..,..,..,...
                        //name,win,draw,lose
                    }
                    else;
                }
            } catch (IOException ex) {
                //ex.printStackTrace();
                System.out.println("Connection lost");
                stop();
            }
        }
    }
}