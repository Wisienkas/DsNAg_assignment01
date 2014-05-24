package dk.sdu.nirei12.zipper;

import java.util.Map;

import dk.sdu.nirei12.priorityqueue.PQ;
import dk.sdu.nirei12.priorityqueue.PQHeap;

public class CharCountHeap {
	
	int[] chars = new int[256];
	int lowest = 0;
	
	public CharCountHeap(){
		
	}
	
	public void addChar(int charIndex){
		chars[charIndex]++;
	}
	
	public void addChars(int charindex, int count){
		chars[charindex] += count;
	}
	
	public CharTreeNode processOptimizedTree(){
		// Reuse of assignment 1's heap
		PQ pq = new PQHeap(chars.length);
		for (int i = 0; i < chars.length ; i++) {
			// No need to process something with no occourence 
			if(chars[i] == 0){
				continue;
			}
			CharTreeNode ctn = new CharTreeNode(chars[i], i); // key is count, value is char_value in utf-8
			ctn.sum = chars[i];
			
			pq.Insert(ctn);
		}
		while(pq.size() > 1){
			optimizeHeap(pq);
		}
		return (CharTreeNode) pq.extractMin();
	}

	public void makeMap(CharTreeNode ctn, Map map) {
		if(ctn.left != null && ctn.right != null && map != null){
			String s = "";
			if(!s.isEmpty()){
				System.err.println("String should be empty");
				System.exit(1);
			}
			CharTreeNode.traversal(map, ctn, s);
		}
	}
	
	private void optimizeHeap(PQ pq) {
		CharTreeNode ctn_1 = (CharTreeNode) pq.extractMin();
		CharTreeNode ctn_2 = (CharTreeNode) pq.extractMin();
		if(lowest > ctn_1.key || lowest > ctn_2.key){
			System.err.println("lower than previous??");
			System.exit(1);
		}
		lowest = ctn_1.key > ctn_2.key ? ctn_1.key : ctn_2.key;
		int sum = ctn_1.key + ctn_2.key;
		CharTreeNode knot = new CharTreeNode(sum, null);
		
		if(ctn_1.key <= ctn_2.key){
			knot.left = ctn_1;
			knot.right = ctn_2;
		}else{
			knot.left = ctn_2;
			knot.right = ctn_1;
		}
		
		pq.Insert(knot);
	}
}
