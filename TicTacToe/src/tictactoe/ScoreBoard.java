package tictactoe;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ScoreBoard extends TableView {
    protected final TableColumn tableColumn;
    protected final TableColumn tableColumn0;
    protected final TableColumn tableColumn1;

    public ScoreBoard() {
        tableColumn = new TableColumn();
        tableColumn0 = new TableColumn();
        tableColumn1 = new TableColumn();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        tableColumn.setPrefWidth(75.0);
        tableColumn.setText("C1");

        tableColumn0.setPrefWidth(75.0);
        tableColumn0.setText("C2");

        tableColumn1.setPrefWidth(75.0);
        tableColumn1.setText("Column X");

        getColumns().add(tableColumn);
        getColumns().add(tableColumn0);
        getColumns().add(tableColumn1);

    }
}
