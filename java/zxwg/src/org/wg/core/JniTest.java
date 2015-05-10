package org.wg.core;

public class JniTest {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			Kernel.test();
		}
		System.out.println(System.currentTimeMillis() - start);
		start = System.currentTimeMillis();
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			test();
		}
		System.out.println(System.currentTimeMillis() - start);
	}

	public static void test() {

	}
}
