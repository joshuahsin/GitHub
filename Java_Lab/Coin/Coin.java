package labs.lab6;

/**
 * A coin with a monetary value.
 */
public class Coin {

	// ADD YOUR INSTANCE VARIABLES HERE
	String name;
	double value;

	/**
	 * Constructs a coin.
	 * 
	 * @param value the monetary value of the coin
	 * @param name  the name of the coin
	 */
	public Coin(String name, double value) {
		// FILL IN
		this.name = name;
		this.value = value;
	}


	/**
	 * Gets the coin value.
	 * 
	 * @return the value
	 */
	public double getValue() {
		return value; // FIX ME
	}


	/**
	 * Gets the coin name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name; // FIX ME
	}
}