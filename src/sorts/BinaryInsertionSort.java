package sorts;

import sorts.business.SortInfo;

@SortInfo(name = "Binary Insertion Sort", designer = "N/A", link = "http://en.wikipedia.org/wiki/Insertion_sort")
public class BinaryInsertionSort extends Sort {

	int	index, rangeStart, rangeEnd, mid;

	protected void initialize () {
		index = 1;
		rangeStart = 0;
		rangeEnd = 1;
	}

	private void calcMid () {
		mid = (rangeStart + rangeEnd) / 2;
	}

	private void swapIntoPlaceAndReset () {
		for ( int i = index; i > rangeEnd; i-- ) {
			swap( i, i - 1 );
		}
		index++;
		rangeStart = 0;
		rangeEnd = index;
	}

	protected void nextMove () {
		calcMid();
		if ( this.compare( index, mid ) >= 0 ) {
			rangeStart = mid + 1;
		} else {
			rangeEnd = mid;
		}

		if ( rangeStart >= rangeEnd ) {
			swapIntoPlaceAndReset();
		}

	}

	protected boolean doneCheck () {
		return index == arrayLength();
	}

	public static void main ( String[] args ) {
		BinaryInsertionSort is = new BinaryInsertionSort();
		is.initialize( new int[] { 4, 7, 6, 9, 6, 4, 3, 1, 2, 0, 5, 3 } );

		try {
			do {
				System.out.println( is.getArrayToString() );
				System.out.println( "Comps: " + is.getComparisons() + " Swaps: " + is.getSwaps() );
			} while ( !is.next() );

		} catch ( SortCompletedException e ) {
			e.printStackTrace();
		}
	}

}