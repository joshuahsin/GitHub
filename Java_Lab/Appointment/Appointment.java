package labs.lab6;

/**
 * Represents an appointment. An appointment An appointment consists of a description, 
 * the date, starting time, and ending time; for example:
 * 
 * Dentist 2019/10/1 17:30 18:30
 * 45J Class 2019/10/02 8:30 10:00
 */
public class Appointment {

	private String description;
	private AppointmentDate day;
	private AppointmentTime startTime;
	private AppointmentTime endTime;


	/**
	 * 
	 * @param s	the string from which to construct the Appointment object
	 * 
	 * Expected format: Description Date StartTime EndTime
	 * 
	 * Date in YYYY/MM/DD format
	 * Times in military format (no AM/PM)
	 * Assume description will never be empty, nor consist of only whitespace
	 * Assume start time time will always come before end time
	 * 
	 * @throws RuntimeException if invalid input format
	 */
	public Appointment(String s) throws RuntimeException {
		// FILL IN
		description = "";
		String appointment[] = s.split(" ");
		for(int i = 0 ; i < appointment.length ; i++) {
			if(appointment[i].equals("")) {
				throw new RuntimeException();
			}
		}
		day = new AppointmentDate(appointment[appointment.length - 3]);
		//System.out.println(appointment[appointment.length - 2]);
		startTime = new AppointmentTime(appointment[appointment.length - 2]);
		endTime = new AppointmentTime(appointment[appointment.length - 1]);
		if(appointment.length == 4) {
			description += appointment[0];
		}
		else {
			for(int i = 0 ; i < (appointment.length - 3); i++) {
				if(i == appointment.length - 4) {
					description += appointment[i];
				}
				else {
					description += appointment[i] + " ";
				}
			}
		}
	}


	/**
	 * Determines if this appointment is the same as another appointment.
	 * 
	 * @param other the other appointment
	 * @return true if the appointments are equal, false otherwise
	 */
	public boolean equals(Object other) {
		if(other instanceof Appointment) {
			Appointment otherAppointment = (Appointment) other;
			return description.equals(otherAppointment.description) && day.equals(otherAppointment.day) 
					&& startTime.equals(otherAppointment.startTime) && endTime.equals(otherAppointment.endTime);
		}
		return false; // FIX ME
	}


	/**
	 * Determines if an appointment falls on a certain day.
	 * 
	 * @param d the appointment date
	 * @return true if the appointment date falls on the date, false otherwise
	 */
	public boolean fallsOn(AppointmentDate d) {
		return day.equals(d);
	}


	/**
	 * Return a string representation in the format:
	 * Description Date StartTime EndTime
	 */
	public String toString() {
		String return_string = description + " " + day + " " + startTime + " " + endTime;
		return return_string; // FIX ME
	}

}
