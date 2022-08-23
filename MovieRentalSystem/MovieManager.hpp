/*
 * MovieManager.hpp
 *
 *  Created on: Nov 12, 2019
 *      Author: Michael Vo #31082403, Joshua Hsin #13651420
 */

#ifndef MOVIEMANAGER_H_
#define MOVIEMANAGER_H_

#include <iostream>
#include <string>
using namespace std;
#include "Movies.h"
#include "Renter.h"
#include "MovieManagerUI.h"


class MovieManager {
public:
	MovieManager(int);
	void run();
	void addMovie(Movie);
	void discontinueMovie(string);
	void rentMovie(string, Renter);
	void returnRental(int, string);
	void printInventory();
	void quitProgram();
	int getTotalMovies();
	Movie getInventory(int);
	void decrementTotalMovies();


private:
	MovieManagerUI myMMUI;
	Movie inventory[20];
	int totalMovies;
	//int maxMovies;

};



#endif /* MOVIEMANAGER_H_ */
