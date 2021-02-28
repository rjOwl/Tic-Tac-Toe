package tictactoe;

import client.ClientThread;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Popup {

    static public final ClientThread client = ClientThread.getInstance();

    public static void display() {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);

        popupwindow.setTitle("Popup window");

        Label label1 = new Label();

        label1.setLayoutX(40.0);
        label1.setLayoutY(50.0);

        label1.setStyle("-fx-font-weight: bold;-fx-font-size: 10pt;");

        Button button1 = new Button("Close");

        String winner = client.iWon;
        if (winner.equals("x")) {
            label1.setText(" The Winner is X ");
        } else if (winner.equals("o")) {
            label1.setText(" The Winner is O");
        } else if(winner.equals("draw")){
            label1.setText("NOBODY WINS");
        }

        button1.setStyle("-fx-background-color:linear-gradient(to right, #EB3349 0%, #F45C43  51%, #EB3349  100%); \n"
                + "    -fx-background-radius: 30;\n"
                + "    -fx-background-insets: 0,1,2,3,0;\n"
                + "    -fx-text-fill: #ffffff;\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-padding: 5 10 5 10;");

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupwindow.close();
            }
        }
        );

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label1, button1);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 300, 250);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }

}
