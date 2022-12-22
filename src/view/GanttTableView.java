package view;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale.Category;

import javax.crypto.spec.GCMParameterSpec;

import org.w3c.dom.css.Rect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

import javafx.scene.control.TableView;
import model.GanttTask;

public class GanttTableView extends TableView<GanttTask>{


    private  ObservableList<GanttTask> row;
    private  Collection<TableColumn<GanttTask, String>> column;
    private TableView<GanttTask> table= new TableView<GanttTask>();

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
                    break;
                    case 2:
                        rect.setFill(Color.SKYBLUE);  
                    break;
                    case 1:
                        rect.setFill(Color.PALEVIOLETRED);  
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

      this.getItems().add(new GanttTask("Kommentar", LocalDate.of(2015, 12, 31), LocalDate.of(2016, 12, 31), 1, true, 0.3,  "aaa"));
      this.getItems().add(new GanttTask("a", LocalDate.of(2015, 12, 31), LocalDate.of(2016, 12, 31), 1, true, 0.9,  "akjjaa"));
      this.getItems().add(new GanttTask("b", LocalDate.of(2015, 12, 31), LocalDate.of(2016, 12, 31), 2, false, 0.8,  "adfghaa"));
      this.getItems().add(new GanttTask("c", LocalDate.of(2015, 12, 31), LocalDate.of(2016, 12, 31), 3, true, 0.5,  "aadsfa"));
      this.getItems().add(new GanttTask("d", LocalDate.of(2015, 12, 31), LocalDate.of(2016, 12, 31), 3, false, 0.0,  "aadsfa"));

      //Setting the size of the table
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
 

}
    

