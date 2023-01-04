package view;

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
    private GanttMenuView menu;

    public GanttChart() {

        ObservableList<GanttTask> ganttTasks = addGanttTasks();
        ganttTaskView = new GanttTaskView().generate();
        ganttTaskView.setItems(ganttTasks);

        timelineWithGraphicView = new TimelineTaskWithGraphicView().init(13, true);
        timelineWithGraphicView.setGanttPiece(ganttTasks);

        menu = new GanttMenuView().init(timelineWithGraphicView.getStartDay(), timelineWithGraphicView.getEndDay(),
                timelineWithGraphicView);

        ganttChart = new SplitPane(ganttTaskView.getTableWithLegendView(), timelineWithGraphicView);

        ganttChartWithMenu = new BorderPane();
        ganttChartWithMenu.setTop(menu);
        ganttChartWithMenu.setCenter(ganttChart);
    }

    private ObservableList<GanttTask> addGanttTasks() {
        ObservableList<GanttTask> ganttTasks = FXCollections
                .<GanttTask>observableArrayList();
        GanttTask ganttTask1 = new GanttTask("Erstellung des Mockups", LocalDate.of(2023, 01, 17),
                LocalDate.of(2023, 01, 26), 1, true, " Mockup erstellen");
        GanttTask ganttTask2 = new GanttTask("Aufbau der Architektur", LocalDate.of(2023, 01, 01),
                LocalDate.of(2023, 01, 15), 2, false, "Arbeit struktirieren");
        GanttTask ganttTask3 = new GanttTask("Erstellung der Datenmodel", LocalDate.of(2023, 04, 26),
                LocalDate.of(2023, 05, 30), 3, true, "Daten Bank erstellen");
        GanttTask ganttTask4 = new GanttTask("Implementierung eines Prototyps", LocalDate.of(2023, 12, 27),
                LocalDate.of(2024, 02, 7), 3, false, "Prototyp implementieren");
        ganttTasks.addAll(ganttTask1, ganttTask2, ganttTask3, ganttTask4);
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
