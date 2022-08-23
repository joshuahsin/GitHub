/*
 * MovieManagerUI.cpp
 *
 *  Created on: Nov 12, 2019
 *      Author: Michael Vo #31082403, Joshua Hsin #13651420
 */

#ifndef MOVIEMANAGERUI_H_
#define MOVIEMANAGERUI_H_
#include "MovieManagerUI.h"

class MovieManagerUI{
public:
	void printMenu();
	string getCommand();
	void addMovie();
	void rentMovie();
	void discontinueMovie();
	void returnRental();
	string getMovieName();
	string getMovieCode();
	string getRenterFirstName();
	string getRenterLastName();
	int getRenterID();
	//other methods


};



#endif /* MOVIEMANAGERUI_H_ */
