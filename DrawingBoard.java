// Author: 	Frank Madrid
// Purpse: 	Used in Class RiverCrossing. All images and strings are drawn onto class DrawingBoard
//				and displayed within the window in JPanel panelCenter of Class RiverCrossing

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.lang.Number;


public class DrawingBoard extends JPanel implements Runnable{

	private Image leftPerson;				// Image information for the leftPerson icon
	private Image rightPerson;				// Image information for the rightPerson icon
	private ImageIcon imageOne;
	private ImageIcon imageTwo;
		
	public int width;							// Width of the DrawingBoard object
	public int height;						// Height of the DrawingBoard object
	
	private Thread animator;				// Main processing for class DrawingBoard, simulates animation
	
	// Function - DrawingBoard()
	// Default constructor which initializes the member variables of the class
	public DrawingBoard() {
	
		setBackground(Color.BLACK);
		
		// Sets the image for the leftPerson to "LeftPerson.png"
		imageOne = new ImageIcon(this.getClass().getResource("LeftPerson.png"));
		leftPerson = imageOne.getImage();
		
		// Sets the image for the rightPerson to "RightPerson.png"
		imageTwo = new ImageIcon(this.getClass().getResource("RightPerson.png"));
		rightPerson = imageTwo.getImage();
		
		width = getWidth();
		height = getHeight();
	}
	
	// Function - addNotify()
	// Creates and defines the thread which handles the main processing for class DrawingBoard
	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.start();
	}
	
	// Function - paint(Graphics gfx)
	public void paint(Graphics gfx) {
	
		
		int xPos;	// X-Coordinate for the image icon to be drawn
		int yPos;	// Y-Coordinate for the image icon to be drawn
		
		// Call parent classes paint
		super.paint(gfx);
		
		Graphics2D gfx2D = (Graphics2D)gfx;
		
		// For each Person within the linked list "leftBank", if the object's status is set to
		// MOVING, get the x and y coordinates for the object and draw the object to the screen
		for(int i = 0; i < RiverCrossing.leftBank.size(); i++) {
			if(RiverCrossing.leftBank.elementAt(i).status == RiverConstants.MOVING) {
				xPos = RiverCrossing.leftBank.elementAt(i).xPos;
				yPos = RiverCrossing.leftBank.elementAt(i).yPos;
				gfx2D.drawImage(leftPerson, xPos, yPos, this);
			}
		}
		
		// For each Person within the linked list "rightBank", if the object's status is set to
		// MOVING, get the x and y coordinates for the object and draw the object to the screen
		for(int i = 0; i < RiverCrossing.rightBank.size(); i++) {
			if( RiverCrossing.rightBank.elementAt(i).status == RiverConstants.MOVING) {
				xPos = RiverCrossing.rightBank.elementAt(i).xPos;
				yPos = RiverCrossing.rightBank.elementAt(i).yPos;
				gfx2D.drawImage(rightPerson, xPos, yPos, this);
			}
		}
		
		// Output the amount of Person objects currently waiting on each bank
		gfx2D.drawString("Left Queue: " + Integer.toString(RiverCrossing.leftWaitingCount()), 20, 100);
		gfx2D.drawString("Right Queue: " + Integer.toString(RiverCrossing.rightWaitingCount()), getWidth() - 100, 100);
	}
	
	// Function - cycle()
	// Calls the cycle function of the linked list object which in turn calls the cycle() function
	// of each of its members. The cycle functions increment or decrement the X-Coordinate of the
	// Person object if the object's status is set to "MOVING." This change in conjunction with the
	// run() function of this class simulates animation.
	public void cycle() {
		RiverCrossing.leftBank.cycle();
		RiverCrossing.rightBank.cycle();
		
		// Update the personCount of BridgeMonitor.
		RiverCrossing.bridgeMonitor.updatePersonCount();
	}
	
	// Function - run()
	// Main processing for Class DrawingBoard(). Calls cycle() and repaint() to simulate animation
	// and waits a specified amount of time before repeating.
	public void run() {
	
		while(true) {
			cycle();
			repaint();
			
			try {
				Thread.sleep(RiverConstants.DELAY);
			} catch(InterruptedException exception) {
				exception.printStackTrace();
			}
		}
		
	}
}