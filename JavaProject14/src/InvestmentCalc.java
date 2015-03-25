/**
 * InvestmentCalc calculates a Future Investment Value based on user input.
 * 
 * Matt Warman 
 * Version 1.4
 * Date: 03/02/2015
 */

import java.text.DecimalFormat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InvestmentCalc extends Application
{
	// Initialize private variables
	private TextField tfInvestmentAmount = new TextField(); // Serve as a text field for receiving input investment amounts
	private TextField tfYears = new TextField(); // Serve as a text field for receiving input numbers of years
	private TextField tfAnnualInterestRate = new TextField(); // Serve as a text field for receiving investment annual interest rate
	private TextField tfFutureValue = new TextField(); // Serve as an output text field to display calculated future investment value
	private Button btCalculate = new Button("Calculate"); // Serve as a button that initiates future investment value calculation
    private Button btOK = new Button("OK"); // Serve as a button that closes an alert dialog box
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage)
	{
		// Initialize a new grid pane
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER); // Center the pane
		pane.setPadding(new Insets(10)); // Use 10pt padding around all sides
		pane.setHgap(10); // Use a 10pt horizontal gap between nodes
		pane.setVgap(6); // Use a 6pt vertical gat between nodes
		
	    // Place nodes in the pane
	    pane.add(new Label("Investment Amount:"), 0, 0); // Investment Amount label
	    pane.add(tfInvestmentAmount, 1, 0); // Investment Amount text field
	    pane.add(new Label("Years:"), 0, 1); // Years label
	    pane.add(tfYears, 1, 1); // Years text field
	    pane.add(new Label("Annual Interest Rate:"), 0, 2); // Annual Interest Rate label
	    pane.add(tfAnnualInterestRate, 1, 2); // Annual Interest Rate text field
	    pane.add(new Label("Future Value:"), 0, 3); // Future Value label
	    pane.add(tfFutureValue, 1, 3); // Future Value text field
	    pane.add(btCalculate, 1, 4); // Calculate button
		
	    // Set Node Properties
	    tfFutureValue.setText("$0.00"); // Set a placeholder value - helps the user see that this field is auto populated
	    tfFutureValue.setEditable(false); // Set the Future Value text field to be un-editable
	    GridPane.setHalignment(btCalculate, HPos.RIGHT); // Right align the calculate button
	    tfFutureValue.setBackground(null); // Set the text box background to be clear - Indicating that the text cannot be changed
	    
	    // Process events
	    btCalculate.setOnAction(e -> calculateFutureAmount()); // When the Calculate button is clicked, activate the calculateFutureAmount method
	    
	    // Create a scene and place it in the stage
	    Scene scene = new Scene(pane);
	    primaryStage.setTitle("Investment-Value Calculator"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage
	    
	}
	
	private void calculateFutureAmount() // Calculate the Future Amount and display it in the Future Value field
	{
		double investmentAmount = 0; // Hold the input Investment Amount value
		double years = 0; // Hold the input Years value
		double annualInterestRate = 0; // Hold the input Annual Interest Rate value
		double futureValue = 0; // Hold a calculated Future Value value
		boolean correctData = true; // Used to determine whether Future Value should be computed or not
		
		// Get values from text fields and output 
		try
		{
			// Read values from text fields, and save them as double numbers in their respective variables
			investmentAmount = Double.parseDouble(tfInvestmentAmount.getText());
			years = Double.parseDouble(tfYears.getText());
			annualInterestRate = Double.parseDouble(tfAnnualInterestRate.getText());
			
			// If any of the values are outside of the allotted range
			if (investmentAmount < 1 || investmentAmount > 100000 || years < 1 || years > 20 || annualInterestRate < 1 || annualInterestRate > 12.5)
			{
				// Alert the user of the input requirements
				alertDialog("Input Out Of Range", "Please ensure that:\nThe Investment Amount is greater than $1 and less than $100,000\nThe number of years is greater than 1 and less than 20\nThe Annual Interest Rate is greater than 1% and less than 12.5%", 500, 125);
				correctData = false; // Incorrect data was input, so calculation and output should not take place
			}
			else
			{
				correctData = true; // Correct data was input, so calculation and output should take place
			}
		}
		catch (NumberFormatException e) // If the user did not fill all required fields or input invalid characters
		{
			// Alert the user of invalid input
			alertDialog("Invalid Input", "Please ensure that all fields contain numbers", 400, 100);
			correctData = false; // Invalid data was input, so calculation and output should not take place
		}
		catch (Exception e) // If unknown errors occur
		{
			// Alert the user of the unknown error
			alertDialog("Unknown Error", "An unknown error has occurred.", 300, 100);
		}
		
		if (correctData) // If correct data was input
		{
			// Compute the Future Value using the given equation
			futureValue = (investmentAmount * Math.pow((1 + ((annualInterestRate * 0.01)/12)), (years * 12)));
			
			// Output the computed Future Value to its respective text field
			tfFutureValue.setText(new DecimalFormat("$###,###,###,##0.00").format(futureValue));
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