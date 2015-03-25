/**
 * Employee object for use with PayrollCalculator
 * 
 * Matt Warman
 * Date: 03/16/2015
 * Version: 1.1
 */

public class Employee {
	
	private int employeeNumber; // Hold the employee ID number
	private double basePay; // Hold employee hourly pay
	private double hoursWorked; // Hold the number of hours worked by the employee
	
	Employee() // default employee constructor
	{
		employeeNumber = 0; // default employee number
		basePay = 8.25; // default employee base pay
		hoursWorked = 40; // default number of hours worked
	}
	
	// Construct an employee with a given employee number, base pay, and number of hours worked
	Employee(int inputEmployeeNumber, double inputBasePay, double inputHoursWorked)
	{
		employeeNumber = inputEmployeeNumber; // Save the given employee number
		basePay = inputBasePay; // Save the given base pay
		hoursWorked = inputHoursWorked; // Save the given hours worked
	}
	
	// Return the employee number
	int getEmployeeNumber()
	{
		return employeeNumber;
	}
	
	// Set the employee number to a new given one
	void setEmployeeNumber(int inputEmployeeNumber)
	{
		employeeNumber = inputEmployeeNumber;
	}
	
	// Return the employee's base pay
	double getBasePay()
	{
		return basePay;
	}
	
	// Set the employee's base pay to a new given amount
	void setBasePay(double inputBasePay)
	{
		basePay = inputBasePay;
	}
	
	// Return the employee's hours worked
	double getHoursWorked()
	{
		return hoursWorked;
	}
	
	// Set the employee's hours worked to a new given amount
	void setHoursWorked(double inputHoursWorked)
	{
		hoursWorked = inputHoursWorked;
	}
	
	/*
	* Return the amount of overtime worked as the ".5" portion of overtime's 1.5 times normal pay
	* EX: given 60 hours, getOvertime returns 10, making a total of 60 hours plus 10 more since 20
	* overtime hours times 1.5 equals 30. During program runtime, this is added to the hours used in
	* payment calculation, causing the employee to be paid for working 70 hours instead of 60, thus
	* correctly calculating overtime in a fairly simple way.
	*/
	double getOvertime()
	{	
		if (hoursWorked > 40) // If the employee worked overtime
		{
			return .5 * (hoursWorked - 40); // return .5 times the overtime worked - this is added to the already present base pay overtime
		}
		return 0; // return 0 if no overtime was worked
	}
}
