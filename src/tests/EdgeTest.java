package tests;

import static org.junit.Assert.*;
import javafx.scene.paint.Color;
import model.Edges;
import model.Node;
import model.RectangleNode;

import org.junit.Test;

public class EdgeTest {

	@Test
	public void edgeCreationTest() {
		Node node1 = new Node(0, 0, "label", Color.BLACK, Color.BLACK, 1);
		Node node2 = new RectangleNode(50, 20, 20, 30, "label", Color.BLACK, Color.BLACK, 1);
		Edges edge = new Edges(node1, node2, "label", Color.BLACK, Color.BLACK, 1);

		assertTrue(edge.getParent() == node1 && edge.getChild() == node2);

	}

	@Test
	public void directedEdgeCreationTest() {
		Node node1 = new Node(0, 0, "label", Color.BLACK, Color.BLACK, 1);
		Node node2 = new Node(20, 20, "label", Color.BLACK, Color.BLACK, 1);
		Edges edge = new Edges(node1, node2, true, "label", Color.BLACK, Color.BLACK, 1);

		assertTrue(edge.getParent() == node1 && edge.getChild() == node2 && edge.isDirected() == true);

	}
	
	@Test
	public void edgeCommandTest() {
		Node node1 = new Node(0, 0, "label", Color.BLACK, Color.BLACK, 1); node1.setId(1);
		Node node2 = new Node(20, 20, "label", Color.BLACK, Color.BLACK, 1); node2.setId(2);
		Edges edge = new Edges(node1, node2, true, "label", Color.BLACK, Color.BLACK, 1);
		String command = edge.getCommand();
		System.out.println(command);
		assertTrue(command.equals("\\draw[->, line width=0.26mm,draw=c0_0_0](1) edge node{label} (2);\n"));
	}

}
