package controller;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Edges;
import model.Graph;
import model.Node;
import view.View;
import view.ViewArc;
import view.ViewEdge;
import view.ViewLoop;


/**
 * <b>Controle la creation et la modification des Edge</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>graph modifiable.</li>
 * <li>canvas non modifiable</li>
 * <li>view </li>
 * </ul>
 * 
 * @author bgln
 */
public class EdgesController {

	/**
     * Representation du graph (modele)
     */
	public transient Graph graph;
	
	/**
     * vue 
     */
	public transient final View view;
	
	/**
     * parent d'un edge en cours de creation
     */
	public transient Node parent;
	
	/**
     * enfant d'un edge en cours de creation
     */
	public transient Node child;
	
	/**
     * zone de texte utilisee pour afficher des messages a l'utilisateur
     */
	public transient final Label print;
	
	/**
     * edge en creation ou en cours de modification
     */
	public transient Edges edge;
	
	/**
     * zone de dessin du graphe
     */
	public transient final Canvas canvas;
	
	/**
     * pallette de couleur pour choisir la couleur des traits
     */
	public transient final ColorPicker strokeColor;
	
	/**
     * pallete de couleur pour choisir la couleur de remplissage
     */
	public transient final ColorPicker fillColor;
	
	/**
     * zone de texte pour l'etiquette du Edge
     */
	public transient final TextField labelText;
	
	/**
     * zone de texte pour l'epaiseur des traits
     */
	public transient final TextField thicknessText;
	
	private static transient final String ARC = "arc";
	private static transient final String LOOP = "loop";
	private static transient final String EDGE = "edge";
	
	
	/**
	 * Constructeur EdgesController
	 * 
	 * @param graph
	 * @param view
	 * @param parent
	 * @param child
	 * @param print
	 * @param canvas
	 * @param strokeColor
	 * @param fillColor
	 * @param labelText
	 * @param thicknessText
	 */
	public EdgesController(final Graph graph, final View view, final Node parent, final Node child, 
			final Label print, final Canvas canvas,	final ColorPicker strokeColor, final ColorPicker fillColor, 
			final TextField labelText, final TextField thicknessText) {
		super();
		this.graph = graph;
		this.view = view;
		this.parent = parent;
		this.child = child;
		this.print = print;
		this.canvas = canvas;
		this.strokeColor = strokeColor;
		this.fillColor = fillColor;
		this.labelText = labelText;
		this.thicknessText = thicknessText;
	}

	/**
	 * Si un Node a ete touche par l'event, ajoute le Node au comme parent au Edge en cours de creation 
	 * 
	 * @param event
	 * @param shape
	 */
	public final void newParent(final MouseEvent event, final String shape) {
		if (parent == null && graph.findSelectedNode((int) event.getX(), (int) event.getY()) != null) {
			parent = graph.findSelectedNode((int) event.getX(), (int) event.getY());
			if (ARC.equals(shape)) {
				print.setText("Click on a child Node to draw an Arc");
			}
			else if (EDGE.equals(shape)) {
				print.setText("Click on a child Node to draw an Edge");
			}
		}
	}

	/**
	 * cree une nouvelle arrete, un nouvel arc, ou une nouvelle boucle en fonction de shape
	 * 
	 * @param  shape 	"edge" pour creer une simple arret, "arc" pour creer un arc, "loop" pour creer une boucle
	 */
	public final void newEdge(final String shape) {
		edge = new Edges(parent, child, ARC.equals(shape), labelText.getText(), fillColor.getValue(), strokeColor.getValue(),
				Integer.parseInt(thicknessText.getText()));
		graph.addEdge(edge);
		if (EDGE.equals(shape)) {
			view.addCharac(new ViewEdge(canvas, edge));
		}
		else if (ARC.equals(shape)) {
			view.addCharac(new ViewArc(canvas, edge));
		}
		else if (LOOP.equals(shape)) {
			view.addCharac(new ViewLoop(canvas, edge));
		}
		parent = null;
	}

	/**
	 * Creation d'un Edge si le parent et l'enfant ont ete definis.  Definis le parent ou l'enfant sinon
	 * 
	 * @param event
	 * @param  shape	"edge" pour creer une simple arret, "arc" pour creer un arc, "loop" pour creer une boucle
	 */
	public final void newEdgeFactory(final MouseEvent event, final String shape) {
		newParent(event, shape);
		if (parent != null && child == null && graph.findSelectedNode((int) event.getX(), (int) event.getY()) != null) {
			child = graph.findSelectedNode((int) event.getX(), (int) event.getY());
			if (!(parent.getPosX() == child.getPosX() && parent.getPosY() == child.getPosY())) {
				newEdge(shape); // si parent != child
			}
			child = null;
			if (parent == null) {
				if (EDGE.equals(shape)) {
					print.setText("Click on a parent Node to draw an Edge");
				}
				else if (ARC.equals(shape)) {
					print.setText("Click on a parent Node to draw an Arc");
				}
			}
		}
	}

	/**
	 * Creation d'un arc (Edge dirige) si le parent et l'enfant ont ete definis. Definis le parent ou l'enfant sinon
	 * 
	 * @param event
	 */
	public final void createArc(final MouseEvent event) {
		newEdgeFactory(event, ARC);
	}

	/**
	 * Creation d'un simple Edge si le parent et l'enfant ont ete definis.  Definis le parent ou l'enfant sinon
	 * 
	 * @param event
	 */
	public final void createEdge(final MouseEvent event) {
		newEdgeFactory(event, EDGE);
	}

	/**
	 * Creation d'une boucle si le parent et l'enfant ont ete definis.  Definis le parent ou l'enfant sinon
	 * 
	 * @param event
	 */
	public final void createLoop(final MouseEvent event) {
		if (parent == null && graph.findSelectedNode((int) event.getX(), (int) event.getY()) != null) {
			parent = graph.findSelectedNode((int) event.getX(), (int) event.getY());
			child = parent;
			newEdge(LOOP);
			child = null;
		}
	}
	
}
