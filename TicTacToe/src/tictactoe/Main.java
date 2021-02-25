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
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane currentWindow = new LoginScreen(stage);
        currentWindow.setId("main");

        Scene scene = new Scene(currentWindow);
        scene.getStylesheets().add(getClass().getResource("MyStyle.css").toString());
        stage.setResizable(false);
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
