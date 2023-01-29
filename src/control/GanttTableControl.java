package control;

import java.time.LocalDate;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import model.Activity;
import model.ActivityState;

public abstract class GanttTableControl<T extends Activity> extends TableView<T> {

    protected BorderPane tableWithPriorityLegendControl;

    protected String name = "Name";
    protected String start = "Start";
    protected String end = "End";
    protected String duration = "Duration";
    protected String state = "State";
    protected String progress = "Progress";
    protected String description = "description";

    public GanttTableControl() {

    }

    public GanttTableControl<T> generate() {
        tableWithPriorityLegendControl = new BorderPane();
        tableWithPriorityLegendControl.setCenter(this);
        createGanttTableView();
        init();
        return this;
    }

    public abstract void init();

    private void createGanttTableView() {
        // Creating columns
        TableColumn<T, T> numberCol = new TableColumn();
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<T, T>, ObservableValue<T>>() {

            @Override
            public ObservableValue<T> call(CellDataFeatures<T, T> p) {
                return new ReadOnlyObjectWrapper(p.getValue());
            }
        });

        numberCol.setCellFactory(new Callback<TableColumn<T, T>, TableCell<T, T>>() {
            @Override
            public TableCell<T, T> call(TableColumn<T, T> param) {
                return new TableCell<T, T>() {
                    @Override
                    protected void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);

                        if (this.getTableRow() != null && item != null) {
                            setText(this.getTableRow().getIndex() + 1 + "");
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        numberCol.setSortable(false);

        // Creating columns
        TableColumn<T, String> nameCol = new TableColumn<T, String>(name);
        nameCol.setCellValueFactory(new PropertyValueFactory<T, String>("name"));

        TableColumn<T, LocalDate> startCol = new TableColumn<T, LocalDate>(start);
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<T, LocalDate> endCol = new TableColumn<T, LocalDate>(end);
        endCol.setCellValueFactory(new PropertyValueFactory<T, LocalDate>("endDate"));

        TableColumn<T, Integer> durationCol = new TableColumn<T, Integer>(duration);
        durationCol.setCellValueFactory(new PropertyValueFactory<T, Integer>("duration"));

        TableColumn<T, model.ActivityState> stateCol = new TableColumn<T, ActivityState>(state);
        stateCol.setCellValueFactory(new PropertyValueFactory<T, ActivityState>("state"));

        TableColumn<T, Double> progressCol = new TableColumn<T, Double>(progress);
        progressCol.setCellValueFactory(new PropertyValueFactory<T, Double>(
                "progress"));
        progressCol
                .setCellFactory(ProgressBarTableCell.<T>forTableColumn());
        Callback<TableColumn<T, Double>, TableCell<T, Double>> cellFactory = new Callback<TableColumn<T, Double>, TableCell<T, Double>>() {
            public TableCell<T, Double> call(TableColumn<T, Double> p) {
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
                                pb.setStyle("-fx-accent: green");
                            }
                        }
                    }
                };
            }
        };
        progressCol.setCellFactory(cellFactory);

        TableColumn<T, String> descriptionCol = new TableColumn<T, String>(description);
        descriptionCol.setCellValueFactory(new PropertyValueFactory<T, String>("description"));

        this.getColumns().addAll(numberCol, nameCol, startCol, endCol, durationCol, stateCol, progressCol,
                descriptionCol);

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

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public BorderPane getTableWithPriorityLegendControl() {
        return tableWithPriorityLegendControl;
    }

    public void setTableWithPriorityLegendControl(BorderPane tableWithPriorityLegendView) {
        this.tableWithPriorityLegendControl = tableWithPriorityLegendView;
    }

    public T getSelectItem() {
        T p = getSelectionModel().getSelectedItem();

        return p;
    }
}