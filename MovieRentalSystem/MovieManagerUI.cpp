/*
 * MovieManagerUI.cpp
 *
 *  Created on: Nov 12, 2019
 *      Author: Michael Vo #31082403, Joshua Hsin #13651420
 */


#include <string>
#include <iostream>
#include <stdlib.h>

#include <exception>
using namespace std;

#include "MovieManager.hpp"
#include "MovieManagerUI.h"
#include "Movies.h"
#include "Renter.h"


class DuplicateMovieException2:public std::exception {
public:
	const char * errorMessage() const throw (){
		return "You have entered a duplicate movie";
	}
}DupMovie2;

class MovieLimitException2 : public std::exception {
public:
	const char * errorMessage2() const throw (){
		return "There are already a maximum amount of movies in the myMovieManager.getInventory()";
	}
}MovieLimit2;

class EmptyMovieInfoException2 : public std::exception {
public:
	const char * errorMessage3() const throw (){
		return "Either the movie code/movie name was empty when given";
	}
}EmptyInfo2;

class EmptyMovieListException2 : public std::exception {
public:
	const char * errorMessage4() const throw (){
		return "The myMovieManager.getInventory() of movies is currently empty";
	}
}EmptyMovieList2;


class RentedMovieException2 : public std::exception {
public:
	const char * errorMessage5() const throw() {
		return "There are current renters with this movie out";
	}
}RentedMovie2;

class MovieNotFoundException2 : public std::exception{
public:
	const char * errorMessage6() const throw() {
		return "The movie code entered is not found in the myMovieManager.getInventory()";
	}
}MovieNotFound2;



class EmptyRenterNameException2 : public std::exception{
public:
	const char * errorMessage7() const throw(){
		return "Either the first or last name of the renter is empty";
	}
}EmptyRenterName2;

class DuplicateRenterException2 : public std::exception{
public:
	const char * errorMessage8() const throw(){
		return "You are already renting this movie";
	}
}DupRenter2;

class RenterLimitException2 : public std::exception{
public:
	const char * errorMessage9() const throw(){
		return "There are no more copies of the movie you are trying to rent";
	}
}RenterLimit2;


class RenterNotFoundException2 : public std::exception{
public:
	const char * errorMessage10() const throw(){
		return "You are trying to rent a movie that does not exist";
	}
}RenterNot2;


class EmptyRenterListException2 : public std::exception{
public:
	const char * errorMessage11() const throw(){
		return "There are no current movies being rented right now";
	}
}EmptyRenter2;

class InvalidRenterIDException2 : public std::exception{
public:
	const char * errorMessage12() const throw(){
		return "You have entered an invalid renter ID";
	}
}InvalidID2;



void MovieManagerUI::printMenu(){
		//prints the Movie Manager menu
		cout << endl << "Welcome to Movie Rental Kiosk!" << endl;
		cout << endl << "---------" << endl;
		cout << endl << "am: Add Movie" << endl;
		cout << endl << "dm: Discontinue Movie" << endl;
		cout << endl << "rm: Rent Movie" << endl;
		cout << endl << "rr: Return Rental" << endl;
		cout << endl << "p: Print Movie" << endl;
		cout << endl << "q: Quit Program" << endl;
		cout << endl << "---------" << endl;
}

string MovieManagerUI::getCommand(){
	//returns a valid Movie Manager command
	string command = "";
	cout << endl << "Enter Command: ";
	cin >> command;
	cin.ignore();
	while (command != "am" && command != "dm" && command != "rm" && command != "rr" && command != "p" && command != "q"){
		cout << endl << endl << "Invalid command" << endl;
		printMenu();
		cout << endl << "Enter Command: ";
		cin >> command;
		cin.ignore();

	}
	return command;

}



string MovieManagerUI::getMovieCode(){
	//prompts user for movie code and returns
	string movieCode;
	cout << "Enter the movie code: ";
	//cin.ignore();
	getline(cin, movieCode);

	return movieCode;
}

string MovieManagerUI::getMovieName(){
	//prompts user for movie name and returns
	string movieName;
	cout << "Enter the movie name (string): ";
	//cin.ignore();
	getline(cin, movieName);

	return movieName;
}



string MovieManagerUI::getRenterFirstName(){
	//prompts user for renter first name and returns
	string RenterFirstName;
	cout << "Enter a renter First Name (string): ";
	//cin.ignore();
	getline(cin, RenterFirstName);
	return RenterFirstName;
}

string MovieManagerUI::getRenterLastName(){
	//prompts user for renter last name and returns
	string RenterLastName;
	cout << "Enter a renter Last Name (string): ";
	//cin.ignore();
	getline(cin, RenterLastName);
	return RenterLastName;
}


int MovieManagerUI::getRenterID(){
	//prompts user for renter id and returns
	int renterId;
	cout << "Enter a renter ID (int): ";
	if(!(std::cin >> renterId)){
		cout << "Invalid input, ending program..." << endl;
		exit(1);
	}

	return renterId;
}
