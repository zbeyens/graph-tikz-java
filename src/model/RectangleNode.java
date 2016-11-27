package model;

import javafx.scene.paint.Color;

/**
 * <b>Represente noeud rectangulaire.</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>informations de Node.</li>
 * <li>L'abscisse du coin inferieur droit modifiable.</li>
 * <li>L'ordonnee du coin inferieur droit modifiable</li>
 * </ul>
 * 
 * @author bgln
 */
public class RectangleNode extends Node {

	/**
	 * Abscisse du coin inferieur droit du RectangleNode
	 */
	private int posX2;
	/**
	 * Ordonnee du coin inferieur droit du RectangleNode
	 */
	private int posY2;

	/**
	 * @param upperLeftCornerX
	 * @param upperLeftCornerY
	 * @param lowerRightCornerX
	 * @param lowerRightCornerY
	 * @param label
	 * @param color
	 * @param strokeColor
	 * @param thickness
	 */
	public RectangleNode(final int upperLeftCornerX, final int upperLeftCornerY, final int lowerRightCornerX, 
			final int lowerRightCornerY, final String label, final Color color, final Color strokeColor, 
			final int thickness) {
		super(upperLeftCornerX, upperLeftCornerY, label, color, strokeColor, thickness);
		this.posX2 = lowerRightCornerX;
		this.posY2 = lowerRightCornerY;
	}

	/* Verifie que le clic se trouve bien dans un RectangleNode
	 * @see model.Node#isInNode(int, int)
	 */
	public final boolean isInNode(final int clicX, final int clicY) {
		return clicX >= getPosX() && clicX <= posX2 && clicY >= getPosY() && clicY <= posY2;
	}

	// ================================================================================
	// Accessors
	// ================================================================================

	public final int getPosX2() {
		return posX2;
	}

	public final void setPosX2(final int posX2) {
		this.posX2 = posX2;
	}

	public final int getPosY2() {
		return posY2;
	}

	public final void setPosY2(final int posY2) {
		this.posY2 = posY2;
	}

	public final int getWidth() {
		return Math.abs(posY2 - getPosY());
	}

	public final void setWidth(final int width) {
		final int centerY = getCenterY();
		posY2 = centerY + (width / 2);
		posY = centerY - (width / 2);
	}

	public final int getLength() {
		return Math.abs(posX2 - getPosX());
	}

	/**
	 * Modifie La longueur et en fonction de cela posX et posX2
	 * @param length	Longueur de RectangleNode
	 */
	public final void setLength(final int length) {
		final int centerX = getCenterX();
		posX = centerX - (length / 2);
		posX2 = centerX + (length / 2);
	}

	/* Retourne l'abscisse du centre de RectangleNode
	 * @see model.Node#getCenterX()
	 */
	@Override
	public final int getCenterX() {
		final int posX1 = getPosX();
		return (posX1 + (posX2 - posX1) / 2);
	}

	/* Retourne l'ordonnee de RectangleNode
	 * @see model.Node#getCenterY()
	 */
	@Override
	public final int getCenterY() {
		final int posY1 = getPosY();
		return (posY1 + (posY2 - posY1) / 2);
	}

	/* Modifie le centre de RectangleNode
	 * @see model.Node#setCenterXY(int, int)
	 */
	public final void setCenterXY(final int clicX, final int clicY) {
		final int length = getLength() / 2;
		final int width = getWidth() / 2;
		setPosX(clicX - length);
		setPosY(clicY - width);
		setPosX2(clicX + length);
		setPosY2(clicY + width);
	}

	/* 	Retourne la commande tikz complete liee a la creation d'un RectangleNode
	 * @see model.Node#getCommand(int)
	 */
	public final String getCommand(final int nodeId) {
		final String shape = "\\node [rectangle,";
		final String paramPos = super.getCommand("minimum width=", getLength(), getCenterX(), getCenterY(), nodeId);
		final String length = "minimum height=" + Math.round(getWidth() * 0.026458333 * 100) / 100.0 + "cm";

		return shape + length + "," + paramPos + " {" + label + "};\n";
	}

	public char getShape() {
		return 'r';
	}
}
