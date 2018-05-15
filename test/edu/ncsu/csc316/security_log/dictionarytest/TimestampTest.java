/**
 * 
 */
package edu.ncsu.csc316.security_log.dictionarytest;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import edu.ncsu.csc316.security_log.dictionary.Timestamp;

/**
 * Test for timestamp object
 * @author Ryan Buchanan
 *
 */ 
public class TimestampTest {

	/**
	 * test constructor and all other stuff
	 * Test method for {@link edu.ncsu.csc316.security_log.dictionary.Timestamp#Timestamp(java.lang.String)}.
	 */
	@Test 
	public void testTimestamp() {
		Timestamp stampers = new Timestamp("04/26/2017 12:33:15PM");
		Timestamp stampers2 = new Timestamp("04/26/2017 12:33:15PM");
		Timestamp stampers3 = new Timestamp("05/26/2017 12:33:15PM");
		Timestamp stampers4 = new Timestamp("04/26/2017 12:33:15AM");
		Timestamp stampers5 = new Timestamp("04/26/2017 12:33:16AM");
		Timestamp stampersN = null;
		SimpleDateFormat dateThing = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		Date test = null;
		try { 
			test = dateThing.parse("04/26/2017 12:33:15 PM");
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		assertTrue(stampers.equals(stampers2));
		assertFalse(stampers.equals(stampersN));
		assertFalse(stampers.equals(stampers3));
		assertFalse(stampers.equals(stampers4));
		assertFalse(stampers.equals(stampers5));
		
		//System.out.println(stampers.getDate());
		assertTrue(stampers.getDate().equals("04/26/2017"));
		//System.out.println(stampers.getTime());
		assertTrue(stampers.getTime().equals("12:33:15"));
		//System.out.println(stampers.getAmpm());
		assertTrue(stampers.getAmpm().equals("PM"));	
		assertTrue(stampers.getStamp().equals(test));
		//System.out.println(stampers.toString());
		assertTrue(stampers.toString().equals("04/26/2017 12:33:15PM"));
		
		Timestamp stampers6 = new Timestamp("");
		Timestamp stampers7 = new Timestamp(null);
		assertTrue(!stampers.equals(stampers6));
		assertTrue(!stampers.equals(stampers7));
	} 
}
