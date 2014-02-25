package dk.sdu.nirei12.priorityqueue;

public class PQHeap implements PQ{

	private Element[] heap;
	private int currentElements;
	
	
	public PQHeap(int maxElms){
		this.heap = new Element[maxElms];
		this.currentElements = 0;
	}
	
	@Override
	public Element extractMin(){
		Element min = heap[0];
		heap[0] = heap[this.currentElements - 1];
		heap[currentElements - 1] = null;
		currentElements--;
		
		heapifyMin(0);
		
		return null;
	}

	@Override
	public void Insert(Element e) {
		if(this.currentElements < heap.length){
			heap[this.currentElements] = e;
			this.currentElements++;
			for (int i = (this.currentElements - 1) / 2; i > 0; i--) {
				heapifyMin(i);
			}
		}
	}
	
	private void heapifyMin(int i){
		int l = i*2;
		int r = i*2 + 1;
		int smallest = 0;
		if(l < this.currentElements && heap[l].key < heap[i].key){
			smallest = l;
		}else
			smallest = i;
		if(r < this.currentElements && heap[r].key < heap[smallest].key){
			smallest = r;
		}
		if(smallest != i){
			swap(smallest, i);
			heapifyMin(smallest);
		}
	}

	private void swap(int j, int i){
		Element holder = heap[i];
		heap[i] = heap[j];
		heap[j] = holder;
	}
}
