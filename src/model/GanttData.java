package model;

import java.time.Duration;
import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GanttData {

	protected IntegerProperty id;
	protected StringProperty name;
	protected ObjectProperty<LocalDate> startDate;
	protected ObjectProperty<LocalDate> endDate;
	protected StringProperty info;
	protected DoubleProperty workComplete;
	protected IntegerProperty duration;
	protected TaskState state;

	public GanttData() {
	}

	public GanttData(String name, LocalDate startDate, LocalDate endDate) {
		this.name = new SimpleStringProperty(name);
		this.startDate = new SimpleObjectProperty<>(startDate);
		this.endDate = new SimpleObjectProperty<>(endDate);
		double totalWorkingDays = Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays() + 1;
		this.duration = new SimpleIntegerProperty((int) totalWorkingDays);
		if (startDate == null || endDate == null || LocalDate.now().isBefore(startDate)) {
			this.workComplete = new SimpleDoubleProperty(0.0);
		} else if (LocalDate.now().isAfter(endDate)) {
			this.workComplete = new SimpleDoubleProperty(1.0);
		} else {
			this.workComplete = new SimpleDoubleProperty(
					(double) (Duration.between(startDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() + 1)
							/ totalWorkingDays);
		}

		this.state = (workComplete == null || workComplete.get() == 0.0) ? TaskState.HALTED
				: ((workComplete.get() == 1.0) ? TaskState.TERMINATED : TaskState.RUNNING);
	}

	public GanttData(String name, LocalDate startDate, LocalDate endDate, String info) {
		this.name = new SimpleStringProperty(name);
		this.startDate = new SimpleObjectProperty<>(startDate);
		this.endDate = new SimpleObjectProperty<>(endDate);
		double totalWorkingDays = Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays() + 1;
		this.duration = new SimpleIntegerProperty((int) totalWorkingDays);
		if (startDate == null || endDate == null || LocalDate.now().isBefore(startDate)) {
			this.workComplete = new SimpleDoubleProperty(0.0);
		} else if (LocalDate.now().isAfter(endDate)) {
			this.workComplete = new SimpleDoubleProperty(1.0);
		} else {
			this.workComplete = new SimpleDoubleProperty(
					(double) (Duration.between(startDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() + 1)
							/ totalWorkingDays);
		}
		this.state = (workComplete == null || workComplete.get() == 0.0) ? TaskState.HALTED
				: ((workComplete.get() == 1.0) ? TaskState.TERMINATED : TaskState.RUNNING);
		this.info = new SimpleStringProperty(info);
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

	public void setStartDate(LocalDate startDate) {
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

	public StringProperty infoProperty() {
		return this.info;
	}

	public String info() {
		return this.infoProperty().get();
	}

	public void setInfo(String info) {
		this.infoProperty().set(info);
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

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}

	public IntegerProperty durationProperty() {
		return this.duration;
	}

	public Integer getDuration() {
		return this.durationProperty().get();
	}

	public void setdurationProperty(Integer duration) {
		this.durationProperty().set(duration);
	}

}