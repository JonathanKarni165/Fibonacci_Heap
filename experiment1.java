import java.util.Collections;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class experiment1 {
    public static void main(String[] args) {


        ArrayList<Integer> itemsToInsert = new ArrayList<Integer>();
        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>(); 

        
        int NUM = 464646;
        for (int i = 0; i < NUM; i++) {
            itemsToInsert.add(i);
        }
        Collections.shuffle(itemsToInsert);

        long startTime1 = System.nanoTime();

        FibonacciHeap fb = new FibonacciHeap(2);
        for (int i = 0; i < NUM; i++) {
            nodes.add(fb.insert(itemsToInsert.get(i), null));
            //fb.printHeap();
        }

        long endTime1 = System.nanoTime();

        //sort nodes from max to min
        Collections.sort(nodes, new CompNode());

        long startTime2 = System.nanoTime();
        System.out.println("\n\n\n");
        fb.deleteMin();

        //now delete max until 46 left
        int i=0;
        while (fb.size() > 5) {
            fb.delete(nodes.get(i));
            if(fb.size() < 10)
            {
                System.out.println("\n\n\n");
                fb.printHeap();
            }
            
            i++;
        }

        long endTime2 = System.nanoTime();
        double time = ((endTime1-startTime1)+ (endTime2-startTime2))/1000000;
        System.out.println("time: " + (double)(time/1000000));
        System.out.println("size: " + fb.size());
        System.out.println("total links: " + fb.totalLinks());
        System.out.println("total cuts: " + fb.totalCuts());
        System.out.println("number of trees: " + fb.numTrees());


        String filePath = "data.csv";
        String data = time + "," + fb.size() + "," + fb.totalLinks() + "," + fb.totalCuts() + "," + fb.numTrees();
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.append(data + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
         * for (FibonacciHeap.HeapNode heapNode : nodes) {
            System.out.println(heapNode.key);
            }
         */
        
    }
    public static class CompNode implements Comparator<FibonacciHeap.HeapNode>{
        @Override
        public int compare(FibonacciHeap.HeapNode o1, FibonacciHeap.HeapNode o2) {
            if(o1.key < o2.key)
                return 1;
            else if(o1.key == o2.key)
                return 0;
            else
                return -1;
        }
        
    }
}
