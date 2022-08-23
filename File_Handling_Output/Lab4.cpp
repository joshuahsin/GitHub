//============================================================================
// Name        : Lab4.cpp
// Author      : Michael Vo, Joshua Hsin, 31082403, 13651420
// Version     :
// Copyright   : Your copyright notice
// Description : Lab4.cpp contains the main program and starts the program, this creates the file and checks if it exists
//============================================================================

#include <iostream>
#include <fstream>
#include <string>
#include "FileHandling.h"
#include <string>
using namespace std;

int main() {

	FileHandling fileHandler;

	string fileName;
	cout << "Enter a file name: " << endl;
	cin >> fileName;


	fstream file_name;

	if(file_name.is_open()){  // checks if the file exists
		file_name.open("src/" + fileName + ".txt", ios::out | ios::in | ios::trunc); // places file in src and overrides file if exists
	} else {
		file_name.open("src/" + fileName + ".txt", ios::out); // creates a new file and places file in src
	}


	file_name << fileHandler.getPyramid() << endl;

	file_name.close();

	return 0;
}
