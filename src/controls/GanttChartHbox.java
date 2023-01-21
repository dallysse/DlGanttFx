package controls;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import Widgets.GanttBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.GanttDataModel;

public class GanttChartHbox<T extends GanttDataModel> extends HBox {

    private double hboxSize = 10.0;
    // labels
    private String nowLabel = "Now";
    private String earliestLabel = "Earliest";
    private String lastestLabel = "Lastest";

    public GanttChartHbox() {

    }

    public GanttChartHbox(String nowLabel, String earliestLabel, String lastestLabel, double hboxSize) {
        this.nowLabel = nowLabel;
        this.earliestLabel = earliestLabel;
        this.lastestLabel = lastestLabel;
        this.hboxSize = hboxSize;

    }

    public GanttChartHbox<T> init(LocalDate firstDay, LocalDate lastDay, TimelineGraphControl<T> tableView) {

        // get list of years
        ListView<Integer> yearlist = getListOfYears(firstDay, lastDay);

        // scroll to first day of years
        scrollToFirstDayOfYearsEvent(yearlist, tableView);

        Button now = new Button(nowLabel);

        // go to now
        LocalDate nowDate = LocalDate.now();
        if (nowDate.equals(firstDay)) {
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
        this.getChildren().addAll(now, earliest, lastest, yearlist);
        this.setSpacing(20);


        return this;
    }

    /**
     * return list of years from firstDay to lastDay
     * 
     * @param firstDay
     * @param lastDay
     * @return a list of Integer
     */
    public ListView<Integer> getListOfYears(LocalDate firstDay, LocalDate lastDay) {
        int firstOfYear = firstDay.getYear();
        int lastOfYear = lastDay.getYear();

        ListView<Integer> yearsList = new ListView<Integer>();
        yearsList.setOrientation(Orientation.HORIZONTAL);
        // Set the Size of the ListView
        yearsList.setPrefSize(30, 30);

        for (int y = firstOfYear; y <= lastOfYear; y++) {
            yearsList.getItems().add(y);
        }
        return yearsList;
    }

    // events
    /**
     * scroll to given date
     * 
     * @param yearlist
     * @param tableView
     */
    public void scrollToFirstDayOfYearsEvent(ListView<Integer> yearlist, TimelineGraphControl<T> tableView) {
        yearlist.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                int selectedYear = yearlist.getSelectionModel().getSelectedItem();

                List<TableColumn<T, ?>> columnOfSelectedYearList = tableView.getColumns().stream()
                        .filter(e -> e.getText() != null && e.getText().contains(String.valueOf(selectedYear)))
                        .collect(Collectors.toList());

                if (!columnOfSelectedYearList.isEmpty()) {
                    tableView.scrollToColumn(columnOfSelectedYearList.get(0).getColumns().get(0));
                }

            }
        });
    }

    /**
     * scroll to given date
     * 
     * @param button
     * @param date
     * @param chooseFirstDayOfWeek
     */
    public void scrollToGivenDate(Button button, LocalDate date, TimelineGraphControl<T> tableView,
            boolean chooseFirstDayOfWeek) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TableColumn<T, GanttBar> column = tableView.findDayColumn(date, chooseFirstDayOfWeek);

                if (column != null) {
                    scrollToDate(column, tableView);
                } else {
                    System.err.println(String.format("Date %s not found", date));
                }

            }
        });
    }

    /**
     * * scroll and select to given date
     * @param tableView
     * @param column
     */
    private void scrollToDate(TableColumn<T, GanttBar> column, TimelineGraphControl<T> tableView) {
        tableView.scrollToColumn(column);
    }

}
