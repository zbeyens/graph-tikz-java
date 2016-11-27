package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.scene.paint.Color;
import model.Characteristic;

public class CharacteristicTest {

	@Test
	public void CharacCreationTest() {
		Characteristic ch = new Characteristic("label", Color.BLACK, Color.BLACK, 1);
		assertTrue(ch.getLabel() == "label" && ch.getFillColor() == Color.BLACK && ch.getStrokeColor() == Color.BLACK
				&& ch.getThickness() == 1);
	}
	
	@Test
	public void getRGBTest(){
		Characteristic ch = new Characteristic("label", Color.BLACK, Color.BLACK, 1);
		int[] color = ch.getRGB(ch.getStrokeColor());
		assertTrue(color[0] == 0 && color[1] == 0 && color[2] == 0 );
	}

	@Test
	public void getNameOfColorTest(){
		Characteristic ch = new Characteristic("label", Color.BLACK, Color.BLACK, 1);
		int[] color = ch.getRGB(ch.getStrokeColor());
		System.out.println(ch.getNameOfColor(color));
		assertTrue(ch.getNameOfColor(color).equals("c0_0_0"));
	}
	
}
