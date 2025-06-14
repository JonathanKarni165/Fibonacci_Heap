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
		HeapNode a = fHeap.insert(2, "hi");
		HeapNode b = fHeap.insert(10, "bye");
		HeapNode c = fHeap.insert(5, "bye");
		HeapNode d = fHeap.insert(7, "bye");
		

		System.out.println(fHeap.toString());
		System.out.println(fHeap.min.key);
		System.out.println(fHeap.size());
		fHeap.decreaseKey(b, 9);
		System.out.println(fHeap.toString());
		System.out.println(fHeap.toString());
		fHeap.link(a, b);
		//fHeap.link(c, d);
		fHeap.printHeap();
		System.out.println(fHeap.min.key);
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
		newNode.rank = 0;

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
		HeapNode curr = x.parent;
		int numOfCuts = 1;
		x.key -= diff;
		this.min = (this.min.key > x.key) ? x : this.min;
		if(curr == null){
			return 0;
		}
		curr.looserNum++;
		//suggestion to change method name to cutNode or updateCutPointers
		this.updatePointers(x);
		while(curr != null && curr.looserNum == c){
			curr.looserNum = 0;
			if(curr.parent != null){
				curr.parent.looserNum++;
				numOfCuts++;
				this.updatePointers(curr);
			}
			curr = curr.parent;
		}
		return numOfCuts; // should be replaced by student code
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
	 * 
	 */
	public HeapNode link(HeapNode root1, HeapNode root2)
	{
		HeapNode bigNode = root1.key >= root2.key ? root1 : root2;
		HeapNode smallNode = root1.key >= root2.key ? root2 : root1;

		//unlink big node
		bigNode.prev.next = bigNode.next;
		bigNode.next.prev = bigNode.prev;

		//circular linked list -- so the last is prev of the first
		HeapNode firstChildTemp = bigNode.child;
		HeapNode lastChildTemp = bigNode.child!=null? bigNode.child.prev : null;

		//link the roots
		smallNode.child = bigNode;
		bigNode.parent = smallNode;

		//update second degree node linked list
		if(firstChildTemp != null)
		{
			bigNode.next = firstChildTemp;
			firstChildTemp.prev = bigNode;
		}
		else
		{
			bigNode.next = bigNode;
			bigNode.prev = bigNode;
		}
			

		if(lastChildTemp != null)
		{
			bigNode.prev = lastChildTemp;
			lastChildTemp.next = bigNode;
		}

		smallNode.rank++;
		return smallNode;
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

	/*
	 * sub routine for cut
	 * suggestion to change method name to cutNode or updateCutPointers
	 */
	public void updatePointers(HeapNode node){
		// change to have cycles
		// change to have cycles
		// change to have cycles
		// change to have cycles
		// change to have cycles
		// change to have cycles
		// change to have cycles
		node.next.prev = node.prev;
		node.prev.next = node.next;
		if(node.parent != null){
			node.parent.child = node.next;
		}
		node.parent = null;
		node.next = this.firstRoot;
		this.firstRoot.prev = node;
		this.firstRoot = node;
		node.prev = null;
		// change to have cycles
		// change to have cycles
		// change to have cycles
		// change to have cycles
		// change to have cycles
		// change to have cycles
		// change to have cycles
	}
	
	public void printHeap() {
		System.out.println("Fibonacci Heap:");
		if (firstRoot == null) {
			System.out.println("(empty)");
			return;
		}

		HeapNode current = firstRoot;
		Set<HeapNode> visited = new HashSet<>(); // Prevent infinite loops in cyclic lists
		int treeNum = 1;

		while (current != null && !visited.contains(current)) {
			System.out.println("Tree #" + treeNum + " (root key: " + current.key + "):");
			printTree(current, 1);
			visited.add(current);
			current = current.next;
			treeNum++;
		}
	}

	private void printTree(HeapNode node, int depth) {
		if (node == null) return;

		for (int i = 0; i < depth; i++) System.out.print("  "); // Indentation
		System.out.println("Key: " + node.key + ", Info: " + node.info);

		// Recursively print children
		if (node.child != null) {
			HeapNode child = node.child;
			Set<HeapNode> visitedSiblings = new HashSet<>();
			while (child != null && !visitedSiblings.contains(child)) {
				printTree(child, depth + 1);
				visitedSiblings.add(child);
				child = child.next;
			}
		}
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
		public int looserNum;
	}
}
