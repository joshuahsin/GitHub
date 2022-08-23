/*
 * Movies.h
 *
 *  Created on: Nov 12, 2019
 *      Author: Michael Vo #31082403, Joshua Hsin #13651420
 */

#ifndef MOVIES_H_
#define MOVIES_H_

#include "Renter.h"
#include <string>
#include <ostream>

class Movie{
public:
	Movie();
	void rentMovie(Renter);
	void returnRental(int);
	void setMovieCode(string);
	void setMovieName(string);
	string getMovieName();
	string getMovieCode();
	int getRenterID();
	void incrementnumRentedCopies();
	Renter getcurrentRenters(int);
	int getnumRented();
	Renter getcurrentRenters2();
	void shiftRenters(int);
	friend ostream& operator<<(ostream& os, Movie &myMovie);
	void shiftRight(int);
	void storeInCurrentRenters(int, Renter);


private:
	string movieCode;
	string movieName;
	int numRentedCopies;
	Renter currentRenters[10];

};



#endif /* MOVIES_H_ */
