package sorts;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Sort Class allows for generic construction of a sort so that it can be easily represented in a visual way.
 * 
 * @author Jeff
 *
 */
public abstract class Sort {

	private int[]				array;
	private int					comparisons;
	private int					swaps;

	private boolean				isDone;

	private LinkedList<Integer>	compared;
	private LinkedList<Integer>	swapped;

	/**
	 * Intializes the sort with an array
	 * 
	 * @param array
	 *            The array to sort
	 */
	public void initialize ( int[] array ) {
		if ( array == null ) {
			throw new NullPointerException();
		} else {
			isDone = false;
			this.array = array.clone();
			comparisons = 0;
			swaps = 0;
			compared = new LinkedList<Integer>();
			swapped = new LinkedList<Integer>();
			initialize();
		}

	}

	protected abstract void initialize ();

	protected void swap ( int indexA, int indexB ) {
		checkBounds( indexA, indexB );

		// swapped.clear();
		swapped.add( indexA );
		swapped.add( indexB );

		int tmp = array[indexA];
		array[indexA] = array[indexB];
		array[indexB] = tmp;

		swaps++;
	}

	public List<Integer> lastCompared () {
		return compared;
	}

	public List<Integer> lastSwapped () {
		return swapped;
	}

	private void checkBounds ( int indexA, int indexB ) {
		if ( !inBounds( indexA ) ) {
			throw new IndexOutOfBoundsException( "LBound is out of bounds" );
		} else if ( !inBounds( indexB ) ) {
			throw new IndexOutOfBoundsException( "RBound is out of bounds" );
		}
	}

	private void checkBound ( int index ) {
		if ( !inBounds( index ) ) {
			throw new IndexOutOfBoundsException( "Bound is out of bounds" );
		}
	}

	private boolean inBounds ( int index ) {
		return !(index < 0 || index >= array.length);

	}

	protected int compare ( int indexA, int indexB ) {
		checkBounds( indexA, indexB );

		comparisons++;

		// compared.clear();
		compared.add( indexA );
		compared.add( indexB );

		return array[indexA] - array[indexB];

	}

	// protected int compare(int currIndex, int leftIndex, int rightIndex) {
	// checkBounds(currIndex, leftIndex);
	// checkBounds(0, rightIndex);
	// int val;
	//
	// if (compare(leftIndex, rightIndex) > 0) {
	// if (compare(leftIndex, currIndex) > 0) {
	// val = -1;
	// } else {
	// val = 0;
	// }
	// } else {
	// if (compare(rightIndex, currIndex) > 0) {
	// val = 1;
	// } else {
	// val = 0;
	// }
	// }
	//
	// // compared.clear();
	// compared.add(currIndex);
	// compared.add(leftIndex);
	// compared.add(rightIndex);
	//
	// return val;
	//
	// }

	protected int compareToValue ( int index, int value ) {
		checkBound( index );

		comparisons++;

		compared.clear();
		compared.add( index );

		return array[index] - value;
	}

	/**
	 * Goes to the next step in the sort
	 * 
	 * @return <b>true</b> if the sort is completed, <b>false</b> otherwise.
	 * @throws SortCompletedException
	 *             If you call next and it is already sorted
	 */
	public boolean next () throws SortCompletedException {
		if ( isDone() ) {
			throw new SortCompletedException();
		} else if ( doneCheck() ) {
			done();
			return true;
		} else {
			swapped.clear();
			compared.clear();
			// nextMove();
			while ( !doneCheck() && compared.size() < 2 ) {
				nextMove();
			}

			if ( doneCheck() ) {
				done();
				return true;
			} else {
				return false;
			}
		}
	}

	protected abstract void nextMove ();

	protected abstract boolean doneCheck ();

	public int valueOf ( int index ) {
		checkBound( index );
		return array[index];
	}

	/**
	 * 
	 * @return The number of total comparisons
	 */
	public int getComparisons () {
		return comparisons;
	}

	/**
	 * 
	 * @return The number of total swaps
	 */
	public int getSwaps () {
		return swaps;
	}

	/**
	 * 
	 * @return The length of the array
	 */
	public int arrayLength () {
		return array.length;
	}

	protected void done () {
		isDone = true;
	}

	/**
	 * 
	 * @return <b>true</b> if the sort is completed, <b>false</b> otherwise.
	 */
	public boolean isDone () {
		return isDone;
	}

	/**
	 * 
	 * @return The string representation of the array.
	 */
	public String getArrayToString () {
		String result = "[" + array[0];
		for ( int i = 1; i < array.length; i++ ) {
			result += "," + array[i];
		}
		return result + "]";
	}

	/**
	 * 
	 * @return The string representation of the array.
	 */
	public static String getArrayToString ( int[] arr ) {
		String result = "[" + arr[0];
		for ( int i = 1; i < arr.length; i++ ) {
			result += "," + arr[i];
		}
		return result + "]";
	}

	public int[] getArray () {
		return array;
	}
}
