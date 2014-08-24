package sorts;

import sorts.business.SortInfo;

@SortInfo(name = "Test Sort", designer = "N/A", link = "www.google.com")
public class TestSort extends Sort {
	
	int index = 0, end;

	protected void initialize() {
		
	}

	protected void nextMove() {
		
	}

	protected boolean doneCheck() {
		return false;
	}

	@Override
	public String toString() {
		return "Test Sort";
	}

}
