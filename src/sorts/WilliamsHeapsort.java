package sorts;

import sorts.business.SortInfo;

@SortInfo(name = "Williams' Heapsort", designer = "Williams (1964)",
    link = "http://en.wikipedia.org/wiki/Heapsort")
public class WilliamsHeapsort extends Heapsort {

  // protected void nextMove() {
  // if (buildMaxHeap || heapify) {
  // if (hasLeftChild(curr_index)) {
  // int leftChild = leftChild(curr_index);
  //
  // if (hasRightChild(curr_index)) {
  // int rightChild = rightChild(curr_index);
  //
  // switch (this.compare(curr_index, leftChild, rightChild)) {
  // case 0:
  // doneSift();
  // break;
  // case -1:
  // swap(leftChild, curr_index);
  // curr_index = leftChild;
  // break;
  //
  // case 1:
  // swap(rightChild, curr_index);
  // curr_index = rightChild;
  // break;
  // }
  // } else {
  // if (this.compare(leftChild, curr_index) > 0) {
  // swap(leftChild, curr_index);
  // }
  // curr_index = leftChild;
  // doneSift();
  // }
  // } else {
  // doneSift();
  // }
  //
  // } else {
  // swap(0, heapSize - 1);
  // heapSize--;
  // heapify = true;
  //
  // }
  // try {
  // Thread.sleep(10);
  // } catch (InterruptedException e) {
  // e.printStackTrace();
  // }
  // }

  boolean compared = false, isLeft = false;

  protected void nextMove() {
    if (buildMaxHeap || heapify) {
      if (hasLeftChild(curr_index)) {
        int leftChild = leftChild(curr_index);

        if (hasRightChild(curr_index)) {
          int rightChild = rightChild(curr_index);

          if (compared) {
            if (isLeft) {
              if (this.compare(leftChild, curr_index) > 0) {
                swap(leftChild, curr_index);
                curr_index = leftChild;
              } else {
                doneSift();
              }
            } else {
              if (this.compare(rightChild, curr_index) > 0) {
                swap(rightChild, curr_index);
                curr_index = rightChild;
              } else {
                doneSift();
              }
            }
            compared = false;
            isLeft = false;
          } else {
            compared = true;
            isLeft = this.compare(leftChild, rightChild) > 0;
          }

          // switch (this.compare(curr_index, leftChild, rightChild)) {
          // case 0:
          // doneSift();
          // break;
          // case -1:
          // swap(leftChild, curr_index);
          // curr_index = leftChild;
          // break;
          //
          // case 1:
          // swap(rightChild, curr_index);
          // curr_index = rightChild;
          // break;
          // }
        } else {
          if (this.compare(leftChild, curr_index) > 0) {
            swap(leftChild, curr_index);
          }
          curr_index = leftChild;
          doneSift();
        }
      } else {
        doneSift();
      }

    } else {
      swap(0, heapSize - 1);
      heapSize--;
      heapify = true;

    }
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  // public static void main(String[] args) {
  // // int heapSize = 8;
  // // System.out.println((int) Math.pow(2, (int) (Math.log10(heapSize) / Math.log10(2)) - 1) - 1 +
  // (heapSize -
  // // (int) Math.pow(2, (int) (Math.log10(heapSize) / Math.log10(2))) + 1 + 1)/2);
  //
  // WilliamsHeapsort whs = new WilliamsHeapsort();
  // whs.initialize(new int[] { 4, 7, 6, 9, 6, 5 });
  //
  // try {
  // do {
  // System.out.println(whs.getArrayToString());
  // System.out.println("Comps: " + whs.getComparisons() + " Swaps: " + whs.getSwaps());
  // // Thread.sleep(500);
  // } while (!whs.next());
  //
  // } catch (SortCompletedException e) {
  // e.printStackTrace();
  // }
  // }

}
