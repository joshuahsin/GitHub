/*
 * runtimeexcept.hpp
 *
 *  Created on: Jan 28, 2020
 *      Author: joshuahsin
 */

#ifndef RUNTIMEEXCEPT_HPP_
#define RUNTIMEEXCEPT_HPP_

#include <string>

class RuntimeException
{
private:
	std::string errorMsg;
public:
	RuntimeException(const std::string & err){errorMsg = err;}
	std::string getMessage() const {return errorMsg;}
};

inline std::ostream & operator << (std::ostream & out, const RuntimeException & e){
	return out << e.getMessage();
}




#endif /* RUNTIMEEXCEPT_HPP_ */
