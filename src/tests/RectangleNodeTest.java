package tests;

import static org.junit.Assert.*;
import javafx.scene.paint.Color;
import model.CircleNode;
import model.RectangleNode;
import model.SquareNode;

import org.junit.Test;

public class RectangleNodeTest {

	@Test
	public void rectangleNodeCreationTest() {
		RectangleNode node = new RectangleNode(0, 0, 60, 50, "label", Color.BLACK, Color.BLACK, 1);
		assertTrue(node.getWidth() == 50 && node.getLength() == 60);
	}

	@Test
	public void rectangleIsInNodeTest() {
		RectangleNode node = new RectangleNode(0, 0, 60, 50, "label", Color.BLACK, Color.BLACK, 1);
		assertTrue(node.isInNode(40, 25) && node.isInNode(58, 48) && !node.isInNode(113, 113));
	}

	@Test
	public void setWidthLengthTest(){
		RectangleNode node = new RectangleNode(0, 0, 60, 50, "label", Color.BLACK, Color.BLACK, 1);
		node.setLength(100); node.setWidth(40);
		assertTrue(node.getLength() == 100 && node.getWidth() == 40);
		assertTrue(node.getCenterX() == 30 && node.getCenterY() == 25);
	}
	
	@Test
	public void setCenterXYTest() {
		RectangleNode node = new RectangleNode(0, 0, 60, 50, "label", Color.BLACK, Color.BLACK, 1);
		node.setCenterXY(100, 200);
		assertTrue(node.getCenterX() == 100 && node.getCenterY() == 200);
	}
	
	@Test
	public void getCommandTest(){
		RectangleNode rnode = new RectangleNode(0, 0, 50, 70, "label", Color.BLACK, Color.BLACK, 1);
		String rcommand = rnode.getCommand(0);
		System.out.println(rcommand);
		assertTrue(rcommand.equals("\\node [rectangle,minimum height=1.85cm,line width=0.26mm,draw=c0_0_0,fill=c0_0_0,minimum width=1.32cm] (0) at(0.66,0.93) {label};\n"));
	}

}
