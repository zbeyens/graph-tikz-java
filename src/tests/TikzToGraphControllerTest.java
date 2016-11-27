package tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import model.Edges;
import model.Graph;
import model.RectangleNode;

import org.junit.Test;

import view.View;
import controller.TikzToGraphController;

public class TikzToGraphControllerTest {


	
	
	@Test
	public void setCommandsCodeTest() {
		
		Graph graph = new Graph();

		TikzToGraphController controller = new TikzToGraphController(null, graph, null,  null);
		String code = "\\definecolor{c0_0_0}{RGB}{0,0,0};\n" + 
				"\\definecolor{c255_255_255}{RGB}{255,255,255};\n" +

				"\\node [rectangle,line width=0.26mm,draw=c0_0_0,fill=c255_255_255,minimum width=1.32cm,minimum height=0.53cm] (0) at(7.96,6.72) {};\n" +
				"\\node [rectangle,line width=0.26mm,draw=c0_0_0,fill=c255_255_255,minimum width=1.32cm,minimum height=0.53cm] (1) at(7.41,4.58) {};\n";
		
		List<String > commands = controller.setCommandsCode(code);
		assertTrue(commands.get(1).equals("definecolor{c0_0_0}{RGB}{0,0,0};\n") &&
				commands.get(2).equals("definecolor{c255_255_255}{RGB}{255,255,255};\n") &&
				commands.get(3).equals("node[rectangle,linewidth=0.26mm,draw=c0_0_0,fill=c255_255_255,minimumwidth=1.32cm,minimumheight=0.53cm](0)at(7.96,6.72){};\n"));
	}
	
	@Test
	public void interpretTest() {
		
		Graph graph = new Graph();
		TikzToGraphController controller = new TikzToGraphController(new Canvas(), graph, new View(), null);
		String code = 	"\\node [rectangle,line width=0.26mm,minimum width=1.59cm,minimum height=1.85cm] (1) at(0.79,0.93) {label1};\n" +
						"\\node [rectangle,line width=0.26mm,minimum width=1.59cm,minimum height=1.85cm] (2) at(0.79,0.93) {label2};\n" +
						"\\draw[->, draw=c0_0_0, line width=0.26mm](1) edge node{label} (2);\n";
		controller.setCommandsCode(code);
		controller.interpret();
		RectangleNode node1 = (RectangleNode) graph.findNode(1);
		RectangleNode node2 = (RectangleNode) graph.findNode(2);
		Edges edge = graph.getEdges().get(0);
		
		assertTrue(node1.getLabel().equals("label1") &&
				node1.getWidth() == 70 &&
				node1.getLength() == 60 &&
				node1.getThickness() == 1 &&
				node1.getStrokeColor().equals(Color.BLACK) &&
				node1.getFillColor().equals(Color.WHITE) &&
				edge.getParent().equals(node1) &&
				edge.getChild().equals(node2) &&
				edge.getThickness() == 1
		);
	}
}

