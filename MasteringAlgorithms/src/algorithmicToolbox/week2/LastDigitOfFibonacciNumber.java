package algorithmicToolbox.week2;

import java.util.Scanner;

public class LastDigitOfFibonacciNumber {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int number = in.nextInt();
		
		if(number == 0){
			System.out.println(0);
			return;
		}
		else if(number == 1){
			System.out.println(1);
			return;
		}
		
		int[] fibonacci = new int[number+1];
		fibonacci[0] = 0;
		fibonacci[1] = 1;
		
		for(int i=2;i<fibonacci.length;i++){
			fibonacci[i] = (fibonacci[i-1] + fibonacci[i-2]) % 10;
		}
		
		System.out.println(fibonacci[number]);
		
	}

}
