package edu.ncsu.csc316.security_log.managerui;

import java.io.File;
import java.util.Scanner;

import edu.ncsu.csc316.security_log.manager.SecurityLogManager;
/**
 * The user interface for the project
 * @author Ryan Buchanan
 *
 */
public class SecurityLogManagerUI {
	private static SecurityLogManager slm;
	
	/**
	 * The main method of the program that handles
	 * all of the user interface and interaction.
	 * @param args string array
	 */
	public static void main(String[] args){
		System.out.println("Welcome to the Security Log Manager. ");
		System.out.println(" ");
		Scanner input = new Scanner(System.in);	
		String inputFile;
		
		//Loop until user provides some from of valid input.
		while( true ){
			System.out.println("Please enter a valid file to continue (or 'exit' to quit): ");
			inputFile = input.nextLine();
			//If the user enters 'exit' the program exits
			if(inputFile.equals("exit")){
				System.out.println("Goodbye.");
				System.exit(0);
			}
			//Check for file validity
			else if(!(new File(inputFile).exists())){
				System.out.println("The given file path was invalid, please try again.");
				System.out.println(" ");
				continue;
			}
			break; 
		}
		slm = new SecurityLogManager(inputFile);
		main:
		while( true ) {
			String command = null;
			System.out.println(" ");
			System.out.println("Welcome to the security log manager main menu.");
			System.out.println("From here you may choose to: ");
			System.out.println("1) type 'exit' to exit.");
			System.out.println("2) type 'opp' to generate an operational profile.");
			System.out.println("3) type 'use' to generate a user report.");
			System.out.println(" ");
			command = input.nextLine();
			
			if( command.equals("exit") ){
				System.out.println("Goodbye.");
				break;
			} else if( command.equals("opp") ){
				System.out.println("Please enter a start Day: ");
				String sDay = input.nextLine();
				System.out.println("Please enter an end Day: ");
				String eDay = input.nextLine();	
				System.out.println(slm.generateOperationalProfile(sDay, eDay));
				continue main;
			} else if( command.equals("use") ){
				System.out.println("Please enter a valid user: ");
				String username = input.nextLine();
				System.out.println(slm.getUserReport(username));
				continue main;
			} else {
				System.out.println("That was an invalid command, please try again.");
				continue main;
			}						
		}
		input.close();	
	}
}
