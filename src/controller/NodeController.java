package controller;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.CircleNode;
import model.Edges;
import model.Graph;
import model.Node;
import model.RectangleNode;
import model.SquareNode;
import view.View;
import view.ViewCircle;
import view.ViewRectangle;


/**
 * <b>Controle la creation et la modification des Node</b>
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

public class NodeController {

	// Node factory and node creation methods

	/**
     * zone de texte pour la premiere taille caracteristique du Node
     */
	private final transient TextField sizeText1;
	
	/**
     * zone de texte pour la deuxieme taille caracteristique du Node
     */
	private final transient TextField sizeText2;
	
	/**
     * zone de texte pour l'etiquette du Node
     */
	private final transient TextField labelText;
	
	/**
     * zone de texte pour l'epaisseur des traits
     */
	private final transient TextField thicknessText;
	
	/**
     * pallette de couleur pour choisir la couleur de remplissage
     */
	private final transient ColorPicker fillColor;
	
	/**
     * pallette de couleur pour choisir la couleur des traits
     */
	private final transient ColorPicker strokeColor;
	
	/**
     * zone de dessin du graphe
     */
	private final transient Canvas canvas;
	
	/**
     * vue 
     */
	private final transient View view;
	
	/**
     * Node en creation
     */
	private transient Node newNode;
	
	/**
     * Node en cours de modification
     */
	private transient Node editNode;
	
	/**
     * Edge en cours de modification
     */
	private transient Edges editEdge;
	
	/**
     * Quel element est en train d'etre edite : edge ou node
     */
	private transient String editing;
	
	/**
     * Graph dans lequel sont ajoute les Node
     */
	private final transient Graph graph;
	
	private final transient PanelController panelController;
	
	/**
     * Mode du controlleur : move 
     */
	private transient String mode;
	
	/**
     * zone de texte utilisee pour afficher des messages a l'utilisateur
     */
	private final transient Label print;
	
	private final transient static String MOVE = "move";
	private final transient static String NODE = "node";
	private final transient static String EDGE = "edge";
	private final transient static String CLICK = "click";

	/**
	 * Constructeur NodeController
	 * 
	 * @param graph
	 * @param view
	 * @param print
	 * @param sizeText1
	 * @param sizeText2
	 * @param canvas
	 * @param strokeColor
	 * @param fillColor
	 * @param labelText
	 * @param mode
	 * @param thicknessText
	 */
	public NodeController(final PanelController panelController, final TextField sizeText1, final TextField sizeText2, final TextField labelText, final ColorPicker fillColor,
			final ColorPicker strokeColor, final Canvas canvas, final TextField thicknessText, final View view, 
			final Graph graph, final String mode, final Label print) {

		super();
		this.panelController = panelController;
		this.sizeText1 = sizeText1;
		this.sizeText2 = sizeText2;
		this.labelText = labelText;
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		this.canvas = canvas;
		this.thicknessText = thicknessText;
		this.view = view;
		this.graph = graph;
		this.mode = mode;
		this.print = print;
	}

	/**
	 * Ajout du Node en creation au graphe
	 */
	private final void newNodeFactory() {
		view.setCanvas(canvas);
		graph.addNode(newNode);
	}

	/**
	 * Creation d'un SquareNode
	 * 
	 * @param event
	 */
	public final void createSquare(final MouseEvent event) {
		final int side = Integer.parseInt(sizeText1.getText());

		final int posX = (int) (event.getX() - (side / 2));
		final int posY = (int) (event.getY() - (side / 2));

		newNode = new SquareNode(posX, posY, side, labelText.getText(), fillColor.getValue(), strokeColor.getValue(),
				Integer.parseInt(thicknessText.getText()));
		newNodeFactory();
		view.addCharac(new ViewRectangle(canvas, (RectangleNode) newNode));
	}

	/**
	 * Creation d'un RectangleNode
	 * 
	 * @param event
	 */
	public final void createRectangle(final MouseEvent event) {
		final int posX1 = (int) (event.getX() - Integer.parseInt(sizeText1.getText()) / 2);
		final int posY1 = (int) (event.getY() - Integer.parseInt(sizeText2.getText()) / 2);
		final int posX2 = (int) (event.getX() + Integer.parseInt(sizeText1.getText()) / 2);
		final int posY2 = (int) (event.getY() + Integer.parseInt(sizeText2.getText()) / 2);
		newNode = new RectangleNode(posX1, posY1, posX2, posY2, labelText.getText(), fillColor.getValue(),
				strokeColor.getValue(), Integer.parseInt(thicknessText.getText()));
		newNodeFactory();
		view.addCharac(new ViewRectangle(canvas, (RectangleNode) newNode));
	}

	/**
	 * Creation d'un CircleNode
	 * 
	 * @param event
	 */
	public final void createCircle(final MouseEvent event) {
		final int centerX = (int) (event.getX());
		final int centerY = (int) (event.getY());
		final int radius = Integer.parseInt(sizeText1.getText()) / 2;
		newNode = new CircleNode(centerX, centerY, radius, labelText.getText(), fillColor.getValue(),
				strokeColor.getValue(), Integer.parseInt(thicknessText.getText()));
		newNodeFactory();
		view.addCharac(new ViewCircle(canvas, (CircleNode) newNode));
	}
	
	/**
	 * Met le mode a MOVE
	 * 
	 * @param event
	 */
	public final void moveNode(final MouseEvent event) {
		if (graph.findSelectedNode((int) event.getX(), (int) event.getY()) != null) {
			editNode = graph.findSelectedNode((int) event.getX(), (int) event.getY());
			mode = MOVE;
			canvas.setOnMouseClicked(this::moveNodeApply);
			print.setText("Node has been moved.");
		}
	}

	/**
	 * Applique le mouvement d'un Node
	 * 
	 * @param event
	 */
	public final void moveNodeApply(final MouseEvent event) {
		if (MOVE.equals(mode)) {
			graph.moveNode(editNode, (int) event.getX(), (int) event.getY());
		}
	}
	
	/**
	 *  selectionne un element
	 * 
	 * @param event
	 */
	public final void clickOnElement(final MouseEvent event) {
		clickOnNode(event);
		clickOnEdge(event);
	}

	/**
	 *  selectionne un Node
	 * 
	 * @param event
	 */
	public final void clickOnNode(final MouseEvent event) {
		if (graph.findSelectedNode((int) event.getX(), (int) event.getY()) != null) {
			editNode = graph.findSelectedNode((int) event.getX(), (int) event.getY());
			editing = NODE;

			print.setText("Change the label, size(s) or color(s), then Apply.");
			panelController.visible(editNode.getShape());
			panelController.displayButton(CLICK);
		}
	}

	/**
	 *  Selectionne un Edge
	 * 
	 * @param event
	 */
	public final void clickOnEdge(final MouseEvent event) {
		if (graph.findSelectedEdge((int) event.getX(), (int) event.getY()) != null) {
			editEdge = graph.findSelectedEdge((int) event.getX(), (int) event.getY());
			editing = EDGE;

			print.setText("Change the label or color, then Apply.");
			panelController.visible('e');
			panelController.displayButton("click");
		}
	}
	
	/**
	 *  Applique les modifications faites a l'element selectionne
	 * 
	 * @param event
	 */
	public final void selectApply(final ActionEvent event) {
		if (NODE.equals(editing)) {
			print.setText("Edits applied to node.");
			graph.editNode(editNode, labelText.getText(), fillColor.getValue(), strokeColor.getValue(),
					Integer.parseInt(thicknessText.getText()), Integer.parseInt(sizeText1.getText()),
					Integer.parseInt(sizeText2.getText()));
		} else if (EDGE.equals(editing)) {
			print.setText("Edits applied to edge.");
			graph.editEdge(editEdge, labelText.getText(), strokeColor.getValue(),
					Integer.parseInt(thicknessText.getText()));
		}
	}
	
	/**
	 *  supprime l'element selectionne
	 * 
	 * @param event
	 */
	public final void selectDel(final ActionEvent event) {
		if (NODE.equals(editing)) {
			print.setText("Node has been removed.");
			view.removeCharac(editNode);
			graph.removeCharac(editNode);
		} else if (EDGE.equals(editing)) {
			print.setText("Edge has been removed.");
			view.removeCharac(editEdge);
			graph.removeCharac(editEdge);
		}
	}
}
