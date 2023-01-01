package view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.GanttTask;
import view.GanttBarPiece.PieceType;


public class TimelineWithGraphicView extends TableView<GanttTask> {

   // formats
    private DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\n dd "); 
    private DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("EEEE dd. MMM yyyy "); 


    private final static String styleFormat= "-fx-background-color: %s";

    private String weekendColor="lightblue";
    
    private YearMonth currentYearMonth;

    private LocalDate startDay;
    private LocalDate endDay;

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
        this.startDay = (isNumberOfMonth) ? currentYearMonth.atDay(1) : today;
        this.endDay = (isNumberOfMonth) ? currentYearMonth.plusMonths(number).atEndOfMonth():
        today.plusDays(number);
        this.getStyleClass().add("timeline");
       return generate(startDay, endDay);
    }

    public TimelineWithGraphicView generate(LocalDate firstDay, LocalDate lastDay){
        // get list of days      
        setListOfDay(firstDay, lastDay);
        getItems().add(new GanttTask());
        return this;
   }


    public static boolean isWeekend(LocalDate date) {
		return date.getDayOfWeek() == DayOfWeek.SATURDAY 
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY;
	
    }

    public TableView<GanttTask> setListOfDay(LocalDate firstDay, LocalDate lastDay){
        //TableView<GanttTask> table = new TableView<GanttTask>();

        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        int weekNumber = firstDay.get(weekFields.weekOfWeekBasedYear());

        // get first week
        TableColumn<GanttTask, GanttBarPiece> weekColumn=new TableColumn<GanttTask, GanttBarPiece>("W" + weekNumber + ", " + firstDay.format(weekFormatter));
        
        // add first week column
        getColumns().add(weekColumn);
        for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
            // get current week
          //  int currentYear=date.getYear();
            int currentWeeknumber=date.get(weekFields.weekOfWeekBasedYear());

            if(currentWeeknumber != weekNumber){

                // take the current week
                weekNumber = currentWeeknumber;

                // create a new week column 
                weekColumn=new TableColumn<GanttTask, GanttBarPiece>("W" + weekNumber+ ", " + date.format(weekFormatter));

                // add week column and subcolumns to the table
                getColumns().add(weekColumn);
            }

            TableColumn<GanttTask, GanttBarPiece> weekSubcolumn=new TableColumn<GanttTask, GanttBarPiece>(date.format(dayFormatter));

            if(isWeekend(date)){
                weekSubcolumn.setStyle(String.format(styleFormat, weekendColor));
            }

            // create week subcolumns
            weekColumn.getColumns().add(weekSubcolumn); 
            weekColumn.getStyleClass().add("table-view");
        }

        return this;
    }

    public void setGanttPiece(ObservableList<GanttTask> ganttTasks ){
         for(GanttTask ganttTask : ganttTasks){
            setGanttPiece(ganttTask); 
         }
        
    }

    public void setGanttPiece(GanttTask ganttTask){
        // add itemss
        //this.getItems().add(ganttTask);

        // check if start and end are defined
        if(ganttTask.getStartDate() != null && ganttTask.getEndDate() != null){
            TableColumn<GanttTask, GanttBarPiece> firstColumn = findDayColumn (ganttTask.getStartDate());
            TableColumn<GanttTask, GanttBarPiece> lastColumn = findDayColumn (ganttTask.getEndDate());

            if(firstColumn != null && lastColumn != null){
                // draw diagram
                drawDiagram(firstColumn, lastColumn, ganttTask.getName());
                

            } else {
                System.err.println(String.format("gantt piece could not be defined. reason: At least one column could not be found for start: %s end: %s", ganttTask.getStartDate(), ganttTask.getEndDate()));
            }
        } 
        
    }
  
    public TableColumn<GanttTask, GanttBarPiece> findDayColumn(LocalDate localDate){
        if(startDay.equals(localDate)){
            return findDayColumn(localDate, false);
        }else{
            return findDayColumn(localDate, true);
        }
        
    }

    public TableColumn<GanttTask, GanttBarPiece> findDayColumn(LocalDate localDate, boolean chooseFirstDayOfWeek){
        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        int weekNumber = localDate.get(weekFields.weekOfWeekBasedYear());
        int dayOfWeek = localDate.getDayOfWeek().getValue();

        // find week column
        String weekColumnName = (chooseFirstDayOfWeek) ? "W" + weekNumber + ", " + localDate.minusDays(dayOfWeek - 1).format(weekFormatter) :
        "W" + weekNumber + ", " + localDate.format(weekFormatter);
        Optional<TableColumn<GanttTask, ?>> weekColumnOpt = findDayColumn(this.getColumns(), weekColumnName);

        // check if the week column exists
        if(weekColumnOpt.isPresent()) {
            String weekSubcolumnName = localDate.format(dayFormatter);

            // find day column
            Optional<TableColumn<GanttTask, ?>> weekSubcolumn = findDayColumn(weekColumnOpt.get().getColumns(), weekSubcolumnName);

            if(weekSubcolumn.isPresent()){
                return (TableColumn<GanttTask, GanttBarPiece>) weekSubcolumn.get();
            } else {
            System.err.println(String.format("Day column '%s' could not be found", weekColumnName));
            }

        } else {
            System.err.println(String.format("Week column '%s' could not be found", weekColumnName));
        }
        return null;
    }

    private Optional<TableColumn<GanttTask, ?>> findDayColumn(ObservableList<TableColumn<GanttTask,?>> columns, String columnName){
        return columns.stream()
        .filter(e -> e.getText() != null && e.getText().equalsIgnoreCase(columnName)).findFirst();
    }

    public void drawDiagram(TableColumn<GanttTask, GanttBarPiece> startColumn, TableColumn<GanttTask, GanttBarPiece> endColumn, String name){
        boolean isNextCenter = false;

        if(startColumn.equals(endColumn)){
            ((TableColumn<GanttTask, GanttBarPiece>)startColumn).setCellValueFactory(e-> new ObservableGanttBarPiece(PieceType.COMPLET, name));
        } else {
            for(TableColumn<GanttTask, ?> column : getColumns().stream()
            .flatMap(e->e.getColumns().stream()).collect(Collectors.toList())){
                    if(column.equals(startColumn)){
                        isNextCenter = true;
                        ((TableColumn<GanttTask, GanttBarPiece>)column).setCellValueFactory(e-> new ObservableGanttBarPiece(PieceType.BEGINNING, name));
                    } else if(column.equals(endColumn)){
                        isNextCenter = false;
                        ((TableColumn<GanttTask, GanttBarPiece>)column).setCellValueFactory(e-> new ObservableGanttBarPiece(PieceType.END));
                        break;
                    } else if (isNextCenter) {
                        ((TableColumn<GanttTask, GanttBarPiece>)column).setCellValueFactory(e-> new ObservableGanttBarPiece(PieceType.CENTER));
                    }
                }
        }
    }

    public DateTimeFormatter getDayFormatter() {
        return dayFormatter;
    }

    public void setDayFormatter(DateTimeFormatter dayFormatter) {
        this.dayFormatter = dayFormatter;
    }

    public DateTimeFormatter getWeekFormatter() {
        return weekFormatter;
    }

    public void setWeekFormatter(DateTimeFormatter weekFormatter) {
        this.weekFormatter = weekFormatter;
    }

    public static String getStyleformat() {
        return styleFormat;
    }

    public String getWeekendColor() {
        return weekendColor;
    }

    public void setWeekendColor(String weekendColor) {
        this.weekendColor = weekendColor;
    }

    public YearMonth getCurrentYearMonth() {
        return currentYearMonth;
    }

    public void setCurrentYearMonth(YearMonth currentYearMonth) {
        this.currentYearMonth = currentYearMonth;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    
}
