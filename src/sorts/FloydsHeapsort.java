package sorts;

import sorts.business.SortInfo;

@SortInfo(name = "Floyd's Heapsort", designer = "Floyd (1964)", link = "http://en.wikipedia.org/wiki/Heapsort")
public class FloydsHeapsort extends Heapsort {

	private boolean siftUp = false, siftDown = true;

	protected void doneSift() {

		if (siftDown) {
			siftUp = true;
			siftDown = false;
			//System.out.println("Done sift down! " + index);


		} else if (siftUp) {
			siftUp = false;
			siftDown = true;
			//System.out.println("Done sift up! " + index);
			
			if (heapify) {
				heapify = false;
				curr_index = 0;
			}
		}

		if (buildMaxHeap) {
			if (siftDown) {
				index--;
				curr_index = index;
			}
			
			if (index == -1) {

				//System.out.println("\nFINISHED BUILDING!\n");
				buildMaxHeap = false;
				index = curr_index = 0;
			}
		}
	}

	protected void nextMove() {
		if (buildMaxHeap || heapify) {
			if (siftDown) {
				//System.out.println("Current for sift down: " + this.valueOf(curr_index));
				if (hasLeftChild(curr_index)) {
					int leftChild = leftChild(curr_index);

					if (hasRightChild(curr_index)) {
						int rightChild = rightChild(curr_index);

						if (this.compare(leftChild, rightChild) > 0) {
							swap(leftChild, curr_index);
							curr_index = leftChild;
						} else {
							swap(rightChild, curr_index);
							curr_index = rightChild;
						}

					} else {
						swap(leftChild, curr_index);
						curr_index = leftChild;
						doneSift();
					}
				} else {
					doneSift();
				}
			} else if (siftUp) {
				//System.out.println("Current for sift up: " + this.valueOf(curr_index));
				if (hasParent(curr_index) && (heapify || parent(curr_index) >= index)) {
					int parent = parent(curr_index);

					if (this.compare(curr_index, parent) > 0) {
						swap(curr_index, parent);
						curr_index = parent;
					} else {
						doneSift();
					}

				} else {
					doneSift();
				}
			} else {
				//System.err.println("HUH???");
			}
		} else {
			//System.out.println("Here");
			swap(0, heapSize - 1);
			heapSize--;
			heapify = true;
		}
	}


//	public static void main(String[] args) {
//		//int heapSize = 8;
//		////System.out.println((int) Math.pow(2, (int) (Math.log10(heapSize) / Math.log10(2)) - 1) - 1 + (heapSize - (int) Math.pow(2, (int) (Math.log10(heapSize) / Math.log10(2))) + 1 + 1)/2);
//
//		FloydsHeapsort fhs = new FloydsHeapsort();
//		fhs.initialize(new int[] {4,7,6,9,6,5,8,9,4,5,9,3,7});
//
//		try {
//			do {
//				System.out.println(fhs.getArrayToString());
//				////System.out.println("Comps: " + fhs.getComparisons() + " Swaps: " + fhs.getSwaps());
//				//Thread.sleep(500);
//			} while (!fhs.next());
//
//		} catch (SortCompletedException e) {
//			e.printStackTrace();
//		}
//	}

}
