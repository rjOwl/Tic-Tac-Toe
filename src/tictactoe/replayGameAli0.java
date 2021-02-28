///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package javaapplication15;
//
///**
// *
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.PrintWriter;
//import static java.lang.Integer.parseInt;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Scanner;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import static javaapplication15.logic.findBestMove;
//import javafx.animation.KeyFrame;
//import javafx.animation.KeyValue;
//import javafx.animation.Timeline;
//import javafx.application.Application;
//import static javafx.application.Application.launch;
//import static javafx.application.ConditionalFeature.FXML;
//import javafx.application.Platform;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.input.MouseButton;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.CornerRadii;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.media.MediaView;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Line;
//import javafx.scene.shape.Shape;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
///**
// *
// * @author Mohamed Ali
// */
//public class JavaApplication15 extends Application {
//    /// passing vars
//    String user="X";
//    String computer="O";
//    int level=3;
//    
//    
//    private boolean playable = true;
//    private boolean turnX = true;
//    private String winner;
//    private Pane root = new Pane();
//    
//   
//    
//    private Tile[][] board = new Tile[3][3];
//    static char passboard[][] = new char [3][3];
//    
//    int passX=-1;
//    int passY=-1;
//    int posX = 0;
//    int posY = 0;
//    String xoro;
//                        
//    private List<Combo> combos = new ArrayList<>();
//    boolean firstround = true;
//    boolean returntox = false;
//    MediaPlayer mediaplayer;
//    Pane winvideo;
//    Scene winscene;
//    Stage window;
//    
//    private Parent createContent() {
//        root.setPrefSize(600, 600);
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                Tile tile = new Tile();
//                tile.setTranslateX(j * 200);
//                tile.setTranslateY(i * 200);
//                board[j][i] = tile;
//                passboard[j][i] = '_';
//                root.getChildren().add(tile);
//                System.out.print(passboard[j][i]+" ");
//            }
//            System.out.println(" ");
//        }
//       // replayGame();
//        
//        for (int y = 0; y < 3; y++) {
//            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
//        }
//
//        // vertical
//        for (int x = 0; x < 3; x++) {
//            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
//        }
//
//        // diagonals
//        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
//        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));
//  
//    
//       
//
//  root.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
//   
//      System.out.println(e.getX()+"  "+ e.getY());
//      
//      if(e.getX()>0&&e.getX()<200)
//          passX=0;
//      else if(e.getX()>200&&e.getX()<400)
//          passX=1;
//       else if(e.getX()>400&&e.getX()<600)
//           passX=2;
//       if(e.getY()>0&&e.getY()<200)
//          passY=0;
//      else if(e.getY()>200&&e.getY()<400)
//          passY=1;
//      else if(e.getY()>400&&e.getY()<600)
//           passY=2;
//             System.out.println(passX+"  "+ passY);
//        });
//    
//    return root;
// }
//    
//    
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//         primaryStage.setScene(new Scene(createContent()));
//        window = primaryStage;
//         primaryStage.show();
//         
//    }
//    
//    private void checkState() {
//        for (Combo combo : combos) {
//            if (combo.isComplete()) {
//                playable = false;
//                winner =combo.tiles[0].getValue();
//                System.out.println("the winner player is : "+winner);
//               playWinAnimation(combo);
//                break;
//            }
//        }
//    }
//    
//    private void playWinAnimation(Combo combo){
//        Line line = new Line();
//        line.setStartX(combo.tiles[0].getCenterX());
//        line.setStartY(combo.tiles[0].getCenterY());
//        line.setEndX(combo.tiles[0].getCenterX());
//        line.setEndY(combo.tiles[0].getCenterY());
//        line.setStroke(Color.RED);
//        line.setStrokeWidth(5);
//        combo.tiles[0].setBackground(new Background(new BackgroundFill(Color.CYAN,
//                                        CornerRadii.EMPTY, Insets.EMPTY)));;
//        combo.tiles[1].setBackground(new Background(new BackgroundFill(Color.CYAN,
//                                        CornerRadii.EMPTY, Insets.EMPTY)));;
//        combo.tiles[2].setBackground(new Background(new BackgroundFill(Color.CYAN,
//                                        CornerRadii.EMPTY, Insets.EMPTY)));;
//        root.getChildren().add(line);
//
//        Timeline timeline = new Timeline();
//        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000),
//                new KeyValue(line.endXProperty(), combo.tiles[2].getCenterX()),
//                new KeyValue(line.endYProperty(), combo.tiles[2].getCenterY())));
//        
//        timeline.play();
//        
//        winvideo = new Pane();
//        Button back = new Button("Back to main menu");
//        back.setOnAction(e -> window.setScene(new Scene(createContent())));
//        Media videofile = new Media("file:///E:/iti/java/testfile/src/testfile/30SecondTimer.mp4");
//        mediaplayer = new MediaPlayer(videofile);
//        mediaplayer.setAutoPlay(true);
//        mediaplayer.setVolume(.9);
//        MediaView mediaview = new MediaView(mediaplayer);
//        //root.getChildren().add(mediaview);
//        winvideo.getChildren().addAll(mediaview,back);
//        winscene = new Scene(winvideo,444,444);
//        window.setScene(winscene);
//    }
//    
//    private class Combo {
//        private Tile[] tiles;
//        public Combo(Tile... tiles) {
//            this.tiles = tiles;
//        }
//
//        public boolean isComplete() {
//            if (tiles[0].getValue().isEmpty())
//                return false;
//
//            return tiles[0].getValue().equals(tiles[1].getValue())
//                    && tiles[0].getValue().equals(tiles[2].getValue());
//        }
//        
//        public void AI(Combo combo){
//            System.out.println("hello combo");
//            if(combo.tiles[0].getValue().equals(combo.tiles[1].getValue())){
//                combo.tiles[2].drawO();
//                returntox = true;
//                System.out.println("hello combo 1 ");
//            }
//                
//            else if (combo.tiles[0].getValue().equals(combo.tiles[2].getValue())){
//                combo.tiles[1].drawO();
//                returntox = true;
//                System.out.println("hello combo 3 ");
//            }
//            else if (combo.tiles[1].getValue().equals(combo.tiles[2].getValue())){
//                combo.tiles[0].drawO();
//                returntox = true;
//                System.out.println("hello combo 4");
//            }
//        }
//    }
//    
//   public void replayGame(){
//        try {
//            FileInputStream fis = new FileInputStream("savelastgame.txt");
//            Scanner sc = new Scanner(fis);
//            
//            while(sc.hasNext()){
//                try{
//                String li = sc.nextLine();
//                String[] strarray = li.split(":");
//                System.out.println(strarray[2]);
//                 posX = parseInt(strarray[0]);
//                 posY = parseInt(strarray[1]);
//                 xoro = strarray[2];
//                 
//                 
//                /* new Thread(new Runnable() {
//                                    @Override public void run() {
//                                        for (int i =0 ; i<9 ; i++){
//                                        try {
//                                            Thread.sleep(10000);
//                                        } catch (InterruptedException ex) {
//                                            Logger.getLogger(JavaApplication15.class.getName()).log(Level.SEVERE, null, ex);
//                                        }
//                                        Platform.runLater(new Runnable() {
//                                            @Override public void run() {
//                                                  if(new String (xoro).equals("x")){
//                                                 
//                                                                board[posX][posY].drawX();
//                                                                System.out.println("ddddddd");
//                                                            }
//                                                            else if (new String (xoro).equals("o"))
//                                                                board[posX][posY].drawO();
//                                                                                            }
//                                                                                    }
//                                                                                );}
//                                                                      }
//                                                                            }).start();*/
//               
//                 
//                 
//                 
//                /*Timer timer = new Timer();
//                timer.schedule(new TimerTask(){
//                @Override
//                public void run() {
//                    System.out.println("wait is over");
//                     }
//        
//                 }, 2000);*/
//                 
//                 
//                 //Thread.currentThread().sleep(3000);
//                 }catch (Exception e){  }
//                 
//                 
//                /*sc.useDelimiter("[^0-9]+");
//                posX = sc.nextInt();
//                System.out.println(posX);
//               posY = sc.nextInt();
//                System.out.println(posY);
//                sc.useDelimiter("[^A-Za-z]+");
//                xoro = sc.nextLine();
//                System.out.println(xoro);
//                if(xoro == "x"){
//                    board[posY][posX].drawX();
//                    System.out.println("ddddddd");
//                }
//                else 
//                    board[posY][posX].drawO();
//                    */
//                
//            }
//            } catch (FileNotFoundException ex) {  }
//        
//   }
//    
//    
//    private class Tile extends StackPane {
//        private Text text = new Text();
//        //Combo complay = new Combo();
//        public Tile() {
//            Rectangle border = new Rectangle(200, 200);
//            border.setFill(null);
//            border.setStroke(Color.BLACK);
//            
//            getChildren().addAll(border,text);
//            text.setFont(Font.font(72));
//            setAlignment(Pos.CENTER);
//            setOnMouseClicked(event -> {
//                if (!playable)
//                    return;
//
//                if (event.getButton() == MouseButton.PRIMARY) {
//                    /*if (!turnX)
//                        return;
//                    
//                    passboard[passX][passY]='x';
//                    try {
//                        FileOutputStream fos = new FileOutputStream("savelastgame.txt",true);
//                        PrintWriter pw = new PrintWriter(fos);
//                        
//                        pw.println(passX+":"+passY);
//                        pw.close();
//                    } catch (FileNotFoundException ex) { }
//                    drawX();
//                    turnX = false;
//                    checkState();
//                    AITurn(level);*/
//                    replayGame();
//                }
//               /*else if (event.getButton() == MouseButton.SECONDARY) {
//                    if (turnX)
//                        return;
//                        drawO();
//                        turnX = true;
//                        checkState();
//                }*/
//            });
//        }        
//  public void AITurn(int level){
//      if (!playable)
//          return;
//          switch (level){
//          case 1:{
//             for (int j=0;j<3;j++){
//             for (int i =0;i<3;i++){
//             if (board[j][i].getValue().isEmpty()){
//             board[j][i].drawO();
//             turnX = true;
//             checkState();
//              return;
//              }
//           }
//        }
//      }
//       break;
//       case 2:{
//                  if (firstround){
//                if(board[0][0].getValue().isEmpty()){
//                    board[0][0].drawO();
//                    firstround = false;
//                }
//                else{
//                    board[1][1].drawO();
//                    firstround = false;
//                }
//            }
//            else {
//                System.out.println("hello");
//                for (Combo combo : combos){
//                    
//                    combo.AI(combo);
//                    if(returntox){
//                        turnX = true;
//                        checkState();
//                        returntox = false;
//                        break;
//                    }
//                }
//            }
//            
//            turnX = true;
//            checkState();
//              
//                 }break;
//                 case 3:{
//                     
//                    logic.Move bestMove = findBestMove(passboard);
//                    passboard[bestMove.row][bestMove.col]='o';
//                    board[bestMove.row][bestMove.col].drawO();
//                    try {
//                        FileOutputStream fos = new FileOutputStream("savelastgame.txt",true);
//                        PrintWriter pw = new PrintWriter(fos);
//                        
//                        pw.println(bestMove.row+":"+bestMove.col+":ooooo");
//                        pw.close();
//                    } catch (FileNotFoundException ex) { }
//                    checkState();
//                    turnX=true;
//                    
//                       for (int i = 0; i < 3; i++) {
//                         for (int j = 0; j < 3; j++) {
//                          System.out.print(passboard[j][i]+" ");
//                        }
//                        System.out.println(" ");
//                    }
//                 }break;
//             }
//        }
//        
//        public double getCenterX() {
//            return getTranslateX() + 100;
//        }
//
//        public double getCenterY() {
//            return getTranslateY() + 100;
//        }
//  
//        public String getValue() {
//            return text.getText();
//        }
//        
//        private void drawX() {
//            
//            text.setText(user);
//            Scanner sc = new Scanner(System.in);
//        }
//
//        public void drawO() {
//            text.setText(computer);
//        }
//    }
//    
//    public static void main(String[] args) {
//        launch(args);
//    }
//    
//}
//
