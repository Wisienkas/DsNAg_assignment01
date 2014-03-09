package dk.sdu.nirei12.Heapsort;

import dk.sdu.nirei12.priorityqueue.Element;
import dk.sdu.nirei12.priorityqueue.PQ;
import dk.sdu.nirei12.priorityqueue.PQHeap;

import java.util.Scanner;

/**
 * @author nirei12 & thora12
 */
public class Heapsort {
    public static void main(String[] args) {
        System.out.println("Heapsort\n");
        PQ heapSort = new PQHeap(10);

        Scanner scanner = new Scanner(args[0]);
        while (scanner.hasNextInt()) {
            int nextInt = scanner.nextInt();
            heapSort.Insert(new Element(nextInt, null));
        }
        scanner.close();

        Element currentElement = null;
        while ((currentElement = heapSort.extractMin()) != null) {
            System.out.println(currentElement.key);
        }
    }
}
