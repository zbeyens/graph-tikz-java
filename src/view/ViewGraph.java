package view;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import model.Characteristic;

/**
 * <b>Represente la vue lie au graphe.</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>un canevas modifiable</li>
 * <li>un charac ( objet interieur au canevas) modifiable</li>
 * <li>un contexte graphique modifiable</li>
 * <li>un canvas modifiable</li>
 * <li>un strokeColor modifiable</li>
 * <li>un fillColor modifiable</li>
 * <li>une epaisseur modifiable</li>
 * </ul> 
 * 
 * @author bgln
 */
public class ViewGraph {
	
	/**
	 * Canevas: fenetre affichant le graphe
	 */
	protected final transient Canvas canvas;
	
	/**
	 * Contexte graphique
	 */
	protected final transient GraphicsContext graphContext;
	
	/**
	 * Objet a afficher
	 */
	protected transient Characteristic charac;
	

	/**
	 * Couleur des bordures
	 */
	protected transient Color strokeColor;
	/**
	 * Couleur de remplissage
	 */
	protected transient Color fillColor;
	/**
	 * Epaisseur des bordures ou des arretes (et arcs)
	 */
	protected transient int thickness;

	/**Constructeur
	 * 
	 * @param canvas	Le canevas
	 * @param charac	Objet a afficher
	 */
	public ViewGraph(final Canvas canvas, final Characteristic charac) {
		this.canvas = canvas;
		this.charac = charac;
		this.graphContext = canvas.getGraphicsContext2D();
	}

	// ================================================================================

	/**
	 * Definit le format dans lequel l'objet va etre affiche
	 */
	public void draw() {
		thickness = charac.getThickness();
		strokeColor = charac.getStrokeColor();
		fillColor = charac.getFillColor();
		graphContext.setStroke(strokeColor);
		graphContext.setFill(fillColor);
		graphContext.setLineWidth(thickness);
	}

	// ================================================================================
	// Draw Label
	// ================================================================================

	/**
	 * Definit le format dans lequel le label est imprime
	 */
	protected final void drawLabel() {
		graphContext.setFill(Color.BLACK);
		graphContext.setStroke(Color.WHITE);
		graphContext.setTextAlign(TextAlignment.CENTER);
		graphContext.setTextBaseline(VPos.CENTER);
		graphContext.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 14));
	}
	
	public Characteristic getCharac() {
		return charac;
	}

	public final void setCharac(final Characteristic charac) {
		this.charac = charac;
	}
}
