/**
 * TestComparable prompts the user to input two circle radii, compares them, prints which circle is larger,
 * 	and asks whether or not the user would like to run the program again.
 * 
 * Matt Warman
 * 02/15/2015
 */

import java.util.Scanner;
import javax.swing.JOptionPane;

public class TestComparable {
	
	static Scanner input = new Scanner(System.in); // MKW - initialize a scanner to be reused

  // Main method
  public static void main(String[] args) {
	  int endProgram = 0; // MKW - Store value to determine whether the user wants to continue the program or not
	  do // MKW - run the following until the user wants to end the program
	  {
		    // Create two comparable circles
		    Circle1 circle1 = new Circle1(getDoubleInput("Please input the radius of the first circle: "));
		    Circle1 circle2 = new Circle1(getDoubleInput("Please input the radius of the second circle: "));

		    // Display the max circle
		    Circle1 circle = (Circle1)GeometricObject1.max(circle1, circle2);
		    System.out.println("\nThe max circle's radius is " +
		      circle.getRadius());
		    System.out.println(circle);
		    
		    // MKW - Prompt the user to choose whether they would like to run the program again or not
			endProgram = JOptionPane.showConfirmDialog(null, "Would you like to compare another set of circles?", "Run Again?", JOptionPane.YES_NO_OPTION);
			System.out.print("\n");
	  } while (endProgram == 0); // MKW - if the user elects to compare more circles, loop the program
  }
  
  // MKW - This method obtains sanitized user double input
  public static double getDoubleInput(String prompt) // MKW - facilitates an easy and reusable way to take in sanitized user input double values.
  {
      double value = 0; // MKW - initialize the placeholder value
      boolean correctData = true; // MKW - this aids in looping in the event that an incorrect data type is entered
      do // MKW - since it needs to execute at least once
      {
          System.out.print(prompt); // MKW - ask the user to input whatever it was that we wanted them to input. See above.
          input = new Scanner(System.in); // MKW - initialize the placeholder input scanner
          if (input.hasNextDouble()) // MKW - was the input data actually a double?
          {
              value = input.nextDouble(); // MKW - if so, store it in the placeholder variable as such
              if (value >= 0) // MKW - if the input value is zero or positive
              {
              	correctData = true; // MKW - the correct data type was entered
              }
              else // MKW - if the input value is negative
              {
              	System.out.print("\nYou cannot input a negitive value.\nPlease try again.");
              	correctData = false; // MKW - incorrect data was input
              }
          }
          else
          {
              System.out.print("\nYou have input an invalid value. Please try again.\n");
              correctData = false; // MKW - the wrong data type was entered
          }
      } while (!correctData); // MKW - if the wrong data type was entered, give the user another chance to enter correct data
      return value; // MKW - return the value stored in the placeholder variable
  }
}
