package model;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.PartType;

public class ObservableGanttBarPart implements ObservableValue<GanttBarPart> {

	private GanttBarPart piece;

	public ObservableGanttBarPart(PartType type) {
		piece = new GanttBarPart(type, 0, null);
	}

	public ObservableGanttBarPart(PartType type, int id, String name) {
		piece = new GanttBarPart(type, id, name);
	}

	public ObservableGanttBarPart(PartType type, int id) {
		piece = new GanttBarPart(type, id);
	}

	@Override
	public void addListener(InvalidationListener listener) {
	}

	@Override
	public void removeListener(InvalidationListener listener) {
	}

	@Override
	public void addListener(ChangeListener<? super GanttBarPart> listener) {
	}

	@Override
	public GanttBarPart getValue() {
		return piece;
	}

	@Override
	public void removeListener(ChangeListener<? super GanttBarPart> listener) {
	}

}
