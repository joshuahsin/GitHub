/*
 * Renter.cpp
 *
 *  Created on: Nov 12, 2019
 *      Author: Michael Vo #31082403, Joshua Hsin #13651420
 */

#include "Renter.h"
#include <iostream>
#include <string>
using namespace std;
#include <ostream>
#include <sstream>

void Renter::setRenterID(int renterId2){
	renterId = renterId2;
}

void Renter::setfirstName(string firstName2){
	firstName = firstName2;
}

void Renter::setlastName(string lastName2){
	lastName = lastName2;
}

int Renter::getRenterID(){
	return renterId;
}

string Renter::getfirstName(){
	return firstName;
}

string Renter::getlastName(){
	return lastName;
}

ostream& operator<<(ostream& os, Renter &myRenter){
	//overloads the << operator to print out Renter info
	string renterInfo;
	string y;
	stringstream ss;
	ss << myRenter.getRenterID();
	y = ss.str();

	renterInfo += "Renter ID: ";
	renterInfo += y;
	renterInfo += ". Renter Last Name: ";
	renterInfo += myRenter.getlastName();
	renterInfo += ". Renter First Name: ";
	renterInfo += myRenter.getfirstName();

	os << renterInfo;
	return os;
}
