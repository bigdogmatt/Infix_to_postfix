/*
 a new class Operand that is an extension of the Term class. An operand has a member variable called operand that is a double. An
 operand is able to get its double value, be casted to a string, and tell if its an operator or not.
 */
public class Operand extends Term 
{

	private double operand;
	
	public Operand(double operand)
	{
		this.operand = operand;
	}
	
	public double getOperand()
	{
		return operand;
	}
	
	 public String toString()
	 {
		 return "" + operand;
	 }
	
	@Override
	public boolean isOperator() 
	{		
		return false;
	}
	
	

}
