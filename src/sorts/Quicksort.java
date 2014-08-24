package sorts;
import java.util.EmptyStackException;
import java.util.Stack;

import sorts.business.SortInfo;

@SortInfo(name = "Quicksort (End Pivot)", designer = "Hoare (1961)", link = "http://en.wikipedia.org/wiki/Quicksort")
public class Quicksort extends Sort {

	private Stack<Range> ranges;
	
	private Range range;
	
	int i, j, p;
	
	private boolean sorting, done;
	
	private class Range {
		final int start, end;
		
		public Range(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}
	
	protected void initialize() {
		ranges = new Stack<Range>();
		ranges.push(new Range(0, arrayLength() - 1));
		sorting = done = false;
	}

//	protected void nextMove() {
//		if (!sorting) {
//			try {
//				range = ranges.pop();
//				
//				i = range.start - 1;
//				j = range.start;
//				p = range.end;
//				sorting = true;
//			} catch (EmptyStackException e) {
//				done = true;
//				return;
//			}
//		}
//		
//		if (j == p) {
//			swap(p, i+1);
//			if (range.end - (i+2) > 0) {
//				ranges.push(new Range(i + 2, range.end));
//			}
//			if (i - range.start > 0) {
//				ranges.push(new Range(range.start, i));
//			}
//			
//			sorting = false;
//		} else {
//			
//			if (this.compare(j, p) < 0) {
//				swap(j, i+1);
//				i++;
//			}
//			
//			j++;
//		}
//		
//		
//		
//	}
	
	
	protected void nextMove() {
		if (!sorting) {
			try {
				range = ranges.pop();
				
				i = range.start - 1;
				j = range.start;
				p = range.end;
				sorting = true;
			} catch (EmptyStackException e) {
				done = true;
				return;
			}
		}
		
		if (j == p) {
			swap(p, i+1);
			if (range.end - (i+2) > 0) {
				ranges.push(new Range(i + 2, range.end));
			}
			if (i - range.start > 0) {
				ranges.push(new Range(range.start, i));
			}
			
			sorting = false;
			nextMove();
		} else {
			
			if (this.compare(j, p) < 0) {
				swap(j, i+1);
				i++;
			}
			
			j++;
		}
		
		
		
	}

	protected boolean doneCheck() {
		return done;
	}
	
//	public static void main(String[] args) {
//		//int heapSize = 8;
//		//System.out.println((int) Math.pow(2, (int) (Math.log10(heapSize) / Math.log10(2)) - 1) - 1 + (heapSize - (int) Math.pow(2, (int) (Math.log10(heapSize) / Math.log10(2))) + 1 + 1)/2);
//		
//		Sort qs = new Quicksort();
//		qs.initialize(new int[]{4,7,6,9,6,5});
//
//		try {
//			do {
//				System.out.println(qs.getArrayToString());
//				System.out.println("Comps: " + qs.getComparisons() + " Swaps: " + qs.getSwaps());
//				//Thread.sleep(500);
//			} while (!qs.next());
//
//		} catch (SortCompletedException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public String toString() {
//		return "Quicksort";
//	}

}
