package sorts;

import sorts.business.SortInfo;

@SortInfo(name = "Cocktail Sort", designer = "N/A", link = "http://en.wikipedia.org/wiki/Cocktail_sort")
public class CocktailSort extends Sort {

	private int start, end, index, leftLastSwapIndex, rightLastSwapIndex;
	private boolean forward;

	private int numSwaps;

	private boolean done = false;

	protected void initialize() {
		end = arrayLength() - 1;
		// unsorted = arrayLength();
		start = index = 0;
		forward = true;
	}

	protected void nextMove() {
		// System.out.println("START=" + start + " END=" + end + " INDEX=" + index + " FORWARD=" + forward + " DONE="
		// + done + " leftIndex=" + leftLastSwapIndex + " rightIndex=" + rightLastSwapIndex);

		if (forward) {
			if (this.compare(index, index + 1) > 0) {
				swap(index, index + 1);
				rightLastSwapIndex = index;
				numSwaps++;
			}
			if (index + 1 == end) {
				// unsorted--;
				end = rightLastSwapIndex;
				index = end;
				forward = false;

				if (rightLastSwapIndex == leftLastSwapIndex || numSwaps < 1) {
					done = true;
				}

				numSwaps = 0;

			} else {
				index++;
			}
		} else {
			if (this.compare(index, index - 1) < 0) {
				swap(index, index - 1);
				leftLastSwapIndex = index;
				numSwaps++;
			}
			if ((index - 1 == start) && (index > 0)) {
				// unsorted--;
				start = leftLastSwapIndex;
				index = start;
				forward = true;

				if (rightLastSwapIndex == leftLastSwapIndex || numSwaps < 1) {
					done = true;
				}

				numSwaps = 0;

				// System.out.println("END=" + end + " INDEX=" + index + " FORWARD=" + forward + " DONE=" + done);

			} else {
				index--;
			}
		}
	}

	protected boolean doneCheck() {
		return done;
	}

}
