package demo2;

import java.util.ArrayList;



public class bubblesort<T extends Comparable> implements Filter{
	public void sorting(ArrayList<T> arr) {
		int n = arr.size();
		for ( int i = 0; i < n-1; i++) {
			for (int j = 0; j< n-i-1; j++ ) {
				if (arr.get(j).compareTo(arr.get(j+1)) > 0) {
					T temp = arr.get(j);
					arr.set(j, arr.get(j+1));
					arr.set(j+1, temp);
				}
			}
		}
	}
	
	public void filtering(ArrayList<T> arr) {
		int n = arr.size();
		for (int i = 0; i < n; i++) {
			if (i%2 == 0) {
				arr.remove(i);
			}
		}
	}
	
	public static void main(String args[]) {
//		bubblesort bs = new bubblesort();
//		ArrayList<Test> arr = new ArrayList();
//		arr.add(new Test("yeifh"));
//		arr.add(new Test("heufheu"));
//		arr.add(new Test("jeifei"));
//		arr.add(new Test("Appef"));
//		bs.sorting(arr);
//		for (Test i : arr) {
//			System.out.println(i);
//		}
//		bs.filtering(arr);
//		System.out.println("After filtering: ");
//		for (Test i : arr) {
//			System.out.println(i);
//		}
	}

	@Override
	public boolean comparing(Object t1, Object t2) {
		// TODO Auto-generated method stub
		return false;
	}
}


