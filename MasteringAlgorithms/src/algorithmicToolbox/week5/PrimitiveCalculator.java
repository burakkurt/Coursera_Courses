package algorithmicToolbox.week5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PrimitiveCalculator {
	
	public static List<Integer> numList = new ArrayList<Integer>();

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int number = in.nextInt();
		int[] minNumCoins = new int[number+1];
		
		minNumCoins[0] = 0;
		for(int m=1;m<minNumCoins.length;m++){
			int numCoins1=200000;
			int numCoins2=200000;
			int numCoins3=200000;
			
			if(1<=m)
				numCoins1 = minNumCoins[m-1] + 1;
			if(1<=m/2 && (m%2) == 0)
				numCoins2 = minNumCoins[m/2] + 1;
			if(1<=m/3 && (m%3) == 0)
				numCoins3 = minNumCoins[m/3] + 1;
			
			int min = min(numCoins1, numCoins2, numCoins3);
			
			minNumCoins[m] = min;
		}
		
		numList.add(number);
		backtrack(minNumCoins, number);
		Collections.sort(numList);
		
		System.out.println(numList.size()-1);
		for (int i : numList) {
			System.out.print(i + " ");
		}
		
	}
	
	public static int min(int a, int b, int c){
		int[] arr = new int[3];
		arr[0] = a;
		arr[1] = b;
		arr[2] = c;
		
		Arrays.sort(arr);
		
		return arr[0];
	}
	
	public static List<Integer> backtrack(int[] arr, int number){
		
		if((number%3) == 0 && arr[number/3] == arr[number]-1){
			numList.add(number/3);
			backtrack(arr, number/3);
		}
		else if((number%2) == 0 && arr[number/2] == arr[number]-1){
			numList.add(number/2);
			backtrack(arr, number/2);
		}
		else if((number-1) >= 1 && arr[number-1] == arr[number]-1){
			numList.add(number-1);
			backtrack(arr, number-1);
		}
		
		return numList;
	}

}
