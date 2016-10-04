package algorithmicToolbox.week3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class CoveringSegmentsByPoints {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int numSegments = in.nextInt();
		Segment[] segmentArray = new Segment[numSegments];
		
		for(int i=0;i<numSegments;i++){
			int left = in.nextInt();
			int right = in.nextInt();
			
			segmentArray[i] = new Segment(left,right);
		}
		
		Arrays.sort(segmentArray);
		
		HashSet<Integer> rightPointSet = new HashSet<Integer>();
		for (int i=0;i<segmentArray.length;i++) {
			Segment currentSegment = segmentArray[i];
			
			boolean segmentCovers = false;
			for (Integer rightNumber : rightPointSet) {
				if(currentSegment.isSegmentCoverPoint(rightNumber)){
					segmentCovers = true;
					break;
				}
			}
			
			if(segmentCovers == false)
				rightPointSet.add( currentSegment.getRightPoint() );
			
		}
		
		System.out.println(rightPointSet.size());
		for (Integer integer : rightPointSet) {
			System.out.print(integer + " ");
		}
		
	}

}

class Segment implements Comparable<Segment>{
	
	private int leftPoint;
	private int rightPoint;
	
	Segment(int leftPoint, int rightPoint){
		this.leftPoint = leftPoint;
		this.rightPoint = rightPoint;
	}
	
	public int getLeftPoint(){
		return leftPoint;
	}
	
	public int getRightPoint(){
		return rightPoint;
	}

	public boolean isSegmentCoverPoint(int rightPoint){
		if(getLeftPoint() <= rightPoint && getRightPoint() >= rightPoint)
			return true;
		
		return false;
	}
	
	@Override
	public int compareTo(Segment otherSegment) {
		if(rightPoint > otherSegment.getRightPoint())
			return 1;
		else if(rightPoint < otherSegment.getRightPoint())
			return -1;
		else
			return 0;
	}
	
}
