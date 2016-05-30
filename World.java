package world;

import java.util.List;

import java.util.Map;

import javafx.scene.image.Image;

/**
 * The World Interface in order to create more than one worlds. For example if we want
 * another world not the ArisWorld we can change it from the property file of course if we have
 * build another world.
 * 
 * @author  Aris
 */

public interface World {
	/**
	 * Change the View to Look right ( 45 degrees )
	 */
	void look_right();
	
	/**
	 * Change the View to Look Left ( 45 degrees )
	 */
	void look_left();

	/**
	 * Fix the screen arrows
	 */
	void arrows_view();

	/**
	 * Walk one step straight
	 */
	boolean go_straight();

	/**
	 * get current screen
	 */
	Screen getCurrentScreen();

	/**
	 * get current Image
	 */
	Image getCurrent_Image();
	
	/**
	 * get the Items from the current screen
	 */
	Map<String, Item> getItems();

	/**
	 * get the current Map
	 */
	Image getCurrent_Map();

	Image getLeftArrow();

	Image getRightArrow();

	Image getStraightArrow();
	
	/**
	 * Add one item to the Inventory
	 */
	void inventoryAddItem(String itemName);
	
	/**
	 * Remove one item from the inventory
	 */
	void inventoryRemoveItem(InventoryItem item);

	/**
	 * Get the last item from the inventory
	 */
	InventoryItem getLastInventoryItem();

	/**
	 * Get the whole inventory as a List
	 */
	List<InventoryItem> getInvenotryList();
	
	/**
	 * Get the Compass
	 */
	int getCompassRotation();
	
	/**
	 * Get the current Room
	 */
	Room getCurrent_room();
}