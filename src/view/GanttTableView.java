package view;

import java.time.LocalDate;
import java.util.Collection;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.GanttTask;

public class GanttTableView extends TableView<GanttTask>{


    private  ObservableList<GanttTask> row;
    private  Collection<TableColumn<GanttTask, String>> column;
    private TableView<GanttTask> table= new TableView<GanttTask>();
    private BorderPane completeView;
    private VBox details = new VBox();
    private HBox lpHBox;
    private Label lp= new Label("Low Priority");
    private HBox mpHBox ;
    private Label mp= new Label("Medium Priority");
    private HBox hpHBox ;
    private Label hp= new Label("Hight Priority");
    private Rectangle rect1 = new Rectangle();
    private Rectangle rect2 = new Rectangle();
    private Rectangle rect3 = new Rectangle();

    public GanttTableView() {
      //Creating columns
      TableColumn< GanttTask, String> nameCol = new TableColumn< GanttTask, String> ("Task Name");
      nameCol.setCellValueFactory(new PropertyValueFactory<GanttTask, String>("name"));

      TableColumn< GanttTask, LocalDate>  startCol = new TableColumn< GanttTask, LocalDate>("Task Start");
      //startCol.setPrefWidth(100);
      startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));

      TableColumn< GanttTask, LocalDate> endCol = new TableColumn< GanttTask, LocalDate>("Task End");
      //endCol.setPrefWidth(100);
      endCol.setCellValueFactory(new PropertyValueFactory< GanttTask, LocalDate>("endDate"));

      TableColumn< GanttTask, Integer> priorityCol = new TableColumn< GanttTask, Integer>("Priority");
      priorityCol.setCellValueFactory(new PropertyValueFactory< GanttTask, Integer>("level"));
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
                        rect.setFill(Color.PALEGREEN);  
                        details.getChildren().add(rect);
                        
                    break;
                    case 2:
                        rect.setFill(Color.SKYBLUE);  
                        details.getChildren().add(rect);
                    break;
                    case 1:
                        rect.setFill(Color.PALEVIOLETRED);  
                        details.getChildren().add(rect);
                    break;
                }
                setGraphic(rect);

            }
        }
    });

 
      TableColumn< GanttTask, Boolean> isCriticalCol = new TableColumn<GanttTask, Boolean> ("IsCritical");
      isCriticalCol.setCellValueFactory(new PropertyValueFactory< GanttTask, Boolean>("isCritical"));
      //isCriticalCol.setCellValueFactory(cellData -> cellData.getValue().getIsCritical());

      //isCriticalCol.setCellFactory(CheckBoxTableCell.forTableColumn(isCriticalCol));

      isCriticalCol.setCellFactory(column -> new CheckBoxTableCell()); 
    
      TableColumn< GanttTask, Double> workCompleteCol = new TableColumn<GanttTask, Double> ("Work Complete");
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

      TableColumn< GanttTask, String> statusCol = new TableColumn< GanttTask, String>("Task Infos");
      statusCol.setCellValueFactory(new PropertyValueFactory< GanttTask, String>("status"));

      TableColumn< GanttTask, String> infoCol = new TableColumn< GanttTask, String>("Task Infos");
      infoCol.setCellValueFactory(new PropertyValueFactory< GanttTask, String>("info"));

     
      //Adding data to the table
      //this.setItems(activities);

      //this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      this.getColumns().addAll(nameCol, startCol, endCol, priorityCol, isCriticalCol,workCompleteCol, infoCol);


      this.getItems().add(new GanttTask("Kommentar", LocalDate.of(2015, 12, 31), LocalDate.of(2016, 12, 31), 1, true, "aaa"));
      this.getItems().add(new GanttTask("a", LocalDate.of(2022, 12, 21), LocalDate.of(2022, 12, 31), 1, true, "akjjaa"));
      this.getItems().add(new GanttTask("b", LocalDate.of(2023, 01, 14), LocalDate.of(2023, 12, 31), 2, false, "adfghaa"));
      this.getItems().add(new GanttTask("c", LocalDate.of(2022, 12, 26), LocalDate.of(2022, 12, 30), 3, true, "aadsfa"));
      this.getItems().add(new GanttTask("d", LocalDate.of(2022, 12, 27), LocalDate.of(2022, 12, 28), 3, false, "aadsfa"));

      completeView = new BorderPane();
      completeView.setCenter(this);
      completeView.setBottom(details);


      //echelle
      rect1.setX(20); //setting the X coordinate of upper left //corner of rectangle   
      rect1.setY(20); //setting the Y coordinate of upper left //corner of rectangle   
      rect1.setWidth(20); //setting the width of rectangle   
      rect1.setHeight(20); 
      rect1.setFill(Color.PALEGREEN);  
      lpHBox = new HBox (rect1, lp);
      lpHBox.setSpacing(50);
      HBox.setMargin(rect1, new Insets(10, 10, 10, 10));
      HBox.setMargin(lp, new Insets(10, 10, 10, 10));


      rect2.setX(20); //setting the X coordinate of upper left //corner of rectangle   
      rect2.setY(20); //setting the Y coordinate of upper left //corner of rectangle   
      rect2.setWidth(20); //setting the width of rectangle   
      rect2.setHeight(20); 
      rect2.setFill(Color.SKYBLUE);  
      mpHBox = new HBox (rect2, mp);
      mpHBox.setSpacing(50);
      HBox.setMargin(rect2, new Insets(10, 10, 10, 10));
      HBox.setMargin(mp, new Insets(10, 10, 10, 10));


      rect3.setX(20); //setting the X coordinate of upper left //corner of rectangle   
      rect3.setY(20); //setting the Y coordinate of upper left //corner of rectangle   
      rect3.setWidth(20); //setting the width of rectangle   
      rect3.setHeight(20); 
      rect3.setFill(Color.PALEVIOLETRED);  
      hpHBox = new HBox (rect3, hp);
      hpHBox.setSpacing(50);
      HBox.setMargin(rect3, new Insets(10, 10, 10, 10));
      HBox.setMargin(hp, new Insets(10, 10, 10, 10));

      details.getChildren().addAll(lpHBox, mpHBox, hpHBox);


    }
    private void setPriorityStyle(int priority){
        switch(priority){
            case 3:
                getStyleClass().add("priorityLow");
                break;
            case 2:
                getStyleClass().add("priorityMedium");
                break;
            case 1:
                getStyleClass().add("priorityHigh");
                break;
        }
        System.out.println(getStyleClass());
    }
    public ObservableList<GanttTask> getRow() {
        return row;
    }
    public void setRow(ObservableList<GanttTask> row) {
        this.row = row;
    }
    public Collection<TableColumn<GanttTask, String>> getColumn() {
        return column;
    }
    public void setColumn(Collection<TableColumn<GanttTask, String>> column) {
        this.column = column;
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
    public VBox getDetails() {
        return details;
    }
    public void setDetails(VBox details) {
        this.details = details;
    }

 

}
    

