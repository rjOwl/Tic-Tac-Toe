package tictactoe;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ScoreBoard extends TableView {
    protected final TableColumn tableColumn;
    protected final TableColumn tableColumn0;
    protected final TableColumn tableColumn1;
    protected final TableColumn tableColumn2;

    public ScoreBoard() {
        tableColumn = new TableColumn();
        tableColumn0 = new TableColumn();
        tableColumn1 = new TableColumn();
        tableColumn2 = new TableColumn();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(300.0);
        setPrefWidth(400.0);

        tableColumn.setPrefWidth(75.0);
        tableColumn.setText("Player");

        tableColumn0.setPrefWidth(75.0);
        tableColumn0.setText("Win");

        tableColumn1.setPrefWidth(75.0);
        tableColumn1.setText("Lose");

        tableColumn2.setPrefWidth(75.0);
        tableColumn2.setText("Draw");

        getColumns().add(tableColumn);
        getColumns().add(tableColumn0);
        getColumns().add(tableColumn1);
        getColumns().add(tableColumn2);
    }
}
