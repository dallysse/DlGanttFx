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

public class Activity {

	protected IntegerProperty id;
	protected StringProperty name;
	protected ObjectProperty<LocalDate> startDate;
	protected ObjectProperty<LocalDate> endDate;
	protected StringProperty description;
	protected DoubleProperty progress;
	protected IntegerProperty duration;
	protected ActivityState state;

	public Activity() {
	}

	public Activity(int id, String name, LocalDate startDate, LocalDate endDate) {
		check(id >= 0, "the Id must be > 0 ");
		check(!name.isEmpty(), "the name can't be empty");
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.startDate = new SimpleObjectProperty<>(startDate);
		this.endDate = new SimpleObjectProperty<>(endDate);
		this.startDate = new SimpleObjectProperty<>(startDate);
		this.endDate = new SimpleObjectProperty<>(endDate);
		setValue(startDate, endDate);

	}

	public Activity(int id, String name, LocalDate startDate, LocalDate endDate, String description) {
		check(id >= 0, "the Id must be > 0 ");
		check(!name.isEmpty(), "the name can't be empty");
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.startDate = new SimpleObjectProperty<>(startDate);
		this.endDate = new SimpleObjectProperty<>(endDate);
		setValue(startDate, endDate);
	}

	/**
	 * calculate Duration, progress and deduct state
	 * 
	 * @param startDate
	 * @param endDate
	 */
	private void setValue(LocalDate startDate, LocalDate endDate) {
		check(startDate.isBefore(endDate), "The start Date can't before end Date");
		double totalWorkingDays = Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays() + 1;
		this.duration = new SimpleIntegerProperty((int) totalWorkingDays);
		if (startDate == null || endDate == null || LocalDate.now().isBefore(startDate)) {
			this.progress = new SimpleDoubleProperty(0.0);
		} else if (LocalDate.now().isAfter(endDate)) {
			this.progress = new SimpleDoubleProperty(1.0);
		} else {
			this.progress = new SimpleDoubleProperty(
					(double) (Duration.between(startDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() + 1)
							/ totalWorkingDays);
		}
		this.state = (progress == null || progress.get() == 0.0) ? ActivityState.HALTED
				: ((progress.get() == 1.0) ? ActivityState.TERMINATED : ActivityState.RUNNING);
	}

	public IntegerProperty idProperty() {
		return this.id;
	}

	public int getId() {
		return this.idProperty().get();
	}

	public void seId(final int id) {
		this.idProperty().set(id);
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

	public StringProperty descriptionProperty() {
		return this.description;
	}

	public String description() {
		return this.descriptionProperty().get();
	}

	public void setdescription(String description) {
		this.descriptionProperty().set(description);
	}

	public DoubleProperty progressProperty() {
		return this.progress;
	}

	public Double getProgress() {
		return this.progressProperty().get();
	}

	public void setProgress(Double progress) {
		this.progressProperty().set(progress);
	}

	public ActivityState getState() {
		return state;
	}

	public void setState(ActivityState state) {
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

	public static void check(boolean bedigung, String msg) {
		if (!bedigung)
			throw new IllegalArgumentException(msg);
	}

}