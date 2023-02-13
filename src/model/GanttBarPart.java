package model;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GanttBarPart extends Pane {


	private PartType type;
	private int id;
	private String name;

	public GanttBarPart(PartType type) {
		this(type, 0, null);
	}

	public GanttBarPart(PartType type, int id) {
		this.type = type;
		this.id = id;
		switch (type) {
			case BEGINNING:
				getStyleClass().add("gantt-part-beginning");
				getChildren().add(new Label("" + (id + 1)));
				break;
			case CENTER:
				getStyleClass().add("gantt-part-center");
				break;
			case END:
				getStyleClass().add("gantt-part-end");
				break;
			case COMPLET:
				getStyleClass().add("gantt-complet");
				getChildren().add(new Label("" + (id + 1)));
				break;
		}
	}

	public GanttBarPart(PartType type, int id, String name) {
		this.type = type;
		this.id = id;
		this.name = name;

		switch (type) {
			case BEGINNING:
				getStyleClass().add("gantt-part-beginning");
				getChildren().add(new Label("" + (id + 1) + " " + name));
				break;
			case CENTER:
				getStyleClass().add("gantt-part-center");
				break;
			case END:
				getStyleClass().add("gantt-part-end");
				break;
			case COMPLET:
				getStyleClass().add("gantt-complet");
				getChildren().add(new Label("" + (id + 1) + " " + name));
				break;
		}
	}

	public PartType getType() {
		return type;
	}

	public void setType(PartType type) {
		this.type = type;
	}

	public void setId(int id) {
		this.id = id;
	}

}
