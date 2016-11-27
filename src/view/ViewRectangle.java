package view;

import javafx.scene.canvas.Canvas;
import model.RectangleNode;
/**
 * <b>Represente la vue lie au noeuds rectangulaires.</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>un noeud rectangulaire modifiable</li>
 * </ul> 
 * 
 * @author bgln
 */
public class ViewRectangle extends ViewNode {

	/**
	 * Noeud rectangulaire
	 */
	private final transient RectangleNode node;

	/**Constructeur
	 * @param canvas	Le canevas
	 * @param node	noeud rectangulaire a afficher
	 */
	public ViewRectangle(final Canvas canvas, final RectangleNode node) {
		super(canvas, node);
		this.node = node;
		draw();
	}

	/* Imprime le rectangleNode sur le canevas
	 * @see view.ViewGraph#draw()
	 */
	public final void draw() {
		super.draw();
		graphContext.fillRect(node.getPosX(), node.getPosY(), node.getLength(), node.getWidth());
		graphContext.strokeRect(node.getPosX(), node.getPosY(), node.getLength(), node.getWidth());
		drawNodeLabel();
	}

}
