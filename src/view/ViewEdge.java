package view;

import javafx.scene.canvas.Canvas;
import model.Edges;
/**
 * <b>Represente la vue lie a l'affichage d'une arrete (non dirige et qui n'est pas une boucle).</b>
 * @author bgln
 */
public class ViewEdge extends ViewEdges {

	/**Constructeur
	 * 
	 * @param canvas	le canevas
	 * @param edge	l'arrete a afficher
	 */
	public ViewEdge(final Canvas canvas, final Edges edge) {
		super(canvas, edge);
		draw();
	}

	/** Imptime une arrete sur le canevas
	 * @see view.ViewGraph#draw()
	 */
	public final void draw() {
		super.draw();
		graphContext.strokeLine(parent.getCenterX(), parent.getCenterY(), child.getCenterX(), child.getCenterY());
		drawEdges();
	}

}
