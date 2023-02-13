package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

@SortInfo(name = "Quicksort (Random)", designer = "Hoare (1961)",
          link = "http://en.wikipedia.org/wiki/Quicksort")
public class QuicksortRandom extends Sort {
  private Stack<Range> ranges;
  private Range range;
  int left, right, pivot;
  private Random rand = new Random();
  private boolean sorting, done;

  private class Range {
    final int start, end;

    public Range(int start, int end) {
      this.start = start;
      this.end = end;
    }

    public String toString() { return "(" + start + "," + end + ")"; }
  }

  protected void initialize() {
    ranges = new Stack<Range>();
    ranges.push(new Range(0, arrayLength() - 1));
    sorting = done = false;
  }

  private boolean incLeft, incRight;

  protected void nextMove() {
    if (!sorting) {

      try {
        incLeft = true;
        incRight = false;
        range = ranges.pop();

        left = range.start;
        right = range.end;

        pivot = selectPivot();
        sorting = true;
      } catch (EmptyStackException e) {
        done = true;
        return;
      }
    }

    if (left <= right) {
      if (incLeft) {
        if (this.compare(left, pivot) < 0) {
          left++;
        } else {
          incRight = true;
          incLeft = false;
          return;
        }
      }

      if (incRight) {
        if (this.compare(right, pivot) > 0) {
          right--;
        } else {
          incRight = false;
        }
      }

      if (!incLeft && !incRight) {
        if (left <= right) {
          this.swap(left, right);
          if (left == pivot)
            pivot = right;
          else if (right == pivot)
            pivot = left;

          left++;
          right--;
        }
        incLeft = true;
      }

    } else {
      if (range.end - left > 0) {
        ranges.push(new Range(left, range.end));
      }
      if (right - range.start > 0) {
        ranges.push(new Range(range.start, right));
      }

      sorting = false;
      nextMove();
    }
  }

  private int selectPivot() { return rand.nextInt(right - left) + left; }

  protected boolean doneCheck() { return done; }
}
