package a4;

import java.util.ArrayList;
import java.util.Iterator;


class MyHashTable<K,V> {
	/*
	 *   Number of entries in the HashTable. 
	 */
	private int entryCount = 0;

	/*
	 * Number of buckets. The constructor sets this variable to its initial value,
	 * which eventually can get changed by invoking the rehash() method.
	 */
	private int numBuckets;

	/**
	 * Threshold load factor for rehashing.
	 */
	private final double MAX_LOAD_FACTOR=0.75;

	/**
	 *  Buckets to store lists of key-value pairs.
	 *  Traditionally an array is used for the buckets and
	 *  a linked list is used for the entries within each bucket.   
	 *  We use an Arraylist rather than an array, since the former is simpler to use in Java.   
	 */

	ArrayList< HashLinkedList<K,V> >  buckets;

	/* 
	 * Constructor.
	 * 
	 * numBuckets is the initial number of buckets used by this hash table
	 */

	MyHashTable(int numBuckets) {

		//  ADD YOUR CODE BELOW HERE
		
		//initialize variables for the HashTable
		this.numBuckets = 7;
		this.buckets = new ArrayList<HashLinkedList<K, V>>();
				
		//put a HashLinkedList in each bucket
		for(int i =0; i < numBuckets; i++) {
			HashLinkedList<K, V> newBucket = new HashLinkedList<K, V>();
			buckets.add(i, newBucket);
		}
		
		//  ADD YOUR CODE ABOVE HERE

	}

	/**
	 * Given a key, return the bucket position for the key. 
	 */
	private int hashFunction(K key) {

		return  Math.abs( key.hashCode() ) % numBuckets ;
	}

	/**
	 * Checking if the hash table is empty.  
	 */

	public boolean isEmpty()
	{
		if (entryCount == 0)
			return true;
		else
			return(false);
	}

	/**
	 *   return the number of entries in the hash table.
	 */

	public int size()
	{
		return(entryCount);
	}

	/**
	 * Adds a key-value pair to the hash table. If the load factor goes above the 
	 * MAX_LOAD_FACTOR, then call the rehash() method after inserting. 
	 * 
	 *  If there was a previous value for the given key in this hashtable,
	 *  then overwrite it with new value and return the old value.
	 *  Otherwise return null.   
	 */

	public  V  put(K key, V value) {

		//  ADD YOUR CODE BELOW HERE
		
		//Find out where the key value pair has to go
		int destinationBucketIndex = hashFunction(key);		
				
		//grab the linked list in the bucket
		HashLinkedList<K,V> destinationBucket = buckets.get(destinationBucketIndex);
		
		//obtain the old value for this key in the bucket, if it is there
		if(destinationBucket.containsPair(key, value)) {
			V oldval = destinationBucket.getListNode(key).getValue();
		
			//we need to overwrite the old value and return it
			destinationBucket.remove(key);
			destinationBucket.add(key, value);
			//entryCount++;
			//if(((double)entryCount)/((double)numBuckets) > MAX_LOAD_FACTOR) {
				//rehash();
			//}			
			
			return oldval;
		}
		if(destinationBucket.getListNode(key) != null) {
			V oldVal = destinationBucket.getListNode(key).getValue();
			destinationBucket.remove(key);
			destinationBucket.add(key, value);
			return oldVal;
		}
								
		//if the if statement above is not triggered, the key doesn't exist in this bucket
		destinationBucket.add(key, value);		
		entryCount++;
		
		if(((double)entryCount)/((double)numBuckets) > MAX_LOAD_FACTOR) {
			rehash();
		}	
		
		
		//  ADD YOUR CODE ABOVE HERE
		return null;
	}

	/**
	 * Retrieves a value associated with some given key in the hash table.
     Returns null if the key could not be found in the hash table)
	 */
	public V get(K key) {

		//  ADD YOUR CODE BELOW HERE
		int selectedBucket = hashFunction(key);
		if(this.containsKey(key)) {
			return buckets.get(selectedBucket).getListNode(key).getValue();			
		}

		//  ADD YOUR CODE ABOVE HERE

		return null;
	}

	/**
	 * Removes a key-value pair from the hash table.
	 * Return value associated with the provided key.   If the key is not found, return null.
	 */
	public V remove(K key) {

		//  ADD YOUR CODE BELOW HERE
		int selectedBucket = hashFunction(key);
		
		if(this.containsKey(key)) {
			entryCount--;
			return buckets.get(selectedBucket).remove(key).getValue();
		}

		//  ADD  YOUR CODE ABOVE HERE

		return(null);
	}

	/*
	 *  This method is used for testing rehash().  Normally one would not provide such a method. 
	 */

	public int getNumBuckets(){
		return numBuckets;
	}

	/*
	 * Returns an iterator for the hash table. 
	 */

	public MyHashTable<K, V>.HashIterator  iterator(){
		return new HashIterator();
	}

	/*
	 * Removes all the entries from the hash table, 
	 * but keeps the number of buckets intact.
	 */
	public void clear()
	{
		for (int ct = 0; ct < buckets.size(); ct++){
			buckets.get(ct).clear();
		}
		entryCount=0;		
	}

	/**
	 *   Create a new hash table that has twice the number of buckets.
	 */


	public void rehash()
	{
		//   ADD YOUR CODE BELOW HERE
					
			HashIterator iterator = new HashIterator();			
			ArrayList<HashLinkedList<K,V>> newBuckets = new ArrayList<HashLinkedList<K, V>>();
			numBuckets *= 2; //update number of buckets
						
			//put a HashLinkedList in each bucket
			for(int i =0; i < numBuckets; i++) {
				HashLinkedList<K, V> newBucket = new HashLinkedList<K, V>();
				newBuckets.add(i, newBucket);
			}
			
			
			//put the old key value pairs back in table
			while(iterator.hasNext()) {
				HashNode<K,V> oldNode = iterator.next();
				int destinationBucketIndex = hashFunction(oldNode.getKey());
				newBuckets.get(destinationBucketIndex).add(oldNode.getKey(),oldNode.getValue());
								
			}
			this.buckets.clear();
			this.buckets = newBuckets;
			
			
		//   ADD YOUR CODE ABOVE HERE

	}


	/*
	 * Checks if the hash table contains the given key.
	 * Return true if the hash table has the specified key, and false otherwise.
	 */

	public boolean containsKey(K key)
	{
		int hashValue = hashFunction(key);
		if(buckets.get(hashValue).getListNode(key) == null){
			return false;
		}
		return true;
	}

	/*
	 * return an ArrayList of the keys in the hashtable
	 */

	public ArrayList<K>  keys()
	{

		ArrayList<K>  listKeys = new ArrayList<K>(entryCount);

		//   ADD YOUR CODE BELOW HERE
		for(int i = 0; i < this.entryCount; i++) {
			HashLinkedList<K,V> currList = buckets.get(i);
			for(int j = 0; j<currList.size(); j++) {
				listKeys.add(currList.getNode(j).getKey());
			}
			
		}
		
		return listKeys;
		//   ADD YOUR CODE ABOVE HERE
		
	}

	/*
	 * return an ArrayList of the values in the hashtable
	 */
	public ArrayList <V> values()
	{
		ArrayList<V>  listValues = new ArrayList<V>();

		//   ADD YOUR CODE BELOW HERE
		for(int i = 0; i < this.entryCount; i++) {
			HashLinkedList<K,V> currList = buckets.get(i);
			for(int j = 0; j<currList.size(); j++) {
				listValues.add(currList.getNode(j).getValue());
			}
			
		}
		
		return listValues;	
		//   ADD YOUR CODE ABOVE HERE



		
	}

	@Override
	public String toString() {
		/*
		 * Implemented method. You do not need to modify.
		 */
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buckets.size(); i++) {
			sb.append("Bucket ");
			sb.append(i);
			sb.append(" has ");
			sb.append(buckets.get(i).size());
			sb.append(" entries.\n");
		}
		sb.append("There are ");
		sb.append(entryCount);
		sb.append(" entries in the hash table altogether.");
		return sb.toString();
	}

	/*
	 *    Inner class:   Iterator for the Hash Table.
	 */
	public class HashIterator implements  Iterator<HashNode<K,V> > {
		HashLinkedList<K,V>  allEntries;

		/**
		 * Constructor:   make a linkedlist (HashLinkedList) 'allEntries' of all the entries in the hash table
		 */
		public  HashIterator()
		{

			//  ADD YOUR CODE BELOW HERE
			allEntries = new HashLinkedList<K, V>();
			
			for(HashLinkedList<K,V> bucket : buckets) {
				for(int i = 0; i < bucket.size(); i++){
					allEntries.add(bucket.getNode(i).getKey(), bucket.getNode(i).getValue());
				}
			}

			//  ADD YOUR CODE ABOVE HERE

		}

		//  Override
		@Override
		public boolean hasNext()
		{
			return !allEntries.isEmpty();
		}

		//  Override
		@Override
		public HashNode<K,V> next()
		{
			return allEntries.removeFirst();
		}

		@Override
		public void remove() {
			// not implemented,  but must be declared because it is in the Iterator interface

		}		
	}

}
