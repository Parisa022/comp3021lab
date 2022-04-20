package dm2;

import java.util.ArrayList;
import java.util.Collections;

public class SortedList <T extends Comparable<T>> {
	private ArrayList<T> list;
		
	public SortedList(ArrayList<T> list) {
		this.list = list;
		filter(list);
		bubbleSort(list);
	}
	
	
	void filter(ArrayList<T> list) {
		// TODO: implement the filtering criteria designated in the question.
		int n = list.size();
		ArrayList<T> a = new ArrayList();
		for (int i = 0; i<n;i++) {
			if (i%2 == 0) {
				a.add(list.get(i));
			}
		}
		list = a;
	}
	
	void bubbleSort(ArrayList<T> list) {
		// TODO: implement the bubble sort algorithm
		int n = list.size();
		for ( int i = 0; i < n-1; i++) {
			for (int j = 0; j< n-i-1; j++ ) {
				if (list.get(j).compareTo(list.get(j+1)) > 0) {
					T temp = list.get(j);
					list.set(j, list.get(j+1));
					list.set(j+1, temp);
				}
			}
		}
	}
	
	public ArrayList<T> getList() {
		return list;
	}
}