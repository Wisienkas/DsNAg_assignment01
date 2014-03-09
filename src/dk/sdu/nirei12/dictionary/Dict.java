package dk.sdu.nirei12.dictionary;

/**
 * @author nirei12 & thora12
 */
public interface Dict {
	public void insert(int k);
	public int[] orderedTraversal();
	public boolean search(int k);
}
