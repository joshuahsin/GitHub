package labs.lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class CoinCollection {

	// ADD YOUR INSTANCE VARIABLES HERE
	List<Coin> coin_list = new ArrayList<Coin>();
	
	/**
	 * Constructs a CoinCollection with the data from a file
	 * 
	 * @param fileName	file containing coin collection data
	 */
	public CoinCollection(String fileName) {
		// FILL IN
		Coin tempCoin; 
		try {
			File file = new File(fileName);
			if(!file.exists()) {
				throw new FileNotFoundException();
			}
			Scanner scan = new Scanner(file);
			String temp = "";
			while(scan.hasNextLine()) {
				temp = scan.nextLine();
				String[] temp_string = temp.split(" ");
				Double value;
				try{
					value = Double.valueOf(temp_string[1]);
				}
				catch(NumberFormatException exception) {
					throw new InputMismatchException();
				}
				tempCoin = new Coin(temp_string[0], value);
				coin_list.add(tempCoin);
			}
			scan.close();
		}
		catch(FileNotFoundException exception) {
			System.out.print("File: " + fileName + " not found");
		}
		catch(InputMismatchException exception) {
			System.out.print("Coin value must be of type double");
		}
	}
	
	
	/**
	 * 
	 * @return	the total value of this whole coin collection
	 */
	public double getTotalValue() {
		double total_value = 0;
		for(int i = 0 ; i < coin_list.size() ; i++) {
			total_value += coin_list.get(i).getValue();
		}
		return total_value; // FIX ME
	}
	
	
	/**
	 * 
	 * @param coinName	the name of the type of coin you want to count
	 * @return			the count of that type of coin in the collection
	 */
	public int getCoinCount(String coinName) {
		int coin_count = 0;
		for(int i = 0 ; i < coin_list.size() ; i++) {
			if(coin_list.get(i).getName().equals(coinName)) {
				coin_count += 1;
			}
		}
		return coin_count; // FIX ME
	}
	
	
	/**
	 * 
	 * @return	the total number of coins in the collection
	 */
	public int getTotalCoinCount() {
		return coin_list.size();
	}
}