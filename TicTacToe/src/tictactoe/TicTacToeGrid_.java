/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

/**
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import static tictactoe.logic.findBestMove;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.ConditionalFeature.FXML;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Mohamed Ali
 */
public class TicTacToeGrid_ extends Application {
    /// passing vars
    String user="X";
    String computer="O";
    int level=1;
    boolean reply=true;

    private boolean playable = true;
    private boolean turnX = true;
    private String winner;
    private GridPane root = new GridPane();

    private Tile[][] board = new Tile[3][3];
    static char passboard[][] = new char [3][3];

    int passX=-1;
    int passY=-1;

    private List<Combo> combos = new ArrayList<>();
    boolean firstround = true;
    boolean returntox = false;

    GridPane createContent() {
        root.setPrefSize(300, 300);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 100);
                tile.setTranslateY(i * 100);
                board[j][i] = tile;
                passboard[j][i] = '_';
                root.getChildren().add(tile);
                System.out.print(passboard[j][i]+" ");
            }
            System.out.println(" ");
        }
        
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
            System.out.println(e.getX()+"  "+ e.getY());
            if(e.getX()>0&&e.getX()<100)
                passX=0;
            else if(e.getX()>100&&e.getX()<200)
                passX=1;
             else if(e.getX()>200&&e.getX()<300)
                 passX=2;
             if(e.getY()>0&&e.getY()<100)
                passY=0;
            else if(e.getY()>100&&e.getY()<200)
                passY=1;
            else if(e.getY()>200&&e.getY()<300)
                 passY=2;
                System.out.println(passX+"  "+ passY);
              });

        return root;
    }
    
    

    @Override
    public void start(Stage primaryStage) throws Exception {
         primaryStage.setScene(new Scene(createContent()));
         primaryStage.show();
         
         if(reply) reply();
    }
    
    
    private void saveToReply(char board[][]) throws IOException{
        File savedGame = new File("game.txt");
        FileWriter fileWriter = new FileWriter(savedGame, false); // true to append
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                fileWriter.write(board[j][i]);
            }fileWriter.write("\n");
         }
         fileWriter.close();
   }
    
 private void reply() throws FileNotFoundException{
   Scanner sc = new Scanner(new BufferedReader(new FileReader("game.txt")));
      char [][] myArray = new char[3][3];
      while(sc.hasNextLine()) {
         for (int i=0; i<myArray.length; i++) {
             String line = sc.nextLine();
            for (int j=0; j<line.length(); j++) {
               myArray[i][j] = line.charAt(j);
               if(myArray[i][j]=='x') board[j][i].drawX();
               else if (myArray[i][j]=='o')board[j][i].drawO();
            }
         }
      }
      System.out.println("-------------reply-----------");
      for(int i=0;i<3;i++){
          for (int j=0;j<3;j++){
               System.out.print(myArray[i][j]);
          }System.out.print("\n");
      }
   }
   
    
    private void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                winner =combo.tiles[0].getValue();
                System.out.println("the winner player is : "+winner);
                playWinAnimation(combo);
                break;
            }
        }
    }
    
    
        private void playWinAnimation(Combo combo){
        Line line = new Line();
        line.setStartX(combo.tiles[0].getCenterX());
        line.setStartY(combo.tiles[0].getCenterY());
        line.setEndX(combo.tiles[0].getCenterX());
        line.setEndY(combo.tiles[0].getCenterY());
        line.setStroke(Color.RED);
        line.setStrokeWidth(5);
        combo.tiles[0].setBackground(new Background(new BackgroundFill(Color.CYAN,
                                        CornerRadii.EMPTY, Insets.EMPTY)));;
        combo.tiles[1].setBackground(new Background(new BackgroundFill(Color.CYAN,
                                        CornerRadii.EMPTY, Insets.EMPTY)));;
        combo.tiles[2].setBackground(new Background(new BackgroundFill(Color.CYAN,
                                        CornerRadii.EMPTY, Insets.EMPTY)));;
        root.getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000),
                new KeyValue(line.endXProperty(), combo.tiles[2].getCenterX()),
                new KeyValue(line.endYProperty(), combo.tiles[2].getCenterY())));
        timeline.play();
    }

    private class Combo {
        private Tile[] tiles;
        public Combo(Tile... tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty())
                return false;

            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }
        
        public void AI(){
            System.out.println("hello combo");
            if(tiles[0].getValue().equals(tiles[1].getValue())){
                tiles[2].drawO();
                returntox = true;
                System.out.println("hello combo 1 ");
            }
                
            else if (tiles[0].getValue().equals(tiles[2].getValue())){
                tiles[1].drawO();
                returntox = true;
                System.out.println("hello combo 3 ");
            }
            else if (tiles[1].getValue().equals(tiles[2].getValue())){
                tiles[0].drawO();
                returntox = true;
                System.out.println("hello combo 4");
            }
        }
    }

    private class Tile extends StackPane {
        private Text text = new Text();
        //Combo complay = new Combo();
        public Tile() {
            Rectangle border = new Rectangle(100, 100);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            getChildren().addAll(border,text);
            text.setFont(Font.font(72));
            setOnMouseClicked(event -> {
                if (!playable)
                    return;

                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX)
                        return;
                        passboard[passX][passY]='x';
                        drawX();
                        turnX = false;
                        checkState();
                        AITurn(level);
                    try {
                        saveToReply(passboard);
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacToeGrid_.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    if (turnX)
                        return;
                        drawO();
                        turnX = true;
                        checkState();
                }
            });
        }        

    public void AITurn(int level){
      if (!playable)
          return;
          switch (level){
          case 1:{
             for (int j=0;j<3;j++){
             for (int i =0;i<3;i++){
             if (board[j][i].getValue().isEmpty()){
             board[j][i].drawO();
             turnX = true;
             checkState();
              return;
              }
           }
        }
      }
       break;
       case 2:{
                  /* if (firstround){
                if(board[0][0].getValue().isEmpty()){
                    board[0][0].drawO();
                    firstround = false;
                }
                else{
                    board[1][1].drawO();
                    firstround = false;
                }
            }
            else {
                System.out.println("hello");
                for (Combo combo : combos){
                    
                    combo.AI();
                    if(returntox){
                        turnX = true;
                        checkState();
                        returntox = false;
                        break;
                    }
                }
            }
            
            turnX = true;
            checkState();*/
              
                 }break;
                 case 3:{
                     
                    logic.Move bestMove = findBestMove(passboard);
                    passboard[bestMove.row][bestMove.col]='o';
                    board[bestMove.row][bestMove.col].drawO();
                    checkState();
                    turnX=true;
                    
                       for (int i = 0; i < 3; i++) {
                         for (int j = 0; j < 3; j++) {
                          System.out.print(passboard[j][i]+" ");
                        }
                        System.out.println(" ");
                    }
                 }break;
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
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
