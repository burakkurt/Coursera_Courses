package algorithmicToolbox.week3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PairwiseDistinctSummands {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int number = in.nextInt();
		
		List<Integer> numList = new ArrayList<Integer>();
		int i=1;
		while(number > 0){
			if(number-i > i || number-i == 0){
				number -= i;
				numList.add(i);
			}
			i++;
		}
		
		System.out.println(numList.size());
		for (Integer num : numList) {
			System.out.print(num + " ");
		}
	}
}
