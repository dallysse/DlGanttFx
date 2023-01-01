package view;

import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import model.GanttTask;
import model.GanttTask.TaskState;
import model.TaskPriority;

public class GanttTableView extends TableView<GanttTask>{

    private TableView<GanttTask> table= new TableView<GanttTask>();
    private BorderPane completeView;
    private GridPane legendBox = new GridPane();
    private TaskPriority high = new TaskPriority(new Label("Hight Priority"), Color.PALEVIOLETRED) ;
    private TaskPriority medium = new TaskPriority(new Label("Medium Priority"), Color.SKYBLUE) ;
    private TaskPriority low = new TaskPriority(new Label("Low Priority"), Color.PALEGREEN) ;
    private TimelineWithGraphicView graphicView;

    private String taskName = "Task Name";
    private String taskStart = "Task Start";
    private String taskEnd = "Task End";
    private String taskDuration = "Duration";
    private String taskState = "Task State";
    private String taskPriority = "Priority";
    private String taskIsCritical = "IsCritical";
    private String taskComplete = "Work Complete";
    private String taskInfo = "Task Infos";


    public GanttTableView() {

    }

    public GanttTableView generate () {
        createGanttTaskTableView();
        completeView = new BorderPane();
        completeView.setCenter(this);
        completeView.setBottom(legendBox);
        addLegend();
/* 
    graphicView = new TimelineWithGraphicView();

    this.getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (ObservableValue<? extends GanttTask> observable, GanttTask oldValue,
            GanttTask newValue) -> {
            if (observable != null && observable.getValue() != null) {
                graphicView.setGanttPiece(newValue);
            }
          }) ; */

          return this;
    }

    private void createGanttTaskTableView(){
              //Creating columns
      TableColumn< GanttTask, String> nameCol = new TableColumn< GanttTask, String> (taskName);
      nameCol.setCellValueFactory(new PropertyValueFactory<GanttTask, String>("name"));

      TableColumn< GanttTask, LocalDate>  startCol = new TableColumn< GanttTask, LocalDate>(taskStart);
      startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));

      TableColumn< GanttTask, LocalDate> endCol = new TableColumn< GanttTask, LocalDate>(taskEnd);
      endCol.setCellValueFactory(new PropertyValueFactory< GanttTask, LocalDate>("endDate"));

      TableColumn< GanttTask, Integer> durationCol = new TableColumn< GanttTask, Integer>(taskDuration);
      durationCol.setCellValueFactory(new PropertyValueFactory< GanttTask, Integer>("taskDuration"));


      TableColumn< GanttTask, TaskState> stateCol = new TableColumn< GanttTask, TaskState>(taskState);
      stateCol.setCellValueFactory(new PropertyValueFactory< GanttTask, TaskState>("state")); 

      TableColumn< GanttTask, Integer> priorityCol = new TableColumn< GanttTask, Integer>(taskPriority);
      priorityCol.setCellValueFactory(new PropertyValueFactory< GanttTask, Integer>("priority"));
      priorityCol.setCellFactory(column -> new TableCell<GanttTask, Integer>(){
        @Override
        protected void updateItem(Integer item, boolean empty) {
            Rectangle rect = new Rectangle();
            rect.setX(20); //setting the X coordinate of upper left //corner of rectangle   
            rect.setY(20); //setting the Y coordinate of upper left //corner of rectangle   
            rect.setWidth(20); //setting the width of rectangle   
            rect.setHeight(20); 
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                switch(item){
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

 
      TableColumn< GanttTask, Boolean> isCriticalCol = new TableColumn<GanttTask, Boolean> (taskIsCritical);
      isCriticalCol.setCellValueFactory(new PropertyValueFactory< GanttTask, Boolean>("isCritical"));
      isCriticalCol.setCellValueFactory(cellData -> cellData.getValue().isCriticalProperty());
      isCriticalCol.setCellFactory(CheckBoxTableCell.forTableColumn(isCriticalCol));
      isCriticalCol.setCellFactory(column -> new CheckBoxTableCell()); 
    
      TableColumn< GanttTask, Double> workCompleteCol = new TableColumn<GanttTask, Double> (taskComplete);
      workCompleteCol.setCellValueFactory(new PropertyValueFactory<GanttTask, Double>(
        "workComplete"));
        workCompleteCol
        .setCellFactory(ProgressBarTableCell.<GanttTask> forTableColumn()); 

        Callback<TableColumn<GanttTask, Double>, TableCell<GanttTask, Double>> cellFactory =
        new Callback<TableColumn<GanttTask, Double>, TableCell<GanttTask, Double>>() {
            public TableCell call(TableColumn<GanttTask, Double> p) {
                return new TableCell<GanttTask, Double>() {

                    private ProgressBar pb = new ProgressBar();

                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            pb.setProgress(item);
                            setGraphic(pb);
                        }
                    }
                };
            }
        };
        workCompleteCol.setCellFactory(cellFactory);

      TableColumn< GanttTask, String> infoCol = new TableColumn< GanttTask, String>(taskInfo);
      infoCol.setCellValueFactory(new PropertyValueFactory< GanttTask, String>("info"));
      
       
      this.getColumns().addAll(nameCol, startCol, endCol, durationCol, priorityCol, isCriticalCol,stateCol, workCompleteCol, infoCol);
    }

    private void addLegend(){
      //legende
      HBox lpHBox = createLegendElement(low);
      HBox mpHBox = createLegendElement(medium);
      HBox hpHBox = createLegendElement(high);

      legendBox.add(lpHBox,0,0);
      legendBox.add(mpHBox,1,0);
      legendBox.add(hpHBox,2,0);
    }

    private HBox createLegendElement(TaskPriority priority){
        Rectangle rect = new Rectangle();
        rect.setX(20); //setting the X coordinate of upper left //corner of rectangle   
        rect.setY(20); //setting the Y coordinate of upper left //corner of rectangle   
        rect.setWidth(20); //setting the width of rectangle   
        rect.setHeight(20); 
        rect.setFill(priority.getColor());  
        HBox hBox = new HBox (rect, priority.getLabel());
        hBox.setSpacing(10);
        GridPane.setHgrow(hBox, Priority.ALWAYS);
        HBox.setMargin(rect, new Insets(5, 5, 5, 5));
        HBox.setMargin(priority.getLabel(), new Insets(5, 5, 5, 5));

        return hBox;
    }

    public TableView<GanttTask> getTable() {
        return table;
    }

    public void setTable(TableView<GanttTask> table) {
        this.table = table;
    }

    public BorderPane getCompleteView() {
        return completeView;
    }

    public void setCompleteView(BorderPane completeView) {
        this.completeView = completeView;
    }

    public GridPane getLegendBox() {
        return legendBox;
    }

    public void setLegendBox(GridPane legendBox) {
        this.legendBox = legendBox;
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

    public TimelineWithGraphicView getGraphicView() {
        return graphicView;
    }

    public void setGraphicView(TimelineWithGraphicView graphicView) {
        this.graphicView = graphicView;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStart() {
        return taskStart;
    }

    public void setTaskStart(String taskStart) {
        this.taskStart = taskStart;
    }

    public String getTaskEnd() {
        return taskEnd;
    }

    public void setTaskEnd(String taskEnd) {
        this.taskEnd = taskEnd;
    }

    public String getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(String taskDuration) {
        this.taskDuration = taskDuration;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskIsCritical() {
        return taskIsCritical;
    }

    public void setTaskIsCritical(String taskIsCritical) {
        this.taskIsCritical = taskIsCritical;
    }

    public String getTaskComplete() {
        return taskComplete;
    }

    public void setTaskComplete(String taskComplete) {
        this.taskComplete = taskComplete;
    }

    public String getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

   

}
    

