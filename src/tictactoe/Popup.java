package tictactoe;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Popup {
   
    
public static void display()
{
Stage popupwindow = new Stage();
      
popupwindow.initModality(Modality.APPLICATION_MODAL);

popupwindow.setTitle("Popup window");      
      
Label label1= new Label("Game Result Here");

label1.setStyle("-fx-font-weight: bold;-fx-font-size: 10pt;");

Button button1 = new Button("Close");

Button button2 = new Button("Play Again");

button1.setStyle("-fx-background-color:linear-gradient(to right, #EB3349 0%, #F45C43  51%, #EB3349  100%); \n" +
"    -fx-background-radius: 30;\n" +
"    -fx-background-insets: 0,1,2,3,0;\n" +
"    -fx-text-fill: #ffffff;\n" +
"    -fx-font-weight: bold;\n" +
"    -fx-padding: 5 10 5 10;");

button2.setStyle("-fx-background-color: linear-gradient(to right, #348F50 0%, #56B4D3  51%, #348F50  100%); \n" +
"    -fx-background-radius: 30;\n" +
"    -fx-background-insets: 0,1,2,3,0;\n" +
"    -fx-text-fill: #ffffff;\n" +
"    -fx-font-weight: bold;\n" +
"    -fx-font-size: 14px;");
     
     
button1.setOnAction(e -> popupwindow.close());
     
     

VBox layout= new VBox(10);
     
      
layout.getChildren().addAll(label1, button1,button2);
      
layout.setAlignment(Pos.CENTER);
      
Scene scene1= new Scene(layout, 300, 250);
      
popupwindow.setScene(scene1);
      
popupwindow.showAndWait();

       
}

}

