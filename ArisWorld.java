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
 * The main Class that is responsible for almost Everything in our World
 * It will Initialize and Handle Rooms,Screens,Items,Inventories,Maps..
 * @author Aris
 *
 */
public class ArisWorld implements World {

	private Room current_room;
	private Inventory inventory;
	private Arrows arrows;
	private Map<String, Room> rooms ;	//HashMap for all the rooms
	
	public ArisWorld() throws IOException{
		
		rooms = new HashMap<String, Room>();
		
		// Initialize all our rooms
		String[] rooms = SingletonProperties.getInstance().getProperty("rooms").split(",");
		
		for (int i=0; i<rooms.length; i++)
			this.rooms.put(rooms[i], new Room(rooms[i]));

		//Initialize the Arrows
		arrows = new Arrows();
		
		// Initialize the Inventory
		inventory = new Inventory(5);
		
		// Initialize our Starting Room
		current_room = this.rooms.get(rooms[0]);
	}
	
	@Override
	public void look_right(){
		this.current_room.look_right();
		arrows_view();
	}
	@Override
	public void look_left(){
		this.current_room.look_left();
		arrows_view();
	}	
	@Override
	public void arrows_view(){
		if (getCurrentScreen().isDoor() && getCurrentScreen().isWalkable()){
			this.arrows.setArrow_straight_enter();
		}else if(getCurrentScreen().isWalkable()){
			this.arrows.setArrow_straight();
		}else if(!getCurrentScreen().isWalkable())
			this.arrows.removeArrow_straight();
	}
	@Override
	public boolean go_straight(){
		boolean door = getCurrentScreen().isDoor();
		if(getCurrentScreen().isWalkable()){
			String new_room = this.current_room.moveStraight();
			
			this.current_room = rooms.get(new_room);
			
			arrows_view();
		}
		return door;
	}

	public Screen getCurrentScreen(){
		return this.current_room.getCurrentScreen();
	}
	public Image getCurrent_Image(){
		return new Image(getCurrentScreen().getImageURL().toString());
	}	
	public Map<String, Item> getItems() {
		return getCurrentScreen().getItems();
	}
	public Image getCurrent_Map(){
		return this.current_room.getMap();
	}	
	public Image getLeftArrow(){
		return this.arrows.getArrow_left();
	}
	public Image getRightArrow(){
		return this.arrows.getArrow_right();
	}
	public Image getStraightArrow(){
		return this.arrows.getArrow_straight();
	}
	public void inventoryAddItem(String itemName){
		this.inventory.insertItem(itemName);
	}
	public void inventoryRemoveItem(InventoryItem item){
		this.inventory.removeItem(item);
	}
	public InventoryItem getLastInventoryItem(){
		return this.inventory.getLastItem();
	}
	public List<InventoryItem> getInvenotryList() {
		return inventory.getList();
	}
	public int getCompassRotation(){
		if (this.current_room.getName().equals("D") || this.current_room.getName().equals("E") || this.current_room.getName().equals("F") ){
			return (getCurrentScreen().getPosition() +2 )*45;
		}
		else 
			return getCurrentScreen().getPosition() *45;
	}
	public Room getCurrent_room() {
		return current_room;
	}
}
