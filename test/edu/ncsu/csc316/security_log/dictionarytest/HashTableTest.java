/**
 * 
 */
package edu.ncsu.csc316.security_log.dictionarytest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.security_log.dictionary.HashTable;
import edu.ncsu.csc316.security_log.dictionary.LogEntry;
import edu.ncsu.csc316.security_log.managerio.SecurityLogManagerIO;

/** 
 * Test for the hash table
 * @author Ryan Buchanan
 *
 */
public class HashTableTest {  
	private SecurityLogManagerIO ioTest;
	private HashTable<LogEntry> table;
	/**
	 * set up
	 * @throws java.lang.Exception exception for the test
	 */
	@Before
	public void setUp() throws Exception {
		table = new HashTable<LogEntry>(); 
		ioTest = new SecurityLogManagerIO();
	}

	/**
	 * test the table creation
	 * Test method for {@link edu.ncsu.csc316.security_log.dictionary.HashTable#HashTable()}.
	 */
	@Test
	public void testHashTable() {
		assertTrue(table != null);
		assertTrue(table.size() == 0);
		assertTrue(table.getHashTableLength() == 500);	
	}

	/**
	 * test the insert fxn
	 * Test method for {@link edu.ncsu.csc316.security_log.dictionary.HashTable#insert(java.lang.Object)}.
	 */
	@Test
	public void testInsert() {
		ioTest.readFile(table, "input/activityLog_medium.txt");
		if(table.isEmpty())
			fail("Table is empty");
		LogEntry tester = new LogEntry("lqmadden", "04/17/2017 06:59:40PM", "create", "system alert CA01597");
		assertTrue(table.lookUp(tester) != null);
		assertTrue(table.lookUp(tester).equals(tester));
		//Test adding a new entry. 
		LogEntry newEntry = new LogEntry("test", "04/20/2018 01:15:45AM", "testing", "the thing");
		table.insert(newEntry);
		assertTrue(table.lookUp(newEntry) != null);
		assertTrue(table.lookUp(newEntry).equals(newEntry));
	}

	/**
	 * test the look up fxn
	 * Test method for {@link edu.ncsu.csc316.security_log.dictionary.HashTable#lookUp(java.lang.Object)}.
	 */
	@Test
	public void testLookUp() {
		ioTest.readFile(table, "input/activityLog_medium.txt");
		if(table.isEmpty())
			fail("Table is empty");
		LogEntry tester = new LogEntry("lqmadden", "04/17/2017 06:59:40PM", "create", "system alert CA01597");
		assertTrue(table.lookUp(tester) != null);
		assertTrue(table.lookUp(tester).equals(tester));
		//Test adding a new entry.
		LogEntry newEntry = new LogEntry("test", "04/20/2018 01:15:45AM", "testing", "the thing");
		assertTrue(table.lookUp(newEntry) == null);
		table.insert(newEntry);
		assertTrue(table.lookUp(newEntry) != null);
		assertTrue(table.lookUp(newEntry).equals(newEntry));
		  
		LogEntry newEntry2 = new LogEntry("fzalcala", "02/19/2016 12:49:36AM", "update", "notification NX0017");
		table.insert(newEntry2);
		assertTrue(table.lookUp(newEntry2) != null);
		assertTrue(table.lookUp(newEntry2).equals(newEntry2));
	} 

	/**
	 * test size
	 * Test method for {@link edu.ncsu.csc316.security_log.dictionary.HashTable#size()}.
	 */
	@Test
	public void testSize() {
		ioTest.readFile(table, "input/activityLog_small.txt");
		if(table.isEmpty())
			fail("Table is empty");
		//System.out.println(table.size());
		assertTrue(table.size() == 16);
		assertFalse(table.size() == 1021);
	}

	/**
	 * test the capacity
	 * Test method for {@link edu.ncsu.csc316.security_log.dictionary.HashTable#getHashTableLength()}.
	 */
	@Test
	public void testGetHashTableLength() {
		ioTest.readFile(table, "input/activityLog_small.txt");
		if(table.isEmpty())
			fail("Table is empty");
		assertTrue(table.getHashTableLength() == 500);
		assertFalse(table.getHashTableLength() == 16);
	}

}
