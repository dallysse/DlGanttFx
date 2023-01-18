package view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.GanttResource;

public class GanttResourceView extends GanttTableView<GanttResource> {

    private String role = "Role";

    public GanttResourceView() {
        super();
        this.name = "Resource Name";
        this.start = " Start";
        this.end = " End";
        this.duration = "Duration";
        this.state = "State";
        this.info = "Resource Infos";
    }

    @Override
    public void init() {
    }

    @Override
    public void addSpecificColumns() {
        // Creating columns
        TableColumn<GanttResource, String> roleCol = new TableColumn<GanttResource, String>(role);
        roleCol.setCellValueFactory(new PropertyValueFactory<GanttResource, String>("role"));

        this.getColumns().addAll(roleCol);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
