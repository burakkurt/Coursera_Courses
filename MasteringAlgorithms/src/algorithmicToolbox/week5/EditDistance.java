package algorithmicToolbox.week5;

import java.util.Scanner;

public class EditDistance {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char[] input1 = in.next().toCharArray();
		char[] input2 = in.next().toCharArray();
		
		System.out.println(editDistance(input1, input2));
		
	}
	
	public static int editDistance(char[] input1, char[] input2){
		int[][] distance = new int[input1.length+1][input2.length+1];
		
		for(int n=0;n<distance[0].length;n++)
			distance[0][n] = n;
		for(int m=0;m<distance.length;m++)
			distance[m][0] = m;
		
		for(int j=1;j<distance[0].length;j++){
			for(int i=1;i<distance.length;i++){
				int insertion = distance[i][j-1] + 1;
				int deletion = distance[i-1][j] + 1;
				int match = distance[i-1][j-1];
				int mismatch = distance[i-1][j-1] + 1;
				
				if(input1[i-1] == input2[j-1])
					distance[i][j] = min(insertion, deletion, match);
				else
					distance[i][j] = min(insertion, deletion, mismatch);
			}
		}
		
//		for (int[] is : distance) {
//			for (int i : is) {
//				System.out.print(i + " ");
//			}
//			System.out.println();
//		}
		
		return distance[input1.length][input2.length];
	}
	
	public static int min(int a, int b, int c){
		int min = Integer.MAX_VALUE;
		
		if(a < min && a <= b && a <= c)
			min = a;
		else if(b < min && b <= a && b <= c)
			min = b;
		else if(c < min && c <= a && c <= b)
			min = c;
		
		return min;
	}

}
