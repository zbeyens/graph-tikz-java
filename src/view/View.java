package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import model.Characteristic;
import model.Graph;
import model.Observer;

/**
 * <b>Represente la vue du programme, c'est-a-dire les fenetres, canevas, etc.</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>Fenetre modifiable</li>
 * <li>Scene modifiable</li>
 * <li>un canvas modifiable</li>
 * <li>un graph modifiable</li>
 * <li>une liste de views modifiables</li>
 * </ul> 
 * 
 * @author bgln
 */
public class View extends Application implements EventHandler<ActionEvent>, Observer {

	/**
	 * fenetre du programme
	 */
	private transient Stage window;
	
	/**
	 * Scene: ou les differents objets comme le canevas se trouve
	 */
	private transient Scene scene;
	
	/**
	 * Canevas: fenetre ou se trouve les nodes et edges
	 */
	
	private transient Canvas canvas;
	
	/**
	 * Graphe contenant les node et edges
	 */
	private transient Graph graph;
	
	/**
	 * La partie visible des differents objets.
	 */
	final private transient List<ViewGraph> views;

	/**
	 * Constructeur
	 */
	public View() {
		super();
		views = new ArrayList<ViewGraph>();
	}

	/** Lancement de l'affichage.
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	public final void start(final Stage mainStage) throws IOException {
		window = mainStage;
		try {
			final Class<? extends View> curClass = getClass();
			final URL resFXML = curClass.getResource("/View.fxml");
			final Parent root = FXMLLoader.load(resFXML);
			scene = new Scene(root);
			window.setScene(scene);
			window.setTitle("Diagram editor");
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ================================================================================
	// Update
	// ================================================================================

	/**
	 * Rafraichi le canevas
	 * @see model.Observer#update()
	 */
	public final void update() {
		clear();
		drawGraph();
	}

	/**
	 * Vide le canevas
	 */
	private final void clear() {
		final GraphicsContext graphContext = canvas.getGraphicsContext2D();
		graphContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	/**
	 * Imprime le graphe sur le canevas.
	 */
	private final void drawGraph() {
		for (final ViewGraph view : views) {
			view.draw();
		}
	}

	// ================================================================================
	// Add
	// ================================================================================

	public final void addGraph(final Graph graph) {
		this.graph = graph;
	}

	public final void addCharac(final ViewGraph view) {
		views.add(view);
	}

	/**
	 * Supprime les objets present sur le canevas.
	 * 
	 * @param charac objet a supprimer
	 */
	public final void removeCharac(final Characteristic charac) {
		for (int i = 0; i < views.size(); i++) {
			final ViewGraph view = views.get(i);
			if (view.getCharac() == charac) {
				views.remove(view);
			}
		}
	}
	
	public final void clearView() {
		views.clear();
	}

	// ================================================================================
	// Accessors
	// ================================================================================

	public final void setCanvas(final Canvas canvas) {
		this.canvas = canvas;
	}

	// ================================================================================

	public final void handle(final ActionEvent event) {
		// TODO Auto-generated method stub
	}
}
