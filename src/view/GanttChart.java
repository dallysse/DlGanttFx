package view;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import model.GanttTask;

public class GanttChart  {
    private BorderPane graphicView;
    private SplitPane viewGantt;
    private TimelineView timelineView;
    private GanttTableView ganttTableView;
    private TimelineWithGraphicView timelineWithGraphicView; 
    private GanttMenu menu;
 
    public GanttChart(){
 
        ObservableList<GanttTask> ganttTasks = addGanttTasks();
        ganttTableView = new GanttTaskView().generate();
        ganttTableView.setItems(ganttTasks);

        timelineWithGraphicView = new TimelineWithGraphicView().init(13, true);
        timelineWithGraphicView.setGanttPiece(ganttTasks);

        graphicView=new BorderPane();
        menu = new GanttMenu().init(timelineWithGraphicView.getStartDay(), timelineWithGraphicView.getEndDay(), timelineWithGraphicView);
        graphicView.setTop(menu);  
        graphicView.setCenter(timelineWithGraphicView);     

        viewGantt = new SplitPane(ganttTableView.getCompleteView(), graphicView);
    }

    private ObservableList<GanttTask> addGanttTasks() {
         ObservableList<GanttTask> ganttTasks = FXCollections
        .<GanttTask> observableArrayList();
            GanttTask ganttTask1 = new GanttTask("Erstellung des Mockups", LocalDate.of(2023, 02, 17), LocalDate.of(2023, 02, 26), 1, true,  " Mockup erstellen");
            GanttTask ganttTask2 =new GanttTask("Aufbau der Architektur", LocalDate.of(2023, 01, 01), LocalDate.of(2023, 01, 01), 2, false, "Arbeit struktirieren");
            GanttTask ganttTask3 =new GanttTask("Erstellung der Datenmodel", LocalDate.of(2023, 04, 26), LocalDate.of(2023, 05, 30), 3, true, "Daten Bank erstellen");
            GanttTask ganttTask4 =new GanttTask("Implementierung eines Prototyps", LocalDate.of(2023, 12, 27), LocalDate.of(2024, 02, 7), 3, false, "Prototyp implementieren");    
        ganttTasks.addAll(ganttTask1, ganttTask2, ganttTask3, ganttTask4);
        return ganttTasks;
    }

    public BorderPane getGraphicView() {
        return graphicView;
    }


    public void setGraphicView(BorderPane view) {
        this.graphicView = view;
    }

    public TimelineView getTimelineView() {
        return timelineView;
    }


    public void setTimelineView(TimelineView timelineView) {
        this.timelineView = timelineView;
    }


    public SplitPane getViewGantt() {
        return viewGantt;
    }


    public void setViewGantt(SplitPane viewG) {
        this.viewGantt = viewGantt;
    }

 
}
