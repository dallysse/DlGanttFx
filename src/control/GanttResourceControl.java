package control;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.GanttResource;

public class GanttResourceControl extends GanttTableControl<GanttResource> {

    private String role = "Role";

    public GanttResourceControl() {
        super();
        this.name = "Resource Name";
        this.start = "Resource Start";
        this.end = "Resource End";
        this.duration = "Resource Duration";
        this.state = "Resource State";
        this.description = "Resource Role";
    }

    @Override
    public void init() {
    }

    @Override
    public void addSpecificColumns() {
    }


}
