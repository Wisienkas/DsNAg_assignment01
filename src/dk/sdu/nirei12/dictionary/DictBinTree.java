package dk.sdu.nirei12.dictionary;

import java.util.ArrayList;

/**
 * @author nirei12 & thora12
 */
public class DictBinTree implements Dict {
	
	private Node root;
	
	public DictBinTree(){
		this.root = null;
	}
	
	@Override
	public void insert(int k) {
		if(this.root == null){
			this.root = new Node(null, k);
		}else{
			this.root.addNode(k);
		}
	}

	@Override
	public int[] orderedTraversal() {
		ArrayList<Integer> list = new ArrayList<>();
		inorderTreeWalk(this.root, list);
		
		int[] new_list = new int[list.size()];
		for(int i = 0; i < new_list.length; i++){
			new_list[i] = list.get(i);
		}
		return new_list;
	}
	
	private void inorderTreeWalk(Node x, ArrayList<Integer> list){
		if(x != null){
			inorderTreeWalk(x.left, list);
			list.add(x.key);
			inorderTreeWalk(x.right, list);
		}
	}

	@Override
	public boolean search(int k) {
		if(this.root == null){
			return false;
		}
		return this.root.findKey(k) != null; 
	}

}
