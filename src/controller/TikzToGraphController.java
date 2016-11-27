package controller;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import model.CircleNode;
import model.Edges;
import model.Graph;
import model.Node;
import model.RectangleNode;
import model.SquareNode;
import view.View;
import view.ViewArc;
import view.ViewCircle;
import view.ViewEdge;
import view.ViewLoop;
import view.ViewRectangle;


/**
 * <b>Gere la creation d'un graphe a partir du code Tikz</b>
 * 
 * @author bgln
 */

public class TikzToGraphController {

	/**
     * Representation du graph (modele)
     */
	private final transient Graph graph;
	
	/**
     * Liste de toutes les commandes Tikz
     */
	private transient List<String> commandsCode;
	
	/**
     * Node en train d'etre ajoutee au graph
     */
	private transient Node newNode;
	
	/**
     * Edge en train d'etre ajoutee au graph
     */
	private transient Edges edge;
	
	/**
     * Vue
     */
	private final transient View view;
	
	/**
     * Canvas ou le graphe est d
     */
	private final transient Canvas canvas;
	
	/**
     * zone de texte utilisee pour afficher des messages a l'utilisateur
     */
	private final transient Label print;
	
	/**
     * epaisseur des traits
     */
	private transient int thickness;
	
	
	/**
     * Couleur des traits
     */
	private transient Color strokeColor;
	
	/**
     * Couleur de remplissage
     */
	private transient Color fillColor;
	
	
	
	/**
     * etiquette d'un Node ou d'un Edge
     */
	private transient String label;
	
	/**
     * ID d'un Node
     */
	private transient int nodeId;

	/**
	 * Constructeur
	 * @param canvas
	 * @param graph
	 * @param view
	 * @param print
	 */
	public TikzToGraphController(final Canvas canvas, final Graph graph, final View view,  final Label print) {
		super();
		this.canvas = canvas;
		this.graph = graph;
		this.view = view;
		this.print = print;
		this.thickness = 1;
	}
	
	/**
	 * Ajoute les commandes Tikz
	 * @param tikz
	 */
	public final void setTikzToGraph(final String tikz) {
		view.clearView();
		graph.clear();
		setCommandsCode(tikz);
		interpret();
	}
	
	/**
	 * Separe les commandes du text dans une List.  Separateur : /
	 * @param text Le code complet
	 * @return La liste des commandes
	 */
	public final List<String> setCommandsCode(final String text) {
		commandsCode = new ArrayList<String>();
		for (String str: text.split("\\\\")) {
			str = str.replace(" ", "");
			commandsCode.add(str);
		}
		return commandsCode;
	}
	
	/**
	 * separe la commande dans une liste.  Le separateur est slitChar
	 * @param command
	 * @param splitChar
	 * @return La commande splitee
	 */
	private final List<String> getSplit(final String command, final String splitChar) {
		final ArrayList<String> subStr = new ArrayList<String>();
		for (final String param : command.split(splitChar)) {
			subStr.add(param);
		}
		return subStr;
	}
	
	/**
	 * Interprete les commandes de l'attribut commandsCode  et les executent
	 */
	public final void interpret() {
		try {
			for (final String command : commandsCode) {
				if (!command.isEmpty() && (command.contains("node[") || command.contains("draw["))) {
					final String paramsStr = command.substring(command.indexOf('[')+1, command.indexOf(']'));
					final List<String> params = getSplit(paramsStr, ",");
					characCommandCode(command, params);
					if (command.contains("node[")) {
						nodeCommandCode(command, params);
					}
					else if (command.contains("draw[")) {
						edgeCommandCode(command);
					}
				}
			}
		}
		catch (Exception e) {
			print.setText("Syntax error");
		}
	}

	/**
	 * recupere les caracteristique graphiques a partir de la commande et 
	 * de ses parametres
	 * @param command
	 * @param params
	 */
	private final void characCommandCode(final String command, final List<String> params) {
		thickness = getValue(params, 0.26458333, "linewidth");
		final String fillColorName = getColorName(params, "fill");
		final String strokeColorName = getColorName(params, "draw");
		strokeColor = getRGBFromName(strokeColorName, 0);
		fillColor = getRGBFromName(fillColorName, 1);
		label = getLabel(command);
	}

	/**
	 * Interprete et execute les commandes concernant les Node 
	 * @param command
	 * @param params
	 */
	private final void nodeCommandCode(final String command, final List<String> params) {
		final int posX = getPosId(command, 0);
		final int posY = getPosId(command, 1);
		
		if (command.contains("circle")) {
			createCircle(params, posX, posY, label, fillColor, strokeColor, thickness);
		}
		else if (command.contains("rectangle")) {
			createRectangle(params, posX, posY, label, fillColor, strokeColor, thickness);
		}
		newNode.setId(nodeId);
		graph.addNode(newNode);
	}
	
	
	/**
	 * Cree un CircleNode a partir des parametres de la commande et 
	 * des caracteristiques graphiques
	 * @param params
	 * @param posX
	 * @param posY
	 * @param label
	 * @param fillColor
	 * @param strokeColor
	 * @param thickness
	 */
	private final void createCircle(final List<String> params, final int posX, final int posY, final String label, final Color fillColor, final Color strokeColor, final int thickness) {
		final int diameter = getValue(params, 0.026458333, "size");
		newNode = new CircleNode(posX, posY, diameter/2, 
				label, fillColor, strokeColor, thickness);
		view.addCharac(new ViewCircle(canvas, (CircleNode) newNode));
	}
	
	/**
	 * Cree un RectangleNode a partir des parametres de la commande et des caracteristiques graphiques
	 * @param params
	 * @param posX
	 * @param posY
	 * @param label
	 * @param fillColor
	 * @param strokeColor
	 * @param thickness
	 */
	private final void createRectangle(final List<String> params, final int posX, final int posY, final String label, final Color fillColor, final Color strokeColor, final int thickness) {
		final int width = getValue(params, 0.026458333, "minimumwidth");
		final int height = getValue(params, 0.026458333, "minimumheight");
		
		if (width == height) { //square
			newNode = new SquareNode(posX-width/2, posY-height/2, width, 
					label, fillColor, strokeColor, thickness);
		}
		else {
			newNode = new RectangleNode(posX-width/2, posY-height/2, posX+width/2, posY+height/2, 
					label, fillColor, strokeColor, thickness);
		}
		
		view.addCharac(new ViewRectangle(canvas, (RectangleNode) newNode));
	}
	
	/**
	 * Interprete et execute les commandes concernant les Edge
	 * @param command
	 * @param params
	 */
	private final void edgeCommandCode(final String command) {
		final Node parent = getNode(command, 0);
		final Node child = getNode(command, 1);
		
		if (command.contains("->")) {
			edge = new Edges(parent, child, true, label, fillColor, strokeColor,thickness);
			view.addCharac(new ViewArc(canvas, edge));
		}
		else if (command.contains("loop")) {
			edge = new Edges(parent, parent, true, label, fillColor, strokeColor,thickness);
			view.addCharac(new ViewLoop(canvas, edge));
		}
		else {
			edge = new Edges(parent, child, false, label, fillColor, strokeColor,thickness);
			view.addCharac(new ViewEdge(canvas, edge));
		}
		graph.addEdge(edge);
	}
	
	// ================================================================================
	// Get from command
	// ================================================================================

	/**
	 * recupere la valeur d'un parametre, specifie par son nom parmi la liste de tous les parametre.
	 * Le parametre numerique est multiplie par le coefficient
	 * @param params
	 * @param coef
	 * @param name
	 * @return le parametre
	 */
	private final int getValue(final List<String> params, final double coef, final String name) {
		int value = 1;
		for (int i = 0; i < params.size(); i++) {
			final String param = params.get(i);
			if (param.contains(name)) {
				float valueTikz = 0;
				final int paramIndexEqual = param.indexOf('=');
				final int paramIndexM = param.indexOf('m');
				final int paramIndexC = param.indexOf('c');
				if ("linewidth".equals(name)) {
					final String paramSubString = param.substring(paramIndexEqual+1, paramIndexM);
					valueTikz = Float.parseFloat(paramSubString);
				}
				else {
					final String paramSubString = param.substring(paramIndexEqual+1, paramIndexC);
					valueTikz = Float.parseFloat(paramSubString);
				}
				value = (int) Math.ceil(valueTikz / coef);
			}
		}
		return value;
	}
	
	/**
	 * Recupere le nom de la couleur de remplissage ou des traits en fonction du nom passe en parametre.
	 * @param params
	 * @param name
	 * @return nom de la couleur
	 */
	private final String getColorName(final List<String> params, final String name) {
		String res = "noColor";
		for (int i = 0; i < params.size(); i++) {
			final String param = params.get(i);
			if (param.contains(name)) {
				final int paramIndexEqual = param.indexOf('=');
				res = param.substring(paramIndexEqual + 1);
			}
		}
		return res;
	}
	
	/**
	 * Retourne une couleur a partir du nom de la couleur
	 * @param colorName
	 * @param defaultColor
	 * @return couleur 
	 */
	private final Color getRGBFromName(final String colorName, final float defaultColor) {
		Color res = new Color(defaultColor,defaultColor,defaultColor,1);
		List<String> rgbValues = new ArrayList<String>();
		for(final String command : commandsCode) {
			if (command.contains("definecolor") && command.contains(colorName)) {
				final List<String> nameValue = getSplit(command, "}");
				String rgbStr = nameValue.get(2);
				rgbStr = rgbStr.replace("{", "");
				rgbValues = getSplit(rgbStr, ",");
				final String rgbValue1 = rgbValues.get(0);
				final String rgbValue2 = rgbValues.get(1);
				final String rgbValue3 = rgbValues.get(2);
				res = new Color(Double.parseDouble(rgbValue1)/255, Double.parseDouble(rgbValue2)/255, Double.parseDouble(rgbValue3)/255, 1);
			}
		}
		return res;
	}
	
	/**
	 * Recupere la position d'un Node sur l'axe indique par l'index. 0 -> X ; 1 -> Y
	 * @param command
	 * @param index
	 * @return la position
	 */
	private final int getPosId(final String command, final int index) {
		final List<String> paramPos = getSplit(command, "at");
		final String paramid = paramPos.get(0);
		final int paramIndexPar1 = paramid.indexOf('(');
		final int paramIndexPar2 = paramid.indexOf(')');
		final String paramSubString = paramid.substring(paramIndexPar1 + 1, paramIndexPar2);
		nodeId = Integer.parseInt(paramSubString); 
		
		final String pos = paramPos.get(1);
		final int posIndexPar1 = pos.indexOf('(');
		final int posIndexPar2 = pos.indexOf(')');
		final String posSubString = pos.substring(posIndexPar1 + 1, posIndexPar2);
		final String positions = posSubString;
		final List<String> posXY = getSplit(positions, ",");
		final String posXYIndex = posXY.get(index);
		return (int) (Double.parseDouble(posXYIndex) / 0.026458333);
	}
	
	/**
	 * Retourne le label a partir d'une commande
	 * @param command
	 * @return le label
	 */
	private final String getLabel(final String command) {
		String res = "";
		if (command.contains("{") && command.contains("}")) {
			res = command.substring(command.indexOf('{') + 1, command.indexOf('}'));
		}
		return res;
	}
	
	
	/**
	 * Retourne un Node associe a la commande d'un Edge  et a l'index.  
	 * 0 -> parent 
	 * 1 -> enfant
	 * @param command
	 * @param index
	 * @return le node
	 */
	private final Node getNode(final String command, final int index) {
		final List<String> nodes = getSplit(command, "\\(");
		final List<String> node = getSplit(nodes.get(index+1), "\\)");
		final String nodeIdStr = node.get(0);
		final int nodeId = Integer.parseInt(nodeIdStr);
		
		return graph.findNode(nodeId);
	}
}
