package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Duration;

/**
 * <b>Gere la sauvegarde des fichiers et projets</b>
 * 
 * @author bgln
 */
public class ProjectController {

	/**
	 * Liste des caracteres non admis pour le nommage des fichiers 
	 */
	private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
	/**
	 * Route vers le fichier selectionne en ce moment
	 */
	protected transient TreeItem<File> selectedFile;
	/**
	 * Nom du fichier selectionne en ce moment
	 */
	protected transient String selectedFileName;
	
	/**
	 * Controlleur pour construire un graph a partir de code TiKZ
	 */
	private TikzToGraphController tikzToGraphController;
	/**
	 * Controlleur pour construire du code TiKZ a partir d'un graphe 
	 */
	private GraphToTikzController graphToTikzController;
	/**
	 * Arborescence complete du systeme de fichiers
	 */
	private TreeView<File> filesTree;
	/**
	 * Contenu de l'ecran d'affichage (en bas de l'interface)
	 */
	private Label print;
	/**
	 * Dernière date de sauvegarde du fichier
	 */
	private Label lastSavedDate;
	/**
	 * Nom du nouveau fichier ou dossier
	 */
	private TextField itemName;
	/**
	 * Etat de l'horloge en ce moment
	 */
	private Label clock;
	/**
	 * Zone d'écriture de code TiKZ
	 */
	private TextArea tikz;
	
	/**
	 * 
	 * Constructeur ProjectController
	 * 
	 * @param tikz
	 * @param tikzToGraphController
	 * @param graphToTikzController
	 * @param filesTree
	 * @param print
	 * @param lastSavedDate
	 * @param itemName
	 * @param clock
	 */
	public ProjectController(TextArea tikz, TikzToGraphController tikzToGraphController, GraphToTikzController graphToTikzController,
			TreeView<File> filesTree, Label print, Label lastSavedDate, TextField itemName, Label clock) {
		this.tikz = tikz;
		this.tikzToGraphController = tikzToGraphController;
		this.graphToTikzController = graphToTikzController;
		this.filesTree = filesTree;
		this.print = print;
		this.lastSavedDate = lastSavedDate;
		this.itemName = itemName;
		this.selectedFileName = "";
		this.clock = clock;
	}
	
	/**
	 * Crée un nouveau projet si le nom entré est correct
	 * 
	 * @param event
	 */
	public final void selectProject(final ActionEvent event) {
		if (isValidFile(itemName.getText(), new File("save"))) {
			File dir = new File("save/" + itemName.getText());
			dir.mkdir();
			fillTreeView(new File("save"), null);
			print.setText("New Project created");
		}
		else {
			print.setText("Invalid name. Please use a new name for the project.");
		}
	}
	
	/**
	 * Verifie si un nom de fichier est valide
	 * 
	 * @param fileName nom de fichier a verifier
	 * @param dir repertoire dans lequel le fichier devrait etre cree
	 * @return vrai si le nom de fichier est correct, faux sinon
	 */
	public final boolean isValidFile(String fileName, File dir) {
		boolean res = true;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (fileName.equals(file.getName()) || fileName.isEmpty()) {
				res = false;
			}
			for (int i = 0; i < ILLEGAL_CHARACTERS.length; i++) {
				if (fileName.indexOf(ILLEGAL_CHARACTERS[i]) >= 0) {
					res = false;
				}
			}
		}
		return res;
	}
	
	/**
	 * Cree un nouveau fichier graphique si le nom entre est correct
	 * 
	 * @param event
	 */
	public final void selectGraph(final ActionEvent event) {
		if (isValidFile(itemName.getText(), new File("save/" + selectedFileName))
				&& isValidDirectory(selectedFileName, "save")) {
			writeFile(true, "save/" + selectedFileName + "/" + itemName.getText());
		}
		else {
			print.setText("Invalid name. Please use a new name for the graph.");
		}
	}

	/**
	 * Modifie un fichier (date + code TiKZ)si celui-ci n'est pas nouveau
	 * 
	 * @param isNew si le fichier est nouveau ou pas
	 * @param fileName nom du fichier
	 */
	private final void writeFile(boolean isNew, String fileName) {
		String tikzText = tikz.getText();
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
			
			fillTreeView(new File("save"), null);
			print.setText("New Graph created");
			if (isNew == false) {
				print.setText("Graph loaded");
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				String lastSavedDate = dateFormat.format(date);
				writer.write(lastSavedDate + "\n" + tikzText);
			}
		} catch (IOException ex) {
		  System.out.println("writeFile Exception");
		} finally {
		   try {writer.close();} catch (Exception ex) {/*ignore*/}
		}
	}
	
	/**
	 * Verifie si un nom de repertoire est valide
	 * 
	 * @param fileName nom du repertoire
	 * @param saveDir repertoire dans lequel on veut le sauver
	 * @return vrai si le nom est valide, faux sinon
	 */
	public final boolean isValidDirectory(String fileName, String saveDir) {
		boolean res = false;
		try {
			File saveDirectory = new File(saveDir);
			File[] files = saveDirectory.listFiles();
			for (File file : files) {
				if (fileName.equals(file.getName())) {
					res = true;
				}
			} 
		} catch (Exception e) {
			System.out.println("Directory Exception");
		}
		return res;
	}
	
	/**
	 * Gere l'horloge, met a jour l'arborescence, et ajoute un listener
	 * 
	 * @param dir fichier ou repertoire
	 * @param parent TreeItem parent
	 */
	public final void init(File dir, TreeItem<File> parent) {
		playClock();
		fillTreeView(dir, parent);
		addTreeListener();
		itemName.setStyle("-fx-text-inner-color: gray;");
	}
	
	/**
	 * Gere l'affichage de l'horloge
	 */
	private void playClock() {
		final DateFormat format = new SimpleDateFormat("HH:mm:ss"); 
		final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {  
		     @Override  
		     public void handle(ActionEvent event) {  
		          final Calendar cal = Calendar.getInstance();  
		          clock.setText(format.format(cal.getTime()));  
		     }
		}));  
		timeline.setCycleCount(Animation.INDEFINITE);  
		timeline.play();  
	}
	
	/**
	 * Modifie l'arborescence des fichiers
	 * 
	 * @param dir fichier ou repertoire
	 * @param parent TreeItem parent
	 */
	private void fillTreeView(File dir, TreeItem<File> parent) {
		TreeItem<File> root = getRoot(dir, parent);
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					fillTreeView(file, root);
				} else {
					root.getChildren().add(new TreeItem<>(new File(file.getName())));
				}
			}
			if (parent == null) {
				filesTree.setRoot(root);
			} else {
				parent.getChildren().add(root);
			}
		} catch (Exception e) {
			System.out.println("fillTreeView Exception");
		}
	}
	
	/**
	 * Trouve la racine de l'arborescence du fichier ou repertoire
	 * 
	 * @param dir fichier ou repertoire
	 * @param parent TreeItem parent
	 * @return TreeItem correspondant au fichier dir
	 */
	private TreeItem<File> getRoot(File dir, TreeItem<File> parent) {
		String dirName= "";
		if (parent == null) {
			dirName = "Projects";
		} else {
			dirName = dir.getName();
		}
		TreeItem<File> root = new TreeItem<>(new File(dirName));
		root.setExpanded(true);
		return root;
	}
	
	/**
	 * Ajoute un nouveau listener sur l'arborescence
	 */
	private void addTreeListener() {
		filesTree.setEditable(true);
		MultipleSelectionModel<TreeItem<File>> selectedFilesTree = filesTree.getSelectionModel();
		ReadOnlyObjectProperty<TreeItem<File>> selectedItem = selectedFilesTree.selectedItemProperty();
		selectedItem.addListener((observable, oldValue, newValue) -> updateSelectedItem(newValue));
	}
	
	/**
	 * Met a jour le contenu d'un fichier
	 * 
	 * @param newValue adresse du fichier
	 */
	private void updateSelectedItem(TreeItem<File> newValue) {
		try {
			if (selectedFile != null && selectedFile.getValue().toString() != "Projects") {
				tikz.setText(graphToTikzController.getCommands());
				if (selectedFile != null && !selectedFile.getValue().isDirectory()) {
					writeFile(false, "save/" + selectedFile.getParent().getValue().toString() + "/" + selectedFileName);
				}
				lastSavedDate.setText("");
			}
			selectedFile = newValue;
			selectedFileName = newValue.getValue().toString();
			System.out.println(newValue.getValue().toString());
			tikz.setText(getFileTikz(newValue));
			tikzToGraphController.setTikzToGraph(getFileTikz(newValue));
			
		} catch (Exception e) {
			System.out.println("Update Error");
		}
	}
	
	/**
	 * Lit un fichier et recupere son code tikz
	 * 
	 * @param newValue adresse du fichier
	 * @return code tikz contenu dans le fichier
	 */
	private String getFileTikz(TreeItem<File> newValue) {
		String fileTikz = "";
		File pathToFile = new File("save/" + newValue.getParent().getValue().toString() + "/" + selectedFileName);
		if (newValue.getParent().getValue().toString() != "Projects") {
	        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
	        	String firstLine = br.readLine();
	        	if (firstLine != null) {
		        	lastSavedDate.setText("Last saved date : " + firstLine);
	        	}
	        	else {
	        		lastSavedDate.setText("");
	        	}
	            for (String line; (line = br.readLine()) != null;) {
	               fileTikz += line + "\n";
	            }
	        } catch (IOException e) {
				System.out.println("getFile Exception");
			}
		}
		return fileTikz;
	}
	
}
