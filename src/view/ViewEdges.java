package view;

import javafx.scene.canvas.Canvas;
import model.Edges;
import model.Node;

/**
 * <b>Represente la vue lie a l'affichage des arretes en generale (cela peut etre une boucle, un arc aussi).</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>une arrete modifiable</li>
 * <li>un noeud parent modifiable</li>
 * <li>un noeud enfant modifiable</li>
 * </ul> 
 * @author bgln
 */
public class ViewEdges extends ViewGraph {

	/**
	 * Noeud parent
	 */
	protected transient Node parent;
	
	/**
	 * Noeud enfant
	 */
	protected transient Node child;
	
	/**
	 * Arrete liant ces deux noeuds
	 */
	protected transient Edges edge;

	/**
	 * Constructeur
	 * 
	 * @param canvas	le canevas
	 * @param edge	L'arrete a afficher
	 */
	public ViewEdges(final Canvas canvas, final Edges edge) {
		super(canvas, edge);
		this.edge = edge;
		this.parent = edge.getParent();
		this.child = edge.getChild();
	}

	/**
	 * Place le label au milieu du edge
	 */
	protected final void drawEdgeLabel() {
		drawLabel();
		int posX;
		final Node edgeParent = edge.getParent();
		final Node edgeChild = edge.getChild();
		if (edgeChild.equals(edgeParent)) {
			posX = 2*edgeParent.getCenterX() + 80;
		}
		else {
			posX = edgeChild.getCenterX() + edgeParent.getCenterX();
		}
		graphContext.strokeText(edge.getLabel(),  posX / 2,
				(edgeChild.getCenterY() + edgeParent.getCenterY()) / 2);
		graphContext.fillText(edge.getLabel(), posX / 2,
				(edgeChild.getCenterY() + edgeParent.getCenterY()) / 2);
	}
	
	/**
	 * Imprime une arrete sur le canevas
	 */
	public final void drawEdges() {
		graphContext.setFill(graphContext.getStroke());
		graphContext.fillOval(edge.getCenterX() - 5, edge.getCenterY() - 5, 10, 10);
		drawEdgeLabel();
	}

}
