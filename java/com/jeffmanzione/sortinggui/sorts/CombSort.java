package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;

@SortInfo(name = "Comb Sort", designer = "Dobosiewicz (1980)",
          link = "http://en.wikipedia.org/wiki/Comb_sort")
public class CombSort extends Sort {
  private int index, gap;
  private static final double shrink = 1.3;

  private boolean done = false, didSwap = false;

  protected void nextMove() {

    if (this.compare(index, index + gap) > 0) {
      swap(index, index + gap);
      didSwap = true;
    }

    if ((index + gap + 1) == arrayLength()) {
      index = 0;

      int oldgap = gap;
      gap = (int)(gap / shrink);

      if (gap <= 1) {
        index = 0;
        gap = 1;
      }
      if (!didSwap && oldgap == 1) {
        done = true;
      } else {
        didSwap = false;
      }

    } else {
      index++;
    }
  }

  protected boolean doneCheck() { return done; }

  protected void initialize() {
    index = 0;
    gap = arrayLength() - 1;
  }
}
