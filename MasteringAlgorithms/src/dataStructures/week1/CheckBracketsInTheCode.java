package dataStructures.week1;

import java.util.Scanner;
import java.util.Stack;

public class CheckBracketsInTheCode {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char[] charArr = in.next().toCharArray();
		
		int result = isBracketCorrect(charArr);
		if(result == -1)
			System.out.println("Success");
		else
			System.out.println(result);
		
	}
	
	public static int isBracketCorrect(char[] charArr){
		Stack<Mark> stack = new Stack<Mark>();
		
		for(int i=0;i<charArr.length;i++){	
			if(charArr[i]== '(' || charArr[i]== '[' || charArr[i]== '{')
				stack.push(new Mark(charArr[i], i+1));
			
			Mark lastOpenBracket = new Mark(' ', 0);
			if((charArr[i]== ')' || charArr[i]== ']' || charArr[i]== '}') && stack.empty() == false)
				lastOpenBracket = stack.pop();
			
			if( (charArr[i]== ')' && lastOpenBracket.getCharacter() != '(') || (charArr[i]== ']' && lastOpenBracket.getCharacter() != '[') || 
				(charArr[i]== '}' && lastOpenBracket.getCharacter() != '{') )
				return i+1;
			
		}
		
		if(stack.empty())
			return -1;
		else
			return stack.lastElement().getIndex();
			
	}
}

class Mark{
	private char character;
	private int index;
	
	Mark(char c, int i){
		character = c;
		index = i;
	}
	
	public char getCharacter() {
		return character;
	}
	public int getIndex() {
		return index;
	}
}
