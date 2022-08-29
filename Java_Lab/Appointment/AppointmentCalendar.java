package labs.lab6;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a calendar that manages appointments
 */
public class AppointmentCalendar {

	// ADD YOUR INSTANCE VARIABLES EHRE
	ArrayList<Appointment> AppointmentList;

	public AppointmentCalendar() {
		// FILL IN
		AppointmentList = new ArrayList<Appointment>();
	}


	/**
	 * Adds an appointment to the calendar.
	 * 
	 * @param a the appointment to add
	 */
	public void add(Appointment a) {
		// FILL IN
		AppointmentList.add(a);
	}


	/**
	 * Cancels (removes) an appointment from the calendar.
	 * 
	 * @param a the appointment to cancel
	 */
	public void cancel(Appointment a) {
		// FILL IN
		for(int i = 0 ; i < AppointmentList.size() ; i++) {
			if(AppointmentList.get(i).equals(a)) {
				AppointmentList.remove(i);
				break;
			}
		}
	}


	/**
	 * Gets all appointments for a certain date.
	 * 
	 * @param d the date
	 * @return the appointments for that day
	 */
	public List<Appointment> getAppointmentsForDay(AppointmentDate d) {
		ArrayList<Appointment> DailyAppointments = new ArrayList<Appointment>();
		for(int i = 0 ; i < AppointmentList.size() ; i++) {
			if(AppointmentList.get(i).fallsOn(d)) {
				DailyAppointments.add(AppointmentList.get(i));
			}
		}
		return DailyAppointments; // FIX ME
	}
}
