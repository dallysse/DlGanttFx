


import javafx.application.Application;
import javafx.stage.Stage;
import view.GanttChart;
import javafx.scene.Scene;

public class App extends Application {  

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */

	public void start(Stage primaryStage) {
		GanttChart ganttChart = new GanttChart();

		try {
			Scene scene = new Scene(ganttChart.getViewGantt());
			scene.getStylesheets().add(getClass().getResource("/css/gantt.css").toExternalForm());
			ganttChart = new GanttChart();
			primaryStage.setTitle("Ganttchart");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args); 
  }   
} 