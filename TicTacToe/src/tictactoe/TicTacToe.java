///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Mufasa
 */
public class TicTacToe extends Application {
        
    @Override
    public void start(Stage stage) throws Exception {
        
        int yes=0;
        AnchorPane currentWindow = null;
        if(yes == 0){
            currentWindow = new LoginScreen(stage);
        }
        else{
            currentWindow = new MainWindow(stage);
        }
        Scene scene = new Scene(currentWindow);
//        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
