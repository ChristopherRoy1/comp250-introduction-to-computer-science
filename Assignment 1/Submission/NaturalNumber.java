package ass1;

/*
 *   STUDENT NAME      :	Christopher Roy
 *   STUDENT ID        :	260687695
 *   
 *   If you have any issues that you wish the T.A.s to consider, then you
 *   should list them here.   If you discussed on the assignment in depth 
 *   with another student, then you should list that student's name here.   
 *   We insist that you each write your own code.   But we also expect 
 *   (and indeed encourage) that you discuss some of the technical
 *   issues and problems with each other, in case you get stuck.    

 *   
 */

import java.util.LinkedList;

public class NaturalNumber  {

	/*
	 *   
	 *  If the list has N coefficients,  then then the number is represented is a polynomial:
	 *  coefficients[N-1] base^{N-1} + ...  coefficients[1] base^{1} +  coefficients[0] 
	 *  where base has a particular value and the coefficients are in {0, 1, ...  base - 1}
	 *  
	 *  For any base and any positive integer, the representation of that positive 
	 *  integer as a sum of powers of that base is unique.  
	 *  
	 *  We require that the coefficient of the largest power is non-zero.  
	 *  For example,  '354' is a valid representation (which we call "three hundred fifty four") 
	 *  but '0354' is not.  
	 * 
	 */
	
	private int	base;       

	private LinkedList<Integer>  coefficients;

	//  Constructors

	NaturalNumber(int base){
		
		//  If no string argument is given, then it constructs an empty list of coefficients.
		
		this.base = base;
		coefficients = new LinkedList<Integer>();
	}

	/*       
	 *   The constructor builds a LinkedList of Integers where the integers need to be in {0,1,2,3,4...,base-1}.
	 *   The string only represents a base 10 number when the base is given to be 10.
	 */
	NaturalNumber(String sBase,  int base) throws Exception{
		int i;
		this.base = base;
		coefficients = new LinkedList<Integer>();
		if ((base <2) || (base > 10)){
			System.out.println("constructor error:  base must be between 2 and 10");
			throw new Exception();
		}

		/*
		 *    The large integer inputs will be read in as strings with character digits.  
		 *    These characters will need to be converted to integers.   The characters are represented 
		 *    in ASCII.   See the decimal (dec) and character (char) values in 
		 *    http://www.asciitable.com/  ).   The ASCII value of symbol '0' is 48,  and the ASCII value 
		 *    of symbol '1' is 49, etc.  So for example to get the numerical value of '2', we subtract 
		 *    the character value of '0' (48) from the character value of '2' (50).	
		 */
			
		int l = sBase.length();
		for (int indx = 0; indx < l; indx++){  
			i = sBase.charAt(indx);
			if ( (i >= 48) && (i - 48 < base))                                                    				
				coefficients.addFirst( new Integer(i-48) );
			else{
				System.out.println("constructor error:  all coefficients should be non-negative and less than base");
				throw new Exception();
			}			
		}
	}
	
	/*
	 *   Construct a NaturalNumber object for a number that has just one digit in [0, base).  
	 *   
	 *   This constructor acts as a helper.  It is not called from the Tester class.
	 */

	NaturalNumber(int i,  int base) throws Exception{
		this.base = base;
		coefficients = new LinkedList<Integer>();
		
		if ((i >= 0) && (i < base))
			coefficients.addFirst( new Integer(i) );
		else {
			System.out.println("constructor error: all coefficients should be non-negative and less than base");
			throw new Exception();
		}
	}

	/*
	 *   The plus method computes this.plus(b), that is, a+b where 'this' is a.
	 *   
	 *   If you do not know what the Java keyword 'this' is,  then see
	 *   https://docs.oracle.com/javase/tutorial/java/javaOO/thiskey.html
	 *	
	 */
	
	/* 
	 *	Getter for both the private variables- Added by Navin/Ramchalam for testGrader
	 */
	
	public int getBase()
	{
		return base;
	}
	public LinkedList<Integer>  getCoefficients()
	{
		return coefficients;
	}
	
	
	
	//To perform a+b, call a.plus(b). The parameter second refers to the second operand in a+b, that is, b
	
	public NaturalNumber plus( NaturalNumber  second) throws Exception{
				
		//  initialize the sum as an empty list of coefficients
		
		NaturalNumber sum = new NaturalNumber( this.base );

		if (this.base != second.base){
			System.out.println("ERROR: bases must be the same in an addition");
			throw new Exception();
		}

		/*   
		 * The plus method must not affect the numbers themselves. 
		 * So let's just work  with a copy (a clone) of the numbers. 
		 */

		NaturalNumber  firstClone  = this.clone();
		NaturalNumber  secondClone = second.clone();

		/*
		 *   If the two numbers have a different polynomial order    
		 *   then pad the smaller one with zero coefficients.
		 */
		
		int   diff = firstClone.coefficients.size() - second.coefficients.size();
		while (diff < 0){  // second is bigger
                                                     		
			firstClone.coefficients.addLast(0);			        
			diff++;
		}
		while (diff > 0){  //  this is bigger
			secondClone.coefficients.addLast(0);
			diff--;
		}

		//   ADD YOUR CODE HERE
		
		//  ---------  BEGIN SOLUTION (plus)  ----------
		
		Integer result;
		Integer carry = 0;
		
		//loop runs as many times as there are coefficients in the first number
		for(int i = 0 ; i < firstClone.coefficients.size(); i++) {			
			
			result = new Integer((firstClone.coefficients.get(i) + secondClone.coefficients.get(i) + carry)%base);				
			carry = (firstClone.coefficients.get(i) + secondClone.coefficients.get(i) + carry)/base;
			sum.coefficients.add(i, result);
		}
		
		//if there is a non zero carry, add it to the sum
		if(carry != 0) {
			sum.coefficients.add(firstClone.coefficients.size(), carry);
		}
		
		return sum;		
		
				
		}
		
	
		
		//  ---------  END SOLUTION (plus)  ----------
		
	
	

	/*   
	 *    Slow multiplication algorithm, mentioned in lecture 1.
	 *    You need to implement the plus algorithm in order for this to work.
	 *    
	 *    'this' is the multiplicand i.e. a*b = a+a+a+...+a (b times) where a is multiplicand and b is multiplier
	 */
	
	public NaturalNumber slowTimes( NaturalNumber  multiplier) throws Exception{

		NaturalNumber prod  = new NaturalNumber(0, this.base);
		NaturalNumber one   = new NaturalNumber(1, this.base);
		for (NaturalNumber counter = new NaturalNumber(0, this.base);  counter.compareTo(multiplier) < 0;  counter = counter.plus(one) ){
			prod = prod.plus(this);
		}
		return prod;
	}
	
	/*
	 *    The multiply method must NOT be the same as what you learned in grade school since 
	 *    that method uses a temporary 2D table with space proportional to the square of 
	 *    the number of coefficients in the operands i.e. O(N^2).   Instead, you must write a method 
	 *    that uses space that is proportional to the number of coefficients i.e. O(N).    
	 *    Your algorithm will still take time O(N^2) however.   
	 */

	/*   The multiply method computes this.multiply(b) where 'this' is a.
	 */
	
	public NaturalNumber times( NaturalNumber multiplicand) throws Exception{
		
		//  initialize product as an empty list of coefficients
		NaturalNumber multiplier = this.clone();
		NaturalNumber product	= new NaturalNumber( this.base );

		if (this.base != multiplicand.base){
			System.out.println("ERROR: bases must be the same in a multiplication");
			throw new Exception();
		}
		
		//    ADD YOUR CODE HERE
		
		// --------------  BEGIN SOLUTION (multiply)  ------------------
		
		/*
		 *           multiplicand
		 *          x  multiplier  (this)
		 *        ---------------*/
		
		NaturalNumber multiplicandClone = multiplicand.clone();
		NaturalNumber runningsum = new NaturalNumber(base);
		
		//loop runs as many times as there are coefficients in the multiplier
		for(int i = 0; i < multiplier.coefficients.size(); i++) {
			Integer current_multiplier = multiplier.coefficients.get(i);
			//store the results of single digit multiplications into a running sum
			runningsum = runningsum.plus(multiplicandClone.timesSingleDigit(i,current_multiplier, base));
						
		}
		
		product = runningsum;
		
		//remove leading zeros from the answer
		while ((product.coefficients.size() > 1) & 
				(product.coefficients.getLast().intValue() == 0)){
			product.coefficients.removeLast();
		}
		
		//  ---------------  END SOLUTION  (multiply) -------------------
		
		return product;
	}


	// -------- BEGIN SOLUTION     *helper method* for multiply  -----
	
	/*
	 *    'this' (the caller) will be the multiplicand.   
	 */
	
	public NaturalNumber timesSingleDigit(int currIndex, Integer digit, int base) throws Exception {
		
		NaturalNumber multiplicand = this.clone();
		NaturalNumber product = new NaturalNumber(base);
		Integer result;
		Integer carry = 0;
		
		
		//loop runs as many times as there are coefficients in the multiplicand
		for(int i = 0; i < multiplicand.coefficients.size(); i++) {			
			
			result = (((digit * multiplicand.coefficients.get(i)) + carry)%base);
			carry = (((digit * multiplicand.coefficients.get(i)) + carry)/base);
			product.coefficients.add(result);
		
		}
		
		if(carry.compareTo(0) != 0) {
			product.coefficients.add(carry);
		}	
		
		//current index keeps track of where the single digit is located within the multiplier
		//the while loop adds zeroes in order for running sum to add properly 
		while(currIndex > 0) {
			product.coefficients.addFirst(new Integer(0));
			currIndex--;
		}
		
		
		
		return product;
	}
	
	
	//   END SOLUTION ----------  *helper method* for multiply ---------
	
	
	/*
	 *   The minus method computes this.minus(b) where 'this' is a, and a > b.
	 *   If a < b, then it throws an exception.
	 *	
	 */
	
	public NaturalNumber  minus(NaturalNumber second) throws Exception{

		//  initialize the result (difference) as an empty list of coefficients
		
		NaturalNumber  difference = new NaturalNumber(this.base);

		if (this.base != second.base){
			System.out.println("ERROR: bases must be the same in a subtraction");
			throw new Exception();
		}
		/*
		 *    The minus method is not supposed to change the numbers. 
		 *    But the grade school algorithm sometimes requires us to "borrow"
		 *    from a higher coefficient to a lower one.   So we work
		 *    with a copy (a clone) instead.
		 */

		NaturalNumber  first = this.clone();

		//   You may assume 'this' > second. 
		 
		if (this.compareTo(second) < 0){
			System.out.println("Error: the subtraction a-b requires that a > b");
			throw new Exception();
		}

		//   ADD YOUR CODE HERE
		
		//  ---------  BEGIN SOLUTION (minus)  ----------
		
		Integer borrow = 0; //used to store value being borrowed if first coefficient is smaller than second
		Integer firstcoeff;
		Integer secondcoeff;
		
		//pads the second number with zeroes to make lengths of first and second number equal
		while(second.coefficients.size() < first.coefficients.size()){
			second.coefficients.addLast(new Integer(0));
		}
		
		
			for(int i = 0; i < first.coefficients.size(); i++) {
				firstcoeff = first.coefficients.get(i);
				secondcoeff = second.coefficients.get(i);
				
				//if statement only activates if subtraction would yield a negative number
				if(firstcoeff < secondcoeff) {
					int k = i+1;		
					//while loop scans through the higher order coefficients, looking for a non zero number to borrow from
					while(first.coefficients.get(k) == 0) {
						k++;
					}
					//the for loop borrows and adds the borrow to the adjacent coefficients until coefficient i is reached.
					for(int j = k; j>i; j--) {
						borrow = first.coefficients.get(j);
						first.coefficients.set(j, (borrow-1));
					
						//add the borrow term to the adjacent coefficient, which is equal to the base of the number
						first.coefficients.set(j-1,(first.coefficients.get(j-1) + difference.getBase()));
					}
					//The first coefficient is now greater than the second, so update value.
					firstcoeff = first.coefficients.get(i);
				}
				//subtract the coefficients
				difference.coefficients.add(i,(firstcoeff - second.coefficients.get(i)));
					
			}
		

		//  ---------  END SOLUTION (minus)  ----------

		
		/*  
		 *  In the case of say  100-98, we will end up with 002.  
		 *  Remove all the leading 0's of the result.
		 *
		 *  We are giving you this code because you could easily neglect
		 *  to do this check, which would mess up grading since correct
		 *  answers would appear incorrect.
		 */
		
		while ((difference.coefficients.size() > 1) & 
				(difference.coefficients.getLast().intValue() == 0)){
			difference.coefficients.removeLast();
		}
		return difference;	
	}

	/*   
	 *    Slow division algorithm, mentioned in lecture 1.
	 */

	
	public NaturalNumber slowDivide( NaturalNumber  divisor) throws Exception{

		NaturalNumber quotient = new NaturalNumber(0,base);
		NaturalNumber one = new NaturalNumber(1,base);
		NaturalNumber remainder = this.clone();
		while ( remainder.compareTo(divisor) >= 0 ){
			remainder = remainder.minus(divisor);
			quotient = quotient.plus(one);
		}
		return quotient;
	}


	
	/*  
	 *  The divide method divides 'this' by 'divisor' i.e. this.divide(divisor)
	 *   When this method terminates, there is a remainder but it is ignored (not returned).
	 *   
	 */
	
	public NaturalNumber divide( NaturalNumber  divisor ) throws Exception{
		
		//  initialize quotient as an empty list of coefficients
		
		NaturalNumber  quotient = new NaturalNumber(this.base);
		
		if (this.base != divisor.base){
			System.out.println("ERROR: bases must be the same in an division");
			throw new Exception();
		}
		
		if(divisor.compareTo(new NaturalNumber(0, this.base))==0){
			System.out.println("ERROR: division by zero not possible");
			throw new Exception();
		}
		
		NaturalNumber remainder = this.clone();

		//   ADD YOUR CODE HERE.
		
		//  --------------- BEGIN SOLUTION (divide) --------------------------
		
		NaturalNumber current_dividend = new NaturalNumber(this.base);
		
		//current_dividend will be the section of the remainder being considered for the intermediary divisions
		current_dividend.coefficients.add(remainder.coefficients.getLast());
		remainder.coefficients.removeLast();
		
		//remove padding zeroes from divisor
		while ((divisor.coefficients.size() > 1) & 
				(divisor.coefficients.getLast().intValue() == 0)){
			divisor.coefficients.removeLast();
		}
		
		//will keep track of how many digits are left to be considered in the current_dividend
		int digitsleft = remainder.coefficients.size();
		
		while(digitsleft >= 0) {		
		
			while(current_dividend.compareTo(divisor) < 0 && digitsleft >0){
				//compareTo assumes coefficient N is nonzero, if statement removes a zero
				if(current_dividend.coefficients.getLast() == 0) {
					current_dividend.coefficients.removeLast();
				}
				quotient.coefficients.addFirst(0);
				current_dividend.coefficients.addFirst(remainder.coefficients.getLast());
				remainder.coefficients.removeLast();				
				digitsleft--;
			}
			
			
			int i = 1;
			NaturalNumber product = divisor.clone();
			
			if(product.plus(divisor).compareTo(current_dividend) == 0) {
				product = product.plus(divisor);				
				i++;
			}
			
			//if the if statement above is triggered, the while cannot trigger
			while((product.plus(divisor)).compareTo(current_dividend) <= 0) {
				//finds out how many times the divisor can fit into the current dividend (calculates digit to add to coefficient)
				product = product.plus(divisor);				
				i++;				
			}			
			
			
			//if the current dividend is larger than the product, subtraction is allowed
			if (current_dividend.compareTo(product) > 0) {
				current_dividend = current_dividend.minus(product);
				quotient.coefficients.addFirst(i); //add the number of times the divisor fits into the current dividend to the quotient
			}			
			//if the current dividend is exactly equal to the product, clear all coefficients from the current_dividend
			else if(current_dividend.compareTo(product) == 0){
				current_dividend.coefficients.removeAll(coefficients);
				quotient.coefficients.addFirst(i); //add the number of times the divisor fits into the current dividend to the quotient
			}
			else{			
				//this will only ever happen if we run out of digits for current dividend
				//thus the divisor fits into the current dividend 0 times
				quotient.coefficients.addFirst(0);				
				break;
			}
				//if we still have digits to consider, add the next digit to the current_dividend
				if(digitsleft!=0)
					current_dividend.coefficients.addFirst(remainder.coefficients.removeLast());
				
				
				digitsleft--;
		
		
		}
		
		//remove leading zeroes from the answer
		while ((quotient.coefficients.size() > 1) & 
				(quotient.coefficients.getLast().intValue() == 0)){
			quotient.coefficients.removeLast();
		}
		
		
		// -------------  END SOLUTION  (divide)  ---------------------

		return quotient;		
	}
	
	//   Helper methods

	/*
	 * The methods you write to add, subtract, multiply, divide 
	 * should not alter the two numbers.  If a method require
	 * that one of the numbers be altered (e.g. borrowing in subtraction)
	 * then you need to clone the number and work with the cloned number 
	 * instead of the original. 
	 */
	
	@Override
	public NaturalNumber  clone(){

		//  For technical reasons that don't interest us now (and perhaps ever), this method 
		//  has to be declared public (not private).

		NaturalNumber copy = new NaturalNumber(this.base);
		for (int i=0; i < this.coefficients.size(); i++){
			copy.coefficients.addLast( new Integer( this.coefficients.get(i) ) );
		}
		return copy;
	}
	
	/*
	 *  The subtraction method (minus) computes a-b and requires that a>b.   
	 *  The a.compareTo(b) method is useful for checking this condition. 
	 *  It returns -1 if a < b,  it returns 0 if a == b,  
	 *  and it returns 1 if a > b.
	 *  
	 *  The compareTo() method assumes that the two numbers have the same base.
	 *  One could add code to check this but I didn't.
	 */
	
	private int 	compareTo(NaturalNumber second){

		//   if  this < other,  return -1  
		//   if  this > other,  return  1  
		//   otherwise they are equal and return 0
		
		//   Assume maximum degree coefficient is non-zero.   Then,  if two numbers
		//   have different maximum degree, it is easy to decide which is larger.
		
		int diff = this.coefficients.size() - second.coefficients.size();
		if (diff < 0)
			return -1;
		else if (diff > 0)
			return 1;
		else { 
			
			//   If two numbers have the same maximum degree,  then it is a bit trickier
			//   to decide which number is larger.   You need to compare the coefficients,
			//   starting from the largest and working toward the smallest until you find
			//   coefficients that are not equal.
			
			boolean done = false;
			int i = this.coefficients.size() - 1;
			while (i >=0 && !done){
				diff = this.coefficients.get(i) - second.coefficients.get(i); 
				if (diff < 0){
					return -1;
				}
				else if (diff > 0)
					return 1;
				else{
					i--;
				}
			}
			return 0;    //   if all coefficients are the same,  so numbers are equal.
		}
	}

	/*  computes  'this' * base^n  
	 */
	
	private NaturalNumber timesBaseToThePower(int n){
		for (int i=0; i< n; i++){
			this.coefficients.addFirst(new Integer(0));
		}
		return this;
	}

	/*
	The following method is invoked by System.out.print.
	It returns the string with coefficients in the reverse order 
	which is the natural format for people to reading numbers,
	i.e. people want to read  a[N-1], ... a[2] a[1] a[0]. 
	It does so simply by make a copy of the list with elements in 
	reversed order, and then printing the list using the LinkedList's
	toString() method.
	*/
	
	@Override
	public String toString(){	
		String s = new String(); 
		for (Integer coef : coefficients)     //  Java enhanced for loop
			s = coef.toString() + s ;        //   Append each successive coefficient.
		return "(" + s + ")_" + base;		
	}
	
}

