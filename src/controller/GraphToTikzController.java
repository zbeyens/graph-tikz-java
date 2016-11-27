package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import model.Characteristic;
import model.Edges;
import model.Graph;
import model.Node;

/**
 * <b>Genere le code tikz correspondant au graphe</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>graph modifiable.</li>
 * </ul>
 * 
 * @author bgln
 */
public class GraphToTikzController {
	
	/**
     * Le code tikz est genere pour ce graphe
     */
	private final transient Graph graph;

	/**
	 * Constructeur GraphToTikzController
	 * 
	 * @param graph
	 */
	public GraphToTikzController(final Graph graph) {
		super();
		this.graph = graph;
	}
	
	
	/**
	 * Ajoute les differentes couleurs (format RGB) du graphe dans une liste
	 * 
	 * @param characs liste des elements du graph
	 * @param rgb liste des couleurs
	 */
	private final void characColor(final List<Characteristic> characs,final List<int[]> rgb){
		for (int i = 0; i < characs.size(); i++) {
			final Characteristic  charac= characs.get(i);
			final Color fColor = charac.getFillColor();
			final Color sColor = charac.getStrokeColor();
			final int[] stroke_rgb = charac.getRGB(sColor);
			final int[] fill_rgb = charac.getRGB(fColor);
			if (!isTheColorInTheList(stroke_rgb, rgb)) {rgb.add(stroke_rgb);}
			if (!isTheColorInTheList(fill_rgb, rgb)) {rgb.add(fill_rgb);}
		}
	}
	
	/**
	 * 	Genere toutes les commandes Tikz de couleur pour le graphe
	 * 
	 *	@return		String de toutes les commandes
	 */
	private final String colorToSet() {
		String tikzColor = "";
		final List<Characteristic> characs = graph.getCharac();
		final List<int[]> rgb_s = new ArrayList<>();
		characColor(characs,rgb_s);
		
		for (int j = 0; j < rgb_s.size(); j++) {
			tikzColor = tikzColor + getColorInString(rgb_s.get(j));
		}
		return tikzColor;
	}

	/**
	 * Genere la commande Tikz pour une couleur au format RGB
	 *
	 * @param rgb la couleur
	 * @return commande Tikz de la couleur
	 */
	public final String getColorInString(final int[] rgb) {
		final String s_rgb = rgb[0] + "," + rgb[1] + "," + rgb[2];
		final String s2_rgb = rgb[0] + "_" + rgb[1] + "_" + rgb[2];
		
		return "\\definecolor{c" + s2_rgb + "}{RGB}{" + s_rgb + "};\n";
	}

	/**
	 * Verifie si la couleur rgb1 est la meme que la couleur rgb2
	 *
	 * @param rgb1 la premiere couleur
	 * @param rgb2 la deuxieme couleur
	 * @return true si les deux couleurs sont les meme, false sinon
	 */
	private final boolean isTheSameColor(final int[] rgb1, final int[] rgb2) {
		boolean val = true;
		for (int i = 0; i < 3; i++) {
			if (rgb1[i] != rgb2[i]) {
				val = false;
			}
		}
		return val;
	}

	/**
	 * Verifie si la couleur rgb1 est deja dans la liste de couleur
	 *
	 * @param rgb1 la couleur
	 * @param rgb2 la liste de couleur
	 * @return true si la couleur est deja dans la liste, false sinon
	 */
	private final boolean isTheColorInTheList(final int[] rgb1, final List<int[]> list) {
		boolean val = false;
		for (int i = 0; i < list.size(); i++) {
			if (isTheSameColor(rgb1, list.get(i))) {
				val = true;
			}
		}
		return val;
	}

	/**
	 * Genere toutes les commandes Tikz pour les noeuds du graphe
	 * @return Toutes les commandes Tikz pour les noeuds du graphe
	 */
	private String nodeToSet() {
		final List<Node> nodes = graph.getNodes();
		String tikzNode = "";
		for (int i = 0; i < nodes.size(); i++) {
			final Node node = nodes.get(i);
			tikzNode = tikzNode + node.getCommand(i);
		}
		return tikzNode;
	}
	
	/**
	 * Genere toutes les commandes Tikz pour les arretes et 
	 * les arcs  (Edges) du graphe
	 * 
	 * @return Toutes les commandes Tikz pour les Edges du graphe
	 *	
	 */
	private final String edgeToSet() {
		final List<Edges> edges = graph.getEdges();
		String tikzEdge = "";
		for (int i = 0; i < edges.size(); i++) {
			final Edges edge = edges.get(i);
			tikzEdge = tikzEdge + edge.getCommand();
		}
		return tikzEdge;
	}

	/**
	 * Genere le code Tikz correspondant au graphe
	 *
	 * @return Le code complet correspondant au graphe
	 */
	public final String getCommands() {
		return colorToSet() + "\n" + nodeToSet() + "\n" + edgeToSet();
	}
}

