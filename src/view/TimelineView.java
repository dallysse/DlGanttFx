
package view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.GanttTask;

public class TimelineView extends VBox {

    // formats
    private DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\n MMM dd .yy");
    private final static String styleFormat = "-fx-background-color: %s";

    private String weekendColor = "blue";

    // public VBox view;
    private YearMonth currentYearMonth;

    // labels
    private String nowLabel = "Now";
    private String earliestLabel = "Earliest";
    private String lastestLabel = "Lastest";

    // list dates
    private ListView<LocalDate> list = new ListView<>();
    private ScrollPane tableScroll = new ScrollPane();

    private TableView<GanttTask> table = new TableView<>();

    /**
     * init time timeline
     * 
     * the number can be the number of days or the number of months
     * 
     * @param number
     * @param isNumberOfMonth
     * @return
     */
    public TimelineView init(int number, boolean isNumberOfMonth) {
        currentYearMonth = YearMonth.now();
        LocalDate today = LocalDate.now();
        LocalDate startDay = (isNumberOfMonth) ? currentYearMonth.atDay(1) : today;
        LocalDate endDay = (isNumberOfMonth) ? currentYearMonth.plusMonths(number).atEndOfMonth()
                : today.plusDays(number);

        return generate(startDay, endDay);
    }

    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;

    }

    /**
     * generate time line view for the given interval
     * 
     * @param firstDay
     * @param lastDay
     * @return
     */
    public TimelineView generate(LocalDate firstDay, LocalDate lastDay) {
        // get list of days
        this.list = setListOfDay(firstDay, lastDay);

        // color weekends
        colorWeekends(list);

        // get list of years
        ListView<Integer> ylist = getListOfYears(firstDay, lastDay);

        // scroll to first day of years
        scrollToFirstDayOfYearsEvent(ylist);

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
        HBox.setHgrow(ylist, Priority.ALWAYS);
        menu.setAlignment(Pos.CENTER);

        menu.getChildren().addAll(now, earliest, lastest, ylist);

        this.getChildren().addAll(menu, list, ylabel, tableScroll);
        return this;
    }

    /**
     * set list of day in a list view
     * 
     * @param firstDay
     * @param lastDay
     * @return
     */
    Label ylabel = new Label();

    public ListView<LocalDate> setListOfDay(LocalDate firstDay, LocalDate lastDay) {
        ListView<LocalDate> list = new ListView<LocalDate>();

        for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
            TableColumn<GanttTask, LocalDate> col = new TableColumn<>(date.format(dayFormatter));
            this.table.getColumns().add(col);
            tableScroll = new ScrollPane(this.table);
            tableScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            tableScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            // ylabel.setText(date.format(dayFormatter));
            // allDays.addAll(date);
            // col.setText(date.format(dayFormatter));
            ylabel.setText(Integer.toString(date.getYear()));
            list.getItems().add(date);
            list.setOrientation(Orientation.HORIZONTAL);

            list.setPrefWidth(800);
            list.setPrefHeight(70);
            list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        }
        return list;
    }

    /**
     * color weekends git config --global user.name "dallyssedjouhou"
     * 
     * @param list
     */
    public void colorWeekends(ListView<LocalDate> list) {

        this.list.setCellFactory(new Callback<ListView<LocalDate>, ListCell<LocalDate>>() {
            @Override
            public ListCell<LocalDate> call(ListView<LocalDate> param) {
                return new ListCell<LocalDate>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            // There is no item to display in this cell, so leave it empty
                            setGraphic(null);

                            // Clear the style from the cell
                            setStyle(null);

                        } else {
                            // If the item is equal to the first item in the list, set the style
                            setStyle(null);
                            setText(item.format(dayFormatter));
                            if (isWeekend(item)) {
                                setStyle(String.format(styleFormat, weekendColor));
                            }
                        }
                    }
                };
            }
        });
    }

    /**
     * get list of years
     * 
     * @param firstDay
     * @param lastDay
     * @return
     */
    public ListView<Integer> getListOfYears(LocalDate firstDay, LocalDate lastDay) {
        int firstOfYear = firstDay.getYear();
        int lastOfYear = lastDay.getYear();

        ListView<Integer> ylist = new ListView<Integer>();
        ylist.setOrientation(Orientation.HORIZONTAL);
        // Set the Size of the ListView
        ylist.setPrefSize(30, 30);

        for (int y = firstOfYear; y <= lastOfYear; y++) {
            ylist.getItems().add(y);
        }
        return ylist;
    }

    // events

    /**
     * scroll to the first day of the year event
     * 
     * @param ylist
     */
    public void scrollToFirstDayOfYearsEvent(ListView<Integer> ylist) {
        ylist.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                int selectedYear = ylist.getSelectionModel().getSelectedItem();

                List<LocalDate> daysOfSelectedYearList = list.getItems().stream()
                        .filter(e -> e.getYear() == selectedYear)
                        .collect(Collectors.toList());

                if (!daysOfSelectedYearList.isEmpty()) {
                    scrollAndSelect(daysOfSelectedYearList.get(0));
                }
            }
        });
    }

    /**
     * scroll to given date
     * 
     * @param button
     * @param date
     */
    public void scrollToGivenDate(Button button, LocalDate date) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                scrollAndSelect(date);
            }
        });
    }

    /**
     * * scroll and select to given date
     * 
     * @param date
     */
    private void scrollAndSelect(LocalDate date) {
        list.scrollTo(date);
        list.getSelectionModel().clearSelection();
        list.getSelectionModel().select(date);
    }

}
