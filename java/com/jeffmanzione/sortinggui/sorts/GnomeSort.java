package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;

@SortInfo(name = "Gnome Sort", designer = "Sarbazi-Azad (2000)",
          link = "http://en.wikipedia.org/wiki/Gnome_sort")
public class GnomeSort extends Sort {

  int index, start;

  protected void initialize() { index = start = 1; }

  protected void nextMove() {
    if (this.compare(index, index - 1) >= 0) {
      start++;
      index = start;
    } else {
      swap(index, index - 1);
      index--;

      if (index == 0) {
        start++;
        index = start;
      }
    }
  }

  protected boolean doneCheck() { return index == arrayLength(); }
}