/*
 a new class Operator that is an extension of the Term class. An operator has a member variable called operator that is a character. An
 operator is able to get its character value, be casted to a string, and tell if its an operator or not. It is also able determine its 
 order of operation value
 */
public class Operator extends Term {

	private char operator;
	
	public Operator(char operator)
	{
		this.operator = operator;
	}
	
	public char getOperator()
	{
		return operator;
	}
	
	/*
	a new method that helps with order of operation
	this method takes a character and returns an int
	depending on its operator character value
	 */
	public int orderOfOp()
	 { 
	     switch (operator) //switch statement depending on the given character
	     { 
	     	case '+':
	     	case '-': 
	            return 1; //if the given char is '+' or '-', return 1
	       
	        case '*': 
	        case '/': 
	            return 2; //if the given char is '*' or '/', return 2
	     } 
	     return -1; //otherwise, return -1 
	 } 
	 
	 public String toString()
	 {
		 return "" + operator;
	 }
	 
	 
	@Override
	public boolean isOperator() {
		return true;
	}

}
