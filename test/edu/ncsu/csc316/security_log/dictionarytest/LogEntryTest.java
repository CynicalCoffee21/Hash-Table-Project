package edu.ncsu.csc316.security_log.dictionarytest;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;

import edu.ncsu.csc316.security_log.dictionary.LogEntry;

/**
 * The test cases for the LogEntry object class.
 * @author Ryan Buchanan
 *
 */ 
public class LogEntryTest { 
	/**
	 * Test the creation of a LogEntry object
	 */
	@Test
	public void testLogEntry() {
		LogEntry log1 = null;
		try{   
			log1 = new LogEntry("nrbuchan", "01/02/2012 01:22:22AM", "view", "testing resource"); 
		} catch(Exception e){ 
			fail("Unable to create log entry"); 
		}
		assertFalse(log1 == null);  
		assertFalse(log1.equals("hello"));
		LogEntry log12 = new LogEntry("testName", "01/02/2012 01:22:22am", "view", "testing resource"); 
		assertFalse(log1.equals(log12)); 
		
		LogEntry log2 = new LogEntry(null, "01/02/2012 01:22:22am", "view", "testing resource"); 
		LogEntry log7 = new LogEntry(null, "01/02/2012 01:22:22am", "view", "testing resource"); 
		assertFalse(log1.equals(log2)); 
		assertFalse(log2.equals(log1));  
		assertTrue(log2.equals(log7));
		assertTrue(log2.hashCode() == log7.hashCode());
		LogEntry log3 = new LogEntry("nrbuchan", null, "view", "testing resource"); 
		LogEntry log6 = new LogEntry("nrbuchan", null, "view", "testing resource"); 
		assertFalse(log1.equals(log3));
		assertFalse(log3.equals(log1));
		assertTrue(log3.equals(log6));
		assertTrue(log3.hashCode() == log6.hashCode());
		LogEntry log4 = new LogEntry("nrbuchan", "01/02/2012 01:22:22am", null, "testing resource"); 
		LogEntry log8 = new LogEntry("nrbuchan", "01/02/2012 01:22:22am", null, "testing resource"); 
		assertFalse(log1.equals(log4));
		assertFalse(log4.equals(log1)); 
		assertTrue(log4.equals(log8));
		assertTrue(log4.hashCode() == log8.hashCode());
		LogEntry log5 = new LogEntry("nrbuchan", "01/02/2012 01:22:22am", "view", null);
		LogEntry log9 = new LogEntry("nrbuchan", "01/02/2012 01:22:22am", "view", null);
		assertFalse(log1.equals(log5));
		assertFalse(log5.equals(log1));
		assertTrue(log5.equals(log9));
		assertTrue(log5.hashCode() == log9.hashCode());
		log2.setNext(log1);
		log7.setNext(log1); 
		LogEntry log10 = new LogEntry(null, "01/02/2012 01:22:22am", "view", "testing resource"); 
		LogEntry log11 = new LogEntry(null, "01/02/2012 01:22:22am", "view", "testing resource"); 
		log10.setNext(log3);
		assertTrue(log2.equals(log7));
		assertFalse(log2.equals(log3)); 
		assertFalse(log2.equals(log11));
		assertTrue(log2.getNext().equals(log7.getNext()));
		assertTrue(log2.hashCode() == log7.hashCode());
	}
	/**
	 * Test to see if the username given, is the same
	 *  as the username set.
	 */
	@Test
	public void testUserName(){
		LogEntry log1 = new LogEntry("nrbuchan", "01/02/2012 01:22:22am", "view", "testing resource"); 
		String uname = "nrbuchan"; 
		if( !uname.equals(log1.getUserName()) ){
			fail("User names did not match.");
		}	
	}
	/**
	 * Tests whether the action given is the same as,
	 * the action set.
	 */
	@Test
	public void testAction(){
		LogEntry log1 = new LogEntry("nrbuchan", "01/02/2012 01:22:22am", "view", "testing resource");			
		String action = "view";

		if( !action.equals(log1.getAction()) ){
			fail("Action did not match");
		}
		
		LogEntry log2 = new LogEntry("nrbuchan", "01/02/2012 01:22:22am", "check", "testing resource");			
		assertFalse(log1.equals(log2));
	}
	/**
	 * Tests whether the resource given is the same as the
	 *  resource set.
	 */
	@Test
	public void testResource(){
		LogEntry log1 = new LogEntry("nrbuchan", "01/02/2012 01:22:22am", "view", "testing resource");			
		String resource = "testing resource";
		
		if( !resource.equals(log1.getResource()) ){
			fail("Resource did not match");
		}
		
		LogEntry log2 = new LogEntry("nrbuchan", "01/02/2012 01:22:22am", "view", "testing resource 2");			
		assertFalse(log1.equals(log2));
	}
	/**
	 * Tests whether the resource given is the same as the
	 *  resource set.
	 */
	@Test
	public void testTimestamp(){
		LogEntry log1 = new LogEntry("nrbuchan", "01/02/2012 01:22:22AM", "view", "testing resource");			
		String timeStamp = "01/02/2012 01:22:22 AM";
		String timeStamp2 = "01/02/2012 01:22:22 PM";
		SimpleDateFormat dateThing = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		Date test = null;
		Date test2 = null;
		try { 
			test = dateThing.parse(timeStamp);
			test2 = dateThing.parse(timeStamp2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if( !test.equals(log1.getTimestamp().getStamp()) ){
			fail("timestamps did not match");
		}
		//System.out.println(log1.getTimestamp().getStamp() + " : " + test2);
		assertFalse(test2.equals(log1.getTimestamp().getStamp()));
		
		LogEntry log2 = new LogEntry("nrbuchan", "01/02/2012 01:22:22AM", "view", "testing resource");			
		assertTrue(log1.equals(log2));
		LogEntry log3 = new LogEntry("nrbuchan", "02/02/2012 01:22:22AM", "view", "testing resource");			
		assertFalse(log1.equals(log3));
	}

}	
