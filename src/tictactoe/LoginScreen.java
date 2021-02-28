package tictactoe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import client.ClientThread;

public class LoginScreen extends AnchorPane {

    protected final Label label;
    protected final Label label0;
    protected final TextField textField;
    protected final TextField textField0;
    protected final Button button;
    protected final Button button0;
    protected final ImageView imageView;
    private ClientThread client = ClientThread.getInstance();

    final Stage currentWindow;

    public LoginScreen(Stage myCurrentWindow) {
        currentWindow = myCurrentWindow;
        label = new Label();
        label0 = new Label();
        textField = new TextField();
        textField0 = new TextField();
        button = new Button();
        button0 = new Button();
        imageView = new ImageView();

        label.setLayoutX(69.0);
        label.setLayoutY(151.0);
        label.setPrefHeight(18.0);
        label.setPrefWidth(61.0);
        label.setText("Username");

        label0.setLayoutX(70.0);
        label0.setLayoutY(183.0);
        label0.setText("Password");

        textField.setLayoutX(147.0);
        textField.setLayoutY(147.0);

        textField0.setLayoutX(147.0);
        textField0.setLayoutY(179.0);

        button.setLayoutX(147.0);
        button.setLayoutY(237.0);
        button.setMnemonicParsing(false);
//        button.setStyle("-fx-background-radius: 1em;");
        button.setText("Login");
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                String username = textField.getText();
                String pass = textField0.getText();
                access(username, pass, "login");
//                Scene s = new Scene(new MainWindow(currentWindow));
//                s.getStylesheets().add(getClass().getResource("MyStyle.css").toString());
//                currentWindow.setScene(s);
                }
            }
        );

        button0.setLayoutX(231.0);
        button0.setLayoutY(237.0);
        button0.setMnemonicParsing(false);
//        button0.setStyle("-fx-background-radius: 1em;");
        button0.setText("Sign up");
        button0.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                String username = textField.getText();
                String pass = textField0.getText();
                access(username, pass, "register");
//                AnchorPane main = new MainWindow(currentWindow);
//                main.setId("main");
//                Scene s = new Scene(main);
//                s.getStylesheets().add(getClass().getResource("MyStyle.css").toString());
//                currentWindow.setScene(s);
//                currentWindow.setScene(new Scene(new MainWindow(currentWindow)));
            }
        });

        imageView.setFitHeight(400.0);
        imageView.setFitWidth(321.0);
        imageView.setLayoutX(315.0);
        imageView.setLayoutY(50.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("logo.png").toExternalForm()));
        button.setId("button");
        button0.setId("button0");
        textField.setId("textField");
        textField0.setId("textField0");

        getChildren().add(label);
        getChildren().add(label0);
        getChildren().add(textField);
        getChildren().add(textField0);
        getChildren().add(button);
        getChildren().add(button0);
        getChildren().add(imageView);
    }
    private void access(String username, String pass, String type){
            client.setData(username);
            client.ps.println(type+","+username+","+pass);
            boolean answerFlag = false;
            while(!answerFlag){
                System.out.println("");
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
                client.OK = 2;
                AnchorPane main = new MainWindow(currentWindow);
                main.setId("main");
                Scene s = new Scene(main);
                s.getStylesheets().add(getClass().getResource("mainWindowStyle.css").toString());
                currentWindow.setScene(s);
                answerFlag=false;
        }
    }
}
