package edu.ncsu.csc316.security_log.manager;

import edu.ncsu.csc316.security_log.dictionary.HashTable;
import edu.ncsu.csc316.security_log.dictionary.LogEntry;
import edu.ncsu.csc316.security_log.dictionary.Timestamp;
import edu.ncsu.csc316.security_log.managerio.SecurityLogManagerIO;

/**
 * Security Log manager 2 main class
 * @author Ryan Buchanan
 *
 */
public class SecurityLogManager {
	private static SecurityLogManagerIO slmio;
	private HashTable<LogEntry> table; 
	/**
	 * Constructs a new SecurityLogManager given
	 * the path to the input user activity log file.
	 * 
	 * @param filePath - the path to the user activity log file
	 */	
	public SecurityLogManager(String filePath)	{		
		slmio = new SecurityLogManagerIO();		
		table = new HashTable<LogEntry>();
		slmio.readFile(table, filePath);
	} 
	/**
	 * Produces an operational profile of user activity
	 * performed between the given start and end dates (inclusive)
	 * 
	 *   1/18/2018 12:58:14PM - delete demographics information
   	 *   1/18/2018 1:22:21PM - view prescription information
	 * 
	 * @param start - the start date in the format "MM/DD/YYYY HH:MM:SSXM"
	 * @param end - the end date in the format "MM/DD/YYYY HH:MM:SSXM"
	 * @return a string representing the operational profile
	 */
	public String generateOperationalProfile(String start, String end){
		StringBuilder oppReport = new StringBuilder();
		Timestamp startTime = new Timestamp(start);
		Timestamp endTime = new Timestamp(end);
		int count = 0;
		int tcount = 0;
		FoundOpp[] found = new FoundOpp[table.size()];
		oppReport.append("OperationalProfile[\n");
		for(int i = 0; i < table.getHashTableLength(); i++){
			HashTable<LogEntry>.HashNode<LogEntry> temp = table.lookUpIndex(i);
			if(temp == null)
				continue;			
			//Check if the found date is >= start and <= end.
			if(temp.getValue().getTimestamp().getStamp().compareTo(startTime.getStamp()) > 0 
				&& temp.getValue().getTimestamp().getStamp().compareTo(endTime.getStamp()) < 0){	

				LogEntry forPrinting = temp.getValue();
				String newOperationString = forPrinting.getAction() + " " + forPrinting.getResource();
				boolean exists = false;
				//Check if the found operation exists, if it does increment the frequency.
				//Set exists to true.
				for(int j = 0; j < count; j++){
					if(found[j].compare(newOperationString) == 0){
						found[j].incFrequency();
						exists = true;
						tcount++;
					}
				}
				//If exists is true, do not add the operation to the list.
				if(!exists){
					found[count++] = new FoundOpp(newOperationString);
					//count++;
					tcount++;
				}				
			}
			//A copy and paste of the above conditional that checks all of the 
			// values that may or may not be in the same bucket.
			while(temp.getNext() != null){
				temp = temp.getNext(); //Move through the bucket
				if(temp.getValue().getTimestamp().getStamp().compareTo(startTime.getStamp()) > 0 
						&& temp.getValue().getTimestamp().getStamp().compareTo(endTime.getStamp()) < 0){
					
					LogEntry forPrinting = temp.getValue();
					String newOperationString = forPrinting.getAction() + " " + forPrinting.getResource();
					boolean exists = false;
					//Check if the found operation exists, if it does increment the frequency.
					//Set exists to true.
					for(int j = 0; j < count; j++){
						if(found[j].compare(newOperationString) == 0){
							found[j].incFrequency();
							exists = true;
							tcount++;
						}
					}
					//If exists is true, do not add the operation to the list.
					if(!exists){
						found[count++] = new FoundOpp(newOperationString);
						//count++;
						tcount++;
					}							
				}				
			}
		}
		if(count > 0){	
			//Sort the entries
			sortFound(found, 0, (count - 1));
			for(int i = 0; i < count; i++){
				double percentage = 0;
				int freq = found[i].getFrequency();
				percentage = (double) (((double)freq / (double)tcount) * 100);
				oppReport.append("   " + found[i].getActRes() + ": ");
				oppReport.append("frequency: " + freq + ", " + "percentage: ");
				oppReport.append(String.format( "%2.1f", percentage ) + "%\n");
			}
		} else{
			oppReport.append("   No activity was recorded.\n");	
		}
		oppReport.append("]\n");
 
		return oppReport.toString();
	}
	/**
	 * Produces a list of log entries for a given 
	 * user. The output list is sorted chronologically.
	 * 
	 * @param username - the user for which to generate a report
	 * @return a string representing the user report
	 */
	public String getUserReport(String username){
		StringBuilder userReport = new StringBuilder();
		int count = 0;
		userReport.append("Activity Report for " + username + "[\n");
		LogEntry[] found = new LogEntry[table.size()];
		for(int i = 0; i < table.getHashTableLength(); i++){
			HashTable<LogEntry>.HashNode<LogEntry> temp = table.lookUpIndex(i);
			if(temp == null)
				continue;
			//If the user name matches
			if(temp.getValue().getUserName().equals(username)){
				LogEntry forPrinting = temp.getValue();
				found[count++] = forPrinting;
				//count++;
			}
			//A copy and paste of the above conditional that checks all of the 
			// values that may or may not be in the same bucket.
			while(temp.getNext() != null){
				temp = temp.getNext(); //Move through the bucket
				if(temp.getValue().getUserName().equals(username)){
					LogEntry forPrinting = temp.getValue();
					found[count++] = forPrinting;
					//count++; 
				}				
			}
		}
		if(count > 0){
			sort(found, 0, (count - 1));
			for(int i = 0; i < count; i++){
				LogEntry forPrinting = found[i];
				userReport.append("   " + forPrinting.getTimestamp().toString() + " - ");
				userReport.append(forPrinting.getAction() + " ");
				userReport.append(forPrinting.getResource() + "\n");
			}
		} else{
			userReport.append("   No activity was recorded.\n");
		}
		userReport.append("]");
		return userReport.toString();
	}
	/**
	 * Merge part of the merge sort algorithm
	 * @param entries the log entry array
	 * @param low lower index
	 * @param middle middle index
	 * @param high higher index
	 */
    private void merge(LogEntry entries[], int low, int middle, int high) {
        // Find sizes of two sub arrays to be merged
        int size1 = (middle - low) + 1;
        int size2 = (high - middle); 
        /* Create temporary arrays */
        LogEntry lowEntries[] = new LogEntry[size1];
        LogEntry highEntries[] = new LogEntry[size2];
        /*Copy data to temporary arrays*/
        for (int i = 0; i < size1; ++i)
            lowEntries[i] = entries[low + i];
        for (int j = 0; j < size2; ++j)
            highEntries[j] = entries[middle + 1 + j];
        // Initial indexes of first and second sub arrays
        int loop1 = 0;
        int loop2 = 0;
        // Initial index of merged sub array array
        int index = low;
        //Sort the two sub arrays.
        while (loop1 < size1 && loop2 < size2){
            if (lowEntries[loop1].getTimestamp().getStamp().compareTo(highEntries[loop2].getTimestamp().getStamp()) <= 0){
                entries[index++] = lowEntries[loop1++];
            } else{
                entries[index++] = highEntries[loop2++];
            }
        }
        /* Copy remaining elements of L[] if any */
        while (loop1 < size1){
            entries[index++] = lowEntries[loop1++];
        } 
        /* Copy remaining elements of R[] if any */
        while (loop2 < size2){
            entries[index++] = highEntries[loop2++];
        }
    }
    /**
     * Merge sort Algorithm, takes the log entry array and sorts the
     * 	contents based on their timestamps.
     * 
     * I got a lot of help with this algorithm from :
     * 		https://www.geeksforgeeks.org/merge-sort/
     * 
     * @param entries entries
     * @param low low index
     * @param high high index
     */
    private void sort(LogEntry entries[], int low, int high){
        if (low < high){
            // Find the middle
            int middle = (low + high) / 2;
            // Sort first and second halves
            sort(entries, low, middle);
            sort(entries , (middle + 1), high);
            // Merge the sorted halves
            merge(entries, low, middle, high);
        }
    }
	/**
	 * Merge part of the merge sort algorithm
	 * @param foundOpps the log entry array
	 * @param low lower index
	 * @param middle middle index
	 * @param high higher index
	 */
    private void mergeFound(FoundOpp foundOpps[], int low, int middle, int high) {
        // Find sizes of two sub arrays to be merged
        int size1 = (middle - low) + 1;
        int size2 = (high - middle); 
        /* Create temporary arrays */
        FoundOpp lowOpps[] = new FoundOpp[size1];
        FoundOpp highOpps[] = new FoundOpp[size2];
        /*Copy data to temporary arrays*/
        for (int i = 0; i < size1; ++i)
            lowOpps[i] = foundOpps[low + i];
        for (int j = 0; j < size2; ++j)
            highOpps[j] = foundOpps[middle + 1 + j];
        // Initial indexes of first and second sub arrays
        int loop1 = 0;
        int loop2 = 0;
        // Initial index of merged sub array array
        int index = low;
        //Sort the two sub arrays.
        while (loop1 < size1 && loop2 < size2){
        	//Start by comparing frequencies
            if (lowOpps[loop1].getFrequency() > highOpps[loop2].getFrequency()){
                foundOpps[index++] = lowOpps[loop1++];
                //loop1++;
            } else if(lowOpps[loop1].getFrequency() == highOpps[loop2].getFrequency()){
            	//If the frequencies are the same, compare alphabetically
            	if(lowOpps[loop1].compare(highOpps[loop2].getActRes()) <= 0){
            		 foundOpps[index++] = lowOpps[loop1++];
                     //loop1++;
            	} else {
               	 	foundOpps[index++] = highOpps[loop2++];
               	 	//loop2++;
            	} 
            } else{
            	 foundOpps[index++] = highOpps[loop2++];
                 //loop2++;
            }
            //index++;
        }
        /* Copy remaining elements of L[] if any */
        while (loop1 < size1){
            foundOpps[index++] = lowOpps[loop1++];
            //loop1++;
            //index++;
        } 
        /* Copy remaining elements of R[] if any */
        while (loop2 < size2){
            foundOpps[index++] = highOpps[loop2++];
            //loop2++;
            //index++;
        }
    }
    /**
     * Merge sort Algorithm, takes the log entry array and sorts the
     * 	contents based on their timestamps.
     * 
     * I got a lot of help with this algorithm from :
     * 		https://www.geeksforgeeks.org/merge-sort/
     * 
     * @param foundOpps entries
     * @param low low index
     * @param high high index
     */
    private void sortFound(FoundOpp foundOpps[], int low, int high){
        if (low < high){
            // Find the middle
            int middle = (low + high) / 2;
            // Sort first and second halves
            sortFound(foundOpps, low, middle);
            sortFound(foundOpps , (middle + 1), high);
            // Merge the sorted halves
            mergeFound(foundOpps, low, middle, high);
        }
    }
    /**
     * found Opp is used to organize the operational profile printout
     * @author Ryan Buchanan
     *
     */
    public class FoundOpp {
    	private String actRes;
    	private int frequency;
    	/**
    	 * constructor
    	 * @param actRes action and resource
    	 */
    	public FoundOpp(String actRes){
    		setActRes(actRes);
    		setFrequency(1);
    	}
    	/**
    	 * Increments the frequency field
    	 */
    	public void incFrequency(){
    		this.frequency++;
    	}
    	/**
    	 * Returns lexicographical comparison between the current and given
    	 * actRes fields. 
    	 * @param other another objects actRes field
    	 * @return -1, 0, or 1
    	 */
    	public int compare(String other){
    		return actRes.compareTo(other);
    	}
		/**
		 * returns the action + resource
		 * @return the actRes
		 */
		public String getActRes() {
			return actRes;
		}
		/**
		 * sets action + resource
		 * @param actRes the actRes to set
		 */
		private void setActRes(String actRes) {
			this.actRes = actRes;
		}
		/**
		 * returns frequency count
		 * @return the frequency
		 */
		public int getFrequency() {
			return frequency;
		}
		/**
		 * sets frequency to 1, not really used any other way
		 * @param frequency the frequency to set
		 */
		private void setFrequency(int frequency) {
			this.frequency = frequency;
		}
    	
    }
}
