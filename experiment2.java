import java.util.Collections;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class experiment2 {
    public static void main(String[] args) {


        ArrayList<Integer> itemsToInsert = new ArrayList<Integer>();
        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>(); 


        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter c value");

        String val = myObj.nextLine();  // Read user input

        int cVal = Integer.parseInt(val);
        

        for (int i = 1; i <= 464646; i++) {
            itemsToInsert.add(i);
        }
        Collections.shuffle(itemsToInsert);

        long startTime1 = System.nanoTime();

        FibonacciHeap fb = new FibonacciHeap(2);
        for (int i = 0; i < 464646; i++) {
            nodes.add(fb.insert(itemsToInsert.get(i), null));
        }

        long endTime1 = System.nanoTime();

        //sort nodes from max to min
        Collections.sort(nodes, new CompNode());

        long startTime2 = System.nanoTime();

        fb.deleteMin();

        //now delete max until 46 left
        int i=0;
        while (nodes.get(i).key > 48) {
            fb.decreaseKey(nodes.get(i), nodes.get(i).key);
            // if(fb.size() < 5){
            //     System.out.println("************************************************************************");
            //     fb.printHeap();
            //     System.out.println("************************************************************************");
            // }

            // System.out.println("************************************************************************");
            // fb.printHeap();
            // System.out.println("************************************************************************");

            i++;
        }

        fb.deleteMin();

        long endTime2 = System.nanoTime();
        double time = ((endTime1-startTime1)+ (endTime2-startTime2))/1000000;
        System.out.println("time: " + (double)(time/1000000));
        System.out.println("size: " + fb.size());
        System.out.println("total links: " + fb.totalLinks());
        System.out.println("total cuts: " + fb.totalCuts());
        System.out.println("number of trees: " + fb.numTrees());


        String filePath = "data1.csv";
        String data = time + "," + fb.size() + "," + fb.totalLinks() + "," + fb.totalCuts() + "," + fb.numTrees()+ "," + cVal;
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
