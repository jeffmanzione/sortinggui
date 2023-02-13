package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;

@SortInfo(name = "Floyd's Heapsort", designer = "Floyd (1964)",
          link = "http://en.wikipedia.org/wiki/Heapsort")
public class FloydsHeapsort extends Heapsort {

  private boolean siftUp = false, siftDown = true;

  protected void doneSift() {

    if (siftDown) {
      siftUp = true;
      siftDown = false;

    } else if (siftUp) {
      siftUp = false;
      siftDown = true;

      if (heapify) {
        heapify = false;
        curr_index = 0;
      }
    }

    if (buildMaxHeap) {
      if (siftDown) {
        index--;
        curr_index = index;
      }

      if (index == -1) {
        buildMaxHeap = false;
        index = curr_index = 0;
      }
    }
  }

  protected void nextMove() {
    if (buildMaxHeap || heapify) {
      if (siftDown) {
        if (hasLeftChild(curr_index)) {
          int leftChild = leftChild(curr_index);

          if (hasRightChild(curr_index)) {
            int rightChild = rightChild(curr_index);

            if (this.compare(leftChild, rightChild) > 0) {
              swap(leftChild, curr_index);
              curr_index = leftChild;
            } else {
              swap(rightChild, curr_index);
              curr_index = rightChild;
            }

          } else {
            swap(leftChild, curr_index);
            curr_index = leftChild;
            doneSift();
          }
        } else {
          doneSift();
        }
      } else if (siftUp) {
        if (hasParent(curr_index) && (heapify || parent(curr_index) >= index)) {
          int parent = parent(curr_index);

          if (this.compare(curr_index, parent) > 0) {
            swap(curr_index, parent);
            curr_index = parent;
          } else {
            doneSift();
          }

        } else {
          doneSift();
        }
      }
    } else {
      swap(0, heapSize - 1);
      heapSize--;
      heapify = true;
    }
  }
}
