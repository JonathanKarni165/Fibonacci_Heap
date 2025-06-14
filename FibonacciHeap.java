import java.util.*;
/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 */
public class FibonacciHeap
{
	public static void main(String[] args){
		FibonacciHeap fHeap = new FibonacciHeap(2);
		fHeap.insert(2, "hi");
		fHeap.insert(3, "bye");
		fHeap.insert(0, "lala");

		System.out.println(fHeap.toString());
		System.out.println(fHeap.min.key);
		System.out.println(fHeap.size());

	}
	public HeapNode min; //why both?
	private int c;
	private HeapNode firstRoot;
	private int size;
	private int linkCount;
	private int cutCount;
	private int treeCount;
	
	/**
	 *
	 * Constructor to initialize an empty heap.
	 * pre: c >= 2.
	 *
	 */
	public FibonacciHeap(int c)
	{
		this.c = c;
		this.size = 0;
		this.linkCount = 0;
		this.cutCount = 0;
		this.treeCount = 0;
	}

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapNode.
	 *
	 */
	//check branch
	public HeapNode insert(int key, String info) 
	{   
		HeapNode newNode = new HeapNode();
		newNode.key = key;
		newNode.info = info; 
		if(this.firstRoot == null)
			this.firstRoot = newNode;
		else
		{
			this.firstRoot.prev = newNode;
			newNode.next = this.firstRoot;
			this.firstRoot = newNode;
		}
		if(this.min == null)
			this.min = newNode;
		this.min = (this.min.key > newNode.key) ? newNode : this.min;
		// why is there "min" and "minNode"?
		this.size++;
		this.treeCount++;
		return newNode;
	}

	/**
	 * 
	 * Return the minimal HeapNode, null if empty.
	 *
	 */
	public HeapNode findMin()
	{
		return this.min; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the minimal item.
	 * Return the number of links.
	 *
	 */
	public int deleteMin()
	{
		return 46; // should be replaced by student code

	}

	/**
	 * 
	 * pre: 0<diff<x.key
	 * 
	 * Decrease the key of x by diff and fix the heap.
	 * Return the number of cuts.
	 * 
	 */
	public int decreaseKey(HeapNode x, int diff) 
	{    
		return 46; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the x from the heap.
	 * Return the number of links.
	 *
	 */
	public int delete(HeapNode x) 
	{    
		return 46; // should be replaced by student code
	}

	/**
	 * 
	 * link two trees with the same degree
	 * @param root1 - first tree root to be linked 
	 * @param root2 - first tree root to be linked 
	 */
	public HeapNode link(HeapNode root1, HeapNode root2)
	{
		HeapNode bigNode = root1.key >= root2.key ? root1: root2;
		HeapNode smallNode = root1.key >= root2.key ? root2 : root1;
		
		//circular linked list -- so the last is prev of the first
		HeapNode firstChildTemp = bigNode.child;
		HeapNode lastChildTemp = bigNode.child.prev;

		//link the roots
		bigNode.child = smallNode;

		//update second degree node linked list
		smallNode.next = firstChildTemp;
		firstChildTemp.prev = smallNode;
		lastChildTemp.next = smallNode;

		return bigNode;
	}

	/**
	 * 
	 * Return the total number of links.
	 * 
	 */
	public int totalLinks()
	{
		return this.linkCount; // should be replaced by student code
	}


	/**
	 * 
	 * Return the total number of cuts.
	 * 
	 */
	public int totalCuts()
	{
		return this.cutCount; // should be replaced by student code
	}


	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2)
	{
		return; // should be replaced by student code   		
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size()
	{
		return this.size;
	}


	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees()
	{
		return this.treeCount; // should be replaced by student code
	}

	public String toString()
	{
		String output = "";
		HeapNode cur = this.firstRoot;
		for(int i=0; i<this.treeCount; i++)
		{
			output += "**** tree number: " + i + " *****\n";
			output += cur.key;
			output += "\n**********\n";
			cur = cur.next;
		}
		return output;
	}

	/**
	 * Class implementing a node in a Fibonacci Heap.
	 *  
	 */
	public static class HeapNode{
		public int key;
		public String info;
		public HeapNode child;
		public HeapNode next;
		public HeapNode prev;
		public HeapNode parent;
		public int rank;
	}
}
