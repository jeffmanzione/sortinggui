package sorts;

import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

import sorts.business.SortInfo;

@SortInfo(name = "Quicksort (Random)", designer = "Hoare (1961)",
    link = "http://en.wikipedia.org/wiki/Quicksort")
public class QuicksortRandom extends Sort {

  // private static final int NUM_PROBES = 3;

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

    public String toString() {
      return "(" + start + "," + end + ")";
    }
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

        // System.out.println(range);
        // System.out.println("BEF " + this.getArrayToString());

        // System.out.printf("<<< LEFT=%d RIGHT=%d PIVOT=%d RANGE=%s SORTING=%b INCLEFT=%b
        // INCRIGHT=%b\n", left,
        // right, pivot, range, sorting, incLeft, incRight);

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
      // System.out.println("AFT " + this.getArrayToString());

      if (range.end - left > 0) {
        // System.out.println("RIGHT " + new Range(left, range.end));
        ranges.push(new Range(left, range.end));
      }
      if (right - range.start > 0) {
        // System.out.println("LEFT " + new Range(range.start, right));
        ranges.push(new Range(range.start, right));
      }

      sorting = false;
      nextMove();
    }
    //
    // if (j == p) {
    // swap(p, i + 1);
    // if (range.end - (i + 2) > 0) {
    // ranges.push(new Range(i + 2, range.end));
    // }
    // if (i - range.start > 0) {
    // ranges.push(new Range(range.start, i));
    // }
    //
    // sorting = false;
    // } else {
    //
    // if (this.compare(j, p) < 0) {
    // swap(j, i + 1);
    // i++;
    // }
    //
    // j++;
    // }

  }

  // private int[] selectPivot() {
  // // return left + (right - left) / 2;
  //
  // int range = right - left;
  //
  // int[] choices = new int[NUM_PROBES];
  // boolean[] takenIndices = new boolean[range];
  //
  // choices[0] = rand.nextInt(range);
  // takenIndices[choices[0]] = true;
  // for (int i = 1; i < NUM_PROBES; i++) {
  // int index;
  // while (takenIndices[index = rand.nextInt(range)])
  // ;
  // takenIndices[index] = true;
  // choices[i] = index;
  // }
  // return choices;
  //
  // }

  private int selectPivot() {
    return rand.nextInt(right - left) + left;

  }

  protected boolean doneCheck() {
    return done;
  }
}
