package view;

import javafx.scene.canvas.Canvas;
import model.Edges;

/**
 * <b>Represente la vue lie aux boucles.</b>
 * 
 * @author bgln
 */
public class ViewLoop extends ViewEdges {

	/**
	 * Constructeur.
	 * 
	 * @param canvas	Le canevas
	 * @param edge	Arrete (boucle) a imprimer
	 */
	public ViewLoop(final Canvas canvas, final Edges edge) {
		super(canvas, edge);
		draw();
	}

	/** Imprime une boucle sur le canevas.
	 * @see view.ViewGraph#draw()
	 */
	public final void draw() {
		super.draw();
		graphContext.strokeOval(parent.getCenterX(), parent.getCenterY() - 5, 80, 10);
		drawEdges();
	}
}
