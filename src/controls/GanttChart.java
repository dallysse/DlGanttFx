package controls;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import model.GanttTask;

public class GanttChart {
    private BorderPane ganttChartWithMenu;
    private SplitPane ganttChart;
    private GanttTableView ganttTaskView;
    private TimelineWithGraphicView timelineWithGraphicView;
    private GanttChartHbox menu;

    public GanttChart() {

        ObservableList<GanttTask> ganttTasks = addGanttTasks();
        ganttTaskView = new GanttTaskView().generate();
        ganttTaskView.setItems(ganttTasks);

        timelineWithGraphicView = new TimelineTaskWithGraphicView().init(13, true);
        timelineWithGraphicView.setGanttPiece(ganttTasks);

        menu = new GanttChartHbox().init(timelineWithGraphicView.getStartDay(), timelineWithGraphicView.getEndDay(),
                timelineWithGraphicView);

        ganttChart = new SplitPane(ganttTaskView.getTableWithPriorityLegendView(), timelineWithGraphicView);

        ganttChartWithMenu = new BorderPane();
        ganttChartWithMenu.setTop(menu);
        ganttChartWithMenu.setCenter(ganttChart);
    }

    private ObservableList<GanttTask> addGanttTasks() {
        ObservableList<GanttTask> ganttTasks = FXCollections
                .<GanttTask>observableArrayList();
        GanttTask ganttTask1 = new GanttTask("Creation of the mock-up", LocalDate.of(2023, 01, 01),
                LocalDate.of(2023, 01, 05), 1, true, "design of the Interface");
        GanttTask ganttTask2 = new GanttTask("Structure of the architecture", LocalDate.of(2023, 01, 06),
                LocalDate.of(2023, 01, 13), 2, false, "Work structuring");
        GanttTask ganttTask3 = new GanttTask("Creation of the data model", LocalDate.of(2023, 01, 16),
                LocalDate.of(2023, 01, 30), 3, true, "UML-Diagram");
        GanttTask ganttTask4 = new GanttTask("Prototype implementation", LocalDate.of(2023, 02, 01),
                LocalDate.of(2023, 02, 07), 3, false, "");
        GanttTask ganttTask5 = new GanttTask("Overlay-Test", LocalDate.of(2023, 02, 10),
                        LocalDate.of(2023, 02, 25), 2, false, "test the prototyping");
        ganttTasks.addAll(ganttTask1, ganttTask2, ganttTask3, ganttTask4, ganttTask5);
        return ganttTasks;
    }

    public BorderPane getGanttChartWithMenu() {
        return ganttChartWithMenu;
    }

    public void setGanttChartWithMenu(BorderPane ganttChartWithMenu) {
        this.ganttChartWithMenu = ganttChartWithMenu;
    }

    public SplitPane getGanttChart() {
        return ganttChart;
    }

    public void setGanttChart(SplitPane ganttChart) {
        this.ganttChart = ganttChart;
    }

}
