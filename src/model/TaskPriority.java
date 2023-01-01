package model;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class TaskPriority {
    private Label label;
    private Color color;
    
    public TaskPriority(Label label, Color color) {
        this.label = label;
        this.color = color;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    
}
