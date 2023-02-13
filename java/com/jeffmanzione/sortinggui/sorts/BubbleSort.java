package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;

@SortInfo(name = "Bubble Sort", designer = "Knuth (1956)?",
          link = "http://en.wikipedia.org/wiki/Bubble_sort")
public class BubbleSort extends Sort {

  private int end, index, numSwaps, lastSwapIndex;

  private boolean done = false;

  protected void nextMove() {
    if (this.compare(index, index + 1) > 0) {
      swap(index, index + 1);
      lastSwapIndex = index;
      numSwaps++;
    }

    if (index + 1 == end) {
      end = lastSwapIndex;
      index = 0;
      if (lastSwapIndex == 0 || numSwaps < 1) {
        done = true;
      }

      numSwaps = 0;
    } else {
      index++;
    }
  }

  protected boolean doneCheck() { return done; }

  protected void initialize() {
    end = arrayLength() - 1;
    index = 0;
  }
}
