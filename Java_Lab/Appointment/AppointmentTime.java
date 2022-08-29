package labs.lab6;

/**
 * Represents a time for an appointment
 */
public class AppointmentTime {

	// ADD YOUR INSTANCE VARIABLES EHRE
	int hour;
	int minutes;

	/**
	 * 
	 * @param t	the string from which to construct the AppointmentTime object
	 * 
	 * Expected format: HH:MM or H:MM
	 * 
	 * @throws RuntimeException with the message "Invalid time" if invalid input format 
	 * or time doesn't exist
	 */
	public AppointmentTime(String t) throws RuntimeException {
		// FILL IN
		/*try {*/
			if(t.length() == 4) {
				//System.out.println("e");
				if(t.charAt(1) != ':') {
					//System.out.println("b");
					throw new RuntimeException("Invalid time");
				}
				try {
					if(Character.getNumericValue(t.charAt(0)) == 0) {
						throw new RuntimeException("Invalid time");
					}
					if(Integer.valueOf(t.substring(2, 4)) > 59 || Integer.valueOf(t.substring(2, 4)) < 0) {
						throw new RuntimeException("Invalid time");
					}
					hour = Character.getNumericValue(t.charAt(0));
					minutes = Integer.valueOf(t.substring(2, 4));
				}
				catch(NumberFormatException exception) {
					throw new RuntimeException("Invalid time");
				}
			}
			else if(t.length() == 5) {
				if(t.charAt(2) != ':') {
					throw new RuntimeException("Invalid time");
				}
				try {
					if(t.substring(0, 2).equals("00")) {
						hour = 0;
					}
					else {
						//System.out.println("daweobew");
						if(Integer.valueOf(t.substring(0, 2)) < 0 || Integer.valueOf(t.substring(0, 2)) > 23) {
							//System.out.println("a");
							throw new RuntimeException("Invalid time");
						}
					}
					if(t.substring(3,5).equals("00")) {
						//System.out.println("awefw");
						minutes = 0;
					}
					else {
						if(Integer.valueOf(t.substring(3, 5)) > 59 || Integer.valueOf(t.substring(3, 5)) < 0) {
							//System.out.println("b");
							throw new RuntimeException("Invalid time");
						}
					}
					hour = Integer.valueOf(t.substring(0, 2));
					minutes = Integer.valueOf(t.substring(3, 5));
				}
				catch(NumberFormatException exception) {
					//System.out.println("c");
					throw new RuntimeException("Invalid time");
				}
			}
			else {
				throw new RuntimeException("Invalid time");
			}
		/*}
		catch(RuntimeException exception) {
			System.out.println("Invalid time");
		}*/
	}


	/**
	 * Determines if the appointment times are equal.
	 * 
	 * @param other the other time
	 * @return true if the appointment times are equal, false otherwise
	 */
	public boolean equals(Object other) {
		if(other instanceof AppointmentTime) {
			AppointmentTime time = (AppointmentTime) other;
			return hour == time.hour && minutes == time.minutes;// FIX ME
		}
		return false;
	}


	/**
	 * Prints a string representation of the time in the format HH:MM
	 * 
	 * @return the time
	 */
	public String toString() {
		//System.out.println(String.valueOf(hour));
		String return_string = "";
		if(hour < 10) {
			return_string += "0" + String.valueOf(hour);
		}
		else {
			return_string += String.valueOf(hour);
		}
		return_string += ":";
		if(minutes < 10) {
			return_string += "0" + String.valueOf(minutes);
		}
		else {
			return_string += String.valueOf(minutes);
		}
		return return_string; // FIX ME
	}

}
