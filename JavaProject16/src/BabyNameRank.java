/**
 * This program will take an input name and a year choice, then search for that name's rank during the given year
 * 
 * Matt Warman
 * Date: 04/12/2015
 * Version: 1.7
 */

import java.io.File;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BabyNameRank extends Application
{
	// Initialize private variables	
	private ComboBox<String> cbYear = new ComboBox<String>(); // Initialize a new ComboBox of strings for year selection
	private ComboBox<String> cbGender = new ComboBox<String>(); // Initialize a new ComboBox of strings for gender selection
	private TextField tfName = new TextField(); // Serve as a text field to read the user input name to be searched for
	private TextArea tfOutput = new TextArea(); // Serve as an output text field to display name ranking information
	private Button btFind = new Button("Find Ranking"); // Serve as a button that initiates name rank search
    private Button btOK = new Button("OK"); // Serve as a button that closes a dialog box
    
    // Initialize options to be used to populate the years ComboBox
    private ObservableList<String> years = FXCollections.observableArrayList("2006", "2007", "2008", "2009", "2010", "All");
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage)
	{
		// Initialize the mainWindow
		VBox mainWindow = new VBox(10); // Initialize a new VBox to serve as the main window
		mainWindow.getStylesheets().add("stylesheet.css"); // Load the CSS
		
		// Initialize a GridPane to house labels, text input boxes, and buttons.
		GridPane gridPane = new GridPane(); // Initialize the GridPane
		gridPane.setAlignment(Pos.CENTER); // Center the GridPane
		gridPane.setHgap(75); // Use a 75pt horizontal gap between nodes
		gridPane.setVgap(6); // Use a 6pt vertical gap between nodes
		
	    // Add nodes to the GridPane
		gridPane.add(new Label("Select a Year:"), 0, 0); // Add year selection ComboBox label
		gridPane.add(cbYear, 1, 0); // Add year selection ComboBox
		gridPane.add(new Label("Gender:"), 0, 1); // Add gender selection ComboBox label
		gridPane.add(cbGender, 1, 1); // Add gender selection ComboBox
		gridPane.add(new Label("Enter a Name:"), 0, 2); // Add name input TextField label
		gridPane.add(tfName, 1, 2); // Add name input TextField
		gridPane.add(btFind, 1, 3); // Add "Find Ranking" Button
		
		// Set GridPane node properties
		cbYear.setPrefWidth(100); // Set preferred width of the year selection ComboBox
		cbYear.setItems(years); // Populate the year selection ComboBox with previously initialized options
		cbGender.setPrefWidth(100); // Set preferred width of the gender selection ComboBox
		cbGender.getItems().addAll("Male", "Female"); // Add gender options to the gender ComboBox
		tfName.setPrefWidth(100); // Set preferred width of the name input TextField
		btFind.setPrefWidth(100); // Set preferred width of the "Find Ranking" Button
		
		// Add nodes to the main window
		mainWindow.getChildren().addAll(gridPane, tfOutput);
		
		// Set main window node properties
		VBox.setVgrow(tfOutput, Priority.ALWAYS); // Set the output TextField to expand Vertically when the window is resized (HGrow seems automatically enabled)
		tfOutput.getStyleClass().add("output"); // Add the "output" CSS style
		tfOutput.setPrefHeight(45); // Set the preferred height of the output TextField
		tfOutput.setBackground(null); // Remove the background of the output TextField
		tfOutput.setEditable(false); // Make the output TextField un-editable
		tfOutput.setWrapText(true); // Turn on text wrapping so that a horizontal scroll bar doesn't appear (Sometimes it appears unnecessarily)
		mainWindow.setPadding(new Insets(10, 10, 10, 10)); // Put a border around everything in the main window

		// Set up action events
		btFind.setOnAction(e -> findName()); // When the user presses the "Find Ranking" button, initialize the findName() function
		
		// Allow the user to type the enter key from the Name TextField to search for a name instead of having to press the "Find Ranking" button
		tfName.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key is Enter
				{
					findName(); // Initialize the findName() function
				}
			}
		});
		
		Scene scene = new Scene (mainWindow, 325, 200); // Initialize the scene
		primaryStage.setTitle("Baby Names Rank"); // Set the window title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}
	
	public void findName() // Attempt to search for a name located in the name TextField and report the results
	{
		// Perform initial checks
		if (cbYear.getValue() == null) // If the user didn't select a year value
		{
			alertDialog("Select Year", "Please select a year from the dropdown box.", 375, 80); // Alert the user
			return; // Stop attempting to search for input name, since not all necessary input information is available
		}
		
		else if (cbGender.getValue() == null) // If the user didn't select a gender value
		{
			alertDialog("Select Gender", "Please select a gender from the dropdown box.", 375, 80); // Alert the user
			return; // Stop attempting to search for input name, since not all necessary input information is available
		}
		
		else if (tfName.getText().equals("")) // If the user didn't enter a name to search rankings for
		{
			alertDialog("Insert Name", "Please input a name to search for.", 300, 80); // Alert the user
			return; // Stop attempting to search for input name, since not all necessary input information is available
		}
		
		// If the user elects to search all years, initialize the findAllName function, and return after its completion
		if (cbYear.getValue().equals("All")) {findAllName(); return;}
		
		// Initialize variables to be used in name search
		File nameFile = new File("Babynamesranking" + cbYear.getValue() + ".txt"); // Set up access to the file of the requested year
		Scanner scanner = null; // Initialize a scanner to search for input names
		int rank = 0; // Used to hold values that may be the rank of the input baby name (value determined if applicable in later steps)
		String babies = null; // Used the hold the number of babies with a given name born in a given year
		String nameInFile = null; // Used to report names in the correct case (loaded from file) regardless of input case
		boolean nameFound = false; // Used to aid in reporting name information if found or lack thereof if not
		
		if (nameFile.exists() && !nameFile.isDirectory()) // if the year file exists (and is not a directory)
		{
			try 
			{
				scanner = new Scanner(nameFile); // Initialize scanning of the requested year's file
				scanner.useDelimiter("	| 	|  |\\n"); // Set delimiters used in the file as local scanner delimiters
				
				while (scanner.hasNext()) // While there are still entries in the name file
				{
					if (scanner.hasNextInt()) // If there is another rank available
					{
						rank = scanner.nextInt(); // Save it in the rank variable to possibly be used later (since scanner can't move backward)
					}
					else // If the next element isn't an integer
					{
						scanner.next(); // To be honest, I don't remember why this is here. It may not ever even be called upon for all I know. (It's 23:30 right now and everything is working so I'm not even going to try messing with it.)
					}
					
					if (cbGender.getValue().equals("Female")) // If the user is searching for a female name
					{
						// Skip the next two elements. These are a male name and the number of babies born with that name
						scanner.next();
						scanner.next();
					}
					
					nameInFile = scanner.next(); // Store the name at the current location in the nameInFile variable (this is inefficient but is the easiest way to ensure name matches thanks to .toUpperCase() )
					
					// If the name at the current location in the file matches the name input to be searched for... (both names are converted to upper case so that case mismatch is disregarded)
					if (nameInFile.toUpperCase().equals(tfName.getText().toUpperCase()))
					{
						nameFound = true; // Set the nameFound value to true
						babies = scanner.next(); // Store the next value (the name's rank) in the babies variable
						break; // End the loop
					}
					else // If the names don't match
					{
						if (cbGender.getValue().equals("Male")) // If the user is searching for a male name
						{
							// Skip the next two elements. These are a female name and the number of babies born with that name
							scanner.next();
							scanner.next();
						}
						scanner.next(); // Skip one element to keep everything on the right pattern track
					}
				}
			}
			catch (Exception e) // If there are any random errors reading the file or the data in it
			{
				alertDialog("Unknown Error", "An unknown error has occurred.\nName file corruption may have caused this", 400, 100); // Alert the user
			}
						
			if (nameFound) // If a name match was found
			{
				// Output the results to the user
				tfOutput.setText(nameInFile + " was ranked number " + rank + " in " + cbYear.getValue() + "\n" +
						"There were " + babies + " " + nameInFile + "'s born in " + cbYear.getValue());
			}
			
			if (!nameFound) // If a name match was not found
			{
				// Output this information to the user
				tfOutput.setText("The " + cbGender.getValue().toLowerCase() + " name " + tfName.getText() + " was not ranked in " + cbYear.getValue());
			}
			scanner.close(); // Cleanup after scanner is no longer needed
			return; // End the findName function
		}
		else // If the file selected does not exist or is for some reason a directory (which should never happen if things are coded right)
		{
			tfOutput.setText("The selected year is unavailable."); // Alert the user
			return; // End the findName function
		}
	}
	
	
	public void findAllName()
	{		
		
		// Initialize variables to be used in name search
		File nameFile;
		Scanner scanner = null; // Initialize a scanner to search for input names
		int rank = 0; // Used to hold values that may be the rank of the input baby name (value determined if applicable in later steps)
		int year = 0; // Used to parse through multiple years looking for matching names
		String babies = null; // Used the hold the number of babies with a given name born in a given year
		String nameInFile = null; // Used to report names in the correct case (loaded from file) regardless of input case
		boolean nameFound = false; // Used to aid in reporting name information if found or lack thereof if not
		tfOutput.setText(""); // Clean any stray text is in the output TextField
		TextArea taNamesOutput = new TextArea(); // Initialize a TextArea for output
		
		// Initialize dialog box
		VBox allResults = new VBox(10); // Initialize a VBox to serve as a window displaying results for all years
		allResults.getStylesheets().add("stylesheet.css"); // Load the CSS
		allResults.setAlignment(Pos.CENTER); // Set the VBox to be center aligned
		allResults.setPadding(new Insets(10, 10, 10, 10)); // Put a border around everything in the window
		
		// Set dialog box node properties
		taNamesOutput.getStyleClass().add("output"); // Add the "output" style
		taNamesOutput.setPrefHeight(300); // Set the preferred height of the output TextArea
		taNamesOutput.setBackground(null); // Set the background of the output TextArea to clear
		taNamesOutput.setEditable(false); // Set the output TextArea to be un-editable
		taNamesOutput.setWrapText(true); // Turn on text wrapping so that a horizontal scroll bar doesn't appear (Sometimes it appears unnecessarily)
		btOK.setAlignment(Pos.CENTER); // Align the "OK" button in the center of the window
		
		// Add children to allResults
		allResults.getChildren().addAll(taNamesOutput, btOK);
		
		Stage allResultsBox = new Stage(); // Set up the stage for the dialog box
		allResultsBox.setTitle("All results for the name " + tfName.getText()); // Set the title of the dialog box
		allResultsBox.setScene(new Scene(allResults, 350, 300)); // Set the display window size to the input length and width
		allResultsBox.show(); // Display the stage
		
		for (year = 2006; year < 2011; year++) // Start at year 2006 and go all the way through year 2010
		{
			nameFile = new File("Babynamesranking" + Integer.toString(year) + ".txt"); // Set the file to be accessed to whichever year is currently being searched
			if (nameFile.exists() && !nameFile.isDirectory()) // if the year file exists (and is not a directory)
			{
				try 
				{
					scanner = new Scanner(nameFile); // Initialize scanning of the requested year's file
					scanner.useDelimiter("	| 	|  |\\n"); // Set delimiters used in the file as local scanner delimiters
					
					while (scanner.hasNext()) // While there are still entries in the name file
					{
						if (scanner.hasNextInt()) // If there is another rank available
						{
							rank = scanner.nextInt(); // Save it in the rank variable to possibly be used later (since scanner can't move backward)
						}
						else // If the next element isn't an integer
						{
							scanner.next(); // To be honest, I don't remember why this is here. It may not ever even be called upon for all I know. (It's 23:30 right now and everything is working so I'm not even going to try messing with it.)
						}
						
						if (cbGender.getValue().equals("Female")) // If the user is searching for a female name
						{
							// Skip the next two elements. These are a male name and the number of babies born with that name
							scanner.next();
							scanner.next();
						}
						
						nameInFile = scanner.next(); // Store the name at the current location in the nameInFile variable (this is inefficient but is the easiest way to ensure name matches thanks to .toUpperCase() )
						
						// If the name at the current location in the file matches the name input to be searched for... (both names are converted to upper case so that case mismatch is disregarded)
						if (nameInFile.toUpperCase().equals(tfName.getText().toUpperCase()))
						{
							nameFound = true; // Set the nameFound value to true
							babies = scanner.next(); // Store the next value (the name's rank) in the babies variable
							break; // End the loop
						}
						else // If the names don't match
						{
							if (cbGender.getValue().equals("Male")) // If the user is searching for a male name
							{
								// Skip the next two elements. These are a female name and the number of babies born with that name
								scanner.next();
								scanner.next();
							}
							scanner.next(); // Skip one element to keep everything on the right pattern track
						}
					}
				}
				catch (Exception e) // If there are any random errors reading the file or the data in it
				{
					alertDialog("Unknown Error", "An unknown error has occurred.\nName file corruption may have caused this", 400, 100); // Alert the user
				}

				if (nameFound) // If a name match was found
				{
					// Output the results to the user by appending them to information currently in output TextArea
					taNamesOutput.appendText(nameInFile + " was ranked number " + rank + " in " + year + "\n" +
							"There were " + babies + " " + nameInFile + "'s born in " + year);
				}
				
				if (!nameFound) // If a name match was not found
				{
					// Output this information to the user by appending it to information currently in output TextArea
					taNamesOutput.appendText("The " + cbGender.getValue().toLowerCase() + " name " + tfName.getText() + " was not ranked in " + year);
				}
				
				if (year < 2010) // For years 2006 through 2009
				{
					taNamesOutput.appendText("\n\n"); // Add two lines before the next year section
				}
			}
			else // If the current year file does not exist or is for some reason a directory (which should never happen if things are coded right)
			{
				taNamesOutput.appendText("Data file for year " + year + " is missing.\n\n"); // Append this information to the names output TextArea
			}	
		}
		
		scanner.close(); // Cleanup after scanner is no longer needed
		
		// Close the dialog box when the user clicks "OK"
	    btOK.setOnAction(new EventHandler<ActionEvent>() // Listen for the user to click the "OK" button
	    {
	        @Override public void handle(ActionEvent e) // When the user clicks the "OK" button
	        {
	            allResultsBox.close(); // Close the dialog box
	        }
	    });
	    
	    // Close the dialog box if the user types the Enter key
		taNamesOutput.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to type a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // Handle a key event
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key typed was Enter
				{
					allResultsBox.close(); // Close the  dialog box
				}
			}
		});
		return;
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
