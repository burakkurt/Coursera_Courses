package algorithmicToolbox.week4;

import java.util.Scanner;

public class Inversions {
	
	public static void main(String[] args) {		
		Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int[] numbers = new int[size];
        
        for(int i=0;i<size;i++)
            numbers[i] = in.nextInt();
        
		long inversion = countInversion(numbers, 0, numbers.length-1);
		System.out.println(inversion);
		
	}
	
	public static long countInversion(int[] numbers, int first, int last){
		long x=0, y=0, z=0;
		if(first>=last)
			return 0;
		
		int mid = (first+last)/2;
		x = countInversion(numbers, first, mid);
		y = countInversion(numbers, mid+1, last);
		z = inversion(numbers, first, last);
		
		return x+y+z;
	}
	
	public static int inversion(int[] numbers, int first, int last){
		int mid = (last+first)/2;
		int firstInd = first, secInd = mid+1, i=0;
		int[] finalArr = new int[last-first+1];
		int inversion = 0;
		
		while(firstInd<=mid && secInd<=last){
			if(numbers[firstInd] <= numbers[secInd])
				finalArr[i++] = numbers[firstInd++];
			else{
				finalArr[i++] = numbers[secInd++];
				inversion += mid - firstInd + 1;
			}
		}
		
		while(firstInd <= mid)
			finalArr[i++] = numbers[firstInd++];
		while(secInd <= last)
			finalArr[i++] = numbers[secInd++];
		
		for (int n=0;n<last-first+1;n++)
			numbers[n+first] = finalArr[n];
		
		return inversion;
	}
	
}
