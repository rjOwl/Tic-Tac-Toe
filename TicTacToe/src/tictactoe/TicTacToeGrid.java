package tictactoe;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Mohamed Ali
 * @Editor by Mustafa
 */
public class TicTacToeGrid{
    private boolean playable = true;
    private boolean turnX = true;
    private AnchorPane root = new AnchorPane();
    private Tile[][] board = new Tile[3][3];
    private List<Combo> combos = new ArrayList<>();
    boolean firstround = true;
    boolean returntox = false;

    public AnchorPane createContent() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 100);
                tile.setTranslateY(i * 100);
                board[j][i] = tile;
                root.getChildren().add(tile);
            }
        }

        for (int y = 0; y < 3; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
        }

        // vertical
        for (int x = 0; x < 3; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
        }
        // diagonals
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));
        return root;
    }

    private void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                break;
            }
        }
    }

    private class Combo {
        private Tile[] tiles;
        public Combo(Tile... tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty())
                return false;

            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }

        public void AI(){
            System.out.println("hello combo");
            if(tiles[0].getValue().equals(tiles[1].getValue())){
                tiles[2].drawO();
                returntox = true;
                System.out.println("hello combo 1 ");
            }

            else if (tiles[0].getValue().equals(tiles[2].getValue())){
                tiles[1].drawO();
                returntox = true;
                System.out.println("hello combo 3 ");
            }
            else if (tiles[1].getValue().equals(tiles[2].getValue())){
                tiles[0].drawO();
                returntox = true;
                System.out.println("hello combo 4");
            }
        }
    }

    private class Tile extends StackPane {
        private Text text = new Text();
        //Combo complay = new Combo();
        public Tile() {
            Rectangle border = new Rectangle(100, 100);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            getChildren().addAll(border,text);
            text.setFont(Font.font(72));
            setOnMouseClicked(event -> {
                if (!playable)
                    return;
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX)
                        return;
                    drawX();
                    turnX = !turnX;
                    checkState();
                    AITurn();
                }
            });
        }

        public void AITurn(){
           if (!playable)
                    return;

           if (firstround){
                if(board[0][0].getValue().isEmpty()){
                    board[0][0].drawO();
                    firstround = false;
                }
                else{
                    board[1][1].drawO();
                    firstround = false;
                }
            }
            else {
                System.out.println("hello");
                for (Combo combo : combos){

                    combo.AI();
                    if(returntox){
                        turnX = !turnX;
                        checkState();
                        returntox = false;
                        break;
                    }
                }
            }
            turnX = !turnX;
            checkState();
        }
        public String getValue() {
            return text.getText();
        }
        private void drawX() {
            text.setText("X");
        }
        public void drawO() {
            text.setText("O");
        }
    }
}
