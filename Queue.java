/*
* @author Victor Bieniek
* 10/01/17
* A basic queue class written in java
*/

import java.util.EmptyStackException;
import java.util.Scanner;

public class Queue
{
	private int[] elements;
	private int size;
	private final int DEFAULTSIZE = 8;

	public Queue()
	{
		elements = new int[DEFAULTSIZE];
		size = 0;
	}

	public Queue(int size)
	{
		elements = new int[size];
		size = 0;
	}

	public void enqueue(int v)
	{
		//add to end of queue
		if(size < elements.length)
		{
			elements[size] = v;
		}
		else
		{
			//make a larger array and copy queue to it
			growArray();
			elements[size] = v;
		}
		size++;
	}

	public int dequeue()
	{
		//returns number from the beining of the queue and removes it
		if(this.isEmpty()) throw new EmptyStackException();
		int toReturn = elements[0];

		for(int i = 0; i < size - 1; i++)
		{
			elements[i] = elements[i+1];
		}
		size--;

		//check if queue needs to be shunk and shrink it if it does
		if(elements.length > (int)(size * 1.75) && elements.length > 8)
		{
			shrinkArray();
		}
		return toReturn;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public int getSize()
	{
		return size;
	}

	private void growArray()
	{
		//resizes the array by 25%
		int[] holdValues = elements.clone();
		for(int num : elements) System.out.print(num + " ");
		System.out.println();

		elements = new int[(int)(elements.length * 1.25)];

		System.arraycopy(holdValues, 0, elements, 0, holdValues.length);
	}

	private void shrinkArray()
	{
		int newLen;
		int[] holdValues = elements.clone();

		newLen = (int)(elements.length * 0.75);
		elements = new int[(newLen > size && newLen >= DEFAULTSIZE) ? newLen : (size > DEFAULTSIZE) ? size + 1 : DEFAULTSIZE];

		System.arraycopy(holdValues, 0, elements, 0, size);
		System.out.println("length: " + elements.length);
	}

	public static void main(String[] args)
	{
		Queue q = new Queue();
		Scanner scan = new Scanner(System.in);
		String input;
		String[] inArray;

		while(true)
		{
			System.out.print(">");
			input = scan.nextLine();
			if(input.startsWith("add"))
			{
				//add num
				inArray = input.split(" ");
				if(inArray.length > 1 && inArray[1].matches("\\-?\\d+"))
				{
					q.enqueue(Integer.parseInt(inArray[1]));
					System.out.println("added");
				} else System.out.println("Invalid Input");
			}
			else if(input.equals("get"))
			{
				//get and print
				try{
					System.out.println("Number is " + q.dequeue());
				} catch(EmptyStackException ex){
					System.out.println("Cannot get from empty Queue");
				}
			}
			else if(input.equalsIgnoreCase("help"))
			{
				System.out.println("Commands:\nadd <number>: add number to queue\n"+
					"get: gets a number from the queue");
			}
			else if(input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("q"))
			{
				System.exit(0);
			}
			else
			{
				if(!input.equals(""))System.out.println("Invalid input");
			}
		}

	}
}