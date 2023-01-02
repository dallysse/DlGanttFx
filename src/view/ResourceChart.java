package view;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import model.GanttResource;

public class ResourceChart  {

    private BorderPane graphicView;
    private SplitPane viewGantt;
    private TimelineView timelineView;
    private GanttTableView ganttTableView;
    private GanttMenu menu;
 
    public ResourceChart(){

        ObservableList<GanttResource> resources = addGanttResources();
        ganttTableView = new GanttResourceView().generate();
        ganttTableView.setItems(resources);

        //timelineWithGraphicView = new TimelineWithGraphicView().init(13, true);
        //timelineWithGraphicView.setGanttPiece(resources);

        //graphicView=new BorderPane();
        //menu = new GanttMenu().init(timelineWithGraphicView.getStartDay(), timelineWithGraphicView.getEndDay(), timelineWithGraphicView);
        //graphicView.setTop(menu);  
        //graphicView.setCenter(timelineWithGraphicView);     

        //viewGantt = new SplitPane(ganttTableView.getCompleteView(), graphicView);
        viewGantt = new SplitPane( ganttTableView);

    }

    private ObservableList<GanttResource> addGanttResources() {
         ObservableList<GanttResource> resources = FXCollections
        .<GanttResource> observableArrayList();
        GanttResource resource1 = new GanttResource("Designer", LocalDate.of(2023, 02, 17), LocalDate.of(2023, 02, 26),   "aaa", "Person");
        GanttResource resource2 =new GanttResource("Architect", LocalDate.of(2023, 01, 01), LocalDate.of(2023, 01, 01), "adfghaa", "Person");
        GanttResource resource3 =new GanttResource("Developper", LocalDate.of(2023, 04, 26), LocalDate.of(2023, 05, 30), "aadsfa", "Person");
        GanttResource resource4 =new GanttResource("Security Architect", LocalDate.of(2023, 12, 27), LocalDate.of(2024, 02, 7), "aadsfa", "Machine");    
            resources.addAll(resource1, resource2, resource3, resource4);
        return resources;
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