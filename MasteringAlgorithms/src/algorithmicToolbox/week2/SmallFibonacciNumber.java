package algorithmicToolbox.week2;

import java.util.Scanner;

public class SmallFibonacciNumber {
	

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int number =  in.nextInt();
		
		if(number == 0){
			System.out.println(0);
			return;
		}
		else if(number == 1){
			System.out.println(1);
			return;
		}
		
		int[] fibonacciNumbers = new int[number+1];
		fibonacciNumbers[0] = 0;
		fibonacciNumbers[1] = 1;
		
		for(int i=2;i<fibonacciNumbers.length;i++){
			fibonacciNumbers[i] = fibonacciNumbers[i-1] + fibonacciNumbers[i-2];
		}
		
		System.out.println( fibonacciNumbers[number] );
	}

}
