import java.util.Scanner; //we import scanner so we can get the users input for the equation they want to transform


public class InfixToPostfix {

	/*
	 Method called input that takes a String (the users input) and returns a queue that holds the user input as operands 
	 and operators with not extra spaces. We do this by using a boolean variable called isOperand to help keep track of
	 when we are looking for an operand (true) or an operator (false). This also helps us detect errors. 
	 */
	static Queue input(String eqn) throws InvalidOperatorException, InvalidOperandException, MissingOperandException
	{
		Queue queue = new Queue(); //we first create a new queue named queue to hold the users equation
		boolean isOperand = true; //set our boolean to true because we start out by looking for an operand

		for(int i = 0; i < eqn.length();) //create a for loop to cycle through each character in the input string
		{
			if(isOperand) //initial if statement asking if we are looking for an operand 
			{
				/*
				 if the character at the current position is a space then we move to the next position and we do not 
				 change isOperand because we are still looking for an operand
				 */
				if(eqn.charAt(i) == ' ') 
					i++;

				/*
				 A digit, a plus sign, a minus sign, or a '.' are all valid character for an operand.
				 if the current character is one of these then we create a new string to hold all the 
				 characters in the operand.
				 */
				else if(Character.isDigit(eqn.charAt(i)) || eqn.charAt(i) == '+' || eqn.charAt(i) == '-' || eqn.charAt(i) == '.')
				{
					String operand = "";

					/*
					 if the character is a '+' or a '-' then we add that to the string and increase i to move to the next
					 character in the string but we are still looking for more of this operand so we do not change isOperand
					 */
					if(eqn.charAt(i) == '+' || eqn.charAt(i) == '-')
					{
						operand += eqn.charAt(i);
						i++;
					}

					/*
					 when we get to this while loop we should be at a point in the string where the operand starts after
					 we get the optional '+' or '-' sign. This loop runs while i is still in the length of the string so 
					 we don't go off the end of the string and the current character must be a digit or a '.'
					 Each time this loop runs, the digit or '.' is added to the operand string and i is increased by 1
					 to move to the next character in the given string
					 */
					while(i < eqn.length() && (Character.isDigit(eqn.charAt(i)) || eqn.charAt(i) == '.'))
					{
						operand += eqn.charAt(i);
						i++;						
					}

					/*
					 After we have all the characters of the operand in the operand string we try to use parseDouble on
					 the string to turn that string into the double that we want. We then add this double to the queue
					 as a new Operand and since we just find an operand we are now looking for an operator so we change
					 isOperand to false
					 */
					try
					{
						double num = Double.parseDouble(operand);
						queue.enqueue(new Operand(num));
						isOperand = false;
					}

					/*
					 if we parseDouble doesn't work then the operand we 
					 have is not valid and we throw an InvalidOperandException
					 */
					catch(NumberFormatException e)
					{
						throw new InvalidOperandException();
					}

				}
				/*
				 that final character that we can possibly get when we are looking for an operand 
				 (besides a digit, '+', '-', and '.') is a left parenthesis. If the current character
				 in the string is a '(' then we add the character to the queue as a new operator
				 and we increase i by one to move to the next character in the string. We do not
				 change isOperand because we are still looking for an operand after the '(' 
				 */
				else if(eqn.charAt(i) == '(')
				{
					queue.enqueue(new Operator(eqn.charAt(i)));
					i++;
				}

				/*
				 if we are looking for an operand and we find anything other than a digit, a '+', a '-',
				 a '.', a '(', or a space, then we have an invalid operand and we throw and new InvalidOperandException
				 */
				else
					throw new InvalidOperandException();				
			}

			else //this means isOperand is false, so we are looking for an operator
			{
				/*
				 Just like when looking for an operand, if we encounter a space when we are looking for an operator
				 then we add one to i and move to the next character in the string but we don't change isOperand
				 because we are still looking for an operator
				 */
				if(eqn.charAt(i) == ' ')
					i++;

				/*
				 Valid operators can be a plus sign, a minus sign, a multiplication sign, and a division sign. 
				 If we the current character in the string is one of the valid operators then we add this character
				 to the queue as a new operator, we add one to i to move to the next character in the string, and
				 we change isOperand to true because we just found an operator and we are now looking for an operand
				 */
				else if(eqn.charAt(i) == '+' || eqn.charAt(i) == '-' || eqn.charAt(i) == '*' || eqn.charAt(i) == '/')
				{
					queue.enqueue(new Operator(eqn.charAt(i)));
					i++;
					isOperand = true;
				}

				/*
				 While looking for an operator, we could possible come across a right parenthesis. If this happens and the 
				 current character in the string is a ')' then we add it to the queue as a new operator and we add one to 
				 i to move to the next character in the string. We do not change isOperand because we are still looking for
				 an operator
				 */
				else if(eqn.charAt(i) == ')')
				{
					queue.enqueue(new Operator(eqn.charAt(i)));
					i++;
				}

				/*
				 if we are looking for an operator and we find anything other than a '+', a '-', a '*', a '/', a ')',
				 or a space, then we have an invalid operator and we throw and new InvalidOperatorException
				 */
				else
					throw new InvalidOperatorException(eqn.charAt(i));					
			}
		}

		/*
		 if we have cycled through the whole string and isOperand is true then we are missing an operand
		 because an equation always ends with an operand (or ')') so we throw a MissingOperandException
		 */
		if(isOperand)
			throw new MissingOperandException();

		return queue; //We return the queue that contain the user input equation
	}





	static String standard(Queue queue) throws InvalidOperatorException, InvalidOperandException
	{
		return queue.toString();
	}


	//give a queue and make a new queue for the answer and create a stack to do the converting


	/*
	 Method called infixToPostfix that takes the queue created from the input method and uses a stack and another queue
	 to convert the infix queue into postfix
	 */
	static Queue infixToPostfix(Queue queue) throws UnbalancedRightParenthesisException, UnbalancedLeftParenthesisException 
	{

		Queue result = new Queue(); //declare a new queue to hold the terms of the postfix expression

		Stack stack = new Stack(); //create a new stack to hold the operators and help with the conversion


		while(!queue.isEmpty()) //while loop that runs while the infix queue is not empty
		{ 
			Term t = queue.dequeue();
			
			/*
			 If the first term in the queue is an operand then we just add it to the postfix queue
			 */
			if(!t.isOperator()) 
				result.enqueue(t); 

			/*
			 If the first term in the queue is not an operand then its an operator. We cast this term to an operator and 
			 use the stack depending on the operator to help organize the postfix conversion
			 */
			else {
				Operator op = (Operator) t;
				
				/*
				 if the operator is a left parenthesis then we push it on to the stack
				 */
				if(op.getOperator() == '(')
					stack.push((Operator)t);

				/*
				 if the operator is a right parenthesis then we have to pop off what it on the stack and add it to the queue until
				 we reach a left parenthesis or until the stack is empty. After doing this, if the stack is empty then we throw a
				 new UnbalancedRightParenthesisException because the stack should have a left parenthesis left over. If the stack is
				 not empty then we pop what is on the stack, which should be a left parenthesis
				 */
				else if(op.getOperator() == ')') 
				{ 
					while(!stack.isEmpty() && ((Operator) stack.peek()).getOperator() != '(') 
						result.enqueue(stack.pop()); 

					if(stack.isEmpty())
						throw new UnbalancedRightParenthesisException();

					else
						stack.pop(); 
				} 
				
				/*
				 if the operator isn't a parenthesis pop what is on the stack and add it to the queue while the stack is not 
				 empty and while the order of operation of the current operator is less than the order of operation of
				 the operator on the top of the stack. We then push the current operator onto the stack
				 */
				else
				{ 
					while(!stack.isEmpty() && op.orderOfOp() <= ((Operator) stack.peek()).orderOfOp()) 
						result.enqueue(stack.pop());

					stack.push(op); 
				} 
			}


		} 

		/*
		 while the stack still has stuff in it, if the operator is a left parenthesis then we are missing a right one so
		 we throw a new UnbalancedLeftParenthesisException, then we add the operator to the postfix queue
		 */
		while(!stack.isEmpty()) 
		{
			Operator op = (Operator)stack.pop();

			if(op.getOperator() == '(')
				throw new UnbalancedLeftParenthesisException();
			result.enqueue(op); 
		}

		return result;

	} 

	
	/*
	 A new method that takes the postfix queue and uses a stack to solve the postfix equation
	 */
	static double solve(Queue queue)
	{
		Stack stack = new Stack();

		while(!queue.isEmpty()) //loop that runs as long as the postfix queue is not empty
		{
			Term t = queue.dequeue();

			/*
			 if the term at the front of the queue is not an operator then its an operand and we push it on to the stack
			 */
			if(!t.isOperator())
				stack.push(t);

			/*
			 if the term is not an operand then its an operator. We then pop the two terms (should be operands) before the operator
			 and get their double values. We perform the corresponding operation on these two values depending on what the operator
			 is and we push the new value onto the stack
			 */
			else
			{
				double a = ((Operand) stack.pop()).getOperand();
				double b = ((Operand) stack.pop()).getOperand();

				switch(((Operator) t).getOperator())
				{
				case '+' : stack.push(new Operand(b + a)); break;
				case '-' : stack.push(new Operand(b - a)); break;
				case '*' : stack.push(new Operand(b * a)); break;
				case '/' : stack.push(new Operand(b / a)); break;	    			
				}
			}

		}
		
		/*
		 when the loop is over and the queue is empty, we should have one value in that stack that is the solution. We pop this value
		 and get its double value and return it as the solution to the postfix expression 	
		 */
		return ((Operand) stack.pop()).getOperand();
	}





	public static void main(String[] args) {




		try {

			Scanner in = new Scanner(System.in);

			System.out.print("Enter infix expression: ");
			String exp = in.nextLine();

			Queue queue = input(exp);

			System.out.println("Standard Infix expression: " + queue);

			Queue postfix = infixToPostfix(queue);
			System.out.println("Postfix expression: " + postfix);

			System.out.println("Solution: " + solve(postfix));

			in.close();


		} catch (InvalidOperatorException e) {
			System.out.println("Invalid Operator: " + e.getOperator());
		} catch (InvalidOperandException e) {
			System.out.println("Invalid Operand");
		} catch (UnbalancedRightParenthesisException e) {
			System.out.println("Unbalanced Right Parenthesis: ')'");
		} catch (UnbalancedLeftParenthesisException e) {
			System.out.println("Unbalanced Left Parenthesis: '('");
		} catch (MissingOperandException e) {
			System.out.println("Missing Operand");
		}






	}

}
