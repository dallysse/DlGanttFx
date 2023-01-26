package control;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GanttBar extends Pane {

	public enum PieceType {
		BEGINNING,
		CENTER,
		END,
		COMPLET
	}

	private PieceType type;
	private int id;
	private String name;

	public GanttBar(PieceType type) {
		this(type, 0, null);
	}

	public GanttBar(PieceType type, int id) {
		this.type = type;
		this.id = id;
		switch (type) {
			case BEGINNING:
				getStyleClass().add("gantt-piece-beginning");
				getChildren().add(new Label("" + (id + 1)));
				break;
			case CENTER:
				getStyleClass().add("gantt-piece-center");
				break;
			case END:
				getStyleClass().add("gantt-piece-end");
				break;
			case COMPLET:
				getStyleClass().add("gantt-piece-complet");
				getChildren().add(new Label("" + (id + 1)));
				break;
		}
	}

	public GanttBar(PieceType type, int id, String name) {
		this.type = type;
		this.id = id;
		this.name = name;

		switch (type) {
			case BEGINNING:
				getStyleClass().add("gantt-piece-beginning");
				getChildren().add(new Label("" + (id + 1) + " " + name));
				break;
			case CENTER:
				getStyleClass().add("gantt-piece-center");
				break;
			case END:
				getStyleClass().add("gantt-piece-end");
				break;
			case COMPLET:
				getStyleClass().add("gantt-piece-complet");
				getChildren().add(new Label("" + (id + 1) + " " + name));
				break;
		}
	}

	public PieceType getType() {
		return type;
	}

	public void setType(PieceType type) {
		this.type = type;
	}

	public void setId(int id) {
		this.id = id;
	}

}
