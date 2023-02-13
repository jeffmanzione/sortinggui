package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

@SortInfo(name = "Quicksort (Best of 3, Random)", designer = "Hoare (1961)",
          link = "http://en.wikipedia.org/wiki/Quicksort")
public class QuicksortBestOfThree extends Sort {

  private static final int NUM_PROBES = 3;
  private static final int MIN_THRESHOLD = 10;

  private Stack<Range> ranges;

  private Range range;

  private int left, right, pivot;
  private int[] pivots;

  private enum PivotSelection { NO, FIRST, SECOND, THIRD }

  private int minIndex, maxIndex, toCompare;

  private PivotSelection pivotSelection = PivotSelection.NO;

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

      if (pivotSelection == PivotSelection.NO) {
        try {
          incLeft = true;
          incRight = false;
          range = ranges.pop();

          left = range.start;
          right = range.end;

          if (right - left < MIN_THRESHOLD) {
            pivot = selectRandomPivot();
            sorting = true;
          } else {

            pivots = selectPivot();
            pivotSelection = PivotSelection.FIRST;
            System.out.println("Candidates in [" + left + "," + right +
                               "]: " + (pivots[0] + left) + "(" +
                               this.valueOf(pivots[0] + left) + ") " +
                               (pivots[1] + left) + "(" +
                               this.valueOf(pivots[1] + left) + ") " +
                               (pivots[2] + left) + "(" +
                               this.valueOf(pivots[2] + left) + ")");
          }

        } catch (EmptyStackException e) {
          done = true;
          return;
        }
      }

      switch (pivotSelection) {
      case FIRST:
        if (this.compare(pivots[0] + left, pivots[1] + left) > 0) {
          minIndex = pivots[1] + left;
          maxIndex = pivots[0] + left;
        } else {
          minIndex = pivots[0] + left;
          maxIndex = pivots[1] + left;
        }
        toCompare = pivots[2] + left;
        pivotSelection = PivotSelection.SECOND;
        return;
      case SECOND:
        if (this.compare(minIndex, toCompare) > 0) {
          pivot = minIndex;
          pivotSelection = PivotSelection.NO;
          sorting = true;
        } else {
          pivotSelection = PivotSelection.THIRD;
        }
        return;
      case THIRD:
        if (this.compare(toCompare, maxIndex) > 0) {
          pivot = maxIndex;
        } else {
          pivot = toCompare;
        }
        pivotSelection = PivotSelection.NO;
        sorting = true;
        return;
      default:
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

  private int selectRandomPivot() { return rand.nextInt(right - left) + left; }

  private int[] selectPivot() {
    int range = right - left;

    int[] choices = new int[NUM_PROBES];
    boolean[] takenIndices = new boolean[range];

    choices[0] = rand.nextInt(range);
    takenIndices[choices[0]] = true;
    for (int i = 1; i < NUM_PROBES; i++) {
      int index;
      while (takenIndices[index = rand.nextInt(range)])
        ;
      takenIndices[index] = true;
      choices[i] = index;
    }
    return choices;
  }

  protected boolean doneCheck() { return done; }
}
