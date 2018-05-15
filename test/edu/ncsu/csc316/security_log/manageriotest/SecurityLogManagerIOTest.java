/**
 * 
 */
package edu.ncsu.csc316.security_log.manageriotest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.security_log.dictionary.HashTable;
import edu.ncsu.csc316.security_log.dictionary.LogEntry;
import edu.ncsu.csc316.security_log.managerio.SecurityLogManagerIO;

/**
 * The io test class 
 * @author Ryan Buchanan
 *
 */
public class SecurityLogManagerIOTest {
	private SecurityLogManagerIO ioTest;
	private HashTable<LogEntry> table;
	/**
	 * set up
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		table = new HashTable<LogEntry>();
		ioTest = new SecurityLogManagerIO();
	}
	/**
	 * read file
	 * Test method for {@link edu.ncsu.csc316.security_log.managerio.SecurityLogManagerIO#readFile(java.lang.String)}.
	 */
	@Test
	public void testReadFile() {
		ioTest.readFile(table, "input/activityLog_small.txt");
		if(table.isEmpty())
			fail("Table is empty");
		LogEntry tester = new LogEntry("quhundley", "09/21/2017 08:50:13AM", "import", "office visit OV04312");
		assertTrue(table.lookUp(tester) != null);
		assertTrue(table.lookUp(tester).equals(tester));
	}
	/**
	 * read bigger file
	 * Test method for {@link edu.ncsu.csc316.security_log.managerio.SecurityLogManagerIO#readFile(java.lang.String)}.
	 */
	@Test
	public void testReadFileM() {
		ioTest.readFile(table, "input/activityLog_medium.txt");
		if(table.isEmpty())
			fail("Table is empty");
		LogEntry tester = new LogEntry("lqmadden", "04/17/2017 06:59:40PM", "create", "system alert CA01597");
		assertTrue(table.lookUp(tester) != null);
		assertTrue(table.lookUp(tester).equals(tester));
	}
}
