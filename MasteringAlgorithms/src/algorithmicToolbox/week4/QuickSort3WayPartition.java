package algorithmicToolbox.week4;

import java.util.Scanner;

public class QuickSort3WayPartition {

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		int[] input = new int[size];
		for(int i=0;i<size;i++)
			input[i] = in.nextInt();
		
        sort (input, 0, input.length-1);
        
        for (int i : input) {
			System.out.print(i + " ");
		}
    }

    public static void sort(int[] input, int lowIndex, int highIndex) {


        if (highIndex<=lowIndex) return;

        int lt=lowIndex;
        int gt=highIndex;
        int i=lowIndex+1;

        int pivotIndex=lowIndex;
        int pivotValue=input[pivotIndex];


        while (i<=gt){


            if (input[i] < pivotValue){
            	swap(input, i++, lt++);
            }
            else if (input[i] > pivotValue){
            	swap(input, i, gt--);
            }
            else{
                i++;
            }


        }

        sort (input, lowIndex, lt-1);
        sort (input, gt+1, highIndex);


    }
    
    public static void swap(int[] input, int a, int b){
    	int temp = input[b];
    	input[b] = input[a];
    	input[a] = temp;
    }

}
