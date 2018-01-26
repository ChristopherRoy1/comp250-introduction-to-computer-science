//COMP 250 - Introduction to Computer Science - Fall 2017
//Assignment #3 - Question 1 //Christopher Roy 260687695

import java.util.*;

/*
 *  WordTree class.  Each node is associated with a prefix of some word 
 *  stored in the tree.   (Any string is a prefix of itself.)
 */

public class WordTree
{
	WordTreeNode root;

	// Empty tree has just a root node.  All the children are null.

	public WordTree() 
	{
		root = new WordTreeNode();
	}


	/*
	 * Insert word into the tree.  First, find the longest 
	 * prefix of word that is already in the tree (use getPrefixNode() below). 
	 * Then, add TreeNode(s) such that the word is inserted 
	 * according to the specification in PDF.
	 */
	public void insert(String word)
	{
		//  ADD YOUR CODE BELOW HERE
		
		//prefix will now contain the longest prefix that already exists in our tree
		WordTreeNode prefix = getPrefixNode(word);
		WordTreeNode newNode;
		
		//will hold the new chars that need to be added to the tree
		char newChar;
		
		//the depth of the prefix will tell us useful info about which chars in the word are new
		
		int extraChars = prefix.depth;
		
		if(prefix.depth < word.length()) {
			//grab the new character to be added
			newChar = word.charAt(extraChars);
		
			//create the child node
			newNode = prefix.createChild(newChar);
		
			//link the child node to its parent
			prefix.children[newChar] = newNode;
			extraChars++;
		
			prefix = newNode;
			
			//there may be more chars to add
			while(extraChars - word.length() != 0) {
				
				//grab the new character to be added
				newChar = word.charAt(extraChars);
				
				//create the child node
				newNode = prefix.createChild(newChar);
				
				//link child to parent
				prefix.children[newChar] = newNode;
				prefix = newNode;
				extraChars++;
							
			}
			
			newNode.endOfWord = true;
			
			
		}
		else {
			prefix.endOfWord = true;
		}
		
		
		
		

		//  ADD YOUR ABOVE HERE
	}

	// insert each word in a given list 

	public void loadWords(ArrayList<String> words)
	{
		for (int i = 0; i < words.size(); i++)
		{
			insert(words.get(i));
		}
		return;
	}

	/*
	 * Given an input word, return the TreeNode corresponding the longest prefix that is found. 
	 * If no prefix is found, return the root. 
	 * In the example in the PDF, running getPrefixNode("any") should return the
	 * dashed node under "n", since "an" is the longest prefix of "any" in the tree. 
	 */
	WordTreeNode getPrefixNode(String word)
	{
		//   ADD YOUR CODE BELOW HERE
		int depth = 0;
		char currentChar = word.charAt(depth);
			
		WordTreeNode prefixNode = root;
		
		while(prefixNode.getChild(currentChar) != null) {
			//The prefixNode must have a child if we are at this point in code
			//Set the prefix node to be equal to the child
			prefixNode = prefixNode.getChild(currentChar);
			
			 //we are now deeper in the tree						
						
			if(word.length()-1 != depth) {
				depth++;	
				currentChar = word.charAt(depth); //Set the currentChar to the next char in the string "word"
			}
			else { //if depth has reached the length of the word, prefixNode is necessarily the longest prefix
				break;
			}	
			
		}
		
		
		   return prefixNode; 
		
		//   ADD YOUR CODE ABOVE HERE

	}

	/*
	 * Similar to getPrefixNode() but now return the prefix as a String, rather than as a TreeNode.
	 */

	public String getPrefix(String word)
	{
		return getPrefixNode(word).toString();
	}


	/*
	 *  Return true if word is contained in the tree (i.e. it was added by insert), false otherwise.
	 *  Hint:  any string is a prefix of itself, so you can use getPrefixNode().
	 */
	public boolean contains(String word)
	{  
		//   ADD YOUR CODE BELOW HERE
			
		return (this.getPrefix(word).length() == word.length() && this.getPrefixNode(word).isEndOfWord());   
		
		//   ADD YOUR CODE ABOVE HERE
	}

	/*
	 *  Return a list of all words in the tree that have the given prefix. 
	 *  For example,  getListPrefixMatches("") should return all words in the tree.
	 */
	public ArrayList<String> getListPrefixMatches( String prefix )
	{
		//  ADD YOUR CODE BELOW 
		WordTreeNode prefixNode = getPrefixNode(prefix);
		WordTreeNode currNode;
		ArrayList<String> prefixMatches = new ArrayList<String>();
		
		currNode = prefixNode; //track the current node (used during tree traversal)
		
		
		Stack<WordTreeNode> s = new Stack<WordTreeNode>();
		s.push(prefixNode);
		
		if(prefixNode.depth == 0 && prefix.length() != 0) { //if the prefix node is the root of the tree, and the prefix itself is non zero, return no matches
			return prefixMatches;
		}
		
		//depth-first tree traversal
		//visiting the node adds any prefixes (that are valid words) to the arraylist of prefix matches
		while(!s.isEmpty()) {
			currNode = s.pop();
			//check if the current node is the end of a word
			if(currNode.isEndOfWord()) {
				prefixMatches.add(currNode.toString());
			}
			//visit all the children of the currNode, and push them to the stack
			for(int i = 0; i < currNode.children.length; i++) {
				if(currNode.getChild((char) i) != null) { //if the child is non null (i.e. exists)
					s.push(currNode.getChild((char) i));
				}				
			}
		 
		}
		
		return prefixMatches;
		
				
		//  ADD YOUR CODE ABOVE HERE
	}


	/*
	 *  Below is the inner class defining a node in a Tree (prefix) tree.  
	 *  A node contains an array of children: one for each possible character.  
	 *  The children themselves are nodes.
	 *  The i-th slot in the array contains a reference to a child node which corresponds 
	 *  to character  (char) i, namely the character with  ASCII (and UNICODE) code value i. 
	 *  Similarly the index of character c is obtained by "casting":   (int) c.
	 *  So children[97] = children[ (int) 'a']  would reference a child node corresponding to 'a' 
	 *  since (char)97 == 'a'   and  (int)'a' == 97.
	 * 
	 *  To learn more:
	 * -For all unicode charactors, see http://unicode.org/charts
	 *  in particular, the ascii characters are listed at http://unicode.org/charts/PDF/U0000.pdf
	 * -For ascii table, see http://www.asciitable.com/
	 * -For basic idea of converting (casting) from one type to another, see 
	 *  any intro to Java book (index "primitive type conversions"), or google
	 *  that phrase.   We will cover casting of reference types when get to the
	 *  Object Oriented Design part of this course.
	 */

	public class WordTreeNode
	{
		/*  
		 *   Highest allowable character index is NUMCHILDREN-1
		 *   (assuming one-byte ASCII i.e. "extended ASCII")
		 *   
		 *   NUMCHILDREN is constant (static and final)
		 *   To access it, write "TreeNode.NUMCHILDREN"
		 *   
		 *   For simplicity,  we have given each WordTree node 256 children. 
		 *   Note that if our words only consisted of characters from {a,...,z,A,...,Z} then
		 *   we would only need 52 children.   The WordTree can represent more general words
		 *   e.g.  it could also represent many special characters often used in passwords.
		 */

		public static final int NUMCHILDREN = 256;

		WordTreeNode     parent;
		WordTreeNode[]   children;
		int              depth;            // 0 for root, 1 for root's children, 2 for their children, etc..
		
		char             charInParent;    // Character associated with the tree edge from this node's parent 
		                                  // to this node.
		// See comment above for relationship between an index in 0 to 255 and a char value.
		
		boolean endOfWord;   // Set to true if prefix associated with this node is also a word.

		
		// Constructor for new, empty node with NUMCHILDREN children.  
		//  All the children are automatically initialized to null. 

		public WordTreeNode()
		{
			children = new WordTreeNode[NUMCHILDREN];
			
			//   These assignments below are unnecessary since they are just the default values.
			
			endOfWord = false;
			depth = 0; 
			charInParent = (char)0; 
		}


		/*
		 *  Add a child to current node.  The child is associated with the character specified by
		 *  the method parameter.  Make sure you set as many fields in the child node as you can.
		 *  
		 *  To implement this method, see the comment above the inner class TreeNode declaration.  
		 *  
		 */
		
		public WordTreeNode createChild(char  c) 
		{	   
			WordTreeNode child       = new WordTreeNode();

			// ADD YOUR CODE BELOW HERE		
			
			
			//Next we set the child's parent field equal to the node object calling the method
			child.parent = this;
			
			//The child needs to know what char is along the edge connecting it to its parent
			child.charInParent = c;
			
			//The child's depth is one greater than its parent's depth
			child.depth = this.depth + 1;
			
			
			
			
			
			// ADD YOUR CODE ABOVE HERE

			return child;
		}

		// Get the child node associated with a given character, i.e. that character is "on" 
		// the edge from this node to the child.  The child could be null.  

		public WordTreeNode getChild(char c) 
		{
			return children[ c ];
		}

		// Test whether the path from the root to this node is a word in the tree.  
		// Return true if it is, false if it is prefix but not a word.

		public boolean isEndOfWord() 
		{
			return endOfWord;
		}

		// Set to true for the node associated with the last character of an input word

		public void setEndOfWord(boolean endOfWord)
		{
			this.endOfWord = endOfWord;
		}

		/*  
		 *  Return the prefix (as a String) associated with this node.  This prefix
		 *  is defined by descending from the root to this node.  However, you will
		 *  find it is easier to implement by ascending from the node to the root,
		 *  composing the prefix string from its last character to its first.  
		 *
		 *  This overrides the default toString() method.
		 */

		public String toString()
		{
			// ADD YOUR CODE BELOW HERE
			
			//Start with an empty String
			String prefix = "";
			
			//Define a WordTreeNode that starts at the current node
			WordTreeNode currNode = this;
			
			
			//While loop runs until we reach the root of the tree
			while(currNode.charInParent != ((char)0)) {
				prefix = currNode.charInParent + prefix; //add the char in the edge to the prefix string
				currNode = currNode.parent; //now consider the parent of the node
			}
			
			//once we have reached the parent, we have the prefix
			return prefix;  
			
			// ADD YOUR CODE ABOVE HERE
		}
	}

}