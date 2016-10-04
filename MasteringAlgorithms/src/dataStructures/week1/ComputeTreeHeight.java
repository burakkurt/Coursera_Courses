package dataStructures.week1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ComputeTreeHeight {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner in = new Scanner(file);
		int size = in.nextInt();
		int[] numberArr = new int[size];
		for(int i=0;i<numberArr.length;i++)
			numberArr[i] = in.nextInt();
		
		Node[] nodeArr = new Node[size];
		for(int i=0;i<nodeArr.length;i++)
			nodeArr[i] = new Node(i);
		
		Node root = null;
		for(int i=0;i<numberArr.length;i++){
			if(numberArr[i] == -1){
				root = nodeArr[i];
				root.setHeight(1);
				continue;
			}
			else
				nodeArr[numberArr[i]].addChild(nodeArr[i]);	
		}
		
		System.out.println(computeTree(root));
	}
	
	public static int computeTree(Node root){
		int maxHeight = root.getHeight();
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addLast(root);
		
		while(queue.size() > 0){
			Node curr = queue.removeFirst();
			
			for(Node node:curr.getChildrenList()){
				node.setHeight(curr.getHeight() + 1);
				queue.add(node);
				
				if(node.getHeight() > maxHeight)
					maxHeight = node.getHeight();
			}
		}
		
		return maxHeight;
	}

}
class Node{
	int id;
	ArrayList<Node> childrenList = new ArrayList<Node>();
	int height;
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	Node(int id){
		this.id = id;
	}
	
	public void addChild(Node child){
		childrenList.add(child);
	}
	
	public List<Node> getChildrenList(){
		return childrenList;
	}
	
	public String toString(){
		return "id : " + id;
	}
}
