package sorts;

import java.util.EmptyStackException;
import java.util.Stack;

import sorts.business.SortInfo;

@SortInfo(name = "Quicksort (Middle Pivot)", designer = "Hoare (1961)", link = "http://en.wikipedia.org/wiki/Quicksort")
public class QuicksortMiddlePivot extends Sort {
	private Stack<Range> ranges;

	private Range range;

	int left, right, pivot;

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
				pivot = this.valueOf(left + (right - left) / 2);
				sorting = true;

				// System.out.println(range);
				// System.out.println("BEF " + this.getArrayToString());

				// System.out.printf("<<< LEFT=%d RIGHT=%d PIVOT=%d RANGE=%s SORTING=%b INCLEFT=%b INCRIGHT=%b\n", left,
				// right, pivot, range, sorting, incLeft, incRight);

			} catch (EmptyStackException e) {
				done = true;
				return;
			}
		}

		if (left <= right) {

			if (incLeft) {
				if (this.compareToValue(left, pivot) < 0) {
					left++;
				} else {
					incRight = true;
					incLeft = false;
					return;
				}
			}

			if (incRight) {
				if (this.compareToValue(right, pivot) > 0) {
					right--;
				} else {
					incRight = false;
				}
			}

			if (!incLeft && !incRight) {
				if (left <= right) {
					this.swap(left, right);
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

	protected boolean doneCheck() {
		return done;
	}
}
