package world;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import inputs.SingletonProperties;
import javafx.scene.image.Image;

/**
 * Main Room class that is responsible for all the rooms functions
 * 
 * @author Aris
 *
 */
public class Room {
	
	private int numberOfImages;
	private String name;
	private Map<Integer, Screen> screens; 	// Hashmap for the multiple screens in
											// this room with screen position as
											// key
	private Screen currentScreen = null; 	// The current screen in the room
	private Image map = null;

	public Room(String name) {

		// Class Name
		this.name = name;
			try {
				this.numberOfImages = Integer.valueOf(SingletonProperties.getInstance().getProperty("number_of_images"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		// Image for the Map
		URL url = getClass().getClassLoader().getResource("images/map_" + this.name + ".png");
		
		if (url != null)
			this.map = new Image(url.toString());
		
		// Create multiple screens for each room
		screens = new HashMap<Integer, Screen>();

		// Create multiple screens-constraints for each room
		for (int i = 0; i < numberOfImages; i++)
			this.screens.put(i, createScreenFromPoperties(i));

		// Initialize the starting screen for each room
		setCurrentScreen(getScreen(0));
	}
	
	/**
	 * Create the screen from property file for each room
	 * @param position
	 * @return
	 */
	private Screen createScreenFromPoperties(int position) {
		URL url = getClass().getClassLoader()
				.getResource("images/" + getName() + "." + String.valueOf(position + 1) + ".png");
		try {
			String[] screenStrings = SingletonProperties.getInstance().getRoomConstraints(getName());
			boolean entered = false;
			for (int i = 0; i < screenStrings.length - 1; i++) {
				
				String[] temp = splitScreenString(screenStrings[i]);
				if (temp.length != 6) {
					System.out.println("Check your property file..");
					System.exit(1);
				}
				if (temp[0].equals(String.valueOf(position))) {
					entered = true;
					boolean isDoor = Boolean.valueOf(temp[1]);
					boolean isWalkable = Boolean.valueOf(temp[2]);
					String nextRoom = "";
					if (!temp[3].equals("-"))
						nextRoom = temp[3];
					int lookBack = -1;
					if (!temp[4].equals("-")){
						if(!temp[4].equals("0"))
							lookBack = position + Integer.valueOf(temp[4]);
						else 
							lookBack = 0;
					}
					Item item = null;
					if (!temp[5].equals("-"))
						item = new Item(temp[5], temp[5]);

					Screen screen = new Screen(position, url, isDoor, isWalkable, nextRoom, lookBack, item);
					return screen;
				}
			}
			// Now lets handle the else case i order to decrease our properties
			// values
			if (!entered) {
				String[] temp = splitScreenString(screenStrings[screenStrings.length - 1]);
				if (temp.length != 6) {
					System.out.println("Check your property file..");
					System.exit(1);
				}
				boolean isDoor = Boolean.valueOf(temp[1]);
				boolean isWalkable = Boolean.valueOf(temp[2]);
				String nextRoom = "";
				if (!temp[3].equals("-"))
					nextRoom = temp[3];
				int lookBack = -1;
				if (!temp[4].equals("-"))
					if(!temp[4].equals("0"))
						lookBack = position + Integer.valueOf(temp[4]);
					else 
						lookBack = 0;
				Item item = null;
				if (!temp[5].equals("-"))
					item = new Item(temp[5], temp[5]);

				Screen screen = new Screen(position, url, isDoor, isWalkable, nextRoom, lookBack, item);
				return screen;
			}
		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
			System.exit(1);
		}
		return null;

	}

	private String[] splitScreenString(String myScreenString) {
		String[] info = null;
		try {
			info = myScreenString.trim().split(",");
			return info;
		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
			System.exit(1);
		}
		return info;
	}

	public Screen getCurrentScreen() {
		return currentScreen;
	}

	protected Screen getScreen(int key) {
		return this.screens.get(key);
	}

	protected void setCurrentScreen(Screen currentScreen) {
		this.currentScreen = currentScreen;
	}

	/**
	 * Look Right
	 */
	void look_right() {
		int position = this.currentScreen.getPosition();
		if (position == 7) {
			this.currentScreen = screens.get(0);
		} else {
			this.currentScreen = screens.get(position + 1);
		}
	}
	/**
	 * Look Left
	 */
	void look_left() {
		int position = this.currentScreen.getPosition();
		if (position == 0) {
			this.currentScreen = screens.get(7);
		} else {
			this.currentScreen = screens.get(position - 1);
		}
	}

	/**
	 * Move straight
	 * @return
	 */
	public String moveStraight() {

		if (this.currentScreen.isWalkable()) {

			String next_room = this.currentScreen.getNextRoom();
			this.currentScreen = screens.get(this.currentScreen.getLookBack());
			return next_room;
		}
		return "";
	}
	
	/**
	 * Get Room Name
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	/**
	 *  Get Room Map
	 * @return
	 */
	public Image getMap() {
		return this.map;
	}
}
