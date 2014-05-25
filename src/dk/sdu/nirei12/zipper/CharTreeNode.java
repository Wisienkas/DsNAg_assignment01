package dk.sdu.nirei12.zipper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dk.sdu.nirei12.priorityqueue.Element;

public class CharTreeNode extends Element{
	
	public CharTreeNode parent;
	public CharTreeNode left;
	public CharTreeNode right;
	public int sum;
	
	public CharTreeNode(int i, Object o) {
		super(i, o);
	}

	public static void traversal(Map result, CharTreeNode ct, String s) {
		// means its a leaf
		if(ct == null){
			System.out.println("wrong?");
			return;
		}
		if(ct.left == null && ct.right == null){
//			System.out.println("NEW KEY: " + (int)ct.data + "\tVALUE: " + s + "\tCount: " + ct.key);
			Encode.size += (s.length() * ct.key);
			result.put(ct.data, StringToBits.toBit(s));
		}else if(ct.left == null || ct.right == null){
			System.err.println("one null and not the other?!");
			System.exit(1);
		}else{
			String sl = s + "0";
			String sr = s + "1";
			traversal(result, ct.left, sl);
			traversal(result, ct.right, sr);
		}
	}
	
}
