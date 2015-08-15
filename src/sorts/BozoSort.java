package sorts;

import java.util.Random;

import sorts.business.SortInfo;

@SortInfo(name = "Bozo Sort", designer = "N/A", link = "http://en.wikipedia.org/wiki/Bogosort")
public class BozoSort extends Sort {

  int index;

  protected void initialize() {
    index = 0;
  }

  protected void nextMove() {
    if (this.compare(index, index + 1) > 0) {
      Random rand = new Random();
      int indexA = rand.nextInt(arrayLength());
      int indexB = rand.nextInt(arrayLength());
      swap(indexA, indexB);
      index = 0;
    } else {
      index++;
    }

  }

  protected boolean doneCheck() {
    return index == arrayLength() - 1;
  }

  public static void main(String[] args) {
    BozoSort bs = new BozoSort();
    bs.initialize(new int[] { 4, 7, 6, 9, 6, 4 });

    try {
      do {
        System.out.println(bs.getArrayToString());
        System.out.println("Comps: " + bs.getComparisons() + " Swaps: " + bs.getSwaps());
      } while (!bs.next());

    } catch (SortCompletedException e) {
      e.printStackTrace();
    }
  }

}
