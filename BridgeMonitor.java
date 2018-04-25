// Author: Frank Madrid
// Purpse: Used in Class RiverCrossing(). Monitor object for class Person.

import java.util.*;
class BridgeMonitor  {

	int direction;				// The current flow of traffic on the bridge
									//		"WEST" - Objects move from Right to Left
									//		"EAST" - Objects move from Left to Right
									//		"NONE" - No movement of objects
										
	boolean isEmpty;			// Flag
									//		- True if no persons are moving across the bridge
									//		- False if a person is currently moving across the bridge
	
	int consecutiveCount;	// Number of consecutive person objects which originated from the same
									// side of the bridge
									
	int personCount;			// Person objects currently moving across the bridge
	
	// Function - bridgeMonitor()
	// Default consturctor for Class BridgeMonitor. Instatiates the member objects of the class
	public BridgeMonitor(){
		
		direction = RiverConstants.NONE;
		consecutiveCount = 0;
		personCount = 0;
	}
	
	// Function - checkBridge(Person person)
	// Synchronized member function of Class RiverCrossing(). Complets the following processes
	//		1) Determine if the person object can move across the bridge
	//		2) Update parameters
	//		3) If the object cannot move, then wait
	public synchronized void checkBridge(Person myPerson) {
		
		int tempDirection;	// Temporarily stores the bridge's direction while the bridge empties	
									//	if the consecutiveCount passes its maximum value
		
		// If the person object is allowed to move across the bridge
		if(canMove(myPerson)) {
		
			// If the object's direction is equal to the current direction of the bridge, this implies
			// the object is not the first of its type to cross; therefore increment consecutiveCount.
			// Otherwise, reset consectuiveCount to 0.
			if(direction == myPerson.direction)
					consecutiveCount++;
			else
				consecutiveCount = 0;
			
			personCount++;
			myPerson.yPos = 20;
			myPerson.status = RiverConstants.MOVING;
			direction = myPerson.direction;
			
			// If consecutiveCount has reached its maximum and if an object is waiting on the other side
			// of the bridge, do not let any new objects on the bridge and wait until the bridge is empty
			if(consecutiveCount >= RiverConstants.MAXIMUMCONSECUTIVE && isWaiting(myPerson.direction)) {
				tempDirection = direction;
				direction = RiverConstants.NONE;
				while(!isEmpty()) {}
				direction = tempDirection;
			}		
		}
		// Notifies all other processes that checkBridge is available to be checked once again.
		try {
			myPerson.sleep(300);
		} catch(InterruptedException exception){}
		notify();
		try {
			wait();
		} catch(InterruptedException exception) {
			exception.printStackTrace();
		}
	}
	
	// Function - canMove(Person myPerson)
	// Helper function to checkBridge(). Checks the following cases
	//			Case 1:	The bridge is currently empty
	//					1.1 - Empty because there was no one waiting (Move)
	//					1.2 - Empty because the consecutiveCount reached its maximum while person
	//							objects on the otherside are waiting (Wait)
	//						1.2.1 - Does direction match the previous direction (Wait)
	//						1.2.2 - Does direction not match the previous direction (Move)
	//			Case 2:	The bridge is currently not empty
	//					2.1 - Person objects on the bridge share a direction
	//						2.1.1 - consecutiveCount has not been reached (Move)
	//						2.1.2 - consecutiveCount has been reached (Wait)
	public boolean canMove(Person myPerson) {
		
		// Case 1
		if(isEmpty()) {
		
			// Case 1.1
			if(!isWaiting(myPerson.direction))
				return true;
				
			// Case 1.2
			if(consecutiveCount >= RiverConstants.MAXIMUMCONSECUTIVE)
			
				// Case 1.2.2
				if(myPerson.direction != direction)
					return true;
					
		}
		
		// Case 2
		if(!isEmpty()) {
		
			// Case 2.1
			if(myPerson.direction == direction)
				
				// Case 2.1.1
				if(consecutiveCount < 4)
					return true;
		}
		
		return false;
		
	}
	
	// Function - isEmpty()
	// Returns true if the bridge is empty; otherwise, returns false
	public boolean isEmpty() {
		
		// For each element within the linked list leftBank in Class RiverCrossing(), if the status
		// of the element is "MOVING" then the object is moving across the bridge which implies the
		// bridge is not empty
		for(int i = 0; i < RiverCrossing.leftBank.size(); i++)
				if(RiverCrossing.leftBank.elementAt(i).status == RiverConstants.MOVING)
					return false;
		// For each element within the linked list rightBank in Class RiverCrossing(), if the status
		// of the element is "MOVING" then the object is moving across the bridge which implies the
		// bridge is not empty
		for(int i = 0; i < RiverCrossing.rightBank.size(); i++)
			if(RiverCrossing.rightBank.elementAt(i).status == RiverConstants.MOVING)
				return false;
		
		return true;
	}
	
	// Function - isWaitng(int direction)"
	// Determines if there exist person objects on the opposite side of the bridge that are waiting
	// to cross.
	public synchronized boolean isWaiting(int direction) {
	
		// If the object's direction is West, the object is on the left bank which implies we must
		// check the left bank
		if(direction == RiverConstants.WEST)
			if(RiverCrossing.leftWaitingCount() == 0) {
				return false;
			}
		
		// If the object's direction is East, the object is on the right bank which implies we must
		// check the right bank
		if(direction == RiverConstants.EAST)
				if(RiverCrossing.rightWaitingCount() == 0) {
					return false;
				}
		return true;
	}
	
	// Funtion - updatePersoncount()
	// Helper function to cycle() in Class DrawingBoard. Counts all objects within leftBank
	public void updatePersonCount() {
		int count = 0;
		int finished = 0;
		for(int i = 0; i < RiverCrossing.leftBank.size(); i++) {
			count++;
			if(RiverCrossing.leftBank.elementAt(i).status == RiverConstants.FINISH)
				finished++;
		}
		
		for(int i = 0; i < RiverCrossing.rightBank.size(); i++) {
			count++;
			if(RiverCrossing.rightBank.elementAt(i).status == RiverConstants.FINISH)
				finished++;
		}
		
		count -= finished;
		personCount = count;
	}
}