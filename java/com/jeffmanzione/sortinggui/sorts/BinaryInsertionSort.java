package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;

@SortInfo(name = "Binary Search Insertion Sort", designer = "N/A",
          link = "http://en.wikipedia.org/wiki/Insertion_sort")
public class BinaryInsertionSort extends Sort {

  int index, rangeStart, rangeEnd, mid;

  boolean beginRound = true;

  protected void initialize() {
    index = 1;
    rangeStart = 0;
    rangeEnd = 1;
  }

  protected void nextMove() {
    // If we are just beginning our binary search
    if (beginRound) {
      beginRound = false;
      if (this.compare(index, index - 1) >= 0) {
        index++;
        rangeEnd = index;
        beginRound = true;
      }
    } else {
      mid = (rangeStart + rangeEnd) / 2;
      if (this.compare(index, mid) >= 0) {
        rangeStart = mid + 1;
      } else {
        rangeEnd = mid;
      }

      if (rangeStart >= rangeEnd) {
        for (int i = index; i > rangeEnd; i--) {
          swap(i, i - 1);
        }
        index++;
        rangeStart = 0;
        rangeEnd = index;
        beginRound = true;
      }
    }
  }

  protected boolean doneCheck() { return index == arrayLength(); }
}