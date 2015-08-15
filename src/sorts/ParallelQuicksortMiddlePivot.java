package sorts;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;

import sorts.business.SortInfo;

@SortInfo(name = "Parallel Quicksort (Middle Pivot)", designer = "Hoare (1961)",
    link = "http://en.wikipedia.org/wiki/Quicksort")
public class ParallelQuicksortMiddlePivot extends Sort {

  private List<SimulatedThread> ranges, nextRanges;

  private class Range {
    final int start, end;

    public Range(int start, int end) {
      this.start = start;
      this.end = end;
    }

    public String toString() {
      return "[" + start + "," + end + "]";
    }
  }

  protected void initialize() {
    ranges = new ArrayList<>();
    nextRanges = new ArrayList<>();
    ranges.add(new SimulatedThread(new Range(0, arrayLength() - 1)));
    // sorting = done = false;
  }

  private class SimulatedThread {

    private Range range;

    int left, right, pivot;

    private boolean sorting;
    private boolean incLeft, incRight;

    public SimulatedThread(Range range) {
      this.range = range;

      // System.out.println("RANGE = " + range);
    }

    private void nextMove(Iterator<SimulatedThread> iter) {
      if (!sorting) {
        try {
          incLeft = true;
          incRight = false;

          left = range.start;
          right = range.end;
          pivot = left + (right - left) / 2;
          sorting = true;

          // System.out.println(range);
          // System.out.println("BEF " + getArrayToString());

          // System.out.printf("<<< LEFT=%d RIGHT=%d PIVOT=%d RANGE=%s SORTING=%b INCLEFT=%b
          // INCRIGHT=%b\n",
          // left,
          // right, pivot, range, sorting, incLeft, incRight);

        } catch (EmptyStackException e) {
          return;
        }
      }

      if (left <= right) {

        if (incLeft) {
          if (compare(left, pivot) < 0) {
            left++;
          } else {
            incRight = true;
            incLeft = false;
            return;
          }
        }

        if (incRight) {
          if (compare(right, pivot) > 0) {
            right--;
          } else {
            incRight = false;
          }
        }

        if (!incLeft && !incRight) {
          if (left <= right) {
            swap(left, right);

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
        // System.out.println("AFT " + getArrayToString());

        if (range.end - left > 0) {
          // System.out.println("RIGHT " + new Range(left, range.end));
          nextRanges.add(new SimulatedThread(new Range(left, range.end)));
        }
        if (right - range.start > 0) {
          // System.out.println("LEFT " + new Range(range.start, right));
          nextRanges.add(new SimulatedThread(new Range(range.start, right)));
        }

        sorting = false;
        iter.remove();
        nextMove(iter);
      }
    }

  }

  protected void nextMove() {
    // System.out.println(ranges.size());
    nextRanges.clear();
    Iterator<SimulatedThread> iter = ranges.iterator();
    while (iter.hasNext()) {
      SimulatedThread st = iter.next();
      st.nextMove(iter);
    }
    ranges.addAll(nextRanges);
    // System.out.println(ranges.size());
  }

  protected boolean doneCheck() {
    return ranges.isEmpty();
  }

  @Override
  public String toString() {
    return "Threaded Quicksort";
  }
}
