package labs.lab7;

/**
 * Represents a todo item
 *
 */
public class ToDoItem implements Comparable<ToDoItem> {

	// ADD YOUR INSTANCE VARIABLES HERE
	String description;
	int priority;

	/**
	 * 
	 * @param description
	 * @param priority    must be >= 0, otherwise gets priority of 0
	 */
	public ToDoItem(String description, int priority) {
		// FILL IN
		this.description = description;
		if(priority < 0) {
			this.priority = 0;
		}
		else {
			this.priority = priority;
		}
	}


	public String getDescription() {
		return description; // FIX ME
	}


	public int getPriority() {
		return priority; // FIX ME
	}
	
	
	@Override
	public String toString() {
		return priority + " " + description; // FIX ME
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof ToDoItem) {
			ToDoItem other = (ToDoItem) o;
			return description.equals(other.description) && priority == other.priority;
		}
		return false; // FIX ME
	}


	/**
	 * Compares first by priority, then by description
	 */
	public int compareTo(ToDoItem otherObject) {
		ToDoItem otherToDoItem = (ToDoItem) otherObject;
		if(priority < otherToDoItem.priority) {
			return -1;
		}
		else if(priority > otherToDoItem.priority) {
			return 1;
		}
		else {
			int compare = description.compareTo(otherToDoItem.description);
			if(compare < 0) {
				return -1;
			}
			else if(compare > 0) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}

}
