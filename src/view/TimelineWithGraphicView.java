package view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.GanttTask;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class TimelineWithGraphicView extends VBox {

   // formats
   private DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\n dd "); 
   private DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("EEEE dd. MMM yyyy "); 

   private final static String styleFormat= "-fx-background-color: %s";

   private String weekendColor="lightblue";

   // public VBox view;
    private YearMonth currentYearMonth;

    // labels
    private String nowLabel = "Now";
    private String earliestLabel = "Earliest";
    private String lastestLabel = "Lastest";
    private TableView<GanttTask> tableView = new TableView<>();
    private ScrollPane tableScroll =  new ScrollPane();

    /**
     * set list of day in a list view
     * @param firstDay
     * @param lastDay
     * @return
     */

        /**
     * init time timeline
     * 
     * the number can be the number of days or the number of months
     * @param number
     * @param isNumberOfMonth
     * @return
     */
    public TimelineWithGraphicView init(int number, boolean isNumberOfMonth) {
        currentYearMonth = YearMonth.now();
        LocalDate today = LocalDate.now() ;
        LocalDate startDay = (isNumberOfMonth) ? currentYearMonth.atDay(1) : today;
        LocalDate endDay = (isNumberOfMonth) ? currentYearMonth.plusMonths(number).atEndOfMonth():
        today.plusDays(number);
        
       return generate(startDay, endDay);
    }

    public TimelineWithGraphicView generate(LocalDate firstDay, LocalDate lastDay){
        // get list of days
       
        this.tableView = setListOfDay(firstDay, lastDay);

        tableScroll = new ScrollPane(tableView);
        tableScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        tableScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // get list of years
        ListView<Integer> yearlist = getListOfYears(firstDay, lastDay);

        // scroll to first day of years
        scrollToFirstDayOfYearsEvent(yearlist);
 
        Button now = new Button(nowLabel);

        // go to now
        scrollToGivenDate(now, LocalDate.now());

        Button earliest = new Button(earliestLabel);

        // go to earliest
        scrollToGivenDate(earliest, firstDay);

        Button lastest = new Button(lastestLabel);

        // go to earliest
        scrollToGivenDate(lastest, lastDay);

        Image imgNow = new Image("ressources/clock.png");
        ImageView iconN = new ImageView(imgNow);
        now.setGraphic(iconN); 

        Image ImgEarliest = new Image("ressources/back.png");
        ImageView iconE = new ImageView(ImgEarliest);
        earliest.setGraphic(iconE);

        Image Imglast = new Image("ressources/next.png");
        ImageView iconL = new ImageView(Imglast);
        lastest.setGraphic(iconL); 

        HBox menu = new HBox(10);

        HBox.setHgrow(now, Priority.ALWAYS);
        HBox.setHgrow(earliest, Priority.ALWAYS);
        HBox.setHgrow(lastest, Priority.ALWAYS);
        HBox.setHgrow(yearlist, Priority.ALWAYS);
        menu.setAlignment(Pos.CENTER);

        menu.getChildren().addAll( now, earliest, lastest, yearlist); 
       
        this.getChildren().addAll(menu, tableScroll);

        //menu.getChildren().addAll( now, earliest, lastest, ylist); 
       
        return this;
   }


    public static boolean isWeekend(LocalDate date) {
		return date.getDayOfWeek() == DayOfWeek.SATURDAY 
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY;
	
    }


    public TableView<GanttTask> setListOfDay(LocalDate firstDay, LocalDate lastDay){
        TableView<GanttTask> table = new TableView<GanttTask>();

        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        int weekNumber = firstDay.get(weekFields.weekOfWeekBasedYear());

        // get first year
       // int year=firstDay.getYear();
       // TableColumn<GanttTask, ?> yearColumn=new TableColumn<>(Integer.toString(year));
        TableColumn<GanttTask, ?> yearColumn=new TableColumn<>("W" + weekNumber + ", " + firstDay.format(weekFormatter));

        for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
            // get current year
          //  int currentYear=date.getYear();
          int currentWeeknumber=date.get(weekFields.weekOfWeekBasedYear());

            if(currentWeeknumber != weekNumber){
                // add year column and subcolumns to the table
                table.getColumns().add(yearColumn);

                // take the current year
                weekNumber = currentWeeknumber;

                // create a new year column 
                yearColumn=new TableColumn<>("W" + weekNumber+ ", " + date.format(weekFormatter));
            }

            TableColumn<GanttTask, ?> yearSubcolumn=new TableColumn<>(date.format(dayFormatter));

            if(isWeekend(date)){
                yearSubcolumn.setStyle(String.format(styleFormat, weekendColor));
            }

            // create year subcolumns
            yearColumn.getColumns().add(yearSubcolumn); 
            //yearColumn.getStyleClass().add("table-view .column-header-background;");
            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);                     
        }

        return table;
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

    public void scrollToFirstDayOfYearsEvent(ListView<Integer> yearlist){
        yearlist.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                 int selectedYear = yearlist.getSelectionModel().getSelectedItem();

                
    
               /*  List<LocalDate> daysOfSelectedYearList = tableView.getItems().stream()
                .filter(e->e.getYear() == selectedYear).collect(Collectors.toList());
    
                if(!daysOfSelectedYearList.isEmpty()){
                    scrollAndSelect(daysOfSelectedYearList.get(0));
                } */
                
                List<TableColumn<GanttTask, ?>> columnOfSelectedYearList= tableView.getColumns().stream()
                .filter(e-> e.getText() != null &&  e.getText().contains(String.valueOf(selectedYear)))
                .collect(Collectors.toList());

                if(!columnOfSelectedYearList.isEmpty()){
                    System.out.println(columnOfSelectedYearList.get(0).getText());
                    
                    tableView.scrollToColumn(columnOfSelectedYearList.get(0));
                }
              

            }
        }); 
    }

    /**
     * scroll to given date
     * @param button
     * @param date
     */
    public void scrollToGivenDate(Button button, LocalDate date){
        button.setOnAction(new EventHandler<ActionEvent>() 
                {
                    @Override public void handle(ActionEvent e) 
                    {
                        //scrollAndSelect(date);
                    }
                }); 
    }

    /**
     * * scroll and select to given date
     * @param date
     */
    private void scrollAndSelect(int date){
        TableColumn column = tableView.getColumns().get(date);

        tableView.scrollTo(date);
        //tableView.getSelectionModel().clearSelection();
        //tableView.getSelectionModel().select(date);
    }
    
}
