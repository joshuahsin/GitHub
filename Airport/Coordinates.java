package labs.lab10;

/**
 * Represents a set of latitude/longitude coordinates
 */
public class Coordinates {
	public final double latitude;
	public final double longitude;
	
	public Coordinates(double lat, double lon) {
		latitude = lat;
		longitude = lon;
	}
	
	
	@Override
	public String toString() {
		return latitude + "," + longitude;
	}
	
	
	@Override
	public boolean equals(Object otherObject) {
		if (otherObject instanceof Coordinates) {
			Coordinates other = (Coordinates)otherObject;
			return latitude == other.latitude && longitude == other.longitude;
		}
		return false;
	}
	
	
	/**
	 * Code used from: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
	 * 
	 * Calculate distance between two points in latitude and longitude, not taking
	 * into account altitude difference. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point 
	 * 
	 * @returns Distance in Meters
	 */
	public static double distance(double lat1, double lon1, double lat2, double lon2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    return R * c * 1000; // convert to meters
	}
}