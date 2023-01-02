package model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GanttResource extends GanttData {

    public enum RessourceType {
		HUMAN,
		MATERIAL
    }

    private SimpleStringProperty role;
    private SimpleObjectProperty type;
   
    public GanttResource() {
        super();
    }

    public GanttResource(String name, LocalDate startDate, LocalDate endDate) {
        super(name, startDate, endDate);
    }

    public GanttResource(String name, LocalDate startDate, LocalDate endDate, String info, String role) {
        super(name, startDate, endDate, info);
        this.role = new SimpleStringProperty(role);
    }
    
	public StringProperty roleProperty() {
		return this.role;
	}

	public String getRole() {
		return this.roleProperty().get();
	}

	public void setRole( String role) {
		this.roleProperty().set(role);
	}

	public ObjectProperty<RessourceType> typeProperty() {
		return this.type;
	}

	public RessourceType getType() {
		return this.typeProperty().get();
	}

	public void setType( RessourceType type) {
		this.typeProperty().set(type);
	}
    
}
