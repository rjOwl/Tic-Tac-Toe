package tictactoe;

import java.util.Arrays;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import client.ClientThread;
import javafx.concurrent.Task;

public class ScoreBoard extends TableView {
        private final ClientThread client = ClientThread.getInstance();
        private Task copyWorker;
//    protected final TableColumn tableColumn;
//    protected final TableColumn tableColumn0;
//    protected final TableColumn tableColumn1;
//    protected final TableColumn tableColumn2;

    public ScoreBoard(String board) {
//        tableColumn = new TableColumn();
//        tableColumn0 = new TableColumn();
//        tableColumn1 = new TableColumn();
//        tableColumn2 = new TableColumn();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(300.0);
        setPrefWidth(400.0);

//        tableColumn.setPrefWidth(75.0);
//        tableColumn.setText("Player");
//
//        tableColumn0.setPrefWidth(75.0);
//        tableColumn0.setText("Win");
//
//        tableColumn1.setPrefWidth(75.0);
//        tableColumn1.setText("Lose");
//
//        tableColumn2.setPrefWidth(75.0);
//        tableColumn2.setText("Draw");
//
//        tableColumn.setCellValueFactory(c -> new SimpleStringProperty(new String("123")));
//        tableColumn0.setCellValueFactory(c -> new SimpleStringProperty(new String("456")));
//        tableColumn1.setCellValueFactory(c -> new SimpleStringProperty(new String("123")));
//        tableColumn2.setCellValueFactory(c -> new SimpleStringProperty(new String("456")));
//
//        getColumns().add(tableColumn);
//        getColumns().add(tableColumn0);
//        getColumns().add(tableColumn1);
//        getColumns().add(tableColumn2);
//        getItems().addAll(new SimpleStringProperty(new String("")));

        String[][] staffArray = client.getRecords();

        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(staffArray));
        data.remove(0);//remove titles from data

        for (int i = 0; i < staffArray[0].length; i++) {
            TableColumn tc = new TableColumn(staffArray[0][i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(90);
            getColumns().add(tc);
        }
        this.setItems(data);
    }

    public Task createWorker(String board) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                while(board.isEmpty()){
//                    Thread.sleep(2000);
//                    updateMessage("2000 milliseconds");
//                    updateProgress(i + 1, 10);
                }
                return true;
            }
        };
    }
}






