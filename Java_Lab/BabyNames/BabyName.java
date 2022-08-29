package labs.lab6;

public class BabyName {
	// ADD YOUR INSTANCE VARIABLES HERE
	String name;
	int rank;
	int count;
	double percent;
	
	public BabyName(String name, int rank, int count, double percent) {
		// FILL IN
		this.name = name;
		this.rank = rank;
		this.count = count;
		this.percent = percent;
	}
	
	
	public String getName() {
		return name; // FIX ME
	}
	
	
	public int getRank() {
		return rank; // FIX ME
	}
	
	
	public int getCount() {
		return count; // FIX ME
	}
	
	
	public double getPercent() {
		return percent; // FIX ME
	}
}