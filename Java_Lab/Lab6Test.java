package labs.lab6;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class Lab6Test {
	
	private final double EPSILON = 1e-3;
	
	@Test
	void problems1And2() throws IOException {
		
		// test trimBlankLines:
		
		// trimLines1:
		String testFileName = "res/trimLines1.txt";
		Files.trimBlankLines(testFileName);
		List<String> linesList = Arrays.asList(
				"Down, down, down. There was nothing else to do, so Alice soon began",
				"talking again. 'Dinah'll miss me very much to-night, I should think!'",
				"(Dinah was the cat.)",
				"",
				"",
				"Alice was 12 years old."
				);
		assertLinesMatch(linesList, java.nio.file.Files.readAllLines(new File(testFileName).toPath()));
		
		// trimLines2:
		testFileName = "res/trimLines2.txt";
		Files.trimBlankLines(testFileName);
		assertLinesMatch(linesList, java.nio.file.Files.readAllLines(new File(testFileName).toPath()));
		
		// trimLines3:
		testFileName = "res/trimLines3.txt";
		Files.trimBlankLines(testFileName);
		assertLinesMatch(linesList, java.nio.file.Files.readAllLines(new File(testFileName).toPath()));
		
		// trimLines4:
		testFileName = "res/trimLines4.txt";
		Files.trimBlankLines(testFileName);
		linesList = Arrays.asList(
				"Down, down, down. There was nothing else to do, so Alice soon began",
				"talking again. 'Dinah'll miss me very much to-night, I should think!'",
				"(Dinah was the cat.)",
				"",
				"Alice was 12 years old."
				);
		assertLinesMatch(linesList, java.nio.file.Files.readAllLines(new File(testFileName).toPath()));
		
		// trimLines5-blank:
		testFileName = "res/trimLines5-blank.txt";
		Files.trimBlankLines(testFileName);
		linesList = new ArrayList<String>();
		assertLinesMatch(linesList, java.nio.file.Files.readAllLines(new File(testFileName).toPath()));
		
		// test exception message is printed out:
		String nonExistentFileName = "res/nothing.txt";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		Files.trimBlankLines(nonExistentFileName);
		
		String result = output.toString();
		assertEquals("File: " + nonExistentFileName + " not found", result);
		

		// test reverse:
		
		// reverseLines1:
		testFileName = "res/reverseLines1.txt";
		Files.reverse(testFileName);
		linesList = Arrays.asList(
				"",
				"",
				"",
				"Alice was 12 years old.",
				"",
				"",
				"(Dinah was the cat.)",
				"talking again. 'Dinah'll miss me very much to-night, I should think!'",
				"Down, down, down. There was nothing else to do, so Alice soon began",
				""
				);
		assertLinesMatch(linesList, java.nio.file.Files.readAllLines(new File(testFileName).toPath()));
		
		// reverseLines2:
		testFileName = "res/reverseLines2.txt";
		Files.reverse(testFileName);
		linesList = Arrays.asList(
				"",
				"",
				"",
				"Alice was 12 years old.",
				"",
				"",
				"(Dinah was the cat.)",
				"talking again. 'Dinah'll miss me very much to-night, I should think!'",
				"Down, down, down. There was nothing else to do, so Alice soon began"
				);
		assertLinesMatch(linesList, java.nio.file.Files.readAllLines(new File(testFileName).toPath()));
		
		// reverseLines3:
		testFileName = "res/reverseLines3.txt";
		Files.reverse(testFileName);
		linesList = Arrays.asList(
				"Alice was 12 years old.",
				"",
				"",
				"(Dinah was the cat.)",
				"talking again. 'Dinah'll miss me very much to-night, I should think!'",
				"Down, down, down. There was nothing else to do, so Alice soon began",
				""
				);
		assertLinesMatch(linesList, java.nio.file.Files.readAllLines(new File(testFileName).toPath()));
		
		// reverseLines4:
		testFileName = "res/reverseLines4.txt";
		Files.reverse(testFileName);
		linesList = Arrays.asList(
				"Alice was 12 years old.",
				"",
				"(Dinah was the cat.)",
				"talking again. 'Dinah'll miss me very much to-night, I should think!'",
				"Down, down, down. There was nothing else to do, so Alice soon began",
				""
				);
		assertLinesMatch(linesList, java.nio.file.Files.readAllLines(new File(testFileName).toPath()));
		
		// reverseLines5-blank:
		testFileName = "res/reverseLines5-blank.txt";
		Files.reverse(testFileName);
		linesList = new ArrayList<String>();
		assertLinesMatch(linesList, java.nio.file.Files.readAllLines(new File(testFileName).toPath()));
		
		
		// test exception message is printed out:
		output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		nonExistentFileName = "res/nothing2.txt";
		Files.reverse(nonExistentFileName);
		
		result = output.toString();
		assertEquals("File: " + nonExistentFileName + " not found", result);
	}
	
	
	@Test
	void problems3And4() {
		
		// babynames1.txt (10 records):
		BabyNameData data = new BabyNameData("res/babynames1.txt");
		
		// boy names:
		List<String> boyNames = data.getAllBoyNames();
		
		List<String> expectedList = Arrays.asList(
				"Michael",
				"Christopher",
				"Matthew",
				"Joshua",
				"Nicholas",
				"Jacob",
				"Daniel",
				"Andrew",
				"Tyler",
				"Joseph"
				);
				
		assertTrue(expectedList.size() == boyNames.size() && expectedList.containsAll(boyNames) &&
				boyNames.containsAll(expectedList));
		
		
		// girl names:
		List<String> girlNames = data.getAllGirlNames();
		
		expectedList = Arrays.asList(
				"Megan",
				"Ashley",
				"Samantha",
				"Sarah",
				"Emily",
				"Amanda",
				"Brittany",
				"Elizabeth",
				"Taylor",
				"Jessica"
				);
				
		assertTrue(expectedList.size() == girlNames.size() && expectedList.containsAll(girlNames) &&
				girlNames.containsAll(expectedList));
		
		
		// non-gender specific names:
		List<String> nonGenderSpecificNames = data.getAllNonGenderSpecificNames();
		
		expectedList = new ArrayList<>();
				
		assertTrue(expectedList.size() == nonGenderSpecificNames.size() && 
				expectedList.containsAll(nonGenderSpecificNames) &&
				nonGenderSpecificNames.containsAll(expectedList));
		
		
		// rank:
		int rank = 3;
		assertEquals("Emily", data.getGirlNameAtRank(rank));
		assertEquals("Matthew", data.getBoyNameAtRank(rank));
		
		rank = 10;
		assertEquals("Megan", data.getGirlNameAtRank(rank));
		assertEquals("Joseph", data.getBoyNameAtRank(rank));
		
		rank = 1;
		assertEquals("Jessica", data.getGirlNameAtRank(rank));
		assertEquals("Michael", data.getBoyNameAtRank(rank));
		
		
		// babynames2.txt (70 records):
		data = new BabyNameData("res/babynames2.txt");
		assertEquals(70, data.getAllBoyNames().size());
		assertEquals(70, data.getAllGirlNames().size());
		
		expectedList = Arrays.asList(
				"Taylor",
				"Jordan"
				);
		
		nonGenderSpecificNames = data.getAllNonGenderSpecificNames();
		
		assertTrue(expectedList.size() == nonGenderSpecificNames.size() && 
				expectedList.containsAll(nonGenderSpecificNames) &&
				nonGenderSpecificNames.containsAll(expectedList));
		
		
		// exception for out of range rank:
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	        BabyNameData data2 = new BabyNameData("res/babynames2.txt");
	        data2.getBoyNameAtRank(-1);
	    });

	    String expectedMessage = "Rank -1 out of range of data";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	    
		exception = assertThrows(IllegalArgumentException.class, () -> {
	        BabyNameData data2 = new BabyNameData("res/babynames1.txt");
	        data2.getBoyNameAtRank(100);
	    });

	    expectedMessage = "Rank 100 out of range of data";
	    actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    
	    // exception for file not found:
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		String nonExistentFileName = "nobabynames.txt";
		new BabyNameData(nonExistentFileName);
		
		String result = output.toString();
		assertEquals("File: " + nonExistentFileName + " not found", result);
	}
	
	
	@Test
	void problem5() {
		// coins1.dat:
		CoinCollection collection = new CoinCollection("res/coins1.dat");
		assertEquals(0.36, collection.getTotalValue(), EPSILON);
		assertEquals(3, collection.getTotalCoinCount(), EPSILON);
		assertEquals(1, collection.getCoinCount("quarter"));
		assertEquals(1, collection.getCoinCount("dime"));
		assertEquals(1, collection.getCoinCount("penny"));
		assertEquals(0, collection.getCoinCount("Penny"));
		assertEquals(0, collection.getCoinCount("nickel"));
		
		
		// coins2.dat:
		collection = new CoinCollection("res/coins2.dat");
		assertEquals(0.99, collection.getTotalValue(), EPSILON);
		assertEquals(9, collection.getTotalCoinCount(), EPSILON);
		assertEquals(3, collection.getCoinCount("quarter"));
		assertEquals(2, collection.getCoinCount("dime"));
		assertEquals(4, collection.getCoinCount("penny"));
		assertEquals(0, collection.getCoinCount("Penny"));
		assertEquals(0, collection.getCoinCount("nickel"));
		
		
		// coins3.dat (wrong formatting):
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		String wrongFormatting = "res/coins3.dat";
		new CoinCollection(wrongFormatting);
		
		String result = output.toString();
		assertEquals("Coin value must be of type double", result);
		
		
		// coins4.dat (file not found):
		output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		String nonExistentFileName = "res/missingcoins.dat";
		collection = new CoinCollection(nonExistentFileName);
		
		result = output.toString();
		assertEquals("File: " + nonExistentFileName + " not found", result);
	}
	
	
	@Test
	void problems6Through10() {
		
		// date exceptions:
		
		// wrongly-formatted date:
		Exception exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk 2021-09-10 6:00 6:45");
	    });
	    String expectedMessage = "Invalid date";
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    // wrongly-formatted date:
		exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk September 10, 2021 6:00 6:45");
	    });
		actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    // invalid year:
		exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk -2021/09/10 6:00 6:45");
	    });
		actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    // invalid month:
		exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk 2021/13/10 6:00 6:45");
	    });
		actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    // invalid day:
		exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk 2021/09/31 6:00 6:45");
	    });
		actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    // Feb 29 in non-leap year:
	    exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk 2021/02/29 6:00 6:45");
	    });
		actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    
		// time exceptions:
	    
	    // error in time format:
	    exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk 2021/02/01 600 6:45");
	    });
	    expectedMessage = "Invalid time";
		actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    // error in time format:
	    exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk 2021/02/01 6;00 645");
	    });
		actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    // error in time format:
	    exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk 2021/02/01 600 six-forty-five");
	    });
		actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    // invalid hour:
	    exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk 2021/02/01 29:00 6:45");
	    });
		actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    // invalid hour:
	    exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk 2021/02/01 6:00 -6:45");
	    });
		actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    // invalid minute:
	    exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk 2021/02/01 6:60 6:75");
	    });
		actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    // invalid minute:
	    exception = assertThrows(RuntimeException.class, () -> {
			new Appointment("Take Robert for a walk 2021/02/01 6:00 6:8099");
	    });
		actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    
	    
	    // Valid input (shouldn't throw exceptions):
	    
		// One word appointment names:
	    Appointment appt1 = new Appointment("Walk 2021/9/14 6:00 7:00");
	    Appointment appt2 = new Appointment("Lunch 2021/09/14 12:00 13:00");
	    
	    // Multi-word appointment names:
	    Appointment appt3 = new Appointment("Take Robert for a walk 2021/09/10 3:00 6:45");
	    Appointment appt4 = new Appointment("Take Robert for a walk 2021/9/10 3:00 6:45");
	    Appointment appt5 = new Appointment("Take Robert for a walk 2021/9/11 3:00 6:45");
	    Appointment appt6 = new Appointment("Take a nap 2021/9/11 3:00 6:45");
	    
	    // Appointment.equals:
	    assertTrue(appt3.equals(appt4));
	    assertFalse(appt1.equals(appt3));
	    assertFalse(appt4.equals(appt5));
	    assertFalse(appt5.equals(appt6));
	    
		// Appointment.fallsOn:
	    AppointmentDate date1 = new AppointmentDate("2021/09/14");
	    AppointmentDate date2 = new AppointmentDate("2021/09/10");
	    assertTrue(appt1.fallsOn(date1));
	    assertTrue(appt3.fallsOn(date2));
	    assertFalse(appt1.fallsOn(date2));
	    
	    // Appointment.toString:
	    assertEquals("Walk 2021/09/14 06:00 07:00", appt1.toString());
	    assertEquals("Take Robert for a walk 2021/09/10 03:00 06:45", appt4.toString());
	    
	    // AppointmentDate.equals:
	    AppointmentDate date3 = new AppointmentDate("2021/9/14");
	    assertTrue(date3.equals(date1));
	    assertFalse(date3.equals(date2));
	    
	    // AppointmentDate.toString:
	    AppointmentDate date4 = new AppointmentDate("2000/1/1");
	    assertEquals("2000/01/01", date4.toString());
	    assertEquals("2021/09/14", date3.toString());
	    
	    // AppointmentTime.equals:
	    AppointmentTime time1 = new AppointmentTime("22:03");
	    AppointmentTime time2 = new AppointmentTime("22:03");
	    AppointmentTime time3 = new AppointmentTime("22:04");
	    AppointmentTime time4 = new AppointmentTime("6:00");
	    AppointmentTime time5 = new AppointmentTime("1:51");
	    assertTrue(time1.equals(time2));
	    assertFalse(time1.equals(time3));
	    assertFalse(time4.equals(time5));
	    
	    // AppointmentTime.toString:
	    assertEquals("22:03", time2.toString());
	    assertEquals("06:00", time4.toString());
	    assertEquals("01:51", time5.toString());
		
	    
		
	    // AppointmentCalendar methods:
	   
	    AppointmentCalendar calendar = new AppointmentCalendar();
	    
	    calendar.add(appt1); // Walk 2021/9/14 6:00 7:00
	    calendar.add(appt2); // Lunch 2021/09/14 12:00 13:00
	    calendar.add(appt3); // Take Robert for a walk 2021/09/10 3:00 6:45
	    calendar.add(appt5); // Take Robert for a walk 2021/9/11 3:00 6:45
	    calendar.add(appt6); // Take a nap 2021/9/11 3:00 6:45
		Appointment appt7 = new Appointment("Dentist 2008/02/29 10:00 11:00"); // (Feb 29 in leap year)
		Appointment appt8 = new Appointment("Afternoon tea time 2020/09/14 3:00 5:05");
		Appointment appt9 = new Appointment("Meet friend for coffee 2021/08/14 9:00 10:30");
		Appointment appt10 = new Appointment("ICS 45J 2021/09/14 12:30 1:50");
		calendar.add(appt7);
		calendar.add(appt8);
		calendar.add(appt9);
		calendar.add(appt10);
		
		// AppointmentCalendar.getAppointmentsForDay:
		List<Appointment> apptsExpected = Arrays.asList(
				appt1, 
				appt2,
				appt10
				);
		List<Appointment> apptsActual = calendar.getAppointmentsForDay(new AppointmentDate("2021/09/14"));
		assertTrue(apptsExpected.size() == apptsActual.size() && apptsExpected.containsAll(apptsActual) &&
				apptsActual.containsAll(apptsExpected));
		
		apptsExpected = Arrays.asList(
				appt5,
				appt6
				);
		apptsActual = calendar.getAppointmentsForDay(new AppointmentDate("2021/09/11"));
		assertTrue(apptsExpected.size() == apptsActual.size() && apptsExpected.containsAll(apptsActual) &&
				apptsActual.containsAll(apptsExpected));
		
		apptsExpected = Arrays.asList(
				appt7
				);
		apptsActual = calendar.getAppointmentsForDay(new AppointmentDate("2008/02/29"));
		assertTrue(apptsExpected.size() == apptsActual.size() && apptsExpected.containsAll(apptsActual) &&
				apptsActual.containsAll(apptsExpected));
		
		apptsExpected = Arrays.asList();
		apptsActual = calendar.getAppointmentsForDay(new AppointmentDate("2019/09/11"));
		assertTrue(apptsExpected.size() == apptsActual.size() && apptsExpected.containsAll(apptsActual) &&
				apptsActual.containsAll(apptsExpected));
		
		
		// AppointmentCalendar.cancel:
		calendar.cancel(appt2);
		apptsExpected = Arrays.asList(
				appt1, 
				appt10
				);
		apptsActual = calendar.getAppointmentsForDay(new AppointmentDate("2021/09/14"));
		assertTrue(apptsExpected.size() == apptsActual.size() && apptsExpected.containsAll(apptsActual) &&
				apptsActual.containsAll(apptsExpected));
		
		calendar.cancel(appt10);
		apptsExpected = Arrays.asList(
				appt1
				);
		apptsActual = calendar.getAppointmentsForDay(new AppointmentDate("2021/09/14"));
		assertTrue(apptsExpected.size() == apptsActual.size() && apptsExpected.containsAll(apptsActual) &&
				apptsActual.containsAll(apptsExpected));
		
		calendar.cancel(appt5);
		apptsExpected = Arrays.asList(
				appt6
				);
		apptsActual = calendar.getAppointmentsForDay(new AppointmentDate("2021/09/11"));
		assertTrue(apptsExpected.size() == apptsActual.size() && apptsExpected.containsAll(apptsActual) &&
				apptsActual.containsAll(apptsExpected));
		
		calendar.cancel(appt7);
		apptsExpected = Arrays.asList();
		apptsActual = calendar.getAppointmentsForDay(new AppointmentDate("2008/02/29"));
		assertTrue(apptsExpected.size() == apptsActual.size() && apptsExpected.containsAll(apptsActual) &&
				apptsActual.containsAll(apptsExpected));
		
	}
}
