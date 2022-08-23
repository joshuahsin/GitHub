/*
 * Movies.cpp
 *
 *  Created on: Nov 12, 2019
 *      Author: Michael Vo #31082403, Joshua Hsin #13651420
 */


#include <iostream>
#include <string>
using namespace std;

#include "Movies.h"
#include "Renter.h"
#include "MovieManager.hpp"
#include <ostream>
#include <sstream>
#include <type_traits>

class DuplicateRenterException3 : public std::exception{
public:
	const char * errorMessage8() const throw(){
		return "You are already renting this movie";
	}
}DupRenter3;

class RenterLimitException3 : public std::exception{
public:
	const char * errorMessage9() const throw(){
		return "There are no more copies of the movie you are trying to rent";
	}
}RenterLimit3;

class EmptyRenterListException3 : public std::exception{
public:
	const char * errorMessage11() const throw(){
		return "There are no current movies being rented right now";
	}
}EmptyRenter3;

class InvalidRenterIDException3 : public std::exception{
public:
	const char * errorMessage12() const throw(){
		return "You have entered an invalid renter ID";
	}
}InvalidID3;

class RenterNotFoundException3 : public std::exception{
public:
	const char * errorMessage10() const throw(){
		return "You are trying to rent a movie that does not exist";
	}
}RenterNot3;



Movie::Movie(){
	//Movie constructor
	numRentedCopies = 0;
	movieCode = "";
	movieName = "";
	currentRenters[10];
}

int Movie::getnumRented(){
	//returns number of rented copies of the movie
	return numRentedCopies;
}

void Movie::setMovieCode(string movieCode2){
	//sets the movie code
	movieCode = movieCode2;
}

void Movie::setMovieName(string movieName2){
	//sets the movie name
	movieName = movieName2;
}

string Movie::getMovieName(){
	//returns the Movie Name
	return movieName;
}

string Movie::getMovieCode(){
	return movieCode;
}


void Movie::rentMovie(Renter r){
	//rents a movie to a renter
	try{

		if(numRentedCopies == 10){
			throw RenterLimit3; //exceeds renter limit
		}
		int duplicate = 0;
		for(int i = 0; i < getnumRented(); i++ ){
			if (r.getRenterID() == currentRenters[i].getRenterID()){
				duplicate = 1;
			}
		}
		if (duplicate == 1){
			throw DupRenter3; //duplicate Renter
		}
		currentRenters[numRentedCopies] = r;


		if (r.getRenterID() <= 0){
			throw InvalidID3; //invalid Renter id
		}

} catch (InvalidRenterIDException3& i){
	cout << "Invalid ID Error: " << i.errorMessage12();
}
catch (RenterLimitException3 & j){
	cout << "Renter Limit Error: " << j.errorMessage9();
}
catch (DuplicateRenterException3& k){
  cout << "Duplicate Renter Error: " << k.errorMessage8();
}
}

void Movie::returnRental(int renterId){
	//returns a renter's rented movie
	try{
	if (getnumRented() == 0){
			throw EmptyRenter3; //no Renters
	}
	std::cout << renterId << std::endl;
	//  cant check if trying to remove a renter from a movie if movie does not exist in the first place.. for renternotfound
	/*
	int index2 = -1;
	for(int i = 0; i < getnumRented(); i++){
		std::cout << getcurrentRenters(i).getRenterID() << std::endl;
		if(renterId == getcurrentRenters(i).getRenterID()){
			index2 = i;
		}
	};
	*/

	if(renterId ==  -1){
		throw RenterNot3; //Renter id not in Renter list
	}
	//std::cout << index2 << std::endl;

	for(int i = renterId; i < (numRentedCopies - 1); i++){
		shiftRenters(i);
	};
	numRentedCopies--;
	}

	catch (EmptyRenterListException3& i){
		cout << "Error" << i.errorMessage11();
	}
	catch (RenterNotFoundException3& k){
		cout << "Error" << k.errorMessage10();
	}
}

void Movie::incrementnumRentedCopies(){
	//increments number of rented copies
	numRentedCopies++;
}

Renter Movie::getcurrentRenters(int i){
	//Returns the renter with the given index in the renter list
	return currentRenters[i];
}

void Movie::shiftRenters(int i){
	//shifts renters from the index to the end to the left
	currentRenters[i] = getcurrentRenters(i+1);
}

ostream& operator<<(ostream& os, Movie & myMovie){
	//overloads the << operator to print movie info
	string movieInfo = "";
	string x;
	stringstream ss;
	ss << myMovie.getnumRented();
	x = ss.str();

	movieInfo += "Movie code: ";
	movieInfo += myMovie.getMovieCode();
	movieInfo += ". Movie Name: ";
	movieInfo += myMovie.getMovieName();
	movieInfo += ". Number of rented copies: ";
	movieInfo += x;

	os << movieInfo;
	return os;

	//movieInfo += str;

	os << movieInfo;
	return os;
}

void Movie::shiftRight(int i){
	//shifts renters from the index to the end to the right
	currentRenters[i + 1] = getcurrentRenters(i);

}

void Movie::storeInCurrentRenters(int index, Renter o){
	//stores a renter at given index
	currentRenters[index] = o;
}

