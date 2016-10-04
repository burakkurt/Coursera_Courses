package algorithmicToolbox.week2;

import java.util.Scanner;

public class HugeFibonacciNumberModuloM {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		long n = in.nextLong();
		int m = in.nextInt();
		int[] numbers = new int[150000];
		
		fibonacci(numbers, m);
		int[] period = findPeriod(numbers);
		
		long remainder = (n % period.length);
		int result = period[(int)remainder];
		System.out.println(result);
	}
	
	public static void fibonacci(int[] numbers, int m){
		numbers[0] = 0;
		numbers[1] = 1;
		
		for(int i=2;i<numbers.length;i++){
			numbers[i] = (numbers[i-1] + numbers[i-2]) % m;
		}
		
	}
	
	public static int[] findPeriod(int[] numbers){
		int[] tempList = new int[numbers.length];
		int indexTempList = 0;
		
		for(int i=2;i<numbers.length;i++){
			if(numbers[i] == numbers[indexTempList]){
				tempList[indexTempList] = numbers[i]; 
				indexTempList++;
				
				if( indexTempList > 2 && (tempList[indexTempList-2] == 0 && tempList[indexTempList-1] == 1) ){
					int[] result = new int[indexTempList-2];
					for(int j=0;j<indexTempList-2;j++)
						result[j] = tempList[j];
					
					return result;
				}
			}else{
				indexTempList = 0;
			}
		}
		
		return numbers;
	}

}
