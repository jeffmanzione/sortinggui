package sorts;

import sorts.business.SortInfo;

@SortInfo(name = "Insertion Sort", designer = "N/A", link = "http://en.wikipedia.org/wiki/Insertion_sort")
public class InsertionSort extends Sort {

	int	index, cmpTo;

	protected void initialize () {
		index = 1;
		cmpTo = 0;
	}

	private void swapIntoPlaceAndReset() {
		for ( int i = index; i > cmpTo + 1; i-- ) {
			swap( i, i - 1 );
		}
		index++;
		cmpTo = index - 1;
	}
	
	protected void nextMove () {
		System.out.println(index);
		if ( this.compare( index, cmpTo ) >= 0 ) {
			swapIntoPlaceAndReset();
		} else {
			cmpTo--;
			if ( cmpTo == -1 ) {
				swapIntoPlaceAndReset();
			}
		}
	}

	protected boolean doneCheck () {
		return index == arrayLength();
	}

	public static void main ( String[] args ) {
		InsertionSort is = new InsertionSort();
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