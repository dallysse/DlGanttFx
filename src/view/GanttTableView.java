package view;

import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import model.TaskPriority;
import model.GanttData.State;

public abstract class GanttTableView<T> extends TableView<T>{

    private BorderPane completeView;
    protected GridPane legendBox = new GridPane();
    protected TaskPriority high = new TaskPriority(new Label("Hight Priority"), Color.PALEVIOLETRED) ;
    protected TaskPriority medium = new TaskPriority(new Label("Medium Priority"), Color.SKYBLUE) ;
    protected TaskPriority low = new TaskPriority(new Label("Low Priority"), Color.PALEGREEN) ;

    protected String name = "Name";
    protected String start = "Start";
    protected String end = "End";
    protected String duration = "Duration";
    protected String state = "State";
    protected String complete = "Work Complete";
    protected String info = "Infos";


    public GanttTableView() {

    }

    public GanttTableView<T> generate () {
        createGanttTableView();
        completeView = new BorderPane();
        completeView.setCenter(this);
        completeView.setBottom(legendBox);
        addLegend();
/* 
    graphicView = new TimelineWithGraphicView();

    this.getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (ObservableValue<? extends T> observable, T oldValue,
            T newValue) -> {
            if (observable != null && observable.getValue() != null) {
                graphicView.setGanttPiece(newValue);
            }
          }) ; */

          return this;
    }

    private void createGanttTableView(){
        //Creating columns
        TableColumn< T, String> nameCol = new TableColumn< T, String> (name);
        nameCol.setCellValueFactory(new PropertyValueFactory<T, String>("name"));

        TableColumn< T, LocalDate>  startCol = new TableColumn< T, LocalDate>(start);
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn< T, LocalDate> endCol = new TableColumn< T, LocalDate>(end);
        endCol.setCellValueFactory(new PropertyValueFactory< T, LocalDate>("endDate"));

        TableColumn< T, Integer> durationCol = new TableColumn< T, Integer>(duration);
        durationCol.setCellValueFactory(new PropertyValueFactory< T, Integer>("duration"));


        TableColumn< T, State> stateCol = new TableColumn< T, State>(state);
        stateCol.setCellValueFactory(new PropertyValueFactory< T, State>("state")); 

        TableColumn< T, Double> workCompleteCol = new TableColumn<T, Double> (complete);
        workCompleteCol.setCellValueFactory(new PropertyValueFactory<T, Double>(
        "workComplete"));
        workCompleteCol
        .setCellFactory(ProgressBarTableCell.<T> forTableColumn()); 

        Callback<TableColumn<T, Double>, TableCell<T, Double>> cellFactory =
        new Callback<TableColumn<T, Double>, TableCell<T, Double>>() {
            public TableCell call(TableColumn<T, Double> p) {
                return new TableCell<T, Double>() {

                    private ProgressBar pb = new ProgressBar();

                    @Override
                    public void updateItem(Double item, boolean empty) {
                        if(item != null){
                            super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            pb.setProgress(item);
                            setGraphic(pb);
                        }
                        }
                    }
                };
            }
        };
        workCompleteCol.setCellFactory(cellFactory);

        TableColumn< T, String> infoCol = new TableColumn< T, String>(info);
        infoCol.setCellValueFactory(new PropertyValueFactory< T, String>("info"));

        
        this.getColumns().addAll(nameCol, startCol, endCol, durationCol, stateCol, workCompleteCol, infoCol);

        // add specific columns
        addSpecificColumns();
    }

    public abstract void addSpecificColumns();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
    

