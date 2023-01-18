
import controls.GanttChart;
import controls.ResourceChart;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class App extends Application {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */

	public void start(Stage primaryStage) {
		GanttChart ganttChart = new GanttChart();
		ResourceChart resourceChart = new ResourceChart();

		try {
			//Scene scene = new Scene(resourceChart.getResourceChartWithMenu());
			Scene scene = new Scene(ganttChart.getGanttChartWithMenu());
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