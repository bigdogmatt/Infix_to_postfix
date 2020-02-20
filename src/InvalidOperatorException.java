/*
 a new exception that keeps track of the invalid operator and is then able to return the operator with the getOperator method
 */
public class InvalidOperatorException extends Exception {

	private char operator;
	public InvalidOperatorException(char charAt) {
		operator = charAt;
	}
	
	public char getOperator()
	{
		return operator;
	}

}
