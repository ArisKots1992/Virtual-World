package controller;

import gif.AnimationGif;
import javafx.scene.Scene;

/**
 * A controller for the ArisVirtualWorld Application in order to import multiple controllers easier.
 *
 * @author  Aris
 */
public interface Controller{
	
	/**
	 * Initialize our world
	 */
	public void Initialise(Scene scene,AnimationGif.CustomAnimation anim);
	
	/**
	 * Update our GUI
	 */
	public void updateGui(boolean effect);
	
	/**
	 * Handlers for the movement with Arrow keys
	 */
	public void movement(Scene scene);
	
	/**
	 * Creating the Compass
	 */
	public void createCompass();
	
	/**
	 * Handlers for Cursor effects.
	 */
	public void mouseCursorEffect(Scene scene);
	/* the other functions are private because another Controller might do not need them e.g. Pick Items.
	 * in another implementation we might not need to pick up items
	 */
}