package sorts;

import sorts.business.SortInfo;

@SortInfo(name = "Selection Sort", designer = "N/A",
    link = "http://en.wikipedia.org/wiki/Selection_Sort")
public class SelectionSort extends Sort {
  private int indexOfMin;
  private int start, index;

  protected void initialize() {
    start = 0;
    // min = valueOf(0);
    indexOfMin = 0;
    index = 1;
  }

  protected void nextMove() {
    if (this.compare(index, indexOfMin) < 0) {
      // min = valueOf(index);
      indexOfMin = index;
    }

    index++;

    if (index == arrayLength()) {
      index = start + 2;
      swap(start, indexOfMin);
      start++;
      // min = valueOf(start);
      indexOfMin = start;
    }
  }

  protected boolean doneCheck() {
    return start == arrayLength() - 1;
  }

  public static void main(String[] args) {
    SelectionSort ss = new SelectionSort();
    ss.initialize(new int[] { 4, 7, 6, 9, 6, 4, 3, 1, 2, 0, 5, 3 });

    try {
      do {
        System.out.println(ss.getArrayToString());
        System.out.println("Comps: " + ss.getComparisons() + " Swaps: " + ss.getSwaps());
      } while (!ss.next());

    } catch (SortCompletedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return "Selection Sort";
  }
}
