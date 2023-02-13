package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;

@SortInfo(name = "Insertion Sort", designer = "N/A",
          link = "http://en.wikipedia.org/wiki/Insertion_sort")
public class InsertionSort extends Sort {

  int index, cmpTo;

  protected void initialize() {
    index = 1;
    cmpTo = 0;
  }

  private void swapIntoPlaceAndReset() {
    for (int i = index; i > cmpTo + 1; i--) {
      swap(i, i - 1);
    }
    index++;
    cmpTo = index - 1;
  }

  protected void nextMove() {
    if (this.compare(index, cmpTo) >= 0) {
      swapIntoPlaceAndReset();
    } else {
      cmpTo--;
      if (cmpTo == -1) {
        swapIntoPlaceAndReset();
      }
    }
  }

  protected boolean doneCheck() { return index == arrayLength(); }
}