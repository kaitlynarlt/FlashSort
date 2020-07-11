import java.io.File;
import java.io.PrintStream;
import java.util.Random;

public class Sort{

    public static void main(String[] args) throws Exception{

        File file = new File("output1000iterations_attempt2.txt");
        PrintStream output = new PrintStream(file); 
        
        int num_of_tests = 1000;
        String[] outputs = new String[num_of_tests];
        for(int i = 0 ; i<num_of_tests ; i++){
            outputs[i]="";
        }
        int itterations = 1000;
        int itteratoionMultiplier = 100;
        //int length = (int) Math.pow(100, i);
        //String temp1 = "";
        //String temp2 = "";
        
        for(int i=1;i<itterations;i++){ 
            int length = i* itteratoionMultiplier;
            int totalTime = 0;
            if(i%10==0){
                System.out.println(length);
            }
            //    output.println(length);
            for(int j = 0 ; j < num_of_tests; j ++ ){
                totalTime+=runTest(length);
            }
            double averageTime=totalTime/num_of_tests;
            output.println(length+","+averageTime);
        }
        //output.println(temp1);
       // output.println();
       // output.println(temp2);
        /*for(int i = 0 ; i<outputs.length ; i++){
            output.println(outputs[i]);
        }*/
        System.out.println("done");
        
        
    }
    
    private static long runTest(int length){
        
        int[] numbers = new int[length];
        Random rand = new Random();
        for(int i=0; i<length; i++){
            numbers[i]=Math.abs(rand.nextInt())%100;
        }
        long timeBeforeMilli = System.currentTimeMillis();
        long timeBeforeNano = System.currentTimeMillis();
        ListNode[] sorted = sort(numbers);
        long timeAfterNano = System.currentTimeMillis();
        long timeAfterMilli = System.currentTimeMillis();
        
        int timeTaken = (((int)timeAfterMilli - (int)timeBeforeMilli)*10000) + ((int)timeAfterNano - (int)timeBeforeNano);

       // printArray(numbers);
       // printArray(sorted);
        
        return timeAfter-timeBefore;
        
    }
    
    private static void printArray(int[] values){
        for(int i =  0 ; i < values.length-1 ; i++){
            System.out.print(values[i]+", ");
        }
        System.out.println(values[values.length-1]);
    }
    
    private static void printArray(ListNode[] values){
        for(int i =  0 ; i < values.length -1 ; i++){
            if(values[i] != null){
                ListNode.printList(values[i]);
                System.out.print(", ");
            }
        }
        ListNode.printList(values[values.length-1]);
        System.out.println();
    }
    
    public static ListNode[] sort(int[] from){
        if(from==null || from.length<1){
            return null;
        }
        int min=from[0];
        int max=from[0];
        int sum=0;
        for(int i=0; i<from.length; i++){
            if(from[i]<min){
                min=from[i];
            }
            if(from[i]>max){
                max=from[i];
            }
            sum+=from[i];
        }
        double average = ((double)sum)/((double)from.length);
        int num_below_average=0;
        int num_above_average=0;
        for(int i=0; i<from.length; i++){
            int compare = Double.compare(from[i],average);
            if(compare<=0){
                num_below_average++;
            } else {
                num_above_average++;
            }
        }
        
        double min_offset = (average-min)/(num_above_average);
        double max_offset = (max-average)/(num_above_average);
        
        ListNode[] sorted = new ListNode[from.length+1];
        //System.out.println(sorted.length);
        for(int i=0 ; i<from.length ; i++){
            //hash value at i
            int current = from[i];
            if(current > average){
                double temp = (current-average)/(max_offset);
                int insertLocation = ((int)(temp))+num_below_average;
                sorted[insertLocation]= ListNode.insert(current,sorted[insertLocation]);
            } else if(current < average){
                int insertLocation = (int)((current-min)/min_offset);
                sorted[insertLocation] = ListNode.insert(current,sorted[insertLocation]);
            } else {
                sorted[num_below_average] = ListNode.insert(current,sorted[num_below_average]);
            }
        }
        return sorted;
    }
}