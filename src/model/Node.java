package model;

import javafx.scene.paint.Color;

/**
 * <b>Represente un Node du graphe : un cercle, un rectangle ou un carre.</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>informations de Characteristic.</li>
 * <li>posX modifiable.</li>
 * <li>posY modifiable.</li>
 * <li>id modifiable.</li>
 * </ul>
 * 
 * @author bgln
 */
public class Node extends Characteristic {

	/**
     * Position X du Node dans le canvas.
     */
	protected int posX;
	
	/**
     * Position Y du Node dans le canvas.
     */
	protected int posY;
	
	/**
     * ID du Node dans le canvas. Permet de creer des Edges dans le code tikz.
     */
	protected transient int nodeId;

	
	/**
	 * Constructeur Node.
	 * 
	 * @param posX
	 * @param posY
	 * @param label
	 * @param fillColor
	 * @param strokeColor
	 * @param thickness
	 */
	public Node(final int posX, final int posY, final String label, final Color fillColor, final Color strokeColor, final int thickness) {
		super(label, fillColor, strokeColor, thickness);

		this.posX = posX;
		this.posY = posY;
	}

	// ================================================================================

	/**
	 * Methode utilisee par les classes filles de Node.
	 * 
	 * @param x position X d'un clic.
	 * @param y position Y d'un clic.
	 * @return	
	 */
	public boolean isInNode(final int clickX, final int clickY) {
		return false;
	}

	/**
	 * Methode utilisee par les classes filles de Node.
	 * 
	 * @param x position X d'un clic.
	 * @param y position Y d'un clic.
	 * @return	
	 */
	public void setCenterXY(final int clickX, final int clickY) {
	}
	
	/**
	 * Methode utilisee par les classes filles de Node.
	 * 
	 * @param	nodeId	
	 * @return	
	 */
	public String getCommand(final int nodeId) {
		return "";
	}
	
	/**
	 * Retourne la partie de commande tikz du thickness, du strokeColor, du fillColor, du premier parametre modifiant 
	 * la taille, de la position du centre d'un Node, a partir de l'id de ce Node.
	 * 
	 * @param param Nom du type de Node.
	 * @param size	Premier parametre de taille du Node.
	 * @param posX
	 * @param posY	
	 * @param nodeId
	 * @return	Partie de commande tikz concernant ce Node.
	 */
	public String getCommand(final String param, final float size, final float posX, final float posY, final int nodeId) {
		this.nodeId = nodeId;
		final String thicknessStroke = super.getCommand();
		final String fill = "fill=" + getNameOfColor(getRGB(getFillColor()));
		final String sizeNode = param + Math.round(size * 0.026458333 * 100) / 100.0 + "cm";
		final String posCenter = "(" + Math.round(posX * 0.026458333 * 100) / 100.0 + ","
				+ Math.round(posY * 0.026458333 * 100) / 100.0 + ")";
		return thicknessStroke + "," + fill + "," + sizeNode + "] (" + nodeId + ") at" + posCenter;
	}
	
	// ================================================================================
	// Accessors
	// ================================================================================

	public final int getPosX() {
		return posX;
	}

	public final void setPosX(final int posX) {
		this.posX = posX;
	}

	public final int getPosY() {
		return posY;
	}

	public final void setPosY(final int posY) {
		this.posY = posY;
	}

	public int getCenterX() {
		return posX;
	}

	public int getCenterY() {
		return posY;
	}

	public char getShape() {
		return '\0';
	}

	public final int getId() {
		return nodeId;
	}

	public final void setId(final int nodeId) {
		this.nodeId = nodeId;
	}
}
