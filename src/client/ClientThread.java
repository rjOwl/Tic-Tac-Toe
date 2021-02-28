package client;

import java.net.*;
import java.io.*;
import static java.lang.Integer.parseInt;
import javafx.stage.Stage;
import tictactoe.TicGrid;

public class ClientThread extends Thread {
    public Socket mySocket;
    public DataInputStream dis;
    public PrintStream ps;
    public String myName, opponent, message;
    public Stage currentWindow;
    public int OK=2;
    public boolean IMY=false;
    public int guiThreadCreated=1;
    public boolean PLAY=true;
    private static ClientThread client = null;
    TicGrid obj;
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
//            mySocket = new Socket("41.237.192.233", 5005);
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
            System.out.println("In method Scoreboard");
            int rows = parseInt(message.split(",")[2]);
            if(rows > 0){
                System.out.println(message);
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
                System.out.println("FOR ME: "+message);
                OK=1;
                opponent = new String(message. split(",")[2]);
                return true;
            }
        }
        return false;
    }
    private boolean playFromServer(String message){
        if(message.split(",")[0].equals("play")){
//                System.out.println("PLAY: "+message);
            if(message.split(",")[1].equals(myName)){
                System.out.println("FOR ME: "+message);
                if(message.split(",").length == 6){
                    System.out.println("LENGTH 5: "+message);
                    if(message.split(",")[5].equals("non")){
                        System.out.println("IN CLIENT PLAYER MADE A MOVE: "+message);
                        int x = parseInt(message.split(",")[3]);
                        int y = parseInt(message.split(",")[4]);
                        System.out.println("IN CLIENT PLAYER X: "+x+"  Y: "+y);

                        TicGrid.drawThread(x, y);
//                        obj.board[x][y].drawO();
                    }
                    else if(message.split(",")[5].equals(myName)){
                        System.out.println("IN CLIENT PLAYER MADE A MOVE: "+message);
                        int x = parseInt(message.split(",")[3]);
                        int y = parseInt(message.split(",")[4]);
                        System.out.println("IN CLIENT PLAYER X: "+x+"  Y: "+y);

                        TicGrid.drawThreadO(x, y);
//                        obj.board[x][y].drawO();
                    }
                }
                OK=1;
                opponent = new String(message. split(",")[2]);
//                System.out.println(message);               
            }
//            else if(message.split(",")[2].equals(myName)){
//                OK=1;
//            }
            return true;
        }
        return false;
    }
    
    public boolean endGame(){
        if(message.split(",")[0].equals("endGame")){
            
        }
        return false;
    }

    public String[][] getRecords(){
        String splitted[] = message.split(",");
        int rows = parseInt(splitted[2])+1;

        String [][] data = new String[rows][4];
        data[0][0] = "Username";
        data[0][1] = "win";
        data[0][2] = "lose";
        data[0][3] = "draw";
        int n = 3;
        for(int i=1; i<rows; i++){
            for(int j=0; j<4; j++){
                data[i][j] = splitted[n++];
            }
        }
        return data;
    }
}

