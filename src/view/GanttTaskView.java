package view;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
import model.GanttTask;

public class GanttTaskView extends GanttTableView<GanttTask> {

    private String priority = "Task Priority";
    private String isCritical = "IsCritical";

    public GanttTaskView() {
        super();
        this.name = "Task Name";
        this.start = "Task Start";
        this.end = "Task End";
        this.duration = "Task Duration";
        this.state = "Task State";
        this.info = "Task Infos";
    }

    @Override
    public void addSpecificColumns() {
        // Creating columns
        TableColumn<GanttTask, Integer> priorityCol = new TableColumn<GanttTask, Integer>(priority);
        priorityCol.setCellValueFactory(new PropertyValueFactory<GanttTask, Integer>("priority"));
        priorityCol.setCellFactory(column -> new TableCell<GanttTask, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                Rectangle rect = new Rectangle();
                rect.setX(20); // setting the X coordinate of upper left //corner of rectangle
                rect.setY(20); // setting the Y coordinate of upper left //corner of rectangle
                rect.setWidth(20); // setting the width of rectangle
                rect.setHeight(20);
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    switch (item) {
                        case 3:
                            rect.setFill(low.getColor());
                            legendBox.getChildren().add(rect);

                            break;
                        case 2:
                            rect.setFill(medium.getColor());
                            legendBox.getChildren().add(rect);
                            break;
                        case 1:
                            rect.setFill(high.getColor());
                            legendBox.getChildren().add(rect);
                            break;
                    }
                    setGraphic(rect);

                }
            }
        });

        TableColumn<GanttTask, Boolean> isCriticalCol = new TableColumn<GanttTask, Boolean>(isCritical);
        isCriticalCol.setCellValueFactory(new PropertyValueFactory<GanttTask, Boolean>("isCritical"));
        isCriticalCol.setCellValueFactory(cellData -> cellData.getValue().isCriticalProperty());
        isCriticalCol.setCellFactory(CheckBoxTableCell.forTableColumn(isCriticalCol));
        isCriticalCol.setCellFactory(column -> new CheckBoxTableCell());

        this.getColumns().addAll(priorityCol, isCriticalCol);
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getIsCritical() {
        return isCritical;
    }

    public void setIsCritical(String isCritical) {
        this.isCritical = isCritical;
    }

}
