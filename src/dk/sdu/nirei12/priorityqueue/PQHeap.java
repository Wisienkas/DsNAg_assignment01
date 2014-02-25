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
		heap[0] = heap[this.currentElements];
		heap[currentElements] = null;
		currentElements--;
		
		heapifyMin(0);
		
		return null;
	}

	@Override
	public void Insert(Element e) {
		
	}
	
	private int heapifyMin(int i){
		int childs = getChilds(i);
		int index = i;
		switch (childs) {
		case 2:
			
			break;

		default:
			break;
		}
		return 0;
	}

	private int getChilds(int i) {
		if(i*2 <= this.currentElements){
			if(i*2 + 1 <= this.currentElements){
				return 2;
			}
			return 1;
		}
		return 0;
	}

}
