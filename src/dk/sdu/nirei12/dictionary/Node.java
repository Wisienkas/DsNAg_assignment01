package dk.sdu.nirei12.dictionary;

import java.security.InvalidParameterException;

/**
 * @author nirei12 & thora12
 */
public class Node {
	
	public Node left;
	public Node right;
	public Node parent;
	
	public int key;
	
	public Node(Node parent, int key){
		this.key = key;
		this.parent = parent;
	}
	
	public Node findKey(int key){
		if(key == this.key){
			return this;
		}else if(key < this.key){
			return this.left != null ? this.left.findKey(key) : null;
		}else{
			return this.right != null ? this.right.findKey(key) : null;
		}
	}
	
	public void addNode(int key){
		if(key < this.key){
			if(this.left == null){
				this.left = new Node(this, key);
			}else{
				this.left.addNode(key);
			}
		}else if(key >= this.key){
			if(this.right == null){
				this.right = new Node(this, key);
			}else{
				this.right.addNode(key);
			}
		}else{
			System.out.println("Duplicate key: " + key);
//			throw new InvalidParameterException("Duplicate key: " + key);
		}
	}
	
}
