package algorithmicToolbox.week5;

import java.util.Scanner;

public class TakeAsMuchGoldAsPossible {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int capKnapsack = in.nextInt();
		int numGoldBars = in.nextInt();
		int[] goldBars = new int[numGoldBars];
		for(int i=0;i<numGoldBars;i++)
			goldBars[i] = in.nextInt();
		
		int[][] gold = new int[numGoldBars+1][capKnapsack+1];
		
		for(int i=1;i<gold.length;i++){
			for(int n=1;n<gold[i].length;n++){
				gold[i][n] = gold[i-1][n];
				int value = 0;
				if(goldBars[i-1] <= n)
					value = gold[i-1][n-goldBars[i-1]] + goldBars[i-1];
				if(gold[i][n] < value)
					gold[i][n] = value;
			}
		}
		
		System.out.println(gold[numGoldBars][capKnapsack]);
		
	}

}
