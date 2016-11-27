package tests;

import static org.junit.Assert.*;
import javafx.scene.paint.Color;
import model.Edges;
import model.Graph;
import model.Node;
import controller.GraphToTikzController;
import org.junit.Test;

public class GraphToTikzControllerTest {
	
	@Test
	public void getCommandsTest(){
		Graph graph = new Graph();
		GraphToTikzController control=new GraphToTikzController(graph);
		Node node1 = new Node(0, 3, "label", Color.BLACK, Color.BLACK, 1);
		node1.setId(1);
		Node node2 = new Node(0, 0, "label", Color.BLUE, Color.BLACK, 1);
		Edges edge = new Edges(node1, node2, "label", Color.GREEN, Color.BLACK, 1);
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addEdge(edge);
		String answer="\\definecolor{c0_0_0}{RGB}{0,0,0};\n"+"\\definecolor{c0_0_255}{RGB}{0,0,255};\n"
				+"\\definecolor{c0_128_0}{RGB}{0,128,0};\n\n\n"
				+"\\draw[--, line width=0.26mm,draw=c0_0_0](1) edge node{label} (0);\n";
		String query = control.getCommands();
		System.out.println(query);
		System.out.println(answer);
		assertTrue(query.equals(answer));
	}
	
	
}
