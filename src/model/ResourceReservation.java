package model;

import javafx.beans.property.ObjectProperty;

public class ResourceReservation {

    private ObjectProperty<GanttTask> ganttTask;
    private ObjectProperty<GanttResource> ganttResource;

    public ResourceReservation() {
    }

    public ResourceReservation(ObjectProperty<GanttTask> ganttTask, ObjectProperty<GanttResource> ganttResource) {
        this.ganttTask = ganttTask;
        this.ganttResource = ganttResource;
    }

    public ObjectProperty<GanttTask> getGanttTask() {
        return ganttTask;
    }

    public void setGanttTask(ObjectProperty<GanttTask> ganttTask) {
        this.ganttTask = ganttTask;
    }

    public ObjectProperty<GanttResource> getGanttResource() {
        return ganttResource;
    }

    public void setGanttResource(ObjectProperty<GanttResource> ganttResource) {
        this.ganttResource = ganttResource;
    }

}
