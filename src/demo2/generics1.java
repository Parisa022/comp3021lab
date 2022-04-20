package demo2;

import java.util.Vector;

public class generics1<T> {
	Vector<T> v = new Vector();
	public void enqueue(T o) {
		v.add(o);
	}
	public T dequeue() {
		if (v.size() == 0) return null;
		T o = v.get(v.size()-1);
		v.remove(o);
		return o;
	}
	public void intake (generics1 tmp) {
		T o = (T) tmp.dequeue();
		while(o != null) {
			enqueue(o);
			o = (T) tmp.dequeue();
		}
	}
}
