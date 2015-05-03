package sorts;

import sorts.business.SortInfo;

@SortInfo(name = "Shaker (Selection) Sort", designer = "N/A", link = "http://en.wikipedia.org/wiki/Selection_Sort")
public class ShakerSort extends Sort {
	private int		indexOfMin, indexOfMax;
	private int		start, index, end;
	private boolean	right	= true;

	protected void initialize () {
		start = 0;
		// min = valueOf(0);
		indexOfMin = 0;
		indexOfMax = arrayLength() - 1;
		index = 0;
		end = arrayLength() - 1;
	}

	protected void nextMove () {
		if ( right && index < end ) {
			if ( this.compare( index, indexOfMax ) > 0 ) {
				indexOfMax = index;
				index++;
			} else {
				right = false;
			}
		} else {
			if ( this.compare( index, indexOfMin ) < 0 ) {
				indexOfMin = index;
			}
			index++;

			if ( index >= end + 1) {
				if ( start == indexOfMax && end == indexOfMin ) {
					swap( indexOfMin, indexOfMax );
				} else if ( start == indexOfMax ) {
					swap( end, indexOfMax );
					swap( start, indexOfMin );
				} else {
					swap( start, indexOfMin );
					swap( end, indexOfMax );
				}
				end--;
				start++;
				index = start;
				indexOfMin = start;
				indexOfMax = end;
			}
			right = true;
		}
	}

	protected boolean doneCheck () {
		return end - start < 1;
	}

	public static void main ( String[] args ) {
		ShakerSort ss = new ShakerSort();
		ss.initialize( new int[] { 4, 7, 6, 9, 6, 4, 3, 1, 2, 0, 5, 3 } );

		try {
			do {
				System.out.println( ss.getArrayToString() );
				System.out.println( "Comps: " + ss.getComparisons() + " Swaps: " + ss.getSwaps() );
			} while ( !ss.next() );

		} catch ( SortCompletedException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString () {
		return "Selection Sort";
	}
}
