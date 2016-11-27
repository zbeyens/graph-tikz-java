package model;

import javafx.scene.paint.Color;

/**
 * <b>Represente soit un Edges, soit un Node.</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>label modifiable.</li>
 * <li>strokeColor modifiable.</li>
 * <li>fillColor modifiable.</li>
 * <li>thickness modifiable.</li>
 * </ul>
 * 
 * @see Edges
 * @see Node
 * 
 * @author bgln
 */
public class Characteristic {

	/**
     * L'etiquette de la Characteristic.
     */
	protected String label;
	
	/**
     * La couleur de bordure de la Characteristic.
     */
	protected Color strokeColor;
	
	/**
     * La couleur de remplissage de la Characteristic.
     */
	protected Color fillColor;

	/**
     * L'epaisseur de la Characteristic.
     */
	protected int thickness;

	/**
	 * Constructeur Characteristic.
	 * 
	 * @param label
	 * @param fillColor
	 * @param strokeColor
	 * @param thickness
	 */
	public Characteristic(final String label, final Color fillColor, final Color strokeColor, final int thickness) {
		super();
		this.label = label;
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		this.thickness = thickness;
	}
	
	/**
	 * Retourne un tableau des 3 nuances entieres (R,G,B) a partir d'une couleur.
	 * 
	 * @param	col	Color
	 * @return		Tableau contenant les 3 nuances entieres (R,G,B) de la Color specifiee.
	 */
	public final int[] getRGB(final Color col) {
		final int red = (int) (col.getRed() * 255);
		final int green = (int) (col.getGreen() * 255);
		final int blue = (int) (col.getBlue() * 255);
		final int[] rgb = { red, green, blue };
		return rgb;
	}
	
	/**
	 * Retourne un nom de couleur genere a partir d'un tableau de 3 entiers (R,G,B).
	 * Ce nom est associe a une Color et est utilise dans une commande tikz.
	 * 
	 * @param	rgb	Tableau contenant les 3 nuances entieres (R,G,B) d'une Color.
	 * @return		Nom d'une couleur.
	 */
	public final String getNameOfColor(final int[] rgb) {
		return "c" + Integer.toString(rgb[0]) + "_" + Integer.toString(rgb[1]) + "_" + Integer.toString(rgb[2]);
	}
	
	
	/**
	 * Retourne la partie de commande tikz du thickness et du strokeColor d'un Node a partir de son ID.
	 * 
	 * @return	Commande tikz : thickness et un parametre modifiant la taille.
	 */
	public String getCommand() {
		return "line width=" + String.valueOf(Math.round(getThickness() * 0.26458333 * 100) / 100.0) + "mm"
		+ ",draw=" + getNameOfColor(getRGB(getStrokeColor()));
	}

	// ================================================================================
	// Accessors
	// ================================================================================

	public final String getLabel() {
		return label;
	}

	public final void setLabel(final String label) {
		this.label = label;
	}

	public final int getThickness() {
		return thickness;
	}

	public final void setThickness(final int thickness) {
		this.thickness = thickness;
	}

	public final Color getStrokeColor() {
		return strokeColor;
	}

	public final Color getFillColor() {
		return fillColor;
	}

	public final void setFillColor(final Color color) {
		this.fillColor = color;
	}

	public final void setStrokeColor(final Color strokeColor) {
		this.strokeColor = strokeColor;
	}
}
