/**
 * Text File Encryption/Decryption Program.
 * 
 * Matt Warman 
 * Version 1.1
 */

import java.io.*;
import java.util.Scanner;

public class Encryption_Engine {
	
	static Scanner input = new Scanner(System.in); // initialize a scanner to be reused
	
	public static void main(String args[])
	{
		while (true) // run the main menu until the user elects to end the program
		{
			System.out.print("MI6 Encryption/Decryption\nMain Menu\n\n          Please select an Option\n\n          1. Encrypt a File\n          2. Decrypt a File\n\n          E. Exit\n\nInput Selection: ");
			switch(input.next()) // evaluate user input
			{
			case "1": // user elects to encrypt a file
				fileEncryption(getFileName("\nInput name of file to be encrypted: "), getFileName("Input encrypted output file name: "), key());
				System.out.print("\n\n");
				break;
			case "2": // user electes to decrypt a file
				fileDecryption(getFileName("\nInput name of file to be decrypted: "), getFileName("Input decrypted output file name: "), key());
				System.out.print("\n\n");
				break;
			case "E": // user elects to exit
			case "e":
				exit();
				break;
			default: // in the event that the user inputs an invalid option
				System.out.print("\n\nInvalid Input\n\n\n");
				break;
				
			}
		}
	}

	public static void fileEncryption(String inputFileName, String outputFileName, int key) // encrypt a given file with a given key and output to a given file
	{
		DataInputStream fileInput = null; // initialize a DataInputStream to read data
		DataOutputStream outputStream = null; // initialize a DataOutputStream to write data
		int characterToEncrypt = 0; // hold the character to be encrypted based on the given key
		
		try
		{
			fileInput = new DataInputStream(new FileInputStream(inputFileName + ".txt")); // open the text file to read from
			outputStream = new DataOutputStream(new FileOutputStream(outputFileName + ".dat")); // open the data file to write to
			while ((characterToEncrypt = fileInput.read()) != -1) // as long as there are characters to encrypt, load them in characterToEncrypt
			{
				outputStream.write(characterToEncrypt + key); // encrypt the character by adding the key to it, then store it in the data file
			}
		}
		catch (FileNotFoundException e) // catch file not found errors
		{
			System.out.print("\n\nFile not found");
			pause();
		}
		catch (IOException e) // catch file access errors
		{
			System.out.print("\n\nThere was an error accessing the file");
			pause();
		}
	}
	
	public static void fileDecryption(String inputFileName, String outputFileName, int key)
	{
		DataInputStream fileInput = null; // initialize a DataInputStream to read data
		DataOutputStream outputStream = null; // initialize a DataOutputStream to write data
		int characterToDecrypt = 0; // hold the character to be decrypted based on the given key
		
		try
		{
			fileInput = new DataInputStream(new FileInputStream(inputFileName + ".dat")); // open the data file to read from
			outputStream = new DataOutputStream(new FileOutputStream(outputFileName + ".txt")); // open the text file to write to
			while ((characterToDecrypt = fileInput.read()) != -1)// as long as there are characters to decrypt, load them in characterToDecrypt
			{
				outputStream.write(characterToDecrypt - key); // decrypt the character by adding the key to it, then store it in the text file
			}
			
		}
		catch (FileNotFoundException e)// catch file not found errors
		{
			System.out.print("\n\nFile not found");
			pause();
		}
		catch (IOException e) // catch file access errors
		{
			System.out.print("\n\nThere was an error accessing the file");
			pause();
		}
	}
	
	public static int key() // Retrieve user input encryption key and make sure that it conforms to the necessary restrictions
	{
		int key = 0; // initialize the key variable
		do
		{
			key = getIntInput("Please input key value: "); // receive int input before checking it
			if (key < 1 || key >  500) // if the key does not conform to the restrictions
			{
				System.out.print("\n\nInvalid value input. Please try again.\n\n");
			}
		} while (key < 1 || key > 500); // run the loop until the user inputs a key that conforms to the restrictions
		return key; // return the user input key
	}
	
    public static int getIntInput(String prompt) // facilitates an easy and reusable way to take in sanitized user input integer values.
    {
        int value = 0; // initialize the placeholder value
        boolean correctData = true; // this aids in looping in the event that an incorrect data type is entered
        do // since it needs to execute at least once
        {
            System.out.print(prompt); // ask the user to input whatever it was that we wanted them to input. See above.
            input = new Scanner(System.in); // initialize the placeholder input scanner
            if (input.hasNextInt()) // was the input data actually an int?
            {
                value = input.nextInt(); // if so, store it in the placeholder variable as such
                if (value >= 0) // if the input value is zero or positive
                {
                    correctData = true; // the correct data type was entered
                }
                else // if the input value is negative
                {
                	System.out.print("\nYou cannot input a negative value.\nPlease try again.\n");
                	correctData = false; // incorrect data was input
                }
            }
            else
            {
                System.out.print("\nInvalid value input. Please try again.\n");
                correctData = false; // the wrong data type was entered
            }
        } while (correctData == false); // if the wrong data type was entered, give the user another chance to enter correct data
        return value; // return the value stored in the placeholder variable
    }
    
	public static String getFileName(String prompt) // Facilitates an easy and reusable way to take in user string input
	{
		String fileName;
		input = new Scanner(System.in); // prepare the input scanner
		System.out.print(prompt); // display the prompt for the user
		fileName = input.nextLine();
		if (fileName.endsWith(".txt") || fileName.endsWith(".dat"))
		{
			fileName = fileName.substring(0, fileName.length() - 4);
		}
			
		return fileName; // return the user input as a string
	}
	
    public static void pause() // this is used to pause the program so the user can choose to continue when they are ready
    {
        System.out.print("\nPress enter to continue");
        input = new Scanner(System.in); // initialize a scanner
        if (input.hasNextLine()){}; // wait for the user to press enter before continuing
    }
	
	public static void exit()
	{
		System.out.print("Thank you for using MI6 Encryption."); // print exiting message
		input.close(); // close the input scanner
		System.exit(0); // end the program
	}
}
