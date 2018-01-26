package a4;


public class HashLinkedList<K,V>{
	/*
	 * Fields
	 */
	private HashNode<K,V> head;

	private Integer size;

	/*
	 * Constructor
	 */

	HashLinkedList(){
		head = null;
		size = 0;
	}


	/*
	 *Add (Hash)node at the front of the linked list
	 */

	public void add(K key, V value){
		// ADD CODE BELOW HERE
		HashNode<K, V> newNode = new HashNode<K, V>(key, value);
		if(head == null) {
			head = newNode;			
		}
		else {
			newNode.next = head;
			head = newNode;
		}
		size++;
		// ADD CODE ABOVE HERE
	}

	/*
	 * Get Hash(node) by key
	 * returns the node with key
	 */

	public HashNode<K,V> getListNode(K key){
		// ADD CODE BELOW HERE
		HashNode<K, V> currNode;
		currNode = head; //set the currNode to the first node;
		for(int i = 0; i < size; i++) {
			if(currNode != null && currNode.getKey().equals(key)) {
				return currNode;
			}
			else {
				currNode = currNode.next;
			}
		}
		//at this point, either size  == 0 or the list doesn't contain the key
		return null;
		// ADD CODE ABOVE HERE
	}


	/*
	 * Remove the head node of the list
	 * Note: Used by remove method and next method of hash table Iterator
	 */

	public HashNode<K,V> removeFirst(){
		// ADD CODE BELOW HERE
		if(size == 0) {
			return null;
		}
		
		HashNode<K, V> temp = head;
		head = head.next; //point the head to the new head
		temp.next = null; //temp is no longer part of the list
		size--;
		return temp;		
		// ADD CODE ABOVE HERE		
	}

	/*
	 * Remove Node by key from linked list 
	 */

	public HashNode<K,V> remove(K key){
		// ADD CODE BELOW HERE
		
		
		if(size != 0 && !(this.getListNode(key).equals(head))) {
			HashNode<K, V> currNode = this.getListNode(key);
			HashNode<K, V> prevNode = getPrevNode(currNode);
			HashNode<K, V> nextNode = this.getListNode(key).getNext();
			prevNode.next = nextNode;
			size--;
			return currNode;
			
		}
		else if(size == 0) {
			return null;
		}
		else if(this.getListNode(key).equals(head)) {
			HashNode<K, V> currNode = this.getListNode(key);
			head = currNode.next;
			size--;
			return currNode;
		}
		
		
		// ADD CODE ABOVE HERE
		return null; // removing failed
	}



	/*
	 * Delete the whole linked list
	 */
	public void clear(){
		head = null;
		size = 0;
	}
	/*
	 * Check if the list is empty
	 */

	boolean isEmpty(){
		return size == 0? true:false;
	}

	int size(){
		return this.size;
	}

	//ADD YOUR HELPER  METHODS BELOW THIS
	private HashNode<K,V> getPrevNode(HashNode<K, V> currNode){
		if(size == 0)
			return null;
		
		//holds the current node in the list
		HashNode<K, V> indexNode = head;	
		
		for(int j = 0; j < size;j++) {
			if(indexNode.getNext().equals(currNode)) {
				return indexNode; //if the next node is the node passed as the argument, we want to return it
			}
			indexNode = indexNode.next; //move to the next node
		}
		
		return null; //if the currNode isn't found, then it doesn't have a previous node in the list	
		
	}

	public boolean containsPair(K key, V value) {
		HashNode<K,V> currNode = head;
		
		for(int i = 0; i< size; i++) {
			if(currNode.getKey().equals(key) && currNode.getValue().equals(value))
				return true;
			else
				currNode = currNode.next;
		}		
		return false;
	}
	
	public HashNode<K,V> getNode(int index){
		HashNode<K, V> currNode = head;
		for(int i = 0; i < index; i++) {
			currNode = currNode.next;
		}
		return currNode;
	}
	
	//ADD YOUR HELPER METHODS ABOVE THIS


}
