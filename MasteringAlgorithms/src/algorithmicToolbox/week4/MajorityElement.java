package algorithmicToolbox.week4;

import java.util.Arrays;
import java.util.Scanner;

public class MajorityElement {

	public static void main(String[] args) {
		Scanner in =  new Scanner(System.in);
		int numSize = in.nextInt();
		
		int[] numbers = new int[numSize];
		for(int i=0;i<numSize;i++){
			numbers[i] = in.nextInt();
		}
		Arrays.sort(numbers);
		
		int majNumRepet = 1;
		int tempMajNumRepet = 1;
		for (int n=1;n<numbers.length;n++) {
			if(numbers[n] == numbers[n-1])
				tempMajNumRepet++;
			else
				tempMajNumRepet=1;
			
			if(tempMajNumRepet > majNumRepet)
				majNumRepet = tempMajNumRepet;	
			
		}

		if(majNumRepet > numSize/2)
			System.out.println(1);
		else
			System.out.println(0);
		
	}

}
