package controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class PanelController {

	/**
	 * Zone de texte pour l'epaisseur des traits
	 */
	private transient TextField thicknessText;
	
	/**
	 * Zone de texte pour la premiere taille carateristique
	 */
	private transient TextField sizeText1;
	
	/**
	 * Zone de texte pour la deuxieme taille carateristique
	 */
	private transient TextField sizeText2;
	
	/**
	 * Zone de texte pour la troisieme taille carateristique
	 */
	private transient TextField sizeText3;
	
	/**
	 * Etiquette pour la zone de texte pour l'epaisseur des traits
	 */
	private transient Label labelThickness;
	
	/**
	 * Etiquette pour la zone de texte pour la premiere taille carateristique
	 */
	private transient Label labelSize1;
	
	/**
	 * Etiquette pour la zone de texte pour la deuxieme taille carateristique
	 */
	private transient Label labelSize2;
	
	/**
	 * Boutton pour mettre le mode à 'ajout' d'elements dans le graphe
	 */
	private transient ToggleButton addButton;
	
	/**
	 * Boutton pour mettre le mode à 'modification' d'elements dans le graphe
	 */
	private transient ToggleButton editButton;
	
	/**
	 * Boutton pour mettre le mode à 'deplacement' d'elements dans le graphe
	 */
	private transient ToggleButton moveButton;
	
	/**
	 * Boutton pour supprimer l'element selectionne
	 */
	private transient Button delButton;
	
	/**
	 * Boutton pour appliquer les modifications
	 */
	private transient Button applyButton;

	private final transient static char RECTANGLE = 'r';
	private final transient static char CERCLE = 'c';
	private final transient static char EDGE = 'e';
	private final transient static char SQUARE = 's';
	private final transient static char NONE = 'd';
	private final transient static String ADD = "add";
	private final transient static String MOVE = "move";
	private final transient static String CLICK = "click";
	private final transient static String EDIT = "edit";
	/**
	 * Constructeur
	 * @param thicknessText
	 * @param sizeText1
	 * @param sizeText2
	 * @param sizeText3
	 * @param labelThickness
	 * @param labelSize1
	 * @param labelSize2
	 * @param addButton
	 * @param editButton
	 * @param moveButton
	 * @param applyButton
	 * @param delButton
	 */
	public PanelController(TextField thicknessText, TextField sizeText1, TextField sizeText2, TextField sizeText3,
			Label labelThickness, Label labelSize1, Label labelSize2, ToggleButton addButton, ToggleButton editButton,
			ToggleButton moveButton, Button applyButton, Button delButton) {
		super();
		this.thicknessText = thicknessText;
		this.sizeText1 = sizeText1;
		this.sizeText2 = sizeText2;
		this.sizeText3 = sizeText3;
		this.labelThickness = labelThickness;
		this.labelSize1 = labelSize1;
		this.labelSize2 = labelSize2;
		this.addButton = addButton;
		this.editButton = editButton;
		this.moveButton = moveButton;
		this.applyButton = applyButton;
		this.delButton = delButton;
	}

	/**
	 * Affiche les bonnes zones de textes pour la modification d'une forme
	 * @param shape La forme a modifier
	 */
	public final void visible(final char shape) {
		int sizes;
		if (shape == RECTANGLE) {
			sizes = 2;
		} else if (shape == EDGE) {
			sizes = 0;
		} else if (shape == NONE) {
			sizes = -1;
		} else {
			sizes = 1;
		}
		textAreaVisible(sizes);
		labelVisible(shape);
	}

	/**
	 * Affiche le bon nombre de zones de textes pour la modification d'une forme
	 * @param sizes
	 */
	public void textAreaVisible(final int sizes) {
		if (sizes == -1) {  
			thicknessText.setVisible(false);
		} else {
			thicknessText.setVisible(true);
		}
		if (sizes == 0 || sizes == -1) {
			sizeText1.setVisible(false);
		}
		if (sizes == 1 || sizes == 2 || sizes == 3) {
			sizeText1.setVisible(true);
		}
		if (sizes == 0 || sizes == 10 || sizes == 1) {
			sizeText2.setVisible(false);
		}
		if (sizes == 2 || sizes == 3) {
			sizeText2.setVisible(true);
		}
		if (sizes == 0 || sizes == -1 || sizes == 1 || sizes == 2) {
			sizeText3.setVisible(false);
		}
		if (sizes == 3) {
			sizeText3.setVisible(true);
		}
	}

	/**
	 * Affiche les bons labels pour les zones de textes pour la modification d'une forme
	 * @param sizes
	 */
	public void labelVisible(final char shape) {
		if (shape == NONE) {
			labelThickness.setVisible(false);
		} else {
			labelThickness.setVisible(true);
		}
		if (shape == EDGE || shape == NONE) {
			labelSize1.setVisible(false);
		}
		if (shape != EDGE && shape != NONE) {
			labelSize1.setVisible(true);
		}
		if (shape == SQUARE) {
			labelSize1.setText("Side");
		}
		if (shape == CERCLE) {
			labelSize1.setText("Diameter");
		}
		if (shape != RECTANGLE) {
			labelSize2.setVisible(false);
		}
		if (shape == RECTANGLE) {
			labelSize2.setVisible(true);
			labelSize1.setText("Length");
			labelSize2.setText("Width");
		}
	}

	/**
	 * Affiche les bon bouttons en fonction du mode selectionne passe en parametre
	 * @param choice - Le mode selectionne
	 */
	public final void displayButton(final String choice) {
		if (ADD.equals(choice) || MOVE.equals(choice)) {
			editButton.setSelected(false);
		}
		if (EDIT.equals(choice) || MOVE.equals(choice)) {
			addButton.setSelected(false);
		} else if (ADD.equals(choice)) {
			addButton.setSelected(true);
		}
		if (ADD.equals(choice) || EDIT.equals(choice)) {
			moveButton.setSelected(false);
		}
		if (CLICK.equals(choice)) {
			applyButton.setVisible(true);
			delButton.setVisible(true);
		}
		else {
			applyButton.setVisible(false);
			delButton.setVisible(false);
		}
	}
	
}
