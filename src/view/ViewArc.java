package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import model.Edges;
/**
 * <b>Represente la vue lie a l'affichage des arcs (arretes diriges)</b>
 * 
 * @author bgln
 */
public class ViewArc extends ViewEdges {

	/**Constructeur 
	 * 
	 * @param canvas le canevas
	 * @param edge l'arc a afficher
	 */
	public ViewArc(final Canvas canvas, final Edges edge) {
		super(canvas, edge);
		draw();
	}

	/** Afficher un arc sur la fenetre (canevas) ainsi que son label
	 * @see view.ViewGraph#draw()
	 */
	public final void draw() {
		super.draw();
		drawArrow(2, parent.getCenterX(), parent.getCenterY(), child.getCenterX(), child.getCenterY());
		drawEdgeLabel();
	}

	/**
	 * Affiche l'arc sur le canevas.
	 * 
	 * @param pos
	 * @param posX1 
	 * @param posY1
	 * @param posX2
	 * @param posY2
	 */
	public final void drawArrow(final double pos, final double posX1, final double posY1, final double posX2, 
			final double posY2) {
		super.draw();
		graphContext.strokeLine(parent.getCenterX(), parent.getCenterY(), child.getCenterX(), child.getCenterY());
		graphContext.setFill(graphContext.getStroke());
		final double arrowDx = (posX2 - posX1) / pos;
		final double arrowDy = (posY2 - posY1) / pos;
		final double angle = Math.atan2(arrowDy, arrowDx);
		final double len = Math.sqrt(arrowDx * arrowDx + arrowDy * arrowDy);

		Transform transform = Transform.translate(posX1, posY1);
		final double angleDegree = Math.toDegrees(angle);
		final Rotate rotateAngle = Transform.rotate(angleDegree, 0, 0);
		transform = transform.createConcatenation(rotateAngle);
		graphContext.setTransform(new Affine(transform));
		graphContext.fillPolygon(new double[] { len, len - 10, len - 10, len }, new double[] { 0, -10, 10, 0 }, 4);
		transform = Transform.translate(0, 0);
		graphContext.setTransform(new Affine(transform));
	}

}
