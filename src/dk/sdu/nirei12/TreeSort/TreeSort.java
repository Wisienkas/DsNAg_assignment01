package dk.sdu.nirei12.TreeSort;

import java.util.Scanner;

import dk.sdu.nirei12.dictionary.Dict;
import dk.sdu.nirei12.dictionary.DictBinTree;

/**
 * 
 * @author nirei12 & thora12
 *
 */
public class TreeSort {
	
	public static void main(String[] args) {
		
		Dict dickTreeSort = new DictBinTree();
		
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int nextInt = scanner.nextInt();
            dickTreeSort.insert(nextInt);
        }
        scanner.close();
        for (int i : dickTreeSort.orderedTraversal()) {
			System.out.println(i);
		}
		
        System.out.println("Searching 73: " + dickTreeSort.search(73));
        System.out.println("Searching 82: " + dickTreeSort.search(82));
	}
	
}
