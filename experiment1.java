import java.util.Collections;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class experiment1 {
    public static void main(String[] args) {
        int[] cArr = {2,3,4,10,20,100,1000,5000};

        String filePath = "data3.csv";
        try (FileWriter fw = new FileWriter(filePath, true))
        {
            for (int i = 0; i < cArr.length; i++) {
                double[] CData = {0.0, 0.0, 0.0, 0.0, 0.0};

                for (int j = 0; j < 20; j++) {
                    double[] curData = run(cArr[i]);

                    for (int k = 0; k < CData.length; k++) {
                        CData[k] += curData[k];
                    }
                    
                }
                for (int k = 0; k < CData.length; k++) 
                    CData[k] /= 20;
                fw.append(cArr[i] + "," + CData[0] +"," + CData[1] +"," + CData[2] +"," + CData[3] +"," + CData[4] + "\n");
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        
        
    }
    public static double[] run(int c) {


        ArrayList<Integer> itemsToInsert = new ArrayList<Integer>();
        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>(); 

        for (int i = 1; i <= 464646; i++) {
            itemsToInsert.add(i);
        }
        Collections.shuffle(itemsToInsert);

        long startTime1 = System.nanoTime();

        FibonacciHeap fb = new FibonacciHeap(c);
        for (int i = 0; i < 464646; i++) {
            nodes.add(fb.insert(itemsToInsert.get(i), null));
        }

        long endTime1 = System.nanoTime();

        //sort nodes from max to min
        //Collections.sort(nodes, new CompNode());

        long startTime2 = System.nanoTime();

        fb.deleteMin();

        //now delete max until 46 left
        int i=0;
        while (fb.size() > 46) {
            fb.delete(nodes.get(i));
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
        String data = time + "," + fb.size() + "," + fb.totalLinks() + "," + fb.totalCuts() + "," + fb.numTrees()+ "," + c;
        double[] dataArr = {time,fb.size(),fb.totalLinks(),fb.totalCuts(),fb.numTrees()};
        return dataArr;


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
