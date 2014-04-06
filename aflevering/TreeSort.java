import java.util.Scanner;

/**
 * 
 * @author nirei12 & thora12
 *
 */
public class TreeSort {
	
	public static void main(String[] args) {
		
		Dict dickTreeSort = new DictBinTree();
		
        Scanner scanner = new Scanner(System.in);
	System.out.println("=====");
        while (scanner.hasNextInt()) {
            int nextInt = scanner.nextInt();
            dickTreeSort.insert(nextInt);
        }
        scanner.close();
        for (int i : dickTreeSort.orderedTraversal()) {
			System.out.println(i);
		}
	}
	
}
