package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;
import java.util.EmptyStackException;
import java.util.Stack;

@SortInfo(name = "Stooge Sort", designer = "N/A",
          link = "http://en.wikipedia.org/wiki/Stooge_sort")
public class StoogeSort extends Sort {

  private Stack<Range> ranges;

  private Range range;

  int i, j;

  private boolean done;

  private class Range {
    final int start, end;

    public Range(int start, int end) {
      this.start = start;
      this.end = end;
    }

    public String toString() { return "(" + start + "," + end + ")"; }
  }

  @Override
  protected void initialize() {
    ranges = new Stack<Range>();
    ranges.push(new Range(0, arrayLength() - 1));
  }

  @Override
  protected void nextMove() {
    try {
      range = ranges.pop();

      i = range.start;
      j = range.end;

    } catch (EmptyStackException e) {
      done = true;
      return;
    }

    if (this.compare(i, j) > 0) {
      this.swap(i, j);
    }

    if (i + 1 < j) {
      int t = (j - i + 1) / 3;
      ranges.push(new Range(i, j - t));
      ranges.push(new Range(i + t, j));
      ranges.push(new Range(i, j - t));
    }
  }

  @Override
  protected boolean doneCheck() {
    return done;
  }
}
