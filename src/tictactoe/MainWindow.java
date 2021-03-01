package tictactoe;

import client.ClientThread;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindow extends AnchorPane {
    enum GameType { AI, Local,Room,Replay, None}
    protected final ButtonBar buttonBar;
    protected final Button button;
    protected final Button button0;
    protected final Button button1;
    protected final Label levelLabel;
    protected final RadioButton radioButton;
    protected Pane gridPane;
    protected final ImageView imageView;
    protected final Button button2;
    protected final Button button3;
    protected final Button button4;
    protected final Button button5;
    protected final TicGrid grid = new TicGrid();
    protected ComboBox comboBox = new ComboBox();
    int level;
    protected Stage mainWindow;
    boolean showLevels = false;
    Thread updatableThread = null;
    private final ClientThread client = ClientThread.getInstance();

    public MainWindow(Stage mainWindowScene) {
        this.mainWindow = mainWindowScene;
        buttonBar = new ButtonBar();
        button = new Button();
        button0 = new Button();
        button1 = new Button();
        radioButton = new RadioButton();

//        gridPane = grid.createContent(GameType.None, level=-1, 0);
        gridPane = grid.createContent(GameType.None, level=-1);
        imageView = new ImageView();
        button2 = new Button();
        button3 = new Button();
        button4 = new Button();
        button5 = new Button();
        levelLabel = new Label();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(457.0);
        setPrefWidth(610.0);

        buttonBar.setLayoutX(168.0);
        buttonBar.setLayoutY(16.0);
        buttonBar.setPrefHeight(40.0);
        buttonBar.setPrefWidth(200.0);

        button.setMnemonicParsing(false);
        button.setText("Play AI");
        button.setOnAction(new EventHandler < ActionEvent > () {
            @Override
            public void handle(ActionEvent event) {
                handleOptions(showLevels=true);
//                grid.createContent(GameType.AI, level=-1, 1);
                gridPane = grid.createContent(GameType.AI, level=-1);
                client.guiThreadCreated=1;
            }
        });

        button0.setMnemonicParsing(false);
        button0.setText("Local");
        button0.setOnAction(new EventHandler < ActionEvent > (){
            @Override
            public void handle(ActionEvent event) {
                handleOptions(showLevels=false);
                levelLabel.setText("");
                gridPane = grid.createContent(GameType.Local, level=-1);
                client.guiThreadCreated=1;
            }
        });

        button1.setMnemonicParsing(false);
        button1.setText("Room");
        button1.setOnAction(new EventHandler < ActionEvent > () {
            @Override
            public void handle(ActionEvent event) {
                handleOptions(showLevels=false);
                popUpDialog();
            }
        });

        levelLabel.setLayoutX(492.0);
        levelLabel.setLayoutY(27.0);
        //        radioButton.setMnemonicParsing(false);
        //        radioButton.setPrefHeight(17.0);
        //        radioButton.setPrefWidth(88.0);
        levelLabel.setText("Level");
        levelLabel.setLayoutX(492.0);
        levelLabel.setLayoutY(40.0);
        comboBox.setLayoutX(492.0);
        comboBox.setLayoutY(27.0);
        comboBox.setValue("Levels");
        comboBox.getItems().add("Easy");
//        comboBox.getItems().add("Level 2");
        comboBox.getItems().add("Hard");
        comboBox.setOnAction((e) -> {
            level = comboBox.getSelectionModel().getSelectedIndex();
            level += 1;
            handleOptions(showLevels=true);
            //            FIX THIS LOGIC
            gridPane = grid.createContent(GameType.AI, level);
            client.guiThreadCreated=1;
        });
        gridPane.setLayoutX(168.0);
        gridPane.setLayoutY(75.0);
        gridPane.setPrefHeight(300.0);
        gridPane.setPrefWidth(300.0);
        gridPane.setStyle("-fx-background-color: #FFFFFF;");

        button2.setLayoutX(25.0);
        button2.setLayoutY(32.0);
        button2.setMnemonicParsing(false);
        button2.setPrefHeight(40.0);
        button2.setPrefWidth(120.0);
        button2.setText("Replay game");
        button2.setOnAction(new EventHandler < ActionEvent > () {
            @Override
            public void handle(ActionEvent event) {
                handleOptions(showLevels=false);
                levelLabel.setText("");
                gridPane = grid.createContent(GameType.Replay, level=-1);
                client.guiThreadCreated=1;
            }
        });

        button3.setLayoutX(25.0);
        button3.setLayoutY(100.0);
        button3.setMnemonicParsing(false);
        button3.setPrefHeight(40.0);
        button3.setPrefWidth(120.0);
        button3.setText("Score board");
        button3.setOnAction(new EventHandler < ActionEvent > (){
            @Override
            public void handle(ActionEvent event){
            String msg = getScore(client.myName);
            Stage w =new Stage();
            w.initModality(Modality.APPLICATION_MODAL);
            w.setResizable(false);
            //                w.initStyle(StageStyle.UNDECORATED);
            Scene s = new Scene(new ScoreBoard(msg));
            w.setScene(s);
            w.showAndWait();
           }
        });

        button4.setLayoutX(25.0);
        button4.setLayoutY(170.0);
        button4.setMnemonicParsing(false);
        button4.setPrefHeight(40.0);
        button4.setPrefWidth(120.0);
        button4.setText("Logout");
        button4.setOnAction(new EventHandler < ActionEvent > () {
            @Override
            public void handle(ActionEvent event) {
                client.ps.println("logout"+","+client.myName); 
                System.out.println("loooogOut");
                client.resetClient();
                Stage w = new Stage();
                w.initModality(Modality.APPLICATION_MODAL);
                w.setResizable(false);
                Scene s = new Scene(new LoginScreen(w));
                s.getStylesheets().add(getClass().getResource("loginStyle.css").toString());
                w.setScene(s);
                w.show();
                mainWindow.close();
            }
        });

        button5.setLayoutX(270.0);
        button5.setLayoutY(400.0);
        button5.setMnemonicParsing(false);
        button5.setPrefHeight(40.0);
        button5.setPrefWidth(140.0);
        button5.setText("Cancel Game");
        button5.setOnAction(new EventHandler < ActionEvent > () {
            @Override
            public void handle(ActionEvent event) {
                client.ps.println("cancelGame"+","+client.myName);
                System.out.println("ccaaanccclllleee");}
        });

        button.setId("button");
        button0.setId("button0");
        button1.setId("button1");
        button2.setId("button2");
        button3.setId("button3");
        button4.setId("button4");
        button5.setId("button4");

        buttonBar.getButtons().add(button);
        buttonBar.getButtons().add(button0);
        buttonBar.getButtons().add(button1);
        getChildren().add(buttonBar);
        getChildren().add(comboBox);
        getChildren().add(gridPane);
        getChildren().add(button2);
        getChildren().add(button3);
        getChildren().add(button4);
        getChildren().add(button5);
    }

    private void handleOptions(boolean showLevels){
        this.comboBox.setVisible(showLevels);
        this.gridPane.getChildren().clear();
    }

  private void popUpDialog(){
        gridPane = grid.createContent(GameType.Room, level=-1);

        Stage w = new Stage();
        w.initModality(Modality.APPLICATION_MODAL);
        w.setResizable(false);
        //                w.initStyle(StageStyle.UNDECORATED);
        Scene s = new Scene(new roomDialogue(w));
        w.setScene(s);
        w.showAndWait();
        client.guiThreadCreated=0;
    }

    private String getScore(String username){
        client.ps.println("scoreBoard"+","+username+","+"requested");
        System.out.println("LOADING Scoreboard");
        boolean answerFlag = false;
        while(!answerFlag){
            System.out.println("LOADING Scoreboard");
            if(client.OK == 1){
                answerFlag=true;
            }
            if(client.OK == 0){
                answerFlag=true;
                client.OK = 2;
            }
        }
        if(client.OK == 1){
            client.OK = 2;
            String rows = client.message;
            return rows;
        }
        return "";
    }

    private void showScoreBoard(String username){
        Stage w = new Stage();
        client.ps.println("scoreBoard"+","+username+","+"requested");
        System.out.println("LOADING Scoreboard");
        boolean answerFlag = false;
        while(!answerFlag){
            System.out.println("LOADING Scoreboard");
            if(client.OK == 1){
                answerFlag=true;
            }
            if(client.OK == 0){
                answerFlag=true;
                client.OK = 2;
            }
        }
        if(client.OK == 1){
            client.OK = 2;
            String rows = client.message;
            System.out.println(rows);

            String row[] = rows.split(",");
            int n = Integer.parseInt(rows.split(",")[2]);
//            r = new String[n+1][4];
            int j = 3;

//            n = n * 4;
//            for (int i=0; i < n; i+=3){
//                r[i]
//                sRows.add(new String[]{row[i], row[i+1], row[i+2]});
//            }
//            for(int i = 3; i < n+3; i++){
//                System.out.println(row[i]);
//            }
        }
            answerFlag=false;
            w.initModality(Modality.APPLICATION_MODAL);
            w.setResizable(false);
            //                w.initStyle(StageStyle.UNDECORATED);
//            Scene s = new Scene(new ScoreBoard());
//            w.setScene(s);
            w.showAndWait();
    }
}

