package model;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GanttTask extends Activity {

	private SimpleIntegerProperty priority;
	private BooleanProperty isCritical;

	public GanttTask() {
		super();
	}

	public GanttTask(int id, String name, LocalDate startDate, LocalDate endDate) {
		super(id, name, startDate, endDate);
	}

	public GanttTask(int id, String name, LocalDate startDate, LocalDate endDate, Integer priority, boolean isCritical,
			String info) {
		super(id, name, startDate, endDate, info);
		this.priority = new SimpleIntegerProperty(priority);
		this.isCritical = new SimpleBooleanProperty(isCritical);
	}

	public IntegerProperty priorityProperty() {
		return this.priority;
	}

	public Integer getPriority() {
		return this.priorityProperty().get();
	}

	public void setPriority(Integer priority) {
		this.priorityProperty().set(priority);
	}

	public BooleanProperty isCriticalProperty() {
		return isCritical;
	}

	public final boolean isCritical() {
		return isCriticalProperty().get();
	}

	public final void setIsCritical(boolean isCritical) {
		isCriticalProperty().set(isCritical);
	}
}