package test;

public class Test1 {
	public static class A {}
	public static class B extends A {}
	public static class C extends B {}
	public static void main() {
		A a = new A();
		A b =  new B();
		A c = new C();
		System.out.println(b instanceof B);
	}
}
