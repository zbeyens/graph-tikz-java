
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import controller.EdgesController;
import controller.GraphToTikzController;
import controller.NodeController;
import controller.PanelController;
import controller.ProjectController;
import controller.TikzToGraphController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Graph;
import model.Node;
import view.View;

/**
 * <b>Controlleur principal de l'application</b>
 *
 * @author bgln
 */
public class MyController extends Application implements Initializable {
	
	/**
	 * Mode ADD
	 */
	private final transient static String MODEADD = "add";
	
	/**
	 * Mode EDIT
	 */
	private final transient static String MODEEDIT = "edit";
	
	/**
     * Vue de l'application
     */
	protected static transient View view;
	
	/**
     * Representation du graph (modele)
     */
	protected transient Graph graph;
	
	/**
     * Nouveau Node ajoute au graph 
     */
	protected transient Node newNode;
	
	/**
     * Node parent lors de l'ajout d'un Edge 
     */
	protected transient Node parent;
	
	/**
     * Node enfant lors de l'ajout d'un Edge 
     */
	protected transient Node child;
	
	/**
     * Node utilise pour editer un Node du graphe
     */
	protected transient Node editNode;
	
	/**
     * Sauvegarde du mode de l'application : add (ajoute un element) / move (bouger un element) / edit (modifier un element) 
     */
	protected transient String mode;

	/**
     * Controlleur en charge de la traduction du code Tikz en un graphe editable a la souris
     */
	private transient TikzToGraphController tikzToGraphController;
	
	/**
     * Controlleur en charge de la generation du code Tikz a partir du graph
     */
	private transient GraphToTikzController graphToTikzController;
	
	/**
     * Controlleur en charge de l'ajout de nouveau Nodes et leur modification
     */
	private transient NodeController nodeController;
	
	/**
     * Controlleur en charge de l'ajout de nouveau Edges et leur modification
     */
	private transient EdgesController edgesController;
	
	/**
     * Controlleur en charge de l'affichage des bonnes options pour l'edition d'elements graphiques
     */
	private transient PanelController panelController;
	/**
	 * Controlleur en charge de la sauvegarde des projets et fichiers
	 */
	private transient ProjectController projectController;

	@FXML
	protected transient Label lastSavedDate, clock;
	
	/**
     * zone de texte utilisee pour afficher des messages a l'utilisateur
     */
	@FXML
	protected transient Label print;
	
	/**
     * etiquette de la zone de texte utilisee pour modifier l'epaisseur des traits
     */
	@FXML
	protected transient Label labelThickness;
	
	/**
     * etiquette de la zone de texte utilisee pour modifier la premiere taille characteristique d'une forme
     */
	@FXML
	protected transient Label labelSize1;
	
	/**
     * etiquette de la zone de texte utilisee pour modifier la seconde taille characteristique d'une forme
     */
	@FXML
	protected transient Label labelSize2;
	
	/**
     * etiquette de la zone de texte utilisee pour modifier la troisieme taille characteristique d'une forme
     */
	@FXML
	protected transient Label labelSize3;

	/**
     * zone de texte utilisee pour ajouter une etiquette a un Node ou un Edge
     */
	@FXML
	protected transient TextField labelText;
	
	/**
     * zone de texte utilisee pour modifier l'epaisseur des traits
     */
	@FXML
	protected transient TextField thicknessText;
	
	/**
     * zone de texte utilisee pour modifier la premiere taille characteristique d'une forme
     */
	@FXML
	protected transient TextField sizeText1;
	
	/**
     * zone de texte utilisee pour modifier la seconde taille characteristique d'une forme
     */
	@FXML
	protected transient TextField sizeText2;
	
	/**
     * zone de texte utilisee pour modifier la troisieme taille characteristique d'une forme
     */
	@FXML
	protected transient TextField sizeText3; 
	
	/**
     * boutton utilise pour changer le mode en "ajouter element"
     */
	@FXML
	protected transient ToggleButton addButton;
	
	/**
     * boutton utilise pour changer le mode en "editer element"
     */
	@FXML
	protected transient ToggleButton editButton;
	
	/**
     * boutton utilise pour changer le mode en "bouger element"
     */
	@FXML
	protected transient ToggleButton moveButton;

	/**
     * boutton utilise pour appliquer les modifications faites a un Node ou un Edge
     */
	@FXML
	protected transient Button applyButton;
	
	/**
     * boutton utilise pour supprimer un Node ou un Edge
     */
	@FXML
	protected transient Button delButton;

	/**
     * zone de texte pour l'affichage du code Tikz
     */
	@FXML
	protected transient TextArea tikz;

	/**
     * utilise pour choisir la couleur de remplissage des Node
     */
	@FXML
	protected transient ColorPicker fillColor;
	

	/**
     * utilise pour choisir la couleur de traits
     */
	@FXML
	protected transient ColorPicker strokeColor;

	/**
     * zone de dessin du graphe
     */
	@FXML
	protected transient Canvas canvas;

	@FXML
	protected transient TreeView<File> filesTree;
	
	@FXML
	protected transient TextField itemName;

	// ================================================================================
	// Main/Start/Constructor
	// ================================================================================

	/**
	 * Point de depart de l'application
	 */
	public static void main(final String[] args) {
		view = new View();
		launch(args);
	}

	@Override
	public final void start(final Stage arg) throws Exception {
		view.start(arg);
		
	}

	
	/**
	 * Constructeur MyController.
	 */
	public MyController() {
		super();
		this.graph = new Graph();
		view.addGraph(graph);
		graph.addObserver(view);
		mode = MODEADD;
	}

	// ================================================================================
	// Canvas - Add
	// ================================================================================

	/**
	 * Change le mode en "ajout d'element"
	 * 
	 * @param event
	 */
	@FXML
	void selectAdd(final ActionEvent event) {
		mode = MODEADD;

		print.setText("Choose a Node or an Edge to create");
		panelController.visible('\0');
		panelController.displayButton(MODEADD);
	}

	// ================================================================================
	// TiKZ
	// ================================================================================

	/**
	 * genere un graphe a partir d'un code Tikz
	 * 
	 * @param event
	 */
	@FXML
	public final void selectTikzToGraph(final ActionEvent event) {
		print.setText("Graph generated");
		tikzToGraphController.setTikzToGraph(tikz.getText());
	}

	/**
	 * genere le code Tikz correspondant a un graphe
	 * 
	 * @param event
	 */
	@FXML
	final void selectGraphToTikz(final ActionEvent event) {
		tikz.setText(graphToTikzController.getCommands());
	}

	// ================================================================================
	// Canvas - Edit/Apply
	// ================================================================================
	
	/**
	 *  Change le mode en "edition d'elements"
	 * 
	 * @param event
	 */
	@FXML
	void selectEdit(final ActionEvent event) {
		mode = MODEEDIT;
		canvas.setOnMouseClicked(nodeController::clickOnElement);

		print.setText("Click on a node or an edge (center point) to edit.");
		panelController.visible('d');
		panelController.displayButton(MODEEDIT);
	}

	@FXML
	final void selectApply(final ActionEvent event) {
		nodeController.selectApply(event);
	}

	// ================================================================================
	// Canvas - Delete
	// ================================================================================
	/**
	 *  supprime l'element selectionne
	 * 
	 * @param event
	 */
	@FXML
	void selectDel(final ActionEvent event) {
		nodeController.selectDel(event);
	}

	// ================================================================================
	// Canvas - Move
	// ================================================================================

	/**
	 *  Change le mode en "bouger element"
	 * 
	 * @param event
	 */
	@FXML
	final void selectMove(final ActionEvent event) {
		mode = MODEEDIT;
		canvas.setOnMouseClicked(nodeController::moveNode);

		print.setText("Choose a Node to move.");
		panelController.visible('d');
		panelController.displayButton("move");
	}

	// ================================================================================
	// Nodes - Square/Rectangle/Circle
	// ================================================================================

	/**
	 *  selection de la forme a ajouter : carre
	 * 
	 * @param event
	 */
	@FXML
	void selectSquare(final ActionEvent event) {
		selectAdd(event);
		canvas.setOnMouseClicked(nodeController::createSquare);

		print.setText("Click on the canvas to draw a Square Node");
		panelController.visible('s');
	}

	/**
	 *  selection de la forme a ajouter : rectangle
	 * 
	 * @param event
	 */
	@FXML
	void selectRectangle(final ActionEvent event) {
		selectAdd(event);
		canvas.setOnMouseClicked(nodeController::createRectangle);

		print.setText("Click on the canvas to draw a Rectangle Node");
		panelController.visible('r');
	}

	/**
	 *  selection de la forme a ajouter : cercle
	 * 
	 * @param event
	 */
	@FXML
	final void selectCircle(final ActionEvent event) {
		selectAdd(event);
		canvas.setOnMouseClicked(nodeController::createCircle);

		print.setText("Click on the canvas to draw a Circle Node");
		panelController.visible('c');
	}

	// ================================================================================
	// Edges - Arc/Edge/Loop
	// ================================================================================

	/**
	 *  selection du type d'arrete : arc
	 * 
	 * @param event
	 */
	@FXML
	void selectArc(final ActionEvent event) {
		selectAdd(event);
		canvas.setOnMouseClicked(edgesController::createArc);

		panelController.visible('e');
		if (parent == null) {
			print.setText("Click on a parent Node to draw an Arc");
		} else {
			print.setText("Click on a child Node to draw an Arc");
		}
	}

	/**
	 *  selection du type d'arrete : simple arrete
	 * 
	 * @param event
	 */
	@FXML
	void selectEdge(final ActionEvent event) {
		selectAdd(event);
		canvas.setOnMouseClicked(edgesController::createEdge);

		if (parent == null) {
			print.setText("Click on a parent Node to draw an Edge");
		} else {
			print.setText("Click on a child Node to draw an Edge");
		}
		panelController.visible('e');
	}

	/**
	 *  selection du type d'arrete : boucle
	 * 
	 * @param event
	 */
	@FXML
	void selectLoop(final ActionEvent event) {
		selectAdd(event);
		parent = null;
		child = null;
		canvas.setOnMouseClicked(edgesController::createLoop);

		print.setText("Click on a Node to draw a Loop");
		panelController.visible('e');
	}

	// ================================================================================
	// Project Management
	// ================================================================================

	@FXML
	void selectProject(final ActionEvent event) {
		projectController.selectProject(event);
	}
	
	@FXML
	void selectGraph(final ActionEvent event) {
		projectController.selectGraph(event);
	}
	
	/**
	 *  initialise la vue et les attributs
	 * 
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		view.setCanvas(canvas);

		this.edgesController = new EdgesController(graph, view, parent, child, print, canvas, strokeColor,
				fillColor, labelText, thicknessText);

		this.nodeController = new NodeController(panelController, sizeText1, sizeText2, labelText, fillColor, strokeColor, canvas,
				thicknessText, view, graph, mode, print);

				
		this.tikzToGraphController = new TikzToGraphController(canvas, graph, view, print);

		this.graphToTikzController = new GraphToTikzController(graph);
		this.panelController = new PanelController(thicknessText, sizeText1, sizeText2, sizeText3, labelThickness,
				labelSize1, labelSize2, addButton, editButton, moveButton, applyButton, delButton);
		this.nodeController = new NodeController(panelController, sizeText1, sizeText2, labelText, fillColor, strokeColor, canvas,
				thicknessText, view, graph, mode, print);
		this.projectController = new ProjectController(tikz, tikzToGraphController, graphToTikzController, filesTree, print,
				lastSavedDate, itemName, clock);
		strokeColor.setValue(Color.BLACK);
		panelController.visible('d');
		panelController.displayButton(MODEADD);
		
		projectController.init(new File("save"), null);
	}
	
}
