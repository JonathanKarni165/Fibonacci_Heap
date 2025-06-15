public class extra {
    FibonacciHeap fHeap = new FibonacciHeap(2);
		HeapNode a = fHeap.insert(2, "hi");
		HeapNode b = fHeap.insert(1, "bye");
		HeapNode c = fHeap.insert(5, "bye");
		HeapNode d = fHeap.insert(7, "bye");

		//fHeap.printHeap();
		HeapNode e = fHeap.insert(3, "kiko");
		HeapNode f = fHeap.insert(4, "liko");

		//fHeap.printHeap();
		HeapNode g = fHeap.insert(10, "wow");
		HeapNode h = fHeap.insert(15, "no");
		fHeap.printHeap();
		System.out.println("*****************************************************************************************************");
		fHeap.delete(e);
		
		FibonacciHeap fHeap2 = new FibonacciHeap(2);
		HeapNode k = fHeap.insert(53, "hi");
		HeapNode l = fHeap.insert(52, "bye");
		HeapNode m = fHeap.insert(50, "bye");
		HeapNode n = fHeap.insert(59, "bye");
		HeapNode o = fHeap.insert(55, "bye");


		System.out.println(fHeap.min.key + " " + fHeap.firstRoot.key);
}
