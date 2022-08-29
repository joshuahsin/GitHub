package labs.lab6;

import java.util.Scanner;
import java.util.List;

/**
 * A menu for the appointment calendar system.
 */
public class AppointmentMenu {

	private Scanner in;
	private AppointmentCalendar calendar;

	public AppointmentMenu() {
		in = new Scanner(System.in);
		calendar = new AppointmentCalendar();
	}


	/**
	 * Runs the system.
	 */
	public void run() {
		boolean more = true;

		while (more) {
			System.out.print("A)dd  C)ancel  S)how  Q)uit: ");
			String command = in.nextLine().toUpperCase().trim();

			if (command.equals("A")) {
				boolean done = false;
				while (!done) {
					try {
						System.out.print("Enter appointment info in format: Description Date(YYYY/MM/DD) StartTime EndTime: ");
						String line = in.nextLine();
						Appointment a = new Appointment(line);
						calendar.add(a);
						System.out.println("Appointment added");
						done = true;
					} catch (RuntimeException e) {
						System.out.println(e.getMessage() + ". Try again.");
					}
				}
			} else if (command.equals("C")) {
				boolean done = false;
				while (!done) {
					try {
						System.out.print("Enter appointment date to cancel (YYYY/MM/DD): ");
						String line = in.nextLine();
						AppointmentDate day = new AppointmentDate(line);
						Appointment a = getChoice(calendar.getAppointmentsForDay(day));
						if (a != null) {
							calendar.cancel(a);
							System.out.println("Appointment cancelled");
						}
						else {
							System.out.println("No appointments on that date");
						}
						done = true;
					} catch (RuntimeException e) {
						System.out.println(e.getMessage() + ". Try again.");
					}
				}
			} else if (command.equals("S")) {
				boolean done = false;
				while (!done) {
					try {
						System.out.print("Enter appointment date to show (YYYY/MM/DD): ");
						String line = in.nextLine();
						AppointmentDate day = new AppointmentDate(line);
						List<Appointment> appointments = calendar.getAppointmentsForDay(day);
						if (appointments.size() > 0) {
							for (Appointment appt : appointments) {
								System.out.println(appt);
							}	
						}
						else {
							System.out.println("No appointments on that date");
						}
						done = true;
					} catch (RuntimeException e) {
						System.out.println(e.getMessage() + ". Try again.");
					}
				}

			} else if (command.equals("Q")) {
				more = false;
			}
		}
	}


	private Appointment getChoice(List<Appointment> choices) {
		if (choices.size() == 0)
			return null;
		System.out.println("Enter the letter of the appointment you want to cancel: ");
		while (true) {
			char c = 'A';
			for (Appointment choice : choices) {
				System.out.println(c + ") " + choice);
				c++;
			}
			String input = in.nextLine();
			int n = input.toUpperCase().charAt(0) - 'A';
			if (0 <= n && n < choices.size()) {
				return choices.get(n);
			}
		}
	}


	public static void main(String[] args) {
		new AppointmentMenu().run();
	}

}
