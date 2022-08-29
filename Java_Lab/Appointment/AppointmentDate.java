package labs.lab6;

/**
 * Represents a date for an appointment
 */
public class AppointmentDate {

	// ADD YOUR INSTANCE VARIABLES HERE
	int year;
	int month;
	int day;

	/**
	 * 
	 * @param s	the string from which to construct the AppointmentDate object
	 * 
	 * Expected format: YYYY/MM/DD
	 * 
	 * @throws RuntimeException with the message "Invalid date" if invalid input 
	 * format or date doesn't exist
	 */
	public AppointmentDate(String d) throws RuntimeException {
		// FILL IN
		String date[] = d.split("/");
		/*try {*/
			if(date.length != 3) {
				throw new RuntimeException("Invalid date");
			}
			if(date[0].length() != 4 || date[1].length() > 2 || date[2].length() > 2) {
				throw new RuntimeException("Invalid date");
			}
			if(Integer.valueOf(date[0]) < 1000) {
				throw new RuntimeException("Invalid date");
			}
			if(Integer.valueOf(date[1]) == 0 || Integer.valueOf(date[1]) > 12) {
				throw new RuntimeException("Invalid date");
			}
			if(Integer.valueOf(date[2]) == 0 || Integer.valueOf(date[2]) > 31) {
				throw new RuntimeException("Invalid date");
			}
			if(Integer.valueOf(date[1]) == 2) {
				if(Integer.valueOf(date[0]) % 4 == 0) {
					if(Integer.valueOf(date[2]) > 29) {
						throw new RuntimeException("Invalid date");
					}
				}
				else {
					if(Integer.valueOf(date[2]) > 28) {
						throw new RuntimeException("Invalid date");
					}
				}
			}
			else if(Integer.valueOf(date[1]) == 4 || Integer.valueOf(date[1]) == 6 || 
					Integer.valueOf(date[1]) == 9 || Integer.valueOf(date[1]) == 11) {
				if(Integer.valueOf(date[2]) > 30) {
					throw new RuntimeException("Invalid date");
				}
			}
			year = Integer.valueOf(date[0]);
			month = Integer.valueOf(date[1]);
			day = Integer.valueOf(date[2]);
		/*}
		catch(RuntimeException exception) {
			System.out.print("Invalid input");
		}*/
	}


	/**
	 * Determines if dates are equal.
	 * 
	 * @param the other date
	 * @return true if the dates are equal, false otherwise
	 */
	public boolean equals(Object other) {
		if(other instanceof AppointmentDate) {
			AppointmentDate date = (AppointmentDate) other;
			return year == date.year && month == date.month && day == date.day;
		}
		return false; // FIX ME
	}


	/**
	 * Prints a string representation of the date in the format YYYY/MM/DD
	 * 
	 * @return the date
	 */
	public String toString() {
		String return_string = String.valueOf(year) + "/";
		if(month < 10) {
			return_string += "0" + String.valueOf(month) + "/";
		}
		else {
			return_string += String.valueOf(month) + "/";
		}
		if(day < 10) {
			return_string += "0" + String.valueOf(day);
		}
		else {
			return_string += String.valueOf(day);
		}
		return return_string; // FIX ME
	}
}
