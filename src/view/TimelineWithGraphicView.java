package view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import util.DateUtils;

import Widgets.GanttBar;
import Widgets.GanttBar.PieceType;
import Widgets.ObservableGanttBar;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import model.Object;

public abstract class TimelineWithGraphicView<T extends Object> extends TableView<T> {

    // formats
    private DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\n dd ");
    private DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("EEEE dd. MMM yyyy ");

    private final static String styleFormat = "-fx-background-color: %s";

    private String weekendColor = "lightblue";
    private String todayColor = "lightcoral";

    TableColumn<T, GanttBar> weekSubcolumn ;

    private YearMonth currentYearMonth;

    private LocalDate startDay;
    private LocalDate endDay;

    /**
     * init time timeline
     * 
     * the number can be the number of days or the number of months
     * 
     * @param number
     * @param isNumberOfMonth
     * @return
     */
    public TimelineWithGraphicView<T> init(int number, boolean isNumberOfMonth) {
        currentYearMonth = YearMonth.now();
        LocalDate today = LocalDate.now();
        this.startDay = (isNumberOfMonth) ? currentYearMonth.atDay(1) : today;
        this.endDay = (isNumberOfMonth) ? currentYearMonth.plusMonths(number).atEndOfMonth() : today.plusDays(number);
        this.getStyleClass().add("timeline");
        return generate(startDay, endDay);
    }

    public TimelineWithGraphicView<T> generate(LocalDate firstDay, LocalDate lastDay) {
        // get list of days
        setListOfDay(firstDay, lastDay);
         getItems().add((T) new Object());
        return this;
    }

    public TableView<T> setListOfDay(LocalDate firstDay, LocalDate lastDay) {

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = firstDay.get(weekFields.weekOfWeekBasedYear());

        // get first week
        TableColumn<T, GanttBar> weekColumn = new TableColumn<T, GanttBar>(
                "W" + weekNumber + ", " + firstDay.format(weekFormatter));

        // add first week column
        getColumns().add(weekColumn);
        for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
            // get current week
            // int currentYear=date.getYear();
            LocalDate today = LocalDate.now();

            int currentWeeknumber = date.get(weekFields.weekOfWeekBasedYear());

            if (currentWeeknumber != weekNumber) {

                // take the current week
                weekNumber = currentWeeknumber;

                // create a new week column
                weekColumn = new TableColumn<T, GanttBar>("W" + weekNumber + ", " + date.format(weekFormatter));

                // add week column and subcolumns to the table
                getColumns().add(weekColumn);
            }

            weekSubcolumn = new TableColumn<T, GanttBar>(date.format(dayFormatter));

            if (DateUtils.isWeekend(date)){

                //weekSubcolumn.setStyle(String.format(styleFormat, weekendColor));
                weekSubcolumn.getStyleClass().add("WeekendColor");
            }

            if (date.equals(today)){
                weekSubcolumn.setStyle(String.format(styleFormat, todayColor));
            }
            // create week subcolumns
            weekColumn.getColumns().add(weekSubcolumn);
        }

        return this;
    }

    public void setGanttPiece(ObservableList<T> ganttTasks) {
        // this.setItems(ganttTasks);

        for (T ganttData : ganttTasks) {
            setGanttPiece(ganttData);
        }

    }

    public void setGanttPiece(T ganttData) {
        // check if start and end are defined
        if (ganttData.getStartDate() != null && ganttData.getEndDate() != null) {
            TableColumn<T, GanttBar> firstColumn = findDayColumn(ganttData.getStartDate());
            TableColumn<T, GanttBar> lastColumn = findDayColumn(ganttData.getEndDate());

            if (firstColumn != null && lastColumn != null) {
                // draw diagram
                drawDiagram(firstColumn, lastColumn, ganttData);

            } else {
                System.err.println(String.format(
                        "gantt piece could not be defined. reason: At least one column could not be found for start: %s end: %s",
                        ganttData.getStartDate(), ganttData.getEndDate()));
            }
        }

    }

    public TableColumn<T, GanttBar> findDayColumn(LocalDate localDate) {
        if (startDay.equals(localDate)) {
            return findDayColumn(localDate, false);
        } else {
            return findDayColumn(localDate, true);
        }

    }

    public TableColumn<T, GanttBar> findDayColumn(LocalDate localDate, boolean chooseFirstDayOfWeek) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = localDate.get(weekFields.weekOfWeekBasedYear());
        int dayOfWeek = localDate.getDayOfWeek().getValue();

        // find week column
        String weekColumnName = (chooseFirstDayOfWeek)
                ? "W" + weekNumber + ", " + localDate.minusDays(dayOfWeek - 1).format(weekFormatter)
                : "W" + weekNumber + ", " + localDate.format(weekFormatter);
        Optional<TableColumn<T, ?>> weekColumnOpt = findDayColumn(this.getColumns(), weekColumnName);

        // check if the week column exists
        if (weekColumnOpt.isPresent()) {
            String weekSubcolumnName = localDate.format(dayFormatter);

            // find day column
            Optional<TableColumn<T, ?>> weekSubcolumn = findDayColumn(weekColumnOpt.get().getColumns(),
                    weekSubcolumnName);

            if (weekSubcolumn.isPresent()) {
                return (TableColumn<T, GanttBar>) weekSubcolumn.get();
            } else {
                System.err.println(String.format("Day column '%s' could not be found", weekColumnName));
            }

        } else {
            System.err.println(String.format("Week column '%s' could not be found", weekColumnName));
        }
        return null;
    }

    private Optional<TableColumn<T, ?>> findDayColumn(ObservableList<TableColumn<T, ?>> columns, String columnName) {
        return columns.stream()
                .filter(e -> e.getText() != null && e.getText().equalsIgnoreCase(columnName)).findFirst();
    }

    public void drawDiagram(TableColumn<T, GanttBar> startColumn, TableColumn<T, GanttBar> endColumn,
            T ganttData) {
        boolean isNextCenter = false;

        // add itemss
        //this.getItems().add(ganttData);

        if (startColumn.equals(endColumn)) {
            ((TableColumn<T, GanttBar>) startColumn)
                    .setCellValueFactory(e -> new ObservableGanttBar(PieceType.COMPLET, ganttData.getName()));
        } else {
            for (TableColumn<T, ?> column : getColumns().stream()
                    .flatMap(e -> e.getColumns().stream()).collect(Collectors.toList())) {
                if (column.equals(startColumn)) {
                    isNextCenter = true;
                    ((TableColumn<T, GanttBar>) column).setCellValueFactory(
                            e -> new ObservableGanttBar(PieceType.BEGINNING, ganttData.getName()));
                } else if (column.equals(endColumn)) {
                    isNextCenter = false;
                    ((TableColumn<T, GanttBar>) column)
                            .setCellValueFactory(e -> new ObservableGanttBar(PieceType.END));
                    break;
                } else if (isNextCenter) {
                    ((TableColumn<T, GanttBar>) column)
                            .setCellValueFactory(e -> {
                               return new ObservableGanttBar(PieceType.CENTER);
                            });
                }
            }
        }  
/*         Callback<TableColumn<T, GanttBarPiece>, TableCell<T, GanttBarPiece>> cellFactory = new Callback<TableColumn<T, GanttBarPiece>, TableCell<T, GanttBarPiece>>() {
            public TableCell call(TableColumn<T, GanttBarPiece> p) {
                return new TableCell<T, GanttBarPiece>() {
                    @Override
                    public void updateItem(GanttBarPiece item, boolean empty) {
                        boolean isNextCenter = false;

                        if (startColumn.equals(endColumn)) {
                            ((TableColumn<T, GanttBarPiece>) startColumn)
                                            .setCellValueFactory(e -> {
                                               return new ObservableGanttBarPiece(PieceType.CENTER);
                                            });
                        } else {
                            for (TableColumn<T, ?> column : getColumns().stream()
                                    .flatMap(e -> e.getColumns().stream()).collect(Collectors.toList())) {

                                if (column.equals(startColumn)) {
                                    isNextCenter = true;
                                    ((TableColumn<T, GanttBarPiece>) column)
                                            .setCellValueFactory(e -> {
                                               return new ObservableGanttBarPiece(PieceType.CENTER);
                                            });
                                } else if (column.equals(endColumn) ) {
                                    isNextCenter = false;
                                    ((TableColumn<T, GanttBarPiece>) column)
                                            .setCellValueFactory(e -> {
                                               return new ObservableGanttBarPiece(PieceType.CENTER);
                                            });
                                    break;
                                } else if (isNextCenter) {
                                    ((TableColumn<T, GanttBarPiece>) column)
                                            .setCellValueFactory(e -> {
                                               return new ObservableGanttBarPiece(PieceType.CENTER);
                                            });
                                }
                            }
                        }




                        if (item != null) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(new GanttBarPiece(PieceType.CENTER));
                            }
                        }
                    }
                    
                };
            }
        };

        if(cellFactory!=null){((TableColumn<T, GanttBarPiece>) getColumns().get(0)).setCellFactory(cellFactory);}
         */
        
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
