import java.util.*;
/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 */
public class FibonacciHeap
{
	public static final double PHI = 1.6180339887;
	//TODO: delete main
	public static void main(String[] args){
		FibonacciHeap fHeap1 = new FibonacciHeap(2);
		HeapNode a = fHeap1.insert(6, "hi");
		HeapNode b = fHeap1.insert(2, "hi");
		HeapNode c = fHeap1.insert(1, "hi");
		

		
		fHeap1.deleteMin();
		fHeap1.printHeap();
		


	}
	public HeapNode min;
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
		{
			newNode.next = newNode;
			newNode.prev = newNode;
			this.firstRoot = newNode;
		}
		else
		{
			this.firstRoot.prev.next = newNode;
			newNode.prev  = this.firstRoot.prev;
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







	private int consolidate()
	{
		int numOfLinks = 0;

		HeapNode[] conArr = new HeapNode[1];
		HeapNode temp = null;

		int prevTreeCount = this.treeCount;
		

		//HeapNode curr = this.firstRoot;

		for(int i=0;i<prevTreeCount;i++){


			//detach firstRoot from heap and change pointers (temp holds the detatched part)
			if(i != prevTreeCount-1){
				this.firstRoot.next.prev = this.firstRoot.prev;
				this.firstRoot.prev.next = this.firstRoot.next;

				temp = this.firstRoot;
				this.firstRoot = this.firstRoot.next;

				temp.next = temp;
				temp.prev = temp;

				this.firstRoot = this.firstRoot.next;
			}
			else{
				temp = this.firstRoot;
				this.firstRoot = null;
				this.min = null;
			}
			
			while(temp.rank >= conArr.length){
				conArr = incArr(conArr);
			}
			while(conArr[temp.rank] != null){
				temp = this.link(temp, conArr[temp.rank]);
				conArr[temp.rank-1] = null;
				numOfLinks++;
				this.linkCount++;
				if(temp.rank >= conArr.length){
					conArr = incArr(conArr);
				}
			}
			conArr[temp.rank] = temp;
			
		}

		this.treeCount = 0;
		for(int i=conArr.length-1; i>=0;i--){
			if(conArr[i] != null){
				

				if(this.treeCount == 0){
					this.firstRoot = conArr[i];
					this.min = conArr[i];
				}

				else{
					conArr[i].next = this.firstRoot;
					conArr[i].prev = this.firstRoot.prev;

					this.firstRoot = conArr[i];

					this.firstRoot.prev.next = this.firstRoot;
					this.firstRoot.next.prev = this.firstRoot;
					
					this.min = (this.min.key < this.firstRoot.key) ? this.min : this.firstRoot;
				}

				this.treeCount++;
				conArr[i] = null;
				
			}
		}
		
		return numOfLinks;
	}




	private HeapNode[] incArr(HeapNode[] arr){
		HeapNode[] newArr = new HeapNode[arr.length*2];
		for(int i=0;i<arr.length;i++){
			newArr[i] = arr[i];
		}
		return newArr;
	}






	private int consolidate1()
	{
		int numOfLinks = 0;
		double maxSize = logBase(PHI, this.size)+1;
		maxSize = Math.ceil(maxSize);
		ArrayList<HeapNode> bucketList = new ArrayList<HeapNode>((int)maxSize);
		for(int i=0; i<maxSize ; i++)
			bucketList.add(null);

		//insert to bucket list
		HeapNode cur = this.firstRoot;

		//System.out.println(this.treeCount + " tree count");
		//System.out.println(cur.rank + " rank");

		for(int i=0; i<this.treeCount; i++)
		{
			if(bucketList.get(cur.rank) == null)
			{
				bucketList.set(cur.rank, cur);
				cur = cur.next;
			}
			else
			{
				int curRank = cur.rank;
				HeapNode linkCur = cur;
				while (bucketList.get(curRank) != null) 
				{
					linkCur = link(bucketList.get(curRank), linkCur);
					numOfLinks++;
					this.linkCount++;
					bucketList.set(curRank, null);
					//curRank++;
				}
				bucketList.set(curRank, linkCur);
				cur = linkCur.next;
			}
		}
		//hi
		//pop out of bucket list and build heap
		int i=0;
		int newTreeCount = 0;
		while (bucketList.get(i) == null)
			i++;
		cur = bucketList.get(i);
		newTreeCount++;

		this.firstRoot = cur;
		HeapNode temp;
		i++;

		while(i < (int)maxSize)
		{
			if (bucketList.get(i) != null)
			{
				temp = bucketList.get(i);
				cur.next = temp;
				temp.prev = cur;
				cur = temp;
				newTreeCount++;
			}
			i++;
		}
		cur.next = firstRoot;
		firstRoot.prev = cur;
		this.treeCount = newTreeCount;
		return numOfLinks;
	}

	/**
	 * 
	 * Return the minimal HeapNode, null if empty.
	 *
	 */
	//just return pointer???????????????????????????????????
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

		if(this.size == 1){
			this.size = 0;
			this.treeCount = 0;
			this.min = null;
			this.firstRoot = null;
			return 0;
		}

		if(this.min.next != this.min){
			if(this.min.child != null){
				HeapNode lastChild = this.min.child.prev;
				lastChild.next = this.min.next;
				this.min.child.prev = this.min.prev;
				this.min.prev.next = this.min.child;
				this.min.next.prev = lastChild;
				
			}
			else{
				this.min.prev.next = this.min.next;
				this.min.next.prev = this.min.prev;
			}
		}
		

		if(this.firstRoot == this.min){
			if(this.min.prev != this.min){
				this.firstRoot = this.min.prev;
			}
			else{
				if(this.min.child != null){
					this.firstRoot = this.min.child;
				}
				else{
					this.firstRoot = null;
				}
			}
		}


		HeapNode curr = this.min.child;
		for(int i=0;i<this.min.rank;i++){
			curr.parent = null;

			curr = curr.next;
		}
		this.treeCount--;
		this.treeCount += this.min.rank;
		this.min = this.firstRoot;
		curr = this.firstRoot.next;
		//printHeap();
		for(int i=0;i<this.treeCount-1;i++){
			this.min = (curr.key < this.min.key) ? curr : this.min;
			curr = curr.next;
		}
		this.size--;
		// System.out.println("before consolidate:");
		// this.printHeap();
		// System.out.println("*****************************************************************************************************");
		int links = this.consolidate();
		return links; // should be replaced by student code

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
		this.treeCount++;
		curr.looserNum++;
		//suggestion to change method name to cutNode or updateCutPointers
		this.cutPointerUpdate(x);
		this.cutCount++;
		while(curr != null && curr.looserNum == c){
			curr.looserNum = 0;
			if(curr.parent != null){
				curr.parent.looserNum++;
				numOfCuts++;
				this.cutPointerUpdate(curr);
				this.cutCount++;
				this.treeCount++;
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
		int diff = x.key-this.min.key; //might not work for pre condition in decrease key
		this.decreaseKey(x, diff+1); //should i put the value somewhere?
		int links = deleteMin();
		return links; // should be replaced by student code
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
		HeapNode bigNode = root1.key > root2.key ? root1 : root2;
		HeapNode smallNode = root1.key > root2.key ? root2 : root1;
		
		//unlink big node


		// if (this.firstRoot == bigNode)
		// 	this.firstRoot = bigNode.next;
		
		bigNode.prev.next = bigNode.next;
		bigNode.next.prev = bigNode.prev;
		
		if (smallNode.child != null)
		{
			smallNode.child.prev.next = bigNode;
			bigNode.prev = smallNode.child.prev;
			smallNode.child.prev = bigNode;
			bigNode.next = smallNode.child;
			smallNode.child = bigNode;
		}
		else
		{
			smallNode.child = bigNode;
			bigNode.next = bigNode;
			bigNode.prev = bigNode;
		}
		bigNode.parent = smallNode;
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
		this.min = this.min.key > heap2.min.key ? heap2.min : this.min;
		this.treeCount += heap2.treeCount;
		this.size += heap2.size;
		this.linkCount += heap2.linkCount;
		this.cutCount += heap2.cutCount;

		if(this.firstRoot == null)
		{
			this.firstRoot = heap2.firstRoot;
			
		}
		if(heap2.firstRoot != null && this.firstRoot != null)
		{
			HeapNode first = heap2.firstRoot;
			HeapNode last = heap2.firstRoot.prev;

			this.firstRoot.prev.next = first;
			first.prev = this.firstRoot.prev;
			this.firstRoot.prev = last;
			last.next = this.firstRoot;
		}
		
		heap2.firstRoot = null;
		heap2.min = null;
		heap2 = null;
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
	public void cutPointerUpdate(HeapNode node){
		//always has parent
		//remove from list
		node.parent.rank--;
		node.next.prev = node.prev;
		node.prev.next = node.next;

		//update children
		node.parent.child = (node.next == node) ? null : node.next;
		
		node.parent = null;
		node.next = this.firstRoot;
		node.prev = this.firstRoot.prev;
		this.firstRoot.prev.next = node;
		this.firstRoot.prev = node;
		this.firstRoot = node;
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
		System.out.println("Key: " + node.key + ", looser: " + node.looserNum);

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

	public static double logBase(double base, double value) {
        return Math.log(value) / Math.log(base);
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

		
		public int getKey()
		{
			return this.key;
		}
	}
}
