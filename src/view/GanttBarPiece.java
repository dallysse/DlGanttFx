package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GanttBarPiece extends Pane {
	
	public enum PieceType {
		BEGINNING,
		CENTER,
		END,
		COMPLET
	}
	
	private PieceType type;
	private String name;

	public GanttBarPiece(PieceType type) {
		this(type, null);
	}

	public GanttBarPiece(PieceType type, String name) {
		this.type = type;
		this.name = name;
		
		switch(type) {
		case BEGINNING:
			getStyleClass().add("gantt-piece-beginning");
			getChildren().add(new Label(name));
			break;
		case CENTER:
			getStyleClass().add("gantt-piece-center");
			break;
		case END:
			getStyleClass().add("gantt-piece-end");
			break;
		case COMPLET:
			getStyleClass().add("gantt-piece-complet");
			getChildren().add(new Label(name));
			break;
		}
	}

	public PieceType getType() {
		return type;
	}

	public void setType(PieceType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

