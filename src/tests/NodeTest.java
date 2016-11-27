package tests;

import static org.junit.Assert.assertTrue;
import javafx.scene.paint.Color;
import model.Node;

import org.junit.Test;

public class NodeTest {

	@Test
	public void nodeCreationTest(){
		Node node = new Node(0, 0, "label", Color.BLACK, Color.BLACK, 1);
		assertTrue(node.getPosX() == 0 && node.getPosY() == 0);
	}
	
	@Test
	public void getCommandTest(){
		Node node = new Node(0, 0, "label", Color.BLACK, Color.BLACK, 1);
		String command = node.getCommand("label", 10, 0, 0, 0);
		System.out.println(command);
		assertTrue(command.equals("line width=0.26mm,draw=c0_0_0,fill=c0_0_0,label0.26cm] (0) at(0.0,0.0)"));
	}
}
