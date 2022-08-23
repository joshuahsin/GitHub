/*
 * Renter.h
 *
 *  Created on: Nov 12, 2019
 *      Author: Michael Vo #31082403, Joshua Hsin #13651420
 */

#ifndef RENTER_H_
#define RENTER_H_
using namespace std;
#include <iostream>
#include <ostream>
#include <string>

class Renter{
public:
	void setRenterID(int);
	void setfirstName(string);
	void setlastName(string);
	int getRenterID();
	string getfirstName();
	string getlastName();
	friend ostream& operator<<(ostream& os, Renter& myRenter);

private:
	int renterId;
	string firstName;
	string lastName;
};



#endif /* RENTER_H_ */
