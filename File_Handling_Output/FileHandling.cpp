
/*
 * FileHandling.cpp
 *
 *  Created on: Nov 21, 2019
 *      Author: Michael Vo, Joshua Hsin, 31082403,13651420
 *      Description: Will define any and all variables and functions declared in your header file
 *      creates the pyramid after asking the user for input and returns it as a string
 */

#include <iostream>
using namespace std;
#include <string>
#include <fstream>
#include "FileHandling.h"


string FileHandling::getPyramid(){
	int height;
	cout << "Enter the height of the pyramid: "<< endl;
	cin >> height;


	string pyramid = "";

	int space, columns;

	pyramid +="";

	for(int i = 1, k = 0,j = 0; i <= height; ++i, k = 0, j = 0) {
		pyramid +=" ";
		for(space = 1, columns = height + 1; space <= height-i; ++space, --columns)
		{
			pyramid +=" ";
		}

		while(k != 2*i-1)
		{
			pyramid +="-";
			++k;
		}

		pyramid += "\n";

		for(space = height; space >= columns; --space) {
			pyramid +=" ";
		}
		while(j != columns) {
			if(j==0) {
				pyramid +="|";
				++j;
			} else {
				pyramid +=" |";
				++j;
		}
		}
		pyramid += "\n";

		k = 0;
		if(i == height) {
			for(space = 1; space == 1; --space) {
				pyramid +=" ";
			}
			while(k != 2*i-1) {
				pyramid +="-";
				++k;
			}
		}

	}
	return pyramid;
}
