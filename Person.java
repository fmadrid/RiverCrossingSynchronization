import java.util.*;

class Person extends Thread {

	int myID;		// Identification number
	
	int xPos;		// X-Coordinate of the object's image within Class DrawingBoard
	int yPos;		// Y-Coordinate of the object's image within Class DrawingBoard
	
	int direction;	// Direction at which the objct is moving
						//		"EAST" 	- Moving from Left to Right
						//		"WEST"	- Moving from Right to Left
	
	int status;		// State of the object
						//		"WAITING" 	- Object is to move on its bank
						//		"MOVING" 	- Object is currently moving across a bridge
						//		"FINISHED" 	- Object has finished moving across the river
	
	BridgeMonitor bridgeMonitor;
	
	// Function - Person(int dir)
	// Default Constructor. Defines the parameters for the Person Object.
	public Person(int dir, BridgeMonitor monitor){
		
		// Allocates a unique ID to the object equal to the number of Person objects created
		myID = RiverCrossing.leftBank.total + RiverCrossing.rightBank.total;
		
		// Sets the object's image to the default location. (The image will not be visible at this point)
		if(dir == RiverConstants.EAST)
			xPos = 0;
		if(dir == RiverConstants.WEST)
			xPos = RiverCrossing.panelCenter.getWidth() - 32;
			
		yPos = RiverCrossing.panelCenter.getHeight() - 32;
		
		direction = dir;

		// Defaults the objects to status to "WAITING"
		status = RiverConstants.WAITING;
		
		bridgeMonitor = monitor;
	}
	
	// Function - cycle()
	// Increments the x-coordinate of the object to simiulate movement across a bridge
	public void cycle() {
	
		// If the person has not finished moving across the bridge
		if(status != RiverConstants.FINISH) {
		
			// If the person is currently moving across the bridge
			if(status == RiverConstants.MOVING) {
			
				// Update its X-coordinate to simulate movement
				if(direction == RiverConstants.EAST)
					xPos += 10;
				if(direction == RiverConstants.WEST)
					xPos -= 10;
			}
			
			// If the person has traversed off the screen, set its status to "FINISHED"
			if(xPos < -32 || xPos > RiverCrossing.panelCenter.getWidth() + 32) {
				status = RiverConstants.FINISH;
			}
		}
	}
	
	// Person main processing. Initiated from Class RiverCrossing, lines 118 and 124
	public void run() {
	
		// While the object's status is currently set to WAITING, call the
		// checkBridge function.
		while(status == RiverConstants.WAITING) {
				bridgeMonitor.checkBridge(this);
		}
	}
}

