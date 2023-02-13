package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;

@SortInfo(name = "Williams' Heapsort", designer = "Williams (1964)",
          link = "http://en.wikipedia.org/wiki/Heapsort")
public class WilliamsHeapsort extends Heapsort {
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
}
