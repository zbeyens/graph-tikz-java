package model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

/**
 * <b>Represente le graphe et tous les objets qu'il contient.</b>
 * <p>
 * Une instance de cette classe est caracterisee par les informations suivantes :
 * <ul>
 * <li>des noeuds modifiables.</li>
 * <li>des arretes modifiables.</li>
 * <li>une liste d'observeurs modifiables</li>
 * </ul>
 * 
 * @author bgln
 */
public class Graph {

	/**
	 * Forme rectangle
	 */
	private final transient static char RECTANGLE = 'r';
	
	/**
	 * Forme cercle
	 */
	private final transient static char CIRCLE = 'c';
	
	/**
	 * Forme carre
	 */
	private final transient static char SQUARE = 's';
	
	/**
	 * Liste des noeuds dans le graphe
	 */
	private List<Node> nodes;
	
	/**
	 * Liste des observeurs
	 */
	private final transient List<Observer> observers;
	
	/**
	 * Liste des arretes
	 */
	private List<Edges> edges;

	/**
	 * Constructeur
	 */
	public Graph() {
		this.nodes = new ArrayList<Node>();
		this.observers = new ArrayList<Observer>();
		this.edges = new ArrayList<Edges>();
	}

	/**
	 * Retourne un noeud en fonction de son ID.
	 * @param nodeId	l'identificateur du Node
	 * @return	un Node
	 */
	public final Node findNode(final int nodeId) {
		Node res = null;
		for (int i = 0; i < nodes.size(); i++) {
			final Node curNode = nodes.get(i);
			if (curNode.getId() == nodeId) {
				res = curNode;
			}
		}
		return res;
	}
	
	/**
	 * Retourne un Node sur lequel on a clique.
	 * @param clicX	Abscisse du clic
	 * @param clicY	Ordonee du clic
	 * @return un Node sur lequel on a clique
	 */
	public final Node findSelectedNode(final int clicX, final int clicY) {
		Node res = null;
		for (int i = nodes.size() - 1; i >= 0; i--) {
			final Node curNode = nodes.get(i);
			if (curNode.isInNode(clicX, clicY)) {
				res = curNode;
			}
		}
		return res;
	}

	/**
	 * Retourne un Edge sur lequel on a clique
	 * @param clicX	Abscisse du clic.
	 * @param clicY	Ordonnee du clic.
	 * @return	l'Edge sur lequel on a clique
	 */
	public final Edges findSelectedEdge(final int clicX, final int clicY) {
		Edges res = null;
		for (int i = edges.size() - 1; i >= 0; i--) {
			final Edges curEdges = edges.get(i);
			if (curEdges.isInEdge(clicX, clicY)) {
				res = curEdges;
			}
		}
		return res;
	}

	/**
	 * Notifie tous les observers
	 */
	private final void notifyObserver() {
		for (final Observer o : observers) {
			o.update();
		}
	}

	// ================================================================================
	// Add/Remove/Move
	// ================================================================================

	/**Ajoute un noeud au graphe et notifie les observeurs
	 * @param nodeToAdd	Noeud a ajouter
	 */
	public final void addNode(final Node nodeToAdd) {
		nodes.add(nodeToAdd);
		notifyObserver();
	}

	/**Ajoute un Edge au graphe et notifie les observeurs
	 * @param edge
	 */
	public void addEdge(final Edges edge) {
		edges.add(edge);
		notifyObserver();
	}

	/**Modifie l'emplacement d'un noeud.
	 * @param nodeToMove	Noeud a deplacer
	 * @param posX Abscisse du centre du noeud
	 * @param posY Ordonnee du centre du noeud
	 */
	public final void moveNode(final Node nodeToMove, final int posX, final int posY) {
		for (int i = nodes.size() - 1; i >= 0; i--) {
			final Node curNode = nodes.get(i);
			if (curNode.equals(nodeToMove)) {
				curNode.setCenterXY(posX, posY);
				notifyObserver();
			}
		}
	}

	/**Modifie intrinsequement un Node
	 * @param nodeToEdit
	 * @param label
	 * @param fillColor
	 * @param strokeColor
	 * @param thickness
	 * @param size1	premiere longueur caracteristique
	 * @param size2 deuxieme longueur caracteristique
	 */
	public final void editNode(final Node nodeToEdit, final String label, final Color fillColor, final Color strokeColor, 
			final int thickness, final int size1, final int size2) {
		for (int i = nodes.size() - 1; i >= 0; i--) {
			final Node curNode = nodes.get(i);
			if (curNode.equals(nodeToEdit)) {
				curNode.setLabel(label);
				curNode.setFillColor(fillColor);
				curNode.setStrokeColor(strokeColor);
				curNode.setThickness(thickness);
				if (curNode.getShape() == RECTANGLE) {
					final RectangleNode node = (RectangleNode) curNode;
					node.setWidth(size2);
					node.setLength(size1);
				} else if (curNode.getShape() == SQUARE) {
					final SquareNode node = (SquareNode) curNode;
					node.setWidth(size1);
					node.setLength(size1);
				} else if (curNode.getShape() == CIRCLE) {
					final CircleNode node = (CircleNode) curNode;
					node.setRadius(size1 / 2);
				}
				notifyObserver();
			}
		}
	}

	/**Modifie intrinsequement un Node
	 * @param edgeToEdit Edge a editer
	 * @param label 
	 * @param strokeColor
	 * @param thickness
	 */
	public final void editEdge(final Edges edgeToEdit, final String label, final Color strokeColor, final int thickness) {
		for (int i = edges.size() - 1; i >= 0; i--) {
			final Edges curEdges = edges.get(i);
			if (curEdges.equals(edgeToEdit)) {
				curEdges.setLabel(label);
				curEdges.setStrokeColor(strokeColor);
				curEdges.setThickness(thickness);
				notifyObserver();
			}
		}
	}

	/**Supprime un noeud ou un edge choisie
	 * @param charac objet du graphe
	 */
	public final void removeCharac(final Characteristic charac) {
		for (int i = nodes.size() - 1; i >= 0; i--) {
			if (nodes.get(i) == charac) {
				nodes.remove(i);
				notifyObserver();
			}
		}
		for (int i = edges.size() - 1; i >= 0; i--) {
			if (edges.get(i) == charac) {
				edges.remove(i);
				notifyObserver();
			}
		}
	}
	
	/**
	 * Efface tous dans le graphe
	 */
	public final void clear() {
		nodes.clear();
		edges.clear();
		notifyObserver();
	}

	/**Rajoute un observeur
	 * @param observer
	 */
	public final void addObserver(final Observer observer) {
		observers.add(observer);
	}

	// ================================================================================
	// Accessors
	// ================================================================================

	public final boolean hasNode(final Node node) {
		return nodes.contains(node);
	}
	
	public final List<Node> getNodes() {
		return nodes;
	}
	
	public final void setNodes(final List<Node> nodes) {
		this.nodes = nodes;
	}

	public final boolean hasEdge(final Edges edge) {
		return edges.contains(edge);
	}

	public final List<Edges> getEdges() {
		return edges;
	}
	
	public final void setEdges(final List<Edges> edges) {
		this.edges = edges;
	}

	/**Retourne tous ce qui se trouve dans le graphe.
	 * @return liste d'objets 'Characteristic' se trouvant dans le graphe.
	 */
	public final List<Characteristic> getCharac() {
		final List<Characteristic> charac = new ArrayList<Characteristic>();
		charac.addAll(nodes);
		charac.addAll(edges);
		return charac;
	}
	
}
