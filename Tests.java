package testsJunit;

import static org.junit.Assert.*;
import java.io.IOException;
import java.net.URL;
import org.junit.Rule;
import org.junit.Test;
import javafx.scene.image.Image;
import world.ArisWorld;
import world.Item;
import world.Room;
import world.Screen;
import world.World;
import javafx.scene.image.Image;
/**
 * Junit Test. I start from the inner class and going slowly outside to be sure everything is working perfectly.
 * Item,Screen,Room,World
 * @author Aris
 * @version 1.0
 */
public class Tests {
	
	// Important rule in order to initialize correctly our javaFx
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	/**
	 * Simple Item test
	 */
	@Test
	public void ItemTest() throws IOException {
		Item item1 = new Item("item1","item1");
		Item item2 = new Item("item2","item2");

		// The screens MUST be different objects
		assertTrue("Same Items", !item1.equals(item2));
		assertTrue("Same Image", !item1.getImageViewId().equals(item2.getImageViewId()));
		assertTrue("Same Image", item1.getTranslateX() == item2.getTranslateX());

	}
	/**
	 * Simple Screen Test
	 */
	@Test
	public void ScreenTest() throws IOException {
		URL url = new URL("http://myTestUrl.com/");
		Item item = new Item("item1","item1");
		Screen screen1 = new Screen(0, url, false, true, "A", 0, null);
		Screen screen2 = new Screen(0, url, false, true, "A", 0, null);
		Screen screen3 = new Screen(15, url, true, true, "B", 0, item);

		// The screens MUST be different objects
		assertTrue("Same Screens", !screen1.equals(screen2));
		// Position Must be different
		assertTrue("The same orientation", screen3.getPosition() != screen2.getPosition());
		// The screens must have the same elements
		assertTrue("Different Back picture", screen3.getLookBack() == screen2.getLookBack());
		// door,walkable check
		assertTrue("Different Back picture", screen3.isDoor() && screen3.isWalkable());
		assertTrue("Different Back picture", !screen2.isDoor() && screen2.isWalkable());
		assertTrue("Wrong Items!",screen3.getItems().size() == 1);
	}
	/**
	 * Simple room Test
	 */
	@Test
	public void roomTest() throws IOException {
		Room sp = new Room("startingPoint");
		Room a = new Room("A");
		
		// room "sp"  must have a door infront of it
		// while room "a" must not
		assertTrue("This is not a door", sp.getCurrentScreen().isDoor());
		assertTrue("Different Back picture",  !a.getCurrentScreen().isDoor());
		
		// Next Room check
		assertTrue("next room of A is not B", a.getCurrentScreen().getNextRoom().equals("B"));
	}
	/**
	 * Advanced Complete Test for ArisWorld
	 */
	@Test
	public void WorldTest() throws IOException {
		World aris = new ArisWorld();	// starting point is the room startingPoint
		
		// Advanced test for accuracy 
		aris.go_straight();	// we Enter Room A
		aris.look_right();	// we turn 45 so we need two turns
		aris.look_right();
		aris.go_straight();	// we are now in room D
		aris.go_straight(); // we are now in room E
		aris.go_straight(); // we are now in room F
		aris.look_right();
		aris.look_right();
		aris.go_straight();	// we are now in room Miami
		
		// Check the Room if is Miami!
		assertTrue("Advanced Test Failed", aris.getCurrent_room().getName().equals("miami"));
	}	
	/**
	 * Advanced Rotate Test
	 */
	@Test
	public void WorldTestRotate() throws IOException {
		World aris = new ArisWorld();	// starting point is the room startingPoint
		
		// Rotate test for accuracy 
		aris.look_right();	// we turn 45 so we need 8 turns
		aris.look_right();
		aris.look_right();
		aris.look_right();
		aris.look_right();
		aris.look_right();	// trick for extra test
		aris.look_left();
		aris.look_right();
		aris.look_right();
		aris.look_right();
		aris.go_straight();
		
		// Check the Room if is A!
		assertTrue("Rotate Test Failed", aris.getCurrent_room().getName().equals("A"));
	}	
	/**
	 * Advanced Inventory Test
	 */
	@Test
	public void InventoryTest() throws IOException {
		World aris = new ArisWorld();	// starting point is the room startingPoint
		
		// Rotate test for accuracy 
		aris.look_left();
		aris.look_left();
		aris.inventoryAddItem(aris.getCurrentScreen().getItems().get("item1").getImageViewId());
		aris.look_right();
		aris.look_right();
		aris.go_straight();
		// Check the Room if is A!
		assertTrue("Inventory Test Failed.", aris.getLastInventoryItem().getName().equals("item1"));
	}	
}
