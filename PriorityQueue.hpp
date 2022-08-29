/*
 * proj5.hpp
 *
 *  Created on: Mar 7, 2020
 *      Author: joshuahsin
 */

#ifndef PROJ5_HPP_
#define PROJ5_HPP_



#include <vector>
#include <iostream>
#include <climits>

void swap(int *x, int *y)
{
    int temp = *x;
    *x = *y;
    *y = temp;
}

template<typename Object>
class MyPriorityQueue
{
private:
	//count of elements in Priority Queue
    unsigned count = 0;
    std::vector<Object> vectortracker;

public:
    //return size
 	size_t size() const;

 	//return whether Priority Queue is empty
	bool isEmpty() const;

	//insert element into
	void insert(const Object & elem);

	//return the vector
	std::vector<Object> returnVectorTracker();

	//print the vector
	void printVectorTracker();

	//return the minimum
	const Object & min() const;

	//remove the minimum
	void extractMin();

};

template<typename Object>
void swap(Object *x, Object *y)
{
    Object temp = *x;
    *x = *y;
    *y = temp;
}

template<typename Object>
std::vector<Object> MyPriorityQueue<Object>::returnVectorTracker()
{
	return vectortracker;
}


template<typename Object>
void MyPriorityQueue<Object>::printVectorTracker()
{
	for(int i = 0; i < vectortracker.size(); i++){
		if(i != (vectortracker.size() - 1)){
			std::cout << vectortracker[i] << '-';
		}
		else{
			std::cout << vectortracker[i] << std::endl;
		}
	}
};


template<typename Object>
size_t MyPriorityQueue<Object>::size() const
{
	return count;
}



template<typename Object>
bool MyPriorityQueue<Object>::isEmpty() const
{
	if(count == 0){
	    return true;
    }
    else{
        return false;
    }
}

template<typename Object>
void MyPriorityQueue<Object>::insert(const Object & elem)
{
	Object temp;
	bool stop = false;
	//insert into back of Priority Queue
	vectortracker.push_back(elem);
	int index = count;
	if(count != 0){
		while(!stop){
			if(index == 0){
				stop = true;
			}
			else if(elem < vectortracker[(index-1)/2]){
				//if greater than parent, switch with parent
				temp = vectortracker[(index-1)/2];
				vectortracker[(index-1)/2] = elem;
				vectortracker[index] = temp;
				index = (index - 1)/2;
			}
			else{
				//else break
				stop = true;
			}
        }
    }
	//increment element count
    count++;
}




template<typename Object>
const Object & MyPriorityQueue<Object>::min() const
{
	if(count != 0){
		return vectortracker[0]; // terrible idea, don't actually do this.
	}
	else{
		return NULL;
	}
}



template<typename Object>
void MyPriorityQueue<Object>::extractMin()
{
	if(count == 0){
		//if empty, do nothing
	}
	else if(count == 1){
		//remove only element
         vectortracker.erase(vectortracker.begin());
         count--;
    }
    else if(count == 2){
    	//swap elements and remove min
    	swap(&vectortracker[count - 1], &vectortracker[0]);
    	vectortracker.pop_back();
    	count--;
    }
    else{
    	//swap first and last elements, remove last element, then propagate down if first element is greater than either child until
    	//less than both children or there are no children
    	count--;
    	swap(&vectortracker[count], &vectortracker[0]);
    	vectortracker.pop_back();
    	bool stop = false;
    	int start = 0;
    	while(!stop){
    		//if no children, stop
			if(((start * 2 + 1) >= count - 1) && ((start * 2 + 2) >= count - 1)){
				stop = true;
			}
			else{
				//if left child exists
				if((start * 2 + 1) < count - 1){
					//get left child value
					Object left = vectortracker[(start * 2 + 1)];
					//if right child exists
					if((start * 2 + 2) < count){
						//get right child
						Object right = vectortracker[(start * 2 + 2)];
						//if left and right child less than parent
						if(left < vectortracker[start] && right < vectortracker[start]){
							//and right child less than left child
							if(right < left){
								//swap with right child and continue propagating
								swap(&vectortracker[(start * 2 + 2)], &vectortracker[start]);
								start = start * 2 + 2;
							}
							//and left child less than right child
							else{
								//swap with left child and continue propagating
								swap(&vectortracker[(start * 2 + 1)], &vectortracker[start]);
								start = start * 2 + 1;
							}
						}
						//else if only left child is less than parent
						else if(left < vectortracker[start]){
							//swap with left child and continue propagating
							swap(&vectortracker[(start * 2 + 1)], &vectortracker[start]);
							start = start * 2 + 1;
						}
						//else if only right child is less than parent
						else if(right < vectortracker[start]){
							//swap with right child and continue propagating
							swap(&vectortracker[(start * 2 + 2)], &vectortracker[start]);
							start = start * 2 + 2;
						}
					}

					//else if no right child
					else{
						//if only left child is less than parent
						if(left < vectortracker[start]){
							//swap with left child and continue propagating
							swap(&vectortracker[(start * 2 + 1)], &vectortracker[start]);
							start = start * 2 + 1;
						}
						//else stop
						else{
							stop = true;
						}
					}
				}
			}
		}
    }
}




#endif /* PROJ5_HPP_ */
