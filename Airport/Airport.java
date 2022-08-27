package labs.lab10;

/**
 * Represents an Airport
 */
public class Airport implements Comparable<Airport> {

	private int airportID;
	private String name;
	private String city;
	private String country;
	private String iataCode;
	private String icaoCode;
	private Coordinates coordinates;
	private double altitude;

	/**
	 * See https://openflights.org/data.html for what each parameter means
	 * 
	 * @param airportID
	 * @param name
	 * @param city
	 * @param country
	 * @param iataCode
	 * @param icaoCode
	 * @param coordinates
	 * @param altitude
	 */
	public Airport(int airportID, String name, String city, String country, String iataCode, String icaoCode,
			Coordinates coordinates, double altitude) {
		this.airportID = airportID;
		this.name = name;
		this.city = city;
		this.country = country;
		this.iataCode = iataCode;
		this.icaoCode = icaoCode;
		this.coordinates = coordinates;
		this.altitude = altitude;
	}


	public int getAirportID() {
		return airportID;
	}


	public String getName() {
		return name;
	}


	public String getCity() {
		return city;
	}


	public String getCountry() {
		return country;
	}


	public String getIATAcode() {
		return iataCode;
	}


	public String getICAOcode() {
		return icaoCode;
	}


	public Coordinates getCoordinates() {
		return coordinates;
	}


	public double getAltitude() {
		return altitude;
	}


	@Override
	public String toString() {
		return airportID + "," + name + "," + city + "," + country + "," + iataCode + "," + icaoCode + "," + 
				coordinates + "," + altitude;
	}


	@Override
	public boolean equals(Object otherObject) {
		if (otherObject instanceof Airport) {
			Airport other = (Airport) otherObject;
			return airportID == other.airportID &&
					name.equals(other.name) &&
					city.equals(other.city) &&
					country.equals(other.country) &&
					iataCode.equals(other.iataCode) &&
					icaoCode.equals(other.icaoCode) &&
					coordinates.equals(other.coordinates) &&
					altitude == other.altitude;
		}
		return false;
	}
	
	
	/**
	 * compares lexicographically by airport name
	 */
	@Override
	public int compareTo(Airport other) {
		if (name.compareTo(other.name) < 0)
			return -1;
		if (name.compareTo(other.name) > 0)
			return 1;
		return 0;
	}

}
