package view;

import javafx.scene.canvas.Canvas;
import model.CircleNode;

/**
 * <b>Represente la vue lie a l'affichage des noeuds circulaire.</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>un noeud circulaire modifiable</li>
 * </ul> 
 * 
 * @author bgln
 */
public class ViewCircle extends ViewNode {

	/**
	 * Noeud circulaire
	 */
	private final transient CircleNode node;

	/**
	 * Constructeur
	 * 
	 * @param canvas	canevas
	 * @param node le circleNode a afficher
	 */
	public ViewCircle(final Canvas canvas, final CircleNode node) {
		super(canvas, node);
		this.node = node;
		draw();
	}

	/** Imprime un circleNode sur le canevas
	 * @see view.ViewGraph#draw()
	 */
	public final void draw() {
		super.draw();
		graphContext.fillOval(node.getCenterX() - node.getRadius(), node.getCenterY() - node.getRadius(), node.getDiameter(),
				node.getDiameter());
		graphContext.strokeOval(node.getCenterX() - node.getRadius(), node.getCenterY() - node.getRadius(), node.getDiameter(),
				node.getDiameter());
		drawNodeLabel();
	}

}
