/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication15;

/**
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static javaapplication15.logic.findBestMove;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.ConditionalFeature.FXML;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Mohamed Ali
 */
public class JavaApplication15 extends Application {
    /// passing vars
    String user="X";
    String computer="O";
    int level=3;
    
    
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
    
    private Parent createContent() {
        root.setPrefSize(600, 600);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);
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
      
      if(e.getX()>0&&e.getX()<200)
          passX=0;
      else if(e.getX()>200&&e.getX()<400)
          passX=1;
       else if(e.getX()>400&&e.getX()<600)
           passX=2;
       if(e.getY()>0&&e.getY()<200)
          passY=0;
      else if(e.getY()>200&&e.getY()<400)
          passY=1;
      else if(e.getY()>400&&e.getY()<600)
           passY=2;
             System.out.println(passX+"  "+ passY);
        });
    
    return root;
 }
    
    

    @Override
    public void start(Stage primaryStage) throws Exception {
         primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
    
    private void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                winner =combo.tiles[0].getValue();
                System.out.println("the winner player is : "+winner);
               // playWinAnimation(combo);
                break;
            }
        }
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
            Rectangle border = new Rectangle(200, 200);
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
