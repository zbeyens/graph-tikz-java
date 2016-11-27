package model;

import javafx.scene.paint.Color;

/**
 * <b>Represente noeud circulaire.</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>informations de Node.</li>
 * <li>radius modifiable.</li>
 * </ul>
 * 
 * @author bgln
 */
public class CircleNode extends Node {


	/**
	 * Le rayon du noeud circulaire
	 */
	private int radius;

	/**
	 * @param centerX	Abscisse du centre du cercle.
	 * @param centerY	Ordonn?e du centre du cercle.
	 * @param radius
	 * @param label
	 * @param fillColor
	 * @param strokeColor
	 * @param thickness
	 */
	public CircleNode(final int centerX, final int centerY, final int radius, final String label, 
			final Color fillColor, final Color strokeColor, final int thickness) {
		super(centerX, centerY, label, fillColor, strokeColor, thickness);
		this.radius = radius;
	}

	// ================================================================================

	/* Verifie que le clic se trouve dans un CircleNode.
	 * @see model.Node#isInNode(int, int)
	 */
	
	public final boolean isInNode(final int clicX, final int clicY) {
		final double distanceToCenter = Math.sqrt(Math.pow(clicX - getCenterX(), 2) + Math.pow(clicY - getCenterY(), 2));
		return distanceToCenter <= getRadius();
	}

	public final int getDiameter() {
		return radius * 2;
	}

	public final int getRadius() {
		return radius;
	}

	public final void setRadius(final int radius) {
		this.radius = radius;
	}

	public final int getCenterX() {
		return getPosX();
	}

	public final int getCenterY() {
		return getPosY();
	}

	
	public final void setCenterXY(final int clicX, final int clicY) {
		setPosX(clicX);
		setPosY(clicY);
	}

	public final char getShape() {
		return 'c';
	}

	/* Retourne la commande tikz complete liee a la creation d'un CircleNode
	 * @see model.Node#getCommand(int)
	 */
	public final String getCommand(final int nodeId) { // NOPMD by ziyad on 5/12/16 11:40 AM
		final String shape = "\\node [circle,";
		final String paramPos = super.getCommand("minimum size=", getDiameter(), getCenterX(), getCenterY(), nodeId);
		return shape + paramPos + " {" + label + "};\n";
	}
}
