package algorithmicToolbox.week1;

import java.util.*;
import java.io.*;

public class MaxPairwiseProduct {
	
    static long getMaxPairwiseProduct(int[] numbers) {
        long result = 0;
        int n = numbers.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if ((long)numbers[i] * (long)numbers[j] > result) {
                    result = (long)numbers[i] * (long)numbers[j];
                }
            }
        }
        return result;
    }
    
    static long getMaxPairwiseProductFaster(int[] numbers){
    	
    	if(numbers.length == 0)
    		return 0;
    	else if(numbers.length == 1)
    		return numbers[0];
    	
    	int indMaxNum1 = -1;
    	for(int i=0;i<numbers.length;i++){
    		if(indMaxNum1 == -1 || numbers[i] > numbers[indMaxNum1])
    			indMaxNum1 = i;
    	}
    	
    	int indMaxNum2 = -1;
    	for(int n=0;n<numbers.length;n++){
    		if(n != indMaxNum1 && (indMaxNum2 == -1 || numbers[n] > numbers[indMaxNum2]))
    			indMaxNum2 = n;
    	}
    	
    	return (long)numbers[indMaxNum1]*numbers[indMaxNum2];
    }

    public static void main(String[] args) {
    	
    	/*
    	while(true){
    		Random random = new Random();
    		int[] randomInt = new int[10];
    		for(int i=0;i<10;i++)
    			randomInt[i] = random.nextInt(10000000)+2;
    		
    		for (int i : randomInt) {
				System.out.print(i + " ");
			}
    		
    		long oldInt = getMaxPairwiseProduct(randomInt);
    		long newInt = getMaxPairwiseProductFaster(randomInt);
    		
    		if( getMaxPairwiseProduct(randomInt) == getMaxPairwiseProductFaster(randomInt) ){
        		System.out.println("OK");
    		}else{
    			System.out.println("****** " + oldInt +" - "+ newInt +" MATCH NOT FOUND");
    		}
    		
    	}
    	*/
    	
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }
        System.out.println(getMaxPairwiseProductFaster(numbers));
    	
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

}