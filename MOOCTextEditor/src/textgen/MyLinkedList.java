package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>();
		head.next = tail;
		tail = new LLNode<E>();
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		
		//creating instance of new node
		LLNode<E> newNode = new LLNode<E>();
		newNode.data = element;
		
		if(element == null){
			throw new NullPointerException();
		}
		else if(size() == 0){
//			newNode.next = head.next;
//			newNode.prev = newNode.next.prev;
//			newNode.next.prev = newNode;
//			newNode.prev.next = newNode;
			
			newNode.next = tail;
			newNode.prev = head;
			tail.prev = newNode;
			head.next = newNode;
			
			size++;
			return true;
		}
		else if(size() > 0){
			newNode.next = tail;
			newNode.prev = tail.prev;
			tail.prev.next = newNode;
			tail.prev = newNode;
			
			size++;
			return true;
		}
		
		
		return false;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		
		//cloning head node to tempNode. Iteration will be done over that object
		LLNode<E> tempNode = head;
		
		//exception
		if (index < 0 || index >= this.size()) {
	        throw new IndexOutOfBoundsException();
	    //iteration and returning value
		}else{
	    	int i=0;
	    	while(i<=index){
	    		tempNode = (LLNode<E>) tempNode.next;
	    		if(i == index){
	    			return tempNode.data;
	    		}
	    		i++;
	    			
	    	}
	    		
	    }
		
		return null;
		
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		
		// TODO: Implement this method
		
		//cloning head node to tempNode. This node will be previous node of newNode
		LLNode<E> tempNode = head;
				
		//creating instance of new node
		LLNode<E> newNode = new LLNode<E>();
		newNode.data = element;
		
		if(element == null){
			throw new NullPointerException();
		}
		else if(size() == 0 && index == 0){
			newNode.next = tail;
			newNode.prev = head;
			tail.prev = newNode;
			head.next = newNode;
			
			size++;
		}
		else if (index < 0 || index >= this.size()) {
	        throw new IndexOutOfBoundsException();
		}
		else{
	    	for(int i=0;i<index;i++){
	    		tempNode = tempNode.next;
	    	}
	    	
	    	newNode.next = tempNode.next;
			newNode.prev = tempNode;
			tempNode.next.prev = newNode;
			tempNode.next = newNode;
			
			size++;
		}
		
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		
		// TODO: Implement this method
		if (index < 0 || index >= this.size()) 
	        throw new IndexOutOfBoundsException();
		else{
			//cloning head node to tempNode. This node will be previous node of will be removed one
			LLNode<E> tempNode = head;
			//This node will be removed node
			LLNode<E> removedNode;
			
			for(int i=0;i<index;i++){
				tempNode = tempNode.next;
			}
			
			//assigning the node will be removed to removedNode
			removedNode = tempNode.next;
			
			tempNode.next.next.prev = tempNode;
			tempNode.next = tempNode.next.next;
			
			size--;
			return removedNode.data;
		}
		
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		
		//cloning head node to tempNode. This node will be set
		LLNode<E> tempNode = head;
		
		if(element == null){
			throw new NullPointerException();
		}
		else if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		else{
			for(int i=0;i<=index;i++){
				tempNode = tempNode.next;
			}
			tempNode.data = element;
			return tempNode.data;
		}
		
	}   
	
	public static void main(String[] args){
		MyLinkedList<Integer> l = new MyLinkedList<Integer>();
		l.add(0,5);
		l.add(10);
		l.add(1,15);
		
		l.set(2, 12121);
		l.set(3, 900);
		
		System.out.println(l.size());
		
		System.out.println(l.get(0));
	}
	
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor
	
	public LLNode(){
		this.data = null;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
