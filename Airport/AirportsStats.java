package labs.lab10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * A class for performing various analyses on a set of airport data
 *
 */
public class AirportsStats {

	/**
	 * Given a Stream of Airports, return the number of airports in the Stream
	 */
	public static long problem1_countAirports(Stream<Airport> airports) {
		long count = airports.count();
		return count; // FIX ME
	}
	
	
	/**
	 * Given a Stream of Airports, return the number of different countries represented in the stream
	 */
	public static long problem2_countCountries(Stream<Airport> airports) {
		long count = airports.map(airport -> airport.getCountry())
				.distinct()
				.count();
		return count; // FIX ME
	}
	
	
	/**
	 * Given a Stream of airports, return the airport at the lowest altitude
	 * (use min method of Stream class)
	 */
	public static Optional<Airport> problem3_getAirportAtLowestAltitude(Stream<Airport> airports) {
		Optional<Airport> result = airports.min((a, b) -> (int)(a.getAltitude() - b.getAltitude()));
		if(result.isPresent()) {
			return result;
		}
		else {
			return Optional.empty(); // FIX ME
		}
	}
	
	
	/**
	 * Given a Stream of Airports and the name of a city, return a list of airports in that 
	 * city, sorted in ascending lexicographic order by airport name
	 */
	public static List<Airport> problem4_getAirportsInCity(Stream<Airport> airports, String city) {
		List<Airport> result = airports.filter(airport -> airport.getCity().equals(city))
				.sorted((a, b) -> a.getName().compareTo(b.getName()))
				.collect(Collectors.toList());
		return result; // FIX ME
	}
	
	
	/**
	 * Given a Stream of airports, a set of coordinates, and an int numMiles, return a String that 
	 * contains the names of all airports within numMiles from those coordinates, ordered lexicographically,
	 * each name separated by ", "
	 * 
	 * Use the Coordinates.distance method to calculate distance, but remember to convert between miles and meters
	 */
	public static String problem5_getAllAirportsWithinNumMiles(Stream<Airport> airports, Coordinates coordinates, int numMiles) {
		String return_string = "";
		List<String> names = airports
				.filter(airport -> (Coordinates.distance(coordinates.latitude, coordinates.longitude, 
						airport.getCoordinates().latitude, airport.getCoordinates().longitude) / 1609.34) <= numMiles)
				.map(airport -> airport.getName())
				.sorted()
				.collect(Collectors.toList());
		//System.out.println(names.size());
		for(int i = 0 ; i < names.size() ; i++) {
			if(i != (names.size() - 1)) {
				return_string += names.get(i) + ", ";
			}
			else {
				return_string += names.get(i);
			}
		}
		return return_string; // FIX ME
	}
	
	
	/**
	 * Given a Stream of airports and an integer n, return a list of the names of the top n countries with the most airports,
	 * sorted in descending order by number of airports.
	 * 
	 * If there are < n countries represented in the stream, return them all, sorted in descending order
	 * by number of airports.
	 */
	public static List<String> problem6_getTopNcountriesWithMostAirports(Stream<Airport> airports, int n) {
		ArrayList<String> returnList = new ArrayList<String>();
		Map<String, Long> map = airports.collect(Collectors.groupingBy(airport -> airport.getCountry(), Collectors.counting()));
		Map<Long, String> map2 = new TreeMap<Long, String>();
		for(String country : map.keySet()) {
			map2.put(map.get(country), country);
		}
		for(int i = 0 ; i < n ; i++) {
			if(map2.size() == 0) {
				break;
			}
			returnList.add(((TreeMap<Long, String>) map2).lastEntry().getValue());
			map2.remove(((TreeMap<Long, String>)map2).lastEntry().getKey());
		}
		return returnList; // FIX ME
	}
	
	
	/**
	 * Given a Stream of airports and an airport ID, return the airport closest to the airport
	 * with that ID.
	 * 
	 * Use the Coordinates.distance method to calculate distance between airports, and the Stream.min
	 * method to find the airport with the minimum distance away.
	 * 
	 * If the airport with the given ID is not found, or the stream is empty, return an empty Optional<Airport>
	 * 
	 */
	
	Comparator<Integer> comparator = new Comparator<Integer>() {
        
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    };
    
	public static Optional<Airport> problem7_getClosestAirport(Stream<Airport> airports, int airportID) {
		/*int temp;
		Optional<Airport> result = airports.min((Airport a, Airport b) -> {
				if(a.getAirportID() != airportID && b.getAirportID() == airportID) {
					Coordinates.distance(a.getCoordinates().latitude, a.getCoordinates().longitude, 
					b.getCoordinates().latitude, b.getCoordinates().longitude);
				}
			}
		);
		if(result.isPresent()) {
			return result;
		}
		else {
			return Optional.empty();
		}*/
		List<Airport> list = airports.collect(Collectors.toList());
		Map<Double, Airport> map = new TreeMap<Double, Airport>();
		Coordinates coordinates = null;
		Optional<Airport> result;
		if(list.size() <= 1) {
			return Optional.empty();
		}
		for(int i = 0 ; i < list.size() ; i++) {
			if(list.get(i).getAirportID() == airportID) {
				coordinates = list.get(i).getCoordinates();
				break;
			}
		}
		if(coordinates == null) {
			return Optional.empty();
		}
		for(int i = 0 ; i < list.size() ; i++) {
			if(list.get(i).getAirportID() != airportID) {
				double distance = Coordinates.distance(list.get(i).getCoordinates().latitude, list.get(i).getCoordinates().longitude,
						coordinates.latitude, coordinates.longitude);
				map.put(distance, list.get(i));
			}
		}
		result = Optional.of(((TreeMap<Double, Airport>) map).firstEntry().getValue());
		return result;
	}
	
	
	/**
	 * Given a Stream of airports, return a Map<Double, Long> that maps altitudes to the number
	 * of airports at that altitude, but only include in the map altitudes that have at least 2 
	 * airports at that altitude.
	 * 
	 * If the stream is empty, return an empty map.
	 */
	public static Map<Double, Long> problem8_countAirportsByAltitude(Stream<Airport> airports) {
		Map<Double, Long> map = airports.collect(Collectors.groupingBy(airport -> airport.getAltitude(), Collectors.counting()));
		ArrayList<Double> array = new ArrayList<Double>();
		if(map.size() == 0) {
			return new HashMap<Double, Long>();
		}
		else {
			for(Double altitude : map.keySet()){
				if(map.get(altitude) < 2) {
					array.add(altitude);
				}
			}
			for(int i = 0 ; i < array.size() ; i++) {
				map.remove((array.get(i)));
			}
			return map;
		}
	}
	
	
	/**
	 * Given a Stream of airports, return the average number of airports in each country,
	 * or 0 if the stream is empty
	 */
	public static double problem9_averageNumAirportsPerCountry(Stream<Airport> airports) {
		Map<String, Long> map = airports.collect(Collectors.groupingBy(airport -> airport.getCountry(), Collectors.counting()));
		double sum = 0;
		if(map.size() == 0) {
			return 0;
		}
		else {
			for(String country : map.keySet()) {
				sum += map.get(country);
			}
			sum /= map.size();
			return sum; // FIX ME
		}
	}
	
	
	/**
	 * Given a Stream of airports, return the percentage of airport names that contain the name
	 * of the city they are in (case-insensitive). If a city name is blank, consider the airport name 
	 * to NOT contain it. 
	 * 
	 * If the stream is empty, return 0.
	 */
	public static double problem10_percentAirportsWithCityName(Stream<Airport> airports) {
		List<Boolean> list = airports.map(airport -> airport.getName().toLowerCase().contains(airport.getCity().toLowerCase()) && 
				!airport.getCity().equals(""))
				.collect(Collectors.toList());
		//System.out.println(list.size());
		int size = list.size();
		float contains = 0;
		for(int i = 0 ; i < size ; i++) {
			if(list.get(i) == true) {
				contains += 1;
			}
		}
		if(size != 0) {
			float percentage = (contains / size) * 100;
			return percentage;
		}
		else {
			return 0;
		}
	}
	
	public static void main(String[] args) {
		List<Airport> airportsList3 = AirportsReader.readAirports("res/airports3.dat");
		System.out.println(
		problem10_percentAirportsWithCityName(airportsList3.stream())); // returns 62.309425957841704
		// the above returns map: {0.0=6, 4150.0=4}
		 
	}
}