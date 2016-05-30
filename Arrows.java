package world;

import java.net.URL;

import javafx.scene.image.Image;
/**
 * Class for handling the displaying arrows BOTH FROM KEYBOARD AND FROM MOUSE CLICKS
 * @author Aris
 *
 */
public class Arrows {

	private Image arrow_left = null;
	private Image arrow_straight = null;
	private Image arrow_right = null;
	
	public Arrows() {
		URL url = getClass().getClassLoader().getResource("images/arrow_left.png");
		arrow_left = new Image(url.toString());
		url = getClass().getClassLoader().getResource("images/arrow_straight_enter.png");
		arrow_straight = new Image(url.toString());
		url = getClass().getClassLoader().getResource("images/arrow_right.png");
		arrow_right = new Image(url.toString());
		
	}
	public Image getArrow_left() {
		return arrow_left;
	}

	public Image getArrow_straight() {
		return arrow_straight;
	}

	public void setArrow_straight() {
		URL url = getClass().getClassLoader().getResource("images/arrow_straight.png");
		arrow_straight = new Image(url.toString());
	}
	public void setArrow_straight_enter() {
		URL url = getClass().getClassLoader().getResource("images/arrow_straight_enter.png");
		arrow_straight = new Image(url.toString());
	}
	public void removeArrow_straight() {
		arrow_straight = null;
	}	
	public Image getArrow_right() {
		return arrow_right;
	}

}
