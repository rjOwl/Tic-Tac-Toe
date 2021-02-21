package tictactoe;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class roomDialogue extends AnchorPane {

    protected final TextField textField;
    protected final Button button;
    protected final Button button0;

    public roomDialogue() {

        textField = new TextField();
        button = new Button();
        button0 = new Button();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(112.0);
        setPrefWidth(217.0);

        textField.setLayoutX(26.0);
        textField.setLayoutY(31.0);
        textField.setPrefHeight(25.0);
        textField.setPrefWidth(166.0);
        textField.setPromptText("room id");

        button.setLayoutX(26.0);
        button.setLayoutY(65.0);
        button.setMnemonicParsing(false);
        button.setText("Creat room");

        button0.setLayoutX(123.0);
        button0.setLayoutY(65.0);
        button0.setMnemonicParsing(false);
        button0.setText("Join room");

        getChildren().add(textField);
        getChildren().add(button);
        getChildren().add(button0);

    }
}
