package tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import model.Characteristic;
import model.Edges;
import model.Graph;
import model.Node;
import model.RectangleNode;

import org.junit.Test;

public class GraphTest {

	@Test
	public void graphCreationTest() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Edges> edges = new ArrayList<Edges>();

		Node node1 = new Node(0, 0, "label", Color.BLACK, Color.BLACK, 1);
		Node node2 = new Node(0, 0, "label", Color.BLACK, Color.BLACK, 1);
		nodes.add(node1);
		nodes.add(node2);

		Edges edge1 = new Edges(node1, node2, "label", Color.BLACK, Color.BLACK, 1);
		Edges edge2 = new Edges(node1, node2, "label", Color.BLACK, Color.BLACK, 1);
		edges.add(edge1);
		edges.add(edge2);

		Graph graph = new Graph();
		graph.setNodes(nodes); graph.setEdges(edges);
		assertTrue(graph.hasNode(node1) && graph.hasNode(node2));
		assertTrue(graph.hasEdge(edge1) && graph.hasEdge(edge2));
	}
	
	@Test
	public void addNodeToGraphTest() {
		Graph graph = new Graph();
		Node node = new Node(0, 0, "label", Color.BLACK, Color.BLACK, 1);
		graph.addNode(node);
		assertTrue(graph.getNodes().contains(node));
	}
	
	// ================================================================================
	
	@Test
	public void findNodeTest(){
		ArrayList<Node> nodes = new ArrayList<Node>();
		Node node1 = new Node(0, 0, "label", Color.BLACK, Color.BLACK, 1);
		Node node2 = new Node(0, 0, "label", Color.BLACK, Color.BLACK, 1);
		node1.setId(0);
		node2.setId(1);
		nodes.add(node1);
		nodes.add(node2);

		Graph graph = new Graph();
		graph.setNodes(nodes);
		assertTrue(graph.findNode(1) == node2);
	}

	@Test
	public void findSelectedNodeTest() {
		Graph graph = new Graph();

		RectangleNode node1 = new RectangleNode(20, 20, 100, 100, "label", Color.BLACK, Color.BLACK, 1);
		RectangleNode node2 = new RectangleNode(100, 100, 190, 200, "label", Color.BLACK, Color.BLACK, 1);
		graph.addNode(node1);
		graph.addNode(node2);

		assertTrue(graph.findSelectedNode(75, 75) == node1 && graph.findSelectedNode(160, 160) == node2
				&& graph.findSelectedNode(0, 0) == null);
	}
	
	@Test
	public void findSelectedEdgeTest(){
		Graph graph = new Graph();

		RectangleNode node1 = new RectangleNode(0, 0, 50, 50, "label", Color.BLACK, Color.BLACK, 1);
		RectangleNode node2 = new RectangleNode(100, 100, 150, 150, "label", Color.BLACK, Color.BLACK, 1);
		Edges edge = new Edges(node1, node2, "label", Color.BLACK, Color.BLACK, 1);
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addEdge(edge);

		assertTrue(graph.findSelectedEdge(75, 75) == edge && graph.findSelectedEdge(0, 0) == null);
	}
	
	// ================================================================================

	@Test
	public void moveNodeTest() {
		Graph graph = new Graph();
		RectangleNode node1 = new RectangleNode(0, 0, 80, 80, "label", Color.BLACK, Color.BLACK, 1);
		graph.addNode(node1);
		graph.moveNode(node1, 200, 300);
		assertTrue(node1.getCenterX() == 200 && node1.getCenterY() == 300);
	}

	@Test
	public void editNodeTest() {
		Graph graph = new Graph();
		RectangleNode node1 = new RectangleNode(10, 10, 80, 80, "label", Color.BLACK, Color.BLACK, 1);
		graph.addNode(node1);

		graph.editNode(node1, "new Label", Color.RED, Color.BLUE, 2, 30, 50);
		assertTrue(node1.getLabel() == "new Label" && node1.getFillColor() == Color.RED
				&& node1.getStrokeColor() == Color.BLUE && node1.getWidth() == 50 && node1.getLength() == 30
				&& node1.getThickness() == 2);
	}
	
	@Test
	public void editEdgeTest(){
		Graph graph = new Graph();

		RectangleNode node1 = new RectangleNode(0, 0, 50, 50, "label", Color.BLACK, Color.BLACK, 1);
		RectangleNode node2 = new RectangleNode(100, 100, 150, 150, "label", Color.BLACK, Color.BLACK, 1);
		Edges edge = new Edges(node1, node2, "label", Color.BLACK, Color.BLACK, 1);
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addEdge(edge);

		graph.editEdge(edge, "new Label", Color.RED, 2);
		assertTrue(edge.getLabel() == "new Label" && edge.getStrokeColor() == Color.RED && edge.getThickness() == 2);
	}

	@Test
	public void removeCharacTest() {
		Graph graph = new Graph();
		RectangleNode node1 = new RectangleNode(10, 10, 80, 80, "label", Color.BLACK, Color.BLACK, 1);
		graph.addNode(node1);

		graph.removeCharac(node1);
		assertTrue(!graph.hasNode(node1));
	}
	
	@Test
	public void getCharacTest() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		Node node1 = new Node(0, 0, "label", Color.BLACK, Color.BLACK, 1);
		Node node2 = new Node(0, 0, "label", Color.BLACK, Color.BLACK, 1);
		nodes.add(node1);
		nodes.add(node2);
		Edges edge1 = new Edges(node1, node2, "label", Color.BLACK, Color.BLACK, 1);

		Graph graph = new Graph();
		graph.setNodes(nodes); graph.addEdge(edge1);
		List<Characteristic> charac = graph.getCharac();
		assertTrue(charac.contains(node1) && charac.contains(edge1));
	}

}
