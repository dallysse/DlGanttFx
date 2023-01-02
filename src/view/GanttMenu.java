package view;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.GanttData;
import model.GanttTask;

public class GanttMenu extends HBox{
    
    private double hboxSize = 10.0;
     // labels
     private String nowLabel = "Now";
     private String earliestLabel = "Earliest";
     private String lastestLabel = "Lastest";

    public GanttMenu() {

    }
    
    public GanttMenu(String nowLabel, String earliestLabel, String lastestLabel, double hboxSize) {
        this.nowLabel = nowLabel;
        this.earliestLabel = earliestLabel;
        this.lastestLabel = lastestLabel;
        this.hboxSize = hboxSize;
    }


    public GanttMenu init(LocalDate firstDay, LocalDate lastDay, TimelineWithGraphicView<GanttData> tableView){

        // get list of years
        ListView<Integer> yearlist = getListOfYears(firstDay, lastDay);

          // scroll to first day of years
          scrollToFirstDayOfYearsEvent(yearlist, tableView);
 
          Button now = new Button(nowLabel);
  
          // go to now
          LocalDate nowDate = LocalDate.now();
          if(nowDate.equals(firstDay)){
            scrollToGivenDate(now, nowDate, tableView, false);
          } else {
            scrollToGivenDate(now, nowDate, tableView, true);
          }
  
          Button earliest = new Button(earliestLabel);
  
          // go to earliest
          scrollToGivenDate(earliest, firstDay, tableView, false);
  
          Button lastest = new Button(lastestLabel);
  
          // go to lastest
          scrollToGivenDate(lastest, lastDay, tableView, true);
  
          Image imgNow = new Image("ressources/clock.png");
          ImageView iconN = new ImageView(imgNow);
          now.setGraphic(iconN); 
  
          Image ImgEarliest = new Image("ressources/back.png");
          ImageView iconE = new ImageView(ImgEarliest);
          earliest.setGraphic(iconE);
  
          Image Imglast = new Image("ressources/next.png");
          ImageView iconL = new ImageView(Imglast);
          lastest.setGraphic(iconL); 
  
           new HBox(hboxSize);
  
          HBox.setHgrow(now, Priority.ALWAYS);
          HBox.setHgrow(earliest, Priority.ALWAYS);
          HBox.setHgrow(lastest, Priority.ALWAYS);
          HBox.setHgrow(yearlist, Priority.ALWAYS);
          this.setAlignment(Pos.CENTER);  
          this.getChildren().addAll( now, earliest, lastest, yearlist); 
         
          return this;
    }

    public ListView<Integer> getListOfYears(LocalDate firstDay, LocalDate lastDay){
        int firstOfYear = firstDay.getYear();
        int lastOfYear = lastDay.getYear();

        ListView<Integer> ylist = new ListView<Integer>();
        ylist.setOrientation(Orientation.HORIZONTAL);
        // Set the Size of the ListView
        ylist.setPrefSize(30, 30);

        for (int y = firstOfYear; y<=lastOfYear; y++) {
                ylist.getItems().add(y);
        }
        return ylist;
    }
        // events

    public void scrollToFirstDayOfYearsEvent(ListView<Integer> yearlist, TimelineWithGraphicView<GanttData> tableView){
        yearlist.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                int selectedYear = yearlist.getSelectionModel().getSelectedItem();
                
                List<TableColumn<GanttData, ?>> columnOfSelectedYearList= tableView.getColumns().stream()
                .filter(e-> e.getText() != null &&  e.getText().contains(String.valueOf(selectedYear)))
                .collect(Collectors.toList());

                if(!columnOfSelectedYearList.isEmpty()){
                    tableView.scrollToColumn(columnOfSelectedYearList.get(0).getColumns().get(0));
                }
              

            }
        }); 
    }

      /**
     * scroll to given date
     * @param button
     * @param date
     * @param chooseFirstDayOfWeek
     */
    public void scrollToGivenDate(Button button, LocalDate date, TimelineWithGraphicView tableView, boolean chooseFirstDayOfWeek){
        button.setOnAction(new EventHandler<ActionEvent>() 
                {
                    @Override public void handle(ActionEvent e) 
                    {
                        TableColumn<GanttTask, GanttBarPiece> column = tableView.findDayColumn(date, chooseFirstDayOfWeek);

                        if(column != null){
                            scrollAndSelect(column, tableView);
                        }else{
                            System.err.println(String.format("Date %s not found", date));
                        }
                        
                    }
                }); 
    }

    /**
     * * scroll and select to given date
     * @param date
     */
    private void scrollAndSelect(TableColumn<GanttTask, GanttBarPiece> column, TimelineWithGraphicView tableView){
        tableView.scrollToColumn(column);
    }

 
}
