//Class MyList.java
//Implemented in Class RiverCrossing
import java.util.*;

public class PersonList implements Iterable {
	
	int count;			//The length of MyList
	Node front;			//The first node in MyList
	Node rear;			//The last node in MyList
	
	int total;			// Number of times a Node was inserted into the list. Implemented in Class
							// RiverCrossing (Homework ^)
						
	// Function - MyList()
	// Default constructor for the class. Instantiates the front and rear nodes to null, and sets
	// the count to 0.
	public PersonList() {
		front = null;
		rear = null;
		count = 0;
		total = 0;
	}
	
	// Function - isEmpty(). 
	// Returns true if MyList is empty; otherwise, returns false.
	public boolean isEmpty() {
		return(count == 0);
	}
	
	// Function size(). 
	// Returns the count value.
	public int size() {
		return count;
	}
	
	// Function - insert(C element, int position)
	// Inserts a node into MyList and increments the count. Returns MyList after insertion.
	public PersonList insert(Person element, int position) {
		
		Node newNode = new Node(element);	//Node which will be inserted into MyList
		
		// If MyList is currently empty, update the front and rear node and increments count
		if(isEmpty()) {
		
			front = newNode;		// newNode = front;
			rear = newNode;		// newNode = rear;
			count ++;
			total++;
			
			return this;
		}
		
		// If inserting at the beginning of the list, update the front pointer and increment count
		if(position <= 0) {
			
			newNode.next = front;		//             newNode -> front
			front.previous = newNode;	// [previous <- front] -> newNode
			front = newNode;				// front = newNode
			count++;
			total++;
			
			return this;
		}
		
		// If inserting at the end of the list, update the rear pointer and increment count.
		else if(position >= count) {
		
			newNode.previous = rear;	// [previous <- newNode] -> rear
			rear.next = newNode;			//                  rear -> newNode
			rear = newNode;				// rear = newNode
			count++;
			total++;
			
			return this;
		}
		
		// If inserting within the list (implied), traverses the list to the specified position, 
		// updates the neighboring points and increments the count.
		else {
			// Traverse the list to the specified position
			Node currentNode = front;
			for(int i = 0; i < position; i++)
				currentNode = currentNode.next;
			
			//Update pointers with left neighbor
			currentNode.previous.next = newNode;		// [[previous <- currentNode] -> next] -> newNode
			newNode.previous = currentNode.previous;	//               [previous <- newNode] -> [previous <- currentNode]
			
			//Update pointers with right neighbor
			currentNode.previous = newNode;				// [previous <- currentNode] -> newNode
			newNode.next = currentNode;					//         [newNode -> next] -> currentNode
			
			count++;
			total++;
		}
		
		return this;
		
	}
	
	// Function - remove(int position)
	// Removes the node at the indicated position and decrements the count. Returns the removed
	// element.
	public void remove(int position) {
		
		// If the list is currently empty, break out of the function
		if(count <- 0)
			return;
			
		Node currentNode = front;
		
		//If currentNode is the only element in MyList
		if(count == 1) {
		
			front = null;	//Update front
			rear = null;	//Update rear
			currentNode.next = currentNode.previous = null;	//Remove bode
			
			count--;
			System.out.println("Testing");
			
			return;
		}
		
		// Traverses the list to the specified position, update the neighbor points and remove the node
		for(int i = 0; i < position; i++)
			currentNode = currentNode.next;
			
		// If the front node is being removed, update the front pointer
		if(front == currentNode) {
		
			front = currentNode.next;								// front = [currentNode -> next]
			currentNode.next.previous = null;					// [[previous <- currentNode] -> next] -> null
			
			currentNode.next = currentNode.previous = null;	//               [currentNode -> next] -> null
																			//           [currentNode -> previous] -> null
			
			count--;
			
			return;
		}
		
		// If the rear node is being removed, update the rear pointer
		if(rear == currentNode) {
		
			rear = currentNode.previous;							// rear = [previous -> currentNode]
			currentNode.previous.next = null;					// [[previous <- currentNode] -> next] -> null
			
			currentNode.next = currentNode.previous = null;	//               [currentNode -> next] -> null
																			//           [previous <- currentNode] -> null
			
			count--;
			
			return;
		}
		
		currentNode.previous.next = currentNode.next;		// [[previous <- currentNode] -> next -> [currentNode -> next]
		currentNode.next.previous = currentNode.previous;	// [previous <- [currentNode -> next] -> [previous <- currentNode]
		
		currentNode.next = null;		//     [currentNode -> next] -> null
		currentNode.previous = null;	// [previous <- currentNode] -> null
		
		count--;
		
		return;
	}
	
	// Function - find(int ID)
	// Added for functionality for Class RiverCrossing (Homework 6). Searches the list for an element
	// with an id which matches the specfied id
	public int find(int ID) {
		
		Node currentNode = front;	
		
		for(int i = 0; i < count; i++) {
			if(ID == currentNode.data.myID)
				return i;
			
			currentNode = currentNode.next;
		}
		
		return -1;
	}
	
	// Function - cycle()
	//	Added for functionality for Class RiverCrossing (Homework 6). Calls the function cycle() of
	// each Person object within each node in the list
	public void cycle() {
		
		Node currentNode = front;
		
		for(int i = 0; i < count; i++) {
			currentNode.data.cycle();
			currentNode = currentNode.next;
		}
			
	}
	
	// Function - elementAt(int position)
	// Added for functionality for Class RiverCrossing (Homework 6). Traverses the list returning the
	// Person object located at the specified position
	public Person elementAt(int position) {
	
		Node currentNode = front;
		
		for(int i = 0; i < position; i++)
			currentNode = currentNode.next;
		
		return currentNode.data;
		
	}
	// Function - iterator()
	// 
	public Iterator iterator() {
		return iterator(true);
	}
	
	// Function iterator(boolean direction)
	//
	public Iterator iterator(boolean direction) {
		return new ListIterator(direction);
	}
	
	// Class Node
	protected class Node {
		Person data;
		Node next, previous;
		
		// Function - Node(C input)
		//
		public Node(Person input) {
			next = null;
			previous = null;
			data = input;
		}
	}
	
	// Class ListIterator
	protected class ListIterator implements Iterator {
		
		protected Node currentNode;
		protected boolean forward;
		
		// Function - ListIterator
		//
		public ListIterator(boolean direction) {
			
			forward = direction;
			
			if(direction) currentNode = front;
			else currentNode = rear;
		}
		
		// Function - hasNext()
		//
		public boolean hasNext() {
			return (currentNode != null);
		}
		
		// Function - next()
		//
		public Person next() {
		
			Person element = currentNode.data;
			
			if(forward) currentNode = currentNode.next;
			else currentNode = currentNode.previous;
			
			return element;
		}
		
		// Function - remove()
		// 
		public void remove(){
		}
	}
}