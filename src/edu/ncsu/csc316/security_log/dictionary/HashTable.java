package edu.ncsu.csc316.security_log.dictionary;


/**
 * The hash table class for the slm2
 * @author Ryan Buchanan
 *
 * I had some help after researching generics and general Hash Tables
 * from the following sources:
 * https://www.geeksforgeeks.org/implementing-our-own-hash-table-with-separate-chaining-in-java/
 * https://docs.oracle.com/javase/tutorial/java/generics/types.html
 * https://www.tutorialspoint.com/java/java_generics.htm
 * 
 * @param <E> Generic Type generic type
 */
public class HashTable<E> {
	private HashNode<E>[] table;
	private int size = 0;
	private int capacity = 500;  
	private double loadFactor;
	
	/**
	 * Constructs a new generic HashTable with
	 * some initial default capacity
	 */
	@SuppressWarnings("unchecked") 
	public HashTable(){
		table = new HashNode[capacity];
	}
	/**
	 * Returns the first or only hashnode at the given index
	 * @param index index
	 * @return the current hashnode at the given index
	 */
	public HashNode<E> lookUpIndex(int index){
		if(table[index] == null)
			return null;
		return table[index];
	}
	/**
	 * Checks the load factor of the hash table, 
	 * if its too high generate another one, but bigger...
	 * ...
	 * not necessarily better...just bigger.
	 */
	@SuppressWarnings("unchecked") 
	private void resize(){
		loadFactor = (double) size / capacity; 
		if( loadFactor >= 0.65 ){
			capacity = (capacity * 2) + 1;
			HashNode<E>[] newHashTable = new HashNode[capacity];
			//For every hash node in the table, loop
			for(HashNode<E> node : table){
				//If the node is not null, go through the bucket and rehash the entries
				//Then add them to the new list.
				for(; node != null; node = node.getNext()){
					int hash = node.getValue().hashCode();
					int index = compress(hash);				
					if( newHashTable[index] != null ){
						HashNode<E> temp = newHashTable[index];
						//While the next field of the current bucket is not null
						while(temp.getNext() != null)
							temp = temp.getNext(); //Move through the bucket
						//Once the end of the bucket is reached, 
						// set the next field to the new value
						temp.setNext(new HashNode<E>(node.getValue()));
						//size++;
					} else { 
						newHashTable[index] = new HashNode<E>(node.getValue());
						//size++;
					}
				}
			}
			//Now it is finally time to set the field.
			table = newHashTable;
		}
	}
	/**
	 * Determine if the table is empty
	 * @return true/false
	 */
	public boolean isEmpty(){
		if(size == 0)
			return true;
		return false;
	}
	/**
	 * Inserts the generic value E into the hash table
	 *
	 * @param value - the value to insert into the hash table
	 */
	public void insert(E value){
		resize();
		//Get the hash of the value
		int hash = value.hashCode();
		//Then compress it into something usable
		int index = compress(hash);
		
		if( table[index] != null ){
			HashNode<E> temp = table[index];
			//While the next field of the current bucket is not null
			while(temp.getNext() != null)
				temp = temp.getNext(); //Move through the bucket
			//Once the end of the bucket is reached, 
			// set the next field to the new value
			temp.setNext(new HashNode<E>(value));
			size++;
		} else { 
			table[index] = new HashNode<E>(value);
			size++;
		}
		
	}

	/**
	 * Finds the value E in
	 * the hash table. Returns the value E
	 * if the value was found in the hash table.
	 * If the value is not in the hash table, return null.
	 *
	 * @param value - the value to search for in the hash table
	 * @return the reference to the value in the hash table, or null if the value 
	 *              is not in the hash table
	 */
	public E lookUp(E value){
		//Get the hash of the value
		int hash = value.hashCode();
		//Then compress it into something usable
		int index = compress(hash);
		//Look for the value, if the bucket is not empty
		//Then check each of the values for equivalence and 
		//return the first match
		if( table[index] != null ){
			HashNode<E> temp = table[index];			
			if(temp.getValue().equals(value)){
				return temp.getValue();
			} else {  
				//While the next field of the current bucket is not null
				while(temp.getNext() != null){
					temp = temp.getNext(); //Move through the bucket
					if(temp.getValue().equals(value))
						return temp.getValue();				
				}
				//If we make to the end of the bucket, and still no match
				//Return null because the object was not found.
			}
		}
		return null; 
	}

	/**
	 * Returns the number of values in the hash table
	 * 
	 * @return the number of values in the hash table
	 */
	public int size(){
		return this.size ;
	}

	/**
	 * Returns the length/capacity of the hash table
	 * 
	 * @return the length/capacity of the hash table
	 */
	public int getHashTableLength(){
		return this.capacity ;
	}
	/**
	 * Generates a usable index from a given hash code.
	 * @param hashCode hash code
	 * @return usable index
	 */
	private int compress(int hashCode){
		double hash = (double) hashCode;
		double magic = 0.61803399;
		// ( f(k) * p^-1 )
		double stepOne = (hash * magic);
		// [( f(k) * p^-1 ) - floor( f(k) * p^-1 )]
		double stepTwo = (stepOne - Math.floor(stepOne));
		// floor( m * [( f(k) * p^-1 ) - floor( f(k) * p^-1 )] )
		double stepThree = Math.floor(capacity * stepTwo);
		
		return (int)stepThree;

		//return (hashCode % capacity);
	}

	/**
	 * Bucket for the table
	 * @author Ryan Buchanan
	 *
	 * @param <E>
	 */
	@SuppressWarnings("hiding")
	public class HashNode<E> {
	    private E value;	 
	    // Reference to next node
	    private HashNode<E> next;
	 
	    /**
	     *  Constructor
	     * @param value log entry
	     */
	    public HashNode(E value){
	        setValue(value);
	        setNext(null);
	    }

		/**
		 * returns the value of the node
		 * @return the value
		 */
		public E getValue() {
			return value;
		}
		/**
		 * Sets the value
		 * @param value the value to set
		 */
		private void setValue(E value) {
			this.value = value;
		}
		/**
		 * returns the next field
		 * @return the next
		 */
		public HashNode<E> getNext() {
			return next;
		}

		/**
		 * sets the next field
		 * @param next the next to set
		 */
		public void setNext(HashNode<E> next) {
			this.next = next;
		}
	}
}
