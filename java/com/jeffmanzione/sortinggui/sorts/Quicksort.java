package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;
import java.util.EmptyStackException;
import java.util.Stack;

@SortInfo(name = "Quicksort (End Pivot)", designer = "Hoare (1961)",
          link = "http://en.wikipedia.org/wiki/Quicksort")
public class Quicksort extends Sort {

  private Stack<Range> ranges;

  private Range range;

  int i, j, p;

  private boolean sorting, done;

  private class Range {
    final int start, end;

    public Range(int start, int end) {
      this.start = start;
      this.end = end;
    }
  }

  protected void initialize() {
    ranges = new Stack<Range>();
    ranges.push(new Range(0, arrayLength() - 1));
    sorting = done = false;
  }

  protected void nextMove() {
    if (!sorting) {
      try {
        range = ranges.pop();

        i = range.start - 1;
        j = range.start;
        p = range.end;
        sorting = true;
      } catch (EmptyStackException e) {
        done = true;
        return;
      }
    }

    if (j == p) {
      swap(p, i + 1);
      if (range.end - (i + 2) > 0) {
        ranges.push(new Range(i + 2, range.end));
      }
      if (i - range.start > 0) {
        ranges.push(new Range(range.start, i));
      }

      sorting = false;
      nextMove();
    } else {

      if (this.compare(j, p) < 0) {
        swap(j, i + 1);
        i++;
      }

      j++;
    }
  }

  protected boolean doneCheck() { return done; }
}
