package tictactoe;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class roomDialogue extends AnchorPane {

    protected final TextField textField;
    protected final Button button;
    protected final Label label;

    public roomDialogue() {
        textField = new TextField();
        button = new Button();
        label = new Label();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(112.0);
        setPrefWidth(217.0);

        textField.setLayoutX(82.0);
        textField.setLayoutY(31.0);
        textField.setPrefHeight(25.0);
        textField.setPrefWidth(73.0);
        textField.setPromptText("room id");

        button.setLayoutX(82.0);
        button.setLayoutY(73.0);
        button.setMnemonicParsing(false);
        button.setText("Add Room");

        label.setLayoutX(26.0);
        label.setLayoutY(35.0);
        label.setText("Room Id");

        getChildren().add(textField);
        getChildren().add(button);
        getChildren().add(label);

    }
}
