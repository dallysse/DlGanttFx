package view;

import java.time.LocalDate;

import javax.swing.text.TabableView;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.GanttTask;
import model.GanttTask.TaskState;

public class GanttChart  {
    private BorderPane view=new BorderPane();
    private SplitPane viewGantt;
    private VBox taskview = new VBox();
    private TimelineView timelineView;
    private GridPane root=new GridPane();;  
    private GanttTableView ganttTableView;
    private TimelineWithGraphicView timelineWithGraphicView; 
 



    public GanttChart(){
        //VBox sp1 = new VBox();
        //sp1=new VBox(new Button("Button One"));
        ObservableList<GanttTask> ganttTasks = addGanttTasks();
        ganttTableView = new GanttTableView().generate();
        ganttTableView.setItems(ganttTasks);

        timelineWithGraphicView = new TimelineWithGraphicView().init(13, true);
        timelineWithGraphicView.setGanttPiece(ganttTasks);
        

        root.addRow(1, timelineWithGraphicView);  
        root.addRow(0, new GanttMenu().init(timelineWithGraphicView.getStartDay(), timelineWithGraphicView.getEndDay(), timelineWithGraphicView));  

        //view.setTop();
        //view = new BorderPane(new TimelineView().init(13, true));
        viewGantt = new SplitPane(ganttTableView.getCompleteView(), root);
    }

    private ObservableList<GanttTask> addGanttTasks() {
         ObservableList<GanttTask> ganttTasks = FXCollections
        .<GanttTask> observableArrayList();
            GanttTask ganttTask1 = new GanttTask("Erstellung des Mockups", LocalDate.of(2023, 02, 17), LocalDate.of(2023, 02, 26), 1, true,  "aaa");
            GanttTask ganttTask2 =new GanttTask("Aufbau der Architektur", LocalDate.of(2023, 01, 01), LocalDate.of(2023, 01, 01), 2, false, "adfghaa");
            GanttTask ganttTask3 =new GanttTask("Erstellung der Datenmodel", LocalDate.of(2023, 04, 26), LocalDate.of(2023, 05, 30), 3, true, "aadsfa");
            GanttTask ganttTask4 =new GanttTask("Implementierung eines Prototyps", LocalDate.of(2023, 12, 27), LocalDate.of(2024, 02, 7), 3, false, "aadsfa");    
        ganttTasks.addAll(ganttTask1, ganttTask2, ganttTask3, ganttTask4);
        return ganttTasks;
    }

    public BorderPane getView() {
        return view;
    }


    public void setView(BorderPane view) {
        this.view = view;
    }


    public VBox getTaskview() {
        return taskview;
    }


    public void setTaskview(VBox taskview) {
        this.taskview = taskview;
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
