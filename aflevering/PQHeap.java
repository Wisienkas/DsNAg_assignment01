/**
 * @author nirei12 & thora12
 */
public class PQHeap implements PQ {

    private Element[] heap;
    private int currentElements;


    public PQHeap(int maxElms) {
        this.heap = new Element[maxElms];
        this.currentElements = 0;
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
        if (this.currentElements >= heap.length) {
            this.evolveHeap();
        }

        heap[this.currentElements] = e;
        this.currentElements++;
        int elementIndex = (this.currentElements - 1);
        int parentIndex = (this.currentElements - 1) / 2;
        while (elementIndex > 0 && e.key < heap[parentIndex].key) {
            swap(elementIndex, parentIndex);
            elementIndex = parentIndex;
            parentIndex = (elementIndex - 1) / 2;
        }
    }

    private void heapifyMin(int i) {
        int l = i * 2 + 1;
        int r = i * 2 + 2;
        int smallest = 0;
        if (l < this.currentElements && heap[l].key < heap[i].key) {
            smallest = l;
        } else {
            smallest = i;
        }
        if (r < this.currentElements && heap[r].key < heap[smallest].key) {
            smallest = r;
        }
        if (smallest != i) {
            swap(smallest, i);
            heapifyMin(smallest);
        }
    }

    private void swap(int j, int i) {
        Element holder = heap[i];
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
}
