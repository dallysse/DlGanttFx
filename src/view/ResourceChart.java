package view;

import java.time.LocalDate;

import controls.GanttChartHbox;
import controls.GanttResourceControl;
import controls.GanttTableControl;
import controls.TimelineGraphControl;
import controls.TimelineGraphResourceControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import model.GanttResource;

public class ResourceChart  {

    private SplitPane ResourceChart;
    private  BorderPane ResourceChartWithMenu;
    private GanttTableControl<GanttResource> ganttTableControl;
    private TimelineGraphControl<GanttResource> timelineGraphControl; 
    private GanttChartHbox<GanttResource> menu;
 
    public ResourceChart(){

        ObservableList<GanttResource> resources = addGanttResources();
        ganttTableControl = new GanttResourceControl().generate();
        ganttTableControl.setItems(resources);

        timelineGraphControl = new TimelineGraphResourceControl().init(13, true);
        for(GanttResource resource: resources){
            timelineGraphControl.setGanttPiece(resource);
        }


        menu = new GanttChartHbox<GanttResource>().init(timelineGraphControl.getStartDay(), timelineGraphControl.getEndDay(), timelineGraphControl);

        ResourceChart = new SplitPane(ganttTableControl, timelineGraphControl);

        ResourceChartWithMenu=new BorderPane();
        ResourceChartWithMenu.setTop(menu);  
        ResourceChartWithMenu.setCenter(ResourceChart);   
    }

    private ObservableList<GanttResource> addGanttResources() {
         ObservableList<GanttResource> resources = FXCollections
        .<GanttResource> observableArrayList();
        GanttResource resource1 = new GanttResource("Martin", LocalDate.of(2023, 02, 17), LocalDate.of(2023, 02, 26),   "Font End", "Designer");
        GanttResource resource2 =new GanttResource("Sven", LocalDate.of(2023, 01, 01), LocalDate.of(2023, 01, 19), "Back End", "Architect");
        GanttResource resource3 =new GanttResource("Ursel", LocalDate.of(2023, 04, 26), LocalDate.of(2023, 05, 30), "Back End", "Developper");
        GanttResource resource4 =new GanttResource("Olaf", LocalDate.of(2023, 12, 27), LocalDate.of(2024, 02, 7), "Back End", "Security Architect");    
        resources.addAll(resource1, resource2, resource3, resource4);
        return resources;
    }

    public SplitPane getResourceChart() {
        return ResourceChart;
    }

    public void setResourceChart(SplitPane resourceChart) {
        ResourceChart = resourceChart;
    }

    public BorderPane getResourceChartWithMenu() {
        return ResourceChartWithMenu;
    }

    public void setResourceChartWithMenu(BorderPane resourceChartWithMenu) {
        ResourceChartWithMenu = resourceChartWithMenu;
    }


}