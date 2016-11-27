package model;

import javafx.scene.paint.Color;

/**
 * <b>Represente noeud carre.</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>informations de RectangleNode.</li>
 * </ul>
 * 
 * @author bgln
 */
public class SquareNode extends RectangleNode {

	/**
	 * @param upperLeftCornerX
	 * @param upperLeftCornerY
	 * @param side
	 * @param label
	 * @param fillColor
	 * @param strokeColor
	 * @param thickness
	 */
	public SquareNode(final int upperLeftCornerX, final int upperLeftCornerY, final int side, final String label, 
			final Color fillColor, final Color strokeColor, final int thickness) {
		super(upperLeftCornerX, upperLeftCornerY, upperLeftCornerX + side, upperLeftCornerY + side, label, fillColor,
				strokeColor, thickness);
	}

	public final char getShape() {
		return 's';
	}

}
