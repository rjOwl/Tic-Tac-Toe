package tictactoe;

import client.ClientThread;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static tictactoe.logic.findBestMove;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tictactoe.MainWindow.GameType;

/**
 *
 * @author Mohamed Ali
 */
public class TicGridAli{

    /// passing vars
    GameType gameType;
    private final ClientThread client = ClientThread.getInstance();
    private Tile[][] board = new Tile[3][3];
    private final Pane root = new Pane();

    MediaPlayer mediaplayer;
    Pane winvideo;
    Scene winscene;
    Stage window;

    Thread updatableThread = null;
    static char passboard[][] = new char[3][3];
    int level, passX = -1, passY = -1, posX = 0, posY = 0;
    private boolean playable = true, turnX = true, firstround = true, returntox = false;
    private String winner, user = "X", computer = "O", xoro;

    private List < Combo > combos = new ArrayList < > ();

    Pane createContent(GameType type, int level) {
        resetBoard();
        gameType = type;
        this.level = level;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 100);
                tile.setTranslateY(i * 100);
                board[j][i] = tile;
                passboard[j][i] = '_';
                root.getChildren().add(tile);
                System.out.print(passboard[j][i] + " ");
            }
            System.out.println(" ");
        }
        // replayGame();

        for (int y = 0; y < 3; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
        }

        // vertical
        for (int x = 0; x < 3; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
        }

        // diagonals
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));


        root.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            System.out.println(e.getX() + "  " + e.getY());

            if (e.getX() > 0 && e.getX() < 100)
                passX = 0;
            else if (e.getX() > 100 && e.getX() < 200)
                passX = 1;
            else if (e.getX() > 200 && e.getX() < 300)
                passX = 2;
            if (e.getY() > 0 && e.getY() < 100)
                passY = 0;
            else if (e.getY() > 100 && e.getY() < 200)
                passY = 1;
            else if (e.getY() > 200 && e.getY() < 300)
                passY = 2;
            System.out.println(passX + "  " + passY);
        });

        return root;
    }

    //        replayGame();

    private boolean checkState() {
        for (Combo combo: combos) {
            if (combo.isComplete()) {
                playable = false;
                winner = combo.tiles[0].getValue();
                System.out.println("the winner player is : " + winner);
                playWinAnimation(combo);
                return true;
            }
        }
        return false;
    }

    private void playWinAnimation(Combo combo) {
        Line line = new Line();
        line.setStartX(combo.tiles[0].getCenterX());
        line.setStartY(combo.tiles[0].getCenterY());
        line.setEndX(combo.tiles[0].getCenterX());
        line.setEndY(combo.tiles[0].getCenterY());
        line.setStroke(Color.RED);
        line.setStrokeWidth(5);
        root.getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4000),
            new KeyValue(line.endXProperty(), combo.tiles[2].getCenterX()),
            new KeyValue(line.endYProperty(), combo.tiles[2].getCenterY())));
        timeline.play();
        /*Stage secondStage = new Stage();
        winvideo = new Pane();
        
        Media videofile = new Media("file:///C:/Users/Mufasa/Videos/hi.mp4");
        mediaplayer = new MediaPlayer(videofile);
        mediaplayer.setAutoPlay(true);
        mediaplayer.setVolume(.9);
        MediaView mediaview = new MediaView(mediaplayer);
        mediaview.fitWidthProperty().bind(secondStage.widthProperty());
        mediaview.fitHeightProperty().bind(secondStage.heightProperty());
        root.getChildren().add(mediaview);
        winvideo.getChildren().addAll(mediaview);
        winscene = new Scene(winvideo,444,444);
        secondStage.setScene(winscene);
        secondStage.show();
        Popup.display();*/
    }

    private class Combo {
        private Tile[] tiles;
        public Combo(Tile...tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty())
                return false;

            return tiles[0].getValue().equals(tiles[1].getValue()) &&
                tiles[0].getValue().equals(tiles[2].getValue());
        }

        public void AI(Combo combo) {
            System.out.println("hello combo");
            if (combo.tiles[0].getValue().equals(combo.tiles[1].getValue())) {
                combo.tiles[2].drawO();
                returntox = true;
                System.out.println("hello combo 1 ");
            } else if (combo.tiles[0].getValue().equals(combo.tiles[2].getValue())) {
                combo.tiles[1].drawO();
                returntox = true;
                System.out.println("hello combo 3 ");
            } else if (combo.tiles[1].getValue().equals(combo.tiles[2].getValue())) {
                combo.tiles[0].drawO();
                returntox = true;
                System.out.println("hello combo 4");
            }
        }
    }

    public void replayGame() {
        try {
            FileInputStream fis = new FileInputStream("savelastgame.txt");
            Scanner sc = new Scanner(fis);

            while (sc.hasNext()) {
                String li = sc.nextLine();
                String[] strarray = li.split(":");
                System.out.println(strarray[2]);
                posX = parseInt(strarray[0]);
                posY = parseInt(strarray[1]);
                xoro = strarray[2];
                if (new String(xoro).equals("x")) {
                    board[posX][posY].drawX();
                    System.out.println("ddddddd");
                } else if (new String(xoro).equals("o"))
                    board[posX][posY].drawO();
                /*sc.useDelimiter("[^0-9]+");
                posX = sc.nextInt();
                System.out.println(posX);
               posY = sc.nextInt();
                System.out.println(posY);
                sc.useDelimiter("[^A-Za-z]+");
                xoro = sc.nextLine();
                System.out.println(xoro);
                if(xoro == "x"){
                    board[posY][posX].drawX();
                    System.out.println("ddddddd");
                }
                else 
                    board[posY][posX].drawO();
                    */

            }
        } catch (FileNotFoundException ex) {}

    }


    private class Tile extends StackPane {
        private Text text = new Text();

        //Combo complay = new Combo();
        public Tile() {
            Rectangle border = new Rectangle(100, 100);
            border.setFill(null);

//            border.setFill(new ImagePattern(img));
            border.setArcWidth(70.0); 
            border.setArcHeight(50.0);

            border.setStroke(Color.BLACK);
            getChildren().addAll(border, text);
            text.setFont(Font.font(72));
            setAlignment(Pos.CENTER);
            setOnMouseClicked(event -> {
                handlePlaying(gameType, event);
            });
        }
        public void AITurn(int level){
            if (!playable)
                return;
            switch (level) {
                case 1:
                    {
                        for (int j = 0; j < 3; j++) {
                            for (int i = 0; i < 3; i++) {
                                if (board[j][i].getValue().isEmpty()) {
                                    board[j][i].drawO();
                                    turnX = true;
                                    checkState();
                                    return;
                                }
                            }
                        }
                    }
                    break;
                case 2:
                    {
                        if (firstround) {
                            if (board[0][0].getValue().isEmpty()) {
                                board[0][0].drawO();
                                firstround = false;
                            } else {
                                board[1][1].drawO();
                                firstround = false;
                            }
                        } else {
                            System.out.println("hello");
                            for (Combo combo: combos) {
                                combo.AI(combo);
                                if (returntox) {
                                    turnX = true;
                                    checkState();
                                    returntox = false;
                                    break;
                                }
                            }
                        }
                        turnX = true;
                        checkState();
                    }
                    break;
                case 3:
                    {
                        logic.Move bestMove = findBestMove(passboard);
                        passboard[bestMove.row][bestMove.col] = 'o';
                        board[bestMove.row][bestMove.col].drawO();
                        try {
                            FileOutputStream fos = new FileOutputStream("savelastgame.txt", true);
                            PrintWriter pw = new PrintWriter(fos);

                            pw.println(bestMove.row + ":" + bestMove.col + ":ooooo");
                            pw.close();
                        } catch (FileNotFoundException ex) {}
                        checkState();
                        turnX = true;

                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                System.out.print(passboard[j][i] + " ");
                            }
                            System.out.println(" ");
                        }
                    }
                    break;
            }
        }

        public double getCenterX() {
            return getTranslateX() + 50;
        }

        public double getCenterY() {
            return getTranslateY() + 50;
        }

        public String getValue() {
            return text.getText();
        }

        private void drawX() {
            text.setText(user);
        }

        public void drawO() {
            text.setText(computer);
        }

        private void handlePlaying(GameType gameType, MouseEvent event){
            if (gameType != GameType.None){
                if (!playable)
                    return;
                switch(gameType){
                    case AI:
                        if (event.getButton() == MouseButton.PRIMARY) {
                            rightClickHandler();
                        }
                        if (level !=-1 && !turnX){
                            System.out.println("AI Turn");
                            AITurn(level);
                        }
                        break;
                    case Local:
                        if (event.getButton() == MouseButton.PRIMARY) {
                            rightClickHandler();
                        }
                        else if (event.getButton() == MouseButton.SECONDARY){
                            if (turnX) return;
                            
                            if (text.getText() == ""){
                                 drawO();
                                  turnX = true;}
                            checkState();
                        }
                        break;
                    case Room:
                        if(checkState())
                                break;
                        if (event.getButton() == MouseButton.PRIMARY) {
                            
                            rightClickHandler();
                            if(client.IMY) board[passX][passY].drawO();
                            else board[passX][passY].drawX();
                            String msg = playNetwork(client.myName, client.opponent, passX, passY);
                            if(!msg.isEmpty()){
                                
                                int x = parseInt(new String(msg. split(",")[3]));
                                int y = parseInt(new String(msg. split(",")[4]));
                                    if(client.IMY) board[x][y].drawX();
                                        else board[x][y].drawO();
                                    if(checkState())
                                        break;
                            }
                            
                        }
                        if(checkState())
                                break;
                        break;
                    default:
                    break;
                }
            }
        }
        private void rightClickHandler(){
            if (!turnX) return;
            else{
                passboard[passX][passY] = 'x';
//                drawX();
                if (text.getText() == "")
                    text.setText(user);
                turnX = false;
                checkState();
            }
        }
    }
    private void resetBoard(){
        user = "X";
        computer = "O";
        level=-1;
        playable = true;
        turnX = true;
        winner="";
        board = new Tile[3][3];
        TicGrid.passboard = new char[3][3];
        passX = -1;
        passY = -1;
        posX = 0;
        posY = 0;
//        AIEnabled=false;
//        roomEnabled = false;
//        optionBtnClicked=false;
        xoro="";
        combos = new ArrayList < > ();
        firstround = true;
        returntox = false;
    }
    
    private void threadHandling(String myName, String opponent, int posX, int posY){
        Thread taskThread = new Thread(new Runnable() {
      @Override
      public void run() {
        double progress = 0;
        for(int i=0; i<10; i++){

           playNetwork( myName,  opponent,  posX,  posY);

          progress += 0.1;
          final double reportedProgress = progress;

          Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                function 
        
            }
          });
        }
      }
    });
    }
    //play,hossam,chris,0,0
    private String playNetwork(String myName, String opponent, int posX, int posY){
        client.ps.println("play,"+myName+","+opponent+","+posX+","+posY);
        boolean answerFlag = false;
        while(!answerFlag){
            System.out.println("HAAAAI");
            if(client.OK == 1){
                System.out.println("OK");
                answerFlag=true;
            }
            if(client.OK == 0){
                System.out.println("NOT OK :(");
                answerFlag=true;
                client.OK = 2;
            }
        }
        if(client.OK == 1){
            String response = client.getMessage();
            client.OK = 2;
            answerFlag=false;
            return response;
        }
        return "";
    }
    
}

/*
private void threadHandling(String myName, String opponent, int posX, int posY){
        Thread taskThread = new Thread(new Runnable() {
      @Override
      public void run() {
        double progress = 0;
        for(int i=0; i<10; i++){

           playNetwork( myName,  opponent,  posX,  posY);

          progress += 0.1;
          final double reportedProgress = progress;

          Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                function 
            }
          });
        }
      }
    });
    }
*/