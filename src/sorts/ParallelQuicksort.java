package sorts;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;

import sorts.business.SortInfo;

@SortInfo(name = "Parallel Quicksort (End Pivot)", designer = "Hoare (1961)", link = "http://en.wikipedia.org/wiki/Quicksort")
public class ParallelQuicksort extends Sort {

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

		int i, j, p;

		private boolean sorting;
		private Range range;

		public SimulatedThread(Range range) {
			this.range = range;

			//System.out.println("RANGE = " + range);
		}

		private void nextMove(Iterator<SimulatedThread> iter) {
			if (!sorting) {
				try {
					i = range.start - 1;
					j = range.start;
					p = range.end;
					sorting = true;
				} catch (EmptyStackException e) {
					// done = true;
					return;
				}
			}

			if (j == p) {

				swap(p, i + 1);
				if (range.end - (i + 2) > 0) {
					nextRanges.add(new SimulatedThread(new Range(i + 2,
							range.end)));
				}
				if (i - range.start > 0) {
					nextRanges.add(new SimulatedThread(
							new Range(range.start, i)));
				}
				sorting = false;
				
				iter.remove();
				this.nextMove(iter);
			} else {

				if (ParallelQuicksort.this.compare(j, p) < 0) {
					swap(j, i + 1);
					i++;
				}

				j++;
			}

		}

	}

	protected void nextMove() {
		//System.out.println(ranges.size());
		nextRanges.clear();
		Iterator<SimulatedThread> iter = ranges.iterator();
		while (iter.hasNext()) {
			SimulatedThread st = iter.next();
			st.nextMove(iter);
		}
		ranges.addAll(nextRanges);
		//System.out.println(ranges.size());
	}

	protected boolean doneCheck() {
		return ranges.isEmpty();
	}

}
