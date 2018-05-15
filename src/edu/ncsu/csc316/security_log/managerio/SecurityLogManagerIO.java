package edu.ncsu.csc316.security_log.managerio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc316.security_log.dictionary.HashTable;
import edu.ncsu.csc316.security_log.dictionary.LogEntry;
/**
 * IO for the slm2 project
 * @author Ryan Buchanan
 *
 */
public class SecurityLogManagerIO {

	/**
	 * Reads the given file and adds the entries to the list
	 * @param table is the table to fill up
	 * @param file name of the file to read
	 */
	public void readFile(HashTable<LogEntry> table, String file){
		try {
			File filename = new File(file);
			Scanner fileReader = new Scanner(filename);	 
			fileReader.nextLine(); //Skip the first line
			
			while(fileReader.hasNextLine()){
				process(table, fileReader.nextLine());
			}
			
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Invalid File");
		}
	} 
	/**
	 * Processes a line.
	 * @param line
	 */
	private void process(HashTable<LogEntry> table, String line){
		Scanner lineScan = new Scanner(line);
		lineScan.useDelimiter(", ");
		// ...
		String user = lineScan.next();
		String time = lineScan.next();
		String action = lineScan.next();
		String resource = lineScan.next();
		// ...
		LogEntry entry = new LogEntry(user, time, action, resource);
		// ... 
		table.insert(entry);
		//System.out.println(user + ", " + time + ", " + action + ", " + resource);
		
		lineScan.close();
	}
}
