package algorithmicToolbox.week5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class PointsAndSegments{
	
	//ÇÖZÜLEMEDİ

	public static void main(String[] args) throws FileNotFoundException{
		File file = new File("input.txt");
		Scanner in = new Scanner(file);
		int segmentSize = in.nextInt();
		int pointSize = in.nextInt();
		
		Segment[] segmentArr = new Segment[segmentSize];
		for(int i=0;i<segmentSize;i++)
			segmentArr[i] = new Segment(in.nextInt(), in.nextInt());
		Arrays.sort(segmentArr);
		
		int[] pointArr = new int[pointSize];
		for(int n=0;n<pointSize;n++)
			pointArr[n] = in.nextInt();
		
		int[] resultArr = new int[pointSize];
		
		for(Segment segment: segmentArr){
			for(int k=0;k<pointArr.length;k++){
				if(segment.isSegmentCoverPoint(pointArr[k]))
					resultArr[k]++;
			}
		}
		
		for (Segment s : segmentArr) {
			System.out.println(s);
		}
		System.out.println();
		for (int i : pointArr) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		for (int i : resultArr) {
			System.out.print(i + " ");
		}
		
	}
	

}

class Segment implements Comparable<Segment>{
	int lowerBound;
	int upperBound;
	
	Segment(int lowerBound, int upperBound){
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public int getlowerBound() {
		return lowerBound;
	}

	public int getupperBound() {
		return upperBound;
	}
	
	public boolean isSegmentCoverPoint(int point){
		if(lowerBound <= point && point <= upperBound)
			return true;
		
		return false;
	}
	
	@Override
	public int compareTo(Segment otherSegment) {
		if(upperBound > otherSegment.getupperBound())
			return 1;
		else if(upperBound < otherSegment.getupperBound())
			return -1;
		else
			return 0;
	}
	
	public String toString(){
		return "lowerBound : " + lowerBound + " upperBound : " + upperBound;
	}

	
	
}
