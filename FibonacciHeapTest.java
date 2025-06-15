
public class FibonacciHeapTest {

    public static void main(String[] args) {
        testBasicInsertFindMin();
        testDeleteMinSequence();
        testDecreaseKeyAndCascadingCuts();
        testDeleteNode();
        testMeldHeaps();
        testStatistics();
        testNumTreesAndSize();
    }

    private static void testBasicInsertFindMin() {
        System.out.println("== Test: Basic Insert and FindMin ==");
        FibonacciHeap heap = new FibonacciHeap(2);

        heap.insert(30, "a");
        heap.insert(20, "b");
        heap.insert(40, "c");

        System.out.println("Expected min: 20");
        System.out.println("Actual min: " + heap.findMin().getKey());
        System.out.println();
    }

    private static void testDeleteMinSequence() {
        System.out.println("== Test: DeleteMin Sequence ==");
        FibonacciHeap heap = new FibonacciHeap(2);
        heap.insert(10, "a");
        heap.insert(5, "b");
        heap.insert(15, "c");

        System.out.println("Min before deleteMin: " + heap.findMin().getKey());
        heap.deleteMin();
        System.out.println("Min after deleteMin: " + heap.findMin().getKey()); // Should be 10
        heap.deleteMin();
        System.out.println("Min after another deleteMin: " + heap.findMin().getKey()); // Should be 15
        heap.deleteMin();
        System.out.println("Min after last deleteMin: " + heap.findMin()); // Should be null
        System.out.println();
    }

    private static void testDecreaseKeyAndCascadingCuts() {
        System.out.println("== Test: DecreaseKey and Cascading Cuts ==");
        FibonacciHeap heap = new FibonacciHeap(2);

        FibonacciHeap.HeapNode[] nodes = new FibonacciHeap.HeapNode[10];
        for (int i = 0; i < 10; i++) {
            nodes[i] = heap.insert(i + 10, "val" + (i + 10)); // insert 10–19
        }

        // Now do a lot of decreaseKeys to cause cascading cuts
        heap.decreaseKey(nodes[9], 1);  // smallest
        heap.decreaseKey(nodes[8], 0);  // even smaller -> triggers cut

        System.out.println("Min after cascading cuts: " + heap.findMin().getKey()); // Should be 0
        System.out.println();
    }

    private static void testDeleteNode() {
        System.out.println("== Test: Delete Specific Node ==");
        FibonacciHeap heap = new FibonacciHeap(2);
        FibonacciHeap.HeapNode node5 = heap.insert(5, "five");
        FibonacciHeap.HeapNode node10 = heap.insert(10, "ten");
        FibonacciHeap.HeapNode node20 = heap.insert(20, "twenty");

        heap.delete(node10);

        System.out.println("Min should still be 5: " + heap.findMin().getKey());
        heap.delete(node5);
        System.out.println("Min after deleting 5: " + heap.findMin().getKey());
        System.out.println();
    }

    private static void testMeldHeaps() {
        System.out.println("== Test: Meld Heaps ==");
        FibonacciHeap heap1 = new FibonacciHeap(2);
        FibonacciHeap heap2 = new FibonacciHeap(2);

        heap1.insert(10, "heap1-10");
        heap1.insert(30, "heap1-30");

        heap2.insert(5, "heap2-5");
        heap2.insert(20, "heap2-20");

        heap1.meld(heap2);

        System.out.println("Expected min: 5");
        System.out.println("Actual min after meld: " + heap1.findMin().getKey());
        System.out.println();
    }

    private static void testStatistics() {
        System.out.println("== Test: Total Links and Total Cuts ==");
        FibonacciHeap heap = new FibonacciHeap(2);

        for (int i = 0; i < 10; i++) {
            heap.insert(i + 1, "node" + (i + 1));
        }

        heap.deleteMin();  // Causes links
        System.out.println("Total links (after deleteMin): " + heap.totalLinks());

        FibonacciHeap.HeapNode n = heap.insert(50, "fifty");
        heap.decreaseKey(n, 0); // Causes cuts
        System.out.println("Total cuts (after decreaseKey): " + heap.totalCuts());
        System.out.println();
    }

    private static void testNumTreesAndSize() {
        System.out.println("== Test: numTrees and size ==");
        FibonacciHeap heap = new FibonacciHeap(2);

        heap.insert(3, "x");
        heap.insert(7, "y");
        heap.insert(1, "z");

        System.out.println("Size should be 3: " + heap.size());
        System.out.println("Num trees (before deleteMin): " + heap.numTrees());

        heap.deleteMin(); // Consolidation happens
        System.out.println("Size after deleteMin: " + heap.size());
        System.out.println("Num trees (after deleteMin): " + heap.numTrees());
        System.out.println();
    }
}
