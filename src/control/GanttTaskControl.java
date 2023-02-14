
package control;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.GanttTask;
import model.TaskPriority;

public class GanttTaskControl extends GanttTableControl<GanttTask> {
    private GridPane legendBox = new GridPane();

    // Task priority
    private TaskPriority high = new TaskPriority(new Label("Hight Priority"), Color.PALEVIOLETRED);
    private TaskPriority medium = new TaskPriority(new Label("Medium Priority"), Color.SKYBLUE);
    private TaskPriority low = new TaskPriority(new Label("Low Priority"), Color.PALEGREEN);

    // Name of news columns
    private String priority = "Task Priority";
    private String isCritical = "IsCritical";

    public GanttTaskControl() {
        super();
        // Name of columns
        this.name = "Task Name";
        this.start = "Task Start";
        this.end = "Task End";
        this.duration = "Task Duration";
        this.state = "Task State";
        this.description = "Task Description";
    }

    // add a legend
    @Override
    public void init() {
        tableWithPriorityLegendControl.setBottom(legendBox);
        addPriorityLegend();
    }

    // add news columns
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

    /**
     * add legend in HBox
     */
    private void addPriorityLegend() {
        // legende
        HBox lpHBox = createLegendElement(low);
        HBox mpHBox = createLegendElement(medium);
        HBox hpHBox = createLegendElement(high);

        legendBox.add(lpHBox, 0, 0);
        legendBox.add(mpHBox, 1, 0);
        legendBox.add(hpHBox, 2, 0);
    }

    /**
     * set legend
     * 
     * @param priority
     * @return
     */
    private HBox createLegendElement(TaskPriority priority) {
        Rectangle rect = new Rectangle();
        rect.setX(20); // setting the X coordinate of upper left //corner of rectangle
        rect.setY(20); // setting the Y coordinate of upper left //corner of rectangle
        rect.setWidth(20); // setting the width of rectangle
        rect.setHeight(20);
        rect.setFill(priority.getColor());
        HBox hBox = new HBox(rect, priority.getLabel());
        hBox.setSpacing(10);
        GridPane.setHgrow(hBox, Priority.ALWAYS);
        HBox.setMargin(rect, new Insets(5, 5, 5, 5));
        HBox.setMargin(priority.getLabel(), new Insets(5, 5, 5, 5));

        return hBox;
    }

    public TaskPriority getHigh() {
        return high;
    }

    public void setHigh(TaskPriority high) {
        this.high = high;
    }

    public TaskPriority getMedium() {
        return medium;
    }

    public void setMedium(TaskPriority medium) {
        this.medium = medium;
    }

    public TaskPriority getLow() {
        return low;
    }

    public void setLow(TaskPriority low) {
        this.low = low;
    }

    public GridPane getLegendBox() {
        return legendBox;
    }

    public void setLegendBox(GridPane legendBox) {
        this.legendBox = legendBox;
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
