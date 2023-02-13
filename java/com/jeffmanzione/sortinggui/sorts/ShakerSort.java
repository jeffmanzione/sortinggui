package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;

@SortInfo(name = "Shaker (Selection) Sort", designer = "N/A",
          link = "http://en.wikipedia.org/wiki/Selection_Sort")
public class ShakerSort extends Sort {
  private int indexOfMin, indexOfMax;
  private int start, index, end;
  private boolean right = true;

  protected void initialize() {
    start = 0;
    indexOfMin = 0;
    indexOfMax = arrayLength() - 1;
    index = 0;
    end = arrayLength() - 1;
  }

  protected void nextMove() {
    if (right && index < end) {
      if (this.compare(index, indexOfMax) > 0) {
        indexOfMax = index;
        index++;
      } else {
        right = false;
      }
    } else {
      if (this.compare(index, indexOfMin) < 0) {
        indexOfMin = index;
      }
      index++;

      if (index >= end + 1) {
        if (start == indexOfMax && end == indexOfMin) {
          swap(indexOfMin, indexOfMax);
        } else if (start == indexOfMax) {
          swap(end, indexOfMax);
          swap(start, indexOfMin);
        } else {
          swap(start, indexOfMin);
          swap(end, indexOfMax);
        }
        end--;
        start++;
        index = start;
        indexOfMin = start;
        indexOfMax = end;
      }
      right = true;
    }
  }

  protected boolean doneCheck() { return end - start < 1; }

  @Override
  public String toString() {
    return "Selection Sort";
  }
}
