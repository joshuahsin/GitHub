/*
 * MovieManager.cpp
 *
 *  Created on: Nov 12, 2019
 *      Author: Michael Vo #31082403, Joshua Hsin #13651420
 */


#include <iostream>
#include <ostream>
#include <string>
#include <exception>
using namespace std;

#include "MovieManager.hpp"
#include "MovieManagerUI.h"
#include "Movies.h"
#include "Renter.h"


#include <algorithm>
#include <cctype>
#include <sstream>


MovieManager::MovieManager(int totalMoviesInput)
	{
	totalMovies=totalMoviesInput;
	//Movie thingy[20];
	// = Movie [20];
	//inventory[20];
	}

class DuplicateMovieException:public std::exception {
public:
	const char * errorMessage() const throw (){
		return "You have entered a duplicate movie";
	}
}DupMovie;

class MovieLimitException : public std::exception {
public:
	const char * errorMessage2() const throw (){
		return "There are already a maximum amount of movies in the inventory";
	}
}MovieLimit;

class EmptyMovieInfoException : public std::exception {
public:
	const char * errorMessage3() const throw (){
		return "Either the movie code/movie name was empty when given";
	}
}EmptyInfo;

class EmptyMovieListException : public std::exception {
public:
	const char * errorMessage4() const throw (){
		return "The inventory of movies is currently empty";
	}
}EmptyMovieList;


class RentedMovieException : public std::exception {
public:
	const char * errorMessage5() const throw() {
		return "There are current renters with this movie out";
	}
}RentedMovie;

class MovieNotFoundException : public std::exception{
public:
	const char * errorMessage6() const throw() {
		return "The movie code entered is not found in the inventory";
	}
}MovieNotFound;



class EmptyRenterNameException : public std::exception{
public:
	const char * errorMessage7() const throw(){
		return "Either the first or last name of the renter is empty";
	}
}EmptyRenterName;

class DuplicateRenterException : public std::exception{
public:
	const char * errorMessage8() const throw(){
		return "You are already renting this movie";
	}
}DupRenter;

class RenterLimitException : public std::exception{
public:
	const char * errorMessage9() const throw(){
		return "There are no more copies of the movie you are trying to rent";
	}
}RenterLimit;


class RenterNotFoundException : public std::exception{
public:
	const char * errorMessage10() const throw(){
		return "You are trying to return a renter with an ID that is not found for the movie";
	}
}RenterNot;


class EmptyRenterListException : public std::exception{
public:
	const char * errorMessage11() const throw(){
		return "There are no current movies being rented right now";
	}
}EmptyRenter;

class InvalidRenterIDException : public std::exception{
public:
	const char * errorMessage12() const throw(){
		return "You have entered an invalid renter ID";
	}
}InvalidID;



void MovieManager::run(){
	//Runs the main program

	string command;
	while (command != "q"){
		myMMUI.printMenu();
		command = myMMUI.getCommand();



		if (command == "p"){
			//prints the list of avaliable movies and their renters
			printInventory();
		}

		if (command == "rr"){
			//returns a renter's rented movie
			string movieCode = myMMUI.getMovieCode();
			int renterId = myMMUI.getRenterID();

			try{
				returnRental(renterId, movieCode);
			} catch (RenterNotFoundException& k) {
				cout << "Renter Not Found Error: " << k.errorMessage10() << endl;
			} catch (EmptyRenterListException& u) {
				cout << "Empty Renter List Error: " << u.errorMessage11() << endl;
			} catch (MovieNotFoundException& i){
				cout << "Movie Not Found Error: " << i.errorMessage6() << endl;
			}
		}

		if (command=="rm") {
			//rents a movie to a renter
			string firstName = myMMUI.getRenterFirstName();

			string lastName = myMMUI.getRenterLastName();

			string movieCode = myMMUI.getMovieCode();

			int renterId = myMMUI.getRenterID();

			Renter currentRenter;
			currentRenter.setRenterID(renterId);
			currentRenter.setfirstName(firstName);
			currentRenter.setlastName(lastName);

			try{
				rentMovie(movieCode, currentRenter);
			} catch (EmptyRenterNameException& h) {
				cout << "Empty Renter Name Error: " << h.errorMessage7() << endl;
			} catch (DuplicateRenterException& f) {
				cout << "Duplicate Renter Error: " << f.errorMessage8() << endl;
			} catch (RenterLimitException& i){
				cout << "Renter Limit Error: " << i.errorMessage9() << endl;
			} catch (MovieNotFoundException& j) {
				cout << "Movie Not Found Error: " << j.errorMessage6() << endl;
			}
		}

		if (command == "dm")
		{
			//deletes movie from list of availiable movies
			string movieCode = myMMUI.getMovieCode();

			try{
				discontinueMovie(movieCode);
				totalMovies--;

			} catch (EmptyMovieListException& l) {
				cout << "Empty Movie List Error: " << l.errorMessage4() << endl;
			} catch (RentedMovieException& r) {
				string renterName;
				for(int i = 0; i < totalMovies; i++){
						if (inventory[i].getMovieCode() == movieCode){ // does not account for case sensitivity
							for(int k = 0; k < inventory[i].getnumRented(); k++) { //loop through to get all names of current renters
								renterName += inventory[i].getcurrentRenters(k).getfirstName() + " " + inventory[i].getcurrentRenters(k).getlastName() + "   ";
							}
					}
				}
				cout << "Rented Movie Exception: " << r.errorMessage5() << endl;
				cout << "Here are the current renters: " << renterName << endl;

			} catch (MovieNotFoundException& z) {
				cout << "Movie Not Found Error: " << z.errorMessage6() << endl;
			}
		}


		if (command == "am") {
			//adds movie to list of availiable movies
			string movieCode = myMMUI.getMovieCode();
			string movieName = myMMUI.getMovieName();



			Movie myMovie;
			myMovie.setMovieCode(movieCode);
			myMovie.setMovieName(movieName);

			try {
					addMovie(myMovie);

				} catch (DuplicateMovieException& d) {
					cout << "Duplicate Error: " << d.errorMessage() << endl;

				} catch (MovieLimitException& m){
					cout << "Movie Limit Error: " << m.errorMessage2() << endl;

				} catch (EmptyMovieInfoException& e){
					cout << "Empty Movie Info Error: " << e.errorMessage3() << endl;
				}
			}

		}
		quitProgram();
	}


void MovieManager::addMovie(Movie m){
	//adds movie to list of availiable movies
	if(totalMovies >= 20){
		throw MovieLimit;
	}

	if(m.getMovieCode() == "" || m.getMovieName() == ""){
		throw EmptyInfo;
	}

	for(int i = 0; i < totalMovies; i++){
		if (m.getMovieCode() == inventory[i].getMovieCode() || m.getMovieName() == inventory[i].getMovieName()) {
			throw DupMovie;
		}
	}

	inventory[totalMovies] = m; //adds movie to array of movies
	totalMovies++;
};

void MovieManager::discontinueMovie(string movieCode){
	//deletes movie from list of availiable movies
	cout << "here";
	if (totalMovies <= 0){
		throw EmptyMovieList;
	}
	cout << "2";
	int dm = -1;
	for(int i = 0; i < totalMovies; i++){
		if (movieCode == inventory[i].getMovieCode()){
			dm = i;
			if(inventory[i].getnumRented() > 0){
				throw RentedMovie;
			}
		}

	}

	if(dm == -1){
		throw MovieNotFound;
	}

	for(int i = dm; i < getTotalMovies() - 1; i++){
		inventory[i] = inventory[i + 1];
	}

	//throws exceptions
	//removes movie from array
};

void MovieManager::rentMovie(string movieCode, Renter s){
	//rents a movie to a renter
	int dm = -1;

	if(s.getfirstName() == "" || s.getlastName() == ""){
			throw EmptyRenterName;
	}

	for(int i = 0; i < totalMovies; i++){
		if (movieCode == inventory[i].getMovieCode()){
			dm = i;
		}
	}

	if(dm == -1){
		throw MovieNotFound;
	}

	for(int i = 0; i < inventory[dm].getnumRented(); i++){
		if(s.getRenterID() == inventory[dm].getcurrentRenters(i).getRenterID() ||
				(s.getfirstName() == inventory[dm].getcurrentRenters(i).getfirstName() &&
				s.getlastName() == inventory[dm].getcurrentRenters(i).getlastName())){
			throw DupRenter;
		}
	}

	if(inventory[dm].getnumRented() == 10){
		throw RenterLimit;
	}


	int index;
	for(int i = 0; i < 20; i++){
		if(movieCode == inventory[i].getMovieCode()){
			index = i;
		}
	}

	inventory[index].rentMovie(s);
	inventory[index].incrementnumRentedCopies();

	//throws exceptions
	//adds renter to existing movie
};

void MovieManager::returnRental(int renterId, string movieCode){
	//returns a renter's rented movie

	int dm = -1;
	for(int i = 0; i < totalMovies; i++){
		if (movieCode == inventory[i].getMovieCode()){
			dm = i;
		}

	} if(dm == -1){
		throw MovieNotFound;
	}

	int index;
	for(int i = 0; i < 20; i++){
		if(movieCode == inventory[i].getMovieCode()){
			index = i;
		}
	};


	if (inventory[index].getnumRented() == 0){
			throw EmptyRenter;
	}

	int index2 = -1;
	for(int i = 0; i < inventory[index].getnumRented(); i++){
		if(renterId == inventory[index].getcurrentRenters(i).getRenterID()){
			index2 = i;
		}
	};

	if (index2 == -1){
		throw RenterNot;
	}


	inventory[index].returnRental(index2);



	//throws exceptions
	//removes renter from existing movie
};


void MovieManager::printInventory(){
	//prints list of availiable movies and their renters
	for(int i = 0; i < (totalMovies); i++){
		cout << inventory[i] << endl;
		for(int k = 0; k < inventory[i].getnumRented(); k++){
			string x;
			stringstream ss;
			ss << inventory[i].getcurrentRenters(k).getRenterID();
			x = ss.str();

			cout << "Renter ID: ";
			cout << x << endl;
			cout << inventory[i].getcurrentRenters(k).getlastName() << ", ";
			cout << inventory[i].getcurrentRenters(k).getfirstName() << endl;
		}
	}
};

Movie MovieManager::getInventory(int i){
	//returns movie object at certain index in movie list
	return inventory[i];
}

void MovieManager::quitProgram(){
	//terminates the program
	cout << "You have quit the program" << endl;
};


int MovieManager::getTotalMovies(){
	//returns total number of movies
	return totalMovies;
};

void MovieManager::decrementTotalMovies(){
	//decrements total movies
	totalMovies--;
};




