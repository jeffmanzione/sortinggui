package sorts;

public abstract class Heapsort extends Sort {

  protected int heapSize;

  protected boolean buildMaxHeap, heapify;

  protected int index, curr_index;

  protected void initialize() {
    heapSize = arrayLength();
    buildMaxHeap = true;
    heapify = false;
    index = curr_index = (int) Math.pow(2, (int) (Math.log10(heapSize) / Math.log10(2)) - 1) - 1
        + (heapSize - (int) Math.pow(2, (int) (Math.log10(heapSize) / Math.log10(2))) + 1 + 1) / 2;
    System.out.println(index);
  }

  protected int parent(int index) {
    return (int) (index - 1) / 2;
  }

  protected int leftChild(int index) {
    return index * 2 + 1;
  }

  protected int rightChild(int index) {
    return index * 2 + 2;
  }

  protected boolean hasLeftChild(int index) {
    return leftChild(index) < heapSize;
  }

  protected boolean hasRightChild(int index) {
    return rightChild(index) < heapSize;
  }

  protected boolean hasParent(int index) {
    return index != 0;
  }

  protected void doneSift() {
    if (heapify) {
      heapify = false;
      curr_index = index = 0;
    } else {
      index--;
      curr_index = index;

      if (index == -1) {
        buildMaxHeap = false;
        index = curr_index = 0;
      }
    }
  }

  protected boolean doneCheck() {
    return heapSize == 1;
  }

}
