package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GanttChart  {
    private BorderPane view=new BorderPane();
    private SplitPane viewGantt;
    private VBox taskview = new VBox();
    private TimelineView timelineView;
    private GridPane root=new GridPane();;  
    private VBox leftControl  = new VBox(new Label("Left Control"));
    //private VBox centerControl  = new VBox(new Label("Center Control"));
    private GanttTableView ganttTableView= new GanttTableView();
    ;



    public GanttChart(){
        //VBox sp1 = new VBox();
        //sp1=new VBox(new Button("Button One"));
        root.addRow(0, new TimelineWithGraphicView().init(13, true));  
        //view.setTop();
        //view.setCenter(centerControl);
        //view = new BorderPane(new TimelineView().init(13, true));
        viewGantt = new SplitPane(ganttTableView, root);
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
