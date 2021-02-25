package tictactoe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

class ClientThread extends Thread {
    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;
    String myName;
    Stage currentWindow;
    int OK=2;
    public void setData(String name, Stage currentWindow) {
        this.myName = name;
        this.currentWindow = currentWindow;
    }

    public ClientThread(String name){
        try {
            mySocket = new Socket("102.47.243.156", 5005);
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

    public void run() {
        while (true) {
            try {
                String message = dis.readLine();
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
                        int n=Integer.parseInt(message.split(",")[2]);
                        n = n * 4;
                        for(int i = 3; i < n+3; i++){
                        }
                    }
                    else if(true){
                        //scoreBoard,hossam,3,..,..,..,..,..,..,..,..,...
                        //name,win,draw,lose
                    }
                }
            } catch (IOException ex) {
                //ex.printStackTrace();
                System.out.println("Connection lost");
                stop();
            }
        }
    }
}