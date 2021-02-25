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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScreen1 extends AnchorPane {
    protected final AnchorPane anchorPane;
    protected final Label label;
    protected final Label label0;
    protected final TextField textField;
    protected final TextField textField0;
    protected final Button button;
    protected final Button button0;
    protected final ImageView imageView;

    public LoginScreen1(Stage currentWindow) {

        anchorPane = new AnchorPane();
        label = new Label();
        label0 = new Label();
        textField = new TextField();
        textField0 = new TextField();
        button = new Button();
        button0 = new Button();
        imageView = new ImageView();

        setPrefHeight(400.0);
        setPrefWidth(640.0);

        VBox.setVgrow(anchorPane, javafx.scene.layout.Priority.ALWAYS);
        anchorPane.setMaxHeight(-1.0);
        anchorPane.setMaxWidth(-1.0);
        anchorPane.setPrefHeight(400.0);
        anchorPane.setPrefWidth(498.0);

        label.setLayoutX(59.0);
        label.setLayoutY(141.0);
        label.setPrefHeight(18.0);
        label.setPrefWidth(61.0);
        label.setText("Username");

        label0.setLayoutX(60.0);
        label0.setLayoutY(173.0);
        label0.setText("Password");

        textField.setLayoutX(137.0);
        textField.setLayoutY(137.0);

        textField0.setLayoutX(137.0);
        textField0.setLayoutY(169.0);

        button.setLayoutX(137.0);
        button.setLayoutY(211.0);
        button.setMnemonicParsing(false);
        button.setStyle("-fx-background-radius: 1em;");
        button.setText("Login");
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(textField.getText().equals("1") &&
                        textField0.getText().equals("2")){
//                Stage w = new Stage();
//                Scene s = new Scene(new MainWindow());
//                w.setScene(s);
                currentWindow.setScene(new Scene(new MainWindow(currentWindow)));
//                w.show();
                }
            }
        });

        button0.setLayoutX(212.0);
        button0.setLayoutY(211.0);
        button0.setMnemonicParsing(false);
        button0.setStyle("-fx-background-radius: 1em;");
        button0.setText("Sign up");
        button0.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.out.println(textField.getText());
                System.out.println(textField0.getText());
            }
        });
        imageView.setFitHeight(400.0);
        imageView.setFitWidth(321.0);
        imageView.setLayoutX(305.0);
        imageView.setLayoutY(40.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("img/logo.png").toExternalForm()));

        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(label0);
        anchorPane.getChildren().add(textField);
        anchorPane.getChildren().add(textField0);
        anchorPane.getChildren().add(button);
        anchorPane.getChildren().add(button0);
        anchorPane.getChildren().add(imageView);
        getChildren().add(anchorPane);
    }
}
