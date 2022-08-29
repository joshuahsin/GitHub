package labs.lab6;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BabyNameData {

	// ADD YOUR INSTANCE VARIABLES EHRE
	//File file;
	//String fileName;
	List<String> girlNames = new ArrayList<String>();
	List<String> boyNames = new ArrayList<String>();
	List<BabyName> girl_baby = new ArrayList<BabyName>();
	List<BabyName> boy_baby = new ArrayList<BabyName>();
	
	/**
	 * 
	 * @param babyNamesFileName	name of baby name data file from which to load data
	 */
	public BabyNameData(String babyNamesFileName) {
		// FILL IN
		BabyName temp_girl;
		BabyName temp_boy;
		try {
			File file = new File(babyNamesFileName);
			if(!file.exists()) {
				throw new FileNotFoundException();
			}
			Scanner scan = new Scanner(file);
			String temp = "";
			while(scan.hasNextLine()) {
				temp = scan.nextLine();
				String[] temp_string = temp.split("\\s+");
				temp_boy = new BabyName(temp_string[1], Integer.valueOf(temp_string[0]), Integer.valueOf(temp_string[2]), Double.valueOf(temp_string[3]));
				temp_girl = new BabyName(temp_string[4], Integer.valueOf(temp_string[0]), Integer.valueOf(temp_string[5]), Double.valueOf(temp_string[6]));
				girl_baby.add(temp_girl);
				boy_baby.add(temp_boy);
				girlNames.add(temp_string[4]);
				boyNames.add(temp_string[1]);
			}
			scan.close();
		}
		catch(FileNotFoundException exception) {
			System.out.print("File: " + babyNamesFileName + " not found");
		}
	}


	/**
	 * 
	 * @return	all girl names (in a non-specific order)
	 */
	public List<String> getAllGirlNames() {
		return girlNames; // FIX ME
	}


	/**
	 * 
	 * @return	all boy names (in a non-specific order)
	 */
	public List<String> getAllBoyNames() {
		return boyNames; // FIX ME
	}


	/**
	 * 
	 * @return	all names that appear in the data for both boys and girls (in a non-specific order)
	 */
	public List<String> getAllNonGenderSpecificNames() {
		List<String> allNames = new ArrayList<String>();
		for(int i = 0; i < girl_baby.size(); i++) {
			if(boyNames.contains(girl_baby.get(i).getName())) {
				allNames.add(girl_baby.get(i).getName());
			}
		}
		return allNames;
	}
	
	
	/**
	 * 
	 * @param rank	
	 * @return	the girl name at the rank specified
	 * 
	 * If rank is out of the range of data, throw an IllegalArgumentException with the
	 * message "Rank [rank] out of range of data"
	 */
	public String getGirlNameAtRank(int rank) {
		String girlName = "";
		if(rank <= 0 || rank > girl_baby.size()) {
			throw new IllegalArgumentException("Rank " + rank + " out of range of data");
		}
		for(int i = 0 ; i < girl_baby.size() ; i++) {
			if(girl_baby.get(i).getRank() == rank) {
				girlName = girl_baby.get(i).getName();
			}
		}
		return girlName;
	}
	
	
	/**
	 * 
	 * @param rank	
	 * @return	the boy name at the rank specified
	 * 
	 * If rank is out of the range of data, throw an IllegalArgumentException with the
	 * message "Rank [rank] out of range of data"
	 */
	public String getBoyNameAtRank(int rank) {
		String boyName = "";
		if(rank <= 0 || rank > boy_baby.size()) {
			throw new IllegalArgumentException("Rank " + rank + " out of range of data");
		}
		for(int i = 0 ; i < boy_baby.size() ; i++) {
			if(boy_baby.get(i).getRank() == rank) {
				boyName = boy_baby.get(i).getName();
			}
		}
		return boyName;
	}
}
