/**
 * 
 */
package edu.ncsu.csc316.security_log.dictionary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Time stamp class
 * @author Ryan Buchanan
 *
 */
public class Timestamp {
	private String date; 
	private String time;
	private String ampm;
	private Date stamp; 
	/** 
	 * The constructor
	 * @param line is the string to convert
	 */
	public Timestamp(String line){
		if(line == null || line.isEmpty())
			return;
		SimpleDateFormat dateThing = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		setDate(line);
		setTime(line);
		setAMPM(line);
		try {
			stamp = dateThing.parse(date + " " + time + " " + ampm);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Sets the ampm string
	 * @param line line
	 */
	private void setAMPM(String line){
		this.ampm = line.substring(19, 21);
	}
	/**
	 * Sets the time string
	 * @param line line
	 */
	private void setTime(String line){
		this.time = line.substring(11, 19);
	}
	/**
	 * Sets the date string
	 * @param line line
	 */
	private void setDate(String line){
		this.date = line.substring(0, 10);		
	}
	/**
	 * returns date
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * returns the time
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * returns ampm 
	 * @return the ampm
	 */
	public String getAmpm() {
		return ampm;
	}
	/**
	 * returns the object as a Date
	 * @return the stamp
	 */
	public Date getStamp() {
		return stamp;
	}
	/**
	 * Conerts the object into a string
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(date + " " + time + "" + ampm);
		return builder.toString();
	} 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ampm == null) ? 0 : ampm.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((stamp == null) ? 0 : stamp.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) { 
		if (this == obj) 
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Timestamp other = (Timestamp) obj;
		if (ampm == null) {
			if (other.ampm != null)
				return false;
		} else if (!ampm.equals(other.ampm))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (stamp == null) {
			if (other.stamp != null)
				return false;
		} else if (!stamp.equals(other.stamp))
			return false; 
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	
}
