package tictactoe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindow extends AnchorPane {

    protected final ButtonBar buttonBar;
    protected final Button button;
    protected final Button button0;
    protected final Button button1;
    protected final Label userName;
    protected final RadioButton radioButton;
    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    protected final Button button2;
    protected final Button button3;
    protected final Button button4;
    protected final TicTacToeGrid grid = new TicTacToeGrid();

    public MainWindow(Stage mainWindowScene) {

        buttonBar = new ButtonBar();
        button = new Button();
        button0 = new Button();
        button1 = new Button();
        radioButton = new RadioButton();
        anchorPane = grid.createContent();
//        anchorPane = new AnchorPane();
        imageView = new ImageView();
        button2 = new Button();
        button3 = new Button();
        button4 = new Button();
        userName = new Label();

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

        button0.setMnemonicParsing(false);
        button0.setText("Play Local");

        button1.setMnemonicParsing(false);
        button1.setText("Room");
        button1.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Stage w = new Stage();
                w.initModality(Modality.APPLICATION_MODAL);
                w.setResizable(false);
//                w.initStyle(StageStyle.UNDECORATED);
                Scene s = new Scene(new roomDialogue());
                w.setScene(s);
                w.showAndWait();
            }
        });

        userName.setLayoutX(492.0);
        userName.setLayoutY(27.0);
//        radioButton.setMnemonicParsing(false);
//        radioButton.setPrefHeight(17.0);
//        radioButton.setPrefWidth(88.0);
        userName.setText("Mostafa");

        anchorPane.setLayoutX(168.0);
        anchorPane.setLayoutY(75.0);
        anchorPane.setPrefHeight(300.0);
        anchorPane.setPrefWidth(300.0);
        anchorPane.setStyle("-fx-background-color: #FFFFFF;");

        button2.setLayoutX(25.0);
        button2.setLayoutY(32.0);
        button2.setMnemonicParsing(false);
        button2.setPrefHeight(40.0);
        button2.setPrefWidth(88.0);
        button2.setText("Replay game");

        button3.setLayoutX(25.0);
        button3.setLayoutY(100.0);
        button3.setMnemonicParsing(false);
        button3.setPrefHeight(40.0);
        button3.setPrefWidth(88.0);
        button3.setText("Score board");
        button3.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Stage w = new Stage();
                w.initModality(Modality.APPLICATION_MODAL);
                w.setResizable(false);
//                w.initStyle(StageStyle.UNDECORATED);
                Scene s = new Scene(new ScoreBoard());
                w.setScene(s);
                w.showAndWait();
            }
        });

        button4.setLayoutX(25.0);
        button4.setLayoutY(170.0);
        button4.setMnemonicParsing(false);
        button4.setPrefHeight(40.0);
        button4.setPrefWidth(88.0);
        button4.setText("Logout");
        button4.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                 mainWindowScene.setScene(new Scene(new LoginScreen(mainWindowScene)));
            }
        });

        buttonBar.getButtons().add(button);
        buttonBar.getButtons().add(button0);
        buttonBar.getButtons().add(button1);
        getChildren().add(buttonBar);
        getChildren().add(userName);
//        anchorPane.getChildren().add(imageView);
        getChildren().add(anchorPane);
        getChildren().add(button2);
        getChildren().add(button3);
        getChildren().add(button4);
    }
}
