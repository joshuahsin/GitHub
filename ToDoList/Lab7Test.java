package labs.lab7;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class Lab7Test {

	@Test
	void problem1() {
		assertTrue(Main.problem1_nested("(())")); 
		assertTrue(Main.problem1_nested("((()))")); 
		assertFalse(Main.problem1_nested("(((x))")); 
		assertFalse(Main.problem1_nested("(((x)))")); 
		assertFalse(Main.problem1_nested("fdasf")); 
		assertFalse(Main.problem1_nested("((())"));
		assertTrue(Main.problem1_nested("()"));
		assertFalse(Main.problem1_nested("((()()"));
		assertFalse(Main.problem1_nested("(yy)"));
		assertTrue(Main.problem1_nested("(())"));
		assertFalse(Main.problem1_nested("(((y))"));
		assertFalse(Main.problem1_nested("((y)))"));
		assertTrue(Main.problem1_nested("((()))"));
		assertFalse(Main.problem1_nested("(())))"));
		assertFalse(Main.problem1_nested("((yy())))"));
		assertFalse(Main.problem1_nested("( )"));
		assertFalse(Main.problem1_nested(" "));
		assertFalse(Main.problem1_nested(" () "));
		assertTrue(Main.problem1_nested("(((())))"));
	}
	
	
	@Test
	void problem2() {
		assertEquals(1, Main.problem2_countDoubles("axa"));
		assertEquals(2, Main.problem2_countDoubles("axax"));
		assertEquals(1, Main.problem2_countDoubles("axbx"));
		assertEquals(0, Main.problem2_countDoubles("hi"));
		assertEquals(3, Main.problem2_countDoubles("hihih"));
		assertEquals(3, Main.problem2_countDoubles("ihihhh"));
		assertEquals(0, Main.problem2_countDoubles("ihjxhh"));
		assertEquals(0, Main.problem2_countDoubles(""));
		assertEquals(0, Main.problem2_countDoubles("a"));
		assertEquals(0, Main.problem2_countDoubles("aa"));
		assertEquals(1, Main.problem2_countDoubles("aaa"));
	}
	
	
	@Test
	void problem3() {
		assertTrue(Main.problem3_countCopies("catcowcat", "cat", 2));
		assertFalse(Main.problem3_countCopies("catcowcat", "cow", 2));
		assertTrue(Main.problem3_countCopies("catcowcat", "cow", 1));
		assertTrue(Main.problem3_countCopies("iiijjj", "i", 3));
		assertFalse(Main.problem3_countCopies("iiijjj", "i", 4));
		assertTrue(Main.problem3_countCopies("iiijjj", "ii", 2));
		assertFalse(Main.problem3_countCopies("iiijjj", "ii", 3));
		assertFalse(Main.problem3_countCopies("iiijjj", "x", 3));
		assertTrue(Main.problem3_countCopies("iiijjj", "x", 0));
		assertTrue(Main.problem3_countCopies("iiiiij", "iii", 3));
		assertFalse(Main.problem3_countCopies("iiiiij", "iii", 4));
		assertTrue(Main.problem3_countCopies("ijiiiiij", "iiii", 2));
		assertFalse(Main.problem3_countCopies("ijiiiiij", "iiii", 3));
		assertTrue(Main.problem3_countCopies("dogcatdogcat", "dog", 2));
	}
	
	
	@Test
	void problems4And5() {
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
		
		// testing getSortedItems:
		List<ToDoItem> expectedList1 = Arrays.asList(
				new ToDoItem("Do 45J Lab", 1),
				new ToDoItem("Do laundry", 1),
				new ToDoItem("Walk Robert", 1),
				new ToDoItem("Walk Robert", 1),
				new ToDoItem("Pay bills", 2),
				new ToDoItem("Walk Robert", 2),
				new ToDoItem("Walk Robert", 3),
				new ToDoItem("Take a nap", 4),
				new ToDoItem("Call Dad", 5),
				new ToDoItem("Call Mom", 5),
				new ToDoItem("Grocery shopping", 6)
				);
		assertTrue(expectedList1.equals(list.getSortedItems()));

		// testing findFirstItemWithDescription:
		assertEquals(new ToDoItem("Walk Robert", 1), list.findFirstItemWithDescription("Walk Robert"));
		assertNull(list.findFirstItemWithDescription("Walk bob"));
		
		// testing removeFirstItemWithDescription:
		assertTrue(list.removeFirstItemWithDescription("Walk Robert")); 
		assertFalse(list.removeFirstItemWithDescription("Walk bob"));
		expectedList1 = Arrays.asList(
				new ToDoItem("Do 45J Lab", 1),
				new ToDoItem("Do laundry", 1),
				new ToDoItem("Walk Robert", 1),
				new ToDoItem("Pay bills", 2),
				new ToDoItem("Walk Robert", 2),
				new ToDoItem("Walk Robert", 3),
				new ToDoItem("Take a nap", 4),
				new ToDoItem("Call Dad", 5),
				new ToDoItem("Call Mom", 5),
				new ToDoItem("Grocery shopping", 6)
				);
		assertTrue(expectedList1.equals(list.getSortedItems()));
		
		// testing findAllItemsWithDescription:
		List<ToDoItem> expectedList2 = Arrays.asList(
				new ToDoItem("Walk Robert", 1),
				new ToDoItem("Walk Robert", 2),
				new ToDoItem("Walk Robert", 3)
				);
		assertEquals(expectedList2, list.findAllItemsWithDescription("Walk Robert"));
		assertEquals(new ArrayList<ToDoItem>(), list.findAllItemsWithDescription("Walk bob"));
		
		// testing removeAllItemsWithDescription:
		assertTrue(list.removeAllItemsWithDescription("Walk Robert"));
		assertFalse(list.removeAllItemsWithDescription("Walk bob"));
		expectedList1 = Arrays.asList(
				new ToDoItem("Do 45J Lab", 1),
				new ToDoItem("Do laundry", 1),
				new ToDoItem("Pay bills", 2),
				new ToDoItem("Take a nap", 4),
				new ToDoItem("Call Dad", 5),
				new ToDoItem("Call Mom", 5),
				new ToDoItem("Grocery shopping", 6)
				);
		assertTrue(expectedList1.equals(list.getSortedItems()));
		
		// testing findFirstItemWithPriority:
		assertEquals(new ToDoItem("Do 45J Lab", 1), list.findFirstItemWithPriority(1));
		assertNull(list.findFirstItemWithPriority(10));
		
		// testing removeFirstItemWithPriority:
		assertTrue(list.removeFirstItemWithPriority(1));
		assertFalse(list.removeFirstItemWithPriority(10));
		expectedList1 = Arrays.asList(
				new ToDoItem("Do laundry", 1),
				new ToDoItem("Pay bills", 2),
				new ToDoItem("Take a nap", 4),
				new ToDoItem("Call Dad", 5),
				new ToDoItem("Call Mom", 5),
				new ToDoItem("Grocery shopping", 6)
				);
		assertTrue(expectedList1.equals(list.getSortedItems()));

		// testing findAllItemsWithPriority:
		expectedList2 = Arrays.asList(
				new ToDoItem("Call Dad", 5),
				new ToDoItem("Call Mom", 5)
				);
		assertEquals(expectedList2, list.findAllItemsWithPriority(5));
		assertEquals(new ArrayList<ToDoItem>(), list.findAllItemsWithPriority(10));

		// testing removeAllItemsWithPriority:
		assertTrue(list.removeAllItemsWithPriority(5));
		assertFalse(list.removeAllItemsWithPriority(10));
		expectedList1 = Arrays.asList(
				new ToDoItem("Do laundry", 1),
				new ToDoItem("Pay bills", 2),
				new ToDoItem("Take a nap", 4),
				new ToDoItem("Grocery shopping", 6)
				);
		assertTrue(expectedList1.equals(list.getSortedItems()));
		
		// add a priority of -5, should make it 0:
		list.addItem("Update resume", -5);
		expectedList1 = Arrays.asList(
				new ToDoItem("Update resume", 0),
				new ToDoItem("Do laundry", 1),
				new ToDoItem("Pay bills", 2),
				new ToDoItem("Take a nap", 4),
				new ToDoItem("Grocery shopping", 6)
				);
		assertTrue(expectedList1.equals(list.getSortedItems()));
		
		// testing ToDoItem.toString:
		assertEquals("2 Pay bills", (new ToDoItem("Pay bills", 2)).toString());
		
		// testing ToDoList.toString:
		assertEquals("0 Update resume, 1 Do laundry, 2 Pay bills, 4 Take a nap, 6 Grocery shopping", list.toString());
		
		// testing ToDoItem.equals:
		assertTrue(new ToDoItem("Grocery shopping", 6).equals(expectedList1.get(4)));
		assertFalse(new ToDoItem("Grocery shopping", 6).equals(expectedList1.get(0)));
		
		// testing adding a new item after all of the above has happened, make sure it's inserted in the correct position:
		list.addItem("Go to the gym", 3);
		list.addItem("Get a massage", 4);
		expectedList1 = Arrays.asList(
				new ToDoItem("Update resume", 0),
				new ToDoItem("Do laundry", 1),
				new ToDoItem("Pay bills", 2),
				new ToDoItem("Go to the gym", 3),
				new ToDoItem("Get a massage", 4),
				new ToDoItem("Take a nap", 4),
				new ToDoItem("Grocery shopping", 6)
				);
		assertTrue(expectedList1.equals(list.getSortedItems()));
	}

}
