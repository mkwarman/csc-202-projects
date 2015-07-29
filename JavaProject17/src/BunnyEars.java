/**
 * This program will take an input number of bunnies, and output the sum of the ears of the regular and mutant bunnies
 * 
 * Matt Warman
 * Date: 04/27/2015
 * Version: 1.3
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BunnyEars extends Application
{
	// Initialize private node elements
	private TextField tfNumberOfBunnies = new TextField(); // Initialize the text field to be used to take in user input number of bunnies
	private TextField tfOutput = new TextField(); // Initialize text area to serve as number of ears output
	private Label lbNumberOfBunnies = new Label("Number of Bunnies:"); // Initialize a label to caption the input number of bunnies text field
	private Button btGo = new Button("Calculate"); // Initialize a button used to start the program calculation
    private Button btOK = new Button("OK"); // Serve as a button that closes a dialog box
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage)
	{
		VBox mainWindow = new VBox(10); // Initialize a new VBox to serve as the main application window
		mainWindow.getStylesheets().add("stylesheet.css"); // Load the CSS
		
		HBox inputArea = new HBox(10); // Initialize a new HBox to serve as the user input area
		
		// Set inputArea node to properties
		tfNumberOfBunnies.setPrefWidth(65); // Set the preferred width of the input NumberOfBunnies text field
		inputArea.setAlignment(Pos.CENTER); // Set the position of nodes in the inputArea to be centered
		
		inputArea.getChildren().addAll(lbNumberOfBunnies, tfNumberOfBunnies, btGo); // Add the appropriate nodes to the inputArea
		
		// Set inputArea node properties
		tfOutput.setEditable(false); // Make the output TextField un-editable
		tfOutput.setBackground(null); // Remove the background of the output TextField
		tfOutput.getStyleClass().add("output"); // Add the "output" CSS style
		tfOutput.setText("Number of Ears:"); // Set default text

		
		// Set mainWindow properties
		mainWindow.setPadding(new Insets(10, 10, 10, 10)); // Put a border around everything in the main window
		mainWindow.getChildren().addAll(inputArea, tfOutput); // Add the main sections to the mainWindow

		Scene scene = new Scene (mainWindow, 300, 85); // Initialize the scene
		primaryStage.setTitle("Bunny Ears"); // Set the window title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
		
		// Set up action events
		btGo.setOnAction(e -> startCalculation()); // When the user presses the "Calculate" button, initialize the earCount() function
		
		// Allow the user to type the enter key from the NumberOfBunnies TextField to search for a name instead of having to press the "Calculate" button
		tfNumberOfBunnies.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key is Enter
				{
					startCalculation(); // Initialize the earCount() function
				}
			}
		});
	}
	
	// Set up and start the calculation of bunny ears
	private void startCalculation()
	{
		if (tfNumberOfBunnies.getText().equals("")) // If the user didn't enter a number of bunnies
		{
			alertDialog("Input Needed", "Please input the number of bunnies", 300, 75); // Alert the user
			return; // Don't run the calculation since there isn't enough information to proceed
		}
		try
		{
			if (Integer.parseInt(tfNumberOfBunnies.getText()) < 0) // If the user input a negative number
			{
				// Change the text color to red
				tfNumberOfBunnies.getStyleClass().removeAll("right"); // Remove the "right" style if necessary
				tfNumberOfBunnies.getStyleClass().add("wrong"); // Add the "wrong" style
				
				alertDialog("Invalid Input", "There cannot be negative bunnies\n(Only happy ones)", 300,100); // Alert the user
				return; // Don't run the calculation since the input was invalid
			}
			
			// Calculate the number of ears from the value input in tfNumberOfBunnies and output it to tfOutput
			tfOutput.setText("Number of Ears: " + earCount(Integer.parseInt(tfNumberOfBunnies.getText())));
			
			// Change the text color to black in case it was previously changed to red
			tfNumberOfBunnies.getStyleClass().removeAll("wrong"); // Remove the "wrong" style if applicable
			tfNumberOfBunnies.getStyleClass().add("right"); // Add the "right" style
		}
		catch (Exception e) // If the user entered an invalid number
		{
			// Change the text color to red
			tfNumberOfBunnies.getStyleClass().removeAll("right"); // Remove the "right" style if necessary
			tfNumberOfBunnies.getStyleClass().add("wrong"); // Add the "wrong" style
			
			// Alert the user that the input was invalid
			alertDialog("Invalid Input", "Please ensure that the input number of\nbunnies is in the form of a whole number", 325 ,100);
		}
	}
	
	// Recursively calculate the number of bunny ears
	private int earCount(int bunnies)
	{
		if (bunnies == 1) { return 2; } // Base case
		if (bunnies == 0) { return 0; } // In case the user enters 0 bunnies
		
		switch (bunnies % 2) // Determine if this is a normal odd numbered bunny or a mutant even numbered one
		{
		case 0: // If the bunny is even
			return (3 + earCount(bunnies - 1)); // Add three ears and run the function again with the next bunny
		case 1: // If the bunny is odd
			return (2 + earCount(bunnies - 1)); // Add two ears and run the function again with the next bunny
		default: // This should never be called
			return 0;
		}
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
