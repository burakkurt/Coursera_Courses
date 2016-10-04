package algorithmicToolbox.week3;

import java.util.Scanner;

public class ChangingMoney {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int money = in.nextInt();
		int[] coins = {10,5,1};
		
		int numCoinsUsed = 0;
		for (int i=0;i<coins.length;i++) {
			while(money >= coins[i]){
				money -= coins[i];
				numCoinsUsed++;
			}
		}
		
		System.out.println(numCoinsUsed);
	}

}
