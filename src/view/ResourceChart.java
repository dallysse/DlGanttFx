package view;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import model.GanttResource;

public class ResourceChart  {

    private SplitPane ResourceChart;
    private  BorderPane ResourceChartWithMenu;
    private GanttTableView ganttTableView;
    private TimelineWithGraphicView timelineWithGraphicView; 
    private GanttMenuView menu;
 
    public ResourceChart(){

        ObservableList<GanttResource> resources = addGanttResources();
        ganttTableView = new GanttResourceView().generate();
        ganttTableView.setItems(resources);

        timelineWithGraphicView = new TimelineResourceWithGraphicView().init(13, true);
        timelineWithGraphicView.setGanttPiece(resources);


        menu = new GanttMenuView().init(timelineWithGraphicView.getStartDay(), timelineWithGraphicView.getEndDay(), timelineWithGraphicView);

        ResourceChart = new SplitPane(ganttTableView, timelineWithGraphicView);

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