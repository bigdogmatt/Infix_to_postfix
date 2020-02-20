
public class Queue {//is the blue print for the input and output queue
	private Term [] data = new Term[10] ; //is a queue of terms so that it can be used for both operators and operands
	private int start = 0;
	private int size = 0;
	
	public void enqueue(Term value) {//puts value on the top of the queue and if the queue is full it will create a new stack twice the size
		if(size == data.length) {
			Term [] temp = new Term [size * 2];
			for(int i = 0; i<size; ++i)
				temp[i] = data[(start + i)%data.length];
			data = temp;
			start = 0;
		}
		data[(start + size)%data.length]=value;
		size ++;		
	}
	public Term dequeue() { //removes the first element
		Term first = data[start];
		size--;
		start++;
		return first;
	}
	public Term front() {//returns the first element in the queue
		return data[start];
	}
	public int size() {//returns the size of the queue
		return size;
	}
	
	public String toString() { //put the elements from the queue into a string with a space between them
		String string = "";
		for(int i = 0; i<size; i++) {
			if( i <size - 1) //is size - 1 so that there is not a space after the last element
				string += data[(start + i) % data.length] + " ";
			else
				string += data[(start + i) % data.length];
		}
		return string;
	}
	
	public boolean isEmpty() { //return true if queue is empty
		return(size == 0); 
	}
}

