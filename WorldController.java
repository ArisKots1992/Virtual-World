package controller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import gif.AnimationGif;
import inputs.SingletonProperties;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import world.InventoryItem;
import world.Item;
import world.World;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * My main WorldController that is responsible for every action. It initialize our World and it can handle all
 * the listeners and handlers.(keyboard,mouse,click,buttons,effects,..)
 * @author Aris
 *
 */
public class WorldController implements Controller {

	// FXML Objects
	@FXML
	private ImageView mainImage;
	@FXML
	private ImageView map;
	@FXML
	private ImageView compass,compass_arrow;
	@FXML
	private ImageView arrow_left, arrow_right, arrow_straight;
	@FXML
	private ImageView item1, item2, item3, item4, item5;
	@FXML
	private ImageView inventoryItem1, inventoryItem2, inventoryItem3, inventoryItem4, inventoryItem5;
	@FXML
	private VBox vBox;
	// My Objects
	World myWorld;
	double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
	private AnimationGif.CustomAnimation anim;
	private MediaPlayer mediaPlayer;
	/**
	 * Initialize our world
	 */
	public void Initialise(Scene scene,AnimationGif.CustomAnimation anim) {

		// System.out.println(arrow_straight.getFitHeight());
		// arrow_straight.setTranslateX(168.0);
		// arrow_straight.setTranslateY(-343.0);
		myWorld = (World) SingletonProperties.getInstance().getWorld();
		createCompass();
		updateGui(false);
		// moveCircleOnMousePress(scene);
		movement(scene);			// handles movement with arrows
		mouseNavigation(scene);		// handles screen arrows clicking
		mouseDragItems(scene);		// handles the drag item operation
		mousePickUp(scene);			// handles the item pick up
		mouseCursorEffect(scene);	// handles the cursor effects
       this.anim = anim;
       this.anim.disableBear();
       
       URL resource;
		try {
			resource = getClass().getResource(SingletonProperties.getInstance().getProperty("music"));
			Media media = new Media(resource.toString());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setOnEndOfMedia(new Runnable() {
				public void run() {
					mediaPlayer.stop();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * Function to update our GUI
	 */
	public void updateGui(boolean effect) {

		// The effect for entering doors
		if (effect) {

			FadeTransition ft = new FadeTransition();
			ft.setNode(mainImage);
			ft.setDuration(new Duration(1000));
			ft.setFromValue(0.5);
			ft.setToValue(0.0);
			ft.setCycleCount(1);
			ft.setAutoReverse(true);
			ft.play();
			FadeTransition ft2 = new FadeTransition();
			ft2.setNode(mainImage);
			ft2.setDuration(new Duration(1000));
			ft2.setFromValue(0.5);
			ft2.setToValue(1);
			ft2.setCycleCount(1);
			ft2.setAutoReverse(true);
			ft2.play();
		}
		// Update the GUI
		// Main Image
		mainImage.setImage(myWorld.getCurrent_Image());
		// Arrows
		arrow_left.setImage(myWorld.getLeftArrow());
		arrow_right.setImage(myWorld.getRightArrow());
		if (myWorld.getStraightArrow() == null) {
			arrow_straight.setImage(myWorld.getStraightArrow());
			arrow_straight.setDisable(true);
		} else {
			arrow_straight.setDisable(false);
			arrow_straight.setImage(myWorld.getStraightArrow());
		}
		// Map
		map.setImage(myWorld.getCurrent_Map());
		// Update Items
		Map<String, Item> items = myWorld.getItems();
		nullItemsImages();
		for (String key : items.keySet()) {
			Item item = items.get(key);
			switch (item.getImageViewId()) {
			case "item1":
				item1.setDisable(false);
				item1.setImage(item.getImage());
				break;
			case "item2":
				item2.setDisable(false);
				item2.setImage(item.getImage());
				break;
			case "item3":
				item3.setDisable(false);
				item3.setImage(item.getImage());
				break;
			case "item4":
				item4.setDisable(false);
				item4.setImage(item.getImage());
				break;
			case "item5":
				item5.setDisable(false);
				item5.setImage(item.getImage());
				break;
			}
		}
		// Inventory
		List<InventoryItem> invList = myWorld.getInvenotryList();
		for(InventoryItem invItem : invList){
			if (inventoryItem1.getImage() == null){
				inventoryItem1.setImage(invItem.getImage());
			}else if(inventoryItem2.getImage() == null){
				inventoryItem2.setImage(invItem.getImage());
			}else if(inventoryItem3.getImage() == null){
				inventoryItem3.setImage(invItem.getImage());
			}else if(inventoryItem4.getImage() == null){
				inventoryItem4.setImage(invItem.getImage());
			}else if(inventoryItem5.getImage() == null){
				inventoryItem5.setImage(invItem.getImage());
			}
		}
		// Compass
		compass_arrow.setRotate(myWorld.getCompassRotation());
	}
	/**
	 * Remove images from items
	 */
	public void nullItemsImages() {
		item1.setImage(null);
		item2.setImage(null);
		item3.setImage(null);
		item4.setImage(null);
		item5.setImage(null);
		item1.setDisable(true);
		item2.setDisable(true);
		item3.setDisable(true);
		item4.setDisable(true);
		item5.setDisable(true);
		inventoryItem1.setImage(null);
		inventoryItem2.setImage(null);
		inventoryItem3.setImage(null);
		inventoryItem4.setImage(null);
		inventoryItem5.setImage(null);
	}

	/**
	 * Handlers for the movement with Arrow keys
	 */
	public void movement(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					boolean effect = myWorld.go_straight();
					updateGui(effect);
					break;
				case RIGHT:
					myWorld.look_right();
					updateGui(false);
					break;
				// case DOWN: basketView.setImage(null); break;
				case LEFT:
					myWorld.look_left();
					updateGui(false);
					break;
				}
			}
		});
	}

	/**
	 * Handlers for the movement with mouse( simultaneously with arrows )
	 */
	private void mouseNavigation(Scene scene) {

		arrow_left.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				myWorld.look_left();
				updateGui(false);
			}
		});
		arrow_straight.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				boolean effect = myWorld.go_straight();
				updateGui(effect);
			}
		});
		arrow_right.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				myWorld.look_right();
				updateGui(false);
			}
		});
	}
	
	/**
	 * Handlers in order to change the items positions
	 */
	private void mouseDragItems(Scene scene) {
		item1.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (item1.getImage() != null) {
					orgSceneX = me.getSceneX();
					orgSceneY = me.getSceneY();
					orgTranslateX = ((ImageView) (me.getSource())).getTranslateX();
					orgTranslateY = ((ImageView) (me.getSource())).getTranslateY();

				}
			}
		});
		item1.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				if (legalAction(t.getSceneX(), t.getSceneY(), scene, item1)) {
					double offsetX = t.getSceneX() - orgSceneX;
					double offsetY = t.getSceneY() - orgSceneY;
					double newTranslateX = orgTranslateX + offsetX;
					double newTranslateY = orgTranslateY + offsetY;
					// System.out.println("newTranslate(X,Y) = " +
					// String.valueOf(newTranslateX) + ","
					// + String.valueOf(newTranslateY));
					((ImageView) (t.getSource())).setTranslateX(newTranslateX);
					((ImageView) (t.getSource())).setTranslateY(newTranslateY);
				}
			}
		});
		item2.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (item2.getImage() != null) {
					orgSceneX = me.getSceneX();
					orgSceneY = me.getSceneY();
					orgTranslateX = ((ImageView) (me.getSource())).getTranslateX();
					orgTranslateY = ((ImageView) (me.getSource())).getTranslateY();

				}
			}
		});
		item2.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				if (legalAction(t.getSceneX(), t.getSceneY(), scene, item2)) {
					double offsetX = t.getSceneX() - orgSceneX;
					double offsetY = t.getSceneY() - orgSceneY;
					double newTranslateX = orgTranslateX + offsetX;
					double newTranslateY = orgTranslateY + offsetY;
					// System.out.println("newTranslate(X,Y) = " +
					// String.valueOf(newTranslateX) + ","
					// + String.valueOf(newTranslateY));
					((ImageView) (t.getSource())).setTranslateX(newTranslateX);
					((ImageView) (t.getSource())).setTranslateY(newTranslateY);
				}
			}
		});
		item3.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (item3.getImage() != null) {
					orgSceneX = me.getSceneX();
					orgSceneY = me.getSceneY();
					orgTranslateX = ((ImageView) (me.getSource())).getTranslateX();
					orgTranslateY = ((ImageView) (me.getSource())).getTranslateY();

				}
			}
		});
		item3.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				if (legalAction(t.getSceneX(), t.getSceneY(), scene, item3)) {
					double offsetX = t.getSceneX() - orgSceneX;
					double offsetY = t.getSceneY() - orgSceneY;
					double newTranslateX = orgTranslateX + offsetX;
					double newTranslateY = orgTranslateY + offsetY;
					// System.out.println("newTranslate(X,Y) = " +
					// String.valueOf(newTranslateX) + ","
					// + String.valueOf(newTranslateY));
					((ImageView) (t.getSource())).setTranslateX(newTranslateX);
					((ImageView) (t.getSource())).setTranslateY(newTranslateY);
				}
			}
		});
		item4.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (item4.getImage() != null) {
					orgSceneX = me.getSceneX();
					orgSceneY = me.getSceneY();
					orgTranslateX = ((ImageView) (me.getSource())).getTranslateX();
					orgTranslateY = ((ImageView) (me.getSource())).getTranslateY();

				}
			}
		});
		item4.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				if (legalAction(t.getSceneX(), t.getSceneY(), scene, item4)) {
					double offsetX = t.getSceneX() - orgSceneX;
					double offsetY = t.getSceneY() - orgSceneY;
					double newTranslateX = orgTranslateX + offsetX;
					double newTranslateY = orgTranslateY + offsetY;
					// System.out.println("newTranslate(X,Y) = " +
					// String.valueOf(newTranslateX) + ","
					// + String.valueOf(newTranslateY));
					((ImageView) (t.getSource())).setTranslateX(newTranslateX);
					((ImageView) (t.getSource())).setTranslateY(newTranslateY);
				}
			}
		});
		item5.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (item5.getImage() != null) {
					orgSceneX = me.getSceneX();
					orgSceneY = me.getSceneY();
					orgTranslateX = ((ImageView) (me.getSource())).getTranslateX();
					orgTranslateY = ((ImageView) (me.getSource())).getTranslateY();

				}
			}
		});
		item5.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				if (legalAction(t.getSceneX(), t.getSceneY(), scene, item5)) {
					double offsetX = t.getSceneX() - orgSceneX;
					double offsetY = t.getSceneY() - orgSceneY;
					double newTranslateX = orgTranslateX + offsetX;
					double newTranslateY = orgTranslateY + offsetY;
					// System.out.println("newTranslate(X,Y) = " +
					// String.valueOf(newTranslateX) + ","
					// + String.valueOf(newTranslateY));
					((ImageView) (t.getSource())).setTranslateX(newTranslateX);
					((ImageView) (t.getSource())).setTranslateY(newTranslateY);
				}
			}
		});
	}
	/**
	 * Handlers for picking up items with double click.
	 */
	private void mousePickUp(Scene scene) {
		item1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						myWorld.getCurrentScreen().removeItem("item1");
						myWorld.inventoryAddItem("inventoryItem1");
						updateGui(false);
						if(	myWorld.getInvenotryList().size() == 5)
							enableBear();
					}
				}
			}
		});
		item2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						myWorld.getCurrentScreen().removeItem("item2");
						myWorld.inventoryAddItem("inventoryItem2");
						updateGui(false);
						if(	myWorld.getInvenotryList().size() == 5)
							enableBear();
					}
				}
			}
		});
		item3.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						myWorld.getCurrentScreen().removeItem("item3");
						myWorld.inventoryAddItem("inventoryItem3");
						updateGui(false);
						if(	myWorld.getInvenotryList().size() == 5)
							enableBear();
					}
				}
			}
		});
		item4.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						myWorld.getCurrentScreen().removeItem("item4");
						myWorld.inventoryAddItem("inventoryItem4");
						updateGui(false);
						if(	myWorld.getInvenotryList().size() == 5)
							enableBear();
					}
				}
			}
		});
		item5.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						myWorld.getCurrentScreen().removeItem("item5");
						myWorld.inventoryAddItem("inventoryItem5");
						updateGui(false);
						if(	myWorld.getInvenotryList().size() == 5)
							enableBear();
					}
				}
			}
		});
	}
	
	/**
	 * Handlers for Cursor effects.
	 */
	public void mouseCursorEffect(Scene scene) {
		arrow_straight.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.HAND); // Change cursor to hand
			}
		});
		arrow_straight.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.DEFAULT); // Change cursor to hand
			}
		});	
		arrow_right.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.HAND); // Change cursor to hand
			}
		});
		arrow_right.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.DEFAULT); // Change cursor to hand
			}
		});
		arrow_left.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.HAND); // Change cursor to hand
			}
		});
		arrow_left.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.DEFAULT); // Change cursor to hand
			}
		});
		item1.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.OPEN_HAND); // Change cursor to hand
			}
		});
		item1.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.DEFAULT); // Change cursor to hand
			}
		});
		item2.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.OPEN_HAND); // Change cursor to hand
			}
		});
		item2.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.DEFAULT); // Change cursor to hand
			}
		});
		item3.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.OPEN_HAND); // Change cursor to hand
			}
		});
		item3.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.DEFAULT); // Change cursor to hand
			}
		});
		item4.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.OPEN_HAND); // Change cursor to hand
			}
		});
		item4.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.DEFAULT); // Change cursor to hand
			}
		});
		item5.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.OPEN_HAND); // Change cursor to hand
			}
		});
		item5.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				scene.setCursor(Cursor.DEFAULT); // Change cursor to hand
			}
		});
	}
	/**
	 * Enable Bear Dancing and it can close automatically
	 */
	public void enableBear(){
		anim.enableBear();
		anim.setCycleCount(5);
	    anim.play();
	    mediaPlayer.play();
	}
	public void dropItem(ActionEvent event) {
		if (myWorld.getLastInventoryItem() != null) {
			String itemName = myWorld.getLastInventoryItem().getName();
			String number = itemName.substring(itemName.length() - 1);
			number = "item" + number;
			myWorld.getCurrentScreen().addItem(new Item(number, number));
			myWorld.inventoryRemoveItem(myWorld.getLastInventoryItem());
			updateGui(false);
		}
	}
	public void dropAllItems(ActionEvent event){
		while(myWorld.getLastInventoryItem() != null){
			String itemName = myWorld.getLastInventoryItem().getName();
			String number = itemName.substring(itemName.length() - 1);
			number = "item" + number;
			myWorld.getCurrentScreen().addItem(new Item(number, number));
			myWorld.inventoryRemoveItem(myWorld.getLastInventoryItem());	
		}
		updateGui(false);
	}

	public void createCompass(){
		URL url = getClass().getClassLoader().getResource("images/compass.png");
		compass.setImage(new Image(url.toString()));
		url = getClass().getClassLoader().getResource("images/compass_arrow.png");
		compass_arrow.setImage(new Image(url.toString()));
		compass_arrow.setDisable(true);
		compass.setDisable(true);
	}
	/**
	 * Help function for Dragging the items correctly.
	 */
	boolean legalAction(double x, double y, Scene scene, ImageView item) {
		if (item.getImage() != null && x < scene.getWidth() - vBox.getWidth() - item.getFitWidth() / 2
				&& x > 0 + item.getFitWidth() / 3
				&& y < scene.getHeight() - map.getFitHeight() - item.getFitHeight() / 4
				&& y > 0 + item.getFitHeight() / 3)
			return true;
		return false;
	}

}
