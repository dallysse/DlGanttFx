package control;

import model.GanttResource;

public class GanttResourceControl extends GanttTableControl<GanttResource> {

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
