/**
 * LoanComparison take in a user input loan amount and number of years, then displays a chart of
 * 	the loan's monthly payment and total payment sorted by annual interest rate from five percent
 * 	to eight percent incrementing by 0.125 percent each time.
 * 
 * Matt Warman
 * Version: 1.2
 * Date: 03/12/2015
 */

import java.text.DecimalFormat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoanComparison extends Application
{
	// Initialize private variables
	private TextField tfLoanAmount = new TextField(); // Serve as a text field for receiving input loan amounts
	private TextField tfYears = new TextField(); // Serve as a text field for receiving input numbers of years
	private TextArea taOutput = new TextArea(); // Serve as an output text field to display calculated output
	private Button btGo = new Button("Show Table"); // Serve as a button that initiates future investment value calculation
    private Button btOK = new Button("OK"); // Serve as a button that closes an alert dialog box
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage)
	{
		// Initialize pane
		final BorderPane mainWindow = new BorderPane(); // Initialize a new BorderPane making up the main window
		
		HBox topBar = new HBox(10); // Initialize a new HBox to construct the top bar containing labels and text fields
		Label lbLoanAmount = new Label("Loan Amount: "); // Initialize a "Loan Amount: " label
		Label lbYears = new Label("Years: "); // Initialize a "Years: " label
		tfLoanAmount.setPrefWidth(150); // Set the width of the Loan Amount text field
		tfYears.setPrefWidth(35); // Set the width of the Years text field
		topBar.setAlignment(Pos.CENTER); // Align the topBar HBox so that it lies in the center of its section of the window
		topBar.getChildren().addAll(lbLoanAmount, tfLoanAmount, lbYears, tfYears, btGo); // Add all the children to the topBar HBox
		
		mainWindow.setTop(topBar); // Put the topBar HBox in the top section of the main window
		mainWindow.setCenter(taOutput); // Put the output text area in the center of the main window
		taOutput.setWrapText(true); // Turn on text wrapping so that an unnecessary horizontal scrollbar doesn't appear
		BorderPane.setMargin(taOutput, new Insets(10, 0, 0, 0)); // Set margins on the output text area so that there is some padding between it and the top bar
		mainWindow.setPadding(new Insets(10, 10, 10, 10)); // Put a border around everything in the main window
		
		btGo.setOnAction(e -> calculateOutput()); // When the user presses the "Show Table" button, run the calculations and output the results
		
		Scene scene = new Scene(mainWindow, 500, 300); // Initialize the scene
		primaryStage.setTitle("Loan Comparison"); // Set the window title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}
	
	private void calculateOutput() // Calculate the Future Amount and display it in the Future Value field
	{
		boolean correctData = true; // Used to determine whether Future Value should be computed or not
		Loan loan = new Loan (); // Initialize an instance of a loan
		Double years = 0.0; // Used to determine whether input years is a double or not
		
		try // to catch any errors nicely
		{
			loan.setAnnualInterestRate(5); // Set the initial loan annual interest rate to 5
			loan.setLoanAmount(Double.parseDouble(tfLoanAmount.getText())); // Set the loan amount to the input amount
			years = Double.parseDouble(tfYears.getText()); // Store the input years value in a variable to be examined
			
			// If any of the values are outside of the allotted range
			if (loan.getLoanAmount() < 1000 || loan.getLoanAmount() > 100000 || years < 1 || years > 20)
			{
				// Alert the user of the input requirements
				alertDialog("Input Out Of Range", "Please ensure that:\nThe Loan Amount is greater than $1,000 and less than $100,000\nThe number of years is greater than 1 and less than 20", 500, 125);
				correctData = false; // Incorrect data was input, so calculation and output should not take place
			}
			else if (years != Math.floor(years)) // If the input number of years was a double value
			{
				// Alert the user that years must be an integer
				alertDialog("Invalid Number of Years", "The number of years must be input as an integer", 400, 100);
				tfYears.setText(Integer.toString((int) Math.floor(years))); // Round the input double value to the closest integer
				correctData = false; // Incorrect data was input, so calculation and output should not take place
			}
			else
			{
				loan.setNumberOfYears(Integer.parseInt(tfYears.getText())); // Set the number of years for the loan to the input amount
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
			taOutput.setText("Interest Rate                    Monthly Payment                    "
					+ "Total Payment\n"); // Print text area header
			do // Print lines of loan information until stopped
			{
				// Print current loan annual interest rate, then the corresponding monthly payment, then the corresponding total payment
				taOutput.appendText(new DecimalFormat("0.000%").format(loan.getAnnualInterestRate()/100.0) + "                  " +
						new DecimalFormat("$###,##0.00").format(loan.getMonthlyPayment()) + "                         " +
						new DecimalFormat("$###,##0.00").format(loan.getTotalPayment()) + "\n");
				loan.setAnnualInterestRate(loan.getAnnualInterestRate() + 0.125); // Increase the annual interest rate by 0.125 percent
				
			} while (loan.getAnnualInterestRate() <= 8); // Continue printing incrementing lines until the annual interest rate goes above eight percent
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