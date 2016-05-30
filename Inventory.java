package world;

import java.util.ArrayList;
import java.util.List;

/**
 * Inventory class that handle everything that goes in and out in our inventory. My Inventory is a BAG style inventory
 * for more realistic usage.
 * @author Aris
 *
 */
public class Inventory {
	private int max_items = 5;
	List<InventoryItem> itemList ;
	
	public Inventory(int max_items) {
		this.max_items = max_items;
		itemList = new ArrayList<InventoryItem>();
	}
	/**
	 * Insert an item to the Inventory
	 * @param itemName
	 */
	public void insertItem(String itemName){
		InventoryItem invItem = new InventoryItem(itemName);
		this.itemList.add(invItem);
	}
	/**
	 * Remove an item from the inventory
	 * @param itemToBeRemoved
	 */
	public void removeItem(InventoryItem itemToBeRemoved){
		for (int i=0; i<itemList.size(); i++){
			if ( itemList.get(i).getName().equals(itemToBeRemoved.getName()) )
					itemList.remove(i);
		}
	}
	/**
	 * Get last Inventory Item
	 * @return
	 */
	public InventoryItem getLastItem(){
		if (itemList.size() == 0)
			return null;
		else
			return itemList.get(itemList.size()-1);
	}
	/**
	 * Get inventory List
	 * @return
	 */
	public List<InventoryItem> getList(){
		return itemList;
	}
}
