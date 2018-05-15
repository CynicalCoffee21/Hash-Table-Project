package edu.ncsu.csc316.security_log.dictionary;

/** 
 * The LogEntry is an object that represents a Security Log Entry.
 * @author Ryan Buchanan
 * I got a lot of help from the Java Documentation site, 
 * particularly when dealing with how to handle the timestamps:
 * 
 * https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html
 * 
 * 
 */
public class LogEntry {
	/**
	 * The next field
	 */
	private LogEntry next;
	/**
	 * Username 
	 */
	private String userName;
	/**
	 * action
	 */
	private String action;
	/**
	 * resource
	 */
	private String resource;
	/**
	 * timestamp
	 */
	private Timestamp timestamp;
	/**
	 *The log entry object represents a single entry into the 
	 * Security Log system.
	 * 
	 * Any single entry into the system requires a username, timestamp, action, and resource.
	 * @param user username
	 * @param timestamp timestamp
	 * @param action action
	 * @param resource resource
	 */
	public LogEntry(String user, String timestamp, String action, String resource){
		setUserName(user);
		this.timestamp = new Timestamp(timestamp);
		setAction(action);
		setResource(resource);
	}  
	/**
	 * getter for username
	 * @return the userName
	 */
	public String getUserName() {
		return userName; 
	}
	/**
	 * setter for username
	 * @param userName the userName to set
	 */
	private void setUserName(String userName) {
		this.userName = userName;
	} 
	/**
	 * getter for action
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * setter for action
	 * @param action the action to set
	 */
	private void setAction(String action) {
		this.action = action;
	}
	/**
	 * getter for resource
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * Setter for resource
	 * @param resource the resource to set
	 */
	private void setResource(String resource) {
		this.resource = resource;
	}
	/**
	 * getter for timestamp
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	/**
	 * returns the next field
	 * @return the next
	 */
	public LogEntry getNext() {
		return next;
	}
	/**
	 * sets the next field
	 * @param next the next to set
	 */
	public void setNext(LogEntry next) {
		this.next = next;
	}
	/**
	 * Auto generated hash code fxn
	 * @return integer hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31; 
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + ((resource == null) ? 0 : resource.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return Math.abs(result); 
	}
	/**
	 * Compares two entries for equality
	 * @param obj is the object in question
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogEntry other = (LogEntry) obj;
		if (action == null) {
			if (other.action != null) 
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
}
