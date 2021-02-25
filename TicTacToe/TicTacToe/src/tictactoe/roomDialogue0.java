package tictactoe;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class roomDialogue0 extends AnchorPane {

    protected final TextField textField;
    protected final Button button;
    protected final Button button0;
    protected final Label label;

    public roomDialogue0() {

        textField = new TextField();
        label = new Label();
        button = new Button();
        button0 = new Button();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(112.0);
        setPrefWidth(217.0);

        label.setLayoutX(20.0);
        label.setLayoutY(31.0);
        label.setText("Room Id");
        textField.setPrefHeight(25.0);
        textField.setPrefWidth(20.0);

        textField.setLayoutX(26.0);
        textField.setLayoutY(31.0);
        textField.setPrefHeight(25.0);
        textField.setPrefWidth(166.0);
        textField.setPromptText("room id");

        button.setLayoutX(26.0);
        button.setLayoutY(65.0);
        button.setMnemonicParsing(false);
        button.setText("Add room");

//        button0.setLayoutX(123.0);
//        button0.setLayoutY(65.0);
//        button0.setMnemonicParsing(false);
//        button0.setText("Join room");

        getChildren().add(textField);
        getChildren().add(button);
        getChildren().add(label);

    }
}
