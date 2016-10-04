package algorithmicToolbox.week3;

import java.util.Arrays;
import java.util.Scanner;

public class FractionalKnapsack {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int numTest = in.nextInt();
		int weightOfBag = in.nextInt();
		
		Item[] itemValues = new Item[numTest];
		for(int i=0;i<numTest;i++){
			int calorie = in.nextInt();
			int weight = in.nextInt();
			
			itemValues[i] = new Item(calorie, weight);
		}
		
		System.out.println(fillKnapsack(itemValues, weightOfBag));
		
	}
	
	public static double fillKnapsack(Item[] itemValues, int weightOfBag){
		double valueOfBag = 0D;
		Arrays.sort(itemValues);
		
		for (int n=0;n<itemValues.length;n++) {
			Item currentItem = itemValues[n];
			if(weightOfBag == 0)
				return valueOfBag;
			
			while(weightOfBag > 0 && currentItem.getWeight() > 0){
				int restweightOfBag = min(weightOfBag, currentItem.getWeight());
				double amountWillAdd = restweightOfBag * currentItem.getValue();
				
				valueOfBag += amountWillAdd;
				weightOfBag -= restweightOfBag;
				currentItem.setWeight((int) (currentItem.getWeight() - restweightOfBag) );
			}
		}
		
		return valueOfBag;
	}
	
	public static int min(int weightOfBag, int weight){
		if(weightOfBag < weight)
			return weightOfBag;
		else if(weightOfBag > weight)
			return weight;
		else
			return weightOfBag;
	}

}

class Item implements Comparable<Item>{
	
	int calorie = 0;
	int weight = 0;
	double value;
	
	Item(int calorie, int weight){
		this.calorie = calorie;
		this.weight = weight;
		value = (double)calorie / (double)weight; 
	}
	
	public double getValue() {
		return value;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}

	@Override
	public int compareTo(Item otherItem) {
		if(value > otherItem.getValue())
			return -1;
		else if(value < otherItem.getValue())
			return 1;
		else
			return 0;
	}
	
}
