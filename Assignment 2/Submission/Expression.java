package ass2;
/*
 * Comp 250 - Assignment 2
 * Christopher Roy
 * 260687695
 * 
 * 
 */
import java.util.Stack;
import java.util.ArrayList;

public class Expression  {
	private ArrayList<String> tokenList;

	//  Constructor    
	/**
	 * The constructor takes in an expression as a string
	 * and tokenizes it (breaks it up into meaningful units)
	 * These tokens are then stored in an array list 'tokenList'.
	 */

	Expression(String expressionString) throws IllegalArgumentException{
		tokenList = new ArrayList<String>();
		StringBuilder token = new StringBuilder();
		
		
		int i = 0;
		
		mainLoop:
		while(i < expressionString.length()) {
			
			char currentChar = expressionString.charAt(i);		
			
			if(currentChar == ' ') {
				i++;
				continue;
			}
			
			//if the currentChar is an integer, we need to check adjacent chars to capture every digit in the integer
			while(isInteger(String.valueOf(currentChar))) {	
				
				token.append(currentChar);
				i++;
				
				if(i >= expressionString.length()) {
					continue mainLoop;
				}
				
				if(isInteger(String.valueOf(expressionString.charAt(i)))) {
					currentChar = expressionString.charAt(i);
				}
				else {
					tokenList.add(token.toString());
					token = new StringBuilder();
					continue mainLoop;
				}
				
			}
			
			//at this point in the code, the only possible chars are brackets or operators
			//first check if we are dealing with a bracket, in which case we only care about the current char			
			if(currentChar == '(' || currentChar == ')'){
				token.append(currentChar);
				tokenList.add(token.toString());
				token = new StringBuilder();
				i++;
				continue;
			}
			
			//At this point, only options are operators
			//first determine if they are unary or binary
			char adjacentChar;
			if(i != expressionString.length() - 1) {
				adjacentChar = expressionString.charAt(i+1);
			}
			else {
				token.append(currentChar);
				tokenList.add(token.toString());
				token = new StringBuilder();
				i++;
				continue;
			}
			
			if(currentChar == adjacentChar) {
				token.append(currentChar);
				token.append(currentChar);
				tokenList.add(token.toString());
				token = new StringBuilder();
				i += 2;
			}
			else {
				token.append(currentChar);
				tokenList.add(token.toString());
				token = new StringBuilder();
				i++;
			}
			
						
		}
	}

	/**
	 * This method evaluates the expression and returns the value of the expression
	 * Evaluation is done using 2 stack ADTs, operatorStack to store operators
	 * and valueStack to store values and intermediate results.
	 * - You must fill in code to evaluate an expression using 2 stacks
	 */
	public Integer eval(){
		Stack<String> operatorStack = new Stack<String>();
		Stack<Integer> valueStack = new Stack<Integer>();
		
		
		int total = 0;
		int i = 0;
		
		//get the size of the operator stack to be greater than 0
		operatorStack.push(tokenList.get(i));
		
		i++;
		String currToken;		
		
		while(operatorStack.size() > 0) {					
			currToken = tokenList.get(i);		
			//push tokens to proper stacks until a ) or ] is reached
			while(!(currToken.equals(")")) && !(currToken.equals("]"))) {
				
				if(i == tokenList.size()) {
					break;
				}
				
				
				if(isInteger(currToken)) 
					valueStack.push(Integer.parseInt(currToken));				
				else 
					operatorStack.push(currToken);		
				
					i++;
					currToken = tokenList.get(i);
					
			}
					
			//if we are outside of the inner while loop, push the ) or ] to the operator stack,
			operatorStack.push(currToken);
			
			//first pop the ')' or ']', and keep track of which type it was
			boolean absVal = false;
			if (operatorStack.pop().contains("]")){
				absVal = true;
			}
			
			//by definition, there needs to be an integer value here now, which will be at the top of the stack
			int secondVal = valueStack.pop();
			
			//depending on the operator type, firstVal may not be initialized
			int firstVal;
			int expressionVal;
			String operator;
			if(!(operatorStack.peek()).contains("[")) {
				operator = operatorStack.pop();
				if(operatorType(operator) == "binary") {
					firstVal = valueStack.pop();				
					expressionVal = evalBinExpression(operator, firstVal, secondVal);				
				}
				else {
					if(operator.equals("++")) {
						expressionVal = secondVal + 1;
					}
					else if(operator.equals("--")) {
						expressionVal = secondVal - 1;
					}
					else {
						expressionVal = secondVal;
					}
				}
			}
			else {
				expressionVal = secondVal;
			}
				
			
				
			//expression has been evaluated at this point
			//if it is an absolute value expression, we must take its absolute value before 
			if(absVal) 
				valueStack.push(Math.abs(expressionVal));
			else
				valueStack.push(expressionVal);
				
			//now we need to pop the closing parenthesis, which has to be the topmost token on the operatorStack
			//by definition of an expression
			if(operatorStack.size() == 0) {
				break;
			}
			operatorStack.pop();
			i++;
									
			if(i >= tokenList.size()) {
				break;
			}
			currToken = tokenList.get(i);
				
		}
		
		total = valueStack.pop();
		
		return total;   
	}

	//Helper methods
	
	private String operatorType(String element) {

		String type = "";
		if(element.equals("+") || element.equals("-") || element.equals("*") || element.equals("/"))
			type = "binary";
		else
			type = "unary";
		
		return type;
		
	}
	
	private int evalBinExpression(String operator, int val1, int val2) {
		if(operator.equals("+")) {
			return (val1 + val2);
		}
		else if (operator.equals("-")) {
			return (val1 - val2);
		}
		else if (operator.equals("*")) {
			return (val1 * val2);
		}
		else {
			return (val1 / val2);
		}
		
	}
	
	
	/**
	 * Helper method to test if a string is an integer
	 * Returns true for strings of integers like "456"
	 * and false for string of non-integers like "+"
	 * - DO NOT EDIT THIS METHOD
	 */
	
	private boolean isInteger(String element){
		try{
			Integer.valueOf(element);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}

	/**
	 * Method to help print out the expression stored as a list in tokenList.
	 * - DO NOT EDIT THIS METHOD    
	 */

	@Override
	public String toString(){	
		String s = new String(); 
		for (String t : tokenList )
			s = s + "~"+  t;
		return s;		
	}

}

