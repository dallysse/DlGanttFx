package model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GanttResource extends Activity {

	private SimpleStringProperty role;
	private SimpleObjectProperty<ResourceType> type;

	public GanttResource() {
		super();
	}

	public GanttResource(int id, String name, LocalDate startDate, LocalDate endDate) {
		super(id, name, startDate, endDate);
	}

	public GanttResource(int id, String name, LocalDate startDate, LocalDate endDate, String role) {
		super(id, name, startDate, endDate, role);
	}

	public StringProperty roleProperty() {
		return this.role;
	}

	public String getRole() {
		return this.roleProperty().get();
	}

	public void setRole(String role) {
		this.roleProperty().set(role);
	}

	public ObjectProperty<ResourceType> typeProperty() {
		return this.type;
	}

	public ResourceType getType() {
		return this.typeProperty().get();
	}

	public void setType(ResourceType type) {
		this.typeProperty().set(type);
	}

}
