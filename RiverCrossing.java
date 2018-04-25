// Title: Classic Synchronization Problems
// Author: Frank Madrid
// Purpose: 1) Become familiar with multithreading
//				2) Become familiar with synchronization amongst threads

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class RiverCrossing extends JFrame implements ActionListener{

	// Flag to initiate DEBUGGING
	static boolean debugMode;

	// Instantiate the bottom panel
	JPanel panelBottom = new JPanel();

	// Intantiate the buttons which serve as the menu operations for the program.
	JButton buttonMenu[] = {	new JButton("Generate a Left person"),
		new JButton("Generate a Right person")};

	// Instantiante the center panel
	static JPanel panelCenter = new DrawingBoard();

	// Define the bridge monitor
	static BridgeMonitor bridgeMonitor = null;

	// Define the doubly linked list
	static PersonList leftBank = null;
	static PersonList rightBank = null;

	// Function - RiverCrossing()
	// Calls the various "initialize" functions to instantiate the objects of Class RiverCrossing
	public RiverCrossing() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(RiverConstants.FRAME_LENGTH, RiverConstants.FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setTitle("River Crossing Problem");
		setResizable(false);
		setVisible(true);
		
		initializeBottomPanel();
		initializePersonLists();
		initializeMonitor();
		initializeBorderLayout();
	}

	// Function - initializeBorderLayout()
	// Helper function of RiverCrossing(). Instantiates the Container "content" and defines its
	// layout, size, and the panels which occupy its panes.
	public void initializeBorderLayout() {

		// Instantiate the container
		Container content = null;

		// Instantiate the container
		content = getContentPane();

		// Set container length and height
		content.setSize(RiverConstants.CONTENT_LENGTH, RiverConstants.CONTENT_HEIGHT);

		// Define container as a layout and add the instantiated panels to the layout
		content.setLayout(new BorderLayout());		
		content.add(panelBottom, BorderLayout.SOUTH);
		content.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setBackground(Color.black);
	}

	// Function - initializeBottomPanel()
	// Helper function of RiverCrossing(). Implements the "action listener" extension to each of the
	// buttons, adds them to the bottom panel, and enables the buttons.
	public void initializeBottomPanel() {

		// Implements "ActionListener" to each button and adds them to the bototm panel.
		for(int i = 0; i < buttonMenu.length; i++) {	
			buttonMenu[i].addActionListener(this);
			panelBottom.add(buttonMenu[i]);
		}
	}

	// Function - initializePersonLists()
	// Helper function of RiverCrossing(). Instantiats each doubly linked list. One whose nodes will
	// consist of all the people on the left bank, and the other which will consist of all the people
	// in the right bank.
	public void initializePersonLists() {
		leftBank = new PersonList();
		rightBank = new PersonList();
	}

	// Function - initializeMonitors()
	// Helper function of RiverCrossing(). Instatiates the bridge monitor
	public void initializeMonitor() {

		bridgeMonitor = new BridgeMonitor();
	}

	// Function - actionPerformed(ActionEvent mouseClick)
	// Monitors the menu buttons within the bottom panel and determines if they have been activated
	// via a mouse click.
	public void actionPerformed(ActionEvent mouseClick) {

		// Instantiate the person object to be added
		Person person = null;

		// Set default value to "no button"
		int buttonClicked = RiverConstants.NOBUTTONSELECTED;

		// Determines which button has been clicked
		Object object = mouseClick.getSource();
		for(int i = 0; i < buttonMenu.length; i++) {
			if(object == buttonMenu[i]) {
				buttonClicked = i;
				break;
			}
		}

		// Processes the mouse click by performing the intended actions
		switch(buttonClicked) {

			// Generate a person moving East on the left bank and starts its processing
		case 0 : person = new Person(RiverConstants.EAST, bridgeMonitor);
			leftBank.insert(person, 0);
			person.start();
			break;

			// Generate a person moving West on the right bank and starts its processing
		case 1 : person = new Person(RiverConstants.WEST, bridgeMonitor);
			rightBank.insert(person, 0);
			person.start();
			break;
		}
	}


	// Function - leftWaitingCount()
	// Determines the amount of person objects in the linked list "leftBank" whose status is
	// currently set to "WAITING".
	static public int leftWaitingCount() {

		int count = 0;

		for(int i = 0; i < RiverCrossing.leftBank.size(); i++)
		if(RiverCrossing.leftBank.elementAt(i).status == RiverConstants.WAITING)
		count++;

		return count;
	}

	// Function - rightWaitingCount()
	// Determines the amount of person objects in the linked list "rightBank" whose status is
	// currently set to "WAITING".
	static public int rightWaitingCount() {

		int count = 0;

		for(int i = 0; i < RiverCrossing.rightBank.size(); i++)
		if(RiverCrossing.rightBank.elementAt(i).status == RiverConstants.WAITING)
		count++;

		return count;
	}

	// Function - main(String args[])
	// Main program prcoessing
	public static void main(String args[]) {

		new RiverCrossing();
		
	}

}	