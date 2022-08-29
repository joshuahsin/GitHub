package labs.lab7;

public class Main {

	/**
	 * Given a string, returns true if it is a nesting of one or more pairs of parentheses, like "(())" or "((()))". 
	 * If there are any other characters besides parentheses in the string, return false.
	 * 
	 * @param str	the string to check for nested parentheses. str.length() will always be > 0.
	 * @return		true if the string is a nesting or 1 or more pairs of parentheses
	 */
	public static boolean problem1_nested(String str) {
		int length = str.length();
		if(length % 2 != 0) {
			return false;
		}
		int half_length = str.length() / 2;
		for(int i = 0 ; i < half_length ; i++) {
			if(str.charAt(i) != '(') {
				return false;
			}
		}
		for(int i = half_length ; i < length ; i++) {
			if(str.charAt(i) != ')') {
				return false;
			}
		}
		return true; // FIX ME
	}


	/**
	 * We'll say that a "double" in a string is two instances of a char, separated by a char. 
	 * So in "AxA", the A's make a double. Doubles can overlap, so "AxAxA" contains 3 doubles -- 2 for A and 1 for x. 
	 * This method recursively computes the number of doubles in the given string.
	 * 
	 * @param str	the string to check for doubles
	 * @return		the number of doubles in the given string
	 */
	public static int problem2_countDoubles(String str) {
		if(str.length() < 3) {
			return 0;
		}
		else if(str.length() == 3) {
			if(str.charAt(0) == str.charAt(2)) {
				return 1;
			}
			else {
				return 0;
			}
		}
		else {
			if(str.charAt(0) == str.charAt(2)) {
				return problem2_countDoubles(str.substring(1, str.length())) + 1;
			}
			else {
				return problem2_countDoubles(str.substring(1, str.length()));
			}
		}// FIX ME
	}


	/**
	 * Given a string and a non-empty substring (sub), computes recursively if at least n copies of sub 
	 * appear in the string somewhere, possibly with overlapping.
	 * 
	 * @param str	the string to check for copies
	 * @param sub	the substring to look for in the string
	 * @param n		the number of copies to look for in the string (will always be non-negative)
	 * @return		true if at least n copies of sub appear in the string somewhere, with overlapping
	 */
	public static boolean problem3_countCopies(String str, String sub, int n) {
		if(n == 0) {
			return true;
		}
		else if(n == 1) { 
			if(str.contains(sub)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if(str.contains(sub)) {
				int index = str.indexOf(sub);
				return problem3_countCopies(str.substring(index + 1, str.length()), sub, n - 1);
			}
			else {
				return false;
			}
		}
	}
	
	public static void main(String[] args) {
		ToDoList list = new ToDoList();
		list.addItem("Walk Robert", 1);
		list.addItem("Walk Robert", 1);
		list.addItem("Walk Robert", 2);
		list.addItem("Walk Robert", 3);
		list.addItem("Do 45J Lab", 1);
		list.addItem("Grocery shopping", 6);
		list.addItem("Take a nap", 4);
		list.addItem("Pay bills", 2);
		list.addItem("Call Mom", 5);
		list.addItem("Do laundry", 1);
		list.addItem("Call Dad", 5);
		
		list.removeAllItemsWithDescription("Walk Robert");
		list.removeAllItemsWithDescription("Walk bob");
		
		list.removeFirstItemWithPriority(1);
		list.removeFirstItemWithPriority(10);
		
		list.removeAllItemsWithPriority(5);
		list.removeAllItemsWithPriority(10);
		
		list.addItem("Update resume", -5);
		
		System.out.println(list.getSortedItems());
		
		String list1 = list.toString();
		System.out.println(list1);

	}
}