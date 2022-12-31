package view;


import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import view.GanttBarPiece.PieceType;

public class ObservableGanttBarPiece implements ObservableValue<GanttBarPiece> {

	private GanttBarPiece piece;
	
	public ObservableGanttBarPiece(PieceType type) {
		piece = new GanttBarPiece(type);
	}

	public ObservableGanttBarPiece(PieceType type, String name) {
		piece = new GanttBarPiece(type, name);
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
	public void addListener(ChangeListener<? super GanttBarPiece> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GanttBarPiece getValue() {
		return piece;
	}

	@Override
	public void removeListener(ChangeListener<? super GanttBarPiece> listener) {
		// TODO Auto-generated method stub
		
	}

}
