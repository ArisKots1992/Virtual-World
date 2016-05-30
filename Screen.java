package world;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * Everything that will be displayed on each screen for each room ( like Frames in
 * movies )
 * 
 * @author Aris
 *
 */
public class Screen {

	private int position = 0;
	private URL imageURL = null; // NOT Image image; because we should not load
									// all the images at once only the ones that
									// we use
	// private Image image = null; <----- Only for a small number of images
	private boolean door = false;
	private boolean walkable = false;
	private String nextRoom = "";
	private int lookBack = -1;
	private Map<String, Item> items;
	
	public Screen(int position, URL imageURL, boolean door, boolean walkable, String nextRoom, int lookBack,Item item) {
		this.position = position;
		this.imageURL = imageURL;
		this.door = door;
		this.walkable = walkable;
		this.nextRoom = nextRoom;
		this.lookBack = lookBack;
		this.items = new HashMap<String, Item>();
		if( item!=null )
			this.items.put(item.getImageViewId(), item);

	}
	/**
	 * Remove Item from screen
	 * @param id
	 */
	public void removeItem(String id) {
		this.items.remove(id);
	}

	public void editItem(String id,double translateX, double translateY) {
		this.items.get(id).setTranslateX(translateX);
		this.items.get(id).setTranslateY(translateY);
	}
	/**
	 * add an item to the screen
	 * @param item
	 */
	public void addItem(Item item) {
		this.items.put(item.getImageViewId(), item);
	}
	/** 
	 * get all the screens Item HashMap
	 * @return
	 */
	public Map<String, Item> getItems() {
		return items;
	}
	/**
	 * Get the Screen Position
	 * @return
	 */
	public int getPosition() {
		return position;
	}
	/** 
	 * Check if there is a door in the current screen
	 * @return
	 */
	public boolean isDoor() {
		return door;
	}
	/**
	 * check if the current screen is walkable
	 * @return
	 */
	public boolean isWalkable() {
		return walkable;
	}
	
	public URL getImageURL() {
		return imageURL;
	}

	/**
	 * Get the next room from the current screen
	 * @return
	 */
	public String getNextRoom() {
		return nextRoom;
	}
	/**
	 * Look Back
	 * @return
	 */
	public int getLookBack() {
		return lookBack;
	}
}
