

#include <string>
#include <vector>
#include <iostream>
#include <typeinfo>

template<typename Key, typename Value>
class MyAVLTree
{
private:
	struct node{
		int height;
		Key key;
		Value value;
		node* parent;
		node* leftchild;
		node* rightchild;
	};
    size_t count;
    size_t height;
    size_t right_height;
    size_t left_height;
    std::vector<node*>  added_nodes;
    std::string name;
    //std::vector<node*> inOrderVector;
    // fill in private member data here
    // If you need to declare private functions, do so here too.

public:
    node* rootNode;
    MyAVLTree();
    std::vector<Key> inOrderVector;
    // In general, a copy constructor and assignment operator
    // are good things to have.
    // For this quarter, I am not requiring these.
    // The destructor is, however, required.
    ~MyAVLTree()
    {
        // TODO
    }

    // size() returns the number of distinct keys in the tree.
    size_t size() const;

    // isEmpty() returns true if and only if the tree has no values in it.
    bool isEmpty() const;

    // contains() returns true if and only if there
    //  is a (key, value) pair in the tree
    //  that has the given key as its key.
    bool contains(const Key & k) const;
    bool checkBalence(node* currentNode);
    void inOrderTraversal(node* node);
    // find returns the value associated with the given key
    // If !contains(k), this will throw an ElementNotFoundException
    // There needs to be a version for const and non-const MyAVLTrees.
   Value & find(const Key & k);
   const Value & find(const Key & k) const;

    // Inserts the given key-value pair into
    // the tree and performs the AVL re-balance
    // operation, as described in lecture.
    // If the key already exists in the tree,
    // you may do as you please (no test cases in
    // the grading script will deal with this situation)
    void insert(const Key & k, const Value & v);
    void Rotate(node* startingNode);
    void increment(const Key & k);
    // in general, a "remove" function would be here
    // It's a little trickier with an AVL tree
    // and I am not requiring it for this quarter's ICS 46.
    // You should still read about the remove operation

    // Each returns them in a different order.
  //std::vector<Key> inOrder() const;
       void preOrder(node* node) const;
       void postOrder(node* node) const;


   };

	template<typename Key, typename Value>
	MyAVLTree<Key, Value>::MyAVLTree() // @suppress("Class members should be properly initialized")
	{
		count = 0;
		height = 0;
		right_height = 0;
		left_height = 0;
		rootNode = new node;
		rootNode->height = -1;
		rootNode->parent = nullptr;
		/*
		node* leftNode;
		leftNode = new node;
		leftNode->height = -1;
		rootNode->leftchild = leftNode;
		node* rightNode;
		rightNode = new node;
		rightNode->height = -1;
		rootNode->rightchild = rightNode;
		*/
	}


   template<typename Key, typename Value>
   size_t MyAVLTree<Key, Value>::size() const
   {
       return count; // stub
   }

   template<typename Key, typename Value>
   bool MyAVLTree<Key, Value>::isEmpty() const
   {
       if(rootNode->height == -1){
    	   return true;
       }
       return false;
   }



   template<typename Key, typename Value>
   bool MyAVLTree<Key, Value>::contains(const Key & k) const
   {
       for(int i = 0; i < added_nodes.size(); i++)
       {
           if(added_nodes[i]->key == k)
           {
               return true;
           }
       }
       return false; // stub
   }

       template<typename Key, typename Value>
       const Value & MyAVLTree<Key, Value>::find(const Key & k) const
       {
         for(int i = 0; i < added_nodes.size(); i++){
           if(added_nodes[i]->key == k){
             return added_nodes[i]->value;
           }
         }
         exit(EXIT_FAILURE);
         //return added_nodes[0]->value; //not only a stub, but a terrible idea.
       }

       template<typename Key, typename Value>
       Value & MyAVLTree<Key, Value>::find(const Key & k)
       {
         for(int i = 0; i < added_nodes.size(); i++){
           if(added_nodes[i]->key == k){
             std::cout << "answer1" << added_nodes[i]->value << std::endl;
             return added_nodes[i]->value;
           }
         }
         std::cout << "answer2" << added_nodes[0]->value << std::endl;
         return added_nodes[0]->value; //not only a stub, but a terrible idea.
       }

       template<typename Key, typename Value>
       void MyAVLTree<Key, Value>::insert(const Key & k, const Value & v)
       {
           node* insert_node;
           insert_node = new node;
           insert_node->height = 0;
           insert_node->key = k;
           insert_node->value = v;
           node* right_child = new node;
           right_child->height = -1;
           right_child->parent = insert_node;
           //right_child = nullptr;
           node* left_child = new node;
           left_child->height = -1;
           left_child->parent = insert_node;
           //left_child = nullptr;
           insert_node->rightchild = right_child;
           insert_node->leftchild = left_child;

           if(count == 0){
        	   rootNode = insert_node;
        	   rootNode->parent = nullptr;
           }
           else{
        	   node* tracking_node = new node;
        	   tracking_node = rootNode;
           //std::cout << "height "<< tracking_node->rightchild->height << std::endl;
        	   while(tracking_node->height != -1){
				   if(insert_node->key < tracking_node->key){
					   tracking_node = tracking_node->leftchild;
				   }
				   else if(insert_node->key > tracking_node->key){
					   tracking_node = tracking_node->rightchild;
				   }
        	   }
           //std::cout <<tracking_node->parent->key << std::endl;
			   if(tracking_node->parent->key < insert_node->key){
				   //std::cout << "less than" << std::endl;
				   tracking_node->parent->rightchild = insert_node;
				   insert_node->parent = tracking_node->parent;
			   }
			   else if(tracking_node->parent->key > insert_node->key){
				   tracking_node->parent->leftchild = insert_node;
				   insert_node->parent = tracking_node->parent;
			   }
           //std::cout << rootNode->rightchild->key << std::endl;
                  node* current_node;
                  current_node = new node;
                  current_node = insert_node;
                  //std::cout << "a" << std::endl;
                  while(current_node->parent != nullptr){
                	  //std::cout << "current_ndoe//"<< current_node->parent->key << std::endl;
                	  current_node = current_node->parent;
                	  if(current_node->rightchild && current_node->leftchild){
                		  current_node->height = std::max(current_node->rightchild->height, current_node->leftchild->height) + 1;
                	  }
                	  else if(!current_node->rightchild){
                	      current_node->height = current_node->leftchild->height + 1;
                	  }
                	  else{
                	      current_node->height = current_node->rightchild->height + 1;
                	  }
                	  std::cout << "Inserted node with key " << current_node->key << " and value " << current_node->value << " at height " << current_node->height << std::endl;
                  }
           	   }
           	   //std::cout <<"done" << std::endl;
           	   if(!checkBalence(rootNode)){
           		   std::cout << "Not balenced, rotating" << std::endl;
           		   Rotate(insert_node);
           	   }

               added_nodes.push_back(insert_node);
               count++;
               return;
            }

                  template<typename Key, typename Value>
                  void MyAVLTree<Key, Value>::inOrderTraversal(node* node)
                  {
                	  //std::cout << node->key << std::endl;
                	  inOrderVector.push_back(node->key);
                      if(node->height == -1){
                    	  return;
                      }
                      //inOrderVector.push_back(node->key);

                      inOrderTraversal(node->leftchild);


                      std::cout <<"node "<< node->key << " ";

                      inOrderTraversal(node->rightchild);
                  }


                  template<typename Key, typename Value>
                  void MyAVLTree<Key, Value>::preOrder(node* node) const
                  {
                      if(node->height == -1){
                    	  return;
                      }

                      std::cout << "node" << node->key << " ";

                      preOrder(node->leftchild);

                      preOrder(node->rightchild);
                  }

                  template<typename Key, typename Value>
					void MyAVLTree<Key, Value>::postOrder(node* node) const
					{
						if(node->height == -1){
						  return;
						}

						postOrder(node->leftchild);

						postOrder(node->rightchild);

						std::cout << "node" << node->key << " ";
					}

                  template<typename Key, typename Value>
                  void MyAVLTree<Key, Value>::Rotate(node* startingNode)
                  {
                	  //std::cout << startingNode->key << std::endl;
                	  //std::cout << "bbbbbb" <<std::endl;

                      node* X;
                      X = new node;
                      X = startingNode;
                      node* Y;
                      Y = new node;
                      Y = startingNode->parent;
                      node* Z;
                      Z = new node;
                      Z = startingNode->parent->parent;

					  bool stop = false;
                	  while(!stop){
                		  if(Z->parent == nullptr){
                		      stop = true;
                		  }
                		  else if(abs(Z->rightchild->height - Z->leftchild->height) <= 1){
                			  Z = Z->parent;
                			  X = X->parent;
                			  Y = Y->parent;
                		  }
                		  else{
                			  stop = true;
                		  }
                	  }

                      //std::cout << "aaaa" <<std::endl;
                      if(Y->key < X->key && X->key < Z->key){

                    	  node* tree0;
                    	  tree0 = new node;
                    	  tree0 = Y->leftchild;
                    	  node* tree1;
                    	  tree1 = new node;
                    	  tree1 = X->leftchild;
                    	  node* tree2;
                    	  tree2 = new node;
                    	  tree2 = X->rightchild;
                    	  node* tree3;
                    	  tree3 = new node;
                    	  tree3 = Z->rightchild;
                    	  if(Z->parent != nullptr){
                    		  if(Z->parent->rightchild->key == Z->key){
                    			  X->parent = Z->parent;
                    			  Z->parent->rightchild = X;
                    		  }

                    		  else if(Z->parent->leftchild->key == Z->key){
                    			  X->parent = Z->parent;
                    			  Z->parent->leftchild = X;

                    		  }
                    	  }
                    		  else{
                    			  rootNode = X;
                    			  X->parent = nullptr;
                    		  }
                   X->leftchild = Y;
                   Y->parent = X;
                   Y->leftchild = tree0;
                   Y->rightchild = tree1;
                   X->rightchild = Z;
                   Z->parent = X;
                   Z->leftchild = tree2;
                   Z->rightchild = tree3;

                   X->height += 1;
                   Y->height -= 1;
                   Z->height -= 2;
                   node* current_node;
                   current_node = new node;
                   current_node = X;
                   while(current_node->parent != nullptr){
                	   current_node->parent->height -= 1;
                	   current_node = current_node->parent;
                   }
                   }
                   else if(X->key < Y->key && Y->key < Z->key){
                	   node* tree0;
					  tree0 = new node;
					  tree0 = X->leftchild;
					  node* tree1;
					  tree1 = new node;
					  tree1 = X->rightchild;
					  node* tree2;
					  tree2 = new node;
					  tree2 = Y->rightchild;
					  node* tree3;
					  tree3 = new node;
					  tree3 = Z->rightchild;
					  if(Z->parent != nullptr){
						  if(Z->parent->rightchild->key == Z->key){
							  Y->parent = Z->parent;
							  Z->parent->rightchild = Y;
						  }

						  else if(Z->parent->leftchild->key == Z->key){
							  Y->parent = Z->parent;
							  Z->parent->leftchild = Y;

						  }
					  }
						  else{
							  rootNode = Y;
							  Y->parent = nullptr;
						  }

					  Y->leftchild = X;
					 X->parent = Y;
					 X->leftchild = tree0;
					 tree0->parent = X;
					 X->rightchild = tree1;
					 tree1->parent = X;
					 Y->rightchild = Z;
					 Z->parent = Y;
					 Z->leftchild = tree2;
					 tree2->parent = Z;
					 Z->rightchild = tree3;
					 tree3->parent = Z;

					 Z->height -= 2;
					 node* current_node;
					 current_node = new node;
					 current_node = Y;
					 while(current_node->parent != nullptr){
						 current_node->parent->height -= 1;
						 current_node = current_node->parent;
					 }
                   }
                   else if(Z->key < X->key && X->key < Y->key){
                	   //std::cout << "here" << std::endl;
                	  node* tree0;
					  tree0 = new node;
					  tree0 = Z->leftchild;
					  node* tree1;
					  tree1 = new node;
					  tree1 = X->leftchild;
					  node* tree2;
					  tree2 = new node;
					  tree2 = X->rightchild;
					  node* tree3;
					  tree3 = new node;
					  tree3 = Y->rightchild;
					  if(Z->parent != nullptr){
						  if(Z->parent->rightchild->key == Z->key){
							  X->parent = Z->parent;
							  Z->parent->rightchild = X;
						  }

						  else if(Z->parent->leftchild->key == Z->key){
							  X->parent = Z->parent;
							  Z->parent->leftchild = X;

						  }
					  }
						  else{
							  rootNode = X;
							  X->parent = nullptr;
						  }

					  X->leftchild = Z;
					 Z->parent = X;
					 Z->leftchild = tree0;
					 tree0->parent = Z;
					 Z->rightchild = tree1;
					 tree1->parent = Z;
					 X->rightchild = Y;
					 Y->parent = X;
					 Y->leftchild = tree2;
					 tree2->parent = Y;
					 Y->rightchild = tree3;
					 tree3->parent = Y;

					 X->height += 1;
					 Y->height -= 1;
					 Z->height -= 2;
					 //std::cout << "gay" << std::endl;
					 node* current_node;
					 current_node = new node;
					 current_node = X;
					 while(current_node->parent != nullptr){
						 //std::cout << "dum" << std::endl;
						 current_node->parent->height -= 1;
						 current_node = current_node->parent;
					 }
                   }
                   else if(Z->key < Y->key && Y->key < X->key){
                 	  node* tree0;
 					  tree0 = new node;
 					  tree0 = Z->leftchild;
 					  node* tree1;
 					  tree1 = new node;
 					  tree1 = Y->leftchild;
 					  node* tree2;
 					  tree2 = new node;
 					  tree2 = X->leftchild;
 					  node* tree3;
 					  tree3 = new node;
 					  tree3 = X->rightchild;
 					  if(Z->parent != nullptr){
 						  if(Z->parent->rightchild->key == Z->key){
 							  Y->parent = Z->parent;
 							  Z->parent->rightchild = Y;
 						  }

 						  else if(Z->parent->leftchild->key == Z->key){
 							  Y->parent = Z->parent;
 							  Z->parent->leftchild = Y;

 						  }
 					  }
 						  else{
 							  rootNode = Y;
 							  Y->parent = nullptr;
 						  }

 					 Y->leftchild = Z;
 					 Z->parent = Y;
 					 Z->leftchild = tree0;
 					 tree0->parent = Z;
 					 Z->rightchild = tree1;
 					 tree1->parent = Z;
 					 Y->rightchild = X;
 					 X->parent = Y;
 					 X->leftchild = tree2;
 					 tree2->parent = X;
 					 X->rightchild = tree3;
 					 tree3->parent = X;

 					 Z->height -= 2;
					 node* current_node;
					 current_node = new node;
					 current_node = Y;
					 while(current_node->parent != nullptr){
						 current_node->parent->height -= 1;
						 current_node = current_node->parent;
					 }
                   }
                      if(X->parent != nullptr){
                    	  std::cout << "x parent "<< X->parent->key << std::endl;
                      }
                      else{
                    	  std::cout << "x parent nullptr" << std::endl;
                      }
                      std::cout << X->key << std::endl;
                      std::cout << X->leftchild->key << std::endl;
                      std::cout << X->rightchild->key << std::endl;
                      if(Y->parent != nullptr){
                    	  std::cout << "y parent "<< Y->parent->key << std::endl;
                      }
                      else{
                    	  std::cout << "y parent nullptr" << std::endl;
                      }
                      std::cout << Y->key << std::endl;
                      std::cout << Y->leftchild->key << std::endl;
                      std::cout << Y->rightchild->key << std::endl;
                      if(Z->parent !=nullptr){
                    	  std::cout << "z parent "<< Z->parent->key << std::endl;
                      }
                      else{
                    	  std::cout << "z parent nullptr" << std::endl;
                      }
                      std::cout << Z->key << std::endl;
                      std::cout << Z->leftchild->key << std::endl;
                      std::cout << Z->rightchild->key << std::endl;


           }

                  template<typename Key, typename Value>
                  bool MyAVLTree<Key, Value>::checkBalence(node* currentNode)
                  {
                      if(currentNode->height == -1){
                          return true;
                      }
                      else{
                          if(abs(currentNode->rightchild->height - currentNode->leftchild->height) > 1){
                              return false;
                          }
                          else{
                              return checkBalence(currentNode->rightchild) && checkBalence(currentNode->leftchild);
                          }
                      }

                  }

                  template<typename Key, typename Value>
                  void MyAVLTree<Key, Value>::increment(const Key & k)
				  {
                	  for(int i = 0; i < added_nodes.size();i++){
                		  if(added_nodes[i]->key == k){
                			  if(!std::strcmp(typeid(added_nodes[i]->key).name(), "i")){
                				  added_nodes[i]->value += 1;
                			  }else{
                				  std::cout << "Cannot increment " << typeid(Value).name() << " type" << std::endl;
                			  }
                          }
                	  }
				  }









