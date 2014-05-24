package dk.sdu.nirei12.priorityqueue;

import algorithms.ArrayGenerator;
import algorithms.ArrayUtils;

/**
 * @author nirei12 & thora12
 */
public class PQHeap implements PQ {
	
	// Used to verify the heap, think of it as a unittest. Dont worry about it
	public static void main(String[] args) {
		try{
			System.out.println("Creating Random Array");
			Integer[] A = ArrayGenerator.makeRandomArray(1000000, 1, 9999999);
			System.out.println("Array Done!");
			System.out.println("Inserting into heap!");
			PQHeap pq = new PQHeap(1);
			for (Integer integer : A) {
				pq.Insert(new Element(integer, null));
			}
			if(!pq.verifyHeap(0)){
				System.out.println("NOT CORRECT HEAP!");
				System.exit(1);
			}
			System.out.println("Insertion Done!");
			Element[] heap = pq.getArray();
//			for (int i = 0; i < A.length; i++) {
//				System.out.print(heap[i].key + ", ");
//			}
			
			for (int i = 0; i < A.length; i++) {
				A[i] = pq.extractMin().key;
//				System.out.println("\nexstract: ");
				heap = pq.getArray();
//				for (int j = 0; j < A.length - i - 1; j++) {
//					System.out.print(heap[j].key + ", ");
//				}
			}
			System.out.println("Verifying Array sorted!");
			if(ArrayUtils.verifyArraySorted(A)){
				System.out.println("passed!");
				System.out.println("length of A: " + A.length);
			}else{
				System.out.println("failed!");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

    private Element[] heap;
    private int currentElements;
	private int inserts;

    public PQHeap(int maxElms) {
        this.heap = new Element[maxElms];
        this.currentElements = 0;
        this.inserts = 0;
    }

    @Override
    public Element extractMin() {
        if(this.currentElements <= 0)
        {
            return null;
        }

        Element min = heap[0];
        heap[0] = heap[this.currentElements - 1];
        heap[currentElements - 1] = null;
        currentElements--;

        heapifyMin(0);

        return min;
    }

    @Override
    public void Insert(Element e) {
    	inserts++;
        if (this.currentElements >= heap.length) {
            this.evolveHeap();
        }
        heap[this.currentElements++] = e;
        int i = this.currentElements - 1;
        int p = (i - 1) / 2;
        while(p >= 0 && heap[p].key > heap[i].key){
//        	System.out.println("parent: " + p + " val: " + heap[p].key + " higher than: "
//        			+ i + " val: " + heap[i].key + "\tInserts: " + inserts);
        	swap(p, i);
        	i = p;
        	p = (p - 1) / 2;
        }
    }

    private void heapifyMin(int i) {
        int l = (i * 2) + 1; // if 0 -> 0 * 2 + 1 = 1  || if 1 = 1 * 2 + 1 = 3
        int r = (i * 2) + 2; // if 0 -> 0 * 2 + 2 = 2  || if 1 = 1 * 2 + 2 = 4
        Element ep = heap[i];
        if(l >= currentElements){
        	return; // if its a null value on left right is too so
        }else{
        	if(r >= currentElements){ // Need only focus on l
        		if(heap[l].key < ep.key){
//        			System.out.println("swap left child!");
        			swap(l, i);
        			heapifyMin(l);
        		}
        	}else if(heap[r].key < heap[l].key){
        		if(heap[r].key < ep.key){
//        			System.out.println("swap right child!");
        			swap(r, i);
        			heapifyMin(r);
        		}
        	}else if(heap[l].key <= heap[r].key){
        		if(heap[l].key < heap[i].key){
//        			System.out.println("swap left child!");
        			swap(l, i);
        			heapifyMin(l);
        		}
        	}
        }
    }

    private void swap(int j, int i) {
        Element holder = heap[i];
//        System.out.println("swapping: " + i + " with " + j);
        heap[i] = heap[j];
        heap[j] = holder;
    }

    private void evolveHeap() {
        Element[] elements = new Element[this.currentElements * 2];
        for (int i = 0; i < this.currentElements; i++) {
            elements[i] = this.heap[i];
        }
        this.heap = elements;
    }

	@Override
	public int size() {
		return this.currentElements;
	}
	
	// used for unittest only
	public Element[] getArray(){
		return this.heap;
	}
	
	// used only for testing as well
	public boolean verifyHeap(int i){
		if(i * 2 + 1 < currentElements){
			if(heap[i * 2 + 1].key < heap[i].key){
//				System.out.println("child at index: " + (i * 2 + 1) + " val: " + heap[i * 2 + 1].key
//						+ " is less than parent: " + i + " val: " + heap[i].key);
				return false;
			}else{
				if(!verifyHeap(i * 2 + 1)){
					return false;
				}
			}
		}
		if(i * 2 + 2 < currentElements){
			if(heap[i * 2 + 2].key < heap[i].key){
//				System.out.println("child at index: " + (i * 2 + 1) + " val: " + heap[i * 2 + 1].key
//						+ " is less than parent: " + i + " val: " + heap[i].key);
				return false;
			}else{
				if(!verifyHeap(i * 2 + 2)){
					return false;
				}
			}
		}
		return true;
	}
}
