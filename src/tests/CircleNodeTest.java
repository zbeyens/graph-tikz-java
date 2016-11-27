package tests;

import static org.junit.Assert.*;
import javafx.scene.paint.Color;
import model.CircleNode;
import model.RectangleNode;
import model.SquareNode;

import org.junit.Test;

public class CircleNodeTest {

	@Test
	public void circleNodeCreationTest() {
		CircleNode node = new CircleNode(0, 0, 60, "label", Color.BLACK, Color.BLACK, 1);
		assertTrue(node.getRadius() == 60 && node.getCenterX() == 0 && node.getCenterY() == 0);
	}

	@Test
	public void circleIsInNodeTest() {
		CircleNode node = new CircleNode(0, 0, 60, "label", Color.BLACK, Color.BLACK, 1);
		assertTrue(node.isInNode(30, 50) && !node.isInNode(70, 0));
	}
	
	@Test
	public void getCommandTest(){
		CircleNode cnode = new CircleNode(0, 0, 50, "label", Color.BLACK, Color.BLACK, 1);
		String ccommand = cnode.getCommand(0);
		assertTrue(ccommand.equals("\\node [circle,line width=0.26mm,draw=c0_0_0,fill=c0_0_0,minimum size=2.65cm] (0) at(0.0,0.0) {label};\n"));
	}
	
	
}
