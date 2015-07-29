/**
 * A student has a unique ID, a name, a city, a state, and a class designation.
 * 
 * Matt Warman
 * Date: 05/06/2015
 * Version: 1.1
 */

public class Student
{
	// Declare variables
	private int number; // Student ID number
	private String name; // Student Name
	private String city; // Student City
	private String state; // Student State
	private String className; // Student Class (ex Freshman)
	
	Student() // Default student constructor
	{
		// Set default values
		number = 0;
		name = "Undefined";
		city = "Undefined";
		state = "Undefined";
		className = "Undefined";
	}
	
	// Student constructor given input
	Student(int inputNumber, String inputName, String inputCity, String inputState, String inputClassName)
	{
		// Set variables to amounts input
		number = inputNumber;
		name = inputName;
		city = inputCity;
		state = inputState;
		className = inputClassName;
	}
	
	// Getter method for student number
	int getNumber()
	{
		return number; // Return student number
	}
	
	// Setter method for student number
	void setNumber(int inputNumber)
	{
		number = inputNumber; // Set number to new input value
	}
	
	// Getter method for Student name
	String getName()
	{
		return name; // Return student name
	}
	
	// Setter method for Student name
	void setName(String inputName)
	{
		name = inputName; // Set name to new input value
	}
	
	// Getter method for student city
	String getCity()
	{
		return city; // Return student city
	}
	
	// Setter method for student city
	void setCity(String inputCity)
	{
		city = inputCity; // Set city to new input value
	}
	
	// Getter method for student state
	String getState()
	{
		return state; // Return student state
	}
	
	// Setter method for student state
	void setState(String inputState)
	{
		state = inputState; // Set state to new input value
	}
	
	// Getter method for student class name
	String getClassName()
	{
		return className; // Return student class name
	}
	
	// Setter method for student class name
	void setClassName(String inputClassName)
	{
		className = inputClassName; // Set class name to new input value
	}
	
	@Override
	public String toString() // Return student information in a pre-formatted, easy to display, string.
	{
		/*
		 * Output Text Area Restrictions:
		 * 
		 * Student ID: 10 characters max - The largest integer contains 10 digits ("2,147,483,647")
		 * Student Name: 26 characters max - Max determined by evaluating remaining space minus space for column separation
		 * Student City: 26 characters max
		 * Student State: 24 characters max - "Northern Mariana Islands", the longest state/territory name, is exactly 24 characters
		 * Student Class: 9 characters max - "Sophomore", the longest class name, is exactly 9 characters
		 */
		
		// Initialize StringBuilder strings for the 5 output values needed
		StringBuilder numberString = new StringBuilder(Integer.toString(number)); // Convert the integer number to a string before passing it
		StringBuilder nameString = new StringBuilder(name);
		StringBuilder cityString = new StringBuilder(city);
		StringBuilder stateString = new StringBuilder(state);
		StringBuilder classNameString = new StringBuilder(className);
		
		
		// Add white space when appropriate to put values in the right place for the output
		while (numberString.length() < 10) // If the number string is less than 18 characters
		{
			numberString.append(" "); // Add whitespace to number string until it is a total of 18 characters
		}
		
		while (nameString.length() < 26) // If the name string is less than 24 characters
		{
			nameString.append(" "); // Add whitespace to name string until it is a total of 24 characters
		}
		
		while (cityString.length() < 26) // If the city string is less than 24 characters
		{
			cityString.append(" "); // Add whitespace to city string until it is a total of 24 characters
		}
		
		while (stateString.length() < 24) // If the state string is less than 24 characters
		{
			stateString.append(" "); // Add whitespace to state string until it is a total of 24 characters
		}
		
		while (classNameString.length() < 9) // If the class name string is less than 9 characters
		{
			classNameString.append(" "); // Add whitespace to class name string until it is a total of 9 characters
		}
		
		// Truncate any strings that are longer than their allotted maximum so that they can be displayed correctly in the output table
		numberString.setLength(10);
		nameString.setLength(26);
		cityString.setLength(26);
		stateString.setLength(24);
		classNameString.setLength(9);
		
		// Return the completed string
		return (numberString + "  " + nameString+ "  " + cityString + "  " + stateString + "  " + classNameString);
	}
}