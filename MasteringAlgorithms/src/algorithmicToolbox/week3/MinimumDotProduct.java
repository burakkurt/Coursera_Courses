package algorithmicToolbox.week3;

import java.util.Arrays;
import java.util.Scanner;

public class MinimumDotProduct {

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		
		int[] numArr1 = new int[size];
		for (int i=0;i<numArr1.length;i++)
			numArr1[i] = in.nextInt();
		int[] numArr2 = new int[size];
		for (int i=0;i<numArr2.length;i++)
			numArr2[i] = in.nextInt();
		
		Arrays.sort(numArr1);
		Arrays.sort(numArr2);
		
		long result = 0;
		for(int i=0;i<numArr1.length;i++){
			result += (long)((long)numArr1[i] * (long)numArr2[(numArr2.length-1)-i]);
		}
		
		System.out.println(result);
	}

}
