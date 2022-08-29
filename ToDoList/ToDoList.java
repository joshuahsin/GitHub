package labs.lab7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Represents a list of TodoItems
 *
 */
public class ToDoList {

	// ADD YOUR INSTANCE VARIABLES EHRE
	ArrayList<ToDoItem> ItemList;

	public ToDoList() {
		// FILL IN
		ItemList = new ArrayList<ToDoItem>();
	}


	/**
	 * Adds an item to the list
	 * 
	 * @param description	description of item to add 
	 * @param priority		priority of item to add
	 */
	public void addItem(String description, int priority) {
		// FILL IN
		ItemList.add(new ToDoItem(description, priority));
	}


	/**
	 * Removes the first item with the given description
	 * 
	 * @param description	item description to remove
	 * @return				true if the item was removed
	 */
	public boolean removeFirstItemWithDescription(String description) {
		for(int i = 0 ; i < ItemList.size() ; i++) {
			if(ItemList.get(i).getDescription().equals(description)) {
				ItemList.remove(i);
				return true;
			}
		}
		return false; // FIX ME
	}


	/**
	 * Removes all items with the given description
	 * 
	 * @param description	item description to remove
	 * @return				true if at least one item was removed
	 */
	public boolean removeAllItemsWithDescription(String description) {
		boolean return_bool = false;
		for(int i = 0 ; i < ItemList.size() ; i++) {
			if(ItemList.get(i).getDescription().equals(description)) {
				ItemList.remove(i);
				if(return_bool == false) {
					return_bool = true;
				}
				i--;
			}
		}
		return return_bool; // FIX ME
	}


	/**
	 * Removes the first item with the given priority
	 * "First item" is determined according to the sort order
	 * of the ToDoItems in the list.
	 * 
	 * @param priority	item priority to remove
	 * @return			true if the item was removed
	 */
	public boolean removeFirstItemWithPriority(int priority) {
		for(int i = 0 ; i < ItemList.size() ; i++) {
			if(ItemList.get(i).getPriority() == (priority)) {
				ItemList.remove(i);
				return true;
			}
		}
		return false; // FIX ME
	}
	
	
	/**
	 * Removes all items with the given priority
	 * 
	 * @param priority	item priority to remove
	 * @return			true if at least one item was removed
	 */
	public boolean removeAllItemsWithPriority(int priority) {
		boolean return_bool = false;
		for(int i = 0 ; i < ItemList.size() ; i++) {
			if(ItemList.get(i).getPriority() == (priority)) {
				ItemList.remove(i);
				if(return_bool == false) {
					return_bool = true;
				}
				i--;
			}
		}
		return return_bool; // FIX ME
	}


	/**
	 * Returns the first item with the given description.
	 * "First item" is determined according to the sort order
	 * of the ToDoItems in the list.
	 * 
	 * @param description	item description to find
	 * @return				the item, or null if not found
	 */
	public ToDoItem findFirstItemWithDescription(String description) {
		for(int i = 0 ; i < ItemList.size() ; i++) {
			if(ItemList.get(i).getDescription().equals(description)) {
				return ItemList.get(i);
			}
		}
		return null; // FIX ME
	}


	/**
	 * Returns a sorted list of all items with the given description
	 * 
	 * @param description	item description to find
	 * @return				a list of all items with the matching description
	 */
	public List<ToDoItem> findAllItemsWithDescription(String description) {
		ArrayList <ToDoItem> returnlist = new ArrayList<ToDoItem>();
		for(int i = 0 ; i < ItemList.size() ; i++) {
			if(ItemList.get(i).getDescription().equals(description)) {
				returnlist.add(ItemList.get(i));
			}
		}
		Collections.sort(returnlist);
		return returnlist; // FIX ME
	}


	/**
	 * Returns the first item with the given priority.
	 * "First item" is determined according to the sort order
	 * of the ToDoItems in the list.
	 * 
	 * @param priority	item priority to find
	 * @return			the item, or null if not found
	 */
	public ToDoItem findFirstItemWithPriority(int priority) {
		for(int i = 0 ; i < ItemList.size() ; i++) {
			if(ItemList.get(i).getPriority() == priority) {
				return ItemList.get(i);
			}
		}
		return null; // FIX ME
	}
	
	
	/**
	 * Returns a sorted list of all items with the given priority
	 * 
	 * @param priority	item priority to find
	 * @return			a list of all items with the matching priority
	 */
	public List<ToDoItem> findAllItemsWithPriority(int priority) {
		ArrayList <ToDoItem> returnlist = new ArrayList<ToDoItem>();
		for(int i = 0 ; i < ItemList.size() ; i++) {
			if(ItemList.get(i).getPriority() == priority) {
				returnlist.add(ItemList.get(i));
			}
		}
		Collections.sort(returnlist);
		return returnlist; // FIX ME
	}


	/**
	 * @return	a list of all ToDoItems, sorted
	 */
	public List<ToDoItem> getSortedItems() {
		ArrayList <ToDoItem> returnlist = new ArrayList<ToDoItem>();
		for(int i = 0 ; i < ItemList.size() ; i++) {
			returnlist.add(ItemList.get(i));
		}
		Collections.sort(returnlist);
		return returnlist; // FIX ME
	}
	
	
	/**
	 * Prints all the items in the list, sorted, with a comma and space between
	 * each one
	 */
	@Override
	public String toString() {
		String return_string = "";
		List <ToDoItem> sortedlist = getSortedItems();
		for(int i = 0 ; i < sortedlist.size() ; i++) {
			return_string += sortedlist.get(i).toString() + ", ";
		}
		return return_string.substring(0, return_string.length() - 2); // FIX ME
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof ToDoList) {
			ToDoList other = (ToDoList)o;
			if(ItemList.size() != other.ItemList.size()) {
				return false;
			}
			else {
				List<ToDoItem> ItemListOrdered = getSortedItems();
				List<ToDoItem> OtherListOrdered = other.getSortedItems();
				for(int i = 0 ; i < ItemListOrdered.size() ; i++) {
					if(!(ItemListOrdered.get(i).equals(OtherListOrdered.get(i)))) {
						return false;
					}
				}
				return true;
			}
		}
		return false; // FIX ME
	}
}
