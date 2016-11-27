package model;

import javafx.scene.paint.Color;

/**
 * <b>Represente une arrete.</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>informations de Characteristic.</li>
 * <li>un noeud parent modifiable.</li>
 * <li>un noeud enfant modifiable.</li>
 * <li>le fait de savoir si c'est un arc ou une arrete (si c'est dirige ou pas)</li>
 * </ul>
 * 
 * @author bgln
 */
public class Edges extends Characteristic {

	/**
	 * Noeud parent
	 */
	private Node parent;
	/**
	 * Noeud enfant
	 */
	private Node child;
	/**
	 * vrai si l'arrete est dirige (arc), faux si il ne l'est pas
	 */
	private boolean directed;

	/**
	 * @param parent
	 * @param child
	 * @param label
	 * @param fillColor
	 * @param strokeColor
	 * @param thickness
	 */
	public Edges(final Node parent, final Node child, final String label, final Color fillColor, 
			final Color strokeColor, final int thickness) {
		super(label, fillColor, strokeColor, thickness);
		directed = false;
		this.parent = parent;
		this.child = child;
		this.label = label;
	}

	// ================================================================================

	/**
	 * Verifie si le clic se trouve bien sur le cercle au milieu de l'Edge
	 * @param clicX	Abscisse du clic
	 * @param clicY	Ordonee du clic
	 * @return Vrai si le clic se trouve sur le cercle au milieu de l'Edge, faux sinon
	 */
	public final boolean isInEdge(final int clicX, final int clicY) {
		final double rad = Math.sqrt(Math.pow(clicX - getCenterX(), 2) + Math.pow(clicY - getCenterY(), 2));
		return rad <= 5;
	}

	/**
	 * @param parent
	 * @param child
	 * @param directed
	 * @param label
	 * @param fillColor
	 * @param strokeColor
	 * @param thickness
	 */
	public Edges(final Node parent, final Node child, final boolean directed, final String label, 
			final Color fillColor, final Color strokeColor, final int thickness) {
		super(label, fillColor, strokeColor, thickness);
		this.directed = directed;
		this.parent = parent;
		this.child = child;
		this.label = label;
	}

	// ================================================================================
	// Accessors
	// ================================================================================

	public final Node getParent() {
		return parent;
	}

	public final void setParent(final Node parent) {
		this.parent = parent;
	}

	public final Node getChild() {
		return child;
	}

	public final void setChild(final Node child) {
		this.child = child;
	}

	public final boolean isDirected() {
		return directed;
	}

	public final void setDirected(final boolean directed) {
		this.directed = directed;
	}

	/**
	 * Retourne l'abscisse du centre de l'Edge entre le noeud parent et enfant
	 * @return l'abscisse du entre de l'edge
	 */
	public final int getCenterX() {
		int res = (parent.getCenterX() + child.getCenterX()) / 2;
		if (this.parent == this.child) {
			res = parent.getCenterX() + 40;
		}
		return res;
	}

	/**
	 * Retourne l'ordonee du centre de l'Edge entre le noeud parent et enfant
	 * @return l'ordonee du entre de l'edge
	 */
	public final int getCenterY() {
		int res = (parent.getCenterY() + child.getCenterY()) / 2;
		if (this.parent == this.child) {
			res = parent.getCenterY() + 5;
		}
		return res;
	}
	/* Retourne la commande tikz complete liee a la creation d'un edge.
	 * @see model.Characteristic#getCommand()
	 */
	public final String getCommand(){
		final int id_parent = parent.getId();
		final int id_child = child.getId();
		final String thicknessStroke = super.getCommand();
		String direction = "--";
		if (isDirected()) {
			direction = "->";
		}
		if (id_parent == id_child) {
			direction = "loop right";
		}
		final String edgeParameters = direction + ", " + thicknessStroke;
		return "\\draw["+edgeParameters+"](" + id_parent + ") edge node{"+label+"} ("+id_child+");\n";
	}

}
