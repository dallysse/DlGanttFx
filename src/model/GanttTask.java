package model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GanttTask {

	
/* 	private String id;
	private int priority;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double workComplete;
	private String info;
	private boolean 
	; */
	public enum TaskState {
		RUNNING,
		HALTED,
		TERMINATED
	}

	private IntegerProperty id;
	private StringProperty name;
	private ObjectProperty<LocalDate> startDate;
	private ObjectProperty<LocalDate> endDate;
	private SimpleIntegerProperty priority;
	private StringProperty info;
	private DoubleProperty workComplete;
	private BooleanProperty isCritical;
	private IntegerProperty taskDuration;
	private TaskState state;
	private ObservableList<GanttTask> ganttTasks = FXCollections
	.observableArrayList();

	public GanttTask() {}
	
	public GanttTask(String name, LocalDate startDate, LocalDate endDate) {
		this.name = new SimpleStringProperty(name);
		this.startDate = new SimpleObjectProperty<>(startDate);
		this.endDate = new SimpleObjectProperty<>(endDate);
	}
		
	public GanttTask(String name, LocalDate startDate, LocalDate endDate, Integer priority, boolean isCritical, String info) {
		this.name = new SimpleStringProperty(name);
		this.startDate = new SimpleObjectProperty<>(startDate);
		this.endDate = new SimpleObjectProperty<>(endDate);
		double totalWorkingDays = Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays() + 1;
		this.taskDuration = new SimpleIntegerProperty((int) totalWorkingDays);
		this.priority = new SimpleIntegerProperty(priority);
		this.isCritical = new SimpleBooleanProperty(isCritical);
		this.workComplete = new SimpleDoubleProperty(( startDate == null || endDate == null ) ? 0.0 :
		(LocalDate.now().isBefore(startDate) ? 0.0 :
		(double) (Duration.between( startDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() + 1) / totalWorkingDays) );
		this.state= (workComplete == null || workComplete.get() == 0.0) ? TaskState.HALTED : ((workComplete.get() == 1.0) ? TaskState.TERMINATED : TaskState.RUNNING) ;
		this.info=new SimpleStringProperty(info);
	}
	
	public StringProperty nameProperty() {
		return this.name;
	}

	public String getName() {
		return this.nameProperty().get();
	}

	public void setName(final String name) {
		this.nameProperty().set(name);
	}

	public ObjectProperty<LocalDate> startDateProperty() {
		return this.startDate;
	}

	public LocalDate getStartDate() {
		return this.startDateProperty().get();
	}

	public void setStartDate( LocalDate startDate) {
		this.startDateProperty().set(startDate);
	}


	public ObjectProperty<LocalDate> endDateProperty() {
		return this.endDate;
	}

	public LocalDate getEndDate() {
		return this.endDateProperty().get();
	}

	public void setEndDate(final LocalDate endDate) {
		this.endDateProperty().set(endDate);
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

	public StringProperty infoProperty() {
		return this.info;
	}

	public String info() {
		return this.nameProperty().get();
	}

	public void setInfo(String info) {
		this.infoProperty().set(info);
	}
    public BooleanProperty isCriticalProperty() {
        return isCritical ;
    }

    public final boolean isCritical() {
        return isCriticalProperty().get();
    }

    public final void setIsCritical(boolean isCritical) {
        isCriticalProperty().set(isCritical);
    }

	public DoubleProperty workCompleteProperty() {
		return this.workComplete;
	}

	public Double getWorkComplete() {
		return this.workCompleteProperty().get();
	}

	public void setWorkComplete(Double workComplete) {
		this.workCompleteProperty().set(workComplete);
	}

	public ObservableList<GanttTask> ganttTaskProperty() {
		return ganttTasks;
	  }

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}

	public IntegerProperty taskDurationProperty() {
		return this.taskDuration;
	}

	public Integer gettaskDuration() {
		return this.taskDurationProperty().get();
	}

	public void settaskDurationProperty(Integer taskDuration) {
		this.taskDurationProperty().set(taskDuration);
	}

/* 	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getWorkComplete() {
		return workComplete;
	}

	public void setWorkComplete(Double workComplete) {
		this.workComplete = workComplete;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean getIsCritical() {
		return isCritical;
	}

	public void setCritical(boolean isCritical) {
		this.isCritical = isCritical;
	}

	public boolean isDueToday() {
		if(endDate.compareTo(LocalDate.now()) == 0)
			return true;
		return false;
	}
	
	public boolean isOverdue() {
		if(endDate.compareTo(LocalDate.now()) < 0)
			return true;
		return false;
	}
	
	public String getUid() {
		return id;
	}
	
	public int getLevel() {
		return priority;
	}

	public void setLevel(int level) {
		this.priority = level;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	} */
	
}