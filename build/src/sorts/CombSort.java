package sorts;

import sorts.business.SortInfo;

@SortInfo(name = "Comb Sort", designer = "Dobosiewicz (1980)", link = "http://en.wikipedia.org/wiki/Comb_sort")
public class CombSort extends Sort {
	private int					index, gap;
	private static final double	shrink	= 1.3;

	private boolean				done	= false, didSwap = false;

	protected void nextMove () {
		
		if ( this.compare( index, index + gap ) > 0 ) {
			swap( index, index + gap );
			didSwap = true;
		}

		if ( (index + gap + 1) == arrayLength() ) {
			index = 0;

			int oldgap = gap;
			gap = (int) (gap / shrink);

			if ( gap <= 1 ) {
				index = 0;
				gap = 1;
			}
			if (!didSwap && oldgap == 1) {
				done = true;
			} else {
				didSwap = false;
			}

		} else {
			index++;
		}

	}

	// Better but not true to the algorithm
	// protected void nextMove() {
	// // Insertion sort when gap gets to 2 to optimize the last few passes
	// if (gap <= 1) {
	// if (this.compare(index, index - 1) >= 0) {
	// start++;
	// index = start;
	// } else {
	// swap(index, index - 1);
	// index--;
	//
	// if (index == 0) {
	// start++;
	// index = start;
	// }
	// }
	//
	// if (index == arrayLength()) {
	// done = true;
	// }
	// } else {
	// if (this.compare(index, index + gap) > 0) {
	// swap(index, index + gap);
	// }
	//
	// if ((index + gap + 1) == arrayLength()) {
	// index = 0;
	//
	// gap = (int) (gap / shrink);
	//
	// if (gap <= 1) {
	// index = 1;
	// }
	//
	// } else {
	// index++;
	// }
	// }
	// }

	// protected void nextMove() {
	// // System.out.println(index + " " + (index + gap));
	// if (this.compare(index, index + gap) > 0) {
	// swap(index, index + gap);
	// numSwaps++;
	// }
	//
	// if ((index + gap + 1) == arrayLength()) {
	// index = 0;
	// if (numSwaps == 0 && gap == 1) {
	// done = true;
	// } else {
	// gap = (int) (gap / shrink);
	// if (gap == 0) {
	// gap = 1;
	// }
	// numSwaps = 0;
	// }
	// } else {
	// index++;
	// }
	// }

	protected boolean doneCheck () {
		return done;
	}

	protected void initialize () {
		index = 0;
		gap = arrayLength() - 1;
	}
}
