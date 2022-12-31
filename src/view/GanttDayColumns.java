package view;
import java.time.DayOfWeek;
import java.time.LocalDate;

import model.GanttTask;
import view.GanttBarPiece.PieceType;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;

public class GanttDayColumns extends TableColumn<GanttTask,GanttBarPiece> {
	
	private LocalDate representedDate;
	

	
	public GanttDayColumns(LocalDate representedDate) {
		super();
		this.representedDate = representedDate;
		init();
	}
	
	public GanttDayColumns(int year, int monthValue, DayOfWeek dayOfWeek) {
    }

    private void init() {
		setCellValueFactory(this::createPiece);
		setResizable(false);
		

	}
	
	private ObservableValue<GanttBarPiece> createPiece(CellDataFeatures<GanttTask,GanttBarPiece> cdf) {
		LocalDate taskStartDate = cdf.getValue().getStartDate();
		LocalDate taskEndDate = cdf.getValue().getEndDate();
		
		if(representedDate.isBefore(taskStartDate) || representedDate.isAfter(taskEndDate))
			return null;
		
		if(representedDate.isEqual(taskStartDate))
			return new ObservableGanttBarPiece(PieceType.BEGINNING); 
			
		if(representedDate.isAfter(taskStartDate) && representedDate.isBefore(taskEndDate))
			return new ObservableGanttBarPiece(PieceType.CENTER); 
		
		if(representedDate.isEqual(taskEndDate))
			return new ObservableGanttBarPiece(PieceType.END); 
		
		return null;
	}
	
}