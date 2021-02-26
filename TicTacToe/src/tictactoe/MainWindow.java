package tictactoe;

import client.ClientThread;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindow extends AnchorPane {
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
    protected final TicGrid grid = new TicGrid();
    protected ComboBox comboBox = new ComboBox();
    int level;
    protected Stage mainWindow;
    boolean AIEnabled = false, optionBtnClicked=false;
    private ClientThread client = ClientThread.getInstance();

    public MainWindow(Stage mainWindowScene) {
        this.mainWindow = mainWindowScene;
        buttonBar = new ButtonBar();
        button = new Button();
        button0 = new Button();
        button1 = new Button();
        radioButton = new RadioButton();
        gridPane = grid.createContent(false, false, false, -1);
        imageView = new ImageView();
        button2 = new Button();
        button3 = new Button();
        button4 = new Button();
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
                handleOptions(true, false, -1);
                grid.createContent(AIEnabled, optionBtnClicked, false, level);
                System.out.println(AIEnabled +"\n"+optionBtnClicked+"\n"+level);
            }
        });

        button0.setMnemonicParsing(false);
        button0.setText("Play Local");
        button0.setOnAction(new EventHandler < ActionEvent > (){
            @Override
            public void handle(ActionEvent event) {
                handleOptions(false, true, -1);
                levelLabel.setText("");
                gridPane = grid.createContent(AIEnabled, optionBtnClicked, false, level);
            }
        });

        button1.setMnemonicParsing(false);
        button1.setText("Room");
        button1.setOnAction(new EventHandler < ActionEvent > () {
            @Override
            public void handle(ActionEvent event) {
                levelLabel.setText("");
                handleOptions(false, true, -1);
                popUpDialog();
            }
        });

        levelLabel.setLayoutX(492.0);
        levelLabel.setLayoutY(27.0);
        //        radioButton.setMnemonicParsing(false);
        //        radioButton.setPrefHeight(17.0);
        //        radioButton.setPrefWidth(88.0);
        levelLabel.setText("Level"+level);
        levelLabel.setLayoutX(492.0);
        levelLabel.setLayoutY(40.0);
        comboBox.setLayoutX(492.0);
        comboBox.setLayoutY(27.0);
        comboBox.setValue("Levels");
        comboBox.getItems().add("Level 1");
        comboBox.getItems().add("Level 2");
        comboBox.getItems().add("Level 3");
        comboBox.setOnAction((e) -> {
            level = comboBox.getSelectionModel().getSelectedIndex();
            level += 1;
            //            FIX THIS LOGIC 
            levelLabel.setText("Level"+level);
            handleOptions(false, true, level);
            gridPane = grid.createContent(AIEnabled, optionBtnClicked, false, level);
            System.out.println(AIEnabled +"\n"+optionBtnClicked+"\n"+level);
//            System.out.println("   ComboBox.getValue(): " + level);
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
        button2.setPrefWidth(88.0);
        button2.setText("Replay game");
        button2.setOnAction(new EventHandler < ActionEvent > () {
            @Override
            public void handle(ActionEvent event) {
                grid.replayGame();
            }
        });

        button3.setLayoutX(25.0);
        button3.setLayoutY(100.0);
        button3.setMnemonicParsing(false);
        button3.setPrefHeight(40.0);
        button3.setPrefWidth(88.0);
        button3.setText("Score board");
        button3.setOnAction(new EventHandler < ActionEvent > () {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showScoreBoard("hossam");
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        button4.setLayoutX(25.0);
        button4.setLayoutY(170.0);
        button4.setMnemonicParsing(false);
        button4.setPrefHeight(40.0);
        button4.setPrefWidth(88.0);
        button4.setText("Logout");
        button4.setOnAction(new EventHandler < ActionEvent > () {
            @Override
            public void handle(ActionEvent event) {
                mainWindow.setScene(new Scene(new LoginScreen(mainWindow)));
            }
        });

        buttonBar.getButtons().add(button);
        buttonBar.getButtons().add(button0);
        buttonBar.getButtons().add(button1);
        getChildren().add(buttonBar);
        getChildren().add(comboBox);
//        getChildren().add(levelLabel);
        //        gridPane.getChildren().add(imageView);
        getChildren().add(gridPane);
        getChildren().add(button2);
        getChildren().add(button3);
        getChildren().add(button4);
    }
    private void handleOptions(boolean AIEnabled, boolean optionBtnClicked, int level){
        this.AIEnabled = AIEnabled;
        this.optionBtnClicked = optionBtnClicked;
        this.level = level;
        this.comboBox.setVisible(AIEnabled);
        this.gridPane.getChildren().clear();
    }
    private void popUpDialog(){
        gridPane = grid.createContent(false, true, true, -1);

        Stage w = new Stage();
        w.initModality(Modality.APPLICATION_MODAL);
        w.setResizable(false);
        //                w.initStyle(StageStyle.UNDECORATED);
        Scene s = new Scene(new roomDialogue(w));
        w.setScene(s);
        w.showAndWait();
    }

    private void showScoreBoard(String username) throws IOException{
        Stage w = new Stage();
        client.ps.println("scoreBoard"+","+username+","+"requested");

        boolean answerFlag = false;
        while(!answerFlag){
            if(client.OK == 1){
                answerFlag=true;
            }
            if(client.OK == 0){
                answerFlag=true;
                client.OK = 2;
            }
        }
        if(1 == 1 && client.OK == 1){
            client.OK = 2;
            String rows = client.getRows();
            String row[] = rows.split(",");
            int n = Integer.parseInt(rows.split(",")[2]);
            n = n * 4;
            for(int i = 3; i < n+3; i++){
                System.out.println(row[i]);
            }
            answerFlag=false;
        }
    }
}

