package control;

import control.GanttBar.PieceType;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ObservableGanttBar implements ObservableValue<GanttBar> {

	private GanttBar piece;

	public ObservableGanttBar(PieceType type) {
		piece = new GanttBar(type, 0, null);
	}

	public ObservableGanttBar(PieceType type, int id, String name) {
		piece = new GanttBar(type, id, name);
	}

	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(ChangeListener<? super GanttBar> listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public GanttBar getValue() {
		return piece;
	}

	@Override
	public void removeListener(ChangeListener<? super GanttBar> listener) {
		// TODO Auto-generated method stub

	}

}
