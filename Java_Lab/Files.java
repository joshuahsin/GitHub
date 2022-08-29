package labs.lab6;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Files {

	/**
	 * Removes any blank lines at the beginning or end of a file
	 * 
	 * @param fileName	name of the file with blank lines to be trimmed
	 */
	public static void trimBlankLines(String fileName) {
		//Fill In
		try {
			File input = new File(fileName);
			Scanner scan = new Scanner(input);
			String output = "";
			String temp = "";
			boolean beginning = true;
			while(scan.hasNextLine()) {
				temp = scan.nextLine();
				if(beginning) {
					if(!temp.equals("")) {
						output += temp + "\n";
						beginning = false;
					}
				}
				else {
					if(temp.equals("")) {
						String temp2 = "\n";
						while(scan.hasNextLine()) {
							temp = scan.nextLine();
							temp2 += temp + "\n";
							if(!temp.equals("")) {
								output += temp2;
								break;
							}
						}
					}
					else {
						output += temp + "\n";
					}
				}
			}
			scan.close();
			if(!output.equals("")) {
				output = output.substring(0, output.length() - 1);
			}
			PrintWriter out = new PrintWriter(fileName);
			out.print(output);
			out.close();
		}
		catch(FileNotFoundException exception) {
			System.out.print("File: " + fileName + " not found");
		}
	}
	
	
	/**
	 * Reverses the order of lines in a file
	 * 
	 * @param fileName	name of the file to be reversed
	 */
	public static void reverse(String fileName) {
        // FILL IN
		ArrayList<String> lines = new ArrayList<String>();
		try {
			File input = new File(fileName);
			Scanner scan = new Scanner(input);
			String temp;
			while(scan.hasNextLine()) {
				temp = scan.nextLine();
				lines.add(temp + "\n");
			}
			scan.close();
			PrintWriter out = new PrintWriter(fileName);
			for(int i = lines.size() - 1 ; i >= 0 ; i--) { 
				//System.out.println(i);
				out.print(lines.get(i));
			}
			out.close();
			//System.out.println(lines);
		}
		catch(FileNotFoundException exception) {
			System.out.print("File: " + fileName + " not found");
		}
	}
}
