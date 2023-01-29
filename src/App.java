
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.time.LocalDate;

import control.DatelineGraphControl;
import control.DatelineTaskGraphControl;
import control.GanttChartHbox;
import control.GanttTableControl;
import control.GanttTaskControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import model.GanttTask;

public class App extends Application {
        private BorderPane ganttChartWithMenu;
        private SplitPane ganttChart;
        private GanttTableControl<GanttTask> ganttTaskControl;
        private DatelineGraphControl<GanttTask> timelineGraphControl;
        private GanttChartHbox<GanttTask> menu;

        public App() {

                ObservableList<GanttTask> ganttTasks = addGanttTasks();
                ganttTaskControl = new GanttTaskControl();
                ganttTaskControl.setItems(ganttTasks);
                ganttTaskControl.generate();
                timelineGraphControl = new DatelineTaskGraphControl().generate(LocalDate.of(2023, 01, 01),LocalDate.of(2025, 01, 26));
                timelineGraphControl.setGanttPiece(ganttTasks);
                timelineGraphControl.scrollToColumnEvent(ganttTaskControl);

                menu = new GanttChartHbox<GanttTask>().init(timelineGraphControl.getStartDay(),
                                timelineGraphControl.getEndDay(),
                                timelineGraphControl);

                ganttChart = new SplitPane(ganttTaskControl.getTableWithPriorityLegendControl(), timelineGraphControl);
                ganttChartWithMenu = new BorderPane();
                ganttChartWithMenu.setTop(menu);
                ganttChartWithMenu.setCenter(ganttChart);               

        }
       
        private ObservableList<GanttTask> addGanttTasks() {
                ObservableList<GanttTask> ganttTasks = FXCollections
                .<GanttTask>observableArrayList();
                GanttTask ganttTask1 = new GanttTask(0, "Creation of the mock-up",
                LocalDate.of(2023, 01, 26),
                LocalDate.of(2023, 01, 30), 1, true, "design of the Interface");
                GanttTask ganttTask2 = new GanttTask(1, "Structure of the architecture",
                LocalDate.of(2023, 01, 26),
                LocalDate.of(2023, 01, 31), 1, true, "Work structuring");
                GanttTask ganttTask3 = new GanttTask(2, "Creation of the data model",
                LocalDate.of(2023, 01, 31),
                LocalDate.of(2023, 02, 03), 3, true, "UML-Diagram");
                GanttTask ganttTask4 = new GanttTask(3, "Prototype implementation",
                LocalDate.of(2023, 02, 01),
                LocalDate.of(2023, 02, 10), 3, false, "");
                GanttTask ganttTask5 = new GanttTask(4, "Review", LocalDate.of(2023, 02, 02),
                LocalDate.of(2023, 02, 05), 3, true, "test the prototyping");
                GanttTask ganttTask6 = new GanttTask(5, "Validation", LocalDate.of(2023, 02,
                06),
                LocalDate.of(2023, 02, 9), 1, true, "design of the Interface");
                GanttTask ganttTask7 = new GanttTask(6, "Overlay-Test", LocalDate.of(2023, 02, 4
                ),
                LocalDate.of(2023, 02, 8), 2, false, "Work structuring");
                GanttTask ganttTask8 = new GanttTask(7, "Dev Deployment", LocalDate.of(2023, 02,
                8),
                LocalDate.of(2023, 02, 15), 3, true, "UML-Diagram");
                GanttTask ganttTask9 = new GanttTask(8, "Client Validation", LocalDate.of(2023,
                03, 01),
                LocalDate.of(2023, 03, 11), 3, false, "");
                GanttTask ganttTask10 = new GanttTask(9, "Production Deployment",
                LocalDate.of(2023, 02, 10),
                LocalDate.of(2023, 02, 25), 2, false, "test the prototyping");
                GanttTask ganttTask11 = new GanttTask(10, "Creation of the new ticket", LocalDate.of(2023, 01, 20),
                                LocalDate.of(2023, 02, 11), 1, true, "design of the Interface");
                GanttTask ganttTask12 = new GanttTask(11, "Validation of the new ticket", LocalDate.of(2023, 01, 5),
                                LocalDate.of(2023, 01, 9), 2, false, "Work structuring");
                GanttTask ganttTask13 = new GanttTask(12, "Creation of the data model", LocalDate.of(2023, 01, 30),
                                LocalDate.of(2023, 02, 02), 3, true, "UML-Diagram");
                GanttTask ganttTask14 = new GanttTask(13, "Prototype implementation", LocalDate.of(2023, 02, 3),
                                LocalDate.of(2023, 02, 7), 3, false, "");
                GanttTask ganttTask15 = new GanttTask(14, "Overlay-Test", LocalDate.of(2023, 02, 8),
                                LocalDate.of(2023, 02, 13), 2, false, "test the prototyping");
                ganttTasks.addAll(ganttTask1, ganttTask2, ganttTask3, ganttTask4, ganttTask5, ganttTask6, ganttTask7, ganttTask8,ganttTask11, ganttTask12);

          return ganttTasks;
          }

        public BorderPane getGanttChartWithMenu() {
                return ganttChartWithMenu;
        }

        public void setGanttChartWithMenu(BorderPane ganttChartWithMenu) {
                this.ganttChartWithMenu = ganttChartWithMenu;
        }

        public SplitPane getGanttChart() {
                return ganttChart;
        }

        public void setGanttChart(SplitPane ganttChart) {
                this.ganttChart = ganttChart;
        }

	public void start(Stage primaryStage) {

		try {
			//Scene scene = new Scene(resourceChart.getResourceChartWithMenu());
			Scene scene = new Scene(getGanttChartWithMenu());
			scene.getStylesheets().add(getClass().getResource("/css/gantt.css").toExternalForm());
			primaryStage.setTitle("Ganttchart");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}