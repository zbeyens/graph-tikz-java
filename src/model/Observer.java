package model;


/**
 * A chaque modification du graphe, l'observer est update
 * et la vue en est informee
 * 
 * @author bgln
 *
 */
public interface Observer {
	public void update();
}
