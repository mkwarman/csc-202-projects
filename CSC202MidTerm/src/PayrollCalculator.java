/**
 * PayrollCalculator presents a GUI which allows the user to add employees, their hours, their hourly rate, and an optional
 * 	raise, then calculates the total amount to be paid
 * 
 * Matt Warman
 * Date: 03/16/2015
 * Version: 1.5
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PayrollCalculator extends Application {
	
	// Initialize groups and lists
	static List<Employee> Employees = new ArrayList<Employee>(); // initialize an array list of Loan objects
    final ToggleGroup raiseSelection = new ToggleGroup(); // Set up a toggle group so that only one raise selection can be made at a time
	
	// Initialize private variables
	private TextField tfEmployeeNumber = new TextField(); // Serve as a text field for receiving input employee numbers
	private TextField tfBasePay = new TextField(); // Serve as a text field for receiving input number of hours worked
	private TextField tfHoursWorked = new TextField(); // Serve as a text field for receiving input number of hours worked
	private TextField tfTotal = new TextField(); // Serve as a text field for outputting the Total amount
	private TextArea taOutput = new TextArea(); // Serve as an output text field to display calculated output
	private RadioButton rbNoRaise = new RadioButton(); // Serve as a radio button for selecting no raise
	private RadioButton rb1Raise = new RadioButton(); // Serve as a radio button for selecting a one percent raise
	private RadioButton rb2Raise = new RadioButton(); // Serve as a radio button for selecting a two percent raise
	private RadioButton rb5Raise = new RadioButton(); // Serve as a radio button for selecting a five percent raise
	private Button btAdd = new Button("Add"); // Serve as a button that initiates employee addition
    private Button btOK = new Button("OK"); // Serve as a button that closes an alert dialog box
    private Button btReset = new Button("Reset"); // Serve as a button that resets the output text area
    private Button btYes = new Button("Yes"); // Serve as a button that closes an alert dialog box, confirming the action
    private Button btCancel = new Button("Cancel"); // Serve as a button that closes an alert dialog box, canceling the action
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage)
	{
		// Initialize main window
		final BorderPane mainWindow = new BorderPane(); // Initialize a new BorderPane making up the main window
		mainWindow.getStylesheets().add("stylesheet.css"); // Load the CSS
		
		// Initialize the topbar
		HBox topBar = new HBox(10); // Initialize a new HBox to construct the top bar containing labels and text fields
		topBar.setAlignment(Pos.CENTER); // Align the topBar HBox so that it lies in the center of its section of the window
		
		// Add nodes to the topbar
		Label lbEmployeeNumber = new Label("Employee Number: "); // Initialize a "Employee Number: " label
		Label lbBasePay = new Label("Base Pay: "); // Initialize a "Base Pay: " label
		Label lbHoursWorked = new Label("Hours Worked: "); // Initialize a "Hours Worked: " label
		tfEmployeeNumber.setPrefWidth(85); // Set the width of the Loan Amount text field
		tfBasePay.setPrefWidth(60); // Set the width of the Years text field
		tfHoursWorked.setPrefWidth(60); // Set the width of the Loan Amount text field
		HBox.setMargin(tfEmployeeNumber, new Insets (0, 20, 0, 0)); // Put some space between the text field and the next node
		HBox.setMargin(tfBasePay, new Insets (0, 20, 0, 0)); // Put some space between the text field and the next node
		HBox.setMargin(tfHoursWorked, new Insets (0, 10, 0, 0)); // Put some space between the text field and the next node
		topBar.getChildren().addAll(lbEmployeeNumber, tfEmployeeNumber, lbBasePay, tfBasePay, lbHoursWorked, tfHoursWorked, btAdd); // Add all the children to the topBar HBox
		
		// Initialize the sidebar
		GridPane sideBar = new GridPane(); // Initialize a new gridpane to construct the right section of the window making up the raise selection sidebar
		sideBar.setAlignment(Pos.CENTER); // Center the pane
		sideBar.setHgap(10); // Use a 10pt horizontal gap between nodes
		sideBar.setVgap(6); // Use a 6pt vertical gap between nodes
		
	    // Add nodes to the sidebar
		sideBar.add(new Label("1% Raise:"), 0, 0); // One percent raise selection label
		sideBar.add(rb1Raise, 1, 0); // No raise selection radio button
		sideBar.add(new Label("2% Raise:"), 0, 1); // Two percent raise selection label
		sideBar.add(rb2Raise, 1, 1); // No raise selection radio button
		sideBar.add(new Label("5% Raise:"), 0, 2); // Five percent raise selection label
		sideBar.add(rb5Raise, 1, 2); // No raise selection radio button
		sideBar.add(new Label("No Raise:"), 0, 3); // No raise selection label
		sideBar.add(rbNoRaise, 1, 3); // No raise selection radio button
		
		// Set sidebar properties
		rbNoRaise.setToggleGroup(raiseSelection); // Add no raise radio button to the raiseSelection toggle group
		rbNoRaise.setSelected(true); // Set the default state of this radio button as selected
		rb1Raise.setToggleGroup(raiseSelection); // Add 1% raise radio button to the raiseSelection toggle group
		rb2Raise.setToggleGroup(raiseSelection); // Add 2% raise radio button to the raiseSelection toggle group
		rb5Raise.setToggleGroup(raiseSelection); // Add 5% raise radio button to the raiseSelection toggle group
		
		// Initialize the footer
		HBox footer = new HBox(0);
		footer.setAlignment(Pos.CENTER_RIGHT);
		
		// Add nodes to the footer
		Label lbTotal = new Label("Total Amount: "); // Initialize a "Total Amount: " label
		
		// Set footer properties
		tfTotal.setPrefWidth(150); // Set the width of the text box
		tfTotal.setEditable(false); // Set the text box to be un-editable
		tfTotal.setBackground(null); // Set the text box background to be clear - Indicating that the text cannot be changed
		tfTotal.setText("$0.00"); // Set an initial value
		
		footer.getChildren().addAll(lbTotal, tfTotal, btReset); // Add all the footer children
		
		
		mainWindow.setTop(topBar); // Put the topBar HBox in the top section of the main window
		mainWindow.setRight(sideBar); // Put the sidebar in the right section of the main window
		mainWindow.setBottom(footer); // Put the footer area at the bottom of the main window
		mainWindow.setCenter(taOutput); // Put the output text area in the center of the main window
		// taOutput.setWrapText(true); // Turn on text wrapping so that an unnecessary horizontal scrollbar doesn't appear
		taOutput.setBackground(null); // Give the output area a more greyed out look - indicating its un-editable property
		taOutput.setEditable(false); // Set the output area to be un-editable
		taOutput.getStyleClass().add("output"); // Add the "output" style

		BorderPane.setMargin(taOutput, new Insets(10, 10, 10, 0)); // Set margins on the output text area so that there is some padding between it and the surrounding elements
		taOutput.setText("Employee Number" + "          " + "Base Pay" + "         " + "Hours Worked" + "          " + "Pay Amount\n" + "--------------------------------------------------------------------------\n"); // Reset the output box to the headers
		
		mainWindow.setPadding(new Insets(10, 10, 10, 10)); // Put a border around everything in the main window
		
		btAdd.setOnAction(e -> addEmployee()); // When the user presses the "Add" button, add the employee to the employee ArrayList
		rbNoRaise.setOnAction(e -> refreshTable()); // When the user selects the No Raise radio button, refresh the table
		rb1Raise.setOnAction(e -> refreshTable()); // When the user selects the 1% Raise radio button, refresh the table
		rb2Raise.setOnAction(e -> refreshTable()); // When the user selects the 2% Raise radio button, refresh the table
		rb5Raise.setOnAction(e -> refreshTable()); // When the user selects the 5% Raise radio button, refresh the table
		btReset.setOnAction(e -> reset()); // When the user presses the "Reset" button, reset the output text area
		
		// Allow the user to type the enter key to add a new employee instead of having to press the add button
		tfEmployeeNumber.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key is Enter
				{
					addEmployee(); // Attempt to add a new employee
				}
			}
		});
		
		tfBasePay.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key is Enter
				{
					addEmployee(); // Attempt to add a new employee
				}
			}
		});
		
		tfHoursWorked.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key is Enter
				{
					addEmployee(); // Attempt to add a new employee
				}
			}
		});
		
		btAdd.setOnKeyPressed(new EventHandler<KeyEvent>() // Listen for the user to press a key
		{
			@Override
			public void handle (KeyEvent keyEvent) // When the user presses a key
			{
				if (keyEvent.getCode() == KeyCode.ENTER) // If the key is Enter
				{
					addEmployee(); // Attempt to add a new employee
				}
			}
		});
		
		Scene scene = new Scene(mainWindow, 750, 300); // Initialize the scene
		primaryStage.setTitle("Payroll Calculator"); // Set the window title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}
	
	// Adds an Employee to the Employees ArrayList if all the information was entered correctly
	public void addEmployee()
	{
		double employeeNumber = 0.0; // Hold the input employee number
		double basePay = 0.0; // Hold the input employee base pay
		double hoursWorked = 0.0; // Hold the input employee hours worked
		boolean correctData = true; // Used to determine whether output should be computed or not
		
		// Get values from fields and (potentially) store them as an employee object
		try
		{
			// Fill evaluation variables
			employeeNumber = Double.parseDouble(tfEmployeeNumber.getText());
			basePay = Double.parseDouble(tfBasePay.getText());
			hoursWorked = Double.parseDouble(tfHoursWorked.getText());
			
			if (employeeNumber < 1 || employeeNumber > 2147483647) // If the employee number is outside the allowed range
			{
				tfEmployeeNumber.getStyleClass().removeAll("right"); // Remove the "right" style
				tfEmployeeNumber.getStyleClass().add("wrong"); // Add the "wrong" style
			}
			else  // If the employee number is inside the allowed range
			{
				tfEmployeeNumber.getStyleClass().add("right"); // Change the text color to black
			}
			
			if (basePay < 8.25) // If the base pay is outside the allowed range
			{
				tfBasePay.getStyleClass().removeAll("right"); // Remove the "right" style
				tfBasePay.getStyleClass().add("wrong"); // Change the text color to red
			}
			else // If the base pay is inside the allowed range
			{
				tfBasePay.getStyleClass().add("right"); // Change the text color to black
			}
			
			if (hoursWorked < 4) // If the hours worked is outside the allowed range
			{
				tfHoursWorked.getStyleClass().removeAll("right"); // Remove the "right" style
				tfHoursWorked.getStyleClass().add("wrong"); // Change the text color to red
			}
			else // If the hours worked is inside the allowed range
			{
				tfHoursWorked.getStyleClass().add("right"); // Change the text color to black
			}
			
			// If any of the values are outside of the allotted range
			if (employeeNumber < 1 || employeeNumber > 2147483647 || basePay < 8.25 || hoursWorked < 4)
			{
				// Alert the user of the input requirements
				alertDialog("Input Out Of Range", "Please ensure that:\nThe Employee Number is greater than 1 and less than 2147483647\nThe Base Hourly Pay is greater than the minimum $8.25\nThe number of Hours Worked is greater than 4.", 500, 125);
				correctData = false; // Incorrect data was input, so calculation and output should not take place
			}
			
			else if (employeeNumber != Math.floor(employeeNumber)) // If the input employee number was a double value
			{
				// Alert the user that employee number must be an integer
				alertDialog("Invalid Employee Number", "The Employee Number must be an integer", 400, 100);
				tfEmployeeNumber.setText(Integer.toString((int) Math.floor(employeeNumber))); // Round the input double value to the closest integer
				correctData = false; // Incorrect data was input, so calculation and output should not take place
			}
			
			else if (hoursWorked > 60) // If the user inputs hours worked over the 60 hour maximum
			{
				// Alert the user of the hour limit
				alertDialog("Over Hour Limit", "The maximum allowed working hours is 60.\nAdditional hours will not be counted for.", 400, 100);
				tfHoursWorked.setText(Integer.toString(60)); // Set the Hours Worked text field to the maximum 60
				correctData = false; // Incorrect data was input, so calculation and output should not take place
			}
			else
			{
				correctData = true; // Correct data was input, so calculation and output should take place
			}
		}
		catch (NumberFormatException | NullPointerException e) // If the user did not fill all required fields or input invalid characters
		{
			// Alert the user of invalid input
			alertDialog("Invalid Input", "Please ensure that all fields contain numbers", 400, 100);
			correctData = false; // Invalid data was input, so calculation and output should not take place
		}
		catch (Exception e) // If unknown errors occur
		{
			// Alert the user of the unknown error
			alertDialog("Unknown Error", "An unknown error has occurred.", 300, 100);
			correctData = false; // Invalid data was input, so calculation and output should not take place
			// System.out.print(e); // For Debugging Only
		}
		
		if (correctData) // If all data was entered correctly
		{
			Employee newEmployee = new Employee((int)employeeNumber, basePay, hoursWorked); // Create a new Employee in newEmployee
			Employees.add(newEmployee); // Add the newEmployee to the Employees ArrayList
			tfEmployeeNumber.setText(null); // Reset the Employee Number field
			tfBasePay.setText(null); // Reset the Base Pay field
			tfHoursWorked.setText(null); // Reset the Hours Worked field
			tfEmployeeNumber.requestFocus(); // Set the focus back to the employee number field
			refreshTable(); // Refresh the table
		}
	}
	
	// When the user selects a raise radio button, update the values effected
	public void refreshTable()
	{
		double raise = 0.0; // Hold the raise percentage for easy calculation
		double totalAmount = 0.0; // Hold the total amount for output after calculation
		
		if (raiseSelection.getSelectedToggle() == rbNoRaise) // If the "No Raise" radio button is selected
		{
			raise = 0; // set raise to 0 percent
		}
		if (raiseSelection.getSelectedToggle() == rb1Raise) // If the "1% Raise" radio button is selected
		{
			raise = 0.01; // set raise to 1 percent
		}
		if (raiseSelection.getSelectedToggle() == rb2Raise) // If the "2% Raise" radio button is selected
		{
			raise = 0.02; // set raise to 2 percent
		}
		if (raiseSelection.getSelectedToggle() == rb5Raise) // If the "5% Raise" radio button is selected
		{
			raise = 0.05; // set raise to 5 percent
		}

		// Set up the column headings
		taOutput.setText("Employee Number" + "          " + "Base Pay" + "         " + "Hours Worked" + "          " + "Pay Amount\n" + "--------------------------------------------------------------------------\n"); // Reset the output box to the headers
		
		for (int i = 0; i < Employees.size(); i++) // for all entries in the Employees ArrayList
		{
			// Output employee payment information for the current employee
			
			// Output the employee number
			taOutput.appendText(Employees.get(i).getEmployeeNumber() + "               ");
			
			
			// Fix whitespace by dynamically adding space based on the size of the employeeNumber
			if (Employees.get(i).getEmployeeNumber() < 10){taOutput.appendText(" ");}
			if (Employees.get(i).getEmployeeNumber() < 100){taOutput.appendText(" ");}
			if (Employees.get(i).getEmployeeNumber() < 1000){taOutput.appendText(" ");}
			if (Employees.get(i).getEmployeeNumber() < 10000){taOutput.appendText(" ");}
			if (Employees.get(i).getEmployeeNumber() < 100000){taOutput.appendText(" ");}
			if (Employees.get(i).getEmployeeNumber() < 1000000){taOutput.appendText(" ");}
			if (Employees.get(i).getEmployeeNumber() < 10000000){taOutput.appendText(" ");}
			if (Employees.get(i).getEmployeeNumber() < 100000000){taOutput.appendText(" ");}
			if (Employees.get(i).getEmployeeNumber() < 1000000000){taOutput.appendText(" ");}

			
			// Output the employee base pay, factoring in a rise if applicable
			taOutput.appendText(new DecimalFormat("$###,##0.00").format((Employees.get(i).getBasePay() + Employees.get(i).getBasePay()*raise)) + "      ");
			
			// Fix whitespace by dynamically adding space based on the size of the basePay
			if ((Employees.get(i).getBasePay() + Employees.get(i).getBasePay()*raise) < 10){taOutput.appendText(" ");}
			if ((Employees.get(i).getBasePay() + Employees.get(i).getBasePay()*raise) < 100){taOutput.appendText(" ");}
			if ((Employees.get(i).getBasePay() + Employees.get(i).getBasePay()*raise) < 1000){taOutput.appendText("  ");}
			if ((Employees.get(i).getBasePay() + Employees.get(i).getBasePay()*raise) < 10000){taOutput.appendText(" ");}
			if ((Employees.get(i).getBasePay() + Employees.get(i).getBasePay()*raise) < 100000){taOutput.appendText(" ");}
			
			// Output the number of hours worked by the employee
			taOutput.appendText(new DecimalFormat("#0.00").format(Employees.get(i).getHoursWorked()) + "                 ");
			
			// Fix whitespace by dynamically adding space based on the size of the Hours Worked
			if (Employees.get(i).getHoursWorked() < 10){taOutput.appendText(" ");}
			
			// Output the amount to pay the employee, factoring in a raise and overtime if applicable
			taOutput.appendText(new DecimalFormat("$###,###,##0.00").format((Employees.get(i).getBasePay() + Employees.get(i).getBasePay() * raise) * (Employees.get(i).getHoursWorked() + Employees.get(i).getOvertime()))+ "\n");
			
			// Add the pay amount to the total pay amount
			totalAmount = (totalAmount + ((Employees.get(i).getBasePay() + Employees.get(i).getBasePay() * raise) * (Employees.get(i).getHoursWorked() + Employees.get(i).getOvertime())));
		}
		tfTotal.setText(new DecimalFormat("$###,###,###,##0.00").format(totalAmount)); // update the total amount
	}
	
	public StackPane reset() // remove all entries from the payroll calculator
	{
		ImageView alertImage = new ImageView("alert.gif"); // Initialize alert image
		alertImage.setFitWidth(50); // Set size of alert image
		alertImage.setPreserveRatio(true); // Preserve image aspect ratio during resizing
		alertImage.setSmooth(true); // Resize image smoothly
		alertImage.setCache(true); // Cache the image for quick loading
		
		
	    Text alertMessage = new Text("Resetting will cause all entries to be permanently erased.\nAre you sure you want to continue?"); // Initialize alert body text
	    
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
	    // alertBox.getStylesheets().add("stylesheet.css"); // Load the CSS
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
	            Employees.clear(); // Delete all employee objects
	            refreshTable(); // Refresh the table
	            alertStage.close(); // Close the alert dialog box
	        }
	    });
	    
	    alertStage.setTitle("Are You Sure?"); // Set the stage title to the input string 
	    alertStage.setScene(new Scene(alertBox, 425, 100)); // Set the display window size to the input length and width
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
	    
	    AnchorPane alertAnchorPane = new AnchorPane(); // Initialize AnchorPane called alertAnchorPane for alert image and body text
	    AnchorPane.setTopAnchor(alertImage, 10.0); // Anchor alert image 10 pixels from the top of the dialog box
	    AnchorPane.setLeftAnchor(alertImage, 10.0); // Anchor alert image 10 pixels from the left side of the dialog box
	    AnchorPane.setTopAnchor(alertMessage, 10.0); // Anchor alert body text 10 pixels from the top of the dialog box
	    AnchorPane.setLeftAnchor(alertMessage, 75.0); // Anchor alert body text 75 pixels from the left side of the dialog box (so that it clears the alert image)
	    alertAnchorPane.getChildren().addAll(alertImage, alertMessage); // Gather up all the alertAnchorPane nodes and add them to the pane
	    
	    StackPane errorPane = new StackPane(); // Initialize a new StackPane called errorPane to construct the final makeup of the dialog box
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
	public static void main(String[] args) { 
	    launch(args);
	}
}
