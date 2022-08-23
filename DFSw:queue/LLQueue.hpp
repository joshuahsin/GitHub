//============================================================================
// Name        : proj2schindler.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#ifndef __PROJ2_QUEUE_HPP
#define __PROJ2_QUEUE_HPP

#include "runtimeexcept.hpp"
#include <iostream>

class QueueEmptyException : public RuntimeException
{
public:
	QueueEmptyException(const std::string & err) : RuntimeException(err) {}
};


template<typename Object>
class LLQueue
{
private:
	struct node{
		Object data;
		node* next;
		node* previous;
	};
	node* prev;
	node* current;
	node* start;
	size_t count;
public:
	LLQueue();
	~LLQueue(){
		delete start;
		//delete prev;
		//delete current;
	};
	size_t size() const;
	bool isEmpty() const;

	Object & front();
	const Object & front() const;

	void enqueue(const Object & elem);

	void dequeue();
};

template<typename Object>
LLQueue<Object>::LLQueue()
{
	count = 0;
	node* n;
	n = new node;
	n->previous = nullptr;
	n->next = nullptr;
	start = n;
	current = n;
	prev = n;
}//set count to 0 and create new node with nullptrs to next and previous. Set start, current, and prev equal to new node

template<typename Object>
size_t LLQueue<Object>::size() const
{
	return count;
}//return size of count

template<typename Object>
bool LLQueue<Object>::isEmpty() const
{
	return (size() == 0);
}//return true if queue is empty, false otherwise

template<typename Object>
Object & LLQueue<Object>::front()
{
	try{
		if( isEmpty() )
		{
			throw QueueEmptyException("Cannot return front from an empty list");
		}
	}
	catch(QueueEmptyException & e){
		std::cout << "Cannot return front from an empty list" << std::endl;
		exit(1);
	}//if queue is empty, throw error and exit
	return start->data; //if queue is not empty, return value of start node
}

template<typename Object>
void LLQueue<Object>::enqueue(const Object & elem)	//Add new node to the end of the queue
{
	node* n;
	n = new node;
	n->data = elem;
	n->next = NULL;					//create new node with value elem and next pointing to NULL
	if(count == 0){
		start = n;
		current = n;
		prev->next = current;
		current->previous = prev;	//if queue is empty, set start and current to new node and manipulate appropriate pointers
	}
	if(count == 1){
		start = current;
		current = n;
		start->next = current;
		prev = start;
		current->previous = prev;	//If queue has one node, set start to current, then set current equal to new node
									//Following this, set start.next to the new current. Finally, set prev equal to start
									//and current.previous = prev.
	}
	if(count > 1){
		prev = current;
		current = n;
		current->previous = prev;
		prev->next = current;		//If queue has more than one node, set prev = current, current = new node,
									//current.previous = prev and prev.next = current
	}
	count += 1;						//increment count
}

template<typename Object>
void LLQueue<Object>::dequeue()		//remove node from beginning of queue
{
	try{
		if( isEmpty() )
		{
			throw QueueEmptyException("Cannot dequeue from an empty list");
		}
	}
	catch(QueueEmptyException & e){
		std::cout << "Cannot dequeue from an empty list" << std::endl;
		exit(1);
	}	//if empty, throw error
	if (count > 2){
		start = start->next;		//If more than two nodes, simply move start pointer
	}
	else if (count == 2){
		 node* n;
		 n = new node;
		 n -> next = current;
		 n -> previous = nullptr;
		 prev = n;
		 start = current;			//Create new node n that points to current using next pointer
		 	 	 	 	 	 	 	//and null previous pointer, then set prev equal to the new node.
		 	 	 	 	 	 	 	//Set start equal to current
	}
	else if (count == 1){
		node* n;
		n = new node;
		n->next = nullptr;
		n->previous = nullptr;
		start = n;
		current = n;
		prev -> next = current;		//Create new node with no pointers, set start and current equal to new node,
									//then set prev.next = new current
	}
	count -= 1;						//decrement count
}



#endif
