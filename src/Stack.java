import java.util.NoSuchElementException;

public class Stack { //create a blue print for a stack that will be used to convert from infix to postfix
	private Term[] data = new Term[10];//is a stack of terms so that it can be used for both operators and operands
	private int size = 0;
	
	public void push(Term value) { //puts value on the top of the stack and if the stack is full it will create a new stack twice the size
		if(size == data.length) {
			Term[]temp = new Term[size *2];
			for(int i=0; i<size; i++) {
				temp[i] = data[i];	
			}
			data = temp;
		}
		data[size] = value;
		size ++;
		
	}
	public Term pop() { //will remove the top element in the stack
		if(size == 0)
			throw new NoSuchElementException();
		Term top = data[size - 1];
		size --;
		return top;
	}
	public Term peek() { //will return the top element in the stack
		if(size == 0) 
			throw new NoSuchElementException();
		return data[size - 1];
	}
			 
	public int size() { //gives back the size of the stack
		return size;
	}
	
	public boolean isEmpty() { //will return true if the stack is empty
		return(size == 0); 
}
}

