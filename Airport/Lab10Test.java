package labs.lab10;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Lab10Test {
	
	private final double EPSILON = 1e-3;

	List<Airport> airportsList;
	List<Airport> airportsList1;
	List<Airport> airportsList2;
	List<Airport> airportsList3;
	List<Airport> airportsList4;
	List<Airport> airportsList5;
	List<Airport> airportsList6;
	
	//This method will be executed before all the other methods.
	@BeforeEach
	void before() {
		String path = "res/"; //<- Assign the path of the .dat files to this variable. Do not include the file name, 
								// it will be added below.
		airportsList = AirportsReader.readAirports(path + "airports.dat");
		airportsList1 = AirportsReader.readAirports(path + "airports1.dat");
		airportsList2 = AirportsReader.readAirports(path + "airports2.dat");
		airportsList3 = AirportsReader.readAirports(path + "airports3.dat");
		airportsList4 = AirportsReader.readAirports(path + "airports4.dat");
		airportsList5 = AirportsReader.readAirports(path + "airports5.dat");
		airportsList6 = AirportsReader.readAirports(path + "airports6.dat");	
	}

	
	@Test
	void testProblem1_countAirports() {
		assertEquals(7543, AirportsStats.problem1_countAirports(airportsList.stream()));
		assertEquals(10, AirportsStats.problem1_countAirports(airportsList1.stream()));
		assertEquals(24, AirportsStats.problem1_countAirports(airportsList2.stream()));
		assertEquals(0, AirportsStats.problem1_countAirports(airportsList3.stream()));
	}
	
	
	@Test
	void testProblem2_countCountries() {
		assertEquals(2, AirportsStats.problem2_countCountries(airportsList1.stream()));
		assertEquals(11, AirportsStats.problem2_countCountries(airportsList2.stream()));
		assertEquals(237, AirportsStats.problem2_countCountries(airportsList.stream()));
		assertEquals(0, AirportsStats.problem2_countCountries(airportsList3.stream()));
	}
	
	
	@Test
	void testProblem3_getAirportAtLowestAltitude() {
		Optional<Airport> lowest = AirportsStats.problem3_getAirportAtLowestAltitude(airportsList.stream());
		assertTrue(lowest.isPresent());
		if (lowest.isPresent()) {
			Airport result = lowest.get();
			assertEquals(1600, result.getAirportID());
		}
		
		lowest = AirportsStats.problem3_getAirportAtLowestAltitude(airportsList1.stream());
		assertTrue(lowest.isPresent());
		if (lowest.isPresent()) {
			Airport result = lowest.get();
			assertEquals(6, result.getAirportID());
		}
		
		lowest = AirportsStats.problem3_getAirportAtLowestAltitude(airportsList2.stream());
		assertTrue(lowest.isPresent());
		if (lowest.isPresent()) {
			Airport result = lowest.get();
			assertEquals(13719, result.getAirportID());
		}
		
		lowest = AirportsStats.problem3_getAirportAtLowestAltitude(airportsList3.stream());
		assertFalse(lowest.isPresent());
		
		lowest = AirportsStats.problem3_getAirportAtLowestAltitude(airportsList4.stream());
		assertTrue(lowest.isPresent());
		if (lowest.isPresent()) {
			Airport result = lowest.get();
			assertEquals(6, result.getAirportID());
		}
		
		lowest = AirportsStats.problem3_getAirportAtLowestAltitude(airportsList5.stream());
		assertTrue(lowest.isPresent());
		if (lowest.isPresent()) {
			Airport result = lowest.get();
			assertEquals(2, result.getAirportID());
		}
	}
	
	
	@Test
	void testProblem4_getAirportsInCity() {
		assertTrue(AirportsStats.problem4_getAirportsInCity(airportsList1.stream(), "Los Angeles").isEmpty());
		
		List<Airport> result = AirportsStats.problem4_getAirportsInCity(airportsList1.stream(), "Wewak");
		assertTrue(result.size() == 1 &&
				result.get(0).getAirportID() == 6);
		
		assertTrue(AirportsStats.problem4_getAirportsInCity(airportsList2.stream(), "Los Angeles").isEmpty());
		
		result = AirportsStats.problem4_getAirportsInCity(airportsList2.stream(), "Beijing");
		assertTrue(result.size() == 1 &&
				result.get(0).getAirportID() == 13726);
		
		result = AirportsStats.problem4_getAirportsInCity(airportsList2.stream(), "Goroka");
		assertTrue(result.size() == 1 &&
				result.get(0).getAirportID() == 1);
		
		result = AirportsStats.problem4_getAirportsInCity(airportsList2.stream(), "Godthaab");
		assertTrue(result.size() == 1 &&
				result.get(0).getAirportID() == 8);
		
		result = AirportsStats.problem4_getAirportsInCity(airportsList.stream(), "Beijing");
		assertTrue(result.size() == 3 &&
				result.get(0).getAirportID() == 3364 &&
				result.get(1).getAirportID() == 6341 &&
				result.get(2).getAirportID() == 13726);
		
		result = AirportsStats.problem4_getAirportsInCity(airportsList.stream(), "Chicago");
		assertTrue(result.size() == 4 &&
				result.get(0).getAirportID() == 8593 &&
				result.get(1).getAirportID() == 3747 &&
				result.get(2).getAirportID() == 3830 &&
				result.get(3).getAirportID() == 3818);
		
		result = AirportsStats.problem4_getAirportsInCity(airportsList.stream(), "Los Angeles");
		assertTrue(result.size() == 3 &&
				result.get(0).getAirportID() == 3484 &&
				result.get(1).getAirportID() == 2654 &&
				result.get(2).getAirportID() == 8220);
		
		result = AirportsStats.problem4_getAirportsInCity(airportsList.stream(), "New York");
		assertTrue(result.size() == 4 &&
				result.get(0).getAirportID() == 7767 &&
				result.get(1).getAirportID() == 3797 &&
				result.get(2).getAirportID() == 3697 &&
				result.get(3).getAirportID() == 8123);
		
		result = AirportsStats.problem4_getAirportsInCity(airportsList.stream(), "Paris");
		assertTrue(result.size() == 4 &&
				result.get(0).getAirportID() == 1382 &&
				result.get(1).getAirportID() == 11095 &&
				result.get(2).getAirportID() == 1380 &&
				result.get(3).getAirportID() == 1386);
		
		result = AirportsStats.problem4_getAirportsInCity(airportsList.stream(), "Wewak");
		assertTrue(result.size() == 1 &&
				result.get(0).getAirportID() == 6);
		
		assertTrue(AirportsStats.problem4_getAirportsInCity(airportsList3.stream(), "Wewak").isEmpty());
	}
	
	
	@Test
	void testProblem5_getAllAirportsWithinNumMiles() {
		assertEquals("Goroka Airport, Madang Airport, Mount Hagen Kagamuga Airport, Nadzab Airport",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList1.stream(),
						new Coordinates(-6.081689834590001, 145.391998291), 
						100));
		assertEquals("Goroka Airport, Madang Airport, Mount Hagen Kagamuga Airport, Nadzab Airport",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList1.stream(),
						new Coordinates(-6.081689834590001, 145.391998291), 
						200));
		assertEquals("Goroka Airport, Madang Airport, Mount Hagen Kagamuga Airport, Nadzab Airport, Wewak International Airport",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList1.stream(),
						new Coordinates(-6.081689834590001, 145.391998291), 
						250));
		assertEquals("Goroka Airport, Madang Airport, Mount Hagen Kagamuga Airport, Nadzab Airport, Port Moresby Jacksons International Airport, Wewak International Airport",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList1.stream(),
						new Coordinates(-6.081689834590001, 145.391998291), 
						300));
		assertEquals("",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList1.stream(), 
						new Coordinates(90.0, 45.0), 
						100));
		assertEquals("Thule Air Base",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList1.stream(), 
						new Coordinates(90.0, 45.0), 
						1000));
		assertEquals("",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList3.stream(), 
						new Coordinates(90.0, 45.0), 
						1000));
		assertEquals("John Wayne Airport-Orange County Airport",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList.stream(), 
						new Coordinates(33.6405, -117.8443), // UCI's coordinates
						5));
		assertEquals("El Toro Marine Corps Air Station, John Wayne Airport-Orange County Airport",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList.stream(), 
						new Coordinates(33.6405, -117.8443), // UCI's coordinates
						10));
		assertEquals("El Toro Marine Corps Air Station, John Wayne Airport-Orange County Airport",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList.stream(), 
						new Coordinates(33.6405, -117.8443), // UCI's coordinates
						15));
		assertEquals("El Toro Marine Corps Air Station, Fullerton Municipal Airport, John Wayne Airport-Orange County Airport, Los Alamitos Army Air Field",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList.stream(), 
						new Coordinates(33.6405, -117.8443), // UCI's coordinates
						20));
		assertEquals("El Toro Marine Corps Air Station, Fullerton Municipal Airport, John Wayne Airport-Orange County Airport, Long Beach /Daugherty Field/ Airport, Los Alamitos Army Air Field",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList.stream(), 
						new Coordinates(33.6405, -117.8443), // UCI's coordinates
						25));
		assertEquals("Banning Municipal Airport, Bermuda Dunes Airport, Bob Hope Airport, Brackett Field, Brown Field Municipal Airport, Cable Airport, Camarillo Airport, Camp Pendleton MCAS (Munn Field) Airport, Catalina Airport, Chino Airport, Edwards Air Force Base, El Toro Marine Corps Air Station, Flabob Airport, French Valley Airport, Fullerton Municipal Airport, General Abelardo L. Rodrguez International Airport, General WM J Fox Airfield, Gillespie Field, Jack Northrop Field Hawthorne Municipal Airport, Jacqueline Cochran Regional Airport, John Wayne Airport-Orange County Airport, Long Beach /Daugherty Field/ Airport, Los Alamitos Army Air Field, Los Angeles International Airport, March ARB Airport, Mc Clellan-Palomar Airport, Miramar Marine Corps Air Station - Mitscher Field, Mojave Airport, North Island Naval Air Station-Halsey Field, Oceanside Municipal Airport, Ontario International Airport, Oxnard Airport, Palm Springs International Airport, Palmdale Regional/USAF Plant 42 Airport, Point Mugu Naval Air Station (Naval Base Ventura Co), Ramona Airport, Redlands Municipal Airport, Riverside Municipal Airport, San Bernardino International Airport, San Clemente Island Naval Auxiliary Landing Field, San Diego International Airport, San Gabriel Valley Airport, San Nicolas Island Nolf Airport, Santa Monica Municipal Airport, Southern California Logistics Airport, Van Nuys Airport, Whiteman Airport, Zamperini Field",
				AirportsStats.problem5_getAllAirportsWithinNumMiles(airportsList.stream(), 
						new Coordinates(33.6405, -117.8443), // UCI's coordinates																					// location
						100));
	}
	
	
	@Test
	void testProblem6_getTopNcountriesWithMostAirports() {
		List<String> result = AirportsStats.problem6_getTopNcountriesWithMostAirports(airportsList1.stream(), 1);
		assertTrue(result.size() == 1 &&
				result.get(0).equals("Papua New Guinea"));
		
		result = AirportsStats.problem6_getTopNcountriesWithMostAirports(airportsList1.stream(), 2);
		assertTrue(result.size() == 2 &&
				result.get(0).equals("Papua New Guinea") &&
				result.get(1).equals("Greenland"));
		
		result = AirportsStats.problem6_getTopNcountriesWithMostAirports(airportsList1.stream(), 3);
		assertTrue(result.size() == 2 &&
				result.get(0).equals("Papua New Guinea") &&
				result.get(1).equals("Greenland"));
		
		result = AirportsStats.problem6_getTopNcountriesWithMostAirports(airportsList3.stream(), 3);
		assertTrue(result.size() == 0);
		
		result = AirportsStats.problem6_getTopNcountriesWithMostAirports(airportsList.stream(), 1);
		assertTrue(result.size() == 1 &&
				result.get(0).equals("United States"));
		
		result = AirportsStats.problem6_getTopNcountriesWithMostAirports(airportsList.stream(), 2);
		assertTrue(result.size() == 2 &&
				result.get(0).equals("United States") &&
				result.get(1).equals("Canada"));
		
		result = AirportsStats.problem6_getTopNcountriesWithMostAirports(airportsList.stream(), 3);
		assertTrue(result.size() == 3 &&
				result.get(0).equals("United States") &&
				result.get(1).equals("Canada") &&
				result.get(2).equals("Australia"));
		
		result = AirportsStats.problem6_getTopNcountriesWithMostAirports(airportsList.stream(), 4);
		assertTrue(result.size() == 4 &&
				result.get(0).equals("United States") &&
				result.get(1).equals("Canada") &&
				result.get(2).equals("Australia") &&
				result.get(3).equals("Brazil"));
		
		result = AirportsStats.problem6_getTopNcountriesWithMostAirports(airportsList.stream(), 5);
		assertTrue(result.size() == 5 &&
				result.get(0).equals("United States") &&
				result.get(1).equals("Canada") &&
				result.get(2).equals("Australia") &&
				result.get(3).equals("Brazil") &&
				result.get(4).equals("Russia"));
		
		result = AirportsStats.problem6_getTopNcountriesWithMostAirports(airportsList5.stream(), 1);
		assertTrue(result.size() == 1 &&
				result.get(0).equals("Papua New Guinea"));
		
		result = AirportsStats.problem6_getTopNcountriesWithMostAirports(airportsList5.stream(), 10);
		assertTrue(result.size() == 1 &&
				result.get(0).equals("Papua New Guinea"));
	}
	
	
	@Test 
	void testProblem7_getClosestAirport() {
		Optional<Airport> closest = AirportsStats.problem7_getClosestAirport(airportsList1.stream(), 1);
		assertTrue(closest.isPresent());
		if (closest.isPresent()) {
			Airport result = closest.get();
			assertEquals(2, result.getAirportID());
		}
		
		closest = AirportsStats.problem7_getClosestAirport(airportsList1.stream(), 7);
		assertTrue(closest.isPresent());
		if (closest.isPresent()) {
			Airport result = closest.get();
			assertEquals(8, result.getAirportID());
		}
		
		closest = AirportsStats.problem7_getClosestAirport(airportsList1.stream(), 10);
		assertTrue(closest.isPresent());
		if (closest.isPresent()) {
			Airport result = closest.get();
			assertEquals(9, result.getAirportID());
		}
		
		closest = AirportsStats.problem7_getClosestAirport(airportsList2.stream(), 13719);
		assertTrue(closest.isPresent());
		if (closest.isPresent()) {
			Airport result = closest.get();
			assertEquals(13718, result.getAirportID());
		}
		
		closest = AirportsStats.problem7_getClosestAirport(airportsList2.stream(), 13721);
		assertTrue(closest.isPresent());
		if (closest.isPresent()) {
			Airport result = closest.get();
			assertEquals(13716, result.getAirportID());
		}
		
		closest = AirportsStats.problem7_getClosestAirport(airportsList2.stream(), 13);
		assertFalse(closest.isPresent());
		
		closest = AirportsStats.problem7_getClosestAirport(airportsList4.stream(), 4321);
		assertTrue(closest.isPresent());
		if (closest.isPresent()) {
			Airport result = closest.get();
			assertEquals(7, result.getAirportID());
		}
		
		closest = AirportsStats.problem7_getClosestAirport(airportsList4.stream(), 3747);
		assertTrue(closest.isPresent());
		if (closest.isPresent()) {
			Airport result = closest.get();
			assertEquals(3830, result.getAirportID());
		}
		
		closest = AirportsStats.problem7_getClosestAirport(airportsList4.stream(), 3830);
		assertTrue(closest.isPresent());
		if (closest.isPresent()) {
			Airport result = closest.get();
			assertEquals(3747, result.getAirportID());
		}
		
		closest = AirportsStats.problem7_getClosestAirport(airportsList5.stream(), 2);
		assertFalse(closest.isPresent());
		
		closest = AirportsStats.problem7_getClosestAirport(airportsList3.stream(), 2);
		assertFalse(closest.isPresent());
	}
	
	
	@Test
	void testProblem8_countAirportsByAltitude() {
		assertEquals(0, AirportsStats.problem8_countAirportsByAltitude(airportsList1.stream()).size());
		assertEquals(0, AirportsStats.problem8_countAirportsByAltitude(airportsList3.stream()).size());
		
		Map<Double, Long> result = AirportsStats.problem8_countAirportsByAltitude(airportsList6.stream());
		Set<Double> keys = result.keySet();
		assertTrue(result.size() == 2 &&
				keys.contains(0.0) &&
				keys.contains(4150.0) &&
				!keys.contains(20.0) &&
				!keys.contains(371.0) &&
				!keys.contains(330.0) &&
				result.get(0.0) == 6 &&
				result.get(4150.0) == 4);
		
		result = AirportsStats.problem8_countAirportsByAltitude(airportsList.stream());
		keys = result.keySet();
		assertTrue(result.size() == 1137 &&
				keys.contains(11.0) &&
				keys.contains(489.0) &&
				keys.contains(883.0) &&
				keys.contains(1545.0) &&
				keys.contains(5007.0) &&
				!keys.contains(4320.0) &&
				!keys.contains(757.0) &&
				!keys.contains(255.0) &&
				result.get(250.0) == 9 &&
				result.get(256.0) == 9 &&
				result.get(680.0) == 3 &&
				result.get(6070.0) == 2); 
	}
	
	
	@Test
	void testProblem9_averageNumAirportsPerCountry() {
		assertEquals(5.0, AirportsStats.problem9_averageNumAirportsPerCountry(airportsList1.stream()), EPSILON);
		assertEquals(2.1818, AirportsStats.problem9_averageNumAirportsPerCountry(airportsList2.stream()), EPSILON);
		assertEquals(0, AirportsStats.problem9_averageNumAirportsPerCountry(airportsList3.stream()), EPSILON);
		assertEquals(2.8, AirportsStats.problem9_averageNumAirportsPerCountry(airportsList4.stream()), EPSILON);
		assertEquals(1.0, AirportsStats.problem9_averageNumAirportsPerCountry(airportsList5.stream()), EPSILON);
		assertEquals(1.666667, AirportsStats.problem9_averageNumAirportsPerCountry(airportsList6.stream()), EPSILON);
		assertEquals(31.827004, AirportsStats.problem9_averageNumAirportsPerCountry(airportsList.stream()), EPSILON);
	}
	
	
	@Test
	void testProblem10_percentAirportsWithCityName() {
		assertEquals(80.0, AirportsStats.problem10_percentAirportsWithCityName(airportsList1.stream()), EPSILON);
		assertEquals(58.333333333333336, AirportsStats.problem10_percentAirportsWithCityName(airportsList2.stream()), EPSILON);
		assertEquals(0, AirportsStats.problem10_percentAirportsWithCityName(airportsList3.stream()), EPSILON);
		assertEquals(85.714296, AirportsStats.problem10_percentAirportsWithCityName(airportsList4.stream()), EPSILON);
		assertEquals(100.0, AirportsStats.problem10_percentAirportsWithCityName(airportsList5.stream()), EPSILON);
		assertEquals(60.0, AirportsStats.problem10_percentAirportsWithCityName(airportsList6.stream()), EPSILON);
		assertEquals(62.309425957841704, AirportsStats.problem10_percentAirportsWithCityName(airportsList.stream()), EPSILON);
	}
}
