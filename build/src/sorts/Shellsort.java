package sorts;

import sorts.business.SortInfo;

@SortInfo(name = "Shellsort", designer = "Shell (1959), Ciura (2001)", link = "http://en.wikipedia.org/wiki/Shell_sort")
public class Shellsort extends Sort {

	// Ciura numbers
	private static final int[] gaps = { 701, 301, 132, 57, 23, 10, 4, 1 };

	private int gap_index, i, j;

	private boolean done;

	@Override
	protected void initialize() {
		done = false;
		gap_index = 0;
		while (gaps[gap_index] > arrayLength()) {
			gap_index++;
		}

		i = gaps[gap_index];
		j = 0;
	}

	@Override
	protected void nextMove() {
		//System.out.printf("GINEX=%d GAP=%d INDEX=%d J=%d\n", gap_index, gaps[gap_index], i, j);

		if (j < 0) {
			// reached end of insertion
			if (i + 1 == arrayLength()) {
				// reset with new gap
				resetNewGap();
			} else {
				// inc gap
				incGap();
			}
		}

		if (j >= 0) {
			// during insertion
			comparison();
		}
	}

	private void comparison() {
		// swap if out of order
		if (this.compare(j, j + gaps[gap_index]) > 0) {
			this.swap(j, j + gaps[gap_index]);

			j -= gaps[gap_index];
		} else if (i + 1 == arrayLength()) {
			resetNewGap();
		} else {
			incGap();
		}

	}

	private void incGap() {
		i++;
		j = i - gaps[gap_index];
	}

	private void resetNewGap() {
		gap_index++;

		if (gap_index >= gaps.length) {
			// already did gap index 1
			done = true;
		} else {
			// go to next gap
			i = gaps[gap_index];
			j = i - gaps[gap_index];
		}
	}

	@Override
	protected boolean doneCheck() {
		return done;
	}

}
