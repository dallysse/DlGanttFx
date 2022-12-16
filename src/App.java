import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import view.TimelineView;

public class App extends Application {  

	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Timeline");
			primaryStage.setScene(new Scene(new TimelineView().init(13, true)));
			//primaryStage.setScene(new Scene(new TimelineView().generate(LocalDate.now().minusDays(1), LocalDate.now().plusDays(3))));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args); 
  }   
} 