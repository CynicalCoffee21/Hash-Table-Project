/**
 * 
 */
package edu.ncsu.csc316.security_log.managertest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.security_log.manager.SecurityLogManager;

/**
 * The test for the slm main class. Most of the testing of these methods is via black box.
 * Therefore, most of these tests are going to be relatively trivial.
 * @author Ryan Buchanan 
 * 
 */
public class SecurityLogManagerTest {
	private SecurityLogManager slm;
	private SecurityLogManager slm2;
	/**
	 * set up
	 * @throws java.lang.Exception exception for the test
	 */  
	@Before
	public void setUp() throws Exception {		
		try{
			slm = new SecurityLogManager("input/activityLog_medium.txt"); 
			slm2 = new SecurityLogManager("input/activityLog_small.txt"); 
		} catch(Exception e){
			fail("Cannot create slm");  
		} 
	}

	/**
	 * constructor test
	 * Test method for {@link edu.ncsu.csc316.security_log.manager.SecurityLogManager#SecurityLogManager(java.lang.String)}.
	 */
	@Test
	public void testSecurityLogManager() {
		if (slm == null)
			fail("slm was null");
		assertTrue(slm.generateOperationalProfile("08/04/2016 06:57:34AM", "04/26/2017 12:33:15PM") != null);
		assertTrue(slm.getUserReport("quhundley") != null);
	}

	/**
	 * test gen opp
	 * Test method for {@link edu.ncsu.csc316.security_log.manager.SecurityLogManager#generateOperationalProfile(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGenerateOperationalProfile() {
		if (slm == null) 
			fail("slm was null"); 
		assertTrue(slm.generateOperationalProfile("08/04/2016 06:57:34AM", "04/26/2017 12:33:15PM") != null);
		assertTrue(slm.generateOperationalProfile("01/04/2015 06:57:34AM", "03/06/2017 12:33:15PM") != null);

	}
 
	/**
	 * test user rep
	 * Test method for {@link edu.ncsu.csc316.security_log.manager.SecurityLogManager#getUserReport(java.lang.String)}.
	 */
	@Test
	public void testGetUserReport() {
		if (slm2 == null) 
			fail("slm2 was null");
			String report = "Activity Report for quhundley[\n"
			  + "   07/18/2015 07:57:42PM - sort ICD-9 Code 196\n"
			  + "   02/04/2016 08:49:22AM - sort ICD-9 Code 196\n"				
			  + "   11/20/2016 02:07:54PM - sort ICD-9 Code 196\n"	
			  + "   08/04/2017 11:01:45AM - sort ICD-9 Code 196\n"
			  + "   09/21/2017 08:50:13AM - import office visit OV04312\n"
			  + "]";
			String badReport = "Activity Report for nrbuchan[\n"
					+ "   No activity was recorded.\n"
					+ "]";		
		//System.out.println(slm2.getUserReport("quhundley"));
		assertTrue(report.equals(slm2.getUserReport("quhundley")));
		assertTrue(slm.getUserReport("quhundley") != null);
		//System.out.println(slm2.getUserReport("nrbuchan"));
		assertTrue(badReport.equals(slm2.getUserReport("nrbuchan")));
	}

}
