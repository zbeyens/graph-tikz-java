package view;

import javafx.scene.canvas.Canvas;
import model.Node;
/**
 * <b>Represente la vue lie au noeuds.</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>un noeud modifiable</li>
 * </ul> 
 * @author bgln
 */
public class ViewNode extends ViewGraph {

	/**
	 * Noeud.
	 */
	private final transient Node node;

	/**Constructeur
	 * @param canvas	Le canevas
	 * @param node	le noeud a afficher
	 */
	public ViewNode(final Canvas canvas, final Node node) {
		super(canvas, node);
		this.node = node;
	}

	/**
	 * Imprime le label du node sur le canevas a partir du centre  de celui-ci.
	 */
	protected final void drawNodeLabel() {
		drawLabel();
		graphContext.strokeText(node.getLabel(), node.getCenterX(), node.getCenterY());
		graphContext.fillText(node.getLabel(), node.getCenterX(), node.getCenterY());
	}

}
