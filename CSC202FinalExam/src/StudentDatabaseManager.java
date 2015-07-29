/**
 * StudentDatabaseManager presents a GUI which allows a user to add and interact with sorted student objects
 * 
 * Matt Warman
 * Date: 05/06/2015
 * Version: 1.4
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class StudentDatabaseManager extends Application {
	
	// Initialize constants (Trying to move away from "magic numbers")
	public static final int MAIN_WINDOW_WIDTH = 885; // Sets the width of the main window (Was 870, but 885 plays nicely with a vertical scrollbar)
	public static final int MAIN_WINDOW_HEIGHT = 330; // Sets the height of the main window (Large enough to hold 12 entries without scrollbar
	public static final int DEFAULT_NODE_WIDTH = 100; // Sets the default width of many nodes
	public static final String DEFAULT_COMBOBOX_TEXT = "Select One"; // Sets the initial placeholder text in ComboBoxes
	
	// Initialize groups and lists
	static List<Student> Students = new ArrayList<Student>(); // Initialize an array list of Student objects
	
	// Initialize variables
	private TextField tfStudentID = new TextField(); // Input field for student ID
	private TextField tfStudentName = new TextField(); // Input field for student name
	private TextField tfStudentCity = new TextField(); // Input field for student city
	private TextArea taOutput = new TextArea(); // Output area for listing student entries
	private Button btLeft = new Button("<"); // Serve as a "left" button
	private Button btRight = new Button(">"); // Serve as a "right" button
	private Button btReset = new Button("Reset"); // Serve as a reset button to delete all entries
	private Button btNew = new Button("New"); // Serve as a new entry button to reset input fields and get the program ready to accept a new student
	private Button btSave = new Button("Save"); // Serve as both a save and an overwrite button to save student data
	private Button btOK = new Button("OK"); // Serve as an OK button to dismiss a dialog box
    private Button btYes = new Button("Yes"); // Serve as a button that closes an alert dialog box, confirming the action
    private Button btCancel = new Button("Cancel"); // Serve as a button that closes an alert dialog box, canceling the action
	private ComboBox<Object> cbStudentState = new ComboBox<Object>(); // Initialize a new ComboBox of strings for state selection
	private ComboBox<String> cbStudentClass = new ComboBox<String>(); // Initialize a new ComboBox of strings for class selection
	Separator separator = new Separator(); // Serve as a separator in an observable list. It is define here so it can be determined if it is selected
	
	/*
	 * Serve as a status indicator to keep track of whether the user is editing an entry or adding more
	 * 
	 * When (selected == -1), the program will know the information being input should be read with the intention of adding a new student
	 * When (selected != -1), the program will know that the user is editing an entry, conveniently, 'selected' will keep track of the index
	 *	 of the student entry being edited. This aids in quite a few places. For example, this ensures that the user can overwrite the entry
	 * 	 with the same student ID as before, without being alerted that that id is currently in use.
	 */
	int selected = -1; // Helpful little bugger. See above ^
	
    // Initialize options to be used to populate the State ComboBox
    private ObservableList<Object> states = FXCollections.observableArrayList("Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado",
    		"Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
    		"Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada",
    		"New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
    		"Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia",
    		"Wisconsin", "Wyoming", separator, "District of Columbia", "Puerto Rico", "Guam", "American Samoa", "U.S. Virgin Islands",
    		"Northern Mariana Islands");
    
    // Initialize options to be used to populate the Class ComboBox
    private ObservableList<String> classes = FXCollections.observableArrayList("Freshman", "Sophomore", "Junior", "Senior");
	
	Scene scene; // Initialize the scene
	
	BorderPane mainWindow;
    
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage)
	{
		// Construct the GUI
		setUpGUI(); // Set up all the GUI components
		primaryStage.setTitle("Student Database Manager"); // Set the window title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
		
		// Set up actions
		btLeft.setOnAction(e -> left()); // Set up left button action
		btRight.setOnAction(e -> right()); // Set up right button action
		btReset.setOnAction(e -> reset()); // Set up "Reset" button action
		btNew.setOnAction(e -> newEntry()); // Set up "New" button action
		btSave.setOnAction(e -> saveEntry()); // Set up "Save" button action
		
		setUpEnterKeyAction(); // Set up enter key actions
	}
	
	// Blank out all the student attribute input fields and set up the program for accepting new Student entries
	public void newEntry()
	{
		// If text was wrong prior to user selecting "new", remove the "wrong" style
		if (tfStudentID.getStyleClass().contains("wrong"))
		{
			tfStudentID.getStyleClass().removeAll("wrong"); // Remove the "wrong" style
			tfStudentID.getStyleClass().add("normal"); // Add the "normal" style
		}
		
		if (selected != -1) // If the user was editing a selection prior to clicking "New"
		{
			// Change selected to -1, indicating the user is no longer editing existing Students
			selected = -1;
			
			// Change the save button from "Overwrite" to "Save"
			btSave.setText("Save");
			
			// Change node CSS from "edit" to "normal"
			tfStudentID.getStyleClass().removeAll("edit");
			tfStudentID.getStyleClass().add("normal");
			tfStudentName.getStyleClass().removeAll("edit");
			tfStudentName.getStyleClass().add("normal");
			tfStudentCity.getStyleClass().removeAll("edit");
			tfStudentCity.getStyleClass().add("normal");
			btSave.getStyleClass().removeAll("edit");
			btSave.getStyleClass().add("normal");
			
			// De-select lines in the output text area
			taOutput.positionCaret(0);
		}
		
		// Clear text fields and reset ComboBoxes to DEFAULT_COMBOBOX_TEXT
		tfStudentID.clear();
		tfStudentName.clear();
		tfStudentCity.clear();
		cbStudentState.setValue(DEFAULT_COMBOBOX_TEXT);
		cbStudentClass.setValue(DEFAULT_COMBOBOX_TEXT);
	}
	
	// Save a new entry or edit an existing one
	public void saveEntry()
	{
		if(!verifyCorrectInput()) // Determine if input information is valid and usable
		{
			return; // If input contained invalid information, exit the function without making any changes
		}
		
		if (selected == -1) // If the user is adding a new student rather than editing an existing one
		{
			if (duplicateID(Integer.parseInt(tfStudentID.getText()))) // Determine if the entered student ID has already been taken or not
			{
				// If it has been taken already, alert the user...
				alertDialog("ID Already Taken", "A student with this ID already exists.\nPlease choose another.", 300, 100);
				
				// ... and change the text color to red
				tfStudentID.getStyleClass().removeAll("normal"); // Remove the "normal" style if necessary
				tfStudentID.getStyleClass().add("wrong"); // Add the "wrong" style
				
				return; // Exit the function without making any changes
			}
			else // The entered student ID has not already been taken
			{
				// Change the text color to black in case it was previously changed to red
				tfStudentID.getStyleClass().removeAll("wrong"); // Remove the "wrong" style if applicable
				tfStudentID.getStyleClass().add("normal"); // Add the "normal" style
			}
			
			// After passing the previous tests, add the student to the Students array using the input data.
			Students.add(new Student(Integer.parseInt(tfStudentID.getText()), tfStudentName.getText(), tfStudentCity.getText(),
					cbStudentState.getValue().toString(), cbStudentClass.getValue()));
		}
		
		
		/*
		 * Probably wont ever be called, but if the user has managed to get the program in edit mode (selected != -1) without there being any
		 * 	student objects, and then attempts to overwrite something, clear the inputs, set the program up for new entries, and exit the saveEntry
		 * 	function as there are no entries to overwrite
		 */
		else if (Students.size() == 0) // Program is in edit mode (selected != -1), but there are no student entries
		{
			newEntry(); // Clear inputs, reset selected to -1, get the program ready for new entries 
			return; // Exit the function since there's nothing to overwrite
		}
		
		else // The user is attempting to edit an entry (selected != -1) and there are existing entries making editing possible
		{
			/*
			 * Determine if the input student ID has already been taken
			 * 
			 * NOTE: duplicateID will only report true if the input student ID matches an entry OTHER THAN the one being edited.
			 * This allows the user to edit a single student's information while keeping its ID the same without it being flagged as duplicate
			 * 	and still ensuring that the user doesn't attempt to reuse a different student's ID.
			 */
			if (duplicateID(Integer.parseInt(tfStudentID.getText()))) // Make sure the ID isn't attributed to a different student
			{
				// If it is, alert the user...
				alertDialog("ID Already Taken", "A student with this ID already exists.\nPlease choose another.", 300, 100);
				
				// ...and change the text color to red
				tfStudentID.getStyleClass().removeAll("edit"); // Remove the "edit" style if necessary
				tfStudentID.getStyleClass().add("wrong"); // Add the "wrong" style
				
				return; // Exit the function without making any changes
			}
			else // The student ID is not a duplicate
			{
				// Change the text color to blue in case it was previously changed to red
				tfStudentID.getStyleClass().removeAll("wrong"); // Remove the "wrong" style if applicable
				tfStudentID.getStyleClass().add("edit"); // Add the "edit" style
			}
			
			/*
			 * Remove the student and re-add them with the new information. This puts them at the bottom of the list and allows for quick,
			 * single-pass sorting.
			 */
			// Remove the student
			Students.remove(selected);
			// Re-add the student with the new information
			Students.add(new Student(Integer.parseInt(tfStudentID.getText()), tfStudentName.getText(), tfStudentCity.getText(),
					cbStudentState.getValue().toString(), cbStudentClass.getValue()));
		}
		
		sort(); // A new index exists at the bottom of the list, insert it in the correct location within the array
		
		refreshOutput(); // Refresh the output to reflect changes
	}
	
	// Select the previous student
	public void left()
	{
		if (Students.size() == 0) // If there are no student objects present to edit
		{ 
			// Alert the user
			alertDialog("No Enteries To Edit", "There are no students available to edit.\nTry adding some students first.", 325, 100);
			return; // Exit the function
		}
		
		/*
		 * If there was something wrong with a previously entered student ID, causing it to have the "wrong" CSS style attributed to it,
		 * 	remove the the "wrong" style and add the "edit" style 
		 */
		if (tfStudentID.getStyleClass().contains("wrong")) // If the student ID text field has the "wrong" CSS style attributed to it
		{
			// Change the text color from bold red to normal blue
			tfStudentID.getStyleClass().removeAll("wrong"); // Remove the "wrong" style if applicable
			tfStudentID.getStyleClass().add("edit"); // Add the "edit" style
		}
		
		/*
		 * If (selected == -1), meaning the user was not previously editing anything but now chooses to do so, set up the program and the GUI
		 * 	for editing entries
		 */
		if (selected == -1) // If the current program is currently set up to accept new students
		{
			// Change the "Save" button text to "Overwrite"
			btSave.setText("Overwrite");
			
			// Change CSS styles from "normal" to "edit"
			tfStudentID.getStyleClass().removeAll("normal");
			tfStudentID.getStyleClass().add("edit");
			tfStudentName.getStyleClass().removeAll("normal");
			tfStudentName.getStyleClass().add("edit");
			tfStudentCity.getStyleClass().removeAll("normal");
			tfStudentCity.getStyleClass().add("edit");
			btSave.getStyleClass().removeAll("normal");
			btSave.getStyleClass().add("edit");
			
			/*
			 * Set the selected to 0, both selecting the first student entry and changing the program state from
			 * 	accepting new entries (selected == -1) to editing entries (selected != -1)
			 */
			selected = 0;
		}
		
		// If the selected entry isn't the first one (Students array index 0)
		if (selected != 0)
		{
			selected--; // Decrement the selected number, selecting the previous entry.
		}

		refreshOutput(); // Refresh the output, showing the newly selected entry
		load(selected); // Load the data from the selected entry into the input boxes to be edited
	}
	
	// Select the next student
	public void right()
	{
		if (Students.size() == 0) // If there are no student objects present to edit
		{ 
			// Alert the user
			alertDialog("No Enteries To Edit", "There are no students available to edit.\nTry adding some students first.", 325, 100);
			return; // Exit the function
		}
		
		/*
		 * If there was something wrong with a previously entered student ID, causing it to have the "wrong" CSS style attributed to it,
		 * 	remove the the "wrong" style and add the "edit" style 
		 */
		if (tfStudentID.getStyleClass().contains("wrong")) // If the student ID text field has the "wrong" CSS style attributed to it
		{
			// Change the text color from bold red to normal blue
			tfStudentID.getStyleClass().removeAll("wrong"); // Remove the "wrong" style if applicable
			tfStudentID.getStyleClass().add("edit"); // Add the "edit" style
		}
		
		/*
		 * If (selected == -1), meaning the user was not previously editing anything but now chooses to do so, set up the program and the GUI
		 * 	for editing entries
		 */
		if (selected == -1) // If the current program is currently set up to accept new students
		{
			// Change the "Save" button text to "Overwrite"
			btSave.setText("Overwrite");
			
			// Change CSS styles from "normal" to "edit"
			tfStudentID.getStyleClass().removeAll("normal");
			tfStudentID.getStyleClass().add("edit");
			tfStudentName.getStyleClass().removeAll("normal");
			tfStudentName.getStyleClass().add("edit");
			tfStudentCity.getStyleClass().removeAll("normal");
			tfStudentCity.getStyleClass().add("edit");
			btSave.getStyleClass().removeAll("normal");
			btSave.getStyleClass().add("edit");
			
			/*
			 * Set the selected to (Students.size() - 1), both selecting the last student entry and changing the program state from
			 * 	accepting new entries (selected == -1) to editing entries (selected != -1)
			 */
			selected = (Students.size() - 1); // (Students.size() - 1) references the last entry index number in the Students array
		}
		
		// If the selected entry isn't the last one (Students array index (Students.size() - 1))
		if (selected != (Students.size() - 1))
		{
			selected++; // Increment the selected number, selecting the next entry.
		}

		refreshOutput(); // Refresh the output, showing the newly selected entry
		load(selected); // Load the data from the selected entry into the input boxes to be edited
	}
	
	// Load data from a given selected entry index (int index) into the input area to be edited
	public void load(int index)
	{
		// This shouldn't be called since both the left() and right() functions return if the Students array is empty
		if (Students.size() == 0) // If there are no entries
		{
			return; // Exit the function without loading anything since theres nothing to load
		}
		
		// Load the data from the selected index into its respective input fields
		tfStudentID.setText(Integer.toString(Students.get(index).getNumber())); // Load the student number as a string before passing it
		tfStudentName.setText(Students.get(index).getName());
		tfStudentCity.setText(Students.get(index).getCity());
		
		// Load the previously selected values from the selected index into their respective ComboBoxes
		cbStudentState.setValue(Students.get(index).getState());
		cbStudentClass.setValue(Students.get(index).getClassName());
	}
	
	// Sort the Students array by student ID in ascending order
	public void sort()
	{
		/*
		 * Insertion sort was chosen since only one value will ever need to be sorted into a the Students list at any given time
		 * 	Furthermore, prior to insertion, the array will already be sorted since each entry is inserted into the array in the
		 * 	correct location upon creation.
		 * Plus, insertion sort is very efficient for small arrays, even more so when only one element at the end of the array is out of order.
		 */
		
		
		int sortIndex = Students.size() - 1; // Set sortIndex as the last entry in the Students array (which holds new entries prior sorting)
		
		/*
		 * While sortIndex doesn't equal 0 (the first object index and smallest position)
		 * 	--and--
		 * 	the student ID value of the student at array index 'sortIndex' is less than the student ID value of the student immediately before it 	
		 */
		while (sortIndex > 0 && Students.get(sortIndex).getNumber() < Students.get(sortIndex-1).getNumber())
		{
			/*
			 * Swap the student at array index sortIndex with the student immediately before it, since the student at array index sortIndex
			 * 	has a lower student ID
			 */
			Collections.swap(Students, sortIndex, sortIndex-1);
			
			sortIndex--; // Decrement the sortIndex number
		}
		
		// If sort() was called while selected was being edited, change the selected array index number to that of the selected entry's new location
		if (selected != -1)
		{
			selected = sortIndex; // Change the selected index to the selected entry's new index location
		}
	}
	
	// Refresh the list of students and select the entry being edited when applicable
	public void refreshOutput()
	{
		// Clear previous data and set output header
		taOutput.setText("ID          Name                        City                        State                     Class    \n"
				+ "-------------------------------------------------------------------------------------------------------");
		
		// For every student entry in Students
		for (int i = 0; i < Students.size(); i++) // Start at index 0 and go through the entire Students array
		{
			taOutput.appendText("\n" + Students.get(i).toString()); // Append information about the student at the current index to the output
		}
		
		// If the user is currently editing an entry, highlight that entry
		if (selected != -1)
		{
			taOutput.requestFocus(); // taOutput must have focus to correctly highlight text
			
			// Highlight (select) text corresponding to the entry being edited from the beginning of the line to the end
			taOutput.selectRange(104 * (selected + 2), 104 * (selected + 3));
		}
	}
	
	// Ensure that all entered information is correct
	public boolean verifyCorrectInput()
	{
		int studentID = 0; // Aids in determining whether the user input a valid integer or not
		
		if (tfStudentID.getText().equals("")) // If the Student ID field is empty
		{
			alertDialog("Input Student ID", "Please input a Student ID", 275, 75); // Alert the user
			return false; // Return false, since insufficient data was entered
		}
		
		try
		{
			// Attempt to parse an integer from the Student ID input text field
			studentID = Integer.parseInt(tfStudentID.getText());
		}
		catch (Exception e) // If whatever was entered in the Student ID text field could not be parsed as an integer
		{
			// Alert the user
			alertDialog("Invalid Input", "Please ensure that the Student ID is a positive integer\ngreater than 0 and less than 2,147,483,648", 410, 90);
			
			// Change the text color to red
			tfStudentID.getStyleClass().removeAll("edit"); // Remove the "edit" style if necessary
			tfStudentID.getStyleClass().removeAll("normal"); // Remove the "normal" style if necessary
			tfStudentID.getStyleClass().add("wrong"); // Add the "wrong" style
			
			return false; // Return false since invalid input was input
		}
		
		if (studentID < 0) // If the input student ID was negative
		{
			// Alert the user
			alertDialog("Invalid Input", "Please ensure that the Student ID is a positive integer\ngreater than 0 and less than 2,147,483,648", 410, 90);
			
			// Change the text color to red
			tfStudentID.getStyleClass().removeAll("edit"); // Remove the "edit" style if necessary
			tfStudentID.getStyleClass().removeAll("normal"); // Remove the "normal" style if necessary
			tfStudentID.getStyleClass().add("wrong"); // Add the "wrong" style
			
			return false; // Return false since invalid input was input
		}
		
		// Reset red text to black or blue
		if (tfStudentID.getStyleClass().contains("wrong"))
		{	
			// Remove the "wrong" style
			tfStudentID.getStyleClass().removeAll("wrong");
			
			// Add the correct style based on whether entries are being edited or not
			if (selected == -1) { tfStudentID.getStyleClass().add("normal"); } // Change text to black
			else { tfStudentID.getStyleClass().add("edit"); } // Change text to blue
		}
		
		if (tfStudentName.getText().equals("")) // If the Student Name field is empty
		{
			// Alert the user
			alertDialog("Input Name", "Please input a Student Name", 275, 75);
			return false; // Return false, since insufficient data was entered
		}
		
		if (tfStudentCity.getText().equals("")) // If the Student City field is empty
		{
			// Alert the user
			alertDialog("Input City", "Please input a Student City", 275, 75);
			return false; // Return false, since insufficient data was entered
		}
		
		// If the user selected the separator in the Student State ComboBox or never selected a state
		if (cbStudentState.getValue().equals(separator) || cbStudentState.getValue().equals(DEFAULT_COMBOBOX_TEXT))
		{
			// Alert the user
			alertDialog("Select State", "Please select a Student State", 275, 75);
			return false; // Return false, since insufficient or invalid data was entered
		}
		
		// If the user never selected a Class Name
		if (cbStudentClass.getValue().equals(DEFAULT_COMBOBOX_TEXT))
		{
			// Alert the user
			alertDialog("Select Class", "Please select a Student Class", 275, 75);
			return false; // Return false, since insufficient data was entered
		}
		
		return true; // If all tests passed, return true
	}
	
	// This method does all the heavy lifting of setting up the GUI and all the miscellaneous node preferences
	public void setUpGUI()
	{
		// Initialize main window
		mainWindow = new BorderPane(); // A BorderPane makes a nice main window
		mainWindow.setPadding(new Insets(10, 10, 10, 10)); // Set some padding around everything in the main window
		mainWindow.getStylesheets().add("stylesheet.css"); // Load the CSS
		
		// Initialize top bar for inputting and editing student information
		HBox topBar = new HBox(7); // Separate nodes by 7 pixels
		topBar.setAlignment(Pos.CENTER); // Align the topBar HBox so that it lies in the center of its section of the window
		topBar.setPadding(new Insets(0, 0, 10, 0)); // Set some padding between the topBar nodes and the output text area

		{ // Set top bar node properties and add them to the top bar (Separated for ease of reading)
			
			Insets labelSpace = new Insets(0, 0, 0, 15); // labelSpace serves as the space between separate topBar node groups (label and field)
			
			// Set up labels
			Label lbStudentID = new Label("Student ID:");
			Label lbStudentName = new Label("Name:");
			Label lbStudentCity = new Label("City:");
			Label lbStudentState = new Label("State:");
			Label lbStudentClass = new Label("Class:");
			
			// Separate nodes nicely in a controlled manner using labelSpace to separate labels from previous fields
			lbStudentName.setPadding(labelSpace);
			lbStudentCity.setPadding(labelSpace);
			lbStudentState.setPadding(labelSpace);
			lbStudentClass.setPadding(labelSpace);
			
			// Set node widths
			tfStudentID.setPrefWidth(DEFAULT_NODE_WIDTH);
			tfStudentName.setPrefWidth(DEFAULT_NODE_WIDTH);
			tfStudentCity.setPrefWidth(DEFAULT_NODE_WIDTH);
			cbStudentState.setPrefWidth(115);
			cbStudentClass.setPrefWidth(115);
			
			// Set other node properties
			cbStudentState.setItems(states); // Add state values to states ComboBox
			cbStudentState.setValue(DEFAULT_COMBOBOX_TEXT); // Set placeholder text
			cbStudentClass.setItems(classes); // Add class name values to class name ComboBox
			cbStudentClass.setValue(DEFAULT_COMBOBOX_TEXT); // Set placeholder text
			
			// Add the nodes to the topBar
			topBar.getChildren().addAll(lbStudentID, tfStudentID, lbStudentName, tfStudentName, lbStudentCity, tfStudentCity, lbStudentState,
					cbStudentState, lbStudentClass, cbStudentClass);
		}
		
		// Initialize bottom bar for buttons (Left, Right, "Clear", "New", "Save")
		HBox bottomBar = new HBox(85); // Separate nodes by 85 pixels
		bottomBar.setAlignment(Pos.CENTER_RIGHT); // Align the bottomBar HBox so that it lies in the center of its section of the window
		bottomBar.setPadding(new Insets(10, 0, 0, 0)); // Separate from output text area
		
		{ // Set bottom bar node properties and add them to the bottom bar (Separated for ease of reading)	
			
			/*
			 * I chose to use two additional HBoxes to set up the one bottomBar because I wanted the left and right buttons to lie in the middle
			 * 	of the window while the "Reset", "New" and "Save" buttons stayed to the right of the window.
			 * Grouping these nodes into two larger nodes and setting them apart using bottomBar's node separation value made this possible
			 */
			
			// Set up left and right button section for bottom bar
			HBox leftRightButtons = new HBox(5); // Separate nodes by 5 pixels
			leftRightButtons.setAlignment(Pos.CENTER); // Center align nodes
			leftRightButtons.getChildren().addAll(btLeft, btRight); // Add the left and right buttons
			
			// Set up New and Save button section for bottom bar
			HBox newSaveButtons = new HBox(10); // Separate nodes by 10 pixels
			
			// Set button widths
			btReset.setPrefWidth(DEFAULT_NODE_WIDTH); // Default width
			btNew.setPrefWidth(DEFAULT_NODE_WIDTH); // Default width
			btSave.setPrefWidth(DEFAULT_NODE_WIDTH); // Default width
			
			// Set other node properties and add them to the newSaveButtons HBox
			newSaveButtons.setAlignment(Pos.CENTER); // Center buttons
			newSaveButtons.getChildren().addAll(btReset, btNew, btSave); // Add buttons

			// Set bottom bar node properties and add them to the bottom bar
			bottomBar.getChildren().addAll(leftRightButtons, newSaveButtons);
		}
		
		// Set up and add elements to main window
		taOutput.setWrapText(true); // Turn on text wrapping so that an unnecessary horizontal scrollbar doesn't appear
		taOutput.setEditable(false); // Set the output area to be un-editable
		taOutput.getStyleClass().add("output"); // Add the "output" style
		// Set up header
		taOutput.setText("ID          Name                        City                        State                     Class    \n"
				+ "-------------------------------------------------------------------------------------------------------");
		
		// Set sub section locations in main window
		mainWindow.setTop(topBar); // Put the topBar in the Top section
		mainWindow.setCenter(taOutput); // Put taOutput in the Center section
		mainWindow.setBottom(bottomBar); // Put the bottomBar in the Bottom section
		
		// Define the scene and window size
		scene = new Scene(mainWindow, MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
	}
	
	// This method determines if the input student ID already exists in the array
	public boolean duplicateID(int idNumber)
	{
		int maxIndex = Students.size() - 1; // Define the max index as the last index in the Students array (Students.size() - 1)
		int minIndex = 0; // Define the min index as the first index in the Students array (0)
		
		while (maxIndex >= minIndex) // While the maxIndex value is greater than the minIndex value (if (maxIndex < minIndex) no duplicate was found)
		{
			// Set midpointIndex as the midpoint between minIndex and maxIndex. Integer rounding is expected and poses no issue
			int midpointIndex = minIndex + ((maxIndex-minIndex)/2);
			
			/*
			 * If the input idNumber is equal to the student ID of the student at the midpointIndex of the Students array
			 * --AND--
			 * the midpointIndex is NOT the same index as the 'selected' index for editing
			 * 
			 * This way if an entry is selected for editing (with its Students array index stored in 'selected'), but its ID is not changed,
			 * 	it wont be flagged as a duplicate upon overwriting, but using another entry's student ID will be flagged as a duplicate.
			 */
			if (idNumber == Students.get(midpointIndex).getNumber() && midpointIndex != selected) // See above ^
			{
				return true; // Return true indicating a duplicate was detected
			}
			
			// If the input student ID number was greater than the student ID number of the student at the midpointIndex of the Students array
			else if (idNumber > Students.get(midpointIndex).getNumber())
			{
				minIndex = midpointIndex + 1; // Set minIndex to be the index immediately after the midpointIndex
			}
			
			// If the input student ID number was less than the student ID number of the student at the midpointIndex of the Students array
			else
			{
				maxIndex = midpointIndex - 1; // Set maxIndex to be the index immediately before the midpointIndex
			}
		}
		
		return false; // If a duplicate was found you wouldn't be here right now. Return false
	}
	
	// This method initializes enter key actions, allowing the user to just press the enter key to save rather than have to click the button.
	public void setUpEnterKeyAction()
	{
		tfStudentID.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key is Enter
				{
					saveEntry(); // Attempt to save the student
				}
			}
		});
		
		tfStudentName.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key is Enter
				{
					saveEntry(); // Attempt to save the student
				}
			}
		});
		
		tfStudentCity.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key is Enter
				{
					saveEntry(); // Attempt to save the student
				}
			}
		});
		
		cbStudentState.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key is Enter
				{
					saveEntry(); // Attempt to save the student
				}
			}
		});
		
		cbStudentClass.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key is Enter
				{
					saveEntry(); // Attempt to save the student
				}
			}
		});
		
		taOutput.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.UP) // If the key is Enter
				{
					left(); // Attempt to save the student
				}
			}
		});
		
		taOutput.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.DOWN) // If the key is Enter
				{
					left(); // Attempt to save the student
				}
			}
		});
	}
	
	public StackPane reset() // remove all entries from the Students array
	{
		ImageView alertImage = new ImageView("alert.gif"); // Initialize alert image
		alertImage.setFitWidth(50); // Set size of alert image
		alertImage.setPreserveRatio(true); // Preserve image aspect ratio during resizing
		alertImage.setSmooth(true); // Resize image smoothly
		alertImage.setCache(true); // Cache the image for quick loading
		
		
	    Text alertMessage = new Text("Resetting will cause all entries to be permanently erased.\nAre you sure you want to continue?"); // Initialize alert body text
	    alertMessage.getStyleClass().add("alert"); // Add the "alert" CSS style

	    
	    AnchorPane alertAnchorPane = new AnchorPane(); // Initialize AnchorPane called alertAnchorPane for alert image and body text
	    AnchorPane.setTopAnchor(alertImage, 10.0); // Anchor alert image 10 pixels from the top of the dialog box
	    AnchorPane.setLeftAnchor(alertImage, 10.0); // Anchor alert image 10 pixels from the left side of the dialog box
	    AnchorPane.setTopAnchor(alertMessage, 10.0); // Anchor alert body text 10 pixels from the top of the dialog box
	    AnchorPane.setLeftAnchor(alertMessage, 75.0); // Anchor alert body text 75 pixels from the left side of the dialog box (so that it clears the alert image)
	    alertAnchorPane.getChildren().addAll(alertImage, alertMessage); // Gather up all the alertAnchorPane nodes and add them to the pane
	    
	    HBox options = new HBox(10); // Initialize a HBox to hold the cancel and yes buttons
	    options.setAlignment(Pos.BOTTOM_CENTER); // Center the HBox
	    
	    options.getChildren().addAll(btCancel, btYes); // Add the nodes to the options HBox
	    
	    StackPane alertBox = new StackPane(); // Initialize a new StackPane called errorPane to construct the final makeup of the dialog box
	    alertBox.getStylesheets().add("stylesheet.css"); // Load the CSS
	    StackPane.setAlignment(alertAnchorPane, Pos.TOP_LEFT); // Align the AnchorPane to the top left of the StackPane
	    StackPane.setAlignment(options, Pos.BOTTOM_CENTER); // Align the buttons to the bottom center of the StackPane
	    StackPane.setMargin(options, new Insets(8,8,8,8)); // Set margins around the exterior of the buttons
	    alertBox.getChildren().addAll(alertAnchorPane, options); // Gather up all the errorPane nodes and all them to the pane
	   
	    Stage alertStage = new Stage(); // Create a scene and place it in the stage
	    
	    btCancel.setOnAction(new EventHandler<ActionEvent>() // Listen for the user to click the "Cancel" button
	    {
	        @Override public void handle(ActionEvent e) // When the user clicks the "Cancel" button
	        {
	        	alertStage.close(); // Close the alert dialog box
	        }
	    });
	    
	    btYes.setOnAction(new EventHandler<ActionEvent>() // Listen for the user to click the "Yes" button
	    {
	        @Override public void handle(ActionEvent e) // When the user clicks the "Yes" button
	        {
	            Students.clear(); // Delete all student objects
	            newEntry(); // Reset all the input areas
	            refreshOutput(); // Refresh the output table
	            alertStage.close(); // Close the alert dialog box
	        }
	    });
	    
	    alertStage.setTitle("Are You Sure?"); // Set the stage title to the input string 
	    alertStage.setScene(new Scene(alertBox, 425, 90)); // Set the display window size to the input length and width
	    alertStage.showAndWait(); // Display the stage and wait for the user to press "OK"
	    
	    return alertBox; // Return the errorPane so that the exception dialog boxes still show up correctly
	}
	
	// Serves as a customizable alert dialog box given input header message (string), body message (string), length (pixels), and width (pixels)
	public StackPane alertDialog(String title, String body, int length, int width)
	{
		ImageView alertImage = new ImageView("alert.gif"); // Initialize alert image
		alertImage.setFitWidth(50); // Set size of alert image
		alertImage.setPreserveRatio(true); // Preserve image aspect ratio during resizing
		alertImage.setSmooth(true); // Resize image smoothly
		alertImage.setCache(true); // Cache the image for quick loading
		
	    Text alertMessage = new Text(body); // Initialize alert body text
	    alertMessage.getStyleClass().add("alert"); // Add the "alert" CSS style
	    
	    AnchorPane alertAnchorPane = new AnchorPane(); // Initialize AnchorPane called alertAnchorPane for alert image and body text
	    AnchorPane.setTopAnchor(alertImage, 10.0); // Anchor alert image 10 pixels from the top of the dialog box
	    AnchorPane.setLeftAnchor(alertImage, 10.0); // Anchor alert image 10 pixels from the left side of the dialog box
	    AnchorPane.setTopAnchor(alertMessage, 10.0); // Anchor alert body text 10 pixels from the top of the dialog box
	    AnchorPane.setLeftAnchor(alertMessage, 75.0); // Anchor alert body text 75 pixels from the left side of the dialog box (so that it clears the alert image)
	    alertAnchorPane.getChildren().addAll(alertImage, alertMessage); // Gather up all the alertAnchorPane nodes and add them to the pane
	    
	    StackPane errorPane = new StackPane(); // Initialize a new StackPane called errorPane to construct the final makeup of the dialog box
	    errorPane.getStylesheets().add("stylesheet.css"); // Load the CSS
	    StackPane.setAlignment(alertAnchorPane, Pos.TOP_LEFT); // Align the AnchorPane to the top left of the StackPane
	    StackPane.setAlignment(btOK, Pos.BOTTOM_CENTER); // Align the "OK" button to the bottom center of the StackPane
	    StackPane.setMargin(btOK, new Insets(8,8,8,8)); // Set margins around the exterior of the "OK" button
	    errorPane.getChildren().addAll(alertAnchorPane, btOK); // Gather up all the errorPane nodes and all them to the pane
	   
	    Stage errorStage = new Stage(); // Create a scene and place it in the stage
	    
	    btOK.setOnAction(new EventHandler<ActionEvent>() // Listen for the user to click the "OK" button
	    {
	        @Override public void handle(ActionEvent e) // When the user clicks the "OK" button
	        {
	            errorStage.close(); // Close the alert dialog box
	        }
	    });
	    
	    // Close the alert box if the user types the Enter key
		btOK.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to type a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // Handle a key event
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key typed was Enter
				{
		            errorStage.close(); // Close the alert dialog box
				}
			}
		});
	    
	    errorStage.setTitle(title); // Set the stage title to the input string 
	    errorStage.setScene(new Scene(errorPane, length, width)); // Set the display window size to the input length and width
	    errorStage.showAndWait(); // Display the stage and wait for the user to press "OK"
	    
	    return errorPane; // Return the errorPane so that the exception dialog boxes still show up correctly
	}
	
	/**
	 * The main method is only needed for the IDE with limited
	 * JavaFX support. Not needed for running from the command line.
	 */
	public static void main(String[] args)
	{ 
	    launch(args);
	}
}