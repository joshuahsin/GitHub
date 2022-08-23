/*
 * proj2.cpp
 *
 *  Created on: Jan 28, 2020
 *      Author: joshuahsin
 */

#include "LLQueue.hpp"
#include <iostream>

#include <vector>

void countPaths(const std::vector< std::vector <unsigned> > & friends, unsigned start
		, std::vector<unsigned> & pathLengths, std::vector<unsigned> & numShortestPaths)
/* Given a vector of vectors representing neighboring nodes of each node i of friends, a start node, and
 * two empty vectors representing shortest path lengths to node i and the number of shortest paths to node i,
 * print the two vectors representing the shortest path lengths and number of shortest paths to each node i */
{
	std::vector<bool> discovered;  				//bool vector of discovered nodes
	for(int i = 0; i < friends.size(); i++){
		if(i != start){
			discovered.push_back(false);
		}
		else{
			discovered.push_back(true);
		}
	}
	LLQueue<int> * QueueList;
	QueueList = new LLQueue<int>[friends.size()];
	LLQueue<int> emptyQueue;
	emptyQueue.enqueue(start);
	QueueList[0] = emptyQueue;						//initialize QueueList as a list of queues with one queue containing the start node
	unsigned * distpaths;
	distpaths = new unsigned[friends.size()];
	for(int i = 0; i < friends.size();i++){
		if(i == start){
			distpaths[i] = 0;
		}
		else{
			distpaths[i] = 100;
		}
	}												//initialize distpaths, with start node at length 0 and other nodes at legnth 100
	unsigned * numDiffShortestPaths;
	numDiffShortestPaths =  new unsigned[friends.size()];
	for(int i = 0; i < friends.size();i++){
		if(i == start){
			numDiffShortestPaths[i] = 1;
		}
		else{
			numDiffShortestPaths[i] = 0;
		}
	}												//initialize numDiffShortestPaths with start node at 1 path and other nodes at 0 paths
	int i = 0;
	while(!QueueList[i].isEmpty()){
		LLQueue<int> emptyQueue2;
		QueueList[i+1] = emptyQueue2;				//initialize empty queue in next index of queue list
		int sizeQueueList = QueueList[i].size();	//get size of current queuelist
		for(int j = 0; j < sizeQueueList; j++){
			unsigned temp = QueueList[i].front();	//set temp to the front of the queue
			QueueList[i].dequeue();					//dequeue from fronst of queue
			for(int k = 0; k < friends[temp].size(); k++){
				//iterate through nodes connected to temp node
				if(distpaths[friends[temp][k]] > distpaths[temp] + 1){
					distpaths[friends[temp][k]] = distpaths[temp] + 1;
					numDiffShortestPaths[friends[temp][k]] = numDiffShortestPaths[temp];
					/*If current shortest distance to a neighbor node of temp form start node is greater than
					 * shortest distance to temp node from start node + 1, new shortest distance to neighbor node
					 * is set to shortest distance to temp node from start node + 1. Number of shortest paths is
					 * set equal to number of shortest paths to temp
					 */
				}
				else if(distpaths[friends[temp][k]] == distpaths[temp] + 1){
					numDiffShortestPaths[friends[temp][k]] += numDiffShortestPaths[temp];
					/* If current shortest distance to a neighbor node of temp form start node is greater than
					 * shortest distance to temp node from start node, that means more than one neighbor node has
					 * the same shortest distance. Add number of different shortest path to current node to
					 * current number of different shortest paths to temp node. */
				}
				if(discovered[friends[temp][k]] == false){
					discovered[friends[temp][k]] = true;
					QueueList[i+1].enqueue(friends[temp][k]);	//if neighbor node is not discovered, set it as discovered
																//and add to next queue in queue list to be traversed.
				}
			}
			QueueList[i].enqueue(temp);		//Add temp back to queue list at position i to represent shortest length to node i
		}
		i = i + 1;
	}

	//int count = 0;
	for(int j = 0; j < i; j++){
		int length = QueueList[j].size();
		for(int k = 0; k < length; k++){
			unsigned temp = QueueList[j].front();			//get node temp from front of queueList
			QueueList[j].dequeue();
			pathLengths[temp] = j;							//set minimum length to node temp from start node to j in pathLengths
		}
	}
	for(int j = 0; j < friends.size(); j++){
		numShortestPaths[j] = numDiffShortestPaths[j];		//transfer numDiffShortestPaths to numShortestPaths
	}

	for(int j = 0; j < friends.size(); j++){
		std::cout << "pathlengths "<< j << " " << pathLengths[j] << std::endl;
	}

	for(int j = 0; j < friends.size(); j++){
		std::cout << "numshortestpaths "<< j << " " << numShortestPaths[j] << std::endl;
	}
}

int main(){
	std::vector<unsigned> a;
	a.push_back(1);

	std::vector<unsigned> b;
	b.push_back(0);
	b.push_back(2);
	b.push_back(3);

	std::vector<unsigned> c;
	c.push_back(1);
	c.push_back(4);

	std::vector<unsigned> d;
	d.push_back(1);
	d.push_back(4);

	std::vector<unsigned> e;
	e.push_back(2);
	e.push_back(3);
	e.push_back(5);

	std::vector<unsigned> f;
	f.push_back(4);

	std::vector< std::vector<unsigned> > g1;
	g1.push_back(a);
	g1.push_back(b);
	g1.push_back(c);
	g1.push_back(d);
	g1.push_back(e);
	g1.push_back(f);
	std::vector<unsigned> pathLengths(6);
	std::vector<unsigned> numShortestPaths(6);

	countPaths(g1, 0, pathLengths, numShortestPaths);

	return 0;
}
