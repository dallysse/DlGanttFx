package view;

import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import model.TaskPriority;
import model.ObjectState;

public abstract class GanttTableView<T> extends TableView<T> {

    protected BorderPane tableWithPriorityLegendView;

    protected String name = "Name";
    protected String start = "Start";
    protected String end = "End";
    protected String duration = "Duration";
    protected String state = "State";
    protected String complete = "Work Complete";
    protected String info = "Infos";

    public GanttTableView() {

    }

    public GanttTableView<T> generate() {
        tableWithPriorityLegendView = new BorderPane();
        tableWithPriorityLegendView.setCenter(this);
        createGanttTableView();
        init();
        return this;
    }

    public abstract void init();

    private void createGanttTableView() {
        // Creating columns
        TableColumn<T, String> nameCol = new TableColumn<T, String>(name);
        nameCol.setCellValueFactory(new PropertyValueFactory<T, String>("name"));

        TableColumn<T, LocalDate> startCol = new TableColumn<T, LocalDate>(start);
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<T, LocalDate> endCol = new TableColumn<T, LocalDate>(end);
        endCol.setCellValueFactory(new PropertyValueFactory<T, LocalDate>("endDate"));

        TableColumn<T, Integer> durationCol = new TableColumn<T, Integer>(duration);
        durationCol.setCellValueFactory(new PropertyValueFactory<T, Integer>("duration"));

        TableColumn<T, model.ObjectState> stateCol = new TableColumn<T, ObjectState>(state);
        stateCol.setCellValueFactory(new PropertyValueFactory<T, ObjectState>("state"));

        TableColumn<T, Double> workCompleteCol = new TableColumn<T, Double>(complete);
        workCompleteCol.setCellValueFactory(new PropertyValueFactory<T, Double>(
                "workComplete"));
        workCompleteCol
                .setCellFactory(ProgressBarTableCell.<T>forTableColumn());

        Callback<TableColumn<T, Double>, TableCell<T, Double>> cellFactory = new Callback<TableColumn<T, Double>, TableCell<T, Double>>() {
            public TableCell call(TableColumn<T, Double> p) {
                return new TableCell<T, Double>() {

                    private ProgressBar pb = new ProgressBar();

                    @Override
                    public void updateItem(Double item, boolean empty) {
                        if (item != null) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                pb.setProgress(item);
                                setGraphic(pb);
                            }
                        }
                    }
                };
            }
        };
        workCompleteCol.setCellFactory(cellFactory);

        TableColumn<T, String> infoCol = new TableColumn<T, String>(info);
        infoCol.setCellValueFactory(new PropertyValueFactory<T, String>("info"));

        this.getColumns().addAll(nameCol, startCol, endCol, durationCol, stateCol, workCompleteCol, infoCol);

        // add specific columns
        addSpecificColumns();
    }

    public abstract void addSpecificColumns();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public BorderPane getTableWithPriorityLegendView() {
        return tableWithPriorityLegendView;
    }

    public void setTableWithPriorityLegendView(BorderPane tableWithPriorityLegendView) {
        this.tableWithPriorityLegendView = tableWithPriorityLegendView;
    }

}
