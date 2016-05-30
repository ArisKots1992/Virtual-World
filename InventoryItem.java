package world;

import java.net.URL;

import javafx.scene.image.Image;

/**
 * InventoryItem object is like an Item object but without the coordinates. and with smaller resolution
 * @author Aris
 *
 */
public class InventoryItem {

	String name;
	Image image;
	String Description;

	public InventoryItem(String itemName) {
		URL url = getClass().getClassLoader().getResource("images/" + itemName + ".png");
		if (url != null)
			this.image = new Image(url.toString());
		this.name = itemName;
	}
	/**
	 * Get Inventory Item name
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

}
