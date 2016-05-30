package world;

import java.net.URL;

import javafx.scene.image.Image;

/**
 * Item Objects that each screen can have with the ability to save their coordinates for drag and drop movement
 * @author Aris
 *
 */
public class Item {

	private String imageViewId;
	private double translateX;
	private double translateY;
	private Image image;
	private String description;
	
	public Item(String itemName,String imageViewId) {
		
		URL url = getClass().getClassLoader().getResource("images/" + itemName + ".png");
		if (url != null)
			this.image = new Image(url.toString());
		this.translateX = 0;
		this.translateY = 0;
		this.imageViewId = imageViewId;
	}
	public String getImageViewId() {
		return imageViewId;
	}

	public void setImageViewId(String imageViewId) {
		this.imageViewId = imageViewId;
	}

	public double getTranslateX() {
		return translateX;
	}

	public void setTranslateX(double translateX) {
		this.translateX = translateX;
	}

	public double getTranslateY() {
		return translateY;
	}

	public void setTranslateY(double translateY) {
		this.translateY = translateY;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
