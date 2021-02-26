package client;

import java.net.*;
import java.io.*;
import javafx.stage.Stage;

public class ClientThread extends Thread {
    public Socket mySocket;
    public DataInputStream dis;
    public PrintStream ps;
    public String myName, opponent, message;
    public Stage currentWindow;
    public int OK=2;
    public boolean IMY=false;
    private static ClientThread client = null;

    public void setData(String name) {
        this.myName = name;
//        this.currentWindow = currentWindow;
    }

    public static ClientThread getInstance(){
        if(client == null)
            client = new ClientThread("");
        return client;
    }

    public static void resetClient(){
        client = null;
    }

    private ClientThread(String name){
        try{
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
                    if(signing(message)){}
                    // scoreBoard,hossam,requested
                    //scoreBoard,hossam,3,..,..,..,..,..,..,..,..,...
                    //name,win,draw,lose
                    else if(scoreBoard(message)){}
                    else if(readyGame(message)){}
                    else if(play(message)){}
                    else;
                }
            } catch (IOException ex) {
                //ex.printStackTrace();
                System.out.println("Connection lost");
                stop();
            }
        }
    }
        private boolean signing(String message){
        if( message.split(",")[0].equals("login")
            || message.split(",")[0].equals("register")){
            if(new String(message.split(",")[2]).equals("true")){
                OK=1;
                return true;               
            }
        }
        else{
                OK=0;
        }
        return false;
    }
    private boolean scoreBoard(String message){
        if(message. split(",")[0].equals("scoreBoard")){
            if(new String(message.split(",")[2]).equals("true")){
                OK = 1;
                return true;                
            }
        }
        else OK=0;
        return false;
    }

    private boolean readyGame(String message){
        if(new String(message. split(",")[0]).equals("readyGame")){
            if(new String(message. split(",")[2]).equals("true")){
                System.out.println(message);               
                OK=1;
                IMY=true;
            }
            else if(new String(message. split(",")[2]).equals("false")) OK=1;
            return true;
        }
        return false;
    }

    private boolean play(String message){
        if(message.split(",")[0].equals("play")){
            if(message.split(",")[1].equals(myName)){
                OK=1;
                opponent = new String(message. split(",")[2]);
            }
            else if(message. split(",")[2].equals(myName)){
                OK=1;
            }
            return true;
        }
        return false;
    }
}


