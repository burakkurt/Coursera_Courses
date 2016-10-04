package algorithmicToolbox.week4;

import java.util.Arrays;
import java.util.Scanner;

public class BinarySearch {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int numberSize = in.nextInt();
		
		int[] numbers = new int[numberSize];
		for (int i=0;i<numbers.length;i++) {
			numbers[i] = in.nextInt();
		}
		Arrays.sort(numbers);
		
		int testSize = in.nextInt();
		int[] testArr = new int[testSize];
		for(int n=0;n<testSize;n++){
			testArr[n] = in.nextInt();
		}
		
		
		for (int i : testArr) {
			System.out.print(binarySearch(numbers, i) + " ");
		}
		
	}
	
	public static int binarySearch(int[] numberArr, int number){
		int low=0;
		int high=numberArr.length-1;
		while(low <= high){
			int mid = (low+high)/2;
			if(numberArr[mid] > number)
				high = mid-1;
			else if(numberArr[mid] < number)
				low = mid+1;
			else
				return mid;
		}
		
		return -1;
	}

}
