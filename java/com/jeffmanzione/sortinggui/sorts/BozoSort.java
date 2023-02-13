package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;
import java.util.Random;

@SortInfo(name = "Bozo Sort", designer = "N/A",
          link = "http://en.wikipedia.org/wiki/Bogosort")
public class BozoSort extends Sort {

  int index;

  protected void initialize() { index = 0; }

  protected void nextMove() {
    if (this.compare(index, index + 1) > 0) {
      Random rand = new Random();
      int indexA = rand.nextInt(arrayLength());
      int indexB = rand.nextInt(arrayLength());
      swap(indexA, indexB);
      index = 0;
    } else {
      index++;
    }
  }

  protected boolean doneCheck() { return index == arrayLength() - 1; }
}
