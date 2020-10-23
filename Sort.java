import java.io.File;
import java.io.PrintStream;
import java.util.Random;

public class Sort{
    
    /* This program will repeatedly run the Flash sort method on randomly generated sets of int numbers.
     * The program will record and output the runtime for set of int numbers of various sizes to the file name given in ResultsFileName.
     * Each set size is considered to be a distinct data point.
     * The number of data points measured is given by NumberOfDataPoints.
     * Multiple trials will be run for each data point. The number of trials per data point is given by NumberOfTrialsRunPerDataPoint.
     * The size of the set used for each data point increases by ItterativeIncrease, such that the smallest set measured will be of size ItterativeIncrease, and the largest will be of size NumberOfDataPoints*ItterativeIncrease.
     */
    private static String ResultsFileName = "output100dp.txt";
    private static int NumberOfDataPoints = 100;
    private static int NumberOfTrialsRunPerDataPoint = 1000;
    private static int ItterativeIncrease = 100;
    
    /* Indicates wether results should be recorded in nanoseconds (true) or milliseconds (false).
     * For tests involving smaller sets of numbers for flashsort, it is recommended that data be recorded in nanoseconds (true).
     * For tests involving larger sets, it is recommended that data be recorded in milliseconds (false) to prevent integer overflow.
     */
    private static boolean UseNanoTime = true;
    
    // how frequently results are printed to the screen
    private static int NumberOfItterationsToPrintResults = 10;
    
    public static void main(String[] args) throws Exception{
        
        // open a file/printstream to store/write results
        File file = new File(ResultsFileName);
        PrintStream toResults = new PrintStream(file); 
        
        /* Indicates format of document for ease of understanding, 
         * while still allowing the document to be easily parsed 
         */
        toResults.print("Set size, average runtime");
        if (UseNanoTime){
            toResults.println(" in nanoseconds");
        } else {
            toResults.println(" in milliseconds");
        }
        
        /* For each set size, takes the average of the recorded runtimes
         * for each trial and prints to the results file.
         * Average runtime will be calculated by storing cumulative runtime of all trials
         * and dividing by the number of trials run.
         */
        for(int i=1 ; i<=NumberOfDataPoints ; i++){ 
            
            int setSize = i* ItterativeIncrease; // current set size
            int cumulativeRuntime = 0; //keeps track of runtime
            
            
            
            // run trials and compute average
            for(int j = 0 ; j < NumberOfTrialsRunPerDataPoint; j ++ ){
                cumulativeRuntime+=runTest(setSize);
            }
            double averageTime=cumulativeRuntime/NumberOfTrialsRunPerDataPoint;
            // prints results of every 10th test to screen to reassure user that the program is still running.
            if(i%NumberOfItterationsToPrintResults==0){
                System.out.println("data point #"+i+"\n\tNumber of elements to be sorted: "+setSize);
                System.out.print("\t Average Runtime: "+averageTime);
                if(UseNanoTime){
                    System.out.println(" ns");
                } else {
                    System.out.println(" ms");
                }
            }
            // record results
            toResults.println(setSize+","+averageTime);
        }
        
        // alerts user that the program is done running
        System.out.println("done");     
        
    }
    
    /* Generates array of randomly generated ints and runs Flashsort on the array.
     * Returns the time taken in nanoseconds.
     */
    private static long runTest(int length){
        
        // generate array of random numbers
        int[] numbers = new int[length];
        Random rand = new Random();
        for(int i=0; i<length; i++){
            numbers[i]=Math.abs(rand.nextInt())%100;
        }
        
        // Record time before sort function is called.
        // Record nano time after milli time as nano time is more precise measurement
        // and more likely to change of the two.
        long timeBeforeMilli = System.currentTimeMillis();
        long timeBeforeNano = System.nanoTime(); 
        
        sort(numbers);  //run flash sort
        
        // Record time after function is called.
        // Record nano time first as is more likely to change if called second.
        long timeAfterNano = System.nanoTime();
        long timeAfterMilli = System.currentTimeMillis();
        
        if (UseNanoTime){ 
            return (((int)timeAfterMilli - (int)timeBeforeMilli)*10000) + ((int)timeAfterNano - (int)timeBeforeNano); 
        } else {
            return (int)timeAfterMilli - (int)timeBeforeMilli;
        }
                
    }
    
    /* The flash sort function.
     * Takes array of unsorted integers as arg.
     * Returns sorted array 
     */
    public static int[] sort(int[] from){
        // Check if from is null
        if(from==null || from.length<1){
            return null;
        }
        
        // find the min, max and average value of elements in from
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
        
        
        // find the number of elements below/above the average
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
        
        // find range of values for each index.
        // min_offset gives the size of the range of values covered for indices below the average.
        // max_offset gives the size of the range of values covered for indices above the average.
        double min_offset = (average-min)/(num_above_average);
        double max_offset = (max-average)/(num_above_average);
        
        ListNode[] sorted = new ListNode[from.length+1];
        for(int i=0 ; i<from.length ; i++){
            int current = from[i];
            if(current > average){
                // locate index where current element goes and sort into elements in that index
                int insertLocation = ((int)((current-average)/(max_offset)))+num_below_average;
                sorted[insertLocation]= ListNode.insert(current,sorted[insertLocation]);
            } else if(current < average){
                // locate index where current element goes and sort into elements in that index
                int insertLocation = (int)((current-min)/min_offset);
                sorted[insertLocation] = ListNode.insert(current,sorted[insertLocation]);
            } else {
                sorted[num_below_average] = ListNode.insert(current,sorted[num_below_average]);
            }
            
        }
        
        int[] toReturn = new int[from.length];

        int currentToReturnIndex = 0;
        for(int i=0; i<sorted.length; i++){
            ListNode node = sorted[i];
            if(node != null){
                while (node.next != null){
                    toReturn[currentToReturnIndex] = node.value;
                    currentToReturnIndex++;
                    node=node.next;
                }
            }
        }
        return toReturn;
    }
}
