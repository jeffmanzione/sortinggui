package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;

@SortInfo(name = "Selection Sort", designer = "N/A",
          link = "http://en.wikipedia.org/wiki/Selection_Sort")
public class SelectionSort extends Sort {
  private int indexOfMin;
  private int start, index;

  protected void initialize() {
    start = 0;
    indexOfMin = 0;
    index = 1;
  }

  protected void nextMove() {
    if (this.compare(index, indexOfMin) < 0) {
      indexOfMin = index;
    }

    index++;

    if (index == arrayLength()) {
      index = start + 2;
      swap(start, indexOfMin);
      start++;
      indexOfMin = start;
    }
  }

  protected boolean doneCheck() { return start == arrayLength() - 1; }

  @Override
  public String toString() {
    return "Selection Sort";
  }
}
