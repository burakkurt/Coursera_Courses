package spelling;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		boolean inserted = false;
		if(word == null || isWord(word.toLowerCase()) == true)
			return false;
		else{
			TrieNode curr = root;
			TrieNode next = null;
			for(char c:word.toLowerCase().toCharArray()){
				next = curr.getChild(c);
				if(next == null){
					next = curr.insert(c);
					inserted = true;
				}
				curr = next;
			}
			
			if(curr.endsWord() == false){
				curr.setEndsWord(true);
				inserted = true;
			}
			if(inserted)
				size++;
			
			return inserted;
		}
		
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) {
		TrieNode curr = root;
		for (char c : s.toLowerCase().toCharArray()) {
			curr = curr.getChild(c);
			// if next doesn't exist, return false
			if (curr == null)
				return false;
		}
		// we're at a valid node, but may not be valid
		return curr.endsWord();
	}
	
	public static void main(String[] args){
		AutoCompleteDictionaryTrie a = new AutoCompleteDictionaryTrie();
//		a.addWord("sss");
//		a.addWord("aaa");
//		a.addWord("ddd");
//		a.addWord("eee");
//		a.addWord("ccc");
		a.addWord("hellow");
		a.addWord("hellowl");
		a.addWord("helloe");
		a.addWord("hellor");
		a.addWord("hellot");
		a.addWord("helloy");
		a.addWord("pencil");
		a.addWord("z");
		
		System.out.println();
		
		//root
//		System.out.println("Root");
//		for (Character c : a.root.getValidNextCharacters()) {
// 			System.out.println(c);
// 		}
		
		a.predictCompletions("hello", 4);
		a.predictCompletions("pen", 2);
		a.predictCompletions("e", 2);
	
		//a.printTree();
		
	}
	

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 char[] charArray = prefix.toLowerCase().toCharArray();
    	 List<String> completionsList = new ArrayList<String>();
    	 LinkedList<TrieNode> queue = new LinkedList<TrieNode>();
    	 TrieNode curr = root;
    	 TrieNode next = null;
    	 
    	 for(char c:charArray){
    		 next = curr.getChild(c);

    		 if(next == null)
    			 return completionsList;
//    		 else
//    			 System.out.println(next.getText());
    		 
    		 curr = next;
    	 }
    	 
    	 queue.addLast(curr);
    	 
    	 TrieNode removedNode;
    	 int numberOfCompletions = 0;
    	 while(!queue.isEmpty()){
    		 removedNode = queue.removeFirst();
    		 if(removedNode.endsWord() && numberOfCompletions<numCompletions){
    			 completionsList.add(removedNode.getText().toString());
    			 numberOfCompletions++;
    		 }
    			 
    		 
    		 for(char c:removedNode.getValidNextCharacters()){
        		 next = removedNode.getChild(c); 
        		 
        		 if(next != null){
        			 queue.addLast(next);
        			 //System.out.println(next.getText());
    		 	 }
        		 
    		 }
    	 }
    	 
    	 
    	 System.out.println("completionsList :");
    	 for(int i=0;i<completionsList.size();i++){
    		 System.out.println(completionsList.get(i));
    	 }
    	 
         return completionsList;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}