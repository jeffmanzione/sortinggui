package sorts;

import sorts.business.SortInfo;

@SortInfo(name = "Gnome Sort", designer = "Sarbazi-Azad (2000)", link = "http://en.wikipedia.org/wiki/Gnome_sort")
public class GnomeSort extends Sort {

	int index, start;
	
	protected void initialize() {
		index = start = 1;
	}

	protected void nextMove() {
		if (this.compare(index, index - 1) >= 0) {
			start++;
			index = start;
		} else {
			swap(index, index - 1);
			index--;
			
			if (index == 0) {
				start++;
				index = start;
			}
		}
	}

	protected boolean doneCheck() {
		return index == arrayLength();
	}
	
	public static void main(String[] args) {
		GnomeSort is = new GnomeSort();
		is.initialize(new int[]{4,7,6,9,6,4,3,1,2,0,5,3});

		try {
			do {
				System.out.println(is.getArrayToString());
				System.out.println("Comps: " + is.getComparisons() + " Swaps: " + is.getSwaps());
			} while (!is.next());
			
		} catch (SortCompletedException e) {
			e.printStackTrace();
		}
	}

}